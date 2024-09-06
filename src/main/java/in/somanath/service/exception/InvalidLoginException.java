package in.somanath.service.exception;

@SuppressWarnings("serial")
public class InvalidLoginException extends RuntimeException {
	
	public InvalidLoginException(String message) {
		super(message);
	}

}
