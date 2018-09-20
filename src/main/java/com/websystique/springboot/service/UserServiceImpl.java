package com.websystique.springboot.service;

import java.util.List;

import com.websystique.springboot.model.User;
import com.websystique.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author   : Mohammad Nuruzzaman
* @email    : dr.zaman1981@gmail.com
* @version  : v1.0, Date: Sep 19, 2018 3:17:25 PM
*/

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;

	public User findById(Long id) {
		return userRepository.findOne(id);
	}

	public User findByEmailAddress(String email) {
		return userRepository.findByEmailAddress(email);
	}

	public User findByMobileNumber(String mobile) {
		return userRepository.findByMobileNumber(mobile);
	}
	
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public void updateUser(User user){
		saveUser(user);
	}

	public void deleteUserById(Long id){
		userRepository.delete(id);
	}

	public void deleteAllUsers(){
		userRepository.deleteAll();
	}

	public List<User> findAllUsers(){
		return userRepository.findAll();
	}

	public boolean isUserEmailExist(User user) {
		return findByEmailAddress(user.getEmailAddress()) != null;
	}

	public boolean isMobileExist(User user) {
		return findByMobileNumber(user.getMobileNumber()) != null;
	}

	public User isEmailExistWithCurrentUser(String email, Long id) {
		return userRepository.isEmailExistWithCurrentUser(email, id);
	}	
	public boolean isEmailExistWithCurrentUser(User currentUser) {
		return isEmailExistWithCurrentUser(currentUser.getEmailAddress(), currentUser.getId()) != null;
	}

	public User isMobileExistWithCurrentUser(String mobile, Long id) {
		return userRepository.isMobileExistWithCurrentUser(mobile, id);
	}	
	public boolean isMobileExistWithCurrentUser(User currentUser) {
		return isMobileExistWithCurrentUser(currentUser.getMobileNumber(), currentUser.getId()) != null;
	}


}
