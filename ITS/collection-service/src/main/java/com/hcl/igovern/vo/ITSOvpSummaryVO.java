package com.hcl.igovern.vo;

import java.sql.Timestamp;

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
	private Double ovpBalance;
	private String ovpstsCd;
	private String ovpdisCd;
	private String ovpclsCd;
	private String ovporgCd;
	private String ovpdorgCd;
	private String statusMessage;
}
