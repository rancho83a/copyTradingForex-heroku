package forex.copytradingforex.web;


import forex.copytradingforex.model.view.EconomicIndicatorViewModel;
import forex.copytradingforex.service.EconomicIndicatorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/economic-indicators")
public class EconomicIndicatorController {
    private final EconomicIndicatorService economicIndicatorService;

    public EconomicIndicatorController(EconomicIndicatorService economicIndicatorService) {
        this.economicIndicatorService = economicIndicatorService;
    }

    @GetMapping("/all")
    public String allIndicators(Model model) {
        List<EconomicIndicatorViewModel> allEconomicIndicators = economicIndicatorService.getAllEconomicIndicators();
        model.addAttribute("indicators", allEconomicIndicators);

        return "economic-indicators";
    }
}
