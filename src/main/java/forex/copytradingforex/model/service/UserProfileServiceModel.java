package forex.copytradingforex.model.service;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class UserProfileServiceModel {
    Long id;
    String username;
    String fullName;
    String imageUrl;
    String email;
    Integer age;
    Integer experience;
    BigDecimal currentCapital;
    BigDecimal totalYield;
    BigDecimal totalDeposit;
    BigDecimal totalWithdraw;
    BigDecimal withdrawAmount;
    String myTrader;
    Long myTraderId;
    BigDecimal bufferedAmount;

    public BigDecimal getBufferedAmount() {
        return bufferedAmount;
    }

    public UserProfileServiceModel setBufferedAmount(BigDecimal bufferedAmount) {
        this.bufferedAmount = bufferedAmount;
        return this;
    }

    public Long getMyTraderId() {
        return myTraderId;
    }

    public UserProfileServiceModel setMyTraderId(Long myTraderId) {
        this.myTraderId = myTraderId;
        return this;
    }

    public String getMyTrader() {
        return myTrader;
    }

    public UserProfileServiceModel setMyTrader(String myTrader) {
        this.myTrader = myTrader;
        return this;
    }

    public BigDecimal getWithdrawAmount() {
        return withdrawAmount;
    }

    public UserProfileServiceModel setWithdrawAmount(BigDecimal withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserProfileServiceModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public UserProfileServiceModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserProfileServiceModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public UserProfileServiceModel setAge(Integer age) {
        this.age = age;
        return this;
    }

    public Integer getExperience() {
        return experience;
    }

    public UserProfileServiceModel setExperience(Integer experience) {
        this.experience = experience;
        return this;
    }

    public BigDecimal getCurrentCapital() {
        return currentCapital;
    }

    public UserProfileServiceModel setCurrentCapital(BigDecimal currentCapital) {
        this.currentCapital = currentCapital;
        return this;
    }

    public BigDecimal getTotalYield() {
        return totalYield;
    }

    public UserProfileServiceModel setTotalYield(BigDecimal totalYield) {
        this.totalYield = totalYield;
        return this;
    }

    public Long getId() {
        return id;
    }

    public UserProfileServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserProfileServiceModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public BigDecimal getTotalDeposit() {
        return totalDeposit;
    }

    public UserProfileServiceModel setTotalDeposit(BigDecimal totalDeposit) {
        this.totalDeposit = totalDeposit;
        return this;
    }

    public BigDecimal getTotalWithdraw() {
        return totalWithdraw;
    }

    public UserProfileServiceModel setTotalWithdraw(BigDecimal totalWithdraw) {
        this.totalWithdraw = totalWithdraw;
        return this;
    }
    //    public BigDecimal getInitialCapital() {
//        return initialCapital;
//    }
//
//    public UserProfileServiceModel setInitialCapital(BigDecimal initialCapital) {
//        this.initialCapital = initialCapital;
//        return this;
//    }
}
