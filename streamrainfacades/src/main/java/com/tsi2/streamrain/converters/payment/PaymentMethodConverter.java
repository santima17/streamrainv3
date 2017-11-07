package com.tsi2.streamrain.converters.payment;

import java.util.ArrayList;
import java.util.List;

import com.tsi2.streamrain.converters.interfaces.IConverter;
import com.tsi2.streamrain.datatypes.payment.PaymentMethodDto;
import com.tsi2.streamrain.model.generator.PaymentMethods;

public class PaymentMethodConverter implements IConverter<PaymentMethodDto, PaymentMethods> {

	public PaymentMethodDto converter(PaymentMethods source) {
		PaymentMethodDto paymentMethodDto = new PaymentMethodDto();
		paymentMethodDto.setId(source.getId());
		paymentMethodDto.setName(source.getName());
		return paymentMethodDto;
	}

	public PaymentMethods deConverter(PaymentMethodDto source) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<PaymentMethodDto> convertAll(List<PaymentMethods> source) {
		List<PaymentMethodDto> dtoList = new ArrayList<PaymentMethodDto>();
		for(PaymentMethods paymentMethod : source) {
			dtoList.add(converter(paymentMethod));
		}
		return dtoList;
	}

	public List<PaymentMethods> deConvertAll(List<PaymentMethodDto> source) {
		// TODO Auto-generated method stub
		return null;
	}

}
