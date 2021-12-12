package forex.copytradingforex.model.entity;

import forex.copytradingforex.model.entity.enums.RoleEnum;

import javax.persistence.*;

@Entity
@Table(name="roles")
public class RoleEntity extends BaseEntity{


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleEnum role;

    public RoleEntity() {
    }

    public RoleEnum getRole() {
        return role;
    }

    public RoleEntity setRole(RoleEnum role) {
        this.role = role;
        return this;
    }
}
