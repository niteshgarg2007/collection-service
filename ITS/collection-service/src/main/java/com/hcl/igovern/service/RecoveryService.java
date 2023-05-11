package com.hcl.igovern.service;

import java.util.List;

import com.hcl.igovern.vo.ContextDataVO;
import com.hcl.igovern.vo.ITSOvpSummaryVO;
import com.hcl.igovern.vo.ITSRecoveryHistoryVO;
import com.hcl.igovern.vo.ITSRecoverySummaryVO;
import com.hcl.igovern.vo.ItsRecoveryDetailsVO;
import com.hcl.igovern.vo.ItsRecoveryVO;

public interface RecoveryService {

	ItsRecoveryVO addRecoveryAndDetails(ItsRecoveryVO itsRecoveryVO);

	List<ITSOvpSummaryVO> getOverpaymentDetailsList(ContextDataVO contextData);

	List<ITSRecoverySummaryVO> getITSRecoverySummaryList(Long victimBadActorXrefId);

	List<ITSRecoveryHistoryVO> getITSRecoveryHistoryList(Long selectedRecoveryId);

	ItsRecoveryVO getITSRecoveryDataByRecoveryId(Long recoveryId);

	List<ItsRecoveryDetailsVO> getExistingRecoveryDetailsList(ContextDataVO contextData);

	List<ItsRecoveryDetailsVO> getOverpaymentUpdateDetailsListByParams(ContextDataVO contextData);

}
