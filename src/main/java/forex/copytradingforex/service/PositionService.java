package forex.copytradingforex.service;

import forex.copytradingforex.model.binding.PositionAddBindingModel;
import forex.copytradingforex.model.binding.PositionUpdateBindingModel;
import forex.copytradingforex.model.entity.PositionEntity;
import forex.copytradingforex.model.service.PositionAddServiceModel;
import forex.copytradingforex.model.service.PositionUpdateServiceModel;
import forex.copytradingforex.model.view.PositionDetailsView;
import forex.copytradingforex.model.view.PositionViewModel;

import java.math.BigDecimal;
import java.util.List;

public interface PositionService {
    List<PositionViewModel> getAllPositions();

    PositionDetailsView findById(Long id, String userIdentifier);

    boolean isOwner(String username, Long id);

    void deletePosition(Long id);

    PositionAddServiceModel addPosition(PositionAddBindingModel positionAddBindModel, String ownerId);

    PositionUpdateBindingModel mapDetailsViewToUpdateBindingModel(PositionDetailsView positionDetailsView);

    void updatePosition(PositionUpdateBindingModel updateBindingModel);

    PositionEntity getPictureById(Long id);
}
