package com.hcl.igovern.vo;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import com.hcl.igovern.entity.ItsOverpaymentEO;

import lombok.Data;

@Data
@Component
public class ItsOverpaymentDetailsVO {
	
	private Long ovpdtlsId;
	private Long ovpId;
	private String ovptypCd;
	private Timestamp cbwkBweDt;
	private Long clmId;
	private Long addCompPrgDtlId;
	private ItsOverpaymentEO itsOverpayment;
	
	public Long getOvpdtlsId() {
		return ovpdtlsId;
	}
	public void setOvpdtlsId(Long ovpdtlsId) {
		this.ovpdtlsId = ovpdtlsId;
	}
	public Long getOvpId() {
		return ovpId;
	}
	public void setOvpId(Long ovpId) {
		this.ovpId = ovpId;
	}
	public String getOvptypCd() {
		return ovptypCd;
	}
	public void setOvptypCd(String ovptypCd) {
		this.ovptypCd = ovptypCd;
	}
	public Timestamp getCbwkBweDt() {
		return cbwkBweDt;
	}
	public void setCbwkBweDt(Timestamp cbwkBweDt) {
		this.cbwkBweDt = cbwkBweDt;
	}
	public Long getClmId() {
		return clmId;
	}
	public void setClmId(Long clmId) {
		this.clmId = clmId;
	}
	public Long getAddCompPrgDtlId() {
		return addCompPrgDtlId;
	}
	public void setAddCompPrgDtlId(Long addCompPrgDtlId) {
		this.addCompPrgDtlId = addCompPrgDtlId;
	}
	public ItsOverpaymentEO getItsOverpayment() {
		return itsOverpayment;
	}
	public void setItsOverpayment(ItsOverpaymentEO itsOverpayment) {
		this.itsOverpayment = itsOverpayment;
	}
}
