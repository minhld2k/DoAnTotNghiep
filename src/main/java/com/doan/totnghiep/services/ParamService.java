package com.doan.totnghiep.services;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

@Service
public class ParamService {
	@Autowired
	HttpServletRequest request;
	
	public String getString(String param, String defaultParam) {
		String value = request.getParameter(param);
		return value != null && !value.equals("") ? HtmlUtils.htmlEscape(value,"UTF-8") : defaultParam;
	};
	
	public int getInt(String param, int defaultParam) {
		String value = getString(param, String.valueOf(defaultParam));
		return Integer.parseInt(value);
	};
	
	public long getLong(String param, int defaultParam) {
		String value = getString(param, String.valueOf(defaultParam));
		return Long.parseLong(value);
	};
	
	public double getDouble(String param, double defaultParam) {
		String value = getString(param, String.valueOf(defaultParam));
		return Double.parseDouble(value);
	};
	
	public boolean getBoolean(String param, boolean defaultParam) {
		String value = getString(param, String.valueOf(defaultParam));
		return Boolean.parseBoolean(value);
	};
	
	public Date getDate(String param, String pattern) {
		String valua = getString(param, "");
		try {
			return new SimpleDateFormat(pattern).parse(valua);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public File save(MultipartFile file , String path) {
		if(!file.isEmpty()) {
			File dir = new File(request.getServletContext().getRealPath(path));
			if(!dir.exists()) {
				dir.mkdirs();
			}
			try {
				File saveFile = new File(dir,file.getOriginalFilename());
				file.transferTo(saveFile);
				return saveFile;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return null;
	}
	
	public void setAttribute(String name, Object value) {
		request.setAttribute(name, value);
	}
	
	public String getServletPath() {
		return request.getServletPath();
	}
}
