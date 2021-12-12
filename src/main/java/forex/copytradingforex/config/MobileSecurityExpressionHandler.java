package forex.copytradingforex.config;

import forex.copytradingforex.service.PositionService;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;

public class MobileSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

    private final PositionService positionService;

    public MobileSecurityExpressionHandler(PositionService positionService) {
        this.positionService = positionService;
    }


    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication, MethodInvocation invocation) {
        OwnerSecurityExpressionRoot root = new OwnerSecurityExpressionRoot(authentication);

        root.setPositionService(positionService);
        root.setPermissionEvaluator((getPermissionEvaluator()));
        root.setTrustResolver(new AuthenticationTrustResolverImpl());
        root.setRoleHierarchy(getRoleHierarchy());
        return root;
    }
}
