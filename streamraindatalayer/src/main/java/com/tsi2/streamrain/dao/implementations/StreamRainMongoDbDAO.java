package com.tsi2.streamrain.dao.implementations;

import java.net.UnknownHostException;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.tsi2.streamrain.dao.interfaces.IDAOMongoDBService;


public class StreamRainMongoDbDAO implements IDAOMongoDBService {

	public Integer saveChatMessageToContent(Integer contentID, String userNickname, String text, boolean delete,
			String tenantID) {
		try {
			MongoClient mongo = new MongoClient("localhost", 27017);
			DB db = mongo.getDB("streamrain");
			DBCollection table = db.getCollection("userChatMessage");
			BasicDBObject document = new BasicDBObject();
			document.put("userNickname", userNickname);
			document.put("contentID", contentID);
			document.put("text", text);
			document.put("date", new Date());
			document.put("isDeleted", delete);
			table.insert(document);
			return 1;
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return 0;
		}
	}


}
