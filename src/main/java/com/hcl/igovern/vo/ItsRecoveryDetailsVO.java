package com.hcl.igovern.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItsRecoveryDetailsVO {
	
	private Long recoveryDtlsId;
	private Long recoveryId;
	private Long victimBadActorXrefId;
	private String badActorSsn;
	private String originalSsn;
	private Long ovpId;
	private Double totalOvpAmount;
	private Double ovpCurrentBalance;
	private String paymentMethod;
	private Double paymentAmount;
	private String comment;
	private List<ItsOverpaymentTransactionsVO> itsOverpaymentTranDtlsVO;
	private Double ovpBalanceCheck;
}
