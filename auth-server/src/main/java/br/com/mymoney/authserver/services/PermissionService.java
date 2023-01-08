package br.com.mymoney.authserver.services;

import br.com.mymoney.authserver.models.dtos.CheckPermissionDto;
import br.com.mymoney.authserver.models.dtos.ResultCheckPermissionDto;
import br.com.mymoney.authserver.models.entities.Permission;
import br.com.mymoney.authserver.models.entities.Role;
import br.com.mymoney.authserver.models.entities.User;
import br.com.mymoney.authserver.repositories.PermissionRepository;
import br.com.mymoney.authserver.repositories.UserRepository;
import br.com.mymoney.crudcommon.services.CrudService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PermissionService extends CrudService<Permission, Long> {

    private final UserRepository userRepository;
    private final AntPathMatcher pathMatcher;

    public ResultCheckPermissionDto checkPermissionForUser(CheckPermissionDto dto, HttpServletRequest request) {
        boolean hasPermission = false;
        Optional<UUID> userUUID = getUUIDAuthenticated(request);
        if(userUUID.isPresent()){
            Optional<User> user = userRepository.findFirstUserByUuid(userUUID.get());
            if(user.isPresent()){
                if(((PermissionRepository) repository).findAllUrl()
                        .stream().anyMatch(p -> pathMatcher.match(p, dto.url()))){
                    if(!user.get().getRoles().isEmpty() &&
                            ((PermissionRepository) repository).findAllUrlByRolesAndMethod(user.get().getRoles().stream().map(Role::getName).collect(Collectors.toSet()), dto.method())
                                    .stream().anyMatch(p -> pathMatcher.match(p, dto.url()))){
                        hasPermission = true;
                    }
                }else{
                    hasPermission = true;
                }
            }
        }
        return new ResultCheckPermissionDto(userUUID.orElse(null), dto.url(), dto.method(), hasPermission);
    }
}
