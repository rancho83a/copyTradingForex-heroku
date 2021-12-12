package forex.copytradingforex.service.impl;

import forex.copytradingforex.model.entity.CommentEntity;
import forex.copytradingforex.model.entity.PositionEntity;
import forex.copytradingforex.model.entity.UserEntity;
import forex.copytradingforex.model.service.CommentServiceModel;
import forex.copytradingforex.model.view.CommentViewModel;
import forex.copytradingforex.repository.CommentRepository;
import forex.copytradingforex.repository.PositionRepository;
import forex.copytradingforex.repository.UserRepository;
import forex.copytradingforex.service.CommentService;
import forex.copytradingforex.web.exception.ObjectNotFoundException;
import forex.copytradingforex.web.exception.PositionNotFoundException;
import forex.copytradingforex.web.exception.UsernameNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PositionRepository positionRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PositionRepository positionRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.positionRepository = positionRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public List<CommentViewModel> getAllComments(Long positionId) {
        PositionEntity positionEntity = positionRepository.

                //There are 2 options to fix N+1 problem with fetch.LAZY and @Transactional

                //With special FETCH Query
                //findByIdByFetch(positionId)

                //Alternative for previous above method
                        findByIdByEntityGraph(positionId)
                .orElseThrow(
                        () -> new PositionNotFoundException(positionId)
                );

        return positionEntity.getComments()
                .stream().map(this::mapToCommentViewModel)
                .collect(Collectors.toList());
    }

    private CommentViewModel mapToCommentViewModel(CommentEntity commentEntity) {
        return modelMapper.map(commentEntity, CommentViewModel.class)
                .setCreated(commentEntity.getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .setCanApprove(true)
                .setOwner(commentEntity.getAuthor().getFullName())
                .setCanDelete(true);

    }

    @Override
    public CommentViewModel createNewComment(CommentServiceModel commentServiceModel) {

        //- если в тесте про PostComment поставить @WithMockUser("testUser2") - t.e. users которого нет в репо, то тест
        //должен провалиться, если же он не проваливается - то поставить ето ограничение (у меня проваливается)
        //Objects.requireNonNull(commentServiceModel.getOwner());

        PositionEntity position = positionRepository.findById(commentServiceModel.getPositionId()).orElseThrow(
                () -> new PositionNotFoundException(commentServiceModel.getPositionId()));

        UserEntity author = userRepository.findByUsername(commentServiceModel.getOwner()).orElseThrow(
                () -> new UsernameNotFoundException(commentServiceModel.getOwner()));

        CommentEntity newComment = modelMapper.map(commentServiceModel, CommentEntity.class)
                .setApproved(false)
                .setTextContent(commentServiceModel.getTextContent())
                .setCreated(LocalDateTime.now())
                .setPosition(position)
                .setAuthor(author);

        CommentEntity savedComment = commentRepository.save(newComment);

        return mapToCommentViewModel(savedComment);
    }
}
