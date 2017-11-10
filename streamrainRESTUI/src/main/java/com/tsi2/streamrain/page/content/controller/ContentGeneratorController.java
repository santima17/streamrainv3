package com.tsi2.streamrain.page.content.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsi2.streamrain.datatypes.category.CategoryDto;
import com.tsi2.streamrain.datatypes.content.ContentCastDto;
import com.tsi2.streamrain.datatypes.content.ContentDto;
import com.tsi2.streamrain.datatypes.user.UserDto;
import com.tsi2.streamrain.services.category.interfaces.ICategoryService;
import com.tsi2.streamrain.services.content.interfaces.IContentService;
import com.tsi2.streamrain.services.session.interfaces.ISessionService;

@RestController
@RequestMapping("/generator/createContent")
public class ContentGeneratorController {
	
	@Value("${location.file.path}")
	private String location;
	
	@Autowired
	IContentService contentService;
	
	@Autowired
	ICategoryService categoryService;
	
	@Autowired
	ISessionService sessionService;
	
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ContentDto> getAllUsers() {
        return contentService.getAllContents(sessionService.getCurrentTenant());
    }
	
    @RequestMapping(value = "/{contentID}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContentDto> getContent(@PathVariable Integer contentID) {
    	ContentDto content = contentService.getContentById(contentID, sessionService.getCurrentTenant());
        ResponseEntity<ContentDto> response;
        if (content == null) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            response = new ResponseEntity<>(content, HttpStatus.OK);
        }
        return response;
    }
    
    /*@RequestMapping(value = "/uploadPicture", method = RequestMethod.POST)
    public ResponseEntity<BindingResult> uploadImagen(@RequestParam("picture") MultipartFile picture, @RequestParam("video") MultipartFile video, @RequestPart("datos") String datos) {
    	ResponseEntity<BindingResult> result = new ResponseEntity<>(HttpStatus.CREATED);
    	ContentDto content;
    	try {
			content =  new ObjectMapper().readValue(datos, ContentDto.class);
			/*Iterator<String> itr =  request.getFileNames();

    	    MultipartFile picture = request.getFile(itr.next());
    	    MultipartFile video = request.getFile(itr.next());
    	    MultipartFile data = request.getFile(itr.next());*/ 
    	        	    
			/*String pictureName = recordFile(picture);
			String videoName = recordFile(video);
		} catch (JsonParseException e1) {
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();    		
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return result;
    }*/
    
	@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<BindingResult> insertContent(@RequestParam("picture") MultipartFile picture, @RequestParam("video") MultipartFile video, @RequestPart("datos") String datos, BindingResult result) {
    	ResponseEntity<BindingResult> response = new ResponseEntity<>(HttpStatus.CREATED);
    	    	
    	if (result.hasErrors()) {
    		return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    	}
    	ContentDto contentDto;
		try {
			contentDto =  new ObjectMapper().readValue(datos, ContentDto.class);
			
			contentDto.setTenantId(sessionService.getCurrentTenant());
			String pictureName = recordFile(picture);
			contentDto.setCoverPictureUrl(pictureName);
			String videoName = recordFile(video);
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
			contentService.saveContent(contentDto, sessionService.getCurrentTenant());
			return response;
		} catch (JsonParseException e1) {
			e1.printStackTrace();
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		} catch (IOException e1) {
			e1.printStackTrace();    		
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
    }
	
    @RequestMapping(value = "/{contentID}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<BindingResult> updateUser(@PathVariable Integer contentID, @RequestBody @Valid ContentDto contentDto, BindingResult result) {
    	ResponseEntity<BindingResult> response = new ResponseEntity<>(HttpStatus.CREATED);
    	if (result.hasErrors()) {
    		return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    	}
    	ContentDto contentOld = contentService.getContentById(contentID, sessionService.getCurrentTenant());
        if (contentOld == null) {
        	return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }else {
        	try {
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
        	}catch (Exception e) {
    			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    		}
        	contentService.updateContent(contentDto, sessionService.getCurrentTenant());
        }
        return response;
    }
    
    @RequestMapping(value = "/{contentID}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContent(@PathVariable Integer contentID) {
    	contentService.deleteContent(contentID, sessionService.getCurrentTenant());
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
	
//	
//	@ModelAttribute("categoriesOptionList")
//	public List<CategoryDto> populateCategoriesList() {
//		return categoryService.getAllCategories("generator1");
//	}
//	
//	@ModelAttribute("contentsOptionList")
//	public List<ContentDto> populateSimilarContentList() {
//		return contentService.getAllContents("generator1");
//	}
//	
//	@ModelAttribute("typeList")
//	public Map<Integer, String> populateTypeList() {
//		Map<Integer, String> types = new HashMap<>();
//		types.put(1,"Pelicula");
//		types.put(2,"Serie");
//		types.put(3,"Evento Deportivo");
//		types.put(4,"Evento Espectaculo");
//		return types;
//	}
//	
//	@ModelAttribute("contentDto")
//	public ContentDto populateForm() {
//		 ContentDto contentDto = new ContentDto();
//		 ContentCastDto actor = new ContentCastDto();
//		 actor.setFirstName("");
//		 actor.setLastName("");
//		 actor.setIsActor(true);
//		 ContentCastDto actor2 = new ContentCastDto();
//		 actor2.setFirstName("");
//		 actor2.setLastName("");
//		 actor2.setIsActor(true);
//		 ContentCastDto actor3 = new ContentCastDto();
//		 actor3.setFirstName("");
//		 actor3.setLastName("");
//		 actor3.setIsActor(true);
//		 ContentCastDto actor4 = new ContentCastDto();
//		 actor4.setFirstName("");
//		 actor4.setLastName("");
//		 actor4.setIsActor(true);
//		 ContentCastDto actor5 = new ContentCastDto();
//		 actor5.setFirstName("");
//		 actor5.setLastName("");
//		 actor5.setIsActor(true);
//		 Set<ContentCastDto> actores = new HashSet<ContentCastDto>(Arrays.asList(actor,actor2, actor3, actor4, actor5));
//		 contentDto.setActors(actores);
//		 
//		 ContentCastDto director = new ContentCastDto();
//		 director.setFirstName("");
//		 director.setLastName("");
//		 director.setIsDirector(true);
//		 ContentCastDto director2 = new ContentCastDto();
//		 director2.setFirstName("");
//		 director2.setLastName("");
//		 director2.setIsDirector(true);
//		 ContentCastDto director3 = new ContentCastDto();
//		 director3.setFirstName("");
//		 director3.setLastName("");
//		 director3.setIsDirector(true);
//		 Set<ContentCastDto> directores = new HashSet<ContentCastDto>(Arrays.asList(director,director2, director3));
//		 contentDto.setDirectors(directores);
//		 //contentDto.setDateStart(new Date());
//		 //contentDto.setFeaturedDateStart(new Date());
//		 //contentDto.setFeaturedDateFinish(new Date());
//	     return contentDto;
//	}
	
//	private static final String CONTENT_PREFIX = "/generator/content/";
//	
//@RequestMapping(value = "/{tenant}/portal/createContent", method = RequestMethod.GET)
//public String showCreateContent (@PathVariable("tenant") String tenant) {	
//	sessionService.setCurrentTenant(tenant);
//	return CONTENT_PREFIX + "createContent";
//}

}
