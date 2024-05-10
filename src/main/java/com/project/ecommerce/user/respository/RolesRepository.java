package com.project.ecommerce.user.respository;

import com.project.ecommerce.user.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolesRepository extends CrudRepository<Role, Long> {
    List<Role> findRoleEntitiesByRoleEnumIn(List<String> roleNames);
}
