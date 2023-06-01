package com.hcl.igovern.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ITSOvpsearchDetailsVO {

	private Long ovpdtlsId;
	private Long victimBadActorXrefId;
	private Long ovpId;
	private String ovptypCd;
	private String ovpstsCd;
	private String ovpdisCd;
	private String ovpclsCd;
	private String ovporgCd;
	private String ovpdorgCd;
	private Long clmId;
	private Long addCompPrgDtlId;
	private String cbwkBweDt;
	private Double ovpAmt;
	private Double recoveryAmt;
}
