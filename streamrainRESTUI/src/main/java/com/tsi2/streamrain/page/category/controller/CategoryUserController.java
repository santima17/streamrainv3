package com.tsi2.streamrain.page.category.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tsi2.streamrain.datatypes.category.CategoryDto;
import com.tsi2.streamrain.services.category.interfaces.ICategoryService;
import com.tsi2.streamrain.services.session.interfaces.ISessionService;

@RestController
@RequestMapping("/user/category")
public class CategoryUserController {
	
	@Autowired
	ICategoryService categoryService;
	
	@Autowired
	ISessionService sessionService;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAllCategories(sessionService.getCurrentTenant());
    }

}
