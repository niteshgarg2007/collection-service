package com.hcl.igovern.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.igovern.entity.ITSRecoveryHistoryEO;
import com.hcl.igovern.entity.ItsClaimantDetailsEO;
import com.hcl.igovern.entity.ItsRecoveryDetailsEO;
import com.hcl.igovern.entity.ItsRecoveryEO;
import com.hcl.igovern.entity.ItsRefundsEO;
import com.hcl.igovern.entity.ItsVictimBadActorXrefEO;
import com.hcl.igovern.entity.VITSOvpSummaryEO;
import com.hcl.igovern.entity.VITSRecoverySummaryEO;
import com.hcl.igovern.exception.BusinessException;
import com.hcl.igovern.repository.ITSRecoveryHistoryRepository;
import com.hcl.igovern.repository.ItsClaimantDetailsRepository;
import com.hcl.igovern.repository.ItsRecoveryRepository;
import com.hcl.igovern.repository.ItsRefundsRepository;
import com.hcl.igovern.repository.ItsVictimBadActorXrefRepository;
import com.hcl.igovern.repository.VITSOvpSummaryRepository;
import com.hcl.igovern.repository.VITSRecoveryDetailsRepository;
import com.hcl.igovern.service.RecoveryService;
import com.hcl.igovern.util.DateUtil;
import com.hcl.igovern.vo.ContextDataVO;
import com.hcl.igovern.vo.ITSOvpSummaryVO;
import com.hcl.igovern.vo.ITSRecoveryHistoryVO;
import com.hcl.igovern.vo.ITSRecoverySummaryVO;
import com.hcl.igovern.vo.ItsRecoveryDetailsVO;
import com.hcl.igovern.vo.ItsRecoveryVO;

@Service
public class RecoveryServiceImpl implements RecoveryService {
	
	Logger logger = LoggerFactory.getLogger(RecoveryServiceImpl.class);
	
	@Autowired
	private VITSOvpSummaryRepository vITSOvpSummaryRepository;
	
	@Autowired
	private ItsRecoveryRepository itsRecoveryRepository;
	
	@Autowired
	private ItsClaimantDetailsRepository itsClaimantDetailsRepository;
	
	@Autowired
	private ItsVictimBadActorXrefRepository itsVictimBadActorXrefRepository;
	
	@Autowired
	private ItsRefundsRepository itsRefundsRepository;
	
	@Autowired
	private VITSRecoveryDetailsRepository vITSRecoveryDetailsRepository;
	
	@Autowired
	private ITSRecoveryHistoryRepository itsRecoveryHistoryRepository;
	
	public static final String ERR_CODE = "ERR_CODE";

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public ItsRecoveryVO addRecoveryAndDetails(ItsRecoveryVO itsRecoveryVO) {
		if (itsRecoveryVO.getItsRecoveryDtlsVO().isEmpty())
			throw new BusinessException(ERR_CODE, "Please enter recovery details data.");
		try {
			ItsRecoveryEO itsRecoveryEO = createRecoveryDetailsData(itsRecoveryVO);
			itsRecoveryEO = itsRecoveryRepository.save(itsRecoveryEO);
			createPendingRefunds(itsRecoveryEO);
			if (itsRecoveryVO.getRecoveryId() != null)
				itsRecoveryVO.setStatusMessage("Recovery has been successfully updated.");
			else
				itsRecoveryVO.setStatusMessage("Recovery has been successfully added.");
		} catch (Exception e) {
			logger.error("Business Exception in RecoveryServiceImpl.addRecoveryAndDetails method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryServiceImpl.addRecoveryAndDetails() method." + e.getMessage());
		}
		return itsRecoveryVO;
	}

	private ItsRecoveryEO createRecoveryDetailsData(ItsRecoveryVO itsRecoveryVO) {
		ItsRecoveryEO itsRecoveryEOTemp = new ItsRecoveryEO();
		List<ItsRecoveryDetailsEO> itsRecoveryDetailsList = null;
		try {
			itsRecoveryEOTemp.setBadActorId(itsRecoveryVO.getBadActorId());
			itsRecoveryEOTemp.setRecoveryDate(DateUtil.strDateToTs(itsRecoveryVO.getRecoveryDate()));
			itsRecoveryEOTemp.setRecoveryStatus(itsRecoveryVO.getRecoveryStatus());
			itsRecoveryEOTemp.setRecoveryEffDate(DateUtil.strDateHyphenToTs(itsRecoveryVO.getRecoveryEffDate()));
			itsRecoveryEOTemp.setTotalPaymentAmount(itsRecoveryVO.getTotalPaymentAmount());
			itsRecoveryEOTemp.setTotalRecoveryAmount(itsRecoveryVO.getTotalRecoveryAmount());
			if (itsRecoveryVO.getAllOvpInd() != null && itsRecoveryVO.getAllOvpInd().equalsIgnoreCase("true"))
				itsRecoveryEOTemp.setAllOvpInd("Y");
			else
				itsRecoveryEOTemp.setAllOvpInd(null);
			if (itsRecoveryVO.getRefundExcessInd() != null && itsRecoveryVO.getRefundExcessInd().equalsIgnoreCase("true"))
				itsRecoveryEOTemp.setRefundExcessInd("Y");
			else
				itsRecoveryEOTemp.setRefundExcessInd(null);
			itsRecoveryDetailsList = populateItsRecoveryDetailsList(itsRecoveryEOTemp,itsRecoveryVO);
			itsRecoveryEOTemp.setItsRecoveryDtls(itsRecoveryDetailsList);
		} catch (Exception e) {
			logger.error("Business Exception in OverpaymentServiceImpl createOverpaymentDetailsData method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentServiceImpl.createOverpaymentDetailsData() method." + e.getMessage());
		}
		
		return itsRecoveryEOTemp;
	}

	private List<ItsRecoveryDetailsEO> populateItsRecoveryDetailsList(ItsRecoveryEO itsRecoveryEOTemp, ItsRecoveryVO itsRecoveryVO) {
		List<ItsRecoveryDetailsEO> itsRecoveryDetailsList = new ArrayList<>();
		try {
			if (!itsRecoveryVO.getItsRecoveryDtlsVO().isEmpty()) {
				for (ItsRecoveryDetailsVO itsRecoveryDetailsTemp : itsRecoveryVO.getItsRecoveryDtlsVO()) {
					if (itsRecoveryDetailsTemp != null && itsRecoveryDetailsTemp.getPaymentAmount() != null && itsRecoveryDetailsTemp.getPaymentAmount() > 0) {
						ItsRecoveryDetailsEO recoveryDetails = new ItsRecoveryDetailsEO();
						recoveryDetails.setRecoveryId(itsRecoveryEOTemp);
						Long victimBadActorXrefId = getVictimBadActorXrefId(itsRecoveryDetailsTemp, itsRecoveryEOTemp);
						if (victimBadActorXrefId == null)
							throw new BusinessException(ERR_CODE, "Some problem occurs to get Victim Bad Actor Xref Id is null");
						recoveryDetails.setVictimBadActorXrefId(victimBadActorXrefId);
						recoveryDetails.setOvpId(itsRecoveryDetailsTemp.getOvpId());
						recoveryDetails.setPaymentMethod(itsRecoveryDetailsTemp.getPaymentMethod());
						recoveryDetails.setPaymentAmount(itsRecoveryDetailsTemp.getPaymentAmount());
						recoveryDetails.setComment(itsRecoveryDetailsTemp.getComment());
						itsRecoveryDetailsList.add(recoveryDetails);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Business Exception in OverpaymentServiceImpl createOverpaymentDetailsData method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentServiceImpl.createOverpaymentDetailsData() method." + e.getMessage());
		}
		
		return itsRecoveryDetailsList;
	}

	private Long getVictimBadActorXrefId(ItsRecoveryDetailsVO itsRecoveryDetailsTemp, ItsRecoveryEO itsRecoveryEOTemp) {
		Long victimBadActorXrefId = null;
		ItsClaimantDetailsEO itsClaimantDetails = itsClaimantDetailsRepository.findByPrtyTaxId(itsRecoveryDetailsTemp.getOriginalSsn());
		if (itsClaimantDetails != null && itsClaimantDetails.getClmtDtlId() != null) {
			ItsVictimBadActorXrefEO itsVictimBadActorXref = itsVictimBadActorXrefRepository.
					findByBadActorIdAndClmtDtlId(itsRecoveryEOTemp.getBadActorId(),itsClaimantDetails.getClmtDtlId());
			if (itsVictimBadActorXref != null && itsVictimBadActorXref.getVictimBadActorXrefId() != null) {
				victimBadActorXrefId = itsVictimBadActorXref.getVictimBadActorXrefId();
			}
		}
		return victimBadActorXrefId;
	}

	@Override
	public List<ITSOvpSummaryVO> getOverpaymentDetailsList(ContextDataVO contextData) {
		List<ITSOvpSummaryVO> itsOvpSummaryVOList = null;
		try {
			if (contextData.getVictimBadActorXrefId() != null ) {
				itsOvpSummaryVOList = getAssociatedVITSOvpSummaryList(contextData.getVictimBadActorXrefId());
			}
			if (contextData.getBadActorId() != null ) {
				itsOvpSummaryVOList = getBadActorVITSOvpSummaryList(contextData.getBadActorId());
			}
			
		} catch (BusinessException e) {
			logger.error("Business Exception in RecoveryServiceImpl.getOverpaymentDetailsList method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryServiceImpl.getOverpaymentDetailsList() method." + e.getMessage());
		}
		
		return itsOvpSummaryVOList;
	}

	private List<ITSOvpSummaryVO> getAssociatedVITSOvpSummaryList(Long victimBadActorXrefId) {
		List<VITSOvpSummaryEO> vITSOvpSummaryEOList = null;
		List<ITSOvpSummaryVO> itsOvpSummaryVOList = new ArrayList<>();
		try {
			vITSOvpSummaryEOList = vITSOvpSummaryRepository.findByVictimBadActorXrefId(victimBadActorXrefId);
			if (vITSOvpSummaryEOList != null && !vITSOvpSummaryEOList.isEmpty()) {
				for (VITSOvpSummaryEO vITSOvpSummaryObj : vITSOvpSummaryEOList) {
					if (vITSOvpSummaryObj != null) {
						ITSOvpSummaryVO itsOvpSummaryVOObj = new ITSOvpSummaryVO();
						BeanUtils.copyProperties(vITSOvpSummaryObj, itsOvpSummaryVOObj);
						itsOvpSummaryVOObj.setOvpCurrentBalance(vITSOvpSummaryObj.getOvpBalance());
						itsOvpSummaryVOList.add(itsOvpSummaryVOObj);
					}
				}
			}
		} catch (BusinessException e) {
			logger.error("Business Exception in RecoveryServiceImpl.getAssociatedVITSOvpSummaryList method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryServiceImpl.getAssociatedVITSOvpSummaryList() method." + e.getMessage());
		}
		
		return itsOvpSummaryVOList;
	}
	
	private List<ITSOvpSummaryVO> getBadActorVITSOvpSummaryList(Long badActorId) {
		List<VITSOvpSummaryEO> vITSOvpSummaryEOList = null;
		List<ITSOvpSummaryVO> itsOvpSummaryVOList = new ArrayList<>();
		try {
			vITSOvpSummaryEOList = vITSOvpSummaryRepository.findByBadActorId(badActorId);
			if (vITSOvpSummaryEOList != null && !vITSOvpSummaryEOList.isEmpty()) {
				for (VITSOvpSummaryEO vITSOvpSummaryObj : vITSOvpSummaryEOList) {
					if (vITSOvpSummaryObj != null && vITSOvpSummaryObj.getOvpBalance() > 0) {
						ITSOvpSummaryVO itsOvpSummaryVOObj = new ITSOvpSummaryVO();
						BeanUtils.copyProperties(vITSOvpSummaryObj, itsOvpSummaryVOObj);
						itsOvpSummaryVOObj.setOvpCurrentBalance(vITSOvpSummaryObj.getOvpBalance());
						itsOvpSummaryVOList.add(itsOvpSummaryVOObj);
					}
				}
			}
		} catch (BusinessException e) {
			logger.error("Business Exception in RecoveryServiceImpl.getBadActorVITSOvpSummaryList method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryServiceImpl.getBadActorVITSOvpSummaryList() method." + e.getMessage());
		}
		return itsOvpSummaryVOList;
	}
	
	private void createPendingRefunds(ItsRecoveryEO itsRecoveryEO) {
		try {
			if (itsRecoveryEO != null && itsRecoveryEO.getRefundExcessInd() != null && 
					itsRecoveryEO.getRefundExcessInd().equalsIgnoreCase("Y") && 
					itsRecoveryEO.getTotalRecoveryAmount() > itsRecoveryEO.getTotalPaymentAmount()) {
				ItsRefundsEO itsRefundsEO = populateITSRefundsObject(itsRecoveryEO);
				if (itsRefundsEO.getRefAmt() != null && itsRefundsEO.getRefAmt() >= 0 && itsRefundsEO.getRecoveryId() != null) {
					//itsRefundsRepository.save(itsRefundsEO);
				}
			}
		} catch (BusinessException e) {
			logger.error("Business Exception in RecoveryServiceImpl.createPendingRefunds method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryServiceImpl.createPendingRefunds() method." + e.getMessage());
		}
	}

	private ItsRefundsEO populateITSRefundsObject(ItsRecoveryEO itsRecoveryEO) {
		ItsRefundsEO itsRefundsEO = new ItsRefundsEO();
		try {
			itsRefundsEO.setRecoveryId(itsRecoveryEO.getRecoveryId());
			itsRefundsEO.setRefAmt(itsRecoveryEO.getTotalRecoveryAmount() - itsRecoveryEO.getTotalPaymentAmount());
			itsRefundsEO.setRefDspn("P");
			itsRefundsEO.setRefAmtIssued(null);
			itsRefundsEO.setRefIssuedDate(null);
		} catch (BusinessException e) {
			logger.error("Business Exception in RecoveryServiceImpl.populateITSRefundsObject method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryServiceImpl.populateITSRefundsObject() method." + e.getMessage());
		}
		return itsRefundsEO;
	}

	@Override
	public List<ITSRecoverySummaryVO> getITSRecoverySummaryList(Long victimBadActorXrefId) {
		List<VITSRecoverySummaryEO> vITSRecoverySummaryEOList = null;
		List<ITSRecoverySummaryVO> itsRecoverySummaryVOList = new ArrayList<>();
		try {
			vITSRecoverySummaryEOList = vITSRecoveryDetailsRepository.findByVictimBadActorXrefId(victimBadActorXrefId);
			if (vITSRecoverySummaryEOList != null && !vITSRecoverySummaryEOList.isEmpty()) {
				for (VITSRecoverySummaryEO vITSRecoverySummaryObj : vITSRecoverySummaryEOList) {
					if (vITSRecoverySummaryObj != null) {
						ITSRecoverySummaryVO itsRecoverySummaryObj = new ITSRecoverySummaryVO();
						BeanUtils.copyProperties(vITSRecoverySummaryObj, itsRecoverySummaryObj);
						itsRecoverySummaryObj.setRecoveryDate(DateUtil.tsDateToStr(vITSRecoverySummaryObj.getRecoveryDate()));
						itsRecoverySummaryObj.setRecoveryEffDate(DateUtil.tsDateToStr(vITSRecoverySummaryObj.getRecoveryEffDate()));
						itsRecoverySummaryVOList.add(itsRecoverySummaryObj);
					}
				}
			}
		} catch (BusinessException e) {
			logger.error("Business Exception in RecoveryServiceImpl getITSRecoverySummaryList method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryServiceImpl.getITSRecoverySummaryList() method." + e.getMessage());
		}
		
		return itsRecoverySummaryVOList;
	}

	@Override
	public List<ITSRecoveryHistoryVO> getITSRecoveryHistoryList(Long selectedRecoveryId) {
		List<ITSRecoveryHistoryEO> itsRecoveryHistoryEOList = null;
		List<ITSRecoveryHistoryVO> itsRecoveryHistoryVOList = new ArrayList<>();
		try {
			itsRecoveryHistoryEOList = itsRecoveryHistoryRepository.findByRecoveryId(selectedRecoveryId);
			if (itsRecoveryHistoryEOList != null && !itsRecoveryHistoryEOList.isEmpty()) {
				for (ITSRecoveryHistoryEO itsRecoveryHistoryEOObj : itsRecoveryHistoryEOList) {
					if (itsRecoveryHistoryEOObj != null) {
						ITSRecoveryHistoryVO itsRecoveryHistoryVOObj = new ITSRecoveryHistoryVO();
						BeanUtils.copyProperties(itsRecoveryHistoryEOObj, itsRecoveryHistoryVOObj);
						itsRecoveryHistoryVOObj.setRecoveryDate(DateUtil.tsDateToStr(itsRecoveryHistoryEOObj.getRecoveryDate()));
						itsRecoveryHistoryVOObj.setDateCreated(DateUtil.getStringDate(itsRecoveryHistoryEOObj.getDateCreated(),null));
						itsRecoveryHistoryVOList.add(itsRecoveryHistoryVOObj);
					}
				}
			}
		} catch (BusinessException e) {
			logger.error("Business Exception in RecoveryServiceImpl.getITSRecoveryHistoryList method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryServiceImpl.getITSRecoveryHistoryList() method." + e.getMessage());
		}
		
		return itsRecoveryHistoryVOList;
	}
}
