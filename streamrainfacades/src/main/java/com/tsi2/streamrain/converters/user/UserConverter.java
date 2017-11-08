package com.tsi2.streamrain.converters.user;

import java.util.ArrayList;
import java.util.List;

import com.tsi2.streamrain.model.generator.Users;
import com.tsi2.streamrain.converters.interfaces.IConverter;
import com.tsi2.streamrain.datatypes.user.UserDto;

public class UserConverter implements IConverter<UserDto, Users>{

	public UserDto converter(Users source) {
		UserDto userDto = new UserDto();
		userDto.setCity(source.getCity());
		userDto.setCountry(source.getCountry());
		userDto.setEmail(source.getEmail());
		userDto.setIsBanned(source.getIsBanned());
		userDto.setPassword(source.getPassword());
		userDto.setNickname(source.getNickname());
		userDto.setFacebookUserId(source.getFacebookUserId());
		userDto.setId(source.getId());
		userDto.setIntegratorId(source.getIntegratorId());
		userDto.setTwitterUserId(source.getTwitterUserId());
		userDto.setBlocked(source.isBlocked()); 
		return userDto;
	}

	public Users deConverter(UserDto source) {
		Users user = new Users();
		user.setCity(source.getCity());
		user.setCountry(source.getCountry());
		user.setEmail(source.getEmail());
		user.setIsBanned(source.getIsBanned());
		user.setPassword(source.getPassword());
		user.setNickname(source.getNickname());
		user.setFacebookUserId(source.getFacebookUserId());
		user.setId(source.getId());
		user.setIntegratorId(source.getIntegratorId());
		user.setTwitterUserId(source.getTwitterUserId());
		user.setBlocked(source.getBlocked());
		return user;
	}

	public List<UserDto> convertAll(List<Users> source) {
		List<UserDto> dtoList = new ArrayList<UserDto>();
		for(Users user : source) {
			dtoList.add(converter(user));
		}
		return dtoList;
	}

	public List<Users> deConvertAll(List<UserDto> source) {
		// TODO Auto-generated method stub
		return null;
	}

}
