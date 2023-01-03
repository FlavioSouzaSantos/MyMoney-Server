package br.com.mymoney.authserver.repositories;

import br.com.mymoney.authserver.models.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {

    @Query("SELECT COUNT(p) FROM Role r JOIN r.permissions p " +
            "WHERE r.name = ?1 AND p.url = ?2 AND p.methods in (?3)")
    Long countPermissionsTheRoleHas(String roleName, String url, String method);
}
