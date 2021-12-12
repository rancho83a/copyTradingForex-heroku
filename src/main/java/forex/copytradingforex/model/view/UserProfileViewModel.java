package forex.copytradingforex.model.view;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class UserProfileViewModel {

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
    String myTrader;
    Long myTraderId;
    BigDecimal bufferedAmount;
    BigDecimal commission;

    public BigDecimal getBufferedAmount() {
        return bufferedAmount;
    }

    public UserProfileViewModel setBufferedAmount(BigDecimal bufferedAmount) {
        this.bufferedAmount = bufferedAmount;
        return this;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public UserProfileViewModel setCommission(BigDecimal commission) {
        this.commission = commission;
        return this;
    }

    public Long getMyTraderId() {
        return myTraderId;
    }

    public UserProfileViewModel setMyTraderId(Long myTraderId) {
        this.myTraderId = myTraderId;
        return this;
    }

    public String getMyTrader() {
        return myTrader;
    }

    public UserProfileViewModel setMyTrader(String myTrader) {
        this.myTrader = myTrader;
        return this;
    }

    @NotNull(message = "Please, fill the amount field")
    @Positive(message = "You can withdraw positive amount only")
    BigDecimal withdrawAmount;

    public BigDecimal getWithdrawAmount() {
        return withdrawAmount;
    }

    public UserProfileViewModel setWithdrawAmount(BigDecimal withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
        return this;
    }

    public Long getId() {
        return id;
    }

    public UserProfileViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public BigDecimal getTotalDeposit() {
        return totalDeposit;
    }

    public UserProfileViewModel setTotalDeposit(BigDecimal totalDeposit) {
        this.totalDeposit = totalDeposit;
        return this;
    }

    public BigDecimal getTotalWithdraw() {
        return totalWithdraw;
    }

    public UserProfileViewModel setTotalWithdraw(BigDecimal totalWithdraw) {
        this.totalWithdraw = totalWithdraw;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserProfileViewModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserProfileViewModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public UserProfileViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserProfileViewModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public UserProfileViewModel setAge(Integer age) {
        this.age = age;
        return this;
    }

    public Integer getExperience() {
        return experience;
    }

    public UserProfileViewModel setExperience(Integer experience) {
        this.experience = experience;
        return this;
    }

    public BigDecimal getCurrentCapital() {
        return currentCapital;
    }

    public UserProfileViewModel setCurrentCapital(BigDecimal currentCapital) {
        this.currentCapital = currentCapital;
        return this;
    }

    public BigDecimal getTotalYield() {
        return totalYield;
    }

    public UserProfileViewModel setTotalYield(BigDecimal totalYield) {
        this.totalYield = totalYield;
        return this;
    }
}