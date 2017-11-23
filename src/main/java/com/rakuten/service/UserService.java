package com.rakuten.service;

import com.rakuten.model.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
}
