package com.hcl.igovern.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hcl.igovern.entity.PITSRecoveryDstPercentageEO;
import com.hcl.igovern.entity.VITSOverpaidWeeksEO;
import com.hcl.igovern.entity.VITSOverpaidWeeksUpdateEO;
import com.hcl.igovern.exception.BusinessException;
import com.hcl.igovern.vo.ContextDataVO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.StoredProcedureQuery;

@Component
public class CommonEntityManagerRepository {
	
	Logger logger = LoggerFactory.getLogger(CommonEntityManagerRepository.class);

	@Autowired
    EntityManager entityManager;
	
	public static final String ERR_CODE = "ERR_CODE";
	
	public List<VITSOverpaidWeeksEO> getProgramCodeDDList(Long victimBadActorXrefId) {
		List<?> vITSOverpaidWeeksListTemp = null;
		List<VITSOverpaidWeeksEO> vITSOverpaidWeeksList = new ArrayList<>();
		VITSOverpaidWeeksEO vITSOverpaidWeeks = null;
		String query=null;
        String sql=null;
		try {
			query = "SELECT DISTINCT CLM_ID,PRGM_CD FROM V_ITS_OVERPAID_WEEKS WHERE VICTIM_BAD_ACTOR_XREF_ID ="+victimBadActorXrefId;
			sql = String.format(query);
			vITSOverpaidWeeksListTemp = entityManager.createNativeQuery(sql).getResultList();
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

	public VITSOverpaidWeeksUpdateEO getExistingProgramCodeDD(ContextDataVO contextData) {
		List<?> vITSOverpaidWeeksUpdateListTemp = null;
		List<VITSOverpaidWeeksUpdateEO> vITSOverpaidWeeksExistingList = new ArrayList<>();
		VITSOverpaidWeeksUpdateEO vITSOverpaidWeeksUpdate = null;
		String query=null;
        String sql=null;
		try {
			query = "SELECT DISTINCT CLM_ID,PRGM_CD FROM V_ITS_OVERPAID_WEEKS_UPDATE "
					+ " WHERE VICTIM_BAD_ACTOR_XREF_ID ="+contextData.getVictimBadActorXrefId() + " AND OVP_ID=" + contextData.getOvpId();
			sql = String.format(query);
			vITSOverpaidWeeksUpdateListTemp = entityManager.createNativeQuery(sql).getResultList();
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
			logger.error("Exception in CommonEntityManagerRepository while calling getExistingProgramCodeDDList method");
			throw new BusinessException(ERR_CODE, "Exception in CommonEntityManagerRepository while calling getExistingProgramCodeDDList method" +e.getMessage());
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
}
