package com.ubosque.api.store.port.in;

import java.util.List;

import com.ubosque.api.store.domain.dto.response.GenericResponse;
import com.ubosque.api.store.domain.dto.response.ReportBranchOffice;
import com.ubosque.api.store.domain.dto.response.ReportUser;

public interface ReportUseCase {
	
	public GenericResponse<List<ReportUser>> reportSalesByUser(String authorization);
	
	public GenericResponse<List<ReportBranchOffice>> reportSalesByBranchOffice(String authorization);

}
