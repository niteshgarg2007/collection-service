package com.hcl.igovern.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ITSRecoveryHistoryVO {

	private Long recoveryId;
	private String recoveryDate;
	private String recoveryStatus;
	private Double totalRecoveryAmount;
	private String dateCreated;
	private String createdBy;
}
