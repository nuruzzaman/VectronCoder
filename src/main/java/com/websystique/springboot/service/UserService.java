package com.websystique.springboot.service;

import com.websystique.springboot.model.User;

import java.util.List;

/**
* @author   : Mohammad Nuruzzaman
* @email    : dr.zaman1981@gmail.com
* @version  : v1.0, Date: Sep 19, 2018 3:17:25 PM
*/

public interface UserService {
	
	User findById(Long id);

	User findByEmailAddress(String email);

	User saveUser(User user);

	void updateUser(User user);

	void deleteUserById(Long id);

	void deleteAllUsers();

	List<User> findAllUsers();

	boolean isUserEmailExist(User user);
	
}