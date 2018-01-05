package exception;

public class IllegalOperatorException extends Exception {

	private static final long serialVersionUID = 98171876941920565L;

	public IllegalOperatorException() {
	}

	public IllegalOperatorException(String arg0) {
		super(arg0);
	}

	public IllegalOperatorException(Throwable arg0) {
		super(arg0);
	}

	public IllegalOperatorException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public IllegalOperatorException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
