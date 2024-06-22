package exception.custom;

public class IllegalAdminAccess extends Exception {
    public IllegalAdminAccess() {
        super();
    }

    public IllegalAdminAccess(String message) {
        super(message);
    }

    public IllegalAdminAccess(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalAdminAccess(Throwable cause) {
        super(cause);
    }
}
