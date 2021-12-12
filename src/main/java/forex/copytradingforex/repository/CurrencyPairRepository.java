package forex.copytradingforex.repository;

import forex.copytradingforex.model.entity.CurrencyPairEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyPairRepository extends JpaRepository<CurrencyPairEntity, Long> {
}
