package org.agric.oxm.web;

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
     * Request parameter name for the search query
     */
    public static final String SEARCH_QUERY_REQUEST_PARAMETER_NAME = "query";

    /**
     * represents the default image size of the uploaded images.
     */
    public static final long DEFAULT_IMAGE_SIZE_IN_BYTES = 100000;
}
