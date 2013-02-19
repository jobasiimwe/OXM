package org.agric.oxm.server;

/**
 * This class holds constants used in the Mohr system.
 * 
 * @author smuwanga
 * @author ctumwebaze
 * 
 */
public final class RmsConstants {

	/**
	 * default constructor. does not allow instantiation
	 */
	private RmsConstants() {
	}

	/**
	 * OS key
	 */
	public static final String OPERATING_SYSTEM_KEY = "os.name";
	/**
	 * Name of the OS running the system
	 */
	public static final String OPERATING_SYSTEM = System
			.getProperty(OPERATING_SYSTEM_KEY);
	/**
	 * OS name Windows XP
	 */
	public static final String OPERATING_SYSTEM_WINDOWS_XP = "Windows XP";
	/**
	 * OS name Windows Vista
	 */
	public static final String OPERATING_SYSTEM_WINDOWS_VISTA = "Windows Vista";
	/**
	 * os name Linux
	 */
	public static final String OPERATING_SYSTEM_LINUX = "Linux";
	/**
	 * os name FreeBSD
	 */
	public static final String OPERATING_SYSTEM_FREEBSD = "FreeBSD";
	/**
	 * os name Mac OS X
	 */
	public static final String OPERATING_SYSTEM_OSX = "Mac OS X";

	/**
	 * the maximum number of records to return when queried through pages.
	 */
	public static final int MAX_NUM_PAGE_RECORD = 25;

	/**
	 * the property key used to access the name path of the user's home
	 * directory currently running this application
	 */
	public static final String USER_HOME_DIRECTORY_KEY = "user.home";

}
