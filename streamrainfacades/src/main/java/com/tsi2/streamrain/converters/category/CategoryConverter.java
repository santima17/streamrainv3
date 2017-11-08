package com.tsi2.streamrain.converters.category;

import java.util.ArrayList;
import java.util.List;

import com.tsi2.streamrain.converters.interfaces.IConverter;
import com.tsi2.streamrain.datatypes.category.CategoryDto;
import com.tsi2.streamrain.datatypes.user.UserDto;
import com.tsi2.streamrain.model.generator.Categories;
import com.tsi2.streamrain.model.generator.Users;

public class CategoryConverter implements IConverter<CategoryDto, Categories> {

	public CategoryDto converter(Categories source) {
		CategoryDto catDto = new CategoryDto();
		catDto.setId(source.getId());
		catDto.setName(source.getName());
		catDto.setDescription(source.getDescription());
		catDto.setCoverPictureUrl(source.getCoverPictureUrl());
		return catDto;
	}

	public Categories deConverter(CategoryDto source) {
		Categories cat = new Categories();
		cat.setName(source.getName());
		cat.setDescription(source.getDescription());
		cat.setCoverPictureUrl(source.getCoverPictureUrl());
		return cat;
	}

	public List<CategoryDto> convertAll(List<Categories> source) {
		List<CategoryDto> dtoList = new ArrayList<CategoryDto>();
		for(Categories cat : source) {
			dtoList.add(converter(cat));
		}
		return dtoList;
	}

	public List<Categories> deConvertAll(List<CategoryDto> source) {
		List<Categories> list = new ArrayList<Categories>();
		for(CategoryDto cat : source) {
			list.add(deConverter(cat));
		}
		return list;
	}

}
