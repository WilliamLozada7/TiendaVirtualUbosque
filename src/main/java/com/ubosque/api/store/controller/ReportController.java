package com.ubosque.api.store.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ubosque.api.store.domain.dto.response.GenericResponse;
import com.ubosque.api.store.domain.dto.response.ReportBranchOffice;
import com.ubosque.api.store.domain.dto.response.ReportUser;
import com.ubosque.api.store.port.in.ReportUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/store/report")
@RequiredArgsConstructor
public class ReportController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class);
	private final ReportUseCase reportUseCase;
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/salesByUser")
	public GenericResponse<List<ReportUser>> reportSalesByUser (
			@Valid @RequestHeader("authorization") String authorization){
				
		LOGGER.info("** ReportController-ReportSalesByUser-Init **");
		return reportUseCase.reportSalesByUser(authorization);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/salesByBranchOffice")
	public GenericResponse<List<ReportBranchOffice>> reportSalesByBranchOffice (
			@Valid @RequestHeader("authorization") String authorization){
				
		LOGGER.info("** ReportController-reportSalesByBranchOffice-Init **");
		return reportUseCase.reportSalesByBranchOffice(authorization);
	}
}
