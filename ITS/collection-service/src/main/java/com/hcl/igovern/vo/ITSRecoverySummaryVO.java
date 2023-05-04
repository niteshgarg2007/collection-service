package com.hcl.igovern.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ITSRecoverySummaryVO {
	
	private Long recoveryId;
	private Long victimBadActorXrefId;
	private Long badActorId;
	private String recoveryDate;
	private String recoveryEffDate;
	private String recoveryStatus;
	private Double totalRecoveryAmount;
	private Double appliedAmount;
}
