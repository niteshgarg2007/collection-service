package com.hcl.igovern.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SpecialChecksVO {

	private Long splChckId;
	private Long clmId;
	private Long prtyId;
	private String cprolCd;
	private String splChckNo;
	private String payToOrderOf;
	private String addrLine1;
	private String addrLine2;
	private String cityNm;
	private String sttCd;
	private String zipCd;
	private String zipPlusFour;
	private Double chkAmt;
	private String reasons;
	private String comments;
	private String glctrlSubCd;
	private Long chckId;
	private String statusMessage;
	private Long itsRefundId;
}
