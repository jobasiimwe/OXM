package org.agric.oxm.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.agric.oxm.server.OXMConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author Job
 * 
 */
public class WebUtils {

	private static Logger log = LoggerFactory.getLogger(WebUtils.class);

	/**
	 * adds navigation properties in the given model
	 * 
	 * @param itemName
	 *            name of the item that will be navigated i.e Staff etc
	 * @param currentPage
	 *            the current page to be viewed
	 * @param numberOfPages
	 *            total number of pages
	 * @param url
	 *            the url to which the page numbers will be appended
	 * @param model
	 */
	public static void prepareNavigation(String itemName,
			long totalNumberOfItems, int numberOfItemsOnPage, int currentPage,
			String url, ModelMap model) {

		double numberOfPages = totalNumberOfItems
				/ (double) OXMConstants.MAX_NUM_PAGE_RECORD;
		int numPages = (int) Math.rint(numberOfPages);

		/*
		 * if the number of pages after the round off is less than the computed
		 * number of pages, then we increment the rounded off number by 1 so
		 * that the records that are in the fraction of the page are shown.
		 */
		if (numPages < numberOfPages) {
			numPages += 1;
		}

		model.put("navigationItem", itemName);
		model.put("navigationCurrentPage", currentPage);
		model.put("navigationTotalPages", numPages);
		model.put("navigationTotalNumberOfItems", totalNumberOfItems);
		model.put("navigationNumberOfItemsOnPage", numberOfItemsOnPage);
		model.put("navigationMaxNumRecordPerPage",
				OXMConstants.MAX_NUM_PAGE_RECORD);
		model.put("navigationUrl", url);
	}

	/**
	 * writes the given error message on the given servlet response object
	 * 
	 * @param response
	 * @param message
	 */
	public static void writeErrorMessageOnResponse(
			HttpServletResponse response, String message) {
		try {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write(message);
			response.getWriter().flush();
		} catch (IOException e) {
			log.error(WebUtils.class.getName(), e);
		}
	}

	/**
	 * checks if the given MultipartFile is an image.
	 * 
	 * @param file
	 * @return
	 */
	public static boolean isFileAnImage(MultipartFile file) {
		if (file != null && file.getSize() > 0) {
			if (!file.getContentType().startsWith("image")) {
				return false;
			}
		}
		return true;
	}

	/**
	 * checks whether the given MultipartFile is of the valid size. I.e. the
	 * file size is not greater than the given size.
	 * 
	 * @param file
	 * @param size
	 * @return
	 */
	public static boolean isValidSize(MultipartFile file, long size) {
		if (file.getSize() <= size)
			return true;

		return false;
	}

	/**
	 * NOT UNIT TESTED Returns the base url (e.g,
	 * <tt>http://myhost:8080/myapp</tt>) suitable for using in a base tag or
	 * building reliable urls.
	 */
	public static String getBaseUrl(HttpServletRequest request) {
		if ((request.getServerPort() == 80) || (request.getServerPort() == 443))
			return request.getScheme() + "://" + request.getServerName()
					+ request.getContextPath();
		else
			return request.getScheme() + "://" + request.getServerName() + ":"
					+ request.getServerPort() + request.getContextPath();
	}

}
