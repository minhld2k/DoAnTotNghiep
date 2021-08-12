package com.doan.totnghiep.services;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.doan.totnghiep.entities.UniFileUpLoads;
import com.doan.totnghiep.repositories.UniFileUpLoadsRepository;
import com.doan.totnghiep.util.CommonUtil;
import com.doan.totnghiep.util.replaceDemo;

@Service
public class UniFileUpLoadsServiceImpl implements UniFileUpLoadsService{
	@Autowired
	UniFileUpLoadsRepository repository;
	
	@Override
	public UniFileUpLoads upload(MultipartFile fileUpload, int kieu, long doiTuongId,String nguoiTao)  {
		UniFileUpLoads uniFileUpload = null;
		String linkDownload = "/download";
		Date dateUpload = new Date();
		long limit = 10;

		long size = fileUpload.getSize();
		long limitsize = (long) ((size / (1024 * 1024)));
		if (limitsize > limit) {
			return uniFileUpload;
		}
		try {
			
			String pathFolder = "/uploads/";
			String contentType = fileUpload.getContentType();

			File theDir = new File(pathFolder);
			// kiểm tra folder tồn tại hay không.
			if (!theDir.exists()) {
				try {
					theDir.mkdirs();
				} catch (Exception e) {
					System.out.println("Lỗi Tạo Folder");
					e.printStackTrace();
				}
			}
			String maSo_ = replaceDemo.getMd5(fileUpload.getOriginalFilename()+theDir.getAbsoluteFile()+ doiTuongId) ;
			// ghi file
			File createFile = new File(theDir.getAbsoluteFile(),maSo_);
			FileOutputStream fileOutputStream = new FileOutputStream(createFile.getAbsolutePath());
			uniFileUpload= new UniFileUpLoads();
			uniFileUpload.setTenFile(CommonUtil.SafeStringHTML(fileUpload.getOriginalFilename()));
			uniFileUpload.setKieuFile(contentType);
			uniFileUpload.setNguoiTao(nguoiTao);
			uniFileUpload.setNgayTao(dateUpload);
			uniFileUpload.setMaSo(maSo_);
			uniFileUpload.setKieu(kieu);
			uniFileUpload.setDoiTuongId(doiTuongId);
			uniFileUpload.setLinkFile(linkDownload + "?maSo="+maSo_);
			System.out.println("#############begin add file");
			uniFileUpload = this.repository.save(uniFileUpload);
			fileOutputStream.write(fileUpload.getBytes());
			fileOutputStream.flush();
			fileOutputStream.close();
			System.out.println("########## the end");
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return uniFileUpload;
	}
}
