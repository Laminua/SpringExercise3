package org.example.controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.accounts.models.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@Slf4j
public class RedirectController {

    @RequestMapping("/")
    public RedirectView redirectFromRootBasedOnRoleIfAlreadyLogged() {
        log.info("Redirecting user from root");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString();

        String redirectUrl = "";
        if (role.contains(Role.ROLE_ADMIN.toString())) {
            redirectUrl = "admin/showUsers";
        } else if (role.contains(Role.ROLE_USER.toString())) {
            redirectUrl = "welcome";
        }

        return new RedirectView(redirectUrl);
    }
}
