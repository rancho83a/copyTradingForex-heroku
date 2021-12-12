package forex.copytradingforex.web;

import forex.copytradingforex.model.view.UserProfileViewModel;
import forex.copytradingforex.service.FundHistoryService;
import forex.copytradingforex.service.UserService;
import forex.copytradingforex.service.impl.CopyTradingForexUser;
import forex.copytradingforex.web.exception.NotEnoughCapitalException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;


@Controller
public class UserLoginController {
    private final UserService userService;
    private final FundHistoryService fundHistoryService;

    public UserLoginController(UserService userService, FundHistoryService fundHistoryService) {
        this.userService = userService;
        this.fundHistoryService = fundHistoryService;
    }

    @Transactional //fetch=Lazy => za proxy oject  pri vikaneto na RoleEntity rtqbva context, bez transactional go nqma
    @GetMapping("/users/login")
    public String login() {
        return "login";
    }

    @PostMapping("/users/login-error")
    public String failedLogin(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                    String username,
            RedirectAttributes redirectAttributes
    ) {
        redirectAttributes.addFlashAttribute("bad_credentials", true);
        redirectAttributes.addFlashAttribute("username", username);

        return "redirect:/users/login";
    }


    @GetMapping("/users/profile")
    public String profile(Model model,
                          @AuthenticationPrincipal CopyTradingForexUser currentUser) {

        UserProfileViewModel byUsername = userService.findByUsername(currentUser.getUserIdentifier());
        model.addAttribute("userProfile",
                userService.findByUsername(currentUser.getUserIdentifier()));
        if (!model.containsAttribute("wrongAmount")) {
            model.addAttribute("wrongAmount", false);
        }
        if (!model.containsAttribute("traderCanNotTrade")) {
            model.addAttribute("traderCanNotTrade", false);
        }

        if (userService.isJoinedToCopy(currentUser.getUserIdentifier())) {
            model.addAttribute("isJoinedToCopy", true);
        }
        if (!userService.getInvestors(currentUser.getUserIdentifier()).isEmpty()) {
            model.addAttribute("haveInvestors", true);
            model.addAttribute("investors", userService.getInvestors(currentUser.getUserIdentifier()));
        }

        if (userService.isJoinedToCopy(currentUser.getUserIdentifier())) {
            if (!userService.isJoinedInvestorCanCopy(currentUser.getUserIdentifier())) {
                model.addAttribute("joinedInvestorCanNotCopy", true);
            }
        }

        if (!userService.isTraderCanTrade(currentUser.getUserIdentifier())) {
            model.addAttribute("traderCanNotTrade", true);
        }
        if(!model.containsAttribute("canDeleteProfile")){
            model.addAttribute("canDeleteProfile", true);
        }

        model.addAttribute("fundHistory", this.userService.getAllFundHistory(currentUser.getUsername()));

        return "profile";
    }


    @PostMapping("/users/deposit")
    public String depositAmount(@RequestParam("depositAmount") String amount,
                                RedirectAttributes redirectAttributes,
                                @AuthenticationPrincipal UserDetails currentUser
    ) {

        if (amount.isBlank()) {
            redirectAttributes.addFlashAttribute("wrongAmount", true);
            return "redirect:/users/profile";
        }
        userService.depositAmount(new BigDecimal(amount), currentUser.getUsername());
        return "redirect:/users/profile";
    }

    @PostMapping("/users/withdraw")
    public String withdrawAmount(

            @Valid UserProfileViewModel userProfile,
            BindingResult bindingResult, RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal CopyTradingForexUser user) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("wrongAmount", true);
            //redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userProfile", bindingResult);
            return "redirect:/users/profile";
        }

        boolean isWithdraw = userService.withdrawAmount(userProfile.getWithdrawAmount(), user.getUserIdentifier());

        if (!isWithdraw) {
            throw new NotEnoughCapitalException("The withdraw is denied! Your capital is less than you want to withdraw!");
        }
        return "redirect:/users/profile";
    }

    //@PreAuthorize("isOwner(#id)")
    //@PreAuthorize("@positionServiceImpl.isOwner(#principal.name, #id)")
    @DeleteMapping("users/delete")
    public String deleteProfile(Principal principal, RedirectAttributes redirectAttributes) {

        if(!this.userService.deleteProfile(principal.getName())){
            redirectAttributes.addFlashAttribute("canDeleteProfile", false);
            return "redirect:/users/profile";
        }

        return "redirect:/";
    }


}
