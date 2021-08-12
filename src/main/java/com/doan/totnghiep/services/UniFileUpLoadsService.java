package com.doan.totnghiep.services;

import org.springframework.web.multipart.MultipartFile;

import com.doan.totnghiep.entities.UniFileUpLoads;

public interface UniFileUpLoadsService {

	UniFileUpLoads upload(MultipartFile fileUpload, int kieu, long doiTuongId, String nguoiTao);

}
