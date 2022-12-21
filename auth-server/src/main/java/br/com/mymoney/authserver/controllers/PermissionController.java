package br.com.mymoney.authserver.controllers;

import br.com.mymoney.authserver.models.entities.Permission;
import br.com.mymoney.crudcommon.controllers.CrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permissions")
public class PermissionController extends CrudController<Permission, Long> {
}
