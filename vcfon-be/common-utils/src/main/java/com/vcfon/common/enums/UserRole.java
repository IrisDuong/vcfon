package com.vcfon.common.enums;

import java.util.stream.Stream;

public enum UserRole {

	ADMIN(1,"ADMIN"),
	MOD(2,"MOD"),
	EMP(3,"EMP");
	
	UserRole(int roleNo, String roleName) {
		this.roleNo = roleNo;
		this.roleName = roleName;
	}
	private final int roleNo;
	private final String roleName;
	public int getRoleNo() {
		return roleNo;
	}
	public String getRoleName() {
		return roleName;
	}
	
	public UserRole findRoleByNo(int roleNo) {
		return Stream.of(UserRole.values())
				.filter(role-> role.getRoleNo() == roleNo)
				.findFirst().orElse(EMP);
	}
}
