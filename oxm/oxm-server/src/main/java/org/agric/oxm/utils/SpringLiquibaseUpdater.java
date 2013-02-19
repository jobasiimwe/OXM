package org.agric.oxm.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Vector;

import javax.sql.DataSource;

import liquibase.FileOpener;
import liquibase.Liquibase;
import liquibase.exception.LiquibaseException;

import org.agric.oxm.model.exception.UnexpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

/**
 * This class handles the execution of the application liquibase changesets
 * 
 * @author ctumwebaze
 * 
 */
public class SpringLiquibaseUpdater implements ResourceLoaderAware, FileOpener {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private DataSource dataSource;
	private String changeLog;
	private String contexts;
	private ResourceLoader resourceLoader;

	/**
	 * initialization method used to setup the liquibase subsystem and also
	 * execution of the changesets
	 * 
	 * @throws Exception
	 *             throws this exception when their is an exception in the
	 *             initialization or execution of the liquibase subsystem or
	 *             execution of the liquibase changesets
	 */
	public void init() throws Exception {
		try {
			log.info("Change-log is " + changeLog);
			this.getResourceAsStream(getChangeLog());
		} catch (Exception ex) {
			log.warn("LiquibaseChangeLog doesnot exist: " + getChangeLog());
			return;
		}

		try {

			Liquibase liquibase = new Liquibase(getChangeLog(), this,
					getDataSource().getConnection());
			liquibase.update(contexts);
		} catch (LiquibaseException ex) {
			throw new UnexpectedException(
					"Liquibase Database Update failure", ex);
		}
	}

	/**
	 * gets the datasource for the application
	 * 
	 * @return the dataSource
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * sets the datasource for the application
	 * 
	 * @param dataSource
	 *            the dataSource to set
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * gets the changelog file to execute
	 * 
	 * @return the changeLog
	 */
	public String getChangeLog() {
		return changeLog;
	}

	/**
	 * sets the changelog file to run/execute
	 * 
	 * @param changeLog
	 *            the changeLog to set
	 */
	public void setChangeLog(String changeLog) {
		this.changeLog = changeLog;
	}

	/**
	 * gets the resource loader used in the loading of the liquibase changeset
	 * files
	 * 
	 * @return
	 */
	private ResourceLoader getResourceLoader() {
		return this.resourceLoader;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.ResourceLoaderAware#setResourceLoader(org
	 * .springframework.core.io.ResourceLoader)
	 */
	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * liquibase.resource.ResourceAccessor#getResourceAsStream(java.lang.String)
	 */
	@Override
	public InputStream getResourceAsStream(String file) throws IOException {
		return getResourceLoader().getResource(
				appendClassPathPrefixIfNecessary(file)).getInputStream();
	}

	/**
	 * appends a classpath prefix to the given file
	 * 
	 * @param file
	 * @return
	 */
	private String appendClassPathPrefixIfNecessary(String file) {
		if (isClassPathPrefixPresent(changeLog)
				&& !isClassPathPrefixPresent(file)) {
			return ResourceLoader.CLASSPATH_URL_PREFIX + file;
		} else {
			return file;
		}
	}

	/**
	 * @param changeLog2
	 * @return
	 */
	private boolean isClassPathPrefixPresent(String file) {
		return file.startsWith(ResourceLoader.CLASSPATH_URL_PREFIX);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see liquibase.resource.ResourceAccessor#getResources(java.lang.String)
	 */
	@Override
	public Enumeration<URL> getResources(String resource) throws IOException {
		Vector<URL> urls = new Vector<URL>();
		urls.add(getResourceLoader().getResource(resource).getURL());
		return urls.elements();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see liquibase.resource.ResourceAccessor#toClassLoader()
	 */
	@Override
	public ClassLoader toClassLoader() {
		return getResourceLoader().getClassLoader();
	}
}
