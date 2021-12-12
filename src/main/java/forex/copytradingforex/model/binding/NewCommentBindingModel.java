package forex.copytradingforex.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NewCommentBindingModel {
    @NotBlank
    @Size(min=10, message = "The minimum length of message must be 10 symbols")
    private String textContent;

    public String getTextContent() {
        return textContent;
    }

    public NewCommentBindingModel setTextContent(String textContent) {
        this.textContent = textContent;
        return this;
    }
}
