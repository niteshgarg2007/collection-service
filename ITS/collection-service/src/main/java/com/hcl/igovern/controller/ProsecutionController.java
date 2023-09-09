package com.hcl.igovern.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.igovern.exception.BusinessException;
import com.hcl.igovern.service.ProsecutionService;
import com.hcl.igovern.validation.InputLongConstraint;
import com.hcl.igovern.vo.ContextDataVO;
import com.hcl.igovern.vo.ITSOvpSummaryVO;
import com.hcl.igovern.vo.ITSProsecutionHistoryVO;
import com.hcl.igovern.vo.ITSProsecutionListVO;
import com.hcl.igovern.vo.ITSProsecutionSummaryVO;
import com.hcl.igovern.vo.ItsProsecutionVO;
import com.hcl.igovern.vo.ItsProsecutionsOverpaymentXrefVO;
import com.hcl.igovern.vo.SearchBadActorDataVO;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/its/apis/prosecution")
@ResponseBody
@Validated
public class ProsecutionController {

Logger logger = LoggerFactory.getLogger(ProsecutionController.class);
	
	@Autowired
	private ProsecutionService prosecutionService;
	
	public static final String ERR_CODE = "ERR_CODE";
	
	@Operation(summary = "Retrieve overpayment details for the victim and bad actor combination or bad actor.")
	@PostMapping("/overpaymentdetailslist")
	public List<ITSOvpSummaryVO> getOverpaymentDetailsList(@RequestBody ContextDataVO contextData) {
		logger.info("Starting to calling getOverpaymentDetailsList method");
		List<ITSOvpSummaryVO> list = null;
		try {
			if (contextData.getVictimBadActorXrefId() != null) {
				list = prosecutionService.getOverpaymentDetailsList(contextData);
			}
			
			if (contextData.getBadActorId() != null) {
				list = prosecutionService.getOverpaymentDetailsList(contextData);
			}
		} catch (BusinessException be) {
			logger.error("Business Exception in ProsecutionController.getOverpaymentDetailsList() method");
			throw new BusinessException(ERR_CODE, "Something went wrong in ProsecutionController.getOverpaymentDetailsList() method." + be.getMessage());
		}
		return list;
	}
	
	@Operation(summary = "Save prosecution and its related data to database.")
	@PostMapping("/addprosecution")
	public ItsProsecutionVO addProsecution(@RequestBody ItsProsecutionVO itsProsecutionVO) {
		ItsProsecutionVO itsProsecutionVOResponse = new ItsProsecutionVO();
		try {
			itsProsecutionVO = prosecutionService.addProsecutionAndDetails(itsProsecutionVO);
			if (itsProsecutionVO != null && (itsProsecutionVO.getStatusMessage() == null || itsProsecutionVO.getStatusMessage().isEmpty())) {
				throw new BusinessException(ERR_CODE, "Something went wrong in ProsecutionController.addProsecution() method.");
			}
		} catch (BusinessException be) {
			logger.error("Business Exception in ProsecutionController.addProsecution() method");
			throw new BusinessException(ERR_CODE, "Something went wrong in ProsecutionController.addProsecution() method." + be.getMessage());
		}
		if (itsProsecutionVO != null) {
			itsProsecutionVOResponse.setStatusMessage(itsProsecutionVO.getStatusMessage());
			itsProsecutionVOResponse.setProsId(itsProsecutionVO.getProsId());
		}
		
		return itsProsecutionVOResponse;
	}
	
	@Operation(summary = "Retrieve prosecution summary list for the victim and bad actor combination.")
	@GetMapping("/prossummarylist/{victimBadActorXrefId}")
	public List<ITSProsecutionSummaryVO> getITSProsecutionSummaryList(@PathVariable @InputLongConstraint String victimBadActorXrefId) {
		logger.info("Starting to calling getITSProsecutionSummaryList method");
		List<ITSProsecutionSummaryVO> list = null;
		try {
			if (victimBadActorXrefId != null) {
				list = prosecutionService.getITSProsecutionSummaryList(Long.valueOf(victimBadActorXrefId));
			}
		} catch (BusinessException be) {
			logger.error("Business Exception in ProsecutionController.getITSProsecutionSummaryList() method");
			throw new BusinessException(ERR_CODE, "Something went wrong in ProsecutionController.getITSProsecutionSummaryList() method." + be.getMessage());
		}
		
		return list;
	}
	
	@Operation(summary = "Retrieve prosecution history list for the selected prosecution id.")
	@GetMapping("/proshistorylist/{selectedProsId}")
	public List<ITSProsecutionHistoryVO> getITSProsHistoryList(@PathVariable @InputLongConstraint String selectedProsId) {
		logger.info("Starting to calling getITSRecoveryHistoryList method");
		List<ITSProsecutionHistoryVO> list = null;
		try {
			if (selectedProsId != null) {
				list = prosecutionService.getITSProsHistoryList(Long.valueOf(selectedProsId));
			}
		} catch (BusinessException be) {
			logger.error("Business Exception in ProsecutionController.getITSProsHistoryList() method");
			throw new BusinessException(ERR_CODE, "Something went wrong in ProsecutionController.getITSProsHistoryList() method." + be.getMessage());
		}
		
		return list;
	}
	
	@Operation(summary = "Retrieve prosecution data for the given prosecution id.")
	@GetMapping("/itsprosecutiondata/{selectedProsId}")
	public ItsProsecutionVO getITSProsDataByProsId(@PathVariable @InputLongConstraint String selectedProsId ) {
		logger.info("Starting to calling getITSProsDataByProsId method");
		ItsProsecutionVO itsProsecutionVO = null;
		try {
			if (selectedProsId != null) {
				itsProsecutionVO = prosecutionService.getITSProsDataByProsId(Long.valueOf(selectedProsId));
			}
		} catch (BusinessException be) {
			logger.error("Business Exception in ProsecutionController.getITSProsDataByProsId() method");
			throw new BusinessException(ERR_CODE, "Something went wrong in ProsecutionController.getITSProsDataByProsId() method." + be.getMessage());
		}
		return itsProsecutionVO;
	}
	
	@Operation(summary = "Retrieve existing prosecution overpayment xref data.")
	@PostMapping("/existingprosovpxreflist")
	public List<ItsProsecutionsOverpaymentXrefVO> getExistingProsecutionOvpXref(@RequestBody ContextDataVO contextData) {
		logger.info("Starting to calling getExistingProsecutionOvpXref method");
		List<ItsProsecutionsOverpaymentXrefVO> list = null;
		try {
			list = prosecutionService.getExistingProsecutionOvpXref(contextData);
		} catch (BusinessException be) {
			logger.error("Business Exception in ProsecutionController.getExistingProsecutionOvpXref() method");
			throw new BusinessException(ERR_CODE, "Something went wrong in ProsecutionController.getExistingProsecutionOvpXref() method." + be.getMessage());
		}
		return list;
	}
	
	@Operation(summary = "Retrieve existing prosecution overpayment xref and overpayment details.")
	@PostMapping("/prosovpxrefUpdatelist")
	public List<ItsProsecutionsOverpaymentXrefVO> getOverpaymentUpdateDetailsListByParams(@RequestBody ContextDataVO contextData) {
		logger.info("Starting to calling getOverpaymentUpdateDetailsListByParams method");
		List<ItsProsecutionsOverpaymentXrefVO> list = null;
		try {
			list = prosecutionService.getOverpaymentUpdateDetailsListByParams(contextData);
		} catch (BusinessException be) {
			logger.error("Business Exception in ProsecutionController.getOverpaymentUpdateDetailsListByParams() method");
			throw new BusinessException(ERR_CODE, "Something went wrong in ProsecutionController.getOverpaymentUpdateDetailsListByParams() method." + be.getMessage());
		}
		return list;
	}
	
	@Operation(summary = "Retrieve bad actor prosecution data")
	@PostMapping("/prosbadactorsearch")
	public List<ITSProsecutionListVO> getSearchBadActorData(@RequestBody SearchBadActorDataVO searchBadActorDataVO ) {
		logger.info("Starting to calling getRecovSearchBadActorData method"); 
		return prosecutionService.getSearchBadActorData(searchBadActorDataVO);			
	}
	
	@Operation(summary = "Retrieve prosecution search details list for the selected prosecution id.")
	@GetMapping("/prosdetailslist/{selectedProsecutionId}")
	public List<ItsProsecutionsOverpaymentXrefVO> getITSProsecutionDetailsList(@PathVariable @InputLongConstraint String selectedProsecutionId) {
		logger.info("Starting to calling getITSRecoveryDetailsList method");
		List<ItsProsecutionsOverpaymentXrefVO> list = null;
		try {
			if (selectedProsecutionId != null) {
				list = prosecutionService.getITSProsecutionDetailsList(Long.valueOf(selectedProsecutionId));
			}
		} catch (BusinessException be) {
			logger.error("Business Exception in RecoveryController.getITSProsecutionDetailsList() method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryController.getITSProsecutionDetailsList() method." + be.getMessage());
		}
		
		return list;
	}
}
