package com.doan.totnghiep.services;

import com.doan.totnghiep.entities.WorkTaskDetail;

public interface WorkTaskDetailService {

	WorkTaskDetail getWorkTaskDetail(long workTaskId);

	void save(WorkTaskDetail item);

	void delete(WorkTaskDetail wt);

}
