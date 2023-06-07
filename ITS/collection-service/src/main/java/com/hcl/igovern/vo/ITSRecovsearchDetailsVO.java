package com.hcl.igovern.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ITSRecovsearchDetailsVO {

	private Long recoveryDtlsId;
	private Long recoveryId;
	private Long ovpId;
	private String paymentMethod;
	private Double paymentAmount;
	private String comment;
	private String badActorSsn;
	private String prtyTaxId;
	private String recoveryStatus;
}
