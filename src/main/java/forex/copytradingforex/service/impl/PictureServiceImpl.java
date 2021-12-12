package forex.copytradingforex.service.impl;

import forex.copytradingforex.model.entity.PictureEntity;
import forex.copytradingforex.model.entity.PositionEntity;
import forex.copytradingforex.model.entity.UserEntity;
import forex.copytradingforex.model.service.PictureAddServiceModel;
import forex.copytradingforex.repository.PictureRepository;
import forex.copytradingforex.service.CloudinaryService;
import forex.copytradingforex.service.PictureService;
import forex.copytradingforex.service.PositionService;
import forex.copytradingforex.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final PositionService positionService;
    private final CloudinaryService cloudinaryService;

    public PictureServiceImpl(PictureRepository pictureRepository, ModelMapper modelMapper, UserService userService, PositionService positionService, CloudinaryService cloudinaryService) {
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;

        this.positionService = positionService;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public void savePicture(PictureAddServiceModel pictureAddServiceModel) {
        UserEntity trader = userService.findUserByUsername(pictureAddServiceModel.getTraderUsername());

        PositionEntity position = positionService.getPictureById(pictureAddServiceModel.getPositionId());
        if (position.getPicture() != null) {
            if (cloudinaryService.delete(position.getPicture().getPublicId())) {
                pictureRepository.delete(position.getPicture());
                position.setPicture(null);
            }
        }

        PictureEntity pictureEntity = new PictureEntity()
                .setTrader(trader).setPosition(position)
                .setPublicId(pictureAddServiceModel.getPublicId())
                .setUrl(pictureAddServiceModel.getUrl())
                .setTitle(pictureAddServiceModel.getTitle());

        this.pictureRepository.save(pictureEntity);
    }
}
