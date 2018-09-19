// $Id: MainRESTController.java, v1.0 Date:Sep 19, 2018 3:17:25 PM Engr.Nuruzzaman $

/*
 * Copyright(c) 2010-2018 eGudang (Malaysia). All Rights Reserved.
 * This software is the proprietary of "Artificial Casting".
 *
 */
package com.au.vectron.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.mockito.internal.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.au.vectron.form.DtoToEntityMapper;
import com.au.vectron.form.EntityToDtoMapper;
import com.au.vectron.form.UserDto;
import com.au.vectron.model.User;
import com.au.vectron.repo.UserRepository;

/**
 *
 * @author   : Mohammad Nuruzzaman
 * @email    : dr.zaman1981@gmail.com
 * @version  : v1.0, Date: Sep 19, 2018 3:17:25 PM
 */

@RestController
public class MainRESTController {

	private DtoToEntityMapper d2eMapper = new DtoToEntityMapper();
	
	private EntityToDtoMapper e2dMapper = new EntityToDtoMapper();
	
	@Autowired
    private UserRepository userRepository;
	
	
	// Linux: /home/{user}/test
    // Windows: C:/Users/{user}/test
    private static String UPLOAD_DIR = System.getProperty("user.home") + "/test";
    
    @PostMapping("/rest/uploadMultiFiles")
    public ResponseEntity<?> uploadFileMulti(@ModelAttribute UserDto user) throws Exception {
    	//Check IsEmail Exist?
    	User isEmailExist = userRepository.findByEmailAddress(user.getEmailAddress());
    	if (isEmailExist !=null) {
    		return new ResponseEntity<String>("Email Address Already Exist", HttpStatus.CONFLICT);
    	}
    	//Check IsMobile Exist? 
    	User isMobileExist = userRepository.findByMobileNumber(user.getMobileNumber());
    	if (isMobileExist !=null) {
    		return new ResponseEntity<String>("Mobile Number Already Exist", HttpStatus.CONFLICT);
    	}
		
        String result = null;
        try {        	
            User saveRes = userRepository.save(d2eMapper.asEntity(user));
            
            Long userId = saveRes.getId(); 
            result = this.saveUploadedFiles(user.getFiles(), userId);

            userRepository.saveImgUserById(result, saveRes.getId()); 
            
        }
        catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
 
        return new ResponseEntity<String>("User Added Successfully- " + user.getFirstName(), HttpStatus.OK);
    }
    
    // Save Files
    private String saveUploadedFiles(MultipartFile[] files, Long userId) throws IOException {
 
        // Make sure directory exists!
        File uploadDir = new File(UPLOAD_DIR);
        uploadDir.mkdirs();
 
        StringBuilder sb = new StringBuilder();
        
        for (MultipartFile file : files) {
 
            if (file.isEmpty()) {
                continue;
            }
            String uploadFilePath = UPLOAD_DIR + "/" + userId +".png";
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadFilePath);
            Files.write(path, bytes);
            sb.append(uploadFilePath).append(", ");
        }
        return sb.toString();
    }
	
    @GetMapping("/rest/getAllFiles")
    public List<UserDto> getListFiles() {
        
    	String pattern = "yyyy-MM-dd hh:mm:ss";
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		File uploadDir = new File(UPLOAD_DIR);
        File[] files = uploadDir.listFiles();
        List<String> imglist = new ArrayList<String>();
        for (File file : files) {
        	imglist.add(file.getName());
        }
        
		List<UserDto> dtoList = new ArrayList<UserDto>();

		List<User> userinfo = (List<User>) userRepository.findAll();
		for (Iterator i = userinfo.iterator(); i.hasNext();) {
			User userInfo = User.class.cast(i.next());
			
			UserDto userDto = new UserDto();
			userDto = e2dMapper.asDto(userInfo);
			
			for(int x=0; x < imglist.size(); x++){
				String userImgName = imglist.get(x); 
				if(userImgName.replace(".png", "").equalsIgnoreCase(String.valueOf(userInfo.getId()))){
					System.out.println(userInfo.getId()+" - "+userInfo.getFirstName()+" - "+userInfo.getEmailAddress()); 
					userDto.setImgName(userImgName); 
				}
			}
			
			userDto.setCreatedDate(simpleDateFormat.format(userInfo.getCreatedDate()));
			userDto.setLastUpdatedDate(simpleDateFormat.format(userInfo.getLastUpdatedDate()));
        	dtoList.add(userDto);
		}    	    
        
        return dtoList;
        
        //return list;
    }
    
    /**	 Download image file 	*/ 
    @GetMapping("/rest/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws MalformedURLException {
        File file = new File(UPLOAD_DIR + "/" + filename);
        if (!file.exists()) {
            throw new RuntimeException("File not found");
        }
        Resource resource = new UrlResource(file.toURI());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(resource);
    }
    
    /**		Delete User 	*/
    @DeleteMapping("/rest/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
    	
    	 userRepository.deleteById(Long.valueOf(id)); 
         
         return new ResponseEntity<String>("User Deleted Successfully", HttpStatus.OK);

    }

    /**		Update User 	*/
    @PutMapping(value = "/rest/updateUser/{id}")
	public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody UserDto user) {
    	//Check IsEmail Exist?
    	User isEmailExist = userRepository.findByEmailAddress(user.getEmailAddress());
    	if (isEmailExist !=null) {
    		return new ResponseEntity<String>("Email Address Already Exist", HttpStatus.CONFLICT);
    	}
    	//Check IsMobile Exist? 
    	User isMobileExist = userRepository.findByMobileNumber(user.getMobileNumber());
    	if (isMobileExist !=null) {
    		return new ResponseEntity<String>("Mobile Number Already Exist", HttpStatus.CONFLICT);
    	}
    	
    	userRepository.saveUserById(user.getFirstName(), user.getLastName(), user.getEmailAddress(), 
    			user.getMobileNumber(), new Date(), Long.valueOf(id)); 
    	
    	return new ResponseEntity<String>("User Updated Successfully", HttpStatus.OK);
	}
    
    
}
