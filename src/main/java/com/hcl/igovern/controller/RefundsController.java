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
import com.hcl.igovern.service.RefundsService;
import com.hcl.igovern.validation.InputLongConstraint;
import com.hcl.igovern.vo.ITSRefundsDataVO;
import com.hcl.igovern.vo.SpecialChecksVO;

import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/its/apis/refunds")
@ResponseBody
@Validated
public class RefundsController {
	
	Logger logger = LoggerFactory.getLogger(RefundsController.class);
	
	@Autowired
	private RefundsService refundsService;

	public static final String ERR_CODE = "ERR_CODE";
	
	@Operation(summary = "Retrieve refunds list for the bad actor.")
	@GetMapping("/itsrefundslist/{badActorId}")
	public List<ITSRefundsDataVO> getITSRefundsListList(@PathVariable @InputLongConstraint String badActorId) {
		logger.info("Starting to calling getITSRefundsListList method");
		List<ITSRefundsDataVO> list = null;
		try {
			if (badActorId != null) {
				list = refundsService.getITSRefundsListList(Long.valueOf(badActorId));
			}
		} catch (BusinessException be) {
			logger.error("Business Exception in RefundsController.getITSRefundsListList() method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RefundsController.getITSRefundsListList() method." + be.getMessage());
		}
		
		return list;
	}
	
	@Operation(summary = "Retrieve refunds data for the given refund id.")
	@GetMapping("/itsrefundsobject/{selectedRefundsId}")
	public ITSRefundsDataVO getITSRefundInfo(@PathVariable @InputLongConstraint String selectedRefundsId ) {
		logger.info("Starting to calling getITSRefundInfo method");
		ITSRefundsDataVO itsRefundsDataVO = null;
		try {
			if (selectedRefundsId != null) {
				itsRefundsDataVO = refundsService.getITSRefundInfo(Long.valueOf(selectedRefundsId));
			}
		} catch (BusinessException be) {
			logger.error("Business Exception in RefundsController.getITSRefundInfo() method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RefundsController.getITSRefundInfo() method." + be.getMessage());
		}
		return itsRefundsDataVO;
	}
	
	@Operation(summary = "Retrieve bad actor data for the given bad actor id.")
	@GetMapping("/itsbadactorheaderobject/{badActorId}")
	public SpecialChecksVO getBadActorInfo(@PathVariable @InputLongConstraint String badActorId ) {
		logger.info("Starting to calling getBadActorInfo method");
		SpecialChecksVO specialChecksVO = null;
		try {
			if (badActorId != null) {
				specialChecksVO = refundsService.getBadActorInfo(Long.valueOf(badActorId));
			}
		} catch (BusinessException be) {
			logger.error("Business Exception in RefundsController.getBadActorInfo() method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RefundsController.getBadActorInfo() method." + be.getMessage());
		}
		return specialChecksVO;
	}
	
	@Operation(summary = "Save special check and its related data to database.")
	@PostMapping("/confirmspecialcheck")
	public SpecialChecksVO confirmSpecialCheck(@RequestBody SpecialChecksVO specialChecksVO) {
		SpecialChecksVO specialChecksVOResponse = new SpecialChecksVO();
		try {
			specialChecksVO = refundsService.confirmSpecialCheck(specialChecksVO);
			if (specialChecksVO != null && (specialChecksVO.getStatusMessage() == null || specialChecksVO.getStatusMessage().isEmpty())) {
				throw new BusinessException(ERR_CODE, "Something went wrong in RefundsController.confirmSpecialCheck() method.");
			}
			if (specialChecksVO != null)
				specialChecksVOResponse.setStatusMessage(specialChecksVO.getStatusMessage());
		} catch (BusinessException be) {
			logger.error("Business Exception in RefundsController.confirmSpecialCheck() method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RefundsController.confirmSpecialCheck() method." + be.getMessage());
		}
		return specialChecksVOResponse;
	}
}
