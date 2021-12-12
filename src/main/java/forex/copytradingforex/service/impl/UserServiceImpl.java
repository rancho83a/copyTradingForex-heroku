package forex.copytradingforex.service.impl;

import forex.copytradingforex.config.TradingSettings;
import forex.copytradingforex.model.entity.CommentEntity;
import forex.copytradingforex.model.entity.FundHistoryEntity;
import forex.copytradingforex.model.entity.RoleEntity;
import forex.copytradingforex.model.entity.UserEntity;
import forex.copytradingforex.model.entity.enums.RoleEnum;
import forex.copytradingforex.model.service.UserRegistrationServiceModel;
import forex.copytradingforex.model.service.UserProfileServiceModel;
import forex.copytradingforex.model.view.FundHistoryViewModel;
import forex.copytradingforex.model.view.UserProfileViewModel;
import forex.copytradingforex.repository.RoleRepository;
import forex.copytradingforex.repository.UserRepository;
import forex.copytradingforex.service.CommentService;
import forex.copytradingforex.service.UserService;
import forex.copytradingforex.web.exception.ObjectNotFoundException;
import forex.copytradingforex.web.exception.UsernameNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    public static final String IMAGE_URL = "https://res.cloudinary.com/drapmo8cx/image/upload/v1638274500/static/experience1_epfhyi.svg";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final CopyTradingForexUserDetailsServiceImpl copyTradingForexUserService;
    private final CommentService commentService;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository,
                           CopyTradingForexUserDetailsServiceImpl copyTradingForexUserService, CommentService commentService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.copyTradingForexUserService = copyTradingForexUserService;
        this.commentService = commentService;
        this.modelMapper = modelMapper;

    }

    @Override
    public boolean isUserNameFree(String username) {
        return userRepository.findByUsernameIgnoreCase(username).isEmpty();
    }

    @Override
    public void registerAndLoginUser(UserRegistrationServiceModel userRegistrationServiceModel) {

        BigDecimal currentCapital = BigDecimal.ZERO;
        if (userRegistrationServiceModel.getCurrentCapital() != null) {
            currentCapital = userRegistrationServiceModel.getCurrentCapital();
        }

        UserEntity newUser = new UserEntity()
                .setUsername(userRegistrationServiceModel.getUsername())
                .setPassword(passwordEncoder.encode(userRegistrationServiceModel.getPassword()))
                .setFullName(userRegistrationServiceModel.getFullName())
                .setEmail(userRegistrationServiceModel.getEmail())
                .setAge(userRegistrationServiceModel.getAge())
                .setExperience(userRegistrationServiceModel.getExperience())
                .setCurrentCapital(currentCapital)
                .setImageUrl(userRegistrationServiceModel.getImageUrl().isBlank() ? IMAGE_URL : userRegistrationServiceModel.getImageUrl())
                .setTotalDeposit(currentCapital)
                .setTotalWithdraw(BigDecimal.ZERO)
                .setBufferedAmount(BigDecimal.ZERO);

        RoleEntity role = roleRepository.getById(userRegistrationServiceModel.getRoleId());

        newUser.setRoles(List.of(role));

        newUser = userRepository.save(newUser);

        // this is spring representation of a user
        UserDetails principal = copyTradingForexUserService.loadUserByUsername(newUser.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                newUser.getPassword(),
                principal.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public UserProfileViewModel findByUsername(String username) {
        UserEntity userEntity = this.findUserByUsername(username);
        UserProfileServiceModel userProfileServiceModel = mapToUserProfileService(userEntity);
        return mapToUserProfileView(userProfileServiceModel);
    }

    private UserProfileViewModel mapToUserProfileView(UserProfileServiceModel userProfileServiceModel) {
        UserProfileViewModel userProfileViewModel = modelMapper.map(userProfileServiceModel, UserProfileViewModel.class);

        userProfileViewModel
                .setCommission(userProfileViewModel.getBufferedAmount()
                        .multiply(TradingSettings.traderRemuneration).setScale(2, RoundingMode.FLOOR));
        return userProfileViewModel;
    }

    private UserProfileServiceModel mapToUserProfileService(UserEntity userEntity) {
        UserProfileServiceModel userProfileServiceModel = modelMapper.map(userEntity, UserProfileServiceModel.class);
        userProfileServiceModel
                .setTotalYield(calculateTotalYield(userEntity.getCurrentCapital(), userEntity.getTotalWithdraw(), userEntity.getTotalDeposit())
                        .setScale(1, RoundingMode.FLOOR));

        if (userEntity.getTrader() != null) {
            userProfileServiceModel.setMyTrader(userEntity.getTrader().getFullName());
            userProfileServiceModel.setMyTraderId(userEntity.getTrader().getId());
        }
        return userProfileServiceModel;
    }

    private BigDecimal calculateTotalYield(BigDecimal currentCapital, BigDecimal totalWithdraw, BigDecimal totalDeposit) {
        if (totalDeposit.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return (currentCapital.add(totalWithdraw).subtract(totalDeposit))
                .multiply(BigDecimal.valueOf(100))
                .divide(totalDeposit, 6, RoundingMode.FLOOR);
    }

    @Override
    public void depositAmount(BigDecimal amount, String username) {
        UserEntity userEntity = this.findUserByUsername(username);

        userEntity.setCurrentCapital(userEntity.getCurrentCapital().add(amount));
        userEntity.setTotalDeposit(userEntity.getTotalDeposit().add(amount));

        userRepository.save(userEntity);
    }

    @Transactional
    @Override
    public boolean withdrawAmount(BigDecimal amount, String username) {
        UserEntity currentUser = this.findUserByUsername(username);

        // if have trader and bufferedAmount>0 - have to pay commisiion to trader
        if (currentUser.getTrader() != null && currentUser.getBufferedAmount().compareTo(BigDecimal.ZERO) > 0) {
            this.revokeTrader(currentUser.getUsername(), currentUser.getTrader().getId());
        }

        currentUser = this.findUserByUsername(username);
        BigDecimal currentCapital = currentUser.getCurrentCapital();

        if (currentCapital.compareTo(amount) < 0) {
            return false;
        }

        currentUser.setCurrentCapital(currentCapital.subtract(amount));
        currentUser.setTotalWithdraw(currentUser.getTotalWithdraw().add(amount));

        userRepository.save(currentUser);
        return true;
    }

    @Transactional
    @Override
    public boolean isTraderCanTrade(String username) {
        UserEntity trader = this.findUserByUsername(username);
        RoleEnum role = trader.getRoles().get(0).getRole();

        if (role.equals(RoleEnum.TRADER)) {
            return trader.getCurrentCapital().compareTo(TradingSettings.requiredTradingCapital) > -1;
        }
        return true;
    }

    @Override
    public List<UserProfileViewModel> getAllTraders() {

        return userRepository.findAllTradersWithCapitalGreaterThanEqual(TradingSettings.requiredTradingCapital)
                .stream()
                .map(this::mapToUserProfileService)
                .map(t -> modelMapper.map(t, UserProfileViewModel.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void joinToCopy(String investorUsername, Long id) {
        UserEntity investor = this.findUserByUsername(investorUsername);

        UserEntity trader = this.userRepository.findByIdByEntityGraph(id)
                .orElseThrow(() -> new ObjectNotFoundException(id));

        investor.setTrader(trader);
        userRepository.save(investor);

        trader.addInvestor(investor);
       // userRepository.save(trader);
    }

    @Override
    public boolean isJoinedToCopy(String username) {
        UserEntity investor = this.findUserByUsername(username);
        return investor.getTrader() != null;
    }

    @Transactional
    @Override
    public void revokeTrader(String investorUsername, Long traderId) {
        UserEntity investor = this.findUserByUsername(investorUsername);

        UserEntity trader = this.userRepository.findByIdByEntityGraph(traderId)
                .orElseThrow(() -> new ObjectNotFoundException(traderId));

        BigDecimal[] data = {investor.getBufferedAmount(), investor.getCurrentCapital(), trader.getCurrentCapital()};


        //check if BufferedAmout is >0 - then remuneration, else - just set it to 0
        if (investor.getBufferedAmount().compareTo(BigDecimal.ZERO) > 0) {
            data = this.remuneration(data);
            investor.setBufferedAmount(data[0]);
            investor.setCurrentCapital(data[1]);
            trader.setCurrentCapital(data[2]);
        } else {
            investor.setBufferedAmount(BigDecimal.ZERO);
        }

        trader.removeInvestor(investor.getId());
       // userRepository.save(trader);


        investor.setTrader(null);
        userRepository.save(investor);
    }

    @Transactional
    @Override
    public void copyPositionToInvestors(String username, BigDecimal yield) {
        UserEntity trader = this.findUserByUsername(username);

        List<UserEntity> investors = trader.getInvestors();
        if (!investors.isEmpty()) {
            investors
                    .forEach(investor -> {
                        if (investor.getCurrentCapital().compareTo(TradingSettings.requiredCopyCapital) > -1) {
                            BigDecimal resultInvestor = yield.multiply(investor.getCurrentCapital()).divide(BigDecimal.valueOf(100), 6, RoundingMode.FLOOR);
                            investor.setBufferedAmount(investor.getBufferedAmount().add(resultInvestor));
                            investor.setCurrentCapital(calculateCurrentCapital(investor.getCurrentCapital(), resultInvestor));
                        }
                        userRepository.save(investor);
                    });
        }
    }

    @Override
    public BigDecimal[] remuneration(BigDecimal[] data) {
        BigDecimal bufferedAmount = data[0];
        BigDecimal investorCapital = data[1];
        BigDecimal traderCapital = data[2];

        if (bufferedAmount.compareTo(BigDecimal.ZERO) < 1) {
            return data;
        }

        BigDecimal remuneration = bufferedAmount.multiply(TradingSettings.traderRemuneration);
        data[1] = investorCapital.subtract(remuneration);
        data[0] = (BigDecimal.ZERO);
        data[2] = traderCapital.add(remuneration);

        return data;
    }

    @Transactional
    @Override
    public List<UserProfileViewModel> getInvestors(String username) {
        UserEntity trader = this.findUserByUsername(username);
        return trader.getInvestors()
                .stream()
                .map(this::mapToUserProfileService)
                .map(this::mapToUserProfileView)
                .collect(Collectors.toList());
    }

    @Override
    public UserEntity findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public boolean canJoin(String investorUsername) {
        UserEntity investor = this.findUserByUsername(investorUsername);
        return investor.getCurrentCapital().compareTo(TradingSettings.requiredInvestorCapitalToJoin) > -1;
    }

    @Override
    public boolean isJoinedInvestorCanCopy(String investorUsername) {
        UserEntity investor = this.findUserByUsername(investorUsername);

        return investor.getCurrentCapital().compareTo(TradingSettings.requiredCopyCapital) > -1;
    }

    @Override
    public List<UserEntity> getTradersWithInvestors() {
        return this.userRepository.getTradersWithInvestors();
    }

    @Override
    public UserEntity save(UserEntity trader) {
        return this.userRepository.save(trader);
    }

    @Override
    public List<UserEntity> getInvestorDueRemunerationFee() {
        return this.userRepository.findAllByBufferedAmountGreaterThan(BigDecimal.ZERO);
    }

    @Transactional
    @Override
    public List<FundHistoryViewModel> getAllFundHistory(String username) {
       return  this.findUserByUsername(username).getFundHistoryRecords()
                .stream()
                .map(this::mapToFundHistoryViewModel)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteProfile(String username) {

        UserEntity user = this.findUserByUsername(username);
        if(user.getCurrentCapital().compareTo(BigDecimal.ZERO)>0){
            return false;
        }

        this.userRepository.deleteById(user.getId());
        return true;


    }

    private FundHistoryViewModel mapToFundHistoryViewModel(FundHistoryEntity fundHistoryEntity) {
        FundHistoryViewModel viewModel = modelMapper.map(fundHistoryEntity, FundHistoryViewModel.class);
        viewModel.setUser(fundHistoryEntity.getUser().getFullName())
                .setCreated(fundHistoryEntity.getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return viewModel;
    }

    private BigDecimal calculateCurrentCapital(BigDecimal currentCapital, BigDecimal financialResult) {
        BigDecimal delta = currentCapital.add(financialResult);

        if (delta.compareTo(BigDecimal.ZERO) < 1) {
            return BigDecimal.ZERO;
        }
        return delta;
    }
}
