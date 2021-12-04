package com.ubosque.api.store.domain.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "T01_USERS")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
	
	@Id
	private String _id;
	private String userId;
	private String userAddres;
	private String userEmail;
	private String userName;
	private String userLogon;
	private String userPassword;
	private String userToken;
	private Date userStartDate;
	private Date userEffectiveDate;
	private String userState;
	private String userBranchOffice;
	private String userRole;
}
