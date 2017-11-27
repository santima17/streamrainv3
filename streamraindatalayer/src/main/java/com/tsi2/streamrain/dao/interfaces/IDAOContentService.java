package com.tsi2.streamrain.dao.interfaces;

import com.tsi2.streamrain.model.generator.UserViews;

public interface IDAOContentService extends IDAOService{
	
	int updateContentView(final UserViews userViews, final String tenantID);

}
