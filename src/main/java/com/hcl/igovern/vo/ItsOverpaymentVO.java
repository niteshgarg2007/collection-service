package com.hcl.igovern.vo;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
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

	public Long getOvpId() {
		return ovpId;
	}

	public void setOvpId(Long ovpId) {
		this.ovpId = ovpId;
	}

	public Long getVictimBadActorXrefId() {
		return victimBadActorXrefId;
	}

	public void setVictimBadActorXrefId(Long victimBadActorXrefId) {
		this.victimBadActorXrefId = victimBadActorXrefId;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getDateModified() {
		return dateModified;
	}

	public void setDateModified(String dateModified) {
		this.dateModified = dateModified;
	}

	public String getOvpdisCd() {
		return ovpdisCd;
	}

	public void setOvpdisCd(String ovpdisCd) {
		this.ovpdisCd = ovpdisCd;
	}

	public String getOvpclsCd() {
		return ovpclsCd;
	}

	public void setOvpclsCd(String ovpclsCd) {
		this.ovpclsCd = ovpclsCd;
	}

	public String getOvpstsCd() {
		return ovpstsCd;
	}

	public void setOvpstsCd(String ovpstsCd) {
		this.ovpstsCd = ovpstsCd;
	}

	public String getOvporgCd() {
		return ovporgCd;
	}

	public void setOvporgCd(String ovporgCd) {
		this.ovporgCd = ovporgCd;
	}

	public String getOvpdorgCd() {
		return ovpdorgCd;
	}

	public void setOvpdorgCd(String ovpdorgCd) {
		this.ovpdorgCd = ovpdorgCd;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public Double getCoc() {
		return coc;
	}

	public void setCoc(Double coc) {
		this.coc = coc;
	}

	public Double getPenalty() {
		return penalty;
	}

	public void setPenalty(Double penalty) {
		this.penalty = penalty;
	}

	public String getProgCode() {
		return progCode;
	}

	public void setProgCode(String progCode) {
		this.progCode = progCode;
	}

	public Long getClaimId() {
		return claimId;
	}

	public void setClaimId(Long claimId) {
		this.claimId = claimId;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public List<OverpaidWeeksVO> getItsOverpaymentDtls() {
		return itsOverpaymentDtls;
	}

	public void setItsOverpaymentDtls(List<OverpaidWeeksVO> itsOverpaymentDtls) {
		this.itsOverpaymentDtls = itsOverpaymentDtls;
	}
}
