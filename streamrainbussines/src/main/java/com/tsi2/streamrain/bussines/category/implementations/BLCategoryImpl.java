package com.tsi2.streamrain.bussines.category.implementations;

import java.util.List;

import com.tsi2.streamrain.bussines.category.interfaces.IBLCategory;
import com.tsi2.streamrain.context.StremRainDataContextLoader;
import com.tsi2.streamrain.dao.implementations.StreamRainMySQLDAO;
import com.tsi2.streamrain.dao.interfaces.IDAOService;
import com.tsi2.streamrain.model.generator.Categories;

public class BLCategoryImpl implements IBLCategory {

	public boolean saveCategory(Categories c, String tenantID) {
		IDAOService daoService = (StreamRainMySQLDAO) StremRainDataContextLoader.contextLoader().getBean("daoUserService");
		daoService.save(c, tenantID);
		return true; 
	}

	public List<Categories> loadAllCategories(String tenantID) {
		IDAOService daoService = (StreamRainMySQLDAO) StremRainDataContextLoader.contextLoader().getBean("daoUserService");
		return daoService.getAll(Categories.class, tenantID);
	}

}
