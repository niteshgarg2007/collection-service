package com.hcl.igovern.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ITSAccountingReportVO {
	
	private String glctrlSubCd;
	private String glctrlSubDesc;
	private String itsGlctrlSubCd;
	private String prgfndAcctNo;
	private Double totalOvpAmount;
	private Double totalRecoveryAmount;
	private Double balance;
}
