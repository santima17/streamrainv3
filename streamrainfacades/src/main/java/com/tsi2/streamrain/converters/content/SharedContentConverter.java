package com.tsi2.streamrain.converters.content;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.tsi2.streamrain.converters.interfaces.IConverter;
import com.tsi2.streamrain.datatypes.content.SharedContentViewDto;
import com.tsi2.streamrain.model.generator.SharedContents;

public class SharedContentConverter implements IConverter<SharedContentViewDto, SharedContents> {

	public SharedContentViewDto converter(SharedContents source) {
		SharedContentViewDto target = new SharedContentViewDto();
		target.setContentId(source.getContents().getId());
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		target.setDateShared(formatter.format(source.getDate()));
		target.setDate(source.getDate());
		target.setId(source.getId());
		target.setUsersByDestinationUserId(source.getUsersByDestinationUserId().getNickname());
		target.setUsersByUserId(source.getUsersByUserId().getNickname());
		return target;
	}

	public SharedContents deConverter(SharedContentViewDto source) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<SharedContentViewDto> convertAll(List<SharedContents> source) {
		List<SharedContentViewDto> dtoList = new ArrayList<SharedContentViewDto>();
		if (source != null) {
			for(SharedContents content : source) {
				dtoList.add(converter(content));
			}
		}
		return dtoList;
	}

	public List<SharedContents> deConvertAll(List<SharedContentViewDto> source) {
		// TODO Auto-generated method stub
		return null;
	}

}
