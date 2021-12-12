package forex.copytradingforex.repository;

import forex.copytradingforex.model.entity.PositionEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository<PositionEntity, Long> {

    // Fix the N+1 problem for FETCH=LAZY -> work without @Transactional Annotation for method
    @Query("SELECT p FROM PositionEntity p LEFT JOIN FETCH p.comments comment WHERE p.id = :paramId")
    Optional<PositionEntity> findByIdByFetch(@Param("paramId") Long id);

    @EntityGraph(value = "position-comments-trader")
    @Query("SELECT p FROM PositionEntity p WHERE p.id = :paramId")
    Optional<PositionEntity> findByIdByEntityGraph(@Param("paramId") Long id);


}
