package com.hcl.igovern.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ITSProsecutionListVO {

	private Long prosId;
	private Long badActorId;
	private String prosVenue;
	private String caseNo;
	private String warrantNo;
	private String warrantReqDt;
	private String iniCourtDt;
	private String prosDisp;
	private String continuedDt;
	private String convictionDt;
	private Double resiAmt;
	private String dateCreated;
	private String badActorSsn;
	private String badActorName;
}
