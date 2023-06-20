package com.hcl.igovern.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hcl.igovern.entity.PITSRecoveryDstPercentageEO;
import com.hcl.igovern.entity.VITSOverpaidWeeksEO;
import com.hcl.igovern.entity.VITSOverpaidWeeksUpdateEO;
import com.hcl.igovern.entity.VITSOvpSummaryEO;
import com.hcl.igovern.entity.VITSProsecutionListEO;
import com.hcl.igovern.entity.VITSRecoverySummaryEO;
import com.hcl.igovern.exception.BusinessException;
import com.hcl.igovern.util.DateUtil;
import com.hcl.igovern.vo.ItsProsecutionsOverpaymentXrefVO;
import com.hcl.igovern.vo.SearchBadActorDataVO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.TypedQuery;

@Component
public class CommonEntityManagerRepository {
	
	Logger logger = LoggerFactory.getLogger(CommonEntityManagerRepository.class);

	@Autowired
	private EntityManager entityManager;
	
	private static final String ERR_CODE = "ERR_CODE";
	private static final String AND_ADDED = " and ";
	private static final DateTimeFormatter DATE_FORMATTER_yyyy_MM_dd = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final String DATE_CREATED_BETWEEN = " CAST(ref.dateCreated AS date) between :frdt and :tdt";
	private static final String BAD_ACTOR_NAME_LIKE = " ref.badActorName LIKE :badActorName ";
	private static final String BAD_ACTOR_NAME = "badActorName";
	private static final String BAD_ACTOR_SSN = "badActorSsn";
	private static final String BAD_ACTOR_SSN_PARAM = "ref.badActorSsn = :badActorSsn";
	
	public List<VITSOverpaidWeeksEO> getProgramCodeDDList(Long victimBadActorXrefId) {
		List<?> vITSOverpaidWeeksListTemp = null;
		List<VITSOverpaidWeeksEO> vITSOverpaidWeeksList = new ArrayList<>();
		VITSOverpaidWeeksEO vITSOverpaidWeeks = null;
		String query=null;
        String sql=null;
		try {
			query = "SELECT DISTINCT CLM_ID,PRGM_CD FROM V_ITS_OVERPAID_WEEKS WHERE VICTIM_BAD_ACTOR_XREF_ID =?";
			sql = String.format(query);
			vITSOverpaidWeeksListTemp = entityManager.createNativeQuery(sql).setParameter(1, victimBadActorXrefId).getResultList();
			if(vITSOverpaidWeeksListTemp!=null && !vITSOverpaidWeeksListTemp.isEmpty()) {
				for (int i = 0; i < vITSOverpaidWeeksListTemp.size(); i++) {
					vITSOverpaidWeeks = new VITSOverpaidWeeksEO();
					Object[] val = (Object[])vITSOverpaidWeeksListTemp.get(i);
					if (val != null && val.length > 0 && val[0] != null && val[0] instanceof BigDecimal) {
						Long claimId = Long.valueOf(((BigDecimal) val[0]).longValue());
						vITSOverpaidWeeks.setClmId(claimId); 
					}
					
					if (val != null && val.length > 0 && val[1] != null) {
						String prgmCd = (String) val[1];
						vITSOverpaidWeeks.setPrgmCd(prgmCd);
					}
					
					vITSOverpaidWeeksList.add(vITSOverpaidWeeks);
				}
			}
		} catch (Exception e) {
			logger.error("Exception in CommonEntityManagerRepository while calling getProgramCodeDDList method");
			throw new BusinessException(ERR_CODE, "Exception in CommonEntityManagerRepository while calling getProgramCodeDDList method" +e.getMessage());
		}
		return vITSOverpaidWeeksList;
	}

	public VITSOverpaidWeeksUpdateEO getExistingProgramCodeDD(Long victimBadActorXrefId, Long ovpId, String isCancelled) {
		List<?> vITSOverpaidWeeksUpdateListTemp = null;
		List<VITSOverpaidWeeksUpdateEO> vITSOverpaidWeeksExistingList = new ArrayList<>();
		VITSOverpaidWeeksUpdateEO vITSOverpaidWeeksUpdate = null;
		String query=null;
        String sql=null;
		try {
			query = "SELECT DISTINCT CLM_ID,PRGM_CD FROM V_ITS_OVERPAID_WEEKS_UPDATE WHERE VICTIM_BAD_ACTOR_XREF_ID =? AND OVP_ID=? AND IS_CANCELLED=?";
			sql = String.format(query);
			vITSOverpaidWeeksUpdateListTemp = entityManager.createNativeQuery(sql).setParameter(1, victimBadActorXrefId).setParameter(2, ovpId).setParameter(3, isCancelled).getResultList();
			if(vITSOverpaidWeeksUpdateListTemp!=null && !vITSOverpaidWeeksUpdateListTemp.isEmpty()) {
				for (int i = 0; i < vITSOverpaidWeeksUpdateListTemp.size(); i++) {
					vITSOverpaidWeeksUpdate = new VITSOverpaidWeeksUpdateEO();
					Object[] val = (Object[])vITSOverpaidWeeksUpdateListTemp.get(i);
					if (val != null && val.length > 0 && val[0] != null && val[0] instanceof BigDecimal) {
						Long claimId = Long.valueOf(((BigDecimal) val[0]).longValue());
						vITSOverpaidWeeksUpdate.setClmId(claimId); 
					}
					
					if (val != null && val.length > 0 && val[1] != null) {
						String prgmCd = (String) val[1];
						vITSOverpaidWeeksUpdate.setPrgmCd(prgmCd);
					}
					
					vITSOverpaidWeeksExistingList.add(vITSOverpaidWeeksUpdate);
				}
				vITSOverpaidWeeksUpdate = vITSOverpaidWeeksExistingList.get(0);
			}
		} catch (Exception e) {
			logger.error("Exception in CommonEntityManagerRepository while calling getExistingProgramCodeDD method");
			throw new BusinessException(ERR_CODE, "Exception in CommonEntityManagerRepository while calling getExistingProgramCodeDD method" +e.getMessage());
		}
		return vITSOverpaidWeeksUpdate;
	}

	public List<PITSRecoveryDstPercentageEO> getITSRecoveryDstPercentage(Long ovpdtlsId) {
		try {    	   
			StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("P_ITS_RECOVERY_DST_PERCENTAGE");
			query.setParameter("OVPDTLS_ID", ovpdtlsId);
			return query.getResultList();
		} catch(Exception e) {
			logger.error("Exception in CommonEntityManagerRepository while calling getITSRecoveryDstPercentage method");
			throw new BusinessException(ERR_CODE, "Exception in CommonEntityManagerRepository while calling getITSRecoveryDstPercentage method" +e.getMessage());
		}
	}

	public List<VITSOvpSummaryEO> getOvpSearchBadActorData(SearchBadActorDataVO searchBadActorDataVO) {
		StringBuilder sql = new StringBuilder();
		TypedQuery<VITSOvpSummaryEO> query = null;
		boolean andQuery = false;
		try {
			sql.append("SELECT ref from VITSOvpSummaryEO ref where ");
			if (StringUtils.isNotBlank(searchBadActorDataVO.getBadActorSsn())) {
				sql.append(BAD_ACTOR_SSN_PARAM);
				andQuery = true;
			}
			if (StringUtils.isNotBlank(searchBadActorDataVO.getFromDt()) && StringUtils.isNotBlank(searchBadActorDataVO.getToDt())) {
				if (andQuery)
					sql.append(AND_ADDED);
				sql.append(DATE_CREATED_BETWEEN);
				andQuery = true;
			}
			if (StringUtils.isNotBlank(searchBadActorDataVO.getBadActorName())) {
				if (andQuery)
					sql.append(AND_ADDED);
				sql.append(BAD_ACTOR_NAME_LIKE);
			}
			
			query = entityManager.createQuery(sql.toString(), VITSOvpSummaryEO.class);
			if (StringUtils.isNotBlank(searchBadActorDataVO.getBadActorSsn())) {
				query.setParameter(BAD_ACTOR_SSN, StringUtils.replace(searchBadActorDataVO.getBadActorSsn(), "-", ""));
			}
			if (StringUtils.isNotBlank(searchBadActorDataVO.getFromDt()) && StringUtils.isNotBlank(searchBadActorDataVO.getToDt())) {
				query.setParameter("frdt", DateUtil.parseDateTime(searchBadActorDataVO.getFromDt(),
						DATE_FORMATTER_yyyy_MM_dd));
				query.setParameter("tdt",
						DateUtil.parseDateTime(searchBadActorDataVO.getToDt(), DATE_FORMATTER_yyyy_MM_dd));
			}
			if (StringUtils.isNotBlank(searchBadActorDataVO.getBadActorName())) {
				query.setParameter(BAD_ACTOR_NAME, "%"+searchBadActorDataVO.getBadActorName()+"%");
			}
			
		} catch (Exception e) {
			logger.error("Exception in CommonEntityManagerRepository while calling getOvpSearchBadActorData method");
			throw new BusinessException(ERR_CODE, "Exception in CommonEntityManagerRepository while calling getOvpSearchBadActorData method" +e.getMessage());
		}
		return query.getResultList();
	}

	public List<VITSRecoverySummaryEO> getRecovSearchBadActorData(SearchBadActorDataVO searchBadActorDataVO) {
		StringBuilder sql = new StringBuilder();
		TypedQuery<VITSRecoverySummaryEO> query = null;
		boolean andQuery = false;
		try {
			sql.append("SELECT ref from VITSRecoverySummaryEO ref where ");
			if (StringUtils.isNotBlank(searchBadActorDataVO.getBadActorSsn())) {
				sql.append(BAD_ACTOR_SSN_PARAM);
				andQuery = true;
			}
			if (StringUtils.isNotBlank(searchBadActorDataVO.getFromDt()) && StringUtils.isNotBlank(searchBadActorDataVO.getToDt())) {
				if (andQuery)
					sql.append(AND_ADDED);
				sql.append(DATE_CREATED_BETWEEN);
				andQuery = true;
			}
			if (StringUtils.isNotBlank(searchBadActorDataVO.getBadActorName())) {
				if (andQuery)
					sql.append(AND_ADDED);
				sql.append(BAD_ACTOR_NAME_LIKE);
			}
			
			query = entityManager.createQuery(sql.toString(), VITSRecoverySummaryEO.class);
			if (StringUtils.isNotBlank(searchBadActorDataVO.getBadActorSsn())) {
				query.setParameter(BAD_ACTOR_SSN, StringUtils.replace(searchBadActorDataVO.getBadActorSsn(), "-", ""));
			}
			if (StringUtils.isNotBlank(searchBadActorDataVO.getFromDt()) && StringUtils.isNotBlank(searchBadActorDataVO.getToDt())) {
				query.setParameter("frdt", DateUtil.parseDateTime(searchBadActorDataVO.getFromDt(),
						DATE_FORMATTER_yyyy_MM_dd));
				query.setParameter("tdt",
						DateUtil.parseDateTime(searchBadActorDataVO.getToDt(), DATE_FORMATTER_yyyy_MM_dd));
			}
			if (StringUtils.isNotBlank(searchBadActorDataVO.getBadActorName())) {
				query.setParameter(BAD_ACTOR_NAME, "%"+searchBadActorDataVO.getBadActorName()+"%");
			}
			
		} catch (Exception e) {
			logger.error("Exception in CommonEntityManagerRepository while calling getRecovSearchBadActorData method");
			throw new BusinessException(ERR_CODE, "Exception in CommonEntityManagerRepository while calling getRecovSearchBadActorData method" +e.getMessage());
		}
		return query.getResultList();
	}

	public List<String> getCostCodeMapping(Long ovpId) {
		List<?> costCodeListTemp = null;
		List<String> costCodeList = new ArrayList<>();
		String query=null;
        String sql=null;
		try {
			query = "SELECT ITS_GLCTRL_SUB_CD FROM V_ITS_COSTCODE_MAPPING WHERE OVP_ID =?";
			sql = String.format(query);
			costCodeListTemp = entityManager.createNativeQuery(sql).setParameter(1, ovpId).getResultList();
			if(costCodeListTemp!=null && !costCodeListTemp.isEmpty()) {
				for (int i = 0; i < costCodeListTemp.size(); i++) {
					String costCode = (String) costCodeListTemp.get(i);
					costCodeList.add(costCode);
				}
			}
		} catch (Exception e) {
			logger.error("Exception in CommonEntityManagerRepository while calling getCostCodeMapping method");
			throw new BusinessException(ERR_CODE, "Exception in CommonEntityManagerRepository while calling getCostCodeMapping method" +e.getMessage());
		}
		return costCodeList;
	}

	public List<ItsProsecutionsOverpaymentXrefVO> getVITSProsecutionUpdateByXrefList(Long victimBadActorXrefId, Long prosId) {
		List<?> itsOvpSummaryVOListTemp = null;
		List<ItsProsecutionsOverpaymentXrefVO> vITSOverpaidWeeksExistingList = new ArrayList<>();
		ItsProsecutionsOverpaymentXrefVO prosOvpXrefVO = null;
		String query=null;
        String sql=null;
		try {
			query = "SELECT DISTINCT V.OVP_ID,V.DATE_CREATED,V.OVP_TOTAL,V.RECOVERY_AMOUNT,V.OVP_PENALTY,V.OVP_INTEREST,V.OVP_COC,"
					+ " V.OVPSTS_CD,V.OVPDIS_CD,V.BAD_ACTOR_SSN,V.PRTY_TAX_ID,V.OVP_BALANCE,"
					+ " X.PROS_ID,X.PROS_OVP_XREF_ID,X.RESI_AMT FROM V_ITS_OVP_SUMMARY V"
					+ " LEFT JOIN ITS_PROSECUTIONS_OVERPAYMENT_XREF X On V.OVP_ID=X.OVP_ID AND X.END_DT IS NULL AND X.PROS_ID=?"
					+ " WHERE VICTIM_BAD_ACTOR_XREF_ID=?";
			sql = String.format(query);
			itsOvpSummaryVOListTemp = entityManager.createNativeQuery(sql).setParameter(1, prosId).setParameter(2, victimBadActorXrefId).getResultList();
			if(itsOvpSummaryVOListTemp!=null && !itsOvpSummaryVOListTemp.isEmpty()) {
				for (int i = 0; i < itsOvpSummaryVOListTemp.size(); i++) {
					prosOvpXrefVO = new ItsProsecutionsOverpaymentXrefVO();
					Object[] val = (Object[])itsOvpSummaryVOListTemp.get(i);
					
					prosOvpXrefVO = setITSProsOvpXrefData(val,prosOvpXrefVO);
					
					vITSOverpaidWeeksExistingList.add(prosOvpXrefVO);
				}
			}
		} catch (Exception e) {
			logger.error("Exception in CommonEntityManagerRepository while calling getVITSProsecutionUpdateByXrefList method");
			throw new BusinessException(ERR_CODE, "Exception in CommonEntityManagerRepository while calling getVITSProsecutionUpdateByXrefList method" +e.getMessage());
		}
		return vITSOverpaidWeeksExistingList;
	}

	public List<ItsProsecutionsOverpaymentXrefVO> getVITSProsecutionUpdateByBadActorList(Long badActorId, Long prosId) {
		List<?> itsOvpSummaryVOListTemp = null;
		List<ItsProsecutionsOverpaymentXrefVO> vITSOverpaidWeeksExistingList = new ArrayList<>();
		ItsProsecutionsOverpaymentXrefVO prosOvpXrefVO = null;
		String query=null;
        String sql=null;
		try {
			query = "SELECT DISTINCT V.OVP_ID,V.DATE_CREATED,V.OVP_TOTAL,V.RECOVERY_AMOUNT,V.OVP_PENALTY,V.OVP_INTEREST,V.OVP_COC,"
					+ " V.OVPSTS_CD,V.OVPDIS_CD,V.BAD_ACTOR_SSN,V.PRTY_TAX_ID,V.OVP_BALANCE,"
					+ " X.PROS_ID,X.PROS_OVP_XREF_ID,X.RESI_AMT FROM V_ITS_OVP_SUMMARY V"
					+ " LEFT JOIN ITS_PROSECUTIONS_OVERPAYMENT_XREF X On V.OVP_ID=X.OVP_ID AND X.END_DT IS NULL AND X.PROS_ID=?"
					+ " WHERE BAD_ACTOR_ID=?";
			sql = String.format(query);
			itsOvpSummaryVOListTemp = entityManager.createNativeQuery(sql).setParameter(1, prosId).setParameter(2, badActorId).getResultList();
			if(itsOvpSummaryVOListTemp!=null && !itsOvpSummaryVOListTemp.isEmpty()) {
				for (int i = 0; i < itsOvpSummaryVOListTemp.size(); i++) {
					prosOvpXrefVO = new ItsProsecutionsOverpaymentXrefVO();
					Object[] val = (Object[])itsOvpSummaryVOListTemp.get(i);
					
					prosOvpXrefVO = setITSProsOvpXrefData(val,prosOvpXrefVO);
					
					vITSOverpaidWeeksExistingList.add(prosOvpXrefVO);
				}
			}
		} catch (Exception e) {
			logger.error("Exception in CommonEntityManagerRepository while calling getVITSProsecutionUpdateByBadActorList method");
			throw new BusinessException(ERR_CODE, "Exception in CommonEntityManagerRepository while calling getVITSProsecutionUpdateByBadActorList method" +e.getMessage());
		}
		return vITSOverpaidWeeksExistingList;
	}

	private ItsProsecutionsOverpaymentXrefVO setITSProsOvpXrefData(Object[] val, ItsProsecutionsOverpaymentXrefVO prosOvpXrefVO) {
		try {
			if (val != null && val.length > 0 && val[0] != null && val[0] instanceof BigDecimal) {
				Long ovpIdVal = Long.valueOf(((BigDecimal) val[0]).longValue());
				prosOvpXrefVO.setOvpId(ovpIdVal);
			}
			
			if (val != null && val.length > 0 && val[1] != null) {
				Timestamp dateCreated = (Timestamp) val[1];
				prosOvpXrefVO.setDateCreated(DateUtil.tsDateToStr(dateCreated));
			}
			
			if (val != null && val.length > 0 && val[2] != null && val[2] instanceof BigDecimal) {
				Double ovpTotal = Double.valueOf(((BigDecimal) val[2]).doubleValue());
				prosOvpXrefVO.setOvpTotal(ovpTotal);
			}
			
			if (val != null && val.length > 0 && val[3] != null && val[3] instanceof BigDecimal) {
				Double recoveryAmount = Double.valueOf(((BigDecimal) val[3]).doubleValue());
				prosOvpXrefVO.setRecoveryAmount(recoveryAmount);
			}
			
			if (val != null && val.length > 0 && val[4] != null && val[4] instanceof BigDecimal) {
				Double ovpPenalty = Double.valueOf(((BigDecimal) val[4]).doubleValue());
				prosOvpXrefVO.setOvpPenalty(ovpPenalty);
			}
			
			if (val != null && val.length > 0 && val[5] != null && val[5] instanceof BigDecimal) {
				Double ovpInterest = Double.valueOf(((BigDecimal) val[5]).doubleValue());
				prosOvpXrefVO.setOvpInterest(ovpInterest);
			}
			
			if (val != null && val.length > 0 && val[6] != null && val[6] instanceof BigDecimal) {
				Double ovpCoC = Double.valueOf(((BigDecimal) val[6]).doubleValue());
				prosOvpXrefVO.setOvpCoc(ovpCoC);
			}
			
			prosOvpXrefVO = setITSProsOvpXrefNextData(val,prosOvpXrefVO);
			
			
			return prosOvpXrefVO;
		} catch (Exception e) {
			logger.error("Exception in CommonEntityManagerRepository while calling setITSProsOvpXrefData method");
			throw new BusinessException(ERR_CODE, "Exception in CommonEntityManagerRepository while calling setITSProsOvpXrefData method" +e.getMessage());
		}
	}

	private ItsProsecutionsOverpaymentXrefVO setITSProsOvpXrefNextData(Object[] val, ItsProsecutionsOverpaymentXrefVO prosOvpXrefVO) {
		try {
			if (val != null && val.length > 0 && val[7] != null) {
				String ovpStsCd = (String) val[7];
				prosOvpXrefVO.setOvpstsCd(ovpStsCd);
			}
			
			if (val != null && val.length > 0 && val[8] != null) {
				String ovpDisCd = (String) val[8];
				prosOvpXrefVO.setOvpdisCd(ovpDisCd);
			}
			
			if (val != null && val.length > 0 && val[9] != null) {
				String badActorSsn = (String) val[9];
				prosOvpXrefVO.setBadActorSsn(badActorSsn);
			}
			
			if (val != null && val.length > 0 && val[10] != null) {
				String prtyTaxId = (String) val[10];
				prosOvpXrefVO.setPrtyTaxId(prtyTaxId);
			}
			
			if (val != null && val.length > 0 && val[11] != null && val[11] instanceof BigDecimal) {
				Double ovpBalance = Double.valueOf(((BigDecimal) val[11]).doubleValue());
				prosOvpXrefVO.setOvpBalance(ovpBalance);
			}
			
			setITSProsOvpXrefNext(val,prosOvpXrefVO);
			
			
			return prosOvpXrefVO;
		} catch (Exception e) {
			logger.error("Exception in CommonEntityManagerRepository while calling setITSProsOvpXrefNextData method");
			throw new BusinessException(ERR_CODE, "Exception in CommonEntityManagerRepository while calling setITSProsOvpXrefNextData method" +e.getMessage());
		}
	}

	private ItsProsecutionsOverpaymentXrefVO setITSProsOvpXrefNext(Object[] val, ItsProsecutionsOverpaymentXrefVO prosOvpXrefVO) {
		try {
			if (val != null && val.length > 0 && val[12] != null && val[12] instanceof BigDecimal) {
				Long prosIdVal = Long.valueOf(((BigDecimal) val[12]).longValue());
				prosOvpXrefVO.setProsId(prosIdVal);
			}
			
			if (val != null && val.length > 0 && val[13] != null && val[13] instanceof BigDecimal) {
				Long prosOvpXrefIdVal = Long.valueOf(((BigDecimal) val[13]).longValue());
				prosOvpXrefVO.setProsOvpXrefId(prosOvpXrefIdVal);
			}
			
			if (val != null && val.length > 0 && val[14] != null && val[14] instanceof BigDecimal) {
				Double restiAmount = Double.valueOf(((BigDecimal) val[14]).doubleValue());
				prosOvpXrefVO.setRestiAmount(restiAmount);
				prosOvpXrefVO.setResiAmt(restiAmount);
			}
			return prosOvpXrefVO;
		} catch (Exception e) {
			logger.error("Exception in CommonEntityManagerRepository while calling setITSProsOvpXrefNext method");
			throw new BusinessException(ERR_CODE, "Exception in CommonEntityManagerRepository while calling setITSProsOvpXrefNext method" +e.getMessage());
		}
	}

	public List<VITSProsecutionListEO> getProsSearchBadActorData(SearchBadActorDataVO searchBadActorDataVO) {
		StringBuilder sql = new StringBuilder();
		TypedQuery<VITSProsecutionListEO> query = null;
		boolean andQuery = false;
		try {
			sql.append("SELECT ref from VITSProsecutionListEO ref where ");
			if (StringUtils.isNotBlank(searchBadActorDataVO.getBadActorSsn())) {
				sql.append(BAD_ACTOR_SSN_PARAM);
				andQuery = true;
			}
			if (StringUtils.isNotBlank(searchBadActorDataVO.getFromDt()) && StringUtils.isNotBlank(searchBadActorDataVO.getToDt())) {
				if (andQuery)
					sql.append(AND_ADDED);
				sql.append(DATE_CREATED_BETWEEN);
				andQuery = true;
			}
			if (StringUtils.isNotBlank(searchBadActorDataVO.getBadActorName())) {
				if (andQuery)
					sql.append(AND_ADDED);
				sql.append(BAD_ACTOR_NAME_LIKE);
			}
			
			query = entityManager.createQuery(sql.toString(), VITSProsecutionListEO.class);
			if (StringUtils.isNotBlank(searchBadActorDataVO.getBadActorSsn())) {
				query.setParameter(BAD_ACTOR_SSN, StringUtils.replace(searchBadActorDataVO.getBadActorSsn(), "-", ""));
			}
			if (StringUtils.isNotBlank(searchBadActorDataVO.getFromDt()) && StringUtils.isNotBlank(searchBadActorDataVO.getToDt())) {
				query.setParameter("frdt", DateUtil.parseDateTime(searchBadActorDataVO.getFromDt(),
						DATE_FORMATTER_yyyy_MM_dd));
				query.setParameter("tdt",
						DateUtil.parseDateTime(searchBadActorDataVO.getToDt(), DATE_FORMATTER_yyyy_MM_dd));
			}
			if (StringUtils.isNotBlank(searchBadActorDataVO.getBadActorName())) {
				query.setParameter(BAD_ACTOR_NAME, "%"+searchBadActorDataVO.getBadActorName()+"%");
			}
			
		} catch (Exception e) {
			logger.error("Exception in CommonEntityManagerRepository while calling getProsSearchBadActorData method");
			throw new BusinessException(ERR_CODE, "Exception in CommonEntityManagerRepository while calling getProsSearchBadActorData method" +e.getMessage());
		}
		return query.getResultList();
	}
}
