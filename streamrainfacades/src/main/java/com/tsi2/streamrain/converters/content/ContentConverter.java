package com.tsi2.streamrain.converters.content;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.tsi2.streamrain.converters.interfaces.IConverter;
import com.tsi2.streamrain.datatypes.content.ContentCastDto;
import com.tsi2.streamrain.datatypes.content.ContentDto;
import com.tsi2.streamrain.model.generator.AlwaysAvailableContents;
import com.tsi2.streamrain.model.generator.Categories;
import com.tsi2.streamrain.model.generator.ContentCasts;
import com.tsi2.streamrain.model.generator.Contents;
import com.tsi2.streamrain.model.generator.FeaturedContents;
import com.tsi2.streamrain.model.generator.LiveOnlyContents;

public class ContentConverter implements IConverter<ContentDto, Contents>{
	
	public ContentCastDto converter(ContentCasts source) {
		ContentCastDto contentsCast = new ContentCastDto();
		contentsCast.setFirstName(source.getFirstName());
		contentsCast.setLastName(source.getLastName());
		contentsCast.setIsActor(source.getIsActor());
		contentsCast.setIsDirector(source.getIsDirector());
		return contentsCast;
	}
	
	public ContentDto converter(Contents source) {
		ContentDto contentDto = new ContentDto();
		contentDto.setId(source.getId());
		contentDto.setCoverPictureUrl(source.getCoverPictureUrl());
		contentDto.setDescription(source.getDescription());
		contentDto.setIsPayPerView(source.getIsPayPerView());
		contentDto.setName(source.getName());
		contentDto.setStorageUrl(source.getStorageUrl());
		contentDto.setType(source.getType());
		Set<ContentCastDto> actors = new HashSet<ContentCastDto>();
		Set<ContentCastDto> directors = new HashSet<ContentCastDto>();
		Iterator<ContentCasts> iterator = source.getContentCastses().iterator();
	    while(iterator.hasNext()) {
	    	ContentCasts cast = iterator.next();
	    	if (cast.getIsActor() != null && cast.getIsActor()) {
	    		actors.add(converter(cast));
	    	}else {
	    		directors.add(converter(cast));
	    	}
	    }
	    contentDto.setActors(actors);
	    contentDto.setDirectors(directors);
	    
	    List<Integer> idCategoriesList = new ArrayList<Integer>();
	    Iterator<Categories> it = source.getCategorieses().iterator();
	    while (it.hasNext()) {
	    	Categories cat = it.next();
	    	idCategoriesList.add(cat.getId());
	    }
	    contentDto.setIdCategories(idCategoriesList);
	    
	    if (source.getAlwaysAvailableContents() != null) {
	    	contentDto.setAlwaysAvailable(true);
	    	contentDto.setDuration(source.getAlwaysAvailableContents().getDuration());
	    }else if (source.getLiveOnlyContents() != null){
	    	contentDto.setDateStart(source.getLiveOnlyContents().getDateStart());
	    	contentDto.setEstimatedDuraction(source.getLiveOnlyContents().getEstimatedDuraction());
	    	contentDto.setJanus_audio_pt(source.getLiveOnlyContents().getJanusAudioPt());
	    	contentDto.setJanus_audio_rtp_map(source.getLiveOnlyContents().getJanusAudioRtpMap());
	    	contentDto.setJanus_video_pt(source.getLiveOnlyContents().getJanusVideoPt());
	    	contentDto.setJanus_video_rtp_map(source.getLiveOnlyContents().getJanusVideoRtpMap());
	    	contentDto.setJanus_pin(source.getLiveOnlyContents().getJanusPin());
	    	contentDto.setJanus_audio_port(source.getLiveOnlyContents().getJanusAudioPort());
	    	contentDto.setJanus_video_port(source.getLiveOnlyContents().getJanusVideoPort());
	    	contentDto.setAlwaysAvailable(false); 	
	    }
	    
	    Iterator<FeaturedContents> iter = source.getFeaturedContentses().iterator();
	    while(iter.hasNext()) {
	    	FeaturedContents featuredContents = iter.next();
	    	contentDto.setFeatured(true);
	    	contentDto.setFeaturedDateStart(featuredContents.getDateStart());
	    	contentDto.setFeaturedDateFinish(featuredContents.getDateFinish());
	    	break;
	    }
		
		return contentDto;
	}
	
	private ContentCasts deConverter(ContentCastDto source) {
		ContentCasts contentsCast = new ContentCasts();
		contentsCast.setFirstName(source.getFirstName());
		contentsCast.setLastName(source.getLastName());
		contentsCast.setIsActor(source.getIsActor());
		contentsCast.setIsDirector(source.getIsDirector());
		return contentsCast;
	}
	
	private Set<ContentCasts> deConvertAllContentCasts(Set<ContentCastDto> source) {
		Set<ContentCasts> dtoList = new HashSet<ContentCasts>();
		if (source != null) {
			for(ContentCastDto contentCast : source) {
				if (!"".equals(contentCast.getFirstName()) && (!"".equals(contentCast.getLastName()))){
					dtoList.add(deConverter(contentCast));
				}
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
		
		for(ContentCasts cast:actors) {
			cast.setContents(contents);
		}
		contents.setContentCastses(actors);
				
		if (source.getAlwaysAvailable()) {
			AlwaysAvailableContents alwaysAvailableContents = new AlwaysAvailableContents();
			alwaysAvailableContents.setDuration(source.getDuration());
			contents.setAlwaysAvailableContents(alwaysAvailableContents);
			alwaysAvailableContents.setContents(contents);
		}else {
			LiveOnlyContents liveOnlyContents = new LiveOnlyContents();
			liveOnlyContents.setDateStart(source.getDateStart());
			liveOnlyContents.setEstimatedDuraction(source.getEstimatedDuraction());
			liveOnlyContents.setJanusAudioPt(source.getJanus_audio_pt());
			liveOnlyContents.setJanusAudioRtpMap(source.getJanus_audio_rtp_map());
			liveOnlyContents.setJanusVideoPt(source.getJanus_video_pt());
			liveOnlyContents.setJanusVideoRtpMap(source.getJanus_video_rtp_map());
			liveOnlyContents.setJanusAudio(true);
			liveOnlyContents.setJanusVideo(true);
			liveOnlyContents.setJanusPin(source.getJanus_pin());
			contents.setLiveOnlyContents(liveOnlyContents);
			liveOnlyContents.setContents(contents);
		}
		
		if (source.getFeatured() != null && source.getFeatured()) {
			FeaturedContents featuredContents = new FeaturedContents();
			featuredContents.setDateStart(source.getFeaturedDateStart());
			featuredContents.setDateFinish(source.getFeaturedDateFinish());
			featuredContents.setContents(contents);
			Set<FeaturedContents> featuresList = new HashSet<FeaturedContents>();
			featuresList.add(featuredContents);
			contents.setFeaturedContentses(featuresList);
		}
						
		return contents;
	}

	public List<ContentDto> convertAll(List<Contents> source) {
		List<ContentDto> dtoList = new ArrayList<ContentDto>();
		if (source != null) {
			for(Contents content : source) {
				dtoList.add(converter(content));
			}
		}
		return dtoList;
	}

	public List<Contents> deConvertAll(List<ContentDto> source) {
		// TODO Auto-generated method stub
		return null;
	}

}
