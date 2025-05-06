package admin_user.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import admin_user.service.CustomSuccessHandler;
import admin_user.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	CustomSuccessHandler customSuccessHandler;

	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(c -> c.disable())

				// Configuration des autorisations pour les pages spécifiques
				.authorizeHttpRequests(request -> request
						.requestMatchers("/admin-page").hasAuthority("ADMIN")
						.requestMatchers("/user-page").hasAuthority("USER")
						.requestMatchers("/registration", "/css/**").permitAll()
						.requestMatchers("/user/save", "/dashboard").authenticated() // Authentification pour les pages de soumission
						.anyRequest().authenticated())

				// Configuration de la page de login
				.formLogin(form -> form
						.loginPage("/login")
						.loginProcessingUrl("/login")
						.successHandler(customSuccessHandler)
						.permitAll())

				// Configuration de la déconnexion
				.logout(form -> form
						.invalidateHttpSession(true)
						.clearAuthentication(true)
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
						.logoutSuccessUrl("/login?logout")
						.permitAll());

		return http.build();
	}

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}
}
