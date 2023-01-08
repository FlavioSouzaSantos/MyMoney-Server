package br.com.mymoney.authserver.repositories;

import br.com.mymoney.authserver.models.entities.Permission;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {

    @Query("SELECT COUNT(p) FROM Role r JOIN r.permissions p " +
            "WHERE r.name IN (?1) AND p.url = ?2 AND ?3 in ELEMENTS(p.methods)")
    Long countPermissionsTheRoleHas(Set<String> rolesName, String url, String method);

    @Cacheable("url_of_methods_permissions")
    @Query("SELECT p.url FROM Permission p JOIN p.roles r " +
            "WHERE r.name IN (?1) AND ?2 IN ELEMENTS(p.methods) " +
            "AND p.actived = true")
    List<String> findAllUrlByRolesAndMethod(Set<String> rolesName, String method);

    @Cacheable("url_permissions")
    @Query("SELECT p.url FROM Permission p WHERE p.actived = true")
    List<String> findAllUrl();
}
