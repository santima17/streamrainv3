package com.tsi2.streamrain.converters.content;

import java.util.List;

import com.tsi2.streamrain.converters.interfaces.IConverter;
import com.tsi2.streamrain.datatypes.content.ContentCastDto;
import com.tsi2.streamrain.datatypes.content.UserContentViewDto;
import com.tsi2.streamrain.model.generator.ContentCasts;
import com.tsi2.streamrain.model.generator.UserViews;

public class UserContentViewConverter implements IConverter<UserContentViewDto, UserViews>{

	public UserContentViewDto converter(UserViews source) {
		UserContentViewDto userContentViewDto = new UserContentViewDto();
		//userContentViewDto.setContentID(source.getId());
		userContentViewDto.setDateFinish(source.getDateFinish());
		userContentViewDto.setDateStart(source.getDateStart());
		userContentViewDto.setSecond(source.getSecond());
		
		//userContentViewDto.setContents(source.getContents());
		//userContentViewDto.setUsers(source.getUsers());
		return userContentViewDto;
	}

	public UserViews deConverter(UserContentViewDto source) {
		UserViews userContentView = new UserViews();
		userContentView.setDateFinish(source.getDateFinish());
		userContentView.setDateStart(source.getDateStart());
		userContentView.setSecond(source.getSecond());
		return userContentView;
	}

	public List<UserContentViewDto> convertAll(List<UserViews> source) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<UserViews> deConvertAll(List<UserContentViewDto> source) {
		// TODO Auto-generated method stub
		return null;
	}

}
