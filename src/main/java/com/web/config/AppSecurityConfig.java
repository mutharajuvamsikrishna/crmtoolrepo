package com.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.web.filter.JwtRequestFilter;
import com.web.service.UserDetail;

@EnableWebSecurity
@Order(1)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired

	private UserDetail myUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService);

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Override
	@Bean(name = "userAuthentication")
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable().authorizeRequests()
				.antMatchers("/api/v1/authenticate", "/api/v1/authenticate1", "/api/v1/register", "/api/v1/save",
						"/api/v1/otp1", "/api/v1/changepassword", "/api/v1/otp5", "/api/v1/changepassword1",
						"/api/v1/adminregister", "/api/v1/adminotp1", "/api/v1/adminsave",
						"/api/v1/adminchangepassword", "/api/v1/adminotp5", "/api/v1/adminchangepassword1",
						"/api/v1/alluserregisters", "/api/v1/alladminregisters", "/api/v1/superadminlogin",
						"/api/v1/superchangepassword", "/api/v1/superchangepassword1", "/api/v1/superadmreq",
						"/api/v1/superdelete", "/api/v1/adminsearch", "/api/v1/supreditupdate",
						"/api/v1/superviewprofessional", "/api/v1/supreg", "/api/v1/supsave", "/api/v1/deleteuserreg",
						"/api/v1/supadminreg", "/api/v1/deleteadminreg", "/api/v1/supadminsave",
						"/api/v1/getalladminreg", "/api/v1/getallreg")
				.permitAll().anyRequest().authenticated().and().exceptionHandling().and().cors().and()

				// Use the
				// configured
				// CORS settings
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}

}