package com.freyder.springcloud.msvc.users.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import com.freyder.springcloud.msvc.users.entities.Role;

public interface RoleRepository extends CrudRepository<Role, Long>{
    //We need to find a custom method that searches by role name
    Optional<Role> findByName(String name);

}
