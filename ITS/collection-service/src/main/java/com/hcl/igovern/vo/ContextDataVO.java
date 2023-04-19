package com.hcl.igovern.vo;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ContextDataVO {
	
	private String badActorSSN;
	private String originalSSN;
	private Long originalClaimantId;
	private Long inputClaimId;
	private Long victimBadActorXrefId;
	
	public String getBadActorSSN() {
		return badActorSSN;
	}
	public void setBadActorSSN(String badActorSSN) {
		this.badActorSSN = badActorSSN;
	}
	public String getOriginalSSN() {
		return originalSSN;
	}
	public void setOriginalSSN(String originalSSN) {
		this.originalSSN = originalSSN;
	}
	public Long getOriginalClaimantId() {
		return originalClaimantId;
	}
	public void setOriginalClaimantId(Long originalClaimantId) {
		this.originalClaimantId = originalClaimantId;
	}
	public Long getInputClaimId() {
		return inputClaimId;
	}
	public void setInputClaimId(Long inputClaimId) {
		this.inputClaimId = inputClaimId;
	}
	public Long getVictimBadActorXrefId() {
		return victimBadActorXrefId;
	}
	public void setVictimBadActorXrefId(Long victimBadActorXrefId) {
		this.victimBadActorXrefId = victimBadActorXrefId;
	}
	

}
