package com.hcl.igovern.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.igovern.entity.VITSAccountingReportEO;
import com.hcl.igovern.exception.BusinessException;
import com.hcl.igovern.repository.VITSAccountingReportRepository;
import com.hcl.igovern.service.AccountingService;
import com.hcl.igovern.vo.ITSAccountingReportVO;

@Service
public class AccountingServiceImpl implements AccountingService {

	Logger logger = LoggerFactory.getLogger(AccountingServiceImpl.class);
	
	@Autowired
	private VITSAccountingReportRepository vITSAccountingReportRepository;
	
	public static final String ERR_CODE = "ERR_CODE";

	@Override
	public List<ITSAccountingReportVO> getITSAccountingReport() {
		List<VITSAccountingReportEO> vITSAccountingReportEOList = null;
		List<ITSAccountingReportVO> itsAccountingReportVOList = new ArrayList<>();
		try {
			vITSAccountingReportEOList = vITSAccountingReportRepository.findAll();
			if (!vITSAccountingReportEOList.isEmpty()) {
				itsAccountingReportVOList = vITSAccountingReportEOList.stream().map(vITSAccountingReportEOObj -> {
					ITSAccountingReportVO accountingReportVOObj = new ITSAccountingReportVO();
					BeanUtils.copyProperties(vITSAccountingReportEOObj, accountingReportVOObj);
					return accountingReportVOObj;
				}).collect(Collectors.toList());
			}
		} catch (BusinessException e) {
			logger.error("Business Exception in AccountingServiceImpl.getITSAccountingReport method");
			throw new BusinessException(ERR_CODE, "Something went wrong in AccountingServiceImpl.getITSAccountingReport() method." + e.getMessage());
		}
		return itsAccountingReportVOList;
	}
}
