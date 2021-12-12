package forex.copytradingforex.repository;

import forex.copytradingforex.model.entity.FundHistoryEntity;
import forex.copytradingforex.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FundHistoryRepository extends JpaRepository<FundHistoryEntity, Long> {

}
