package com.doan.totnghiep.services;

import com.doan.totnghiep.entities.ThoiKhoaBieu;

public interface ThoiKhoaBieuService {

	ThoiKhoaBieu getThoiKhoaBieu(long id);

	void update(ThoiKhoaBieu item);

	void delete(ThoiKhoaBieu entity);

}
