package org.agric.oxm.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.agric.oxm.server.OXMConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

public class WebUtils {

    private static Logger log = LoggerFactory.getLogger(WebUtils.class);

    public static void prepareNavigation(String itemName, long totalNumberOfItems,
	    int numberOfItemsOnPage, int currentPage,
	    String url, ModelMap model) {

	double numberOfPages = totalNumberOfItems / (double) OXMConstants.MAX_NUM_PAGE_RECORD;
	int numPages = (int) Math.rint(numberOfPages);

	if (numPages < numberOfPages) {
	    numPages += 1;
	}

	model.put("navigationItem", itemName);
	model.put("navigationCurrentPage", currentPage);
	model.put("navigationTotalPages", numPages);
	model.put("navigationTotalNumberOfItems", totalNumberOfItems);
	model.put("navigationNumberOfItemsOnPage", numberOfItemsOnPage);
	model.put("navigationUrl", url);
    }

    public static void writeErrorMessageOnResponse(HttpServletResponse response, String message) {
	try {
	    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    response.getWriter().write(message);
	    response.getWriter().flush();
	} catch (IOException e) {
	    log.error(WebUtils.class.getName(), e);
	}
    }

    public static boolean isFileAnImage(MultipartFile file) {
	if (file != null && file.getSize() > 0) {
	    if (!file.getContentType().startsWith("image")) {
		return false;
	    }
	}
	return true;
    }

    public static boolean isValidSize(MultipartFile file, long size) {
	if(file.getSize() <= size)
	    return true;
	
	return false;
    }
}
