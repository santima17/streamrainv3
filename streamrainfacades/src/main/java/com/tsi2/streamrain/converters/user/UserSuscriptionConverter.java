package com.tsi2.streamrain.converters.user;

import java.util.ArrayList;
import java.util.List;

import com.tsi2.streamrain.converters.interfaces.IConverter;
import com.tsi2.streamrain.datatypes.user.UserSuscriptionDto;
import com.tsi2.streamrain.model.generator.UserSubscriptions;

public class UserSuscriptionConverter implements IConverter<UserSuscriptionDto, UserSubscriptions>{

	public UserSuscriptionDto converter(UserSubscriptions source) {
		UserSuscriptionDto target = new UserSuscriptionDto();
		target.setDateFinish(source.getDateFinish());
		target.setDatePayment(source.getDatePayment());
		target.setDateStart(source.getDateStart());
		target.setId(source.getId());
		target.setJanusUserToken(source.getJanusUserToken());
		target.setPaymentMethodId(source.getPaymentMethods().getId());
		target.setUserId(source.getUsers().getId());
		return target;
	}

	public UserSubscriptions deConverter(UserSuscriptionDto source) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<UserSuscriptionDto> convertAll(List<UserSubscriptions> source) {
		List<UserSuscriptionDto> dtoList = new ArrayList<UserSuscriptionDto>();
		for(UserSubscriptions user : source) {
			dtoList.add(converter(user));
		}
		return dtoList;
	}

	public List<UserSubscriptions> deConvertAll(List<UserSuscriptionDto> source) {
		// TODO Auto-generated method stub
		return null;
	}

}
