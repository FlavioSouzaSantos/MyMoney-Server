package br.com.mymoney.authserver.controllers.v1;

import br.com.mymoney.authserver.models.entities.Role;
import br.com.mymoney.crudcommon.controllers.CrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/roles")
public class RoleController extends CrudController<Role, Integer> {
}
