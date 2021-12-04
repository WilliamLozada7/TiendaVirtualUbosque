package com.ubosque.api.store.domain.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportBranchOffice {

	private String branchOfficeName;
	
	private String branchOfficeAmount;
}
