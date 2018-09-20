package com.websystique.springboot.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.websystique.springboot.dto.DtoToEntityMapper;
import com.websystique.springboot.dto.EntityToDtoMapper;
import com.websystique.springboot.dto.UserDto;
import com.websystique.springboot.model.User;
import com.websystique.springboot.repositories.UserRepository;
import com.websystique.springboot.service.UserService;
import com.websystique.springboot.util.CustomErrorType;

/**
 * @author Mohammad Nuruzzaman
 * @email  dr.zaman1981@gmail.com 
 * @date   18 Sept 2018 22:05:40 
 */

@RestController
@RequestMapping("/api")
public class RestApiController {

	private static String UPLOAD_DIR = System.getProperty("user.home") + "/test";
	
	private DtoToEntityMapper d2eMapper = new DtoToEntityMapper();
	
	private EntityToDtoMapper e2dMapper = new EntityToDtoMapper();
	
	@Autowired
	UserService userService; 
	@Autowired
	UserRepository userRepo;

	// -------------------Retrieve All Users---------------------------------------------

	@RequestMapping(value = "/user/", method = RequestMethod.GET)
	public ResponseEntity<List<UserDto>> listAllUsers() {
		String pattern = "yyyy-MM-dd hh:mm:ss";
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		File uploadDir = new File(UPLOAD_DIR);
        File[] files = uploadDir.listFiles();
        List<String> imglist = new ArrayList<String>();
        for (File file : files) {
        	imglist.add(file.getName());
        }
        List<UserDto> dtoList = new ArrayList<UserDto>();

		List<User> userinfo = userService.findAllUsers();
		if (userinfo.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		
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
		
		return new ResponseEntity<List<UserDto>>(dtoList, HttpStatus.OK);
	}

	// -------------------Retrieve Single User------------------------------------------

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable("id") long id) {
		User user = userService.findById(id);
		if (user == null) {
			return new ResponseEntity(new CustomErrorType("User with id " + id 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	// -------------------Create a User-------------------------------------------
	@RequestMapping(value = "/user/", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@ModelAttribute UserDto user, UriComponentsBuilder ucBuilder ) {
		
		User userEntity = d2eMapper.asEntity(user); 
		
		if (userService.isUserEmailExist(userEntity)) {
			return new ResponseEntity<String>("Email Address Already Exist.", HttpStatus.FOUND);
		}
		if (userService.isMobileExist(userEntity)) {
			return new ResponseEntity<String>("Mobile Number Address Already Exist.", HttpStatus.FOUND);
		}
		
		String result = null;
        try {
            User saveRes = userService.saveUser(userEntity);
            
            if(user.getFiles().getSize()>0){
            	Long userId = saveRes.getId(); 
                result = this.saveUploadedFiles(user.getFiles(), userId);
                
                userRepo.saveImgUserById(result, saveRes.getId()); 
            }             
        }
        catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(userEntity.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// Save Files
    private String saveUploadedFiles(MultipartFile file, Long userId) throws IOException {
 
        // Make sure directory exists!
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
 
        StringBuilder sb = new StringBuilder();
        String uploadFilePath = UPLOAD_DIR + "/" + userId +".png";
        
        byte[] bytes = file.getBytes();
        Path path = Paths.get(uploadFilePath);
        Files.write(path, bytes);
        sb.append(uploadFilePath).append(", ");
    
        return sb.toString();
    }
    
	// ------------------- Update a User ------------------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> updateUserWithImage(@PathVariable("id") long id, @ModelAttribute UserDto user, UriComponentsBuilder ucBuilder ) {
    	User userEntity = d2eMapper.asEntity(user); 
		
    	if (userService.isEmailExistWithCurrentUser(userEntity)) {
			return new ResponseEntity<String>("Email Address Already Exist.", HttpStatus.FOUND);
		}
		if (userService.isMobileExistWithCurrentUser(userEntity)) {
			return new ResponseEntity<String>("Mobile Number Address Already Exist.", HttpStatus.FOUND);
		}
		
		User currentUser = userService.findById(id);		
		if (currentUser == null) {
			return new ResponseEntity(new CustomErrorType("Unable to upate. User with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		//delete existing image 
		deleteUserImage(String.valueOf(id)); 
		
		//Upload New Image 
		String result =null; 
		try {
			result = this.saveUploadedFiles(user.getFiles(), id);
			
		} catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
		
		currentUser.setFirstName(user.getFirstName());
		currentUser.setLastName(user.getLastName());
		currentUser.setEmailAddress(user.getEmailAddress());
		currentUser.setMobileNumber(user.getMobileNumber());
		currentUser.setImgName(String.valueOf(id)+".png"); 
		currentUser.setLastUpdatedDate(new Date());
		userService.updateUser(currentUser);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(userEntity.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    
	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody User user) {

		User currentUser = userService.findById(id);
		UserDto userDto = e2dMapper.asDto(currentUser); 
		
		if (userService.isEmailExistWithCurrentUser(user)) {
			userDto.setStatusMessage("Email Address Already Exist"); 
			return new ResponseEntity<UserDto>(userDto, HttpStatus.FOUND);
		}
		if (userService.isMobileExistWithCurrentUser(user)) {
			userDto.setStatusMessage("Mobile Number Already Exist"); 
			return new ResponseEntity<UserDto>(userDto, HttpStatus.FOUND);
		}
		
		if (currentUser == null) {
			return new ResponseEntity(new CustomErrorType("Unable to upate. User with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentUser.setFirstName(user.getFirstName());
		currentUser.setLastName(user.getLastName());
		currentUser.setEmailAddress(user.getEmailAddress());
		currentUser.setMobileNumber(user.getMobileNumber());
		currentUser.setLastUpdatedDate(new Date());		
		userService.updateUser(currentUser);
				
		return new ResponseEntity<UserDto>(e2dMapper.asDto(currentUser), HttpStatus.OK);
	}

	// ------------------- Delete a User-----------------------------------------

	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {

		User user = userService.findById(id);
		if (user == null) {
			return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		userService.deleteUserById(id);
		
		deleteUserImage(String.valueOf(id)); 
	   	 
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

	public void deleteUserImage(String id){
		// Delete the image    	 
	   	 String filePath = UPLOAD_DIR + "/" + id +".png";
	   	 try{ 
	   		 Files.deleteIfExists(Paths.get(filePath)); 
	   	 } catch(NoSuchFileException e) { 
	   		 System.out.println("No such file/directory exists"); 
	   	 } catch(DirectoryNotEmptyException e) { 
	   		 System.out.println("Directory is not empty."); 
	   	 } catch(IOException e) { 
	   		 System.out.println("Invalid permissions."); 
	   	 } 
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
    
}