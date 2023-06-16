package com.hcl.igovern.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ITSProsecutionSummaryVO {
	
	private Long prosId;
	private Long victimBadActorXrefId;
	private Long badActorId;
	private String badActorSsn;
	private String prtyTaxId;
	private String prosVenue;
	private String caseNo;
	private String warrantNo;
	private String warrantReqDt;
	private String iniCourtDt;
	private String prosDisp;
	private String continuedDt;
	private String convictionDt;
	private Double resiAmtTot;
	
}
