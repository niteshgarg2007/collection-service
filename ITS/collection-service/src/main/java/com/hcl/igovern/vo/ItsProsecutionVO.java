package com.hcl.igovern.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItsProsecutionVO {
	
	private Long prosId;
	private Long badActorId;
	private String claimantLocality;
	private Long prosVenueId;
	private String caseNo;
	private String warrantReqDt;
	private String warrantNo;
	private String iniCourtDt;
	private String bpcProsDispCd;
	private String continuedDt;
	private String convictionDt;
	private String restitutionOrderedInd;
	private Double resiAmt;
	private String sentence;
	private String comments;
	private String allOvpInd;
	private String statusMessage;
	private List<ItsProsecutionsOverpaymentXrefVO> itsProsecutionsOverpaymentXrefs;
}
