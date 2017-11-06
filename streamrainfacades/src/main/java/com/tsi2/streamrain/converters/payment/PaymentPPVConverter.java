package com.tsi2.streamrain.converters.payment;

import java.util.ArrayList;
import java.util.List;

import com.tsi2.streamrain.converters.interfaces.IConverter;
import com.tsi2.streamrain.datatypes.payment.UserContentPPVDto;
import com.tsi2.streamrain.model.generator.UserPpvs;

public class PaymentPPVConverter implements IConverter<UserContentPPVDto, UserPpvs> {

	public UserContentPPVDto converter(UserPpvs source) {
		// TODO Auto-generated method stub
		return null;
	}

	public UserPpvs deConverter(UserContentPPVDto source) {
		UserPpvs userPpvs = new UserPpvs();
		userPpvs.setDatePayment(source.getDatePayment());
		return userPpvs;
	}

	public List<UserContentPPVDto> convertAll(List<UserPpvs> source) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<UserPpvs> deConvertAll(List<UserContentPPVDto> source) {
		List<UserPpvs> list = new ArrayList<UserPpvs>();
		for(UserContentPPVDto userContentPPVDto : source) {
			list.add(deConverter(userContentPPVDto));
		}
		return list;
	}

}
