package com.doan.totnghiep.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doan.totnghiep.entities.User;
import com.doan.totnghiep.repositories.UserRepository;
import com.doan.totnghiep.util.CommonUtil;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository repository;
	
	@Override
	public User update(User item) {
		return this.repository.save(item);
	}
	
	@Override
	public User getUser(long id) {
		return this.repository.findById(id).get();
	}

	@Override
	public List<User> findByUsername(String username) {
		return repository.findByUsername(username);
	}
	
	@Override
	public boolean checkLogin(String username, String password) {
		User user = this.findByUsername(username).get(0);
		if (null != user && CommonUtil.checkBcrypt(password, user.getPassword())) {
			return true;
		}	
		return false;
	}
}
