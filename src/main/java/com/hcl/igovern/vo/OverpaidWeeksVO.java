package com.hcl.igovern.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OverpaidWeeksVO {

	private Long claimId;
	private String cbwkBweDt;
	private Double paymentAmount;
	private Double recoveryAmount;
	private String prgmCd;
	private String fundCode;
	private Long victimBadActorXrefId;
}
