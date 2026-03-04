package com.vcfon.usermgt.sysuser.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vcfon.common.exception.InternalServerErrorException;
import com.vcfon.common.exception.NotFoundException;
import com.vcfon.usermgt.sysuser.dto.SysUserDtoRequest;
import com.vcfon.usermgt.sysuser.entity.SysUser;
import com.vcfon.usermgt.sysuser.repo.SysUserRepo;
import com.vcfon.usermgt.sysuser.service.SysUserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SysUserServiceImpl implements SysUserService{

	private final SysUserRepo userRepo;
	
	@Override
	public void createNewUser(SysUserDtoRequest request) {
		try {
			SysUser user = SysUser.builder()
					.userName(request.getUserName())
					.password(request.getPassword())
					.email(request.getEmail())
					.firstName(request.getFirstName())
					.lastName(request.getLastName())
					.phoneNo(request.getPhoneNo())
					.roleNo(request.getRoleNo())
					.avatarUrl(request.getAvatarUrl())
					.sub(request.getSub())
					.active(Boolean.TRUE)
					.deletable(Boolean.FALSE)
					.lockedTimes(0)
					.build();
			userRepo.save(user);
		} catch (Exception e) {
			throw new InternalServerErrorException("Create new user failed");
		}
	}

}
