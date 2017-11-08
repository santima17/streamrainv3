package com.tsi2.streamrain.services.category.interfaces;

import java.util.List;

import com.tsi2.streamrain.datatypes.category.CategoryDto;

public interface ICategoryService {

	boolean saveCategory(final CategoryDto c, final String tenantID);
	
	List<CategoryDto> getAllCategories(final String tenantID);
}
