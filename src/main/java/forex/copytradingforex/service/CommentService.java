package forex.copytradingforex.service;

import forex.copytradingforex.model.entity.CommentEntity;
import forex.copytradingforex.model.service.CommentServiceModel;
import forex.copytradingforex.model.view.CommentViewModel;

import java.util.List;

public interface CommentService {
    List<CommentViewModel> getAllComments(Long positionId);

    CommentViewModel createNewComment(CommentServiceModel commentServiceModel);

}

