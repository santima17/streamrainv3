package com.tsi2.streamrain.page.content.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.tsi.streamrain.datatypes.category.CategoryDto;
import com.tsi2.streamrain.datatypes.content.ContentCastDto;
import com.tsi2.streamrain.datatypes.content.ContentDto;
import com.tsi2.streamrain.services.category.interfaces.ICategoryService;
import com.tsi2.streamrain.services.content.interfaces.IContentService;
import com.tsi2.streamrain.services.tenants.interfaces.ITenantService;

@Controller
public class ContentController {
	
	@Value("${location.file.path}")
	private String location;
	
	@Autowired
	IContentService contentService;
	
	@Autowired
	ICategoryService categoryService;
	
	@Autowired
	ITenantService tenantService;
	
	private static final String CONTENT_PREFIX = "/generator/content/";
		
	@RequestMapping(value = "/{tenant}/portal/createContent", method = RequestMethod.GET)
	public String showCreateContent (@PathVariable("tenant") String tenant) {	
		tenantService.setCurrentTenant(tenant);
		return CONTENT_PREFIX + "createContent";
	}
	
	@RequestMapping(value = "/{tenant}/portal/createContentProcess", method = RequestMethod.POST)
    public String createContent(@PathVariable("tenant") String tenant, @Valid ContentDto contentDto, BindingResult result, Model model) {
		//if (result.hasErrors()) {
		//	return CONTENT_PREFIX + "createContent";
		//}
		try {
			contentDto.setTenantId(tenant);
			String pictureName = recordFile(contentDto.getPicture());
			contentDto.setCoverPictureUrl(pictureName);
			String videoName = recordFile(contentDto.getVideo());
			contentDto.setStorageUrl(videoName);
			if ("1".equals(contentDto.getType())) {
				contentDto.setType("Pelicula");
				contentDto.setAlwaysAvailable(true);
			}else if ("2".equals(contentDto.getType())) {
				contentDto.setType("Serie");
				contentDto.setAlwaysAvailable(true);
			}else if ("3".equals(contentDto.getType())) {
				contentDto.setType("Evento Deportivo");
				contentDto.setAlwaysAvailable(false);
			}else if ("4".equals(contentDto.getType())) {
				contentDto.setType("Evento Espectaculo");
				contentDto.setAlwaysAvailable(false);
			}
			contentService.saveContent(contentDto, tenant);
		} catch (Exception e) {
			e.printStackTrace();
			return "No se ha podido grabar el fichero";
		}
				
		return CONTENT_PREFIX + "createContent";
	}
	
	private String recordFile(MultipartFile uploaded) throws Exception {
				
		String pathFile = location+uploaded.getOriginalFilename();
    	File localFile = new File(pathFile);
    	FileOutputStream os = null;
    	try {
    		os = new FileOutputStream(localFile);
    		os.write(uploaded.getBytes());
    		return uploaded.getOriginalFilename();
    	} finally {
    		if (os != null) {
    			try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    	}
	}
	
	
	@ModelAttribute("categoriesOptionList")
	public List<CategoryDto> populateCategoriesList() {
		return categoryService.getAllCategories("generator1");
	}
	
	@ModelAttribute("contentsOptionList")
	public List<ContentDto> populateSimilarContentList() {
		return contentService.getAllContents("generator1");
	}
	
	@ModelAttribute("typeList")
	public Map<Integer, String> populateTypeList() {
		Map<Integer, String> types = new HashMap<>();
		types.put(1,"Pelicula");
		types.put(2,"Serie");
		types.put(3,"Evento Deportivo");
		types.put(4,"Evento Espectaculo");
		return types;
	}
	
	@ModelAttribute("contentDto")
	public ContentDto populateForm() {
		 ContentDto contentDto = new ContentDto();
		 ContentCastDto actor = new ContentCastDto();
		 actor.setFirstName("");
		 actor.setLastName("");
		 actor.setIsActor(true);
		 ContentCastDto actor2 = new ContentCastDto();
		 actor2.setFirstName("");
		 actor2.setLastName("");
		 actor2.setIsActor(true);
		 ContentCastDto actor3 = new ContentCastDto();
		 actor3.setFirstName("");
		 actor3.setLastName("");
		 actor3.setIsActor(true);
		 ContentCastDto actor4 = new ContentCastDto();
		 actor4.setFirstName("");
		 actor4.setLastName("");
		 actor4.setIsActor(true);
		 ContentCastDto actor5 = new ContentCastDto();
		 actor5.setFirstName("");
		 actor5.setLastName("");
		 actor5.setIsActor(true);
		 Set<ContentCastDto> actores = new HashSet<ContentCastDto>(Arrays.asList(actor,actor2, actor3, actor4, actor5));
		 contentDto.setActors(actores);
		 
		 ContentCastDto director = new ContentCastDto();
		 director.setFirstName("");
		 director.setLastName("");
		 director.setIsDirector(true);
		 ContentCastDto director2 = new ContentCastDto();
		 director2.setFirstName("");
		 director2.setLastName("");
		 director2.setIsDirector(true);
		 ContentCastDto director3 = new ContentCastDto();
		 director3.setFirstName("");
		 director3.setLastName("");
		 director3.setIsDirector(true);
		 Set<ContentCastDto> directores = new HashSet<ContentCastDto>(Arrays.asList(director,director2, director3));
		 contentDto.setDirectors(directores);
		 //contentDto.setDateStart(new Date());
		 //contentDto.setFeaturedDateStart(new Date());
		 //contentDto.setFeaturedDateFinish(new Date());
	     return contentDto;
	}

}
