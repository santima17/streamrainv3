package com.tsi2.streamrain.dao.interfaces;

public interface IDAOMongoDBService {
	
	public Integer saveChatMessageToContent(Integer contentID, String userNickname, String text, boolean delete,
			String tenantID);

}
