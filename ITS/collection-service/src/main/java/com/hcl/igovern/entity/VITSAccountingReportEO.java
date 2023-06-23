package com.hcl.igovern.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name="V_ITS_ACCOUNTING_REPORT")
public class VITSAccountingReportEO implements Serializable {
	
	private static final long serialVersionUID = 5275862876944738944L;
	
	@Id
	@Column(name="GLCTRL_SUB_CD")
	private String glctrlSubCd;
	
	@Column(name="GLCTRL_SUB_DESC")
	private String glctrlSubDesc;
	
	@Column(name="ITS_GLCTRL_SUB_CD")
	private String itsGlctrlSubCd;
	
	@Column(name="PRGFND_ACCT_NO")
	private String prgfndAcctNo;
	
	@Column(name="TOTAL_OVP_AMOUNT")
	private Double totalOvpAmount;
	
	@Column(name="TOTAL_RECOVERY_AMOUNT")
	private Double totalRecoveryAmount;
	
	@Column(name="BALANCE")
	private Double balance;

}
