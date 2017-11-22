package com.tsi2.streamraingenerdor.page.content.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tsi2.streamrain.datatypes.content.ContentDto;
import com.tsi2.streamrain.datatypes.content.ContentVoteDto;
import com.tsi2.streamrain.datatypes.content.SpoilerMarkDto;
import com.tsi2.streamrain.datatypes.content.UserContentCommentDto;
import com.tsi2.streamrain.datatypes.content.UserContentFavDto;
import com.tsi2.streamrain.datatypes.content.UserContentViewDto;
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
    public List<ContentDto> getAllContent() {
        return contentService.getAllContents(sessionService.getCurrentTenant());
    }
	
    @RequestMapping(value = "/{contentID}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContentDto> getContent(@PathVariable Integer contentID) {
    	ContentDto content = contentService.getContentById(contentID, sessionService.getCurrentTenant());
        ResponseEntity<ContentDto> response;
        if (content == null) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            response = new ResponseEntity<ContentDto>(content, HttpStatus.OK);
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
    
    @RequestMapping(value = "/rank/{contentID}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Double> rank(@PathVariable Integer contentID) {
    	Double rank = contentService.getContentRaiting(contentID, sessionService.getCurrentTenant());
        return new ResponseEntity<Double>(rank, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/addContentToFav", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserContentFavDto> voteContent(@RequestBody UserContentFavDto userContentFavDto) {
    	boolean addOk = contentService.addContentToFav(userContentFavDto, sessionService.getCurrentTenant());
        ResponseEntity<UserContentFavDto> response;
        if (addOk) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
		return response;
    }
    
    @RequestMapping(value = "/removeContentToFav", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserContentFavDto> removeVoteContent(@RequestBody UserContentFavDto userContentFavDto) {
    	boolean removeOk = contentService.removeContentOfFav(userContentFavDto, sessionService.getCurrentTenant());
        ResponseEntity<UserContentFavDto> response;
        if (removeOk) {
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
        
    @RequestMapping(value = "/addViewToContent", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserContentViewDto> addViewToContent(@RequestBody UserContentViewDto userContentViewDto) {
    	boolean voteOk = contentService.addViewToContent(userContentViewDto, sessionService.getCurrentTenant());
        ResponseEntity<UserContentViewDto> response;
        if (voteOk) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
		return response;
    }
    
    @RequestMapping(value = "/getLastViewOfContent", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserContentViewDto> getLastViewOfContent(@RequestBody UserContentViewDto userContentViewDto) {
    	UserContentViewDto lastViewDto = contentService.getLastViewToContent(userContentViewDto, sessionService.getCurrentTenant());
        return new ResponseEntity<UserContentViewDto>(lastViewDto, HttpStatus.OK);
    }
    
    
    @RequestMapping(value = "/spolierComment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SpoilerMarkDto> spolierMarkComment(@RequestParam String userNickName, @RequestParam Integer userCommentId) {
    	boolean ok = contentService.spolierMarkComment(userNickName, userCommentId, sessionService.getCurrentTenant());
    	if (ok) {
    		return new ResponseEntity<>(HttpStatus.OK);
    	}else {
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    }
        
}
