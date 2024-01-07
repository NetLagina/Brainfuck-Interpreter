package exception;

import java.io.Serial;

public class PointerOutOfBoundsException extends Exception {

    @Serial
    private static final long serialVersionUID = 5091543542607190696L;

    public PointerOutOfBoundsException(String arg0) {
        super(arg0);
    }

}
