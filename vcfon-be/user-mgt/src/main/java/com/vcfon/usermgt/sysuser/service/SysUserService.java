package com.vcfon.usermgt.sysuser.service;

import com.vcfon.usermgt.sysuser.dto.SysUserDtoRequest;

public interface SysUserService {

	void createNewUser(SysUserDtoRequest request);
}
