package com.hcl.igovern.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItsOverpaymentVO {
	
	private Long ovpId;
	private Long victimBadActorXrefId;
	private String dateCreated;
	private String dateModified;
	private String ovpdisCd;
	private String ovpclsCd;
	private String ovpstsCd;
	private String ovporgCd;
	private String ovpdorgCd;
	private Double amount;
	private Double interest;
	private Double coc;
	private Double penalty;
	private String progCode;
	private Long claimId; 
	private String statusMessage;
	private List<OverpaidWeeksVO> itsOverpaymentDtls;
}
