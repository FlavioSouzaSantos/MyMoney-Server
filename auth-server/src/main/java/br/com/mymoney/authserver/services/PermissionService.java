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

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PermissionService extends CrudService<Permission, Long> {

    private final UserRepository userRepository;

    public ResultCheckPermissionDto checkPermissionForUser(CheckPermissionDto dto, HttpServletRequest request) {
        Optional<UUID> userUUID = getUUIDAuthenticated(request);
        if(userUUID.isPresent()){
            Optional<User> user = userRepository.findFirstUserByUuid(userUUID.get());
            if(user.isPresent() && user.get().getRoles().stream().map(Role::getName).anyMatch(p -> p.equals(dto.roleName()))){
                long count = ((PermissionRepository) repository).countPermissionsTheRoleHas(dto.roleName(), dto.url(), dto.method());
                if(count > 0L){
                    return new ResultCheckPermissionDto(userUUID.get(), dto.url(), dto.method(), true);
                }
            }
        }
        return new ResultCheckPermissionDto(null, dto.url(), dto.method(), false);
    }
}
