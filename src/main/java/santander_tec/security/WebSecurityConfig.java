package santander_tec.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // Habilitamos la securización de nuestra API con @Secured
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserDetailsService userDetailsService;
	private final BCryptPasswordEncoder encoder;

	@Autowired
	public WebSecurityConfig(@Qualifier("userService") UserDetailsService userDetailsService, BCryptPasswordEncoder encoder) {
		this.userDetailsService = userDetailsService;
		this.encoder = encoder;
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public JwtAuthorizationFilter jwtAuthorizationFilter() {
		return new JwtAuthorizationFilter(userDetailsService);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Configuración de la clase que recupera los usuarios y algorito para procesar las passwords
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.cors().and()
				.csrf().disable()
				.authorizeRequests().antMatchers(HttpMethod.POST, "/meets-and-beer/login").permitAll()
				.anyRequest().authenticated().and()
				.addFilterBefore(new JwtAuthenticationFilter(authenticationManager()),
						UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}