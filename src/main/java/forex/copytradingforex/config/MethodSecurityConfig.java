package forex.copytradingforex.config;

import forex.copytradingforex.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    @Autowired
    private MobileSecurityExpressionHandler MobileSecurityExpressionHandler;

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return MobileSecurityExpressionHandler;
    }

    @Bean
    public MobileSecurityExpressionHandler createExpressionHandler(PositionService positionService) {
        return new MobileSecurityExpressionHandler(positionService);
    }

}
