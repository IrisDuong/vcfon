package com.vcfon.usermgt.sysuser.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vcfon.usermgt.sysuser.entity.SysUser;

public interface SysUserRepo extends JpaRepository<SysUser, String>{

	Boolean existsByUserNameAndEmailAndActiveAndDeletable(String userName,String email, Boolean active, Boolean deletable);
}
