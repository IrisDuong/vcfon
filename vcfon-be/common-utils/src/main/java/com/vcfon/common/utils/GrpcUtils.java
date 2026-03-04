package com.vcfon.common.utils;

import java.util.List;

import com.google.protobuf.Any;
import com.google.protobuf.BoolValue;
import com.google.protobuf.Int32Value;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.StringValue;

public class GrpcUtils {

	
	public static Object unpackGrpcValue(Any value) {
		try {
			if(value.is(StringValue.class)) {
				return value.unpack(StringValue.class).getValue();
			}else if(value.is(Int32Value.class)) {
				return value.unpack(Int32Value.class).getValue();
			}else if(value.is(BoolValue.class)) {
				return value.unpack(BoolValue.class).getValue();
			}
			return value.getTypeUrl();
		} catch (InvalidProtocolBufferException e) {
			return null;
		}
	}
}
