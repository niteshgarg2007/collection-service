package com.hcl.igovern.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ITSProsecutionHistoryVO {

	private Long proshistId;
	private Long prosId;
	private String prosVenue;
	private String caseNo;
	private String warrantNo;
	private String prosDisp;
	private Double resiAmtTot;
	private String dateCreated;
	private String createdBy;
	private String dateModified;
	private String modifiedBy;
}
