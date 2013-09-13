package org.agric.oxm.model.exception;

public class SessionExpiredException extends Exception {

    private static final long serialVersionUID = 7704872568564851161L;

    public SessionExpiredException() {
	super();
    }

    public SessionExpiredException(String arg0, Throwable arg1) {
	super(arg0, arg1);
    }

    public SessionExpiredException(String arg0) {
	super(arg0);

    }

    public SessionExpiredException(Throwable arg0) {
	super(arg0);
    }

}
