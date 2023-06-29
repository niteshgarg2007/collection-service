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
import com.hcl.igovern.vo.ITSRecoveryHistoryVO;
import com.hcl.igovern.vo.ITSRecoverySummaryVO;
import com.hcl.igovern.vo.ITSRecoveryUpdateVO;
import com.hcl.igovern.vo.ITSRecovsearchDetailsVO;
import com.hcl.igovern.vo.ItsRecoveryDetailsVO;
import com.hcl.igovern.vo.ItsRecoveryVO;
import com.hcl.igovern.vo.SearchBadActorDataVO;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/its/apis/recovery")
@CrossOrigin(origins = "*")
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
	public List<ITSRecoveryUpdateVO> getOverpaymentDetailsList(@RequestBody ContextDataVO contextData) {
		logger.info("Starting to calling getOverpaymentDetailsList method");
		List<ITSRecoveryUpdateVO> list = null;
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
	
	@Operation(summary = "Retrieve recovery data for the given recovery.")
	@GetMapping("/itsrecoverydata/{recoveryId}")
	public ItsRecoveryVO getITSRecoveryDataByRecoveryId(@PathVariable Long recoveryId ) {
		logger.info("Starting to calling getITSRecoveryDataByRecoveryId method");
		ItsRecoveryVO itsRecoveryVO = null;
		try {
			if (recoveryId != null) {
				itsRecoveryVO = recoveryService.getITSRecoveryDataByRecoveryId(recoveryId);
			}
		} catch (BusinessException be) {
			logger.error("Business Exception in RecoveryController.getITSRecoveryDataByRecoveryId() method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryController.getITSRecoveryDataByRecoveryId() method." + be.getMessage());
		}
		return itsRecoveryVO;
	}
	
	@Operation(summary = "Retrieve existing recovery details.")
	@PostMapping("/existingrecoverydetailslist")
	public List<ItsRecoveryDetailsVO> getExistingRecoveryDetailsList(@RequestBody ContextDataVO contextData) {
		logger.info("Starting to calling getExistingRecoveryDetailsList method");
		List<ItsRecoveryDetailsVO> list = null;
		try {
			list = recoveryService.getExistingRecoveryDetailsList(contextData);
		} catch (BusinessException be) {
			logger.error("Business Exception in RecoveryController.getExistingRecoveryDetailsList() method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryController.getExistingRecoveryDetailsList() method." + be.getMessage());
		}
		return list;
	}
	
	@Operation(summary = "Retrieve existing recovery and overpayment details.")
	@PostMapping("/recoveryUpdatedetailslist")
	public List<ItsRecoveryDetailsVO> getOverpaymentUpdateDetailsListByParams(@RequestBody ContextDataVO contextData) {
		logger.info("Starting to calling getOverpaymentUpdateDetailsListByParams method");
		List<ItsRecoveryDetailsVO> list = null;
		try {
			list = recoveryService.getOverpaymentUpdateDetailsListByParams(contextData);
		} catch (BusinessException be) {
			logger.error("Business Exception in RecoveryController.getOverpaymentUpdateDetailsListByParams() method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryController.getOverpaymentUpdateDetailsListByParams() method." + be.getMessage());
		}
		return list;
	}
	
	@Operation(summary = "process given recovery.")
	@GetMapping("/processrecovery/{recoveryId}")
	public ItsRecoveryVO processSelectedRecovery(@PathVariable Long recoveryId ) {
		logger.info("Starting to calling processSelectedRecovery method");
		ItsRecoveryVO itsRecoveryVO = null;
		try {
			if (recoveryId != null) {
				itsRecoveryVO = recoveryService.processSelectedRecovery(recoveryId);
			}
		} catch (BusinessException be) {
			logger.error("Business Exception in RecoveryController.processSelectedRecovery() method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryController.processSelectedRecovery() method." + be.getMessage());
		}
		return itsRecoveryVO;
	}
	
	@Operation(summary = "Retrieve bad actor recovery data")
	@PostMapping("/recoverybadactorsearch")
	public List<ITSRecoverySummaryVO> getRecovSearchBadActorData(@RequestBody SearchBadActorDataVO searchBadActorDataVO ) {
		logger.info("Starting to calling getRecovSearchBadActorData method"); 
		return recoveryService.getRecovSearchBadActorData(searchBadActorDataVO);			
	}
	
	@Operation(summary = "Retrieve recovery search details list for the selected recovery id.")
	@GetMapping("/recoverySearchdetailslist/{selectedRecoveryId}")
	public List<ITSRecovsearchDetailsVO> getITSRecoveryDetailsList(@PathVariable Long selectedRecoveryId) {
		logger.info("Starting to calling getITSRecoveryDetailsList method");
		List<ITSRecovsearchDetailsVO> list = null;
		try {
			if (selectedRecoveryId != null) {
				list = recoveryService.getITSRecoveryDetailsList(selectedRecoveryId);
			}
		} catch (BusinessException be) {
			logger.error("Business Exception in RecoveryController.getITSRecoveryDetailsList() method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryController.getITSRecoveryDetailsList() method." + be.getMessage());
		}
		
		return list;
	}
}
