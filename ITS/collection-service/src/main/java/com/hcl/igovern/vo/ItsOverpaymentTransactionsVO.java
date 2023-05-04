package com.hcl.igovern.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItsOverpaymentTransactionsVO {
	
	private Long ovptransId;
	private Long ovpdtlsId;
	private Long recoveryDtlsId;
	private Double transAmount;
}
