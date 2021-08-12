package com.doan.totnghiep.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "qtht_filedinhkem")
@Data
@NoArgsConstructor
public class UniFileUpLoads {
	@Id
    @GeneratedValue(generator = "bigid")
    @GenericGenerator(name = "bigid",strategy = "com.doan.totnghiep.util.IDGenerator")
	private Long id;
	
	@Column(name = "nguoitao",length = 100)
	private String nguoiTao;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ngaytao")
	private Date ngayTao;
	
	@Column(name = "tenfile", length = 75)
	private String tenFile;
	
	@Column(name = "kieufile", length = 75)
	private String kieuFile;
	
	@Column(name = "linkfile", length = 512)
	private String linkFile;
	
	@Column(name = "maso", length = 75)
	private String maSo;
	
	@Column(name = "kieu")
	private Integer kieu;
	
	@Column(name = "doituongid")
	private Long doiTuongId;
}
