package com.hcl.igovern.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ITSRecoveryUpdateVO {

	private Long ovpId;
	private Long victimBadActorXrefId;
	private Long badActorId;
	private String badActorSsn;
	private String prtyTaxId;
	private Double ovpTotal;
	private Double ovpBalance;
	private Long recoveryId;
	private String recoveryDate;
	private String recoveryStatus;
	private String recoveryEffDate;
	private Double totalPaymentAmount;
	private Double totalRecoveryAmount;
	private String allOvpInd;
	private String refundExcessId;
	private String paymentMethod;
	private Double paymentAmount;
	private String comment;
	private Double ovpBalanceCheck;
}
