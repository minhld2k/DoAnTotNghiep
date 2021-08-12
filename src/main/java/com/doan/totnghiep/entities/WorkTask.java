package com.doan.totnghiep.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "qlsv_worktasks")
@Data
@NoArgsConstructor
public class WorkTask extends Common{
	 
	private static final long serialVersionUID = 1L;
	
	@Column
	private String ten;
	
	@Column(name = "thutu")
	private Integer thuTu;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "monid")
	private MonHoc mon;
}
