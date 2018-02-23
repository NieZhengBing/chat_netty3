package com.nzb.netty3.common.core.model;

public interface ResultCode {
	int SUCCESS = 0;

	int NO_INVOKER = 1;

	int AGRUMENT_ERROR = 2;

	int UNKOWN_EXCEPTION = 3;

	int PLAYERNAME_NULL = 4;

	int PLAYER_EXIST = 5;

	int PLAYER_NO_EXIST = 6;

	int PASSWARD_ERROR = 7;

	int HAS_LOGIN = 8;

	int LOGIN_FAIL = 9;

	int PLAYER_NO_ONLINE = 10;

	int LOGIN_PLEASE = 11;

	int CAN_CHAT_YOUSELF = 12;

}
