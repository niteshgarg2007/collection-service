package com.hcl.igovern.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.hcl.igovern.auditing.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="ITS_CLAIMANT_DETAILS")
public class ItsClaimantDetailsEO extends Auditable<String> implements Serializable {
	
	private static final long serialVersionUID = -3448750670870767438L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CLMT_DTL_ID")
	private Long clmtDtlId;
	
	@Column(name="PRTY_ID")
	private Long prtyId;
	
	@Column(name="PRTY_TAX_ID")
	private String prtyTaxId;
	
	@Column(name="PNMH_FRST_NM")
	private String pnmhFrstNm;
	
	@Column(name="PNMH_LST_NM")
	private String pnmhLstNm;
	
	@Column(name="PNMH_MI")
	private String pnmhMi;
	
	@Column(name="PNMH_SUFX")
	private String pnmhSufx;
	
	@Column(name="PRTY_BIRTH_DT")
	private Timestamp prtyBirthDt;
	
	@Column(name="PRTY_DEATH_DT")
	private Timestamp prtyDeathDt;
	
	@Column(name="CLMT_GNDR")
	private String clmtGndr;
	
	@Column(name="PRTY_US_CITIZEN_IND")
	private String prtyUsCitizenInd;
	
	@Column(name="OFFC_ID")
	private Long offcId;
	
	@Column(name="PRTY_EFT_ACCT_TYP")
	private String prtyEftAcctTyp;
	
	@Column(name="CLM_WTHHLD_OPT_IND")
	private String clmWthhldInd;
	
	@Column(name="AUTH_DOC_TYPE_CD")
	private String authDocTypeCd;
	
	@Column(name="PRTY_REG")
	private String prtyReg;
	
	@Column(name="PRTY_REG_DT")
	private Timestamp prtyRegDt;
	
	@Column(name="CSS_PW_STS")
	private String cssPwSts;
	
	@Column(name="CHLD_SUP_IND")
	private String chldSupInd;
	
	@Column(name="CLMT_DSAB_IND")
	private String clmtDsabInd;
	
	@Column(name="IVRPSTS_CD")
	private String ivrpstsCd;
	
	@Column(name="PRTY_USERNAME")
	private String prtyUsername;
	
	@Column(name="RACE_CD")
	private String raceCd;
	
	@Column(name="ETHN_CD")
	private String ethnCd;
	
	@Column(name="CLMT_VET_IND")
	private String clmtVetInd;
	
	@Column(name="EDLVL_CD")
	private String edlvlCd;
	
	@Column(name="TTY_IND")
	private String ttyInd;
	
	@Column(name="INTERPRETER_IND")
	private String interpreterInd;
	
	@Column(name="LNGTYP_CD")
	private String lngtypCd;
	
	@Column(name="ADRH_PRI_PHN_NO")
	private String adrhPriPhnNo;
	
	@Column(name="ADRH_EMAIL_ADDR")
	private String adrhEmailAddr;
	
	@Column(name="ADRH_PRFR_CNTCT_MTHD")
	private String adrhPrfrCntctMthd;
	
	@Column(name="PRTY_ACS_EFT_IND")
	private String prtyAcsEftInd;
	
	@Column(name="PERSONAL_ID_NUMBER")
	private String personalIdNumber;
	
	@Column(name="PAY_MTHD_LAST_MODIFIED_DT")
	private Timestamp payMthdLastModifiedDt;
	
	@Column(name="PAY_MTHD_LAST_MODIFIED_BY")
	private String payMthdLastModifiedBy;
	
	@Column(name="PRTY_NAIC_CD")
	private Long prtyNaicCd;
	
	@Column(name="PRTY_FED_LOC_IND")
	private String prtyFedLocInd;
	
	@Column(name="AUTH_DOC_NUMBER")
	private String authDocNumber;
	
	@Column(name="AUTH_DOC_SUBTYPE_CD")
	private String authDocSubtypeCd;
}
