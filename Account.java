package com.seiya.springboot;

import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Account {

	protected Account() {}
	
	public Account(String username, String password, boolean isAdmin) {
		
		setId(0L);
		setUsername(username);
		setPassword(password);
		setEnabled(true);
		setAdmin(isAdmin);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, unique = true)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private boolean enabled;
	
	@Column(nullable = false)
	private boolean admin;
	
	@OneToMany(mappedBy="account", fetch = FetchType.EAGER)
	@Column(nullable=true)
	private List<member> members;
}
