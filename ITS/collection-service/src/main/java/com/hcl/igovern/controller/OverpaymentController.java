package com.hcl.igovern.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.igovern.exception.BusinessException;
import com.hcl.igovern.service.OverpaymentService;
import com.hcl.igovern.util.DateUtil;
import com.hcl.igovern.vo.ContextDataVO;
import com.hcl.igovern.vo.ITSOvpSummaryVO;
import com.hcl.igovern.vo.ItsOverpaymentVO;
import com.hcl.igovern.vo.OverpaidWeeksVO;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/its/apis/overpayment")
@CrossOrigin(origins = "http://localhost:4200")
@ResponseBody
public class OverpaymentController {
	
	Logger logger = LoggerFactory.getLogger(OverpaymentController.class);
	
	@Autowired
	private OverpaymentService overpaymentService;

	@Operation(summary = "Save overpayment and its related data to database.")
	@PostMapping("/addoverpayment")
	public ResponseEntity<?> addOverpayment(
				@RequestBody ItsOverpaymentVO itsOverpaymentVO) {
		ResponseEntity<ItsOverpaymentVO> retVal = null;
		try {
			itsOverpaymentVO = overpaymentService.addOverpaymentAndDetails(itsOverpaymentVO);
			retVal = new ResponseEntity<>(itsOverpaymentVO, HttpStatus.CREATED);
		} catch (BusinessException be) {
			logger.error("Business Exception in OverpaymentController addOverpaymentAndDetails() method");
			BusinessException ce = new BusinessException(be.getErrorCode(),be.getErrorMessage());
			ResponseEntity<BusinessException> retValException = null;
			retValException = new ResponseEntity<>(ce, HttpStatus.BAD_REQUEST);
			return retValException;
		}
		return retVal;
	}
	
	@Operation(summary = "Retrieve current date from server.")
	@GetMapping("/currentdate")
	public ResponseEntity<?> getCurrentdate() {
		ResponseEntity<?> retVal = null;
		OverpaidWeeksVO overpaidWeeks = new OverpaidWeeksVO();
		overpaidWeeks.setCbwkBweDt(DateUtil.getCurrentDateString());
		retVal = new ResponseEntity<>(overpaidWeeks, HttpStatus.CREATED);
		return retVal;
	}
	
	@Operation(summary = "Retrieve available program for the victim and bad actor combination.")
	@GetMapping("/programcodelist/{victimBadActorXrefId}")
	public ResponseEntity<List<?>> getProgramCodeDDList(@PathVariable Long victimBadActorXrefId ) {
		ResponseEntity<List<?>> obj = null;
		logger.info("Starting to calling getProgramCodeDDList method");
		try {
			if (victimBadActorXrefId != null) {
				List<OverpaidWeeksVO> list = overpaymentService.getProgramCodeDDList(victimBadActorXrefId);
				obj = new ResponseEntity<>(list, HttpStatus.CREATED);
			}
		} catch (BusinessException be) {
			logger.error("Business Exception in OverpaymentController getProgramCodeDDList() method");
			BusinessException ce = new BusinessException(be.getErrorCode(),be.getErrorMessage());
			List<BusinessException> ceList = new ArrayList<>();
			ceList.add(ce);
			return new ResponseEntity<List<?>>(ceList, HttpStatus.BAD_REQUEST);
		}
		
		return obj;
	}
	
	@Operation(summary = "Retrieve overpayment weeks for the victim and bad actor combination.")
	@PostMapping("/overpaidweekslist")
	public ResponseEntity<List<?>> getOverpaidWeeksList(@RequestBody ContextDataVO contextData) {
		ResponseEntity<List<?>> obj = null;
		logger.info("Starting to calling getOverpaidWeeksList method");
		try {
			if (contextData.getInputClaimId() != null && contextData.getVictimBadActorXrefId() != null) {
				List<OverpaidWeeksVO> list = overpaymentService.getOverpaidWeeksList(contextData);
				obj = new ResponseEntity<>(list, HttpStatus.CREATED);
			}
		} catch (BusinessException be) {
			logger.error("Business Exception in OverpaymentController getOverpaidWeeksList() method");
			BusinessException ce = new BusinessException(be.getErrorCode(),be.getErrorMessage());
			List<BusinessException> ceList = new ArrayList<>();
			ceList.add(ce);
			return new ResponseEntity<List<?>>(ceList, HttpStatus.BAD_REQUEST);
		}
		return obj;
	}
	
	@Operation(summary = "Retrieve overpayment summary list for the victim and bad actor combination.")
	@GetMapping("/overpaymentsummarylist/{victimBadActorXrefId}")
	public ResponseEntity<List<?>> getITSOverpaymentSummaryList(@PathVariable Long victimBadActorXrefId ) {
		ResponseEntity<List<?>> obj = null;
		logger.info("Starting to calling getITSOverpaymentSummaryList method");
		try {
			if (victimBadActorXrefId != null) {
				List<ITSOvpSummaryVO> list = overpaymentService.getITSOverpaymentSummaryList(victimBadActorXrefId);
				obj = new ResponseEntity<>(list, HttpStatus.CREATED);
			}
		} catch (BusinessException be) {
			logger.error("Business Exception in OverpaymentController getITSOverpaymentSummaryList() method");
			BusinessException ce = new BusinessException(be.getErrorCode(),be.getErrorMessage());
			List<BusinessException> ceList = new ArrayList<>();
			ceList.add(ce);
			return new ResponseEntity<List<?>>(ceList, HttpStatus.BAD_REQUEST);
		}
		
		return obj;
	}
	
	@Operation(summary = "Retrieve overpayment details for the given overpayment.")
	@GetMapping("/overpaymentandovpdetails/{overpaymentId}")
	public ResponseEntity<?> getITSOverpaymentAndOvpDetails(@PathVariable Long overpaymentId ) {
		ResponseEntity<?> retVal = null;
		logger.info("Starting to calling getITSOverpaymentAndOvpDetails method");
		try {
			if (overpaymentId != null) {
				ItsOverpaymentVO itsOverpaymentVO = overpaymentService.getITSOverpaymentAndOvpDetails(overpaymentId);
				retVal = new ResponseEntity<>(itsOverpaymentVO, HttpStatus.CREATED);
			}
		} catch (BusinessException be) {
			logger.error("Business Exception in OverpaymentController getITSOverpaymentAndOvpDetails() method");
			BusinessException ce = new BusinessException(be.getErrorCode(),be.getErrorMessage());
			ResponseEntity<BusinessException> retValException = null;
			retValException = new ResponseEntity<>(ce, HttpStatus.BAD_REQUEST);
			return retValException;
		}
		
		return retVal;
	}
}
