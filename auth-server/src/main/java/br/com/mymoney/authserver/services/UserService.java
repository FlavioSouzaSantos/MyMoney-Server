package br.com.mymoney.authserver.services;

import br.com.mymoney.authserver.AESUtil;
import br.com.mymoney.authserver.exceptions.UseruuuidNotFoundException;
import br.com.mymoney.authserver.models.entities.User;
import br.com.mymoney.authserver.models.pojos.CustomUserDetails;
import br.com.mymoney.authserver.repositories.UserRepository;
import br.com.mymoney.crudcommon.exceptions.ResponseErrorException;
import br.com.mymoney.crudcommon.models.dtos.ResponseErrorDto;
import br.com.mymoney.crudcommon.services.CrudService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService extends CrudService<User, Long> implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    @Value("${security.aes.key:}")
    private String aesKey;

    @Override
    protected void setDefaultValues(Optional<User> entity, HttpServletRequest request) {
        if(entity.isPresent() && entity.get().getId() == null){
            if(entity.get().getRawPassword() == null || entity.get().getRawPassword().isBlank()){
                throw new ResponseErrorException(Set.of(new ResponseErrorDto("password",
                        messageSource.getMessage("validation.model.User.password.NotBlank", null, null))));
            }
            try{
                String rawPasswordDecripted = AESUtil.decrypt(entity.get().getRawPassword(), aesKey);
                entity.get().setPassword(passwordEncoder.encode(rawPasswordDecripted));
            }catch (NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException ex){
                throw new ResponseErrorException(Set.of(new ResponseErrorDto("password",
                        messageSource.getMessage("validation.model.User.password.NotValid", null, null))));
            }
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return ((UserRepository) repository).findFirstUserByEmail(username)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(messageSource.getMessage("error.user.not.found.with.username",
                        new String[]{username}, null)));
    }

    @Transactional(readOnly = true)
    public UserDetails loadUserByUuid(UUID uuid) throws UseruuuidNotFoundException {
        return ((UserRepository) repository).findFirstUserByUuid(uuid)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UseruuuidNotFoundException(messageSource.getMessage("error.user.not.found.with.uuid",
                        new String[]{uuid.toString()}, null)));
    }
}
