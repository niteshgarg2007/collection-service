package com.hcl.igovern.vo;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ITSProsecutionUpdateVO {
	
	private Long ovpId;
	private Long victimBadActorXrefId;
	private Long badActorId;
	private String badActorSsn;
	private String prtyTaxId;
	private Double ovpBalance;
	private Timestamp dateCreated;
	private Double ovpTotal;
	private Double recoveryAmount;
	private Double ovpPenalty;
	private Double ovpInterest;
	private Double ovpCoc;
	private Double restiAmount;
	private String ovpstsCd;
	private String ovpdisCd;
	private Long prosId;
	private Double resiAmtInput;
	private Long prosOvpXrefId;

}
