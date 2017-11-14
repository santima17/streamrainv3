package com.tsi2.streamrain.converters.janus;

import java.util.ArrayList;
import java.util.List;

import com.tsi2.streamrain.converters.interfaces.IConverter;
import com.tsi2.streamrain.datatypes.janus.JanusLiveOnlyInfoDto;
import com.tsi2.streamrain.model.generator.Contents;

public class JanusLiveContentConverter implements IConverter<JanusLiveOnlyInfoDto, Contents> {

	private static final String RTP = "rtp";

	public JanusLiveOnlyInfoDto converter(Contents source) {
		JanusLiveOnlyInfoDto janusLiveOnlyInfoDto = new JanusLiveOnlyInfoDto();
		janusLiveOnlyInfoDto.setAudio(source.getLiveOnlyContents().isJanusAudio());
		janusLiveOnlyInfoDto.setAudioport(source.getLiveOnlyContents().getJanusAudioPort());
		janusLiveOnlyInfoDto.setAudiopt(source.getLiveOnlyContents().getJanusAudioPt());
		janusLiveOnlyInfoDto.setDescription(source.getDescription());
		janusLiveOnlyInfoDto.setPermanent(true);
		janusLiveOnlyInfoDto.setId(source.getId());
		janusLiveOnlyInfoDto.setPin(source.getLiveOnlyContents().getJanusPin());
		janusLiveOnlyInfoDto.setType(RTP);
		janusLiveOnlyInfoDto.setVideo(source.getLiveOnlyContents().isJanusVideo());
		janusLiveOnlyInfoDto.setVideoport(source.getLiveOnlyContents().getJanusVideoPort());
		janusLiveOnlyInfoDto.setVideortpmap(source.getLiveOnlyContents().getJanusVideoRtpMap());
		return janusLiveOnlyInfoDto;
	}

	public Contents deConverter(JanusLiveOnlyInfoDto source) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<JanusLiveOnlyInfoDto> convertAll(List<Contents> source) {
		List<JanusLiveOnlyInfoDto> dtoList = new ArrayList<JanusLiveOnlyInfoDto>();
		for(Contents live : source) {
			dtoList.add(converter(live));
		}
		return dtoList;
	}

	public List<Contents> deConvertAll(List<JanusLiveOnlyInfoDto> source) {
		// TODO Auto-generated method stub
		return null;
	}

}
