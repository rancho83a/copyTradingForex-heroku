package forex.copytradingforex.repository;

import forex.copytradingforex.model.entity.RoleEntity;
import forex.copytradingforex.model.entity.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Long> {
    RoleEntity findByRole(RoleEnum role);

}
