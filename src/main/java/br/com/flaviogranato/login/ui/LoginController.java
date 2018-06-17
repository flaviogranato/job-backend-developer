package br.com.flaviogranato.login.ui;

import br.com.flaviogranato.login.infra.WebUtils;
import java.security.Principal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
  @RequestMapping(value = "/admin",
                  method = RequestMethod.GET)
  public String adminPage(Model model,
                          Principal principal) {
    User loginedUser = (User) ((Authentication) principal).getPrincipal();

    String userInfo = WebUtils.toString(loginedUser);
    model.addAttribute("userInfo",
                       userInfo);
    return "adminPage";
  }


  @RequestMapping(value = {"/",
                           "/login"},
                  method = RequestMethod.GET)
  public String loginPage(Model model) {
    return "loginPage";
  }


  @RequestMapping(value = "/logout",
                  method = RequestMethod.GET)
  public String logoutPage(Model model) {
    model.addAttribute("title",
                       "Logout");
    return "logoutPage";
  }


  @RequestMapping(value = "/userInfo",
                  method = RequestMethod.GET)
  public String userInfo(Model model,
                         Principal principal) {
    String userName = principal.getName();
    System.out.println("User Name: " + userName);
    User loginedUser = (User) ((Authentication) principal).getPrincipal();
    String userInfo = WebUtils.toString(loginedUser);
    model.addAttribute("userInfo",
                       userInfo);
    return "userInfoPage";
  }


  @RequestMapping(value = "/403",
                  method = RequestMethod.GET)
  public String accessDenied(Model model,
                             Principal principal) {
    if (principal != null) {
      User loginedUser = (User) ((Authentication) principal).getPrincipal();

      String userInfo = WebUtils.toString(loginedUser);

      model.addAttribute("userInfo",
                         userInfo);

      String message = "Hi " + principal.getName()
                       + "<br> You do not have permission to access this page!";
      model.addAttribute("message",
                         message);

    }
    return "403Page";
  }
}
