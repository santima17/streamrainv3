package com.tsi2.streamrain.converters.content;

import java.util.ArrayList;
import java.util.List;

import com.tsi2.streamrain.converters.interfaces.IConverter;
import com.tsi2.streamrain.datatypes.content.ContentCastDto;
import com.tsi2.streamrain.model.generator.ContentCasts;
import com.tsi2.streamrain.model.generator.ContentCastsId;

public class ContentCastConverter implements IConverter<ContentCastDto, ContentCasts>{

	public ContentCastDto converter(ContentCasts source) {
		// TODO Auto-generated method stub
		return null;
	}

	public ContentCasts deConverter(ContentCastDto source) {
		ContentCasts contentsCast = new ContentCasts();
		/*ContentCastsId contentCastId = new ContentCastsId();
		contentCastId.setFirstName(source.getFirstName());
		contentCastId.setLastName(source.getLastName());
		contentsCast.setId(contentCastId);
		contentsCast.setIsActor(source.getIsActor());
		contentsCast.setIsDirector(source.getIsDirector());*/
		return contentsCast;
	}

	public List<ContentCastDto> convertAll(List<ContentCasts> source) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ContentCasts> deConvertAll(List<ContentCastDto> source) {
		List<ContentCasts> dtoList = new ArrayList<ContentCasts>();
		for(ContentCastDto contentCast : source) {
			dtoList.add(deConverter(contentCast));
		}
		return dtoList;
	}

	

}
