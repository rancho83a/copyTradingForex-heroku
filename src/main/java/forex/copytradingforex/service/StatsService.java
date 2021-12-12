package forex.copytradingforex.service;

import forex.copytradingforex.model.view.StatsView;

public interface StatsService {
    void onRequest();
    StatsView getStats();

}
