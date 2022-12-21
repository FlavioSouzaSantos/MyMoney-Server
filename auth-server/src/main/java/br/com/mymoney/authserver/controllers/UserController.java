package br.com.mymoney.authserver.controllers;

import br.com.mymoney.authserver.models.entities.User;
import br.com.mymoney.crudcommon.controllers.CrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController extends CrudController<User, Long> {
}
