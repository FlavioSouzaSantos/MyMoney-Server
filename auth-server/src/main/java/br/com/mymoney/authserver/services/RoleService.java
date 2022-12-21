package br.com.mymoney.authserver.services;

import br.com.mymoney.authserver.models.entities.Role;
import br.com.mymoney.crudcommon.services.CrudService;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends CrudService<Role, Integer> {
}
