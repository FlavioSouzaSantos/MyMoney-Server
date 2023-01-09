package br.com.mymoney.authserver.repositories;

import br.com.mymoney.authserver.models.entities.Role;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import static br.com.mymoney.authserver.Constants.CACHE_URL_OF_METHODS_PERMISSIONS;
import static br.com.mymoney.authserver.Constants.CACHE_URL_PERMISSIONS;

public interface RoleRepository extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {

    @CacheEvict(value = {CACHE_URL_OF_METHODS_PERMISSIONS, CACHE_URL_PERMISSIONS}, allEntries = true)
    @Override
    <S extends Role> S save(S entity);

    @CacheEvict(value = {CACHE_URL_OF_METHODS_PERMISSIONS, CACHE_URL_PERMISSIONS}, allEntries = true)
    @Override
    void delete(Role entity);
}
