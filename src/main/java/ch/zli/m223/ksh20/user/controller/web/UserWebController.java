package ch.zli.m223.ksh20.user.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ch.zli.m223.ksh20.user.model.User;
import ch.zli.m223.ksh20.user.service.UserService;

import java.util.List;

@Controller
public class UserWebController {
    @Autowired
    private UserService userService;

    @GetMapping("/web/users")
    String getUserList(Model model) {
        List<User> users = userService.getUserList();

        //Add data to model
        model.addAttribute("users", users);

        //Pocess with 'userList' template
        return "userList";
    }

    @GetMapping("/web/users/{id}")
    String getUserById(Model model, @PathVariable Long id) {
        User user = userService.getUserById(id);

        //Add data to model
        model.addAttribute("user", user);

        //Pocess with 'userList' template
        return "showUser";
    }

    @GetMapping("/web/{id}/edit")
    String editUserById(Model model, @PathVariable Long id) {
        User user = userService.getUserById(id);

        model.addAttribute("user", user);

        return "edit";
    }

    @GetMapping("/error")
    String error() {
        return "error";
    }

    @PostMapping("/web/users")
    String addUser(Model model, @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {
        User user = new User(firstName, lastName, email, password);
        userService.addUser(user);
        if (user != null) {
            List<User> users = userService.getUserList();
            model.addAttribute("users", users);
            return "userList";
        } else {
            return "error";
        }
    }

    @GetMapping("web/register")
    String register(Model model) {
        return "register";
    }

    @GetMapping("web/login")
    String login(Model model) {
        return "login";
    }
}
