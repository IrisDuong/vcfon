package com.vcfon.usermgt.sysuser.service.grpc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.vcfon.common.enums.MailTemplate;
import com.vcfon.grpc.notification.MailProtoRequest;
import com.vcfon.grpc.notification.MailProtoServiceGrpc.MailProtoServiceBlockingStub;
import com.vcfon.grpc.usermgt.SysUserProtoRequest;
import com.vcfon.grpc.usermgt.SysUserProtoResponse;
import com.vcfon.grpc.usermgt.SysUserProtoServiceGrpc.SysUserProtoServiceImplBase;
import com.vcfon.usermgt.sysuser.dto.SysUserDtoRequest;
import com.vcfon.usermgt.sysuser.service.SysUserService;
import com.google.protobuf.Any;
import com.google.protobuf.StringValue;

import io.grpc.stub.StreamObserver;

@Service
public class SysUserGrpcServiceImpl extends SysUserProtoServiceImplBase{

	private final SysUserService sysUserService;
	private final MailProtoServiceBlockingStub mailProtoServiceBlockingStub;

	@Value("${url.gateway}")
	String gatewayURL;
	
	public SysUserGrpcServiceImpl(
			SysUserService sysUserService
		   ,MailProtoServiceBlockingStub mailProtoServiceBlockingStub
	) {
		super();
		this.sysUserService = sysUserService;
		this.mailProtoServiceBlockingStub = mailProtoServiceBlockingStub;
	}

	@Override
	public void createSysUserProto(SysUserProtoRequest request, StreamObserver<SysUserProtoResponse> responseObserver) {
		SysUserDtoRequest userDtoRequest = SysUserDtoRequest.builder()
				.userName(request.getUserName())
				.password(request.getHashedPassword())
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
		try {
			// create new user
			sysUserService.createNewUser(userDtoRequest);
			
			// send mail to new user
			MailProtoRequest mailProtoRequest = MailProtoRequest.newBuilder()
					.setRecipient(request.getEmail())
					.setTemplateCode(MailTemplate.NEW_USER.getTemplateCode())
					.putVariables("fullName", Any.pack(StringValue.of(request.getFirstName())))
					.putVariables("userName", Any.pack(StringValue.of(request.getUserName())))
					.putVariables("changePasswordUrl", Any.pack(StringValue.of(gatewayURL.concat("/user-mgt/sys-user/password/change"))))
					.build();
			mailProtoServiceBlockingStub.sendMail(mailProtoRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		SysUserProtoResponse response = SysUserProtoResponse.newBuilder()
				.setUserName(userDtoRequest.getUserName())
				.setEmail(userDtoRequest.getEmail())
				.setRoleNo(userDtoRequest.getRoleNo())
				.setCreated(true)
				.build();
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}
}
