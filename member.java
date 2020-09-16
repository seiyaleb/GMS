package com.seiya.springboot;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberid;
	
	@SuppressWarnings("deprecation")
	@NotEmpty
	private String membername;
	
	@SuppressWarnings("deprecation")
	@NotEmpty
	private String gender;
	
	@NotNull
	private int grade;
	
	@SuppressWarnings("deprecation")
	@NotEmpty
	private String position;
	
	@NotNull
	private int payFlag;
	
	@ManyToOne
	@JoinColumn(name="id")
	private Account account;
}
