package com.hcl.igovern.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.igovern.exception.BusinessException;
import com.hcl.igovern.service.AccountingService;
import com.hcl.igovern.vo.ITSAccountingReportVO;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/its/apis/accounting")
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "*")
@ResponseBody
public class AccountingController {

	Logger logger = LoggerFactory.getLogger(AccountingController.class);
	
	@Autowired
	private AccountingService accountingService;

	public static final String ERR_CODE = "ERR_CODE";
	
	@Operation(summary = "Retrieve accounting report list.")
	@GetMapping("/itsaccountingreportlist")
	public List<ITSAccountingReportVO> getITSAccountingReport() {
		logger.info("Starting to calling getITSAccountingReport method");
		List<ITSAccountingReportVO> list = null;
		try {
			list = accountingService.getITSAccountingReport();
		} catch (BusinessException be) {
			logger.error("Business Exception in AccountingController.getITSAccountingReport() method");
			throw new BusinessException(ERR_CODE, "Something went wrong in AccountingController.getITSAccountingReport() method." + be.getMessage());
		}
		
		return list;
	}
}
