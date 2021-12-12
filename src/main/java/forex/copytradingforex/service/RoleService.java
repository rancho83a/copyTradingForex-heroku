package forex.copytradingforex.service;

import forex.copytradingforex.model.view.RoleViewModel;

import java.util.List;

public interface RoleService {
    List<RoleViewModel> getTraderAndInvestorRoles();
}
