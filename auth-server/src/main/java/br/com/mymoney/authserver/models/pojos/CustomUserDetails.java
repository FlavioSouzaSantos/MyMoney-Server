package br.com.mymoney.authserver.models.pojos;

import br.com.mymoney.authserver.models.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {
    private String email;
    private String password;
    private boolean actived;

    private boolean blocked;
    private UUID uuid;
    private Set<? extends GrantedAuthority> authorities;

    public CustomUserDetails(User user){
        email = user.getEmail();
        password = user.getPassword();
        actived = user.isActived();
        blocked = user.isBlocked();
        uuid = user.getUuid();
        authorities = user.getRoles().stream().map(p -> "ROLE_"+p.getName()).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !blocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return actived;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
