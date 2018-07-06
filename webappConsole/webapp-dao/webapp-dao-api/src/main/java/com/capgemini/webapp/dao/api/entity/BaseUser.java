package com.capgemini.webapp.dao.api.entity;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

import javax.persistence.Column;
import org.hibernate.validator.constraints.NotEmpty;

@MappedSuperclass
public abstract class BaseUser implements Serializable {

	@NotEmpty
	@Column(name = "FIRST_NAME", nullable = false)
	protected String firstName;	

	@NotEmpty
	@Column(name = "LAST_NAME", nullable = false)
	protected String lastName;

	@NotEmpty
	@Column(name = "EMAIL", nullable = false)
	protected String email;
	
	
	@NotEmpty
	@Column(name = "PASSWORD", nullable = false)
	protected String password;


	
}
