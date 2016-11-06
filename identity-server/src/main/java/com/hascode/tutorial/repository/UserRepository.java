package com.hascode.tutorial.repository;

import com.hascode.tutorial.common.model.User;

public interface UserRepository {
	
	 User findUserById(String name);
	
}
