package com.au.vectron.form; 

import org.modelmapper.ModelMapper;

import com.au.vectron.model.User;

/**
 * @author Mohammad Nuruzzaman
 * @email  dr.zaman1981@gmail.com 
 * @date   18 Sept 2018 22:05:40 
 */

public class DtoToEntityMapper {

	public User asEntity(UserDto dto) {
        return new ModelMapper().map(dto, User.class);
    }
	
}
