package com.hcl.igovern.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ContextDataVO {
	
	private String badActorSSN;
	private String originalSSN;
	private Long originalClaimantId;
	private Long inputClaimId;
	private Long victimBadActorXrefId;
	private Long ovpId;
	private Long badActorId;
}
