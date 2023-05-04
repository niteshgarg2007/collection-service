package com.hcl.igovern.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.igovern.exception.BusinessException;
import com.hcl.igovern.service.RecoveryService;
import com.hcl.igovern.vo.ContextDataVO;
import com.hcl.igovern.vo.ITSOvpSummaryVO;
import com.hcl.igovern.vo.ITSRecoveryHistoryVO;
import com.hcl.igovern.vo.ITSRecoverySummaryVO;
import com.hcl.igovern.vo.ItsRecoveryVO;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/its/apis/recovery")
@CrossOrigin(origins = "http://localhost:4200")
@ResponseBody
public class RecoveryController {

	Logger logger = LoggerFactory.getLogger(RecoveryController.class);
	
	@Autowired
	private RecoveryService recoveryService;
	
	public static final String ERR_CODE = "ERR_CODE";
	
	@Operation(summary = "Save recovery and its related data to database.")
	@PostMapping("/addrecovery")
	public ItsRecoveryVO addRecovery(@RequestBody ItsRecoveryVO itsRecoveryVO) {
		try {
			itsRecoveryVO = recoveryService.addRecoveryAndDetails(itsRecoveryVO);
			if (itsRecoveryVO != null && (itsRecoveryVO.getStatusMessage() == null || itsRecoveryVO.getStatusMessage().isEmpty())) {
				throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryController.addRecovery() method.");
			}
		} catch (BusinessException be) {
			logger.error("Business Exception in RecoveryController.addRecovery() method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryController.addRecovery() method." + be.getMessage());
		}
		return itsRecoveryVO;
	}
	
	@Operation(summary = "Retrieve overpayment details for the victim and bad actor combination or bad actor.")
	@PostMapping("/overpaymentdetailslist")
	public List<ITSOvpSummaryVO> getOverpaymentDetailsList(@RequestBody ContextDataVO contextData) {
		logger.info("Starting to calling getOverpaymentDetailsList method");
		List<ITSOvpSummaryVO> list = null;
		try {
			if (contextData.getVictimBadActorXrefId() != null) {
				list = recoveryService.getOverpaymentDetailsList(contextData);
			}
			
			if (contextData.getBadActorId() != null) {
				list = recoveryService.getOverpaymentDetailsList(contextData);
			}
		} catch (BusinessException be) {
			logger.error("Business Exception in RecoveryController.getOverpaymentDetailsList() method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryController.getOverpaymentDetailsList() method." + be.getMessage());
		}
		return list;
	}
	
	@Operation(summary = "Retrieve recovery summary list for the victim and bad actor combination.")
	@GetMapping("/recoverysummarylist/{victimBadActorXrefId}")
	public List<ITSRecoverySummaryVO> getITSRecoveryDetailsSummaryList(@PathVariable Long victimBadActorXrefId) {
		logger.info("Starting to calling getITSRecoveryDetailsSummaryList method");
		List<ITSRecoverySummaryVO> list = null;
		try {
			if (victimBadActorXrefId != null) {
				list = recoveryService.getITSRecoverySummaryList(victimBadActorXrefId);
			}
		} catch (BusinessException be) {
			logger.error("Business Exception in RecoveryController.getITSRecoveryDetailsSummaryList() method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryController.getITSRecoveryDetailsSummaryList() method." + be.getMessage());
		}
		
		return list;
	}
	
	@Operation(summary = "Retrieve recovery history list for the selected recovery id.")
	@GetMapping("/recoveryhistorylist/{selectedRecoveryId}")
	public List<ITSRecoveryHistoryVO> getITSRecoveryHistoryList(@PathVariable Long selectedRecoveryId) {
		logger.info("Starting to calling getITSRecoveryHistoryList method");
		List<ITSRecoveryHistoryVO> list = null;
		try {
			if (selectedRecoveryId != null) {
				list = recoveryService.getITSRecoveryHistoryList(selectedRecoveryId);
			}
		} catch (BusinessException be) {
			logger.error("Business Exception in RecoveryController.getITSRecoveryHistoryList() method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryController.getITSRecoveryHistoryList() method." + be.getMessage());
		}
		
		return list;
	}
}
