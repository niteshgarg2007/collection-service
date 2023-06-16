package com.hcl.igovern.service;

import java.util.List;

import com.hcl.igovern.vo.ContextDataVO;
import com.hcl.igovern.vo.ITSOvpSummaryVO;
import com.hcl.igovern.vo.ITSProsecutionHistoryVO;
import com.hcl.igovern.vo.ITSProsecutionSummaryVO;
import com.hcl.igovern.vo.ItsProsecutionVO;
import com.hcl.igovern.vo.ItsProsecutionsOverpaymentXrefVO;

public interface ProsecutionService {

	List<ITSOvpSummaryVO> getOverpaymentDetailsList(ContextDataVO contextData);

	ItsProsecutionVO addProsecutionAndDetails(ItsProsecutionVO itsProsecutionVO);

	List<ITSProsecutionSummaryVO> getITSProsecutionSummaryList(Long victimBadActorXrefId);

	List<ITSProsecutionHistoryVO> getITSProsHistoryList(Long selectedProsId);

	ItsProsecutionVO getITSProsDataByProsId(Long selectedProsId);

	List<ItsProsecutionsOverpaymentXrefVO> getExistingProsecutionOvpXref(ContextDataVO contextData);

	List<ItsProsecutionsOverpaymentXrefVO> getOverpaymentUpdateDetailsListByParams(ContextDataVO contextData);

}
