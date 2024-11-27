package src.zoologico;

public class AnimalException extends Exception {

    public AnimalException(String message, Throwable cause) {
        super(message, cause);
    }

    public AnimalException(Throwable cause) {
        super(cause);
    }
}
