package forex.copytradingforex.model.service;


import java.math.BigDecimal;

public class UserRegistrationServiceModel {
    private Long id;

    private Long roleId;

    private String username;


    private String password;


    private String email;


    private String fullName;


    private String imageUrl;


    private Integer age;

    private Integer experience;


    private BigDecimal currentCapital;

    public Long getId() {
        return id;
    }

    public UserRegistrationServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public BigDecimal getCurrentCapital() {
        return currentCapital;
    }

    public UserRegistrationServiceModel setCurrentCapital(BigDecimal currentCapital) {
        this.currentCapital = currentCapital;
        return this;
    }

    public Long getRoleId() {
        return roleId;
    }

    public UserRegistrationServiceModel setRoleId(Long roleId) {
        this.roleId = roleId;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserRegistrationServiceModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getUsername() {
       return  username !=null ? username.trim() : null ;
    }

    public UserRegistrationServiceModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegistrationServiceModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegistrationServiceModel setEmail(String email) {
        this.email = email;
        return this;
    }



    public String getImageUrl() {
        return imageUrl;
    }

    public UserRegistrationServiceModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public UserRegistrationServiceModel setAge(Integer age) {
        this.age = age;
        return this;
    }

    public Integer getExperience() {
        return experience;
    }

    public UserRegistrationServiceModel setExperience(Integer experience) {
        this.experience = experience;
        return this;
    }


}
