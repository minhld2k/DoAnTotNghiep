package com.doan.totnghiep.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "qlsv_thoikhoabieus")
@Entity
@Data
@NoArgsConstructor
public class ThoiKhoaBieu {
	
	@Id
	@GeneratedValue(generator = "bigid")
    @GenericGenerator(name = "bigid",strategy = "com.doan.totnghiep.util.IDGenerator")
	private Long id;
	
	@Column
	private Integer cahoc;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ngay")
	private Date ngay;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "monid")
	private MonHoc mon;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lopid")
	private LopHoc lop;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "phongid")
	private PhongHoc phong;
	
	@Column
	private String mota;

}
