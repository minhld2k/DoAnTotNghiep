package com.doan.totnghiep.services;

import java.util.List;

import com.doan.totnghiep.entities.User;

public interface UserService {

	User getUser(long id);

	User update(User item);

	List<User> findByUsername(String username);

	boolean checkLogin(String username, String password);

}
