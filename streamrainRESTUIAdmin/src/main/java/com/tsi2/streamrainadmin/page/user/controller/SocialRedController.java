package com.tsi2.streamrainadmin.page.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.tsi2.streamrain.services.session.interfaces.ISessionService;
import com.tsi2.streamrain.services.tenants.interfaces.ITenantService;
import com.tsi2.streamrain.services.user.interfaces.IUserService;

@Controller
public class SocialRedController {
	
	@Autowired
	TwitterConnectionFactory connectionFactoryTwitter;
	
	@Autowired
	IUserService userService;
	
	@Autowired
	ISessionService sessionService;
			
	@RequestMapping(value = "/{tenant}/auth/twitter", method = RequestMethod.GET)
	public String showLogin(@PathVariable("tenant") String tenant, HttpServletRequest request, HttpServletResponse response) {
		try {		
			sessionService.setCurrentTenant(tenant);
			
			OAuth1Operations oauth1Operations = connectionFactoryTwitter.getOAuthOperations();
						 
			OAuth1Parameters oAuth1Parameters=new OAuth1Parameters();
			 
			OAuthToken requestToken=oauth1Operations.fetchRequestToken("http://127.0.0.1:8080/streamrainRESTUI/auth/twitter/callback",null);
			 
			String authorizeUrl = oauth1Operations.buildAuthorizeUrl(requestToken.getValue(),oAuth1Parameters);
						
			return "redirect:"+authorizeUrl;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/auth/twitter/callback", method = RequestMethod.GET)
	public ModelAndView twitterCallback(@RequestParam(value="oauth_token", required=false) String oauthToken,
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
				userService.saveUser(user, tenantId);
			}
			
		    ModelAndView mav = null;
		    	    
	    	mav = new ModelAndView("welcome");
	        mav.addObject("firstname", name);
		    
		    return mav;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
