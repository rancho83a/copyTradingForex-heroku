package forex.copytradingforex.repository;

import forex.copytradingforex.model.entity.TradingRuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradingRuleEntityRepository extends JpaRepository<TradingRuleEntity, Long> {
}
