package com.rakuten.service.auth;

import com.rakuten.model.auth.User;

public interface UserService {
    User findUserByEmail(String email);

    void saveUser(User user);
}
