package com.identity.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table
@ToString
public class UserCredential {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String email;
	private String password;
	private String role;
	private int instituteId;
	public UserCredential(int id, String name, String email, String role, int instituteId) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.role = role;
		this.instituteId = instituteId;
	}
	public UserCredential() {
		super();
		// TODO Auto-generated constructor stub
	}		
}