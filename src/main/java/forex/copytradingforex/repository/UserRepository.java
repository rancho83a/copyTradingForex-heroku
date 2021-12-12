package forex.copytradingforex.repository;

import forex.copytradingforex.model.entity.PositionEntity;
import forex.copytradingforex.model.entity.RoleEntity;
import forex.copytradingforex.model.entity.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);


    @EntityGraph(value = "trader-investors")
    @Query("SELECT u FROM UserEntity u WHERE u.id = :paramId")
    Optional<UserEntity> findByIdByEntityGraph(@Param("paramId") Long id);



    Optional<UserEntity> findByUsernameIgnoreCase(String username);

    @Query(value = "SELECT * FROM users LEFT JOIN users_roles as ur " +
            " on users.id = ur.user_entity_id" +
            " LEFT JOIN roles as r  on r.id = ur.roles_id " +
            " WHERE r.role= 'TRADER' AND users.current_capital >= ?1 ", nativeQuery = true)
    List<UserEntity> findAllTradersWithCapitalGreaterThanEqual(BigDecimal capital);

    @Query("SELECT u FROM UserEntity  u WHERE size(u.investors)>0 ")
    List<UserEntity> getTradersWithInvestors();

    List<UserEntity> findAllByBufferedAmountGreaterThan(BigDecimal bufferedAmount);

}

