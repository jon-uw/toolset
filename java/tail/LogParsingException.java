package tail;

public class LogParsingException extends RuntimeException {
    public LogParsingException(String message, Throwable t) {
    	super(message, t);
    }
    public LogParsingException(String message) {
    	super(message);
    }
    public LogParsingException(Throwable t) {
    	super(t);
    }
}
