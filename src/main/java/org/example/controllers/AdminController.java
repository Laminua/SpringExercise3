package org.example.controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.accounts.models.UserProfile;
import org.example.services.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@Slf4j
public class AdminController {
    private final UserProfileService userProfileService;

    @Autowired
    public AdminController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @RequestMapping("/admin/showUsers")
    public String showUsers(Model model) {
        log.info("Showing \"List of users\" page");
        model.addAttribute("usersList", userProfileService.findAll());

        return "admin/index";
    }

    @RequestMapping("/admin/addUserForm")
    public String showAddUserForm(Model model) {
        log.info("Showing \"Add user\" page");

        model.addAttribute("userProfile", new UserProfile());

        return "admin/add-user";
    }

    @RequestMapping("/admin/addUser")
    public ModelAndView addUsersToMap(@ModelAttribute("userProfile") UserProfile profile) {
        log.info("Inserting user into database. Login: "
                + profile.getUsername()
                + " Role: " + profile.getRole()
                + " Имя: " + profile.getName()
                + " Email: " + profile.getEmail());

        if (profile.getName().equals("")) {
            return new ModelAndView("redirect:/");
        } else if (userProfileService.findByUsername(profile.getUsername()) != null) {
            return new ModelAndView("redirect:/admin/userExists");
        }

        userProfileService.save(profile);

        return new ModelAndView("redirect:/admin/showUsers");
    }

    @RequestMapping("/admin/userExists")
    public String showUserExistsMessage() {
        log.info("Showing \"User already exist\" page");

        return "admin/login-exists";
    }

    @RequestMapping("/admin/deleteUser")
    public RedirectView deleteUserFromMap(@RequestParam("userIdToDelete") int id) {
        log.info("Removing user from database, ID: " + id);

        userProfileService.delete(id);

        return new RedirectView("/admin/showUsers");
    }

    @RequestMapping("/admin/updateUserForm")
    public String showUpdateUserForm(@RequestParam("userIdToUpdate") int id, Model model) {
        log.info("Showing \"update user\" page, user ID: " + id);

        UserProfile userProfile = userProfileService.findOne(id);
        model.addAttribute("userProfile", userProfile);

        return "admin/update-user";
    }

    @RequestMapping("/admin/updateUser")
    public RedirectView updateUser(UserProfile profile) {
        log.info("Updating user in database. Name: " + profile.getName() + " Email: " + profile.getEmail());

        userProfileService.update(profile.getId(), profile);

        return new RedirectView("/admin/showUsers");
    }
}
