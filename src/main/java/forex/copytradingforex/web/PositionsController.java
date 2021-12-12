package forex.copytradingforex.web;

import forex.copytradingforex.config.TradingSettings;
import forex.copytradingforex.events.PositionCreatedEvent;
import forex.copytradingforex.model.binding.PositionAddBindingModel;
import forex.copytradingforex.model.binding.PositionUpdateBindingModel;
import forex.copytradingforex.model.service.PositionAddServiceModel;
import forex.copytradingforex.model.view.PositionDetailsView;
import forex.copytradingforex.service.EconomicIndicatorService;
import forex.copytradingforex.service.PositionService;
import forex.copytradingforex.service.UserService;
import forex.copytradingforex.service.impl.CopyTradingForexUser;
import forex.copytradingforex.web.exception.NotEnoughCapitalException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/positions")
public class PositionsController {
    private final PositionService positionService;
    private final EconomicIndicatorService economicIndicatorService;
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;


    public PositionsController(PositionService positionService, EconomicIndicatorService economicIndicatorService, UserService userService, ApplicationEventPublisher eventPublisher) {
        this.positionService = positionService;
        this.economicIndicatorService = economicIndicatorService;
        this.userService = userService;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/all")
    public String allPositions(Model model) {
        model.addAttribute("positions", this.positionService.getAllPositions());
        return "positions";
    }


    //DETAILS
    @GetMapping("/{id}/details")
    public String showPosition(@PathVariable Long id, Model model,
                               // Principal principal){
                               @AuthenticationPrincipal CopyTradingForexUser currentUser) {
        model.addAttribute("position", this.positionService.findById(id, currentUser.getUserIdentifier()));
        if (!model.containsAttribute("noUploadedPicture")) {
            model.addAttribute("noUploadedPicture", false);
        }
        return "position-details";
    }

    //DELETE
    @PreAuthorize("isOwner(#id)")
    //@PreAuthorize("@positionServiceImpl.isOwner(#principal.name, #id)")
    @DeleteMapping("/{id}")
    public String deletePosition(@PathVariable Long id) {

        this.positionService.deletePosition(id);
        return "redirect:/positions/all";
    }

    @GetMapping("/add")
    public String getAddPositionPage(Model model, Principal principal
    ) {
        if (!userService.isTraderCanTrade(principal.getName())) {
            throw new NotEnoughCapitalException(String.format("You can not add position! Your capital is less than %s USD."
                    , TradingSettings.requiredTradingCapital));
        }

        if (!model.containsAttribute("positionAddBindModel")) {
            model
                    .addAttribute("positionAddBindModel", new PositionAddBindingModel())
                    .addAttribute("economicIndicators", economicIndicatorService.getAllEconomicIndicators());

        }
        return "position-add";
    }

    @PostMapping("/add")
    public String addPosition(@Valid PositionAddBindingModel positionAddBindModel,
                              BindingResult bindingResult, RedirectAttributes redirectAttributes,
                              @AuthenticationPrincipal UserDetails trader) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("positionAddBindModel", positionAddBindModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.positionAddBindModel",
                            bindingResult)
                    .addFlashAttribute("economicIndicators", economicIndicatorService.getAllEconomicIndicators());
            return "redirect:/positions/add";
        }
        PositionAddServiceModel savedPositionAddServiceModel = positionService.addPosition(positionAddBindModel,
                trader.getUsername());

        PositionCreatedEvent event = new PositionCreatedEvent(this, trader.getUsername(),
                savedPositionAddServiceModel.getYield());
        eventPublisher.publishEvent(event);

        return "redirect:/positions/" + savedPositionAddServiceModel.getId() + "/details";
    }

    //UPDATE
    @PreAuthorize("isOwner(#id)")
    @GetMapping("/{id}/update")
    public String editPosition(@PathVariable Long id, Model model,
                               @AuthenticationPrincipal CopyTradingForexUser currentUser) {

        PositionDetailsView positionDetailsView = positionService.findById(id, currentUser.getUserIdentifier());
        PositionUpdateBindingModel updateBindingModel = positionService.mapDetailsViewToUpdateBindingModel(positionDetailsView);

        model.addAttribute("updateBindingModel", updateBindingModel);
        return "position-update";
    }

    @PatchMapping("/{id}/update")
    public String updatePosition(@PathVariable Long id,
                                 @Valid PositionUpdateBindingModel updateBindingModel,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {

            //flashAttribute -> ocelyavat pri POST-redirect->GET
            redirectAttributes.addFlashAttribute("updateBindingModel", updateBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.updateBindingModel", bindingResult);

            return "redirect:/positions/" + id + "/update/errors";
        }

        positionService.updatePosition(updateBindingModel);
        return "redirect:/positions/" + id + "/details";
    }

    @GetMapping("/{id}/update/errors")
    public String updatePositionErrors(@PathVariable Long id) {
        return "position-update";
    }


}
