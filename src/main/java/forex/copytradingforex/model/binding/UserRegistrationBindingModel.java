package forex.copytradingforex.model.binding;


import forex.copytradingforex.model.validator.UniqueUserName;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class UserRegistrationBindingModel {

    //private Long id;

    @NotNull(message = "Role can not be empty")
    private Long roleId;


   // @NotBlank(message = "Username can not be empty")
    @Size(min = 2, max = 20, message = "Username must contains from 2 to 20 symbols")
    @UniqueUserName
    private String username;

   // @NotBlank
    @Size(min = 3, message = "Password must contains minimum 3 symbols")
    private String password;
    //@NotBlank
    @Size(min = 3, message = "Password must contains minimum 3 symbols")
    private String confirmPassword;

    @Email
    @NotBlank(message = "Email can not be empty")
    private String email;

   // @NotBlank
    @Size(min = 2, max = 50, message = "Fullname must contains from 2 to 50 symbols")
    private String fullName;


    private String imageUrl;

    @NotNull(message = "Age can not be empty")
    @Min(value = 18, message = "Age must be higher or equal to the 18 years")
    private Integer age;

    @NotNull(message = "Experience can not be empty")
    @PositiveOrZero(message = "Experience must be positive number or 0")
    private Integer experience;


    @PositiveOrZero(message = "Amount must be positive number or 0")
    private BigDecimal currentCapital;

    public String getUsername() {
        return username;
    }

    public UserRegistrationBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegistrationBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegistrationBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegistrationBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserRegistrationBindingModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public UserRegistrationBindingModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public UserRegistrationBindingModel setAge(Integer age) {
        this.age = age;
        return this;
    }

    public Integer getExperience() {
        return experience;
    }

    public UserRegistrationBindingModel setExperience(Integer experience) {
        this.experience = experience;
        return this;
    }

    public BigDecimal getCurrentCapital() {
        return currentCapital;
    }

    public UserRegistrationBindingModel setCurrentCapital(BigDecimal currentCapital) {
        this.currentCapital = currentCapital;
        return this;
    }

    public Long getRoleId() {
        return roleId;
    }

    public UserRegistrationBindingModel setRoleId(Long roleId) {
        this.roleId = roleId;
        return this;
    }
}
