package src.main.java.sec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
@ControllerAdvice(annotations = RestController.class)
@Configuration
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

	@Override
	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {

		String targetUrl = determineTargetUrl(authentication);

		if (response.isCommitted()) {
			return;
		}
		RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
		redirectStrategy.sendRedirect(request, response, targetUrl);
	}
	@ModelAttribute("currentUserRole")
	public int role(Authentication authentication) {
		int role = 0 ;

		// Fetch the roles from Authentication object
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		List<String> roles = new ArrayList<String>();
		for (GrantedAuthority a : authorities) {
			roles.add(a.getAuthority());
		}
		if (roles.contains("ADMIN")) {
			role = 1;
		} 
		else if (roles.contains("EMPLOYE")) {
			role = 2;
		}
		else if (roles.contains("CLIENT")) {
			role = 3;
		}
		return role;
		
	}
	protected String determineTargetUrl(Authentication authentication) {
		String url = "/login?error=true";
		

		// Fetch the roles from Authentication object
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		List<String> roles = new ArrayList<String>();
		for (GrantedAuthority a : authorities) {
			roles.add(a.getAuthority());
		}

		// check user role and decide the redirect URL
		if (roles.contains("ADMIN")) {
			url = "/admin";
			
		} 
		else if (roles.contains("EMPLOYE")) {
			url = "/serviceHome";
			
		}
		else if (roles.contains("CLIENT")) {
			url = "/home";
		}
		return url;
	}
}
