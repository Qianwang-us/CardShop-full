package com.qian.cardshop.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	/*
	 * 
	 * Spring Security Config file
	 * 
	 */

	// wire services needed
	@Autowired
	UserDetailsService userDetailsService;

	// method to config user details class
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		//PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//
//		//auth.inMemoryAuthentication().withUser("admin").password(encoder.encode("admin")).roles("ADMIN");
//		auth.userDetailsService(userDetailsService);
//		
//
//	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.inMemoryAuthentication()
          .withUser("spring")
          .password(encoder.encode("spring"))
          .roles("ADMIN");
		auth.userDetailsService(userDetailsService);
		

	}

//	// method to encode password in db
//	@Bean
//	public DaoAuthenticationProvider daoAuthenticationProvider() {
//		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//		authenticationProvider.setUserDetailsService(userDetailsService);
//		authenticationProvider.setPasswordEncoder(getPasswordEncoder());
//		return authenticationProvider;
//	}
//
//	// method to instantiate new object of BCrypt
//	@Bean
//	public BCryptPasswordEncoder getPasswordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
	
	@Bean
	  public AuthenticationProvider daoAuthenticationProvider() {
	    DaoAuthenticationProvider provider = 
	      new DaoAuthenticationProvider();
	    provider.setPasswordEncoder(passwordEncoder());
	    provider.setUserDetailsService(this.userDetailsService);
	    return provider;
	  }
	  
	@Bean
	public static PasswordEncoder passwordEncoder() {
		 

	    PasswordEncoder defaultEncoder = new BCryptPasswordEncoder();
	    String idForEncode = "bcrypt";

	    Map<String, PasswordEncoder> encoders = new HashMap<>();
	    encoders.put(idForEncode, new BCryptPasswordEncoder()); // med encrpt
	    encoders.put("noop", NoOpPasswordEncoder.getInstance()); // no encyp 
	    encoders.put("pbkdf2", new Pbkdf2PasswordEncoder()); // med
	    encoders.put("scrypt", new SCryptPasswordEncoder());// heavy enc
	    encoders.put("sha256", new StandardPasswordEncoder());// easy enc
	 
	    DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(idForEncode, encoders);
	    passwordEncoder.setDefaultPasswordEncoderForMatches(defaultEncoder);
	 
	    return passwordEncoder;
	}
//	  @Bean
//	  public PasswordEncoder passwordEncoder() {
//	    return new BCryptPasswordEncoder();
//	  }

	// main config method to allow users to which page
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
//				.antMatchers("/newcourse/**", "/newuser/**")
//					.hasRole("ADMIN")
//				.antMatchers("/home")
//					.hasAnyRole("ADMIN", "USER")
				.antMatchers(                    		
		                "/register/**",
		                "/",
		                "/about_us",
		                "/category/**").permitAll()
		        .antMatchers("/admin**"
		        		//"/order/**"
		        		)
				.hasRole("ADMIN")
				.antMatchers(
						"/product/**",
						"/add_to_cart/**",
						"/edit_item/**",
						"/view_cart/**",
						"/remove_from_cart/**"
						//"/order/**"
						)
				.hasRole("USER")
				.antMatchers("/order/**")
				.hasAnyRole("ADMIN", "USER")
				
				.and()
				
				.httpBasic()

				.and()
				
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/")
				
				.and()
				
				.exceptionHandling().accessDeniedPage("/accessdenied")
				
				.and()
				
				.formLogin()
					.loginPage("/login")
					.loginProcessingUrl("/login/authenticate")
					.permitAll()
				//.failureUrl("/accessdenied")
					.failureUrl("/login?error")
				.defaultSuccessUrl("/")
				.usernameParameter("username")
				.passwordParameter("password");
				
				

				
	}

	// pages not to follow the security
	@Override
	public void configure(final WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**");
				//, "/fonts/**", "/styles/**", "/js/**", "/images/**"
	}

}
