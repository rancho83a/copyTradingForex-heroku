package forex.copytradingforex.web.exception;

public class UsernameNotFoundException extends RuntimeException {
    private final String username;

    public UsernameNotFoundException(String username ) {
        super();
        this.username=username;
    }

    public String getUsername() {
        return username;
    }
}


