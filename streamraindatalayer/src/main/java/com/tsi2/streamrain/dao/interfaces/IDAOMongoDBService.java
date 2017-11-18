package com.tsi2.streamrain.dao.interfaces;

public interface IDAOMongoDBService {
	
	public Integer saveChatMessageToContent(final Integer idJanusServer, final String jsonChatMessage, String tenantID);

}
