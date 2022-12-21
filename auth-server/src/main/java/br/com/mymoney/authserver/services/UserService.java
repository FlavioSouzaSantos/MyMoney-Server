package br.com.mymoney.authserver.services;

import br.com.mymoney.authserver.exceptions.UseruuuidNotFoundException;
import br.com.mymoney.authserver.models.pojos.CustomUserDetails;
import br.com.mymoney.authserver.models.entities.User;
import br.com.mymoney.authserver.repositories.UserRepository;
import br.com.mymoney.crudcommon.services.CrudService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService extends CrudService<User, Long> implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return ((UserRepository) repository).findFirstUserByEmail(username)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(messageSource.getMessage("error.user.not.found.with.username",
                        new String[]{username}, null)));
    }

    public UserDetails loadUserByUuid(UUID uuid) throws UseruuuidNotFoundException {
        return ((UserRepository) repository).findFirstUserByUuid(uuid)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UseruuuidNotFoundException(messageSource.getMessage("error.user.not.found.with.uuid",
                        new String[]{uuid.toString()}, null)));
    }
}
