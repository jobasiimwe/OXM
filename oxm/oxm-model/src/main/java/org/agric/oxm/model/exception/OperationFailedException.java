package org.agric.oxm.model.exception;

public class OperationFailedException extends Exception {

    private static final long serialVersionUID = 2832559624459402988L;

    public OperationFailedException() {
	super();
    }

    public OperationFailedException(String arg0, Throwable arg1) {
	super(arg0, arg1);
    }

    public OperationFailedException(String arg0) {
	super(arg0);

    }

    public OperationFailedException(Throwable arg0) {
	super(arg0);
    }

}
