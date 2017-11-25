package com.tsi2.streamrain.page.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tsi2.streamrain.datatypes.user.UserDto;
import com.tsi2.streamrain.page.general.controller.AbstractController;
import com.tsi2.streamrain.security.user.User;
import com.tsi2.streamrain.services.session.interfaces.ISessionService;
import com.tsi2.streamrain.services.tenants.interfaces.ITenantService;
import com.tsi2.streamrain.services.user.interfaces.IUserService;
import com.tsi2.streamrain.springmvc.model.PathTokenVODDto;

@Controller
public class SocialRedController extends AbstractController{
	
	@Autowired
	TwitterConnectionFactory connectionFactoryTwitter;
	
	@Autowired
	IUserService userService;
	
	@Autowired
	ISessionService sessionService;
			
	@RequestMapping(value = "/auth/twitter", method = RequestMethod.GET)
	public ResponseEntity<PathTokenVODDto> showLogin(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			String url = request.getRequestURL().toString();
			String tenantID = url.substring(7,url.indexOf("."));
			
			sessionService.setCurrentTenant(tenantID);
			
			OAuth1Operations oauth1Operations = connectionFactoryTwitter.getOAuthOperations();
						 
			OAuth1Parameters oAuth1Parameters=new OAuth1Parameters();
			String pathPrefix = request.getRequestURL().toString().split("auth")[0];
			OAuthToken requestToken=oauth1Operations.fetchRequestToken( pathPrefix + "auth/twitter/callback",null);
			 
			String authorizeUrl = oauth1Operations.buildAuthorizeUrl(requestToken.getValue(),oAuth1Parameters);
			PathTokenVODDto redirect = new PathTokenVODDto();
			redirect.setPathTokenVOD(authorizeUrl);
			return new ResponseEntity<>(redirect, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/auth/twitter/callback", method = RequestMethod.GET)
	public ResponseEntity<User> twitterCallback(@RequestParam(value="oauth_token", required=false) String oauthToken,
			@RequestParam(value="oauth_verifier", required=false) String oauthVerifier, HttpServletRequest request, HttpServletResponse response) {
		try {						
			OAuth1Operations oauth1Operations = connectionFactoryTwitter.getOAuthOperations();
			
			OAuthToken requestToken = new OAuthToken(oauthToken, null);
			
			OAuthToken accessToken = oauth1Operations.exchangeForAccessToken(new AuthorizedRequestToken(requestToken, oauthVerifier), null);
	
			Connection<Twitter> connection = connectionFactoryTwitter.createConnection(accessToken);
	
			Twitter twitter = connection != null ? connection.getApi() : new TwitterTemplate(accessToken.getValue());
	
			TwitterProfile twitterProfile=twitter.userOperations().getUserProfile();
				
			ConnectionKey connectionKey=connection.getKey();
			
			String name = connection.getDisplayName();
			
			String tenantId = sessionService.getCurrentTenant();
			
			if (!userService.existsUserXTwitterId(connectionKey.getProviderUserId(), tenantId)) {
				UserDto user = new UserDto();
				user.setTwitterUserId(connectionKey.getProviderUserId());
				user.setNickname(name);
				user.setCountry(twitterProfile.getLocation());
				user.setEmail("");
				user.setPassword("");
				user.setCity("");
				user.setBlocked(true);
				userService.saveUser(user, tenantId);
				
			}
			User userdto = new User();
			userdto.setTwitter(true);
			userdto.setUsername(name);
			userdto.setTwitterID(connectionKey.getProviderUserId());
			userdto.setPassword(connectionKey.getProviderUserId());
			//String pathPrefix = request.getRequestURL().toString().split("auth")[0];
			//String token = sentJSONByPOSTGetToken(pathPrefix + "user/login", userdto);		    
			//response.setHeader("Athentication", token);
			return new ResponseEntity<>(userdto, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
