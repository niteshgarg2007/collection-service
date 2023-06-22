package com.hcl.igovern.service;

import java.util.List;

import com.hcl.igovern.entity.ItsRecoveryDetailsEO;
import com.hcl.igovern.entity.VITSOvpSummaryEO;
import com.hcl.igovern.vo.ContextDataVO;
import com.hcl.igovern.vo.ITSRecoveryHistoryVO;
import com.hcl.igovern.vo.ITSRecoverySummaryVO;
import com.hcl.igovern.vo.ITSRecoveryUpdateVO;
import com.hcl.igovern.vo.ITSRecovsearchDetailsVO;
import com.hcl.igovern.vo.ItsRecoveryDetailsVO;
import com.hcl.igovern.vo.ItsRecoveryVO;
import com.hcl.igovern.vo.SearchBadActorDataVO;

public interface RecoveryService {

	ItsRecoveryVO addRecoveryAndDetails(ItsRecoveryVO itsRecoveryVO);

	List<ITSRecoveryUpdateVO> getOverpaymentDetailsList(ContextDataVO contextData);

	List<ITSRecoverySummaryVO> getITSRecoverySummaryList(Long victimBadActorXrefId);

	List<ITSRecoveryHistoryVO> getITSRecoveryHistoryList(Long selectedRecoveryId);

	ItsRecoveryVO getITSRecoveryDataByRecoveryId(Long recoveryId);

	List<ItsRecoveryDetailsVO> getExistingRecoveryDetailsList(ContextDataVO contextData);

	List<ItsRecoveryDetailsVO> getOverpaymentUpdateDetailsListByParams(ContextDataVO contextData);

	ItsRecoveryVO processSelectedRecovery(Long recoveryId);

	List<ITSRecoverySummaryVO> getRecovSearchBadActorData(SearchBadActorDataVO searchBadActorDataVO);

	List<ITSRecovsearchDetailsVO> getITSRecoveryDetailsList(Long selectedRecoveryId);

	Double applyRecovery(ItsRecoveryDetailsEO itsRecoveryDetailsEO, VITSOvpSummaryEO itsOvpSummaryEO);

}
