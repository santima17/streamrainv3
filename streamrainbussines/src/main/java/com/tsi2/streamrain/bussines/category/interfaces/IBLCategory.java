package com.tsi2.streamrain.bussines.category.interfaces;

import java.util.List;

import com.tsi2.streamrain.model.generator.Categories;

public interface IBLCategory {
	
	boolean saveCategory(final Categories c, final String tenantID);
	
	List<Categories> loadAllCategories(final String tenantID);

}
