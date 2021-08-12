package com.doan.totnghiep.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.doan.totnghiep.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	@Query(value="select * from qtht_users where daxoa = 0 and username = ?", nativeQuery = true)
	List<User> findByUsername(String username);
}
