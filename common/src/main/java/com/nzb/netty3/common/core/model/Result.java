package com.nzb.netty3.common.core.model;

import com.nzb.netty3.common.core.serial.Serializer;

public class Result<T extends Serializer> {
	private int resultCode;

	private T content;

	public static <T extends Serializer> Result<T> SUNNCESS(T content) {
		Result<T> result = new Result<T>();
		result.resultCode = ResultCode.SUCCESS;
		result.content = content;
		return result;
	}

	public static <T extends Serializer> Result<T> SUCCESS() {
		Result<T> result = new Result<T>();
		result.resultCode = ResultCode.SUCCESS;
		return result;
	}

	public static <T extends Serializer> Result<T> ERROR(int resultCode) {
		Result<T> result = new Result<T>();
		result.resultCode = resultCode;
		return result;
	}

	public static <T extends Serializer> Result<T> valueOf(int resultCode, T content) {
		Result<T> result = new Result<T>();
		result.resultCode = resultCode;
		result.content = content;
		return result;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

	public boolean isSuccess() {
		return this.resultCode == ResultCode.SUCCESS;
	}

}
