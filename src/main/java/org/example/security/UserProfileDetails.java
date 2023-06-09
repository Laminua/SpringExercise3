package org.example.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.accounts.models.UserProfile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
@Getter
public class UserProfileDetails implements UserDetails {
    private final UserProfile userProfile;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(userProfile.getRole().toString()));
    }

    @Override
    public String getPassword() {
        return this.userProfile.getPassword();
    }

    @Override
    public String getUsername() {
        return this.userProfile.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
