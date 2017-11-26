package com.tsi2.streamrain.converters.content;

import java.util.List;

import com.tsi2.streamrain.converters.interfaces.IConverter;
import com.tsi2.streamrain.datatypes.content.UserContentViewDto;
import com.tsi2.streamrain.model.generator.UserViews;

public class UserContentViewConverter implements IConverter<UserContentViewDto, UserViews>{

	public UserContentViewDto converter(UserViews source) {
		UserContentViewDto userContentViewDto = new UserContentViewDto();
		userContentViewDto.setId(source.getId());
		userContentViewDto.setContentID(source.getContents().getId());
		userContentViewDto.setDateFinish(source.getDateFinish());
		userContentViewDto.setDateStart(source.getDateStart());
		userContentViewDto.setSecond(source.getSecond());
		userContentViewDto.setUserNickname(source.getUsers().getNickname());
		return userContentViewDto;
	}

	public UserViews deConverter(UserContentViewDto source) {
		UserViews userContentView = new UserViews();
		userContentView.setDateFinish(source.getDateFinish());
		userContentView.setDateStart(source.getDateStart());
		userContentView.setSecond(source.getSecond());
		userContentView.setId(source.getId());
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
