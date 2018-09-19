// $Id: UserRepository.java, v1.0 Date:Sep 19, 2018 4:35:00 PM Engr.Nuruzzaman $

/*
 * Copyright(c) 2010-2018 eGudang (Malaysia). All Rights Reserved.
 * This software is the proprietary of "Artificial Casting".
 *
 */
package com.au.vectron.repo;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RestController;

import com.au.vectron.form.UserDto;
import com.au.vectron.model.User;

/**
 *
 * @author   : Mohammad Nuruzzaman
 * @email    : dr.zaman1981@gmail.com
 * @version  : v1.0, Date: Sep 19, 2018 4:35:00 PM
 */

@Transactional
@RestController
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmailAddress(String emailAddress);

	User findByMobileNumber(String mobileNumber);
	
	void save(UserDto form); 
	
	@Query("select count(s) from User s where s.emailAddress = ?1 and id !=?2")
	long countByEmailAddress(String emailAddress, Long id);
	
	@Query("select count(s) from User s where s.mobileNumber = ?1 and id !=?2")
	long countByMobileNumber(String mobileNumber, Long id);
	
	@Transactional
	@Modifying
	@Query("update User set firstName = ?1, lastName = ?2, emailAddress=?3, "
			+ " mobileNumber=?4, lastUpdatedDate=?5 where id = ?6")
	int saveUserById(String firstName, String lastName, String emailAddress, 
			String mobileNumber, Date lastUpdatedDate, Long id);
	
	
	@Transactional
	@Modifying
	@Query("update User set imgName = ?1 where id = ?2")
	int saveImgUserById(String imgName, Long id);
	
	
	
	
	
}
