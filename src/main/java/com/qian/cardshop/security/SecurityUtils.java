package com.qian.cardshop.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public final class SecurityUtils {
	
	static Logger logger = LoggerFactory.getLogger(SecurityUtils.class);

	// method to find login user via email (using sessions )
	private SecurityUtils() {
	}

	public static String getUserName() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		String userName = null;
		if (authentication != null) {
			logger.trace("SecurityUtils, Authentication: "+authentication);
			
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			
			logger.trace("SecurityUtils, userDetails: "+userDetails);
			
			userName = userDetails.getUsername();
			
			logger.trace("SecurityUtils, userName: "+userName);

		}
		return userName;
	}

}
