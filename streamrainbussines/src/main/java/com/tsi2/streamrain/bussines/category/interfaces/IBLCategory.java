package com.tsi2.streamrain.bussines.category.interfaces;

import com.tsi2.streamrain.model.generator.Categories;

public interface IBLCategory {
	
	public boolean saveCategory(final Categories c, final String tenantID);

}
