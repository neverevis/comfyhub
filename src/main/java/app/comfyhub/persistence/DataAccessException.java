package app.comfyhub.persistence;

public class DataAccessException extends RuntimeException {
	public DataAccessException(Throwable t) {
		super(t);
	}

	public DataAccessException(String message) {
		super(message);
	}

}