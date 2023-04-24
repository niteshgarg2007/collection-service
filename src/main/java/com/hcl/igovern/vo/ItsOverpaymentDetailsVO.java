package com.hcl.igovern.vo;

import java.sql.Timestamp;

import com.hcl.igovern.entity.ItsOverpaymentEO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItsOverpaymentDetailsVO {
	
	private Long ovpdtlsId;
	private Long ovpId;
	private String ovptypCd;
	private Timestamp cbwkBweDt;
	private Long clmId;
	private Long addCompPrgDtlId;
	private ItsOverpaymentEO itsOverpayment;
}
