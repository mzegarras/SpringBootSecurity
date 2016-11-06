package net.msonic.tutorial.repository;

import net.msonic.tutorial.common.model.User;

public interface UserRepository {
	
	 User findUserById(String name);
	
}
