package forex.copytradingforex.repository;

import forex.copytradingforex.model.entity.EconomicIndicatorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EconomicIndicatorRepository extends JpaRepository<EconomicIndicatorEntity, Long> {
}
