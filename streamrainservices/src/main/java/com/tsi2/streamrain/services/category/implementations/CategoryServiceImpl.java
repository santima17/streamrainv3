package com.tsi2.streamrain.services.category.implementations;

import java.util.List;

import com.tsi2.streamrain.bussines.category.implementations.BLCategoryImpl;
import com.tsi2.streamrain.bussines.category.interfaces.IBLCategory;
import com.tsi2.streamrain.context.StremRainFacadesContextLoader;
import com.tsi2.streamrain.context.StremRainUserBussinesContextLoader;
import com.tsi2.streamrain.converters.category.CategoryConverter;
import com.tsi2.streamrain.converters.interfaces.IConverter;
import com.tsi2.streamrain.datatypes.category.CategoryDto;
import com.tsi2.streamrain.model.generator.Categories;
import com.tsi2.streamrain.services.category.interfaces.ICategoryService;

public class CategoryServiceImpl implements ICategoryService {
	
	IBLCategory categoryBussines = (BLCategoryImpl) StremRainUserBussinesContextLoader.contextLoader().getBean("categoryBussines");
	IConverter catConverter = (CategoryConverter) StremRainFacadesContextLoader.contextLoader().getBean("categoryConverter");

	public boolean saveCategory(final CategoryDto c, final String tenantID) {
		categoryBussines.saveCategory((Categories) catConverter.deConverter(c), tenantID);
		return true;
	}

	public List<CategoryDto> getAllCategories(final String tenantID) {
		List<Categories> list = categoryBussines.loadAllCategories(tenantID);
		return (List<CategoryDto>) catConverter.convertAll(list);
	}

}
