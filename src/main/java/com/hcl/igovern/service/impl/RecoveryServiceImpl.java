package com.hcl.igovern.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.igovern.entity.ITSRecoveryHistoryEO;
import com.hcl.igovern.entity.ItsBadActorDetailsEO;
import com.hcl.igovern.entity.ItsClaimantDetailsEO;
import com.hcl.igovern.entity.ItsOverpaymentDetailsEO;
import com.hcl.igovern.entity.ItsOverpaymentTransactionsEO;
import com.hcl.igovern.entity.ItsOvpRecvDistributionsEO;
import com.hcl.igovern.entity.ItsRecoveryDetailsEO;
import com.hcl.igovern.entity.ItsRecoveryEO;
import com.hcl.igovern.entity.ItsRefundsEO;
import com.hcl.igovern.entity.ItsVictimBadActorXrefEO;
import com.hcl.igovern.entity.PITSRecoveryDstPercentageEO;
import com.hcl.igovern.entity.VITSOvpSummaryEO;
import com.hcl.igovern.entity.VITSRecoveryProcessInputEO;
import com.hcl.igovern.entity.VITSRecoverySummaryEO;
import com.hcl.igovern.entity.VITSRecoveryUpdateEO;
import com.hcl.igovern.entity.VITSRefundsDataEO;
import com.hcl.igovern.exception.BusinessException;
import com.hcl.igovern.repository.CommonEntityManagerRepository;
import com.hcl.igovern.repository.ITSRecoveryHistoryRepository;
import com.hcl.igovern.repository.ItsBadActorDetailsRepository;
import com.hcl.igovern.repository.ItsClaimantDetailsRepository;
import com.hcl.igovern.repository.ItsOvpRecvDistributionsRepository;
import com.hcl.igovern.repository.ItsRecoveryRepository;
import com.hcl.igovern.repository.ItsRefundsRepository;
import com.hcl.igovern.repository.ItsVictimBadActorXrefRepository;
import com.hcl.igovern.repository.OverpaymentDetailsRepository;
import com.hcl.igovern.repository.OverpaymentTransactionsRepository;
import com.hcl.igovern.repository.VITSOvpSummaryRepository;
import com.hcl.igovern.repository.VITSRecoveryDetailsRepository;
import com.hcl.igovern.repository.VITSRecoveryProcessInputRepository;
import com.hcl.igovern.repository.VITSRecoveryUpdateRepository;
import com.hcl.igovern.repository.VITSRefundsDataRepository;
import com.hcl.igovern.service.RecoveryService;
import com.hcl.igovern.util.DateUtil;
import com.hcl.igovern.util.UIDateRoutines;
import com.hcl.igovern.util.UIUtil;
import com.hcl.igovern.vo.ContextDataVO;
import com.hcl.igovern.vo.ITSRecoveryHistoryVO;
import com.hcl.igovern.vo.ITSRecoverySummaryVO;
import com.hcl.igovern.vo.ITSRecoveryUpdateVO;
import com.hcl.igovern.vo.ITSRefundsDataVO;
import com.hcl.igovern.vo.ItsRecoveryDetailsVO;
import com.hcl.igovern.vo.ItsRecoveryVO;

@Service
public class RecoveryServiceImpl implements RecoveryService {
	
	Logger logger = LoggerFactory.getLogger(RecoveryServiceImpl.class);
	
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
	
	@Autowired
	private ItsBadActorDetailsRepository itsBadActorDetailsRepository;
	
	@Autowired
	private VITSRecoveryUpdateRepository vITSRecoveryUpdateRepository;
	
	@Autowired
	private VITSRecoveryProcessInputRepository vITSRecoveryProcessInputRepository;
	
	@Autowired
	private OverpaymentDetailsRepository overpaymentDetailsRepository;
	
	@Autowired
	private OverpaymentTransactionsRepository overpaymentTransactionsRepository;
	
	@Autowired
	private ItsOvpRecvDistributionsRepository itsOvpRecvDistributionsRepository;
	
	@Autowired
    private CommonEntityManagerRepository commonEntityManagerRepository;
	
	@Autowired
	private VITSOvpSummaryRepository vITSOvpSummaryRepository;
	
	@Autowired
	private VITSRefundsDataRepository vITSRefundsDataRepository;
	
	public static final String ERR_CODE = "ERR_CODE";

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public ItsRecoveryVO addRecoveryAndDetails(ItsRecoveryVO itsRecoveryVO) {
		if (itsRecoveryVO.getItsRecoveryDtlsVO().isEmpty())
			throw new BusinessException(ERR_CODE, "Please enter recovery details data.");
		try {
			if (itsRecoveryVO.getRecoveryId() == null) {
				ItsRecoveryEO itsRecoveryEO = createRecoveryDetailsData(itsRecoveryVO);
				itsRecoveryRepository.save(itsRecoveryEO);
				itsRecoveryVO.setStatusMessage("Recovery has been successfully added.");
			}
			
			if (itsRecoveryVO.getRecoveryId() != null) {
				ItsRecoveryEO itsRecoveryEO = updateRecoveryDetailsData(itsRecoveryVO);
				itsRecoveryRepository.save(itsRecoveryEO);
				itsRecoveryVO.setStatusMessage("Recovery has been successfully updated.");
			}
		} catch (Exception e) {
			logger.error("Business Exception in RecoveryServiceImpl.addRecoveryAndDetails method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryServiceImpl.addRecoveryAndDetails() method." + e.getMessage());
		}
		return itsRecoveryVO;
	}

	private ItsRecoveryEO updateRecoveryDetailsData(ItsRecoveryVO itsRecoveryVO) {
		List<ItsRecoveryDetailsEO> itsRecoveryDetailsList = null;
		try {
			ItsRecoveryEO itsRecoveryEOTemp = updateRecoveryObject(itsRecoveryVO);
			itsRecoveryEOTemp.getItsRecoveryDtls().clear();
			itsRecoveryDetailsList = populateItsRecoveryDetailsList(itsRecoveryEOTemp,itsRecoveryVO);
			itsRecoveryEOTemp.getItsRecoveryDtls().addAll(itsRecoveryDetailsList);
			return itsRecoveryEOTemp;
		} catch (Exception e) {
			logger.error("Business Exception in RecoveryServiceImpl.updateRecoveryDetailsData method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryServiceImpl.updateRecoveryDetailsData() method." + e.getMessage());
		}
	}

	private ItsRecoveryEO updateRecoveryObject(ItsRecoveryVO itsRecoveryVO) {
		ItsRecoveryEO itsRecoveryEO = new ItsRecoveryEO();
		try {
			Optional<ItsRecoveryEO> itsRecoveryEOOpt = itsRecoveryRepository.findById(itsRecoveryVO.getRecoveryId());
			if (itsRecoveryEOOpt.isPresent()) {
				itsRecoveryEO = itsRecoveryEOOpt.get();
				itsRecoveryEO.setBadActorId(itsRecoveryVO.getBadActorId());
				itsRecoveryEO.setRecoveryDate(DateUtil.strDateToTs(itsRecoveryVO.getRecoveryDate()));
				itsRecoveryEO.setRecoveryStatus(itsRecoveryVO.getRecoveryStatus());
				itsRecoveryEO.setRecoveryEffDate(DateUtil.strDateHyphenToTs(itsRecoveryVO.getRecoveryEffDate()));
				itsRecoveryEO.setTotalPaymentAmount(itsRecoveryVO.getTotalPaymentAmount());
				itsRecoveryEO.setTotalRecoveryAmount(itsRecoveryVO.getTotalRecoveryAmount());
				if (itsRecoveryVO.getAllOvpInd() != null && (itsRecoveryVO.getAllOvpInd().equalsIgnoreCase("true") || 
						itsRecoveryVO.getAllOvpInd().equalsIgnoreCase("Y")))
					itsRecoveryEO.setAllOvpInd("Y");
				else
					itsRecoveryEO.setAllOvpInd(null);
				if (itsRecoveryVO.getRefundExcessInd() != null && (itsRecoveryVO.getRefundExcessInd().equalsIgnoreCase("true") || 
						itsRecoveryVO.getRefundExcessInd().equalsIgnoreCase("Y")))
					itsRecoveryEO.setRefundExcessInd("Y");
				else
					itsRecoveryEO.setRefundExcessInd(null);
			}
		} catch (Exception e) {
			logger.error("Business Exception in RecoveryServiceImpl.updateRecoveryObject method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryServiceImpl.updateRecoveryObject() method." + e.getMessage());
		}
		
		return itsRecoveryEO;
	}

	private ItsRecoveryEO createRecoveryDetailsData(ItsRecoveryVO itsRecoveryVO) {
		ItsRecoveryEO itsRecoveryEOTemp = new ItsRecoveryEO();
		List<ItsRecoveryDetailsEO> itsRecoveryDetailsList = null;
		try {
			itsRecoveryEOTemp.setBadActorId(itsRecoveryVO.getBadActorId());
			itsRecoveryEOTemp.setRecoveryDate(DateUtil.strDateToTs(itsRecoveryVO.getRecoveryDate()));
			itsRecoveryEOTemp.setRecoveryStatus("Closed");
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
			logger.error("Business Exception in RecoveryServiceImpl.createRecoveryDetailsData method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryServiceImpl.createRecoveryDetailsData() method." + e.getMessage());
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
			logger.error("Business Exception in RecoveryServiceImpl createOverpaymentDetailsData method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryServiceImpl.createOverpaymentDetailsData() method." + e.getMessage());
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
	public List<ITSRecoveryUpdateVO> getOverpaymentDetailsList(ContextDataVO contextData) {
		List<ITSRecoveryUpdateVO> itsOvpSummaryVOList = null;
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

	private List<ITSRecoveryUpdateVO> getAssociatedVITSOvpSummaryList(Long victimBadActorXrefId) {
		List<VITSRecoveryUpdateEO> vITSRecoveryUpdateEOList = null;
		List<ITSRecoveryUpdateVO> itsRecoveryUpdateVOList = new ArrayList<>();
		try {
			vITSRecoveryUpdateEOList = vITSRecoveryUpdateRepository.findByVictimBadActorXrefIdAndRecoveryIdIsNull(victimBadActorXrefId);
			if (vITSRecoveryUpdateEOList != null && !vITSRecoveryUpdateEOList.isEmpty()) {
				for (VITSRecoveryUpdateEO vITSRecoveryUpdateEOObj : vITSRecoveryUpdateEOList) {
					ITSRecoveryUpdateVO itsRecoveryUpdateVOObj = new ITSRecoveryUpdateVO();
					BeanUtils.copyProperties(vITSRecoveryUpdateEOObj, itsRecoveryUpdateVOObj);
					itsRecoveryUpdateVOObj.setOvpBalanceCheck(vITSRecoveryUpdateEOObj.getOvpBalance());
					itsRecoveryUpdateVOList.add(itsRecoveryUpdateVOObj);
				}
			}
		} catch (BusinessException e) {
			logger.error("Business Exception in RecoveryServiceImpl.getAssociatedVITSOvpSummaryList method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryServiceImpl.getAssociatedVITSOvpSummaryList() method." + e.getMessage());
		}
		
		return itsRecoveryUpdateVOList;
	}
	
	private List<ITSRecoveryUpdateVO> getBadActorVITSOvpSummaryList(Long badActorId) {
		List<VITSRecoveryUpdateEO> vITSRecoveryUpdateEOList = null;
		List<ITSRecoveryUpdateVO> itsRecoveryUpdateVOList = new ArrayList<>();
		try {
			vITSRecoveryUpdateEOList = vITSRecoveryUpdateRepository.findByBadActorIdAndRecoveryIdIsNull(badActorId);
			if (vITSRecoveryUpdateEOList != null && !vITSRecoveryUpdateEOList.isEmpty()) {
				for (VITSRecoveryUpdateEO vITSRecoveryUpdateEOObj : vITSRecoveryUpdateEOList) {
					ITSRecoveryUpdateVO itsRecoveryUpdateVOObj = new ITSRecoveryUpdateVO();
					BeanUtils.copyProperties(vITSRecoveryUpdateEOObj, itsRecoveryUpdateVOObj);
					itsRecoveryUpdateVOObj.setOvpBalanceCheck(vITSRecoveryUpdateEOObj.getOvpBalance());
					itsRecoveryUpdateVOList.add(itsRecoveryUpdateVOObj);
				}
			}
		} catch (BusinessException e) {
			logger.error("Business Exception in RecoveryServiceImpl.getBadActorVITSOvpSummaryList method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryServiceImpl.getBadActorVITSOvpSummaryList() method." + e.getMessage());
		}
		return itsRecoveryUpdateVOList;
	}
	
	private void createPendingRefunds(ItsRecoveryEO itsRecoveryEO) {
		try {
			if (itsRecoveryEO != null && itsRecoveryEO.getRefundExcessInd() != null && 
					itsRecoveryEO.getRefundExcessInd().equalsIgnoreCase("Y") && 
					itsRecoveryEO.getTotalRecoveryAmount() > itsRecoveryEO.getTotalPaymentAmount()) {
				ItsRefundsEO itsRefundsEO = populateITSRefundsObject(itsRecoveryEO);
				if (itsRefundsEO.getRefAmt() != null && itsRefundsEO.getRefAmt() >= 0 && itsRefundsEO.getRecoveryId() != null) {
					itsRefundsRepository.save(itsRefundsEO);
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
						itsRecoveryHistoryVOObj.setCreatedBy(itsRecoveryHistoryEOObj.getCreatedBy());
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

	@Override
	public ItsRecoveryVO getITSRecoveryDataByRecoveryId(Long recoveryId) {
		ItsRecoveryVO itsRecoveryVO  = new ItsRecoveryVO();
		ItsRecoveryEO itsRecoveryEO = null;
		try {
			Optional<ItsRecoveryEO> itsRecoveryEOOpt = itsRecoveryRepository.findById(recoveryId);
			if (itsRecoveryEOOpt.isPresent()) {
				itsRecoveryEO = itsRecoveryEOOpt.get();
				BeanUtils.copyProperties(itsRecoveryEO, itsRecoveryVO);
				itsRecoveryVO.setRecoveryDate(itsRecoveryEO.getRecoveryDate()!=null?UIDateRoutines.getDateString(itsRecoveryEO.getRecoveryDate(),null):null);
				itsRecoveryVO.setRecoveryEffDate(itsRecoveryEO.getRecoveryEffDate()!=null?UIDateRoutines.getDateString(itsRecoveryEO.getRecoveryEffDate(),null):null);
			}
		} catch (IllegalArgumentException ia) {
			logger.error("Business Exception in RecoveryServiceImpl.getITSRecoveryDataByRecoveryId method");
			throw new BusinessException("ERR_CODE1", "Given recovery Id is null, Please provide valid recovery Id." +ia.getMessage());
		} catch (java.util.NoSuchElementException e) {
			logger.error("Business Exception in RecoveryServiceImpl.getITSRecoveryDataByRecoveryId method");
			throw new BusinessException("ERR_CODE2", "Given recovery Id does not exist in Database." +e.getMessage());
		}
		
		return itsRecoveryVO;
	}

	@Override
	public List<ItsRecoveryDetailsVO> getExistingRecoveryDetailsList(ContextDataVO contextData) {
		List<ItsRecoveryDetailsVO> itsRecoveryDetailsVOList = null;
		ItsRecoveryEO itsRecoveryEO = null;
		try {
			logger.info("Starting to calling RecoveryServiceImpl.getExistingRecoveryDetailsList method");
			Optional<ItsRecoveryEO> itsRecoveryEOOpt = itsRecoveryRepository.findById(contextData.getRecoveryId());
			if (itsRecoveryEOOpt.isPresent()) {
				itsRecoveryEO = itsRecoveryEOOpt.get();
				itsRecoveryDetailsVOList = populateExistingRecoveryDetailsVOList(itsRecoveryEO);
			}
		} catch (Exception e) {
			logger.error("Business Exception in RecoveryServiceImpl.getExistingRecoveryDetailsList method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryServiceImpl.getExistingRecoveryDetailsList() method." + e.getMessage());
		}
		
		return itsRecoveryDetailsVOList;
	
	}

	private List<ItsRecoveryDetailsVO> populateExistingRecoveryDetailsVOList(ItsRecoveryEO itsRecoveryEO) {
		List<ItsRecoveryDetailsVO> itsRecoveryDetailsVOList = new ArrayList<>();
		try {
			if (itsRecoveryEO.getItsRecoveryDtls() != null && !itsRecoveryEO.getItsRecoveryDtls().isEmpty()) {
				for (ItsRecoveryDetailsEO recoveryDtlsObj : itsRecoveryEO.getItsRecoveryDtls()) {
					ItsRecoveryDetailsVO itsRecoveryDetailsVO = new ItsRecoveryDetailsVO();
					String badActorSsn = getBadActorSSNByVictimBadActorXrefId(recoveryDtlsObj.getVictimBadActorXrefId());
					itsRecoveryDetailsVO.setBadActorSsn(badActorSsn);
					String originalSsn = getOriginalSSNVictimBadActorXrefId(recoveryDtlsObj.getVictimBadActorXrefId());
					itsRecoveryDetailsVO.setOriginalSsn(originalSsn);
					itsRecoveryDetailsVO.setOvpId(recoveryDtlsObj.getOvpId());
					Optional<VITSOvpSummaryEO> vITSOvpSummaryEOOpt = vITSOvpSummaryRepository.findById(recoveryDtlsObj.getOvpId());
					if (vITSOvpSummaryEOOpt.isPresent()) {
						VITSOvpSummaryEO vITSOvpSummaryEO = vITSOvpSummaryEOOpt.get();
						itsRecoveryDetailsVO.setTotalOvpAmount(vITSOvpSummaryEO.getOvpTotal());
						itsRecoveryDetailsVO.setOvpCurrentBalance(vITSOvpSummaryEO.getOvpBalance());
					}
					itsRecoveryDetailsVO.setPaymentAmount(recoveryDtlsObj.getPaymentAmount());
					itsRecoveryDetailsVO.setPaymentMethod(recoveryDtlsObj.getPaymentMethod());
					itsRecoveryDetailsVO.setComment(recoveryDtlsObj.getComment());
					itsRecoveryDetailsVOList.add(itsRecoveryDetailsVO);
				}
			}
		} catch (Exception e) {
			logger.error("Business Exception in RecoveryServiceImpl.populateExistingRecoveryDetailsVOList method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryServiceImpl.populateExistingRecoveryDetailsVOList() method." + e.getMessage());
		}
		
		return itsRecoveryDetailsVOList;
	}

	private String getOriginalSSNVictimBadActorXrefId(Long victimBadActorXrefId) {
		String originalSSN = null;
		try {
			Optional<ItsVictimBadActorXrefEO> itsVictimBadActorXrefEOOpt = itsVictimBadActorXrefRepository.findById(victimBadActorXrefId);
			if (itsVictimBadActorXrefEOOpt.isPresent()) {
				ItsVictimBadActorXrefEO itsVictimBadActorXrefEO = itsVictimBadActorXrefEOOpt.get();
				if (itsVictimBadActorXrefEO != null) {
					Optional<ItsClaimantDetailsEO> itsClaimantDetailsEOOpt = itsClaimantDetailsRepository.findById(itsVictimBadActorXrefEO.getClmtDtlId());
					if (itsClaimantDetailsEOOpt.isPresent()) {
						ItsClaimantDetailsEO itsClaimantDetailsEO = itsClaimantDetailsEOOpt.get();
						if (itsClaimantDetailsEO != null && itsClaimantDetailsEO.getPrtyTaxId() != null) {
							originalSSN = itsClaimantDetailsEO.getPrtyTaxId();
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("Business Exception in RecoveryServiceImpl.getOriginalSSNVictimBadActorXrefId method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryServiceImpl.getOriginalSSNVictimBadActorXrefId() method." + e.getMessage());
		}
		
		return originalSSN;
	}

	private String getBadActorSSNByVictimBadActorXrefId(Long victimBadActorXrefId) {
		String badActorSSN = null;
		try {
			Optional<ItsVictimBadActorXrefEO> itsVictimBadActorXrefEOOpt = itsVictimBadActorXrefRepository.findById(victimBadActorXrefId);
			if (itsVictimBadActorXrefEOOpt.isPresent()) {
				ItsVictimBadActorXrefEO itsVictimBadActorXrefEO = itsVictimBadActorXrefEOOpt.get();
				if (itsVictimBadActorXrefEO != null) {
					Optional<ItsBadActorDetailsEO> itsBadActorDetailsEOOpt = itsBadActorDetailsRepository.findById(itsVictimBadActorXrefEO.getBadActorId());
					if (itsBadActorDetailsEOOpt.isPresent()) {
						ItsBadActorDetailsEO itsBadActorDetailsEO = itsBadActorDetailsEOOpt.get();
						if (itsBadActorDetailsEO != null && itsBadActorDetailsEO.getBadActSSN() != null) {
							badActorSSN = itsBadActorDetailsEO.getBadActSSN();
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("Business Exception in RecoveryServiceImpl.getBadActorSSNByVictimBadActorXrefId method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryServiceImpl.getBadActorSSNByVictimBadActorXrefId() method." + e.getMessage());
		}
		
		return badActorSSN;
	}

	@Override
	public List<ItsRecoveryDetailsVO> getOverpaymentUpdateDetailsListByParams(ContextDataVO contextDataVO) {
		List<ItsRecoveryDetailsVO> itsRecoveryDetailsVOList = null;
		ItsRecoveryEO itsRecoveryEO = null;
		try {
			logger.info("Starting to calling RecoveryServiceImpl.getOverpaymentUpdateDetailsListByParams method");
			if (contextDataVO.getAllOvpUpdateInd() == null) {
				Optional<ItsRecoveryEO> itsRecoveryEOOpt = itsRecoveryRepository.findById(contextDataVO.getRecoveryId());
				if (itsRecoveryEOOpt.isPresent()) {
					itsRecoveryEO = itsRecoveryEOOpt.get();
					if (itsRecoveryEO.getAllOvpInd() != null && itsRecoveryEO.getAllOvpInd().equalsIgnoreCase("Y")) {
						itsRecoveryDetailsVOList = getvITSRecovListByRecoveryIdAndBadActorId(contextDataVO);
					} else {
						itsRecoveryDetailsVOList = getvITSRecovListByRecoveryIdAndAssociation(contextDataVO);
					}
				}
			} else {
				if (contextDataVO.getAllOvpUpdateInd() != null && contextDataVO.getAllOvpUpdateInd().equalsIgnoreCase("Y")) {
					itsRecoveryDetailsVOList = getvITSRecovListByRecoveryIdAndBadActorId(contextDataVO);
				} else {
					itsRecoveryDetailsVOList = getvITSRecovListByRecoveryIdAndAssociation(contextDataVO);
				}
			}
			
		} catch (Exception e) {
			logger.error("Business Exception in RecoveryServiceImpl.getOverpaymentUpdateDetailsListByParams method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryServiceImpl.getOverpaymentUpdateDetailsListByParams() method." + e.getMessage());
		}
		
		return itsRecoveryDetailsVOList;
	
	}

	private List<ItsRecoveryDetailsVO> getvITSRecovListByRecoveryIdAndAssociation(ContextDataVO contextDataVO) {
		List<VITSRecoveryUpdateEO> vITSRecoveryUpdateEOList = null;
		List<ItsRecoveryDetailsVO> itsRecoveryDetailsVOList = new ArrayList<>();
		try {
			if (contextDataVO.getRecoveryId() != null && contextDataVO.getVictimBadActorXrefId() != null) {
				vITSRecoveryUpdateEOList = vITSRecoveryUpdateRepository.getVITSRecoveryUpdateEOByXrefList(contextDataVO.getVictimBadActorXrefId(),contextDataVO.getRecoveryId());
				if (vITSRecoveryUpdateEOList != null && !vITSRecoveryUpdateEOList.isEmpty()) {
					for (VITSRecoveryUpdateEO vITSRecoveryUpdateEO : vITSRecoveryUpdateEOList) {
						ItsRecoveryDetailsVO itsRecoveryDetailsVO = new ItsRecoveryDetailsVO();
						itsRecoveryDetailsVO.setBadActorSsn(vITSRecoveryUpdateEO.getBadActorSsn());
						itsRecoveryDetailsVO.setOriginalSsn(vITSRecoveryUpdateEO.getPrtyTaxId());
						itsRecoveryDetailsVO.setOvpId(vITSRecoveryUpdateEO.getOvpId());
						itsRecoveryDetailsVO.setTotalOvpAmount(vITSRecoveryUpdateEO.getOvpBalance());
						if (vITSRecoveryUpdateEO.getPaymentAmount() == null)
							vITSRecoveryUpdateEO.setPaymentAmount(0.00);
						itsRecoveryDetailsVO.setOvpCurrentBalance(vITSRecoveryUpdateEO.getOvpBalance() - vITSRecoveryUpdateEO.getPaymentAmount());
						itsRecoveryDetailsVO.setPaymentAmount(vITSRecoveryUpdateEO.getPaymentAmount());
						itsRecoveryDetailsVO.setPaymentMethod(vITSRecoveryUpdateEO.getPaymentMethod());
						itsRecoveryDetailsVO.setComment(vITSRecoveryUpdateEO.getComment());
						itsRecoveryDetailsVO.setOvpBalanceCheck(vITSRecoveryUpdateEO.getOvpBalance());
						itsRecoveryDetailsVOList.add(itsRecoveryDetailsVO);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Business Exception in RecoveryServiceImpl.getvITSRecovListByRecoveryIdAndBadActorId method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryServiceImpl.getvITSRecovListByRecoveryIdAndBadActorId() method." + e.getMessage());
		}
		
		return itsRecoveryDetailsVOList;
	}

	private List<ItsRecoveryDetailsVO> getvITSRecovListByRecoveryIdAndBadActorId(ContextDataVO contextDataVO) {
		List<VITSRecoveryUpdateEO> vITSRecoveryUpdateEOList = null;
		List<ItsRecoveryDetailsVO> itsRecoveryDetailsVOList = new ArrayList<>();
		try {
			if (contextDataVO.getRecoveryId() != null && contextDataVO.getBadActorId() != null) {
				vITSRecoveryUpdateEOList = vITSRecoveryUpdateRepository.getVITSRecoveryUpdateEOByBadActorList(contextDataVO.getBadActorId(),contextDataVO.getRecoveryId());
				if (vITSRecoveryUpdateEOList != null && !vITSRecoveryUpdateEOList.isEmpty()) {
					for (VITSRecoveryUpdateEO vITSRecoveryUpdateEO : vITSRecoveryUpdateEOList) {
						ItsRecoveryDetailsVO itsRecoveryDetailsVO = new ItsRecoveryDetailsVO();
						itsRecoveryDetailsVO.setBadActorSsn(vITSRecoveryUpdateEO.getBadActorSsn());
						itsRecoveryDetailsVO.setOriginalSsn(vITSRecoveryUpdateEO.getPrtyTaxId());
						itsRecoveryDetailsVO.setOvpId(vITSRecoveryUpdateEO.getOvpId());
						itsRecoveryDetailsVO.setTotalOvpAmount(vITSRecoveryUpdateEO.getOvpTotal());
						if (vITSRecoveryUpdateEO.getPaymentAmount() == null)
							vITSRecoveryUpdateEO.setPaymentAmount(0.00);
						itsRecoveryDetailsVO.setOvpCurrentBalance(vITSRecoveryUpdateEO.getOvpBalance() - vITSRecoveryUpdateEO.getPaymentAmount());
						itsRecoveryDetailsVO.setPaymentAmount(vITSRecoveryUpdateEO.getPaymentAmount());
						itsRecoveryDetailsVO.setPaymentMethod(vITSRecoveryUpdateEO.getPaymentMethod());
						itsRecoveryDetailsVO.setComment(vITSRecoveryUpdateEO.getComment());
						itsRecoveryDetailsVO.setOvpBalanceCheck(vITSRecoveryUpdateEO.getOvpBalance());
						itsRecoveryDetailsVOList.add(itsRecoveryDetailsVO);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Business Exception in RecoveryServiceImpl.getvITSRecovListByRecoveryIdAndBadActorId method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryServiceImpl.getvITSRecovListByRecoveryIdAndBadActorId() method." + e.getMessage());
		}
		
		return itsRecoveryDetailsVOList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public ItsRecoveryVO processSelectedRecovery(Long recoveryId) {
		ItsRecoveryVO itsRecoveryVO  = new ItsRecoveryVO();
		ItsRecoveryEO itsRecoveryEO = null;
		try {
			Optional<ItsRecoveryEO> itsRecoveryEOOpt = itsRecoveryRepository.findById(recoveryId);
			if (itsRecoveryEOOpt.isPresent()) {
				itsRecoveryEO = itsRecoveryEOOpt.get();
				itsRecoveryEO.setRecoveryStatus("Processed");
				if (itsRecoveryEO.getItsRecoveryDtls() != null && !itsRecoveryEO.getItsRecoveryDtls().isEmpty()) {
					populateRecoveryProcessData(itsRecoveryEO.getItsRecoveryDtls());
				}
				createPendingRefunds(itsRecoveryEO);
				itsRecoveryRepository.save(itsRecoveryEO);
				itsRecoveryVO.setStatusMessage("Recovery has been processed successfully.");
			}
		} catch (IllegalArgumentException ia) {
			logger.error("Business Exception in RecoveryServiceImpl.processSelectedRecovery method");
			throw new BusinessException("ERR_CODE1", "Given recovery Id is null, Please provide valid recovery Id." +ia.getMessage());
		} catch (java.util.NoSuchElementException e) {
			logger.error("Business Exception in RecoveryServiceImpl.processSelectedRecovery method");
			throw new BusinessException("ERR_CODE2", "Given recovery Id does not exist in Database." +e.getMessage());
		}
		
		return itsRecoveryVO;
	}

	private void populateRecoveryProcessData(List<ItsRecoveryDetailsEO> itsRecoveryDtls) {
		try {
			for (ItsRecoveryDetailsEO itsRecoveryDetailsEO : itsRecoveryDtls) {
				applyRecovery(itsRecoveryDetailsEO);
			}
		} catch (Exception e) {
			logger.error("Business Exception in RecoveryServiceImpl.populateRecoveryProcessData method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryServiceImpl.populateRecoveryProcessData() method." + e.getMessage());
		}
	}

	private void applyRecovery(ItsRecoveryDetailsEO itsRecoveryDetailsEO) {
		try {
			Double currentPaymentAmountBal = itsRecoveryDetailsEO.getPaymentAmount();
			List<VITSRecoveryProcessInputEO> vITSRecoveryProcessInputEOList = vITSRecoveryProcessInputRepository.getRecoveryProcessInput(itsRecoveryDetailsEO.getOvpId());
			if (vITSRecoveryProcessInputEOList != null && !vITSRecoveryProcessInputEOList.isEmpty()) {
				for (VITSRecoveryProcessInputEO vITSRecoveryProcessInputEO : vITSRecoveryProcessInputEOList) {
					if (currentPaymentAmountBal > 0) {
						ItsOverpaymentTransactionsEO itsOvpTransObject = new ItsOverpaymentTransactionsEO();
						if (currentPaymentAmountBal >= vITSRecoveryProcessInputEO.getOvpBalance()) {
							itsOvpTransObject.setTransAmount(vITSRecoveryProcessInputEO.getOvpBalance() * -1);
							currentPaymentAmountBal = currentPaymentAmountBal - vITSRecoveryProcessInputEO.getOvpBalance();
						} else {
							itsOvpTransObject.setTransAmount(currentPaymentAmountBal * -1);
							currentPaymentAmountBal = 0.00;
						}
						itsOvpTransObject.setRecoveryDtlsId(itsRecoveryDetailsEO);
						ItsOverpaymentDetailsEO itsOverpaymentDetailsEO = getOverpaymentDetails(vITSRecoveryProcessInputEO.getOvpdtlsId());
						itsOvpTransObject.setOvpdtlsId(itsOverpaymentDetailsEO);
						overpaymentTransactionsRepository.save(itsOvpTransObject);
						saveOvpRecoveryDistribution(itsRecoveryDetailsEO,vITSRecoveryProcessInputEO,itsOvpTransObject);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Business Exception in RecoveryServiceImpl.applyRecovery method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryServiceImpl.applyRecovery() method." + e.getMessage());
		}
	}

	private void saveOvpRecoveryDistribution(ItsRecoveryDetailsEO itsRecoveryDetailsEO, VITSRecoveryProcessInputEO vITSRecoveryProcessInputEO,
			ItsOverpaymentTransactionsEO itsOvpTransObject) {
		Double percent = 0.00;
		try {
			if (vITSRecoveryProcessInputEO != null && vITSRecoveryProcessInputEO.getOvptypCd().equalsIgnoreCase("S") && vITSRecoveryProcessInputEO.getCbwkBweDt() != null) {
				//get over payment distribution details to distribute in each fund distributions
				List<PITSRecoveryDstPercentageEO> pITSRecoveryDstPercentageEOList = commonEntityManagerRepository.getITSRecoveryDstPercentage(vITSRecoveryProcessInputEO.getOvpdtlsId());
				if (pITSRecoveryDstPercentageEOList != null && !pITSRecoveryDstPercentageEOList.isEmpty()) {
					for (PITSRecoveryDstPercentageEO recoveryDstPercentageEO : pITSRecoveryDstPercentageEOList) {
						ItsOvpRecvDistributionsEO ovpRecvDistributionsEO = new ItsOvpRecvDistributionsEO();
						ovpRecvDistributionsEO.setRecoveryDtlsId(itsRecoveryDetailsEO);
						ItsOverpaymentDetailsEO itsOverpaymentDetailsEO = getOverpaymentDetails(vITSRecoveryProcessInputEO.getOvpdtlsId());
						ovpRecvDistributionsEO.setOvpdtlsId(itsOverpaymentDetailsEO);
						ovpRecvDistributionsEO.setPrgfndAcctNo(recoveryDstPercentageEO.getPrgfndAcctNo());
						if (recoveryDstPercentageEO.getPrgfndAcctNo().equalsIgnoreCase("$UI_UI")) {
							percent = Double.valueOf(UIUtil.roundDouble(recoveryDstPercentageEO.getMonPercentage(), 5));
						} else {
							percent = Double.valueOf(UIUtil.roundDown(recoveryDstPercentageEO.getMonPercentage(), 5));
						}
						ovpRecvDistributionsEO.setRecdstAmount(UIUtil.roundUp(itsOvpTransObject.getTransAmount().doubleValue() * percent.doubleValue(), 2));
						itsOvpRecvDistributionsRepository.save(ovpRecvDistributionsEO);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Business Exception in RecoveryServiceImpl.saveOvpRecoveryDistribution method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryServiceImpl.saveOvpRecoveryDistribution() method." + e.getMessage());
		}
	}

	private ItsOverpaymentDetailsEO getOverpaymentDetails(Long ovpdtlsId) {
		ItsOverpaymentDetailsEO itsOverpaymentDetailsEO = null;
		try {
			Optional<ItsOverpaymentDetailsEO> itsOverpaymentDetailsEOOpt = overpaymentDetailsRepository.findById(ovpdtlsId);
			if (itsOverpaymentDetailsEOOpt.isPresent()) {
				itsOverpaymentDetailsEO = itsOverpaymentDetailsEOOpt.get();
			}
			if (itsOverpaymentDetailsEO == null) {
				throw new BusinessException(ERR_CODE, "Cannot process recovery some problem occurred.");
			}
		} catch (Exception e) {
			logger.error("Business Exception in RecoveryServiceImpl.getOverpaymentDetails method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryServiceImpl.getOverpaymentDetails() method." + e.getMessage());
		}
		
		return itsOverpaymentDetailsEO;
	}

	@Override
	public List<ITSRefundsDataVO> getITSRefundsListList(Long badActorId) {
		List<VITSRefundsDataEO> vITSRefundsDataEOList = null;
		List<ITSRefundsDataVO> itsRefundsDataVOList = new ArrayList<>();
		try {
			vITSRefundsDataEOList = vITSRefundsDataRepository.findByBadActorId(badActorId);
			if (vITSRefundsDataEOList != null && !vITSRefundsDataEOList.isEmpty()) {
				for (VITSRefundsDataEO vITSRefundsDataEOObj : vITSRefundsDataEOList) {
					ITSRefundsDataVO itsRefundsDataVOObj = new ITSRefundsDataVO();
					BeanUtils.copyProperties(vITSRefundsDataEOObj, itsRefundsDataVOObj);
					itsRefundsDataVOObj.setRefDspn(getRefundStatusDesc(vITSRefundsDataEOObj));
					itsRefundsDataVOObj.setDateCreated(vITSRefundsDataEOObj.getDateCreated()!=null?UIDateRoutines.getDateString(vITSRefundsDataEOObj.getDateCreated(),null):null);
					itsRefundsDataVOObj.setRefIssuedDate(vITSRefundsDataEOObj.getRefIssuedDate()!=null?UIDateRoutines.getDateString(vITSRefundsDataEOObj.getRefIssuedDate(),null):null);
					itsRefundsDataVOList.add(itsRefundsDataVOObj);
				}
			}
		} catch (BusinessException e) {
			logger.error("Business Exception in RecoveryServiceImpl getITSRecoverySummaryList method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RecoveryServiceImpl.getITSRecoverySummaryList() method." + e.getMessage());
		}
		
		return itsRefundsDataVOList;
	}

	private String getRefundStatusDesc(VITSRefundsDataEO vITSRefundsDataEOObj) {
		String refundStatusDesc = "";
		if (vITSRefundsDataEOObj.getRefDspn().equalsIgnoreCase("P")) {
			refundStatusDesc =  "Pending Refund";
		}
		if (vITSRefundsDataEOObj.getRefDspn().equalsIgnoreCase("I")) {
			refundStatusDesc =  "Issued";
		}
		return refundStatusDesc;
	}
}
