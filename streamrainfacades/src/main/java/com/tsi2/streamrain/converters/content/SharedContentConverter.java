package com.tsi2.streamrain.converters.content;

import java.util.List;

import com.tsi2.streamrain.converters.interfaces.IConverter;
import com.tsi2.streamrain.datatypes.content.SharedContentViewDto;
import com.tsi2.streamrain.model.generator.SharedContents;

public class SharedContentConverter implements IConverter<SharedContentViewDto, SharedContents> {

	public SharedContentViewDto converter(SharedContents source) {
		SharedContentViewDto target = new SharedContentViewDto();
		target.setContentId(source.getContents().getId());
		target.setDate(source.getDate());
		target.setId(source.getId());
		target.setUsersByDestinationUserId(source.getUsersByDestinationUserId().getId());
		target.setUsersByUserId(source.getUsersByUserId().getId());
		return target;
	}

	public SharedContents deConverter(SharedContentViewDto source) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<SharedContentViewDto> convertAll(List<SharedContents> source) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<SharedContents> deConvertAll(List<SharedContentViewDto> source) {
		// TODO Auto-generated method stub
		return null;
	}

}