// $Id: UserDto.java, v1.0 Date:Sep 19, 2018 3:14:33 PM Engr.Nuruzzaman $

/*
 * Copyright(c) 2010-2018 eGudang (Malaysia). All Rights Reserved.
 * This software is the proprietary of "Artificial Casting".
 *
 */
package com.au.vectron.form;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author   : Mohammad Nuruzzaman
 * @email    : dr.zaman1981@gmail.com
 * @version  : v1.0, Date: Sep 19, 2018 3:14:33 PM
 */

public class UserDto {
	
	private long id;

	private String emailAddress;

	private String firstName;

	private String lastName;
	
	private String mobileNumber;

	private String createdDate;
	
	private String lastUpdatedDate;
		
	private String description;
	
	private String imgName;
	
    private MultipartFile[] files;

    
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	
    
}
