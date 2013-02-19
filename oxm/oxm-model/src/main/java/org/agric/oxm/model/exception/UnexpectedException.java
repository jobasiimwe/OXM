package org.agric.oxm.model.exception;

/**
 * 
 * @author Job
 *
 */
public class UnexpectedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2350440537721966581L;

	/**
     * 
     */
	public UnexpectedException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public UnexpectedException(String message, Throwable arg1) {
		super(message, arg1);
	}

	/**
	 * @param arg0
	 */
	public UnexpectedException(String message) {
		super(message);

	}

	/**
	 * @param arg0
	 */
	public UnexpectedException(Throwable arg0) {
		super(arg0);
	}

}
