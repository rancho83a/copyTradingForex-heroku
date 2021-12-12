package forex.copytradingforex.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NamedEntityGraph(
        name = "trader-investors",
        attributeNodes = {
                @NamedAttributeNode("investors")
        }
)
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String fullName;

    private String imageUrl;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private Integer experience;

    @Column(columnDefinition = "Decimal(19,2) default '0.00'")
    private BigDecimal currentCapital;

    @Column(columnDefinition = "Decimal(19,2) default '0.00'")
    private BigDecimal totalDeposit;

    @Column(columnDefinition = "Decimal(19,2) default '0.00'")
    private BigDecimal totalWithdraw;

    @Column(columnDefinition = "Decimal(19,2) default '0.00'")
    private BigDecimal bufferedAmount;

    @ManyToMany(fetch = FetchType.LAZY)
    //@ManyToMany(fetch = FetchType.EAGER)
    private List<RoleEntity> roles = new ArrayList<>();

    @ManyToOne
    private UserEntity trader;

    @OneToMany(mappedBy = "trader")
    private List<UserEntity> investors = new ArrayList<>();

    @OneToMany(mappedBy = "trader")
    private List<PositionEntity> positions;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<FundHistoryEntity> fundHistoryRecords;
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<CommentEntity> comments;

    public List<CommentEntity> getComments() {
        return comments;
    }

    public UserEntity setComments(List<CommentEntity> comments) {
        this.comments = comments;
        return this;
    }

    public List<FundHistoryEntity> getFundHistoryRecords() {
        return fundHistoryRecords;
    }

    public UserEntity setFundHistoryRecords(List<FundHistoryEntity> fundHistoryRecords) {
        this.fundHistoryRecords = fundHistoryRecords;
        return this;
    }

    public UserEntity getTrader() {
        return trader;
    }

    public UserEntity setTrader(UserEntity trader) {
        this.trader = trader;
        return this;
    }

    public List<UserEntity> getInvestors() {
        return investors;
    }

    public UserEntity setInvestors(List<UserEntity> investors) {
        this.investors = investors;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String nickName) {
        this.email = nickName;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserEntity setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public UserEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public UserEntity setAge(Integer age) {
        this.age = age;
        return this;
    }

    public Integer getExperience() {
        return experience;
    }

    public UserEntity setExperience(Integer experience) {
        this.experience = experience;
        return this;
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public UserEntity setRoles(List<RoleEntity> roles) {
        this.roles = roles;
        return this;
    }


    public BigDecimal getCurrentCapital() {
        return currentCapital;
    }

    public UserEntity setCurrentCapital(BigDecimal currentCapital) {
        this.currentCapital = currentCapital;
        return this;
    }

    public BigDecimal getTotalDeposit() {
        return totalDeposit;
    }

    public UserEntity setTotalDeposit(BigDecimal totalDeposit) {
        this.totalDeposit = totalDeposit;
        return this;
    }

    public BigDecimal getTotalWithdraw() {
        return totalWithdraw;
    }

    public UserEntity setTotalWithdraw(BigDecimal totalWithdraw) {
        this.totalWithdraw = totalWithdraw;
        return this;
    }

    public BigDecimal getBufferedAmount() {
        return bufferedAmount;
    }

    public UserEntity setBufferedAmount(BigDecimal bufferedAmount) {
        this.bufferedAmount = bufferedAmount;
        return this;
    }

    public List<PositionEntity> getPositions() {
        return positions;
    }

    public UserEntity setPositions(List<PositionEntity> positions) {
        this.positions = positions;
        return this;
    }

    public void addInvestor(UserEntity investor) {
        this.investors.add(investor);
    }

    public void removeInvestor(Long investorId) {
        this.investors.removeIf(i -> i.getId().equals(investorId));
    }
}
