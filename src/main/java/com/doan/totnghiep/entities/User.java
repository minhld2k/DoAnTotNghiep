package com.doan.totnghiep.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "qtht_users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User extends Common{

	private static final long serialVersionUID = 1L;
	
	@Column
	private String username;
	
	@Column
	private String password;
	
	@Column
	private String email;
	
	@Column
	private Integer status;
	
	@Column
	private Long nhomid;
	
}
