package com.doan.totnghiep.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.doan.totnghiep.entities.ChucNang;

public interface ChucNangRepository extends JpaRepository<ChucNang, Long> {
	
	@Query(value = "select * from qtht_chucnangs where daxoa = 0 order by id ",nativeQuery = true)
	List<ChucNang> getAllChucNang();
	
	@Query(value = "select count(*) from qtht_chucnangs where daxoa = 0 ",nativeQuery = true)
	int getAllChucNangCount();
	
	@Query(value="select * from qtht_chucnangs where daxoa = 0 and key = ?", nativeQuery = true)
	List<ChucNang> findByKey(String key);

}
