package br.com.mymoney.authserver.services;

import br.com.mymoney.authserver.models.entities.Permission;
import br.com.mymoney.crudcommon.services.CrudService;
import org.springframework.stereotype.Service;

@Service
public class PermissionService extends CrudService<Permission, Long> {
}
