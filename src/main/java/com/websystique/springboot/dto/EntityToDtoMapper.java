package com.websystique.springboot.dto; 

import org.modelmapper.ModelMapper;

import com.websystique.springboot.model.User;

/**
 * @author Mohammad Nuruzzaman
 * @email  dr.zaman1981@gmail.com 
 * @date   18 Sept 2018 22:05:40 
 */

public class EntityToDtoMapper {	
	
	public UserDto asDto(User entity) {
        return new ModelMapper().map(entity, UserDto.class);
    }
	

}
