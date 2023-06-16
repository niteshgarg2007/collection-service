package com.hcl.igovern.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItsProsecutionsOverpaymentXrefVO {

	private Long prosOvpXrefId;
	private Long prosId;
	private Long ovpId;
	private Double resiAmt;
	private String endDt;
	private String dateCreated;
	private Double ovpTotal;
	private Double recoveryAmount;
	private Double ovpPenalty;
	private Double ovpInterest;
	private Double ovpCoc;
	private Double restiAmount;
	private String ovpstsCd;
	private String ovpdisCd;
	private String badActorSsn;
	private String prtyTaxId;
	private Double ovpBalance;
}
