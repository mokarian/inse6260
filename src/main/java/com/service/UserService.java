package com.service;

import com.model.User;

/**
 * A service interface for user
 *
 * @author Maysam Mokarian
 * @version 1.0
 * @since 30.10.2017
 */
public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
}
