package com.au.vectron.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Mohammad Nuruzzaman
 * @email  dr.zaman1981@gmail.com 
 * @date   18 Sept 2018 22:08:55 
 */

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name="email_address", columnDefinition = "VARCHAR(100)", nullable=false)
	private String emailAddress;

	@Column(name="first_name", columnDefinition = "VARCHAR(50)", nullable=false)
	private String firstName;

	@Column(name="last_name", columnDefinition = "VARCHAR(50)", nullable=false)
	private String lastName;
	
	@Column(name="mobile_number", columnDefinition = "VARCHAR(20)", nullable=false)
	private String mobileNumber;

	@Column(name="created_date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable=false, updatable=false)
	private Date createdDate;
	
	@Column(name="last_updated_date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable=false, updatable=false)
	private Date lastUpdatedDate;
	
	@Column(name="image_name", columnDefinition = "VARCHAR(50)", nullable=true)
	private String imgName;
	
	@Transient
	private MultipartFile userImage;

	public User() {
	}

	public User(String email, String firstname, String lastname, String mobile, 
			Date createdDate, Date lastUpdatedDate, MultipartFile userImage, String imgName) {
		this.emailAddress = email;
		this.firstName = firstname;
		this.lastName = lastname;
		this.mobileNumber = mobile;
		this.createdDate = createdDate;
		this.lastUpdatedDate = lastUpdatedDate;
		this.userImage = userImage;
		this.imgName = imgName; 
	}

	public long getId() {
		return id;
	}

	public void setId(long value) {
		this.id = value;
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

	public MultipartFile getUserImage() {
		return userImage;
	}

	public void setUserImage(MultipartFile userImage) {
		this.userImage = userImage;
	}
	
	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	
	@Override
    public String toString() {
        return "User{" +
                "id=" + id +	               
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' + 
                ", userImage='" + userImage + '\'' + 
                ", imgName='" + imgName + '\'' + 
                ", createdDate='" + createdDate + '\'' + 
                ", lastUpdatedDate='" + lastUpdatedDate + '\'' + 
                '}';
    }
	
}
