package com.tsi2.streamrain.services.category.interfaces;

import com.tsi.streamrain.datatypes.category.CategoryDto;

public interface ICategoryService {

	public boolean saveCategory(final CategoryDto c, final String tenantID);
}
