package com.example.UserBaseTest.controller;

import com.example.UserBaseTest.repository.User;
import com.example.UserBaseTest.service.UserService;
import jakarta.websocket.server.PathParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/users")
public class UserViewController {
    final String templatesPath = "UserBaseTest/";
    final String requestPath = "/users";

    private final UserService userService;

    public UserViewController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showUsersPage(Model model) {
        model.addAttribute("users", userService.findAll());
        return getTemplatePath("userList");
    }

    @GetMapping("/create")
    public String createUserPage(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        return getTemplatePath("userCreate");
    }

    @PostMapping("/create")
    public String createUser(
            @RequestParam String name,
            @RequestParam LocalDate birth,
            @RequestParam String email,
            RedirectAttributes redirectAttributes) {
        try {
            User newUser = new User(name, email, birth);
            userService.create(newUser);
            return getRedirectPath("");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("name", name);
            redirectAttributes.addFlashAttribute("email", email);
            redirectAttributes.addFlashAttribute("birth", birth);
            return getRedirectPath("create");
        }
    }

    @GetMapping("/delete")
    public String deleteUserPage(@PathParam("id") Long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return getTemplatePath("userDelete");
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.delete(id);
        return getRedirectPath("");
    }

    @GetMapping("/update")
    public String updateUserPage(@PathParam("id") Long id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("id", id);
        if (!model.containsAttribute("email")) {
            model.addAttribute("name", user.getName());
        }
        if (!model.containsAttribute("email")) {
            model.addAttribute("email", user.getEmail());
        }
        return getTemplatePath("userUpdate");
    }

    @PostMapping("/update")
    public String updateUser(@RequestParam Long id,
                             @RequestParam String name,
                             @RequestParam String email,
                             RedirectAttributes redirectAttributes) {
        try {
            userService.update(id, email, name);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("name", name);
            redirectAttributes.addFlashAttribute("email", email);
            return getRedirectPath("update?id=" + id.toString());
        }
        return getRedirectPath("");
    }

    public String getTemplatePath(String path){
        return templatesPath + path;
    }

    public String getRedirectPath(String path) {
        if(path.isEmpty()){
            return "redirect:" + requestPath;
        }
        else {
            return "redirect:" + requestPath + "/" + path;
        }
    }
}