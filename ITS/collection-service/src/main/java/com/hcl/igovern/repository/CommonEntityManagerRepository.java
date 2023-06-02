package com.hcl.igovern.repository;

import java.math.BigDecimal;
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
import com.hcl.igovern.entity.VITSRecoverySummaryEO;
import com.hcl.igovern.exception.BusinessException;
import com.hcl.igovern.util.DateUtil;
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
				sql.append("ref.badActorSsn = :badActorSsn");
				andQuery = true;
			}
			if (StringUtils.isNotBlank(searchBadActorDataVO.getFromDt()) && StringUtils.isNotBlank(searchBadActorDataVO.getToDt())) {
				if (andQuery)
					sql.append(AND_ADDED);
				sql.append(" CAST(ref.dateCreated AS date) between :frdt and :tdt");
				andQuery = true;
			}
			if (StringUtils.isNotBlank(searchBadActorDataVO.getBadActorName())) {
				if (andQuery)
					sql.append(AND_ADDED);
				sql.append(" ref.badActorName LIKE :badActorName ");
			}
			
			query = entityManager.createQuery(sql.toString(), VITSOvpSummaryEO.class);
			if (StringUtils.isNotBlank(searchBadActorDataVO.getBadActorSsn())) {
				query.setParameter("badActorSsn", StringUtils.replace(searchBadActorDataVO.getBadActorSsn(), "-", ""));
			}
			if (StringUtils.isNotBlank(searchBadActorDataVO.getFromDt()) && StringUtils.isNotBlank(searchBadActorDataVO.getToDt())) {
				query.setParameter("frdt", DateUtil.parseDateTime(searchBadActorDataVO.getFromDt(),
						DATE_FORMATTER_yyyy_MM_dd));
				query.setParameter("tdt",
						DateUtil.parseDateTime(searchBadActorDataVO.getToDt(), DATE_FORMATTER_yyyy_MM_dd));
			}
			if (StringUtils.isNotBlank(searchBadActorDataVO.getBadActorName())) {
				query.setParameter("badActorName", "%"+searchBadActorDataVO.getBadActorName()+"%");
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
				sql.append("ref.badActorSsn = :badActorSsn");
				andQuery = true;
			}
			if (StringUtils.isNotBlank(searchBadActorDataVO.getFromDt()) && StringUtils.isNotBlank(searchBadActorDataVO.getToDt())) {
				if (andQuery)
					sql.append(AND_ADDED);
				sql.append(" CAST(ref.dateCreated AS date) between :frdt and :tdt");
				andQuery = true;
			}
			if (StringUtils.isNotBlank(searchBadActorDataVO.getBadActorName())) {
				if (andQuery)
					sql.append(AND_ADDED);
				sql.append(" ref.badActorName LIKE :badActorName ");
			}
			
			query = entityManager.createQuery(sql.toString(), VITSRecoverySummaryEO.class);
			if (StringUtils.isNotBlank(searchBadActorDataVO.getBadActorSsn())) {
				query.setParameter("badActorSsn", StringUtils.replace(searchBadActorDataVO.getBadActorSsn(), "-", ""));
			}
			if (StringUtils.isNotBlank(searchBadActorDataVO.getFromDt()) && StringUtils.isNotBlank(searchBadActorDataVO.getToDt())) {
				query.setParameter("frdt", DateUtil.parseDateTime(searchBadActorDataVO.getFromDt(),
						DATE_FORMATTER_yyyy_MM_dd));
				query.setParameter("tdt",
						DateUtil.parseDateTime(searchBadActorDataVO.getToDt(), DATE_FORMATTER_yyyy_MM_dd));
			}
			if (StringUtils.isNotBlank(searchBadActorDataVO.getBadActorName())) {
				query.setParameter("badActorName", "%"+searchBadActorDataVO.getBadActorName()+"%");
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
}
