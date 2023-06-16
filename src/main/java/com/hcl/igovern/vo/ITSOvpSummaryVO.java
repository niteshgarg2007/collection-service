package com.hcl.igovern.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ITSOvpSummaryVO {
	
	private Long ovpId;
	private Long victimBadActorXrefId;
	private Double ovpAmount;
	private Double ovpInterest;
	private Double ovpPenalty;
	private Double ovpCoc;
	private Double recoveryAmount;
	private Double restiAmount;
	private Double ovpTotal;
	private Double ovpBalance;
	private String ovpstsCd;
	private String ovpdisCd;
	private String ovpclsCd;
	private String ovporgCd;
	private String ovpdorgCd;
	private String statusMessage;
	private String dateCreated;
	private String createdBy;
	private Long badActorId;
	private String badActorSsn;
	private String prtyTaxId;
	private Double ovpCurrentBalance;
	private String badActorName;
	private String costCode;
	private String currentOverpaymentStatus;
	private Long prosId;
	private Long prosOvpXrefId;
	private Double resiAmtInput;
}
