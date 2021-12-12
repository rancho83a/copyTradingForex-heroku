package forex.copytradingforex.web;

import forex.copytradingforex.model.binding.NewCommentBindingModel;
import forex.copytradingforex.model.service.CommentServiceModel;
import forex.copytradingforex.model.validator.ApiError;
import forex.copytradingforex.model.view.CommentViewModel;
import forex.copytradingforex.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
public class CommentRestController {
    private final CommentService commentService;
    private final ModelMapper modelMapper;

    public CommentRestController(CommentService commentService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/api/{positionId}/comments")
    public ResponseEntity<List<CommentViewModel>> getComments(
            @PathVariable Long positionId,
            Principal principal
    ) {
        List<CommentViewModel> allComments = commentService.getAllComments(positionId);
        return ResponseEntity.ok(allComments);
    }

    @PostMapping("/api/{positionId}/comments")
    public ResponseEntity<CommentViewModel> postNewComment(
            @AuthenticationPrincipal UserDetails principal,
            @PathVariable Long positionId,
            @RequestBody @Valid NewCommentBindingModel newCommentBindingModel
    ) {
        CommentServiceModel commentServiceModel = modelMapper.map(newCommentBindingModel, CommentServiceModel.class)
                .setPositionId(positionId)
                .setOwner(principal.getUsername());
        CommentViewModel newCommentViewModel = commentService.createNewComment(commentServiceModel);

        URI locationOfNewComment =
                URI.create(String.format("/api/%s/comments/%s", positionId, newCommentViewModel.getId()));

        return ResponseEntity.created(locationOfNewComment).body(newCommentViewModel);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> onValidationFailure(MethodArgumentNotValidException e) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        e.getFieldErrors().forEach(fe -> apiError.addFieldWithError(fe.getField()));

        return ResponseEntity.badRequest().body(apiError);
    }
}
