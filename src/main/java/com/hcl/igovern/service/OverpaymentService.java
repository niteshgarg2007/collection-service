package com.hcl.igovern.service;

import java.util.List;

import com.hcl.igovern.vo.ContextDataVO;
import com.hcl.igovern.vo.ITSOvpSummaryVO;
import com.hcl.igovern.vo.ITSOvpsearchDetailsVO;
import com.hcl.igovern.vo.ItsOverpaymentVO;
import com.hcl.igovern.vo.OverpaidWeeksVO;
import com.hcl.igovern.vo.SearchBadActorDataVO;

public interface OverpaymentService {

	List<OverpaidWeeksVO> getProgramCodeDDList(Long victimBadActorXrefId);

	List<OverpaidWeeksVO> getOverpaidWeeksList(ContextDataVO contextData);

	ItsOverpaymentVO addOverpaymentAndDetails(ItsOverpaymentVO itsOverpaymentVO);

	List<ITSOvpSummaryVO> getITSOverpaymentSummaryList(Long victimBadActorXrefId);

	ItsOverpaymentVO getITSOverpaymentAndOvpDetails(Long overpaymentId);

	OverpaidWeeksVO getExistingProgramCodeDD(ContextDataVO contextData);

	List<OverpaidWeeksVO> getExistingOverpaidWeeksList(ContextDataVO contextData);

	List<OverpaidWeeksVO> getOverpaidWeeksUpdatedList(ContextDataVO contextData);

	List<ITSOvpSummaryVO> getITSOverpaymentStatusHistoryList(Long selectedOverpaymentId);

	List<ITSOvpSummaryVO> getOvpSearchBadActorData(SearchBadActorDataVO searchBadActorDataVO);

	List<ITSOvpsearchDetailsVO> getITSOverpaymentDetailsList(Long selectedOverpaymentId);
}
