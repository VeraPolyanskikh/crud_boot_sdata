package crud.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("")
public class UserController {

    public final UserDetailsService userDetailsService;
    // inject via application.properties
    @Value("${welcome.message}")
    private String message;

    @Autowired

    public UserController(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/user")
    public String show(Model model, Principal principal) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
        String name = token.getName();

        model.addAttribute("showUser", userDetailsService.loadUserByUsername(name));
        return "user/show";
    }

    @ModelAttribute("header")
    public String populateHeader() {
        return message;
    }

    @GetMapping
    public String getLoginPage() {
        return "user/login";
    }
}
