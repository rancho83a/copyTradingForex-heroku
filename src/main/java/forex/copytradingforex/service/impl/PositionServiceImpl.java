package forex.copytradingforex.service.impl;

import forex.copytradingforex.model.binding.PositionAddBindingModel;
import forex.copytradingforex.model.binding.PositionUpdateBindingModel;
import forex.copytradingforex.model.entity.*;
import forex.copytradingforex.model.entity.enums.RoleEnum;
import forex.copytradingforex.model.service.PositionAddServiceModel;
import forex.copytradingforex.model.service.PositionUpdateServiceModel;
import forex.copytradingforex.model.view.PositionDetailsView;
import forex.copytradingforex.model.view.PositionViewModel;
import forex.copytradingforex.repository.EconomicIndicatorRepository;
import forex.copytradingforex.repository.PositionRepository;
import forex.copytradingforex.repository.UserRepository;
import forex.copytradingforex.service.PositionService;
import forex.copytradingforex.web.exception.ObjectNotFoundException;
import forex.copytradingforex.web.exception.PositionNotFoundException;
import forex.copytradingforex.web.exception.UsernameNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PositionServiceImpl implements PositionService {
    public static final String PICTURE_URL = "https://res.cloudinary.com/drapmo8cx/image/upload/v1638132687/positions/bg-contacts_ykucb2.jpg";
    private final PositionRepository positionRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final EconomicIndicatorRepository economicIndicatorRepository;

    public PositionServiceImpl(PositionRepository positionRepository, ModelMapper modelMapper, UserRepository userRepository, EconomicIndicatorRepository economicIndicatorRepository) {
        this.positionRepository = positionRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.economicIndicatorRepository = economicIndicatorRepository;
    }

    @Override
    public List<PositionViewModel> getAllPositions() {
        return this.positionRepository
                .findAll()
                .stream()
                .map(this::mapToPositionViewModel)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public PositionDetailsView findById(Long id, String currentUser) {
        PositionEntity positionEntity = this.positionRepository.findById(id)
                .orElseThrow(() ->  new PositionNotFoundException(id));
        PositionDetailsView positionDetailsView = modelMapper.map(positionEntity, PositionDetailsView.class);


        positionDetailsView
                .setTrader(positionEntity.getTrader().getFullName())
                .setEconomicIndicator(positionEntity.getEconomicIndicator().getIndicator().getName())
                .setOpenTime(positionEntity.getOpenTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .setCloseTime(positionEntity.getCloseTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .setCanDeleteOrUpdate(isOwner(currentUser, positionEntity.getId()))
                .setPictureUrl(positionEntity.getPicture() != null ? positionEntity.getPicture().getUrl() : PICTURE_URL)
                .setYield( positionEntity.getYield().setScale(2,RoundingMode.FLOOR));

        return positionDetailsView;
    }

    @Transactional
    @Override
    public boolean isOwner(String username, Long id) {
        Optional<PositionEntity> positionOpt = positionRepository.findById(id);
        Optional<UserEntity> ownOpt = userRepository.findByUsername(username);

        if (positionOpt.isEmpty() || ownOpt.isEmpty()) {
            return false;
        }
        PositionEntity positionEntity = positionOpt.get();
        UserEntity owner = ownOpt.get();


        return isMaster(owner) || positionEntity.getTrader().getUsername().equals(username);
    }

    @Override
    public void deletePosition(Long id) {
        this.positionRepository.deleteById(id);
    }

    @Override
    public PositionAddServiceModel addPosition(PositionAddBindingModel positionAddBindModel, String username) {

        PositionAddServiceModel positionAddServiceModel = modelMapper.map(positionAddBindModel, PositionAddServiceModel.class);
        PositionEntity newPosition = modelMapper.map(positionAddServiceModel, PositionEntity.class);

        EconomicIndicatorEntity economicIndicator = this.economicIndicatorRepository
                .findById(positionAddBindModel.getEconomicIndicatorId()).orElseThrow(()->
                        new ObjectNotFoundException(positionAddBindModel.getEconomicIndicatorId()));
        newPosition.setEconomicIndicator(economicIndicator);

        UserEntity trader = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Trader with id " + username + " was not found"));

        newPosition.setYield(calculatePositionYield(trader.getCurrentCapital(), positionAddServiceModel.getFinancialResult()));


        trader.setCurrentCapital(calculateCurrentCapital(trader.getCurrentCapital(), positionAddServiceModel.getFinancialResult()));

        userRepository.save(trader);

        newPosition.setTrader(trader);


        PositionEntity savedPosition = positionRepository.save(newPosition);


        return modelMapper.map(savedPosition, PositionAddServiceModel.class);
    }


    @Override
    public PositionUpdateBindingModel mapDetailsViewToUpdateBindingModel(PositionDetailsView positionDetailsView) {

        PositionUpdateBindingModel updateBindingModel = modelMapper.map(positionDetailsView, PositionUpdateBindingModel.class);
        updateBindingModel.setOpenTime(LocalDateTime.parse(positionDetailsView.getOpenTime().replace(" ", "T")));
        updateBindingModel.setCloseTime(LocalDateTime.parse(positionDetailsView.getCloseTime().replace(" ", "T")));
        return updateBindingModel;
    }

    @Override
    public void updatePosition(PositionUpdateBindingModel updateBindingModel) {
        PositionUpdateServiceModel serviceModel = modelMapper.map(updateBindingModel, PositionUpdateServiceModel.class);

        PositionEntity positionEntity = positionRepository.findById(serviceModel.getId())
                .orElseThrow(() -> new PositionNotFoundException(serviceModel.getId()));
        positionEntity.setTrade(serviceModel.getTrade())
                .setOpenTime(serviceModel.getOpenTime())
                .setCloseTime(serviceModel.getCloseTime())
                .setOpenPrice(serviceModel.getOpenPrice())
                .setClosePrice(serviceModel.getClosePrice())
                .setVideoUrl(serviceModel.getVideoUrl());
        positionRepository.save(positionEntity);
    }

    @Override
    public PositionEntity getPictureById(Long id) {
        return this.positionRepository.findById(id)
                .orElseThrow(() -> new PositionNotFoundException(id));
    }

    private boolean isMaster(UserEntity user) {
        return user.getRoles()
                .stream()
                .map(RoleEntity::getRole)
                .anyMatch(r -> r == RoleEnum.MASTER);

    }

    private PositionViewModel mapToPositionViewModel(PositionEntity position) {
        PositionViewModel positionViewModel = modelMapper.map(position, PositionViewModel.class);
        positionViewModel.setTrader(position.getTrader().getFullName())
                .setEconomicIndicator(position.getEconomicIndicator().getIndicator().getName())
                .setYield(position.getYield().setScale(2,RoundingMode.FLOOR));

        positionViewModel.setPictureUrl(position.getPicture() != null ? position.getPicture().getUrl() : PICTURE_URL);

        return positionViewModel;
    }

    private BigDecimal calculatePositionYield(BigDecimal capital, BigDecimal financialResult) {

        if (capital.compareTo(BigDecimal.ZERO) < 1) {
            return BigDecimal.ZERO;
        }
        BigDecimal delta = capital.add(financialResult);

        if (delta.compareTo(BigDecimal.ZERO) < 1) {
            return BigDecimal.valueOf(-100);
        }
        return financialResult.multiply(BigDecimal.valueOf(100)).divide(capital, 6, RoundingMode.FLOOR);
    }


    private BigDecimal calculateCurrentCapital(BigDecimal currentCapital, BigDecimal financialResult) {
        BigDecimal delta = currentCapital.add(financialResult);

        if (delta.compareTo(BigDecimal.ZERO) < 1) {
            return BigDecimal.ZERO;
        }
        return delta;
    }
}
