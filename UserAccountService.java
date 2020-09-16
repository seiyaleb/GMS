package com.seiya.springboot;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seiya.springboot.repositories.AccountRepository;

@Service
public class UserAccountService implements UserDetailsService{
	
	@Autowired
	private AccountRepository repository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//usernameにより、UserDetailsオブジェクトを返す
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		if (username == null || "".equals(username)) {
            throw new UsernameNotFoundException("Username is empty");
        }
       
        Account ac = repository.findByUsername(username);
        if (ac == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        
        if (!ac.isEnabled()) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        
        UserAccount user = new UserAccount(ac,getAuthorities(ac));

        return user;
	}

	//ロール（権限の情報）を返す
	private Collection<GrantedAuthority> getAuthorities(Account account) {
		
		if(account.isAdmin()){
			return AuthorityUtils.createAuthorityList("ROLE_ADMIN","ROLE_USER");
			
		}else{
			return AuthorityUtils.createAuthorityList("ROLE_USER");
		}
	}
	
	//以下二つのメソッドでは、ユーザーをDBに登録している（違いはAdminか否か）
	@Transactional
    public void registerAdmin(String username, String password) {
        Account user = new Account(username, passwordEncoder.encode(password),true);
        repository.save(user);
    }

    @Transactional
    public void registerUser(String username, String password) {
        Account user = new Account(username, passwordEncoder.encode(password),false);
        repository.save(user);
    }

}
