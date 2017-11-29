package com.tsi2.streamrain.converters.content;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.tsi2.streamrain.converters.interfaces.IConverter;
import com.tsi2.streamrain.datatypes.content.UserContentCommentDto;
import com.tsi2.streamrain.model.generator.UserComments;

public class UserCommentConverter implements IConverter<UserContentCommentDto, UserComments>{

	public UserContentCommentDto converter(UserComments source) {
		UserContentCommentDto commentDto = new UserContentCommentDto();
		commentDto.setId(source.getId());
		commentDto.setContentID(source.getAlwaysAvailableContents().getContentId());
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		commentDto.setDate(formatter.format(source.getDate()));
		commentDto.setDelete(source.getIsDeleted());
		commentDto.setText(source.getText());
		commentDto.setUserNickname(source.getUsers().getNickname());
		return commentDto;
	}

	public UserComments deConverter(UserContentCommentDto source) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<UserContentCommentDto> convertAll(List<UserComments> source) {
		List<UserContentCommentDto> dtoList = new ArrayList<UserContentCommentDto>();
		for(UserComments comments : source) {
			dtoList.add(converter(comments));
		}
		return dtoList;
	}

	public List<UserComments> deConvertAll(List<UserContentCommentDto> source) {
		// TODO Auto-generated method stub
		return null;
	}

}
