package com.hcl.igovern.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItsRecoveryVO {
	
	private Long recoveryId;
	private Long badActorId;
	private String recoveryDate;
	private String recoveryStatus;
	private String recoveryEffDate;
	private Double totalPaymentAmount;
	private Double totalRecoveryAmount;
	private String allOvpInd;
	private String refundExcessInd;
	private Long victimBadActorXrefId;
	private Long ovpId;
	private String dateCreated;
	private String dateModified;
	private String statusMessage;
	private List<ItsRecoveryDetailsVO> itsRecoveryDtlsVO;
}
