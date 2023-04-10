package org.example.services;

import org.example.accounts.models.UserProfile;
import org.example.security.UserProfileDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserProfileDetailsService implements UserDetailsService {

    private final UserProfileService userProfileService;

    @Autowired
    public UserProfileDetailsService(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserProfile userProfile = userProfileService.findByUsername(username);
        if (userProfile == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        return new UserProfileDetails(userProfile);
    }
}
