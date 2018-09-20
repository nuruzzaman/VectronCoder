package com.websystique.springboot.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
* @author   : Mohammad Nuruzzaman
* @email    : dr.zaman1981@gmail.com
* @version  : v1.0, Date: Sep 19, 2018 3:17:25 PM
*/

@Entity
@Table(name="APP_USER")
public class User implements Serializable{

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Column(name="email_address", columnDefinition = "VARCHAR(100)", nullable=false)
	private String emailAddress;

	@NotEmpty
	@Column(name="first_name", columnDefinition = "VARCHAR(50)", nullable=false)
	private String firstName;

	@NotEmpty
	@Column(name="last_name", columnDefinition = "VARCHAR(50)", nullable=false)
	private String lastName;
	
	@NotEmpty
	@Column(name="mobile_number", columnDefinition = "VARCHAR(20)", nullable=false)
	private String mobileNumber;

	@Column(name="created_date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable=false, updatable=false)
	private Date createdDate;
	
	@Column(name="last_updated_date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable=false, updatable=true)
	private Date lastUpdatedDate;
	
	@Column(name="image_name", columnDefinition = "VARCHAR(50)", nullable=true)
	private String imgName;
	
	@Transient
	private MultipartFile files;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	public MultipartFile getFiles() {
		return files;
	}

	public void setFiles(MultipartFile files) {
		this.files = files;
	}
	
	/*@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (id != null ? !id.equals(user.id) : user.id != null) return false;
		if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
		if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
		if (emailAddress != null ? !emailAddress.equals(user.emailAddress) : user.emailAddress != null) return false;
		if (mobileNumber != null ? !mobileNumber.equals(user.mobileNumber) : user.mobileNumber != null) return false;
		//if (createdDate != null ? !createdDate.equals(user.createdDate) : user.createdDate != null) return false;
		//if (lastUpdatedDate != null ? !lastUpdatedDate.equals(user.lastUpdatedDate) : user.lastUpdatedDate != null) return false;
		return imgName != null ? imgName.equals(user.imgName) : user.imgName == null;
	}*/

	/*@Override
	public int hashCode() {
		int result;
		long temp;
		result = id != null ? id.hashCode() : 0;
		result = 31 * result + (imgName != null ? imgName.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (age != null ? age.hashCode() : 0);
		temp = Double.doubleToLongBits(salary);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}*/

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", emailAddress=" + emailAddress +", mobileNumber=" + mobileNumber 
				+ ", createdDate=" +createdDate + ", lastUpdatedDate="+lastUpdatedDate 
				+ ", imgName=" +imgName +"]";
	}

}
