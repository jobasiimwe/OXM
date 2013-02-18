package org.agric.oxm.model.exception;

public class DatabaseUpdateException extends Exception {

	private static final long serialVersionUID = 2985458643320186396L;

	public DatabaseUpdateException() {
	}

	public DatabaseUpdateException(String message) {
		super(message);
	}

	public DatabaseUpdateException(Throwable cause) {
		super(cause);
	}

	public DatabaseUpdateException(String message, Throwable cause) {
		super(message, cause);
	}
}
