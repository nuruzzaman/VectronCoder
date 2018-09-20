package com.websystique.springboot.repositories;

import com.websystique.springboot.model.User;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
* @author   : Mohammad Nuruzzaman
* @email    : dr.zaman1981@gmail.com
* @version  : v1.0, Date: Sep 19, 2018 3:17:25 PM
*/

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmailAddress(String email);

	@Transactional
	@Modifying
	@Query("update User set imgName = ?1 where id = ?2")
	int saveImgUserById(String imgName, Long id);
	
	
}
