package com.doan.totnghiep.services;

import com.doan.totnghiep.entities.WorkTask;

public interface WorkTaskService {

	WorkTask getWorkTask(long workTaskId);

	void save(WorkTask item);

}
