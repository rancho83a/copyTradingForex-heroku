package forex.copytradingforex.web;


import forex.copytradingforex.config.TradingSettings;
import forex.copytradingforex.service.UserService;
import forex.copytradingforex.service.impl.CopyTradingForexUser;
import forex.copytradingforex.web.exception.NotEnoughCapitalException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/traders")
public class TradersController {

    private final UserService userService;

    public TradersController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/all")
    public String allTraders(Model model,
                             @AuthenticationPrincipal CopyTradingForexUser currentInvestor) {

        if(!model.containsAttribute("canNotJoin")) {
            model.addAttribute("canNotJoin", false);
        }

        if(userService.isJoinedToCopy(currentInvestor.getUserIdentifier())){
            model.addAttribute("isJoinedToCopy", true);
        }
        model.addAttribute("traders", this.userService.getAllTraders());
        return "traders";
    }

    @GetMapping("/{id}/copy")
    public String joinToCopy(
            @PathVariable Long id,
            //@AuthenticationPrincipal CopyTradingForexUser currentInvestor) {
            Principal principal) {

      //  if(!userService.canJoin(currentInvestor.getUserIdentifier())){
        if(!userService.canJoin(principal.getName())){
            throw new NotEnoughCapitalException(
                    String.format("You can not Join to trader! Your capital is less than %s USD."
                            ,TradingSettings.requiredInvestorCapitalToJoin));
        }

       // userService.joinToCopy(currentInvestor.getUserIdentifier(), id);
        userService.joinToCopy(principal.getName(), id);

        return "redirect:/users/profile";
    }

    @GetMapping("/{traderId}/revoke")
    public String revokeTrader(
            Model model,
            @PathVariable Long traderId,
            @AuthenticationPrincipal CopyTradingForexUser currentInvestor) {

        userService.revokeTrader(currentInvestor.getUserIdentifier(), traderId);

        return "redirect:/users/profile";
    }



}

