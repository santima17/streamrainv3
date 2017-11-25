package com.tsi2.streamrain.page.content.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.tsi2.streamrain.datatypes.content.SharedContentViewDto;
import com.tsi2.streamrain.datatypes.content.SpoilerMarkDto;
import com.tsi2.streamrain.datatypes.content.UserContentCommentDto;
import com.tsi2.streamrain.datatypes.content.UserContentFavDto;
import com.tsi2.streamrain.datatypes.content.UserContentViewDto;
import com.tsi2.streamrain.services.category.interfaces.ICategoryService;
import com.tsi2.streamrain.services.content.interfaces.IContentService;
import com.tsi2.streamrain.services.payment.interfaces.IPaymentService;
import com.tsi2.streamrain.services.session.interfaces.ISessionService;
import com.tsi2.streamrain.springmvc.model.PathTokenVODDto;
import com.tsi2.streamrain.utils.Utils;

@RestController
@RequestMapping("/user/content")
public class ContentUserController {
	
	@Value("${location.file.path.docker.vod}")
	private String locationDockerVOD;
	
	@Autowired
	IContentService contentService;
	
	@Autowired
	ICategoryService categoryService;
	
	@Autowired
	ISessionService sessionService;
	
	@Autowired
	IPaymentService paymentService;
		
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ContentDto> getAllContent() {
        return contentService.getAllContents(sessionService.getCurrentTenant());
    }
	
    @RequestMapping(value = "/{contentID}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContentDto> getContentToView(@PathVariable Integer contentID) {
    	ContentDto content = contentService.getContentById(contentID, sessionService.getCurrentTenant());
        ResponseEntity<ContentDto> response;
        if (content == null) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            response = new ResponseEntity<ContentDto>(content, HttpStatus.OK);
        }
        return response;
    }
    
    @RequestMapping(value = "/view/{contentID}/{userNickName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PathTokenVODDto> getContent(@PathVariable Integer contentID, @PathVariable String userNickName) {
    	Long days = paymentService.getDaysValidSubscription(userNickName, sessionService.getCurrentTenant());

        ResponseEntity<PathTokenVODDto> response;
        if (days == null) {
            response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
        	ContentDto contentDto = contentService.getContentById(contentID, sessionService.getCurrentTenant());
        	String pathTokenVOD = Utils.obtainPathTokenVOD(contentDto.getStorageUrl(), days.intValue());
        	PathTokenVODDto dto = new PathTokenVODDto();
        	dto.setPathTokenVOD(locationDockerVOD + pathTokenVOD);
            response = new ResponseEntity<PathTokenVODDto>(dto, HttpStatus.OK);
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
    
    @RequestMapping(value = "/rank/{contentID}/{userNickName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PathTokenVODDto> rankOfUser(@PathVariable Integer contentID, @PathVariable String userNickName) {
    	Integer rank = contentService.getContentRaitingOfUser(contentID, userNickName, sessionService.getCurrentTenant());
    	PathTokenVODDto value = new PathTokenVODDto();
    	value.setPathTokenVOD(rank.toString());
    	return new ResponseEntity<PathTokenVODDto>(value, HttpStatus.OK);
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
    
    @RequestMapping(value = "/shareContent", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SharedContentViewDto> shareContent(@RequestBody SharedContentViewDto sharedContent) {
    	boolean ok = contentService.shareContent(sharedContent, sessionService.getCurrentTenant());
    	if (ok) {
    		return new ResponseEntity<>(HttpStatus.OK);
    	}else {
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    }
    
    @RequestMapping(value = "/getShareContent/{userNickName}/{searchType}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SharedContentViewDto>> getShareContent(@PathVariable String userNickName, @PathVariable Integer searchType) {
    	List<SharedContentViewDto> sharedContent = contentService.getShareContent(userNickName, searchType, sessionService.getCurrentTenant());
    	if (sharedContent != null && !sharedContent.isEmpty()) {
    		return new ResponseEntity<>(sharedContent, HttpStatus.OK);
    	}else {
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    }
        
}
