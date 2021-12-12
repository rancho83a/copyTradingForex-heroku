package forex.copytradingforex.web;


import forex.copytradingforex.model.binding.UserRegistrationBindingModel;
import forex.copytradingforex.model.service.UserRegistrationServiceModel;
import forex.copytradingforex.model.view.RoleViewModel;
import forex.copytradingforex.service.RoleService;
import forex.copytradingforex.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserRegistrationController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final RoleService roleService;

    public UserRegistrationController(UserService userService, ModelMapper modelMapper, RoleService roleService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
    }

    @ModelAttribute("userModel")
    public UserRegistrationBindingModel userModel() {
        return new UserRegistrationBindingModel();
    }

    @GetMapping("/users/register")
    public String registerUser(Model model) {
        if(!model.containsAttribute("isPasswordConfirm")){
            model.addAttribute("isPasswordConfirm", true);
        }

        model.addAttribute("traderInvestorRoles", roleService.getTraderAndInvestorRoles());
        return "register";
    }


    @PostMapping("/users/register")
    public String register(

            @Valid UserRegistrationBindingModel userModel,  // throw 404 error
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        boolean isPasswordConfirm = userModel.getPassword().equals(userModel.getConfirmPassword());

        if (bindingResult.hasErrors() || !isPasswordConfirm) {
            redirectAttributes.addFlashAttribute("userModel", userModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userModel", bindingResult);
            redirectAttributes.addFlashAttribute("isPasswordConfirm", false);
            return "redirect:/users/register";
        }

        UserRegistrationServiceModel userRegistrationServiceModel = modelMapper
                .map(userModel, UserRegistrationServiceModel.class);


        userService.registerAndLoginUser(userRegistrationServiceModel);

        return "redirect:/";
    }
}
