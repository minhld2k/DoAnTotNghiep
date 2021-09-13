package com.doan.totnghiep.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.doan.totnghiep.entities.SinhVien;

public interface SinhVienRepository extends JpaRepository<SinhVien, Long>{
	@Query(value="select * from qlsv_sinhviens where daxoa = 0 and ma = ?", nativeQuery = true)
	List<SinhVien> findByMaSV(String maSV);
}
