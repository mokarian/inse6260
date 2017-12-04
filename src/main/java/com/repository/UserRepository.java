package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.User;

/**
 * A user repository interface
 *
 * @author Maysam Mokarian
 * @version 1.0
 * @since 30.10.2017
 */
@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
	 User findByEmail(String email);
}
