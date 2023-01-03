package br.com.mymoney.authserver.controllers.v1;

import br.com.mymoney.authserver.models.dtos.CheckPermissionDto;
import br.com.mymoney.authserver.models.dtos.ResultCheckPermissionDto;
import br.com.mymoney.authserver.models.entities.Permission;
import br.com.mymoney.authserver.services.PermissionService;
import br.com.mymoney.crudcommon.controllers.CrudController;
import br.com.mymoney.crudcommon.exceptions.ResponseErrorException;
import br.com.mymoney.crudcommon.models.dtos.ResponseErrorDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/permissions")
public class PermissionController extends CrudController<Permission, Long> {

    @PostMapping("/check")
    public ResponseEntity<ResultCheckPermissionDto> checkHasPermission(@Valid @RequestBody CheckPermissionDto dto,
                                                                       BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()){
            Set<ResponseErrorDto> errors = bindingResult.getFieldErrors().stream()
                    .map(ResponseErrorDto::new)
                    .collect(Collectors.toSet());

            throw new ResponseErrorException(errors);
        }

        return ResponseEntity.ok(((PermissionService) crudService).checkPermissionForUser(dto, request));
    }
}
