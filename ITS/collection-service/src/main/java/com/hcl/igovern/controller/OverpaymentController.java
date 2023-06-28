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
import com.hcl.igovern.service.OverpaymentService;
import com.hcl.igovern.vo.ContextDataVO;
import com.hcl.igovern.vo.ITSOvpSummaryVO;
import com.hcl.igovern.vo.ITSOvpsearchDetailsVO;
import com.hcl.igovern.vo.ItsOverpaymentVO;
import com.hcl.igovern.vo.OverpaidWeeksVO;
import com.hcl.igovern.vo.SearchBadActorDataVO;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/its/apis/overpayment")
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "*")
@ResponseBody
public class OverpaymentController {
	
	Logger logger = LoggerFactory.getLogger(OverpaymentController.class);
	
	@Autowired
	private OverpaymentService overpaymentService;
	
	public static final String ERR_CODE = "ERR_CODE";

	@Operation(summary = "Save/Update overpayment and its related data to database.")
	@PostMapping("/addoverpayment")
	public ItsOverpaymentVO addOverpayment(@RequestBody ItsOverpaymentVO itsOverpaymentVO) {
		try {
			itsOverpaymentVO = overpaymentService.addOverpaymentAndDetails(itsOverpaymentVO);
		} catch (BusinessException be) {
			logger.error("Business Exception in OverpaymentController.addOverpayment() method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentController.addOverpayment() method." + be.getMessage());
		}
		return itsOverpaymentVO;
	}
	
	@Operation(summary = "Retrieve available program for the victim and bad actor combination.")
	@GetMapping("/programcodelist/{victimBadActorXrefId}")
	public List<OverpaidWeeksVO> getProgramCodeDDList(@PathVariable Long victimBadActorXrefId ) {
		logger.info("Starting to calling getProgramCodeDDList method");
		List<OverpaidWeeksVO> list = null;
		try {
			if (victimBadActorXrefId != null) {
				list = overpaymentService.getProgramCodeDDList(victimBadActorXrefId);
			}
		} catch (BusinessException be) {
			logger.error("Business Exception in OverpaymentController getProgramCodeDDList() method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentController.getProgramCodeDDList() method." + be.getMessage());
		}
		return list;
	}
	
	@Operation(summary = "Retrieve overpayment weeks for the victim and bad actor combination.")
	@PostMapping("/overpaidweekslist")
	public List<OverpaidWeeksVO> getOverpaidWeeksList(@RequestBody ContextDataVO contextData) {
		logger.info("Starting to calling getOverpaidWeeksList method");
		List<OverpaidWeeksVO> list = null;
		try {
			if (contextData.getInputClaimId() != null && contextData.getVictimBadActorXrefId() != null) {
				list = overpaymentService.getOverpaidWeeksList(contextData);
			}
		} catch (BusinessException be) {
			logger.error("Business Exception in OverpaymentController getOverpaidWeeksList() method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentController.getOverpaidWeeksList() method." + be.getMessage());
		}
		return list;
	}
	
	@Operation(summary = "Retrieve overpayment summary list for the victim and bad actor combination.")
	@GetMapping("/overpaymentsummarylist/{victimBadActorXrefId}")
	public List<ITSOvpSummaryVO> getITSOverpaymentSummaryList(@PathVariable Long victimBadActorXrefId) {
		logger.info("Starting to calling getITSOverpaymentSummaryList method");
		List<ITSOvpSummaryVO> list = null;
		try {
			if (victimBadActorXrefId != null) {
				list = overpaymentService.getITSOverpaymentSummaryList(victimBadActorXrefId);
			}
		} catch (BusinessException be) {
			logger.error("Business Exception in OverpaymentController getITSOverpaymentSummaryList() method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentController.getITSOverpaymentSummaryList() method." + be.getMessage());
		}
		
		return list;
	}
	
	@Operation(summary = "Retrieve overpayment details for the given overpayment.")
	@GetMapping("/overpaymentandovpdetails/{overpaymentId}")
	public ItsOverpaymentVO getITSOverpaymentAndOvpDetails(@PathVariable Long overpaymentId ) {
		logger.info("Starting to calling getITSOverpaymentAndOvpDetails method");
		ItsOverpaymentVO itsOverpaymentVO = null;
		try {
			if (overpaymentId != null) {
				itsOverpaymentVO = overpaymentService.getITSOverpaymentAndOvpDetails(overpaymentId);
			}
		} catch (BusinessException be) {
			logger.error("Business Exception in OverpaymentController getITSOverpaymentAndOvpDetails() method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentController.getITSOverpaymentAndOvpDetails() method." + be.getMessage());
		}
		return itsOverpaymentVO;
	}
	
	@Operation(summary = "Retrieve existing program for the victim and bad actor combination.")
	@PostMapping("/existingprogramcode")
	public OverpaidWeeksVO getExistingProgramCodeDD(@RequestBody ContextDataVO contextData) {
		logger.info("Starting to calling getExistingProgramCodeDD method");
		OverpaidWeeksVO overpaidWeeks = null;
		try {
			if (contextData.getVictimBadActorXrefId() != null && contextData.getOvpId() != null) {
				overpaidWeeks = overpaymentService.getExistingProgramCodeDD(contextData);
			}
		} catch (BusinessException be) {
			logger.error("Business Exception in OverpaymentController getExistingProgramCodeDD() method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentController.getExistingProgramCodeDD() method." + be.getMessage());
		}
		return overpaidWeeks;
	}
	
	@Operation(summary = "Retrieve existing overpayment weeks for the victim and bad actor combination.")
	@PostMapping("/existingoverpaidweekslist")
	public List<OverpaidWeeksVO> getExistingOverpaidWeeksList(@RequestBody ContextDataVO contextData) {
		logger.info("Starting to calling getExistingOverpaidWeeksList method");
		List<OverpaidWeeksVO> list = null;
		try {
			if (contextData.getInputClaimId() != null && contextData.getVictimBadActorXrefId() != null && contextData.getOvpId() != null) {
				list = overpaymentService.getExistingOverpaidWeeksList(contextData);
			}
		} catch (BusinessException be) {
			logger.error("Business Exception in OverpaymentController.getExistingOverpaidWeeksList() method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentController.getExistingOverpaidWeeksList() method." + be.getMessage());
		}
		return list;
	}
	
	@Operation(summary = "Retrieve updated overpayment weeks for the victim and bad actor combination.")
	@PostMapping("/overpaidweeksupdatedlist")
	public List<OverpaidWeeksVO> getOverpaidWeeksUpdatedList(@RequestBody ContextDataVO contextData) {
		logger.info("Starting to calling getOverpaidWeeksUpdatedList method");
		List<OverpaidWeeksVO> list = null;
		try {
			if (contextData.getInputClaimId() != null && contextData.getVictimBadActorXrefId() != null) {
				list = overpaymentService.getOverpaidWeeksUpdatedList(contextData);
			}
		} catch (BusinessException be) {
			logger.error("Business Exception in OverpaymentController.getOverpaidWeeksUpdatedList() method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentController.getOverpaidWeeksUpdatedList() method." + be.getMessage());
		}
		return list;
	}
	
	@Operation(summary = "Retrieve overpayment status history list for the selected overpayment id.")
	@GetMapping("/overpaymentstatushistorylist/{selectedOverpaymentId}")
	public List<ITSOvpSummaryVO> getITSOverpaymentStatusHistoryList(@PathVariable Long selectedOverpaymentId) {
		logger.info("Starting to calling getITSOverpaymentStatusHistoryList method");
		List<ITSOvpSummaryVO> list = null;
		try {
			if (selectedOverpaymentId != null) {
				list = overpaymentService.getITSOverpaymentStatusHistoryList(selectedOverpaymentId);
			}
		} catch (BusinessException be) {
			logger.error("Business Exception in OverpaymentController.getITSOverpaymentStatusHistoryList() method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentController.getITSOverpaymentStatusHistoryList() method." + be.getMessage());
		}
		
		return list;
	}
	
	@Operation(summary = "Retrieve bad actor overpayment data")
	@PostMapping("/overpaymentbadactorsearch")
	public List<ITSOvpSummaryVO> getOvpSearchBadActorData(@RequestBody SearchBadActorDataVO searchBadActorDataVO ) {
		logger.info("Starting to calling getITSOverpaymentStatusHistoryList method"); 
		return overpaymentService.getOvpSearchBadActorData(searchBadActorDataVO);			
	}
	
	@Operation(summary = "Retrieve overpayment search details list for the selected overpayment id.")
	@GetMapping("/overpaymentdetailslist/{selectedOverpaymentId}")
	public List<ITSOvpsearchDetailsVO> getITSOverpaymentDetailsList(@PathVariable Long selectedOverpaymentId) {
		logger.info("Starting to calling getITSOverpaymentDetailsList method");
		List<ITSOvpsearchDetailsVO> list = null;
		try {
			if (selectedOverpaymentId != null) {
				list = overpaymentService.getITSOverpaymentDetailsList(selectedOverpaymentId);
			}
		} catch (BusinessException be) {
			logger.error("Business Exception in OverpaymentController.getITSOverpaymentDetailsList() method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentController.getITSOverpaymentDetailsList() method." + be.getMessage());
		}
		
		return list;
	}
}
