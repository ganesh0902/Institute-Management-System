package com.chat.service;

import java.util.List;

import com.chat.dto.UserCredentialDTO;

public interface IdentityClient {

	public UserCredentialDTO getUserById(int userId);
	public List<UserCredentialDTO>  getAllUser();
}
