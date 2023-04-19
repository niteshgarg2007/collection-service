package com.hcl.igovern.vo;

public class OverpaidWeeksVO {

	private Long claimId;
	private String cbwkBweDt;
	private Double paymentAmount;
	private Double recoveryAmount;
	private String prgmCd;
	private String fundCode;
	private Long victimBadActorXrefId;
	
	public Long getClaimId() {
		return claimId;
	}
	public void setClaimId(Long claimId) {
		this.claimId = claimId;
	}
	public String getCbwkBweDt() {
		return cbwkBweDt;
	}
	public void setCbwkBweDt(String cbwkBweDt) {
		this.cbwkBweDt = cbwkBweDt;
	}
	public Double getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public Double getRecoveryAmount() {
		return recoveryAmount;
	}
	public void setRecoveryAmount(Double recoveryAmount) {
		this.recoveryAmount = recoveryAmount;
	}
	public String getPrgmCd() {
		return prgmCd;
	}
	public void setPrgmCd(String prgmCd) {
		this.prgmCd = prgmCd;
	}
	public String getFundCode() {
		return fundCode;
	}
	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
	}
	public Long getVictimBadActorXrefId() {
		return victimBadActorXrefId;
	}
	public void setVictimBadActorXrefId(Long victimBadActorXrefId) {
		this.victimBadActorXrefId = victimBadActorXrefId;
	}
}
