package forex.copytradingforex.service.impl;

import forex.copytradingforex.model.entity.FundHistoryEntity;
import forex.copytradingforex.model.view.FundHistoryViewModel;
import forex.copytradingforex.repository.FundHistoryRepository;
import forex.copytradingforex.service.FundHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FundHistoryServiceImpl implements FundHistoryService {
    private final FundHistoryRepository fundHistoryRepository;

    public FundHistoryServiceImpl(FundHistoryRepository fundHistoryRepository) {
        this.fundHistoryRepository = fundHistoryRepository;
    }

    @Override
    public void save(FundHistoryEntity fundHistory) {
        this.fundHistoryRepository.save(fundHistory);
    }


}
