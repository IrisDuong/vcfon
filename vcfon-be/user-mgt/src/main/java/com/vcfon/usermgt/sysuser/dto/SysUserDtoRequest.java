package com.vcfon.usermgt.sysuser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysUserDtoRequest {
	private String userName;
	private String password;
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
