package org.agric.oxm.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.agric.oxm.model.User;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

public final class WebConstants {

	/**
	 * default private constructor
	 */
	private WebConstants() {

	}

	/**
	 * name of the header in a request. The represent of this header in the
	 * request indicates that the request is an ajax call
	 */
	public static final String AJAX_REQUEST_HEADER = "X-AjaxRequest";

	/**
	 * the value specified for an ajax request in the header
	 */
	public static final String AJAX_REQUEST_HEADER_SET = "X-AjaxRequest=1";

	/**
	 * model attribute name that can be used to access the system message of an
	 * operation.
	 */
	public static final String MODEL_ATTRIBUTE_SYSTEM_MESSAGE = "systemMessage";

	/**
	 * model attribute name that can be used to access the error message
	 */
	public static final String MODEL_ATTRIBUTE_ERROR_MESSAGE = "errorMessage";

	/**
	 * model attribute name that can be used to access the content header
	 * message
	 */
	public static final String CONTENT_HEADER = "contentHeader";

	/**
	 * model attribute name for long-response-text
	 */
	public static final String LONG_RESPONSE_TEXT = "longResponseText";

	/**
	 * Request parameter name for the search query
	 */
	public static final String SEARCH_QUERY_REQUEST_PARAMETER_NAME = "query";

	/**
	 * represents the default image size of the uploaded images.
	 */
	public static final long DEFAULT_IMAGE_SIZE_IN_BYTES = 100000;

	/**
	 * checks if its a valid image
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

	public static void writePictureOnResponse(String contentType, byte[] pic,
			HttpServletResponse response) throws IOException {
		try {
			response.setContentType(contentType);
			response.getOutputStream().write(pic);
		} catch (IOException e) {
		} finally {
			response.getOutputStream().flush();
		}
	}

	public static ModelAndView DEFAULT_PAGE(User logginedUser) {
		ModelMap model = new ModelMap();
		if (logginedUser.getProfilePicture() == null) {
			model.put("profile_Img", "empty");
		}

		model.put("user", logginedUser);

		return new ModelAndView("dashboard", model);
	}

	public static void loadLoggedInUserProfile(User u, ModelMap model) {
		if (u.getProfilePicture() == null) {
			model.put("profile_Img", "empty");
		}

		model.put("loggedUser", u);
	}
}
