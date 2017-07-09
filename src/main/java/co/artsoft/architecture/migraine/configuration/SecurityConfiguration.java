package co.artsoft.architecture.migraine.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	@Qualifier("userService")
	private UserDetailsService userService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		//Quitar comentarios de esta linea cuando se vaya a usar digest, comentar cuando se vaya a usar basic
		auth.userDetailsService(userService).toString();
		//Quitar comentarios de esta linea cuando se vaya a usar basic, comentar cuando se vaya a usar digest
		//auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//Quitar comentarios de este bloque (comienza en http.authorizeRequests(). y termina en .disable();) cuando se vaya a usar basic y colocar comentarios 
		//cuando se vaya a usar digest
		/*http.authorizeRequests().
			anyRequest()
			.fullyAuthenticated()
		.and()
			.httpBasic()
		.and()
			.csrf()
			.disable();*/
		
		//Quitar comentarios de este bloque (comienza en DigestAuthenticationEntryPoint y termina en .anyRequest().fullyAuthenticated()) cuando se vaya a usar digest
		//y colocar comentarios cuando se vaya a usar basic
		DigestAuthenticationEntryPoint authenticationEntryPoint = new DigestAuthenticationEntryPoint();
        authenticationEntryPoint.setKey("sewatech");
        authenticationEntryPoint.setRealmName("example");
        authenticationEntryPoint.setNonceValiditySeconds(600);
        
        DigestAuthenticationFilter filter = new DigestAuthenticationFilter();
        filter.setAuthenticationEntryPoint(authenticationEntryPoint);
        filter.setUserDetailsService(userDetailsService());

        http.addFilter(filter)
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .authorizeRequests()
                .anyRequest().fullyAuthenticated()
                .and().csrf().disable();
		
		
			
	}

}
