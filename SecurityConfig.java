package com.seiya.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity web)throws Exception{
		
		web.formLogin().loginPage("/login").defaultSuccessUrl("/topselect").failureUrl("/login-error").permitAll();
		web.authorizeRequests().antMatchers("/**").permitAll();
		web.logout().logoutSuccessUrl("/login").permitAll();
	}
	
	//AuthenticationManagerに、UserDetailsServiceの実装クラスと、passwordEncoder()を登録
	@Autowired
	void configureAuthenticationManager(AuthenticationManagerBuilder auth) throws Exception{
		
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		
	}
	
	//passwordEncoder()で利用するパスワードエンコードを行うオブジェクトを返す
	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
