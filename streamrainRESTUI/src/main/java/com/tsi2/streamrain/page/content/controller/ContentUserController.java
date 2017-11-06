package com.tsi2.streamrain.page.content.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tsi2.streamrain.datatypes.content.ContentDto;
import com.tsi2.streamrain.datatypes.content.ContentVoteDto;
import com.tsi2.streamrain.datatypes.content.UserContentCommentDto;
import com.tsi2.streamrain.datatypes.content.UserContentFavDto;
import com.tsi2.streamrain.services.category.interfaces.ICategoryService;
import com.tsi2.streamrain.services.content.interfaces.IContentService;
import com.tsi2.streamrain.services.session.interfaces.ISessionService;

@RestController
@RequestMapping("/user/content")
public class ContentUserController {
	
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
    
    @RequestMapping(value = "/voteContent", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContentVoteDto> voteContent(@RequestBody ContentVoteDto contentVoteDto) {
    	boolean voteOk = contentService.voteContent(contentVoteDto.getContentID(), contentVoteDto.getUserNickname(), contentVoteDto.getRank(), sessionService.getCurrentTenant());
        ResponseEntity<ContentVoteDto> response;
        if (voteOk) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
		return response;
    }
    
    @RequestMapping(value = "/addContentToFav", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserContentFavDto> voteContent(@RequestBody UserContentFavDto userContentFavDto) {
    	boolean voteOk = contentService.addContentToFav(userContentFavDto, sessionService.getCurrentTenant());
        ResponseEntity<UserContentFavDto> response;
        if (voteOk) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
		return response;
    }
    
    @RequestMapping(value = "/addCommentToContent", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserContentCommentDto> addCommentToContent(@RequestBody UserContentCommentDto userContentCommentDto) {
    	boolean voteOk = contentService.addCommentToContent(userContentCommentDto, sessionService.getCurrentTenant());
        ResponseEntity<UserContentCommentDto> response;
        if (voteOk) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
		return response;
    }
    
    
    
    
}
