package com.ubosque.api.store.domain.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportUser {
	
	private String userId;
	
	private String userName;
	
	private String userAmount;
}
