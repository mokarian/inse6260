package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.Role;

/**
 * A role repository interface
 *
 * @author Maysam Mokarian
 * @version 1.0
 * @since 30.10.2017
 */
@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Integer>{
	Role findByRole(String role);

}
