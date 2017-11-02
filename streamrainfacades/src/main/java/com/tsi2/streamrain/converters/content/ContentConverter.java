package com.tsi2.streamrain.converters.content;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tsi2.streamrain.converters.interfaces.IConverter;
import com.tsi2.streamrain.datatypes.content.ContentCastDto;
import com.tsi2.streamrain.datatypes.content.ContentDto;
import com.tsi2.streamrain.model.generator.AlwaysAvailableContents;
import com.tsi2.streamrain.model.generator.ContentCasts;
import com.tsi2.streamrain.model.generator.ContentCastsId;
import com.tsi2.streamrain.model.generator.Contents;
import com.tsi2.streamrain.model.generator.LiveOnlyContents;

public class ContentConverter implements IConverter<ContentDto, Contents>{

	//IConverter contentCastConverter = (ContentCastConverter)StremRainFacadesContextLoader.contextLoader().getBean("contentCastConverter");
	
	public ContentDto converter(Contents source) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private ContentCasts deConverter(ContentCastDto source) {
		ContentCasts contentsCast = new ContentCasts();
		ContentCastsId contentCastId = new ContentCastsId();
		contentCastId.setFirstName(source.getFirstName());
		contentCastId.setLastName(source.getLastName());
		contentsCast.setId(contentCastId);
		contentsCast.setIsActor(source.getIsActor());
		contentsCast.setIsDirector(source.getIsDirector());
		return contentsCast;
	}
	
	private Set<ContentCasts> deConvertAllContentCasts(Set<ContentCastDto> source) {
		Set<ContentCasts> dtoList = new HashSet<ContentCasts>();
		for(ContentCastDto contentCast : source) {
			if (!"".equals(contentCast.getFirstName()) && (!"".equals(contentCast.getLastName()))){
				dtoList.add(deConverter(contentCast));
			}
		}
		return dtoList;
	}

	public Contents deConverter(ContentDto source) {
		Contents contents = new Contents();
		contents.setCoverPictureUrl(source.getCoverPictureUrl());
		contents.setDescription(source.getDescription());
		contents.setIsPayPerView(source.getIsPayPerView());
		contents.setName(source.getName());
		contents.setStorageUrl(source.getStorageUrl());
		contents.setType(source.getType());
		Set<ContentCasts> actors = deConvertAllContentCasts(source.getActors());
		Set<ContentCasts> directors = deConvertAllContentCasts(source.getDirectors());
		actors.addAll(directors);
		//contents.setContentCastses(new HashSet<ContentCasts>());
		//for(ContentCasts cast:actors) {
		//	cast.setContents(contents);
			//contents.getContentCastses().add(cast);
		//}
		//contents.setContentCastses(actors);
		
		if (source.getAlwaysAvailable()) {
			AlwaysAvailableContents alwaysAvailableContents = new AlwaysAvailableContents();
			alwaysAvailableContents.setDuration(source.getDuration());
			contents.setAlwaysAvailableContents(alwaysAvailableContents);
			alwaysAvailableContents.setContents(contents);
		}else {
			LiveOnlyContents liveOnlyContents = new LiveOnlyContents();
			liveOnlyContents.setDateStart(source.getDateStart());
			liveOnlyContents.setEstimatedDuraction(source.getEstimatedDuraction());
			contents.setLiveOnlyContents(liveOnlyContents);
			//liveOnlyContents.setContents(contents);
		}
				
		return contents;
	}

	public List<ContentDto> convertAll(List<Contents> source) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Contents> deConvertAll(List<ContentDto> source) {
		// TODO Auto-generated method stub
		return null;
	}

}
