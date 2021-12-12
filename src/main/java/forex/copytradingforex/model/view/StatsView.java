package forex.copytradingforex.model.view;

public class StatsView {
    private final int anonymRequest;
    private final int authRequest;

    public StatsView(int anonymRequest, int authRequest) {
        this.anonymRequest = anonymRequest;
        this.authRequest = authRequest;
    }

    public int getTotalRequest() {
        return anonymRequest + authRequest;
    }

    public int getAnonymRequest() {
        return anonymRequest;
    }


    public int getAuthRequest() {
        return authRequest;
    }
}
