package com.tsi2.streamrain.dao.implementations;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.tsi2.streamrain.dao.interfaces.IDAOMongoDBService;


public class StreamRainMongoDbDAO implements IDAOMongoDBService {

	public Integer saveChatMessageToContent(final Integer idJanusServer, final String jsonChatMessage, final String tenantID) {
		try {
			MongoClient mongo = new MongoClient("localhost", 27017);
			DB db = mongo.getDB(tenantID);
			DBCollection table = db.getCollection("userChatMessage");
			BasicDBObject document = new BasicDBObject();
			document.put("janus_server", idJanusServer);
			document.put("chat_message", jsonChatMessage);
			table.insert(document);
			return 1;
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return 0;
		}
	}


}
