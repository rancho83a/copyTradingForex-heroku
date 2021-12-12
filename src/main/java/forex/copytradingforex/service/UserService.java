package forex.copytradingforex.service;

import forex.copytradingforex.model.entity.UserEntity;
import forex.copytradingforex.model.service.UserRegistrationServiceModel;
import forex.copytradingforex.model.view.FundHistoryViewModel;
import forex.copytradingforex.model.view.UserProfileViewModel;

import java.math.BigDecimal;
import java.util.List;


public interface UserService {
    boolean isUserNameFree(String username);

    void registerAndLoginUser(UserRegistrationServiceModel userRegistrationServiceModel);

    UserProfileViewModel findByUsername(String username);

    void depositAmount(BigDecimal amount, String username);

    boolean withdrawAmount(BigDecimal amount, String username);

    boolean isTraderCanTrade(String username);

    List<UserProfileViewModel> getAllTraders();

    void joinToCopy(String username, Long id);

    boolean isJoinedToCopy(String username);

    void revokeTrader(String investorUsername, Long traderId);

    void copyPositionToInvestors(String username, BigDecimal yield);

    BigDecimal[] remuneration(BigDecimal[] data);


    List<UserProfileViewModel> getInvestors(String username);

    UserEntity findUserByUsername(String username);

    boolean canJoin(String investorUsername);

    boolean isJoinedInvestorCanCopy(String investorUsername);

    List<UserEntity> getTradersWithInvestors();

    UserEntity save(UserEntity trader);

    List<UserEntity> getInvestorDueRemunerationFee();

    List<FundHistoryViewModel> getAllFundHistory(String username);

    boolean deleteProfile(String username);
}

