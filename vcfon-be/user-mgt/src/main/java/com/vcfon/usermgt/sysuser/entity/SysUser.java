package com.vcfon.usermgt.sysuser.entity;

import com.vcfon.usermgt.sysuser.dto.SysUserDtoRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SYS_USER")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysUser {

	@Id
	@Column(name = "user_name", length = 20, nullable = false, unique = true)
	private String userName;

	@Column(name = "password", length = 500, nullable = false)
	private String password;
	
	@Column(name = "email", length = 30, nullable = false, unique = true)
	private String email;
	private String firstName;
	private String lastName;
	private String phoneNo;
	private int roleNo;
	private String avatarUrl;
	private String sub;
	private Boolean active;
	private Boolean deletable;
	private int lockedTimes;
}
