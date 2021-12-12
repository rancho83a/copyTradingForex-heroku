package forex.copytradingforex.service.impl;

import forex.copytradingforex.model.view.StatsView;
import forex.copytradingforex.service.StatsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class StatsServiceImpl implements StatsService {
    private int anonymRequest, authRequest;

    @Override
    public void onRequest() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && (authentication.getPrincipal() instanceof UserDetails)) {
            authRequest++;
        }else{
            anonymRequest++;
        }

    }

    @Override
    public StatsView getStats() {

        return new StatsView(anonymRequest,authRequest);
    }
}
