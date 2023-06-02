package com.hcl.igovern.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.igovern.entity.ItsOverpaymentDetailsEO;
import com.hcl.igovern.entity.ItsOverpaymentDistributionsEO;
import com.hcl.igovern.entity.ItsOverpaymentEO;
import com.hcl.igovern.entity.ItsOverpaymentTransactionsEO;
import com.hcl.igovern.entity.ItsOvpRecvDistributionsEO;
import com.hcl.igovern.entity.ItsRecoveryDetailsEO;
import com.hcl.igovern.entity.VITSDistributionReversalEO;
import com.hcl.igovern.entity.VITSOverpaidWeeksEO;
import com.hcl.igovern.entity.VITSOverpaidWeeksUpdateEO;
import com.hcl.igovern.entity.VITSOverpaymentDetailsEO;
import com.hcl.igovern.entity.VITSOverpaymentUpdateEO;
import com.hcl.igovern.entity.VITSOvpDistributionEO;
import com.hcl.igovern.entity.VITSOvpRecvDstReversalEO;
import com.hcl.igovern.entity.VITSOvpStatusHistoryEO;
import com.hcl.igovern.entity.VITSOvpSummaryEO;
import com.hcl.igovern.entity.VITSRecoveryStatusEO;
import com.hcl.igovern.entity.VITSTransactionReversalEO;
import com.hcl.igovern.exception.BusinessException;
import com.hcl.igovern.repository.CommonEntityManagerRepository;
import com.hcl.igovern.repository.ItsOvpRecvDistributionsRepository;
import com.hcl.igovern.repository.ItsRecoveryDetailsRepository;
import com.hcl.igovern.repository.OverpaymentDetailsRepository;
import com.hcl.igovern.repository.OverpaymentDistributionsRepository;
import com.hcl.igovern.repository.OverpaymentRepository;
import com.hcl.igovern.repository.OverpaymentTransactionsRepository;
import com.hcl.igovern.repository.VITSDistributionReversalRepository;
import com.hcl.igovern.repository.VITSOverpaidWeeksRepository;
import com.hcl.igovern.repository.VITSOverpaidWeeksUpdateRepository;
import com.hcl.igovern.repository.VITSOverpaymentDetailsRepository;
import com.hcl.igovern.repository.VITSOverpaymentUpdateRepository;
import com.hcl.igovern.repository.VITSOvpDistributionRepository;
import com.hcl.igovern.repository.VITSOvpRecvDstReversalRepository;
import com.hcl.igovern.repository.VITSOvpStatusHistoryRepository;
import com.hcl.igovern.repository.VITSOvpSummaryRepository;
import com.hcl.igovern.repository.VITSRecoveryStatusRepository;
import com.hcl.igovern.repository.VITSTransactionReversalRepository;
import com.hcl.igovern.service.OverpaymentService;
import com.hcl.igovern.util.DateUtil;
import com.hcl.igovern.vo.ContextDataVO;
import com.hcl.igovern.vo.ITSOvpSummaryVO;
import com.hcl.igovern.vo.ITSOvpsearchDetailsVO;
import com.hcl.igovern.vo.ItsOverpaymentVO;
import com.hcl.igovern.vo.OverpaidWeeksVO;
import com.hcl.igovern.vo.SearchBadActorDataVO;


@Service
public class OverpaymentServiceImpl implements OverpaymentService {
	
	Logger logger = LoggerFactory.getLogger(OverpaymentServiceImpl.class);
	
	@Autowired
    private CommonEntityManagerRepository commonEntityManagerRepository;
	
	@Autowired
	private OverpaymentRepository overpaymentRepository;
	
	@Autowired
	private VITSOvpDistributionRepository vITSOvpDistributionRepository;
	
	@Autowired
	private VITSOvpSummaryRepository vITSOvpSummaryRepository;
	
	@Autowired
	private VITSOverpaymentUpdateRepository vITSOverpaymentUpdateRepository;
	
	@Autowired
	private VITSOverpaidWeeksRepository vITSOverpaidWeeksRepository;
	
	@Autowired
	private VITSOverpaidWeeksUpdateRepository vITSOverpaidWeeksUpdateRepository;
	
	@Autowired
	private VITSOvpStatusHistoryRepository vITSOvpStatusHistoryRepository;
	
	@Autowired
	private OverpaymentDetailsRepository overpaymentDetailsRepository;
	
	@Autowired
	private VITSTransactionReversalRepository vITSTransactionReversalRepository;
	
	@Autowired
	private VITSDistributionReversalRepository vITSDistributionReversalRepository;
	
	@Autowired
	private OverpaymentTransactionsRepository overpaymentTransactionsRepository;
	
	@Autowired
	private OverpaymentDistributionsRepository overpaymentDistributionsRepository;
	
	@Autowired
	private VITSOvpRecvDstReversalRepository vITSOvpRecvDstReversalRepository;
	
	@Autowired
	private ItsOvpRecvDistributionsRepository itsOvpRecvDistributionsRepository;
	
	@Autowired
	private ItsRecoveryDetailsRepository itsRecoveryDetailsRepository;
	
	@Autowired
	private VITSRecoveryStatusRepository vITSRecoveryStatusRepository;
	
	@Autowired
	private VITSOverpaymentDetailsRepository vITSOverpaymentDetailsRepository;
	
	public static final String ERR_CODE = "ERR_CODE";

	@Override
	public List<OverpaidWeeksVO> getProgramCodeDDList(Long victimBadActorXrefId) {
		List<VITSOverpaidWeeksEO> programCodeEOList = null;
		List<OverpaidWeeksVO> programCodeVOList = null;
		try {
			logger.info("Starting to calling OverpaymentServiceImpl method");
			programCodeEOList = commonEntityManagerRepository.getProgramCodeDDList(victimBadActorXrefId);
			if (programCodeEOList != null && !programCodeEOList.isEmpty()) {
				programCodeVOList = programCodeEOList.stream()
						.map(vITSOverpaidWeeks -> convertEOToVO(vITSOverpaidWeeks, new OverpaidWeeksVO()))
						.collect(Collectors.toList());
			}
		} catch (Exception e) {
			logger.error("Business Exception in OverpaymentServiceImpl getProgramCodeDDList method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentServiceImpl.getProgramCodeDDList() method." + e.getMessage());
		}
		
		return programCodeVOList;
	}

	@Override
	public List<OverpaidWeeksVO> getOverpaidWeeksList(ContextDataVO contextData) {
		List<VITSOverpaidWeeksEO> overpaidWeeksEOList = null;
		List<OverpaidWeeksVO> overpaidWeeksVOList = null;
		OverpaidWeeksVO overpaidWeeksVO = null;
		List<VITSOvpDistributionEO> vITSOvpDistributionList = null;
		try {
			logger.info("Starting to calling OverpaymentServiceImpl method");
			overpaidWeeksEOList = vITSOverpaidWeeksRepository.findByVictimBadActorXrefIdAndClmIdOrderByCbwkBweDt(contextData.getVictimBadActorXrefId(),contextData.getInputClaimId());
			if (overpaidWeeksEOList != null && !overpaidWeeksEOList.isEmpty()) {
				overpaidWeeksVOList = new ArrayList<>();
				for (VITSOverpaidWeeksEO vopweek : overpaidWeeksEOList) {
					overpaidWeeksVO = new OverpaidWeeksVO();
					overpaidWeeksVO = convertEOToVO(vopweek, overpaidWeeksVO);
					if (vopweek != null && vopweek.getVictimBadActorXrefId() != null && vopweek.getCbwkBweDt() != null && 
							vopweek.getClmId() != null) {
						vITSOvpDistributionList =  vITSOvpDistributionRepository.findByVictimBadActorXrefIdAndCbwkBweDtAndClmId(
								vopweek.getVictimBadActorXrefId(),vopweek.getCbwkBweDt(),vopweek.getClmId());
						if (vITSOvpDistributionList != null && !vITSOvpDistributionList.isEmpty()) {
							overpaidWeeksVO = setFundCodeData(overpaidWeeksVO, vITSOvpDistributionList);
						}
					}
					overpaidWeeksVOList.add(overpaidWeeksVO);
				}
			}
		} catch (Exception e) {
			logger.error("Business Exception in OverpaymentServiceImpl getOverpaidWeeksList method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentServiceImpl.getProgramCodeDDList() method." + e.getMessage());
		}
		
		return overpaidWeeksVOList;
	}
	
	private OverpaidWeeksVO setFundCodeData(OverpaidWeeksVO overpaidWeeksVO, List<VITSOvpDistributionEO> vovpDdstList) {
		String fundCode = "";
		fundCode = vovpDdstList.stream().filter(prgm -> prgm != null && prgm.getPrgfndAcctNo() != null)
				.map(s -> s.getPrgfndAcctNo()).collect(Collectors.joining(","));
		overpaidWeeksVO.setFundCode(fundCode);
		return overpaidWeeksVO;
	}

	private OverpaidWeeksVO convertEOToVO(VITSOverpaidWeeksEO vITSOverpaidWeeks, OverpaidWeeksVO overpaidWeeksVO) {
		if (vITSOverpaidWeeks != null) {
			if (vITSOverpaidWeeks.getClmId() != null)
				overpaidWeeksVO.setClaimId(vITSOverpaidWeeks.getClmId());
			if (vITSOverpaidWeeks.getCbwkBweDt() != null)
				overpaidWeeksVO.setCbwkBweDt(DateUtil.tsDateToStr(vITSOverpaidWeeks.getCbwkBweDt()));
			if (vITSOverpaidWeeks.getPaymentAmount() != null)
				overpaidWeeksVO.setPaymentAmount(vITSOverpaidWeeks.getPaymentAmount());
			if (vITSOverpaidWeeks.getPrgmCd() != null)
				overpaidWeeksVO.setPrgmCd(vITSOverpaidWeeks.getPrgmCd());
			if (vITSOverpaidWeeks.getVictimBadActorXrefId() != null)
				overpaidWeeksVO.setVictimBadActorXrefId(vITSOverpaidWeeks.getVictimBadActorXrefId());
			overpaidWeeksVO.setRecoveryAmount(0.00);
		}
		return overpaidWeeksVO;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public ItsOverpaymentVO addOverpaymentAndDetails(ItsOverpaymentVO itsOverpaymentVO) {
		if (itsOverpaymentVO.getItsOverpaymentDtls().isEmpty())
			throw new BusinessException(ERR_CODE, "Please select at-least one Overpaid Weeks");
		try {
			if (itsOverpaymentVO.getOvpId() == null) {
				if ("C".equalsIgnoreCase(itsOverpaymentVO.getOvpdisCd()) || "CAN".equalsIgnoreCase(itsOverpaymentVO.getOvpstsCd())) {
					throw new BusinessException(ERR_CODE, "Cannot create a cancelled overpayment.");
				}
				ItsOverpaymentEO itsOverpaymentEO = createOverpaymentDetailsData(itsOverpaymentVO);
				if (!itsOverpaymentEO.getItsOverpaymentDtls().isEmpty()) {
					itsOverpaymentEO = createOvpDstAndTransData(itsOverpaymentEO);
				}
				overpaymentRepository.save(itsOverpaymentEO);
				itsOverpaymentVO.setStatusMessage("Overpayment has been successfully added.");
			}
			
			if (itsOverpaymentVO.getOvpId() != null) {
				itsOverpaymentVO = updateOverpaymentAndDetails(itsOverpaymentVO);
			}
		} catch (Exception e) {
			logger.error("Business Exception in OverpaymentServiceImpl.addOverpaymentAndDetails method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentServiceImpl.addOverpaymentAndDetails() method." + e.getMessage());
		}
		return itsOverpaymentVO;
	}

	private ItsOverpaymentVO updateOverpaymentAndDetails(ItsOverpaymentVO itsOverpaymentVO) {
		boolean isRecoveryClosedStaus = false;
		boolean isCancelledOverpayment = false;
		try {
			isRecoveryClosedStaus = checkRecoveryStatusOnOverpayment(itsOverpaymentVO.getOvpId());
			isCancelledOverpayment = checkOverpaymentCancelledStatus(itsOverpaymentVO.getOvpId());
			if (!isRecoveryClosedStaus && !isCancelledOverpayment) {
				updateItsOverpaymentData(itsOverpaymentVO);
				itsOverpaymentVO.setStatusMessage("Overpayment has been successfully updated.");
			} else {
				if (isRecoveryClosedStaus) {
					itsOverpaymentVO.setValidationErrorMessage("Overpayment cannot be updated as unprocessed recovery exists.");
				}
				if (isCancelledOverpayment) {
					itsOverpaymentVO.setValidationErrorMessage("Cancelled overpayments cannot be updated.");
				}
			}
		} catch (Exception e) {
			logger.error("Business Exception in OverpaymentServiceImpl.updateOverpaymentAndDetails method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentServiceImpl.updateOverpaymentAndDetails() method." + e.getMessage());
		}
		return itsOverpaymentVO;
	}

	private boolean checkOverpaymentCancelledStatus(Long ovpId) {
		boolean isCancelledOverpayment = false;
		ItsOverpaymentEO itsOverpaymentEO = getItsOverpaymentEO(ovpId);
		if (itsOverpaymentEO != null && itsOverpaymentEO.getOvpstsCd().equals("CAN")) {
			isCancelledOverpayment = true;
		}
		return isCancelledOverpayment;
	}

	private boolean checkRecoveryStatusOnOverpayment(Long ovpId) {
		boolean isRecoveryClosedStaus = false;
		try {
			List<VITSRecoveryStatusEO> recoveryStatusEOList = vITSRecoveryStatusRepository.findByOvpId(ovpId);
			if (recoveryStatusEOList != null && !recoveryStatusEOList.isEmpty()) {
				isRecoveryClosedStaus = true;
			}
		} catch (Exception e) {
			logger.error("Business Exception in OverpaymentServiceImpl addOverpaymentAndDetails method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentServiceImpl.addOverpaymentAndDetails() method." + e.getMessage());
		}
		return isRecoveryClosedStaus;
	}

	private void updateItsOverpaymentData(ItsOverpaymentVO itsOverpaymentVO) {
		try {
			ItsOverpaymentEO itsOverpaymentEO = getItsOverpaymentEO(itsOverpaymentVO.getOvpId());
			if (itsOverpaymentEO != null) {
				itsOverpaymentEO.setOvpdisCd(itsOverpaymentVO.getOvpdisCd());
				itsOverpaymentEO.setOvpclsCd(itsOverpaymentVO.getOvpclsCd());
				itsOverpaymentEO.setOvpstsCd(itsOverpaymentVO.getOvpstsCd());
				itsOverpaymentEO.setOvporgCd(itsOverpaymentVO.getOvporgCd());
				itsOverpaymentEO.setOvpId(itsOverpaymentVO.getOvpId());
				itsOverpaymentEO.setOvpdorgCd(itsOverpaymentVO.getOvpdorgCd());
				if (itsOverpaymentEO.getItsOverpaymentDtls() != null && !itsOverpaymentEO.getItsOverpaymentDtls().isEmpty() && 
						itsOverpaymentVO.getItsOverpaymentDtls() != null && !itsOverpaymentVO.getItsOverpaymentDtls().isEmpty()) {
					updateItsOverpaymentDetailsEOList(itsOverpaymentEO,itsOverpaymentVO);
				}
			}
		} catch (Exception e) {
			logger.error("Business Exception in OverpaymentServiceImpl.updateItsOverpaymentData method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentServiceImpl.updateItsOverpaymentData() method." + e.getMessage());
		}
	}

	private ItsOverpaymentEO getItsOverpaymentEO(Long ovpId) {
		ItsOverpaymentEO itsOverpaymentEO = null;
		try {
			if (ovpId != null) {
				Optional<ItsOverpaymentEO> itsOverpaymentEOOpt = overpaymentRepository.findById(ovpId);
				if (itsOverpaymentEOOpt.isPresent()) {
					itsOverpaymentEO = itsOverpaymentEOOpt.get();
				}
			}
		} catch (Exception e) {
			logger.error("Business Exception in OverpaymentServiceImpl.getItsOverpaymentEO method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentServiceImpl.getItsOverpaymentEO() method." + e.getMessage());
		}
		
		return itsOverpaymentEO;
	}
	
	private void updateItsOverpaymentDetailsEOList(ItsOverpaymentEO itsOverpaymentEO, ItsOverpaymentVO itsOverpaymentVO) {
		List<ItsOverpaymentDetailsEO> itsOverpaymentDetailsList = new ArrayList<>();
		try {
			for (OverpaidWeeksVO opWeek : itsOverpaymentVO.getItsOverpaymentDtls()) {
				if (opWeek.getOvpdtlsId() == null) {
					// insert over payment detail data and maintain ItsOverpaymentDetailsEO list and store payment amount
					ItsOverpaymentDetailsEO itsOvpDetails = new ItsOverpaymentDetailsEO();
					itsOverpaymentVO.setProgCode(opWeek.getPrgmCd());
					itsOvpDetails.setOvptypCd("S");
					itsOvpDetails.setCbwkBweDt(DateUtil.strDateToTs(opWeek.getCbwkBweDt()));
					setClaimIdOrAddCompPrgDtlId(itsOverpaymentVO, itsOvpDetails);
					itsOvpDetails.setItsOverpayment(itsOverpaymentEO);
					overpaymentDetailsRepository.save(itsOvpDetails);
					itsOvpDetails.setOvpAmt(opWeek.getPaymentAmount());
					itsOverpaymentDetailsList.add(itsOvpDetails);
				} else {
					// maintain ItsOverpaymentDetailsEO list
					ItsOverpaymentDetailsEO itsOvpDetailsExist = getItsOverpaymentDetailsEO(opWeek.getOvpdtlsId());
					if (itsOvpDetailsExist != null) {
						itsOvpDetailsExist.setOvpAmt(opWeek.getPaymentAmount());
						itsOverpaymentDetailsList.add(itsOvpDetailsExist);
					}
				}
			}
			itsOverpaymentDetailsList = updateExistingOverpaymentDetails(itsOverpaymentVO,itsOverpaymentEO,itsOverpaymentDetailsList);
			// maintain over payment transaction and distributions data
			maintainOvpTransactionAndDistribution(itsOverpaymentEO,itsOverpaymentDetailsList);
		} catch (Exception e) {
			logger.error("Business Exception in OverpaymentServiceImpl.updateItsOverpaymentDetailsEOList method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentServiceImpl.updateItsOverpaymentDetailsEOList() method." + e.getMessage());
		}
	}

	private void maintainOvpTransactionAndDistribution(ItsOverpaymentEO itsOverpaymentEO, List<ItsOverpaymentDetailsEO> itsOverpaymentDetailsList) {
		try {
			reverseOvpTransactionAndDistribution(itsOverpaymentEO);
			Long victimXrefId = itsOverpaymentEO.getVictimBadActorXrefId();
			if (!itsOverpaymentEO.getOvpstsCd().equalsIgnoreCase("CAN") && itsOverpaymentDetailsList != null && !itsOverpaymentDetailsList.isEmpty()) {
				for (ItsOverpaymentDetailsEO opweek : itsOverpaymentDetailsList) {
					ItsOverpaymentTransactionsEO transactionsEO = new ItsOverpaymentTransactionsEO();
					transactionsEO.setTransAmount(opweek.getOvpAmt());
					transactionsEO.setOvpdtlsId(opweek);
					overpaymentTransactionsRepository.save(transactionsEO);
					if (opweek.getCbwkBweDt() != null) {
						maintainOverpaymentDistribution(victimXrefId,opweek);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Business Exception in OverpaymentServiceImpl.maintainOvpTransactionAndDistribution method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentServiceImpl.maintainOvpTransactionAndDistribution() method." + e.getMessage());
		}
	}

	private void maintainOverpaymentDistribution(Long victimXrefId, ItsOverpaymentDetailsEO opweek) {
		try {
			List<VITSOvpDistributionEO> vITSOvpDistributionList = vITSOvpDistributionRepository.findByVictimBadActorXrefIdAndCbwkBweDtAndClmId(victimXrefId,opweek.getCbwkBweDt(),opweek.getClmId());
			if (!vITSOvpDistributionList.isEmpty()) {
				for (VITSOvpDistributionEO vITSOvpDistributionEO : vITSOvpDistributionList) {
					ItsOverpaymentDistributionsEO itsOverpaymentDsts = new ItsOverpaymentDistributionsEO();
					itsOverpaymentDsts.setPrgfndAcctNo(vITSOvpDistributionEO.getPrgfndAcctNo());
					itsOverpaymentDsts.setOvpdstAmount(vITSOvpDistributionEO.getEndstPdAmt());
					itsOverpaymentDsts.setOvpdtlsId(opweek);
					overpaymentDistributionsRepository.save(itsOverpaymentDsts);
				}
			}
		} catch (Exception e) {
			logger.error("Business Exception in OverpaymentServiceImpl.maintainOvpTransactionAndDistribution method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentServiceImpl.maintainOvpTransactionAndDistribution() method." + e.getMessage());
		}
	}

	private void reverseOvpTransactionAndDistribution(ItsOverpaymentEO itsOverpaymentEO) {
		ItsOverpaymentDetailsEO itsOvpDetailsEO = null;
		ItsRecoveryDetailsEO itsRecoveryDetailsEO = null;
		try {
			List<VITSTransactionReversalEO> transReversalEOList = vITSTransactionReversalRepository.getByOvpId(itsOverpaymentEO.getOvpId());
			if (transReversalEOList != null && !transReversalEOList.isEmpty()) {
				for (VITSTransactionReversalEO transReversalEO : transReversalEOList) {
					ItsOverpaymentTransactionsEO overpaymentTransEO = new ItsOverpaymentTransactionsEO();
					itsOvpDetailsEO = getItsOverpaymentDetailsEO(transReversalEO.getOvpdtlsId());
					if (itsOvpDetailsEO != null) {
						overpaymentTransEO.setOvpdtlsId(itsOvpDetailsEO);
						if (transReversalEO.getRecoveryDtlsId() != null) {
							itsRecoveryDetailsEO = getItsRecoveryDetailsEO(transReversalEO.getRecoveryDtlsId());
							overpaymentTransEO.setRecoveryDtlsId(itsRecoveryDetailsEO);
						}
						overpaymentTransEO.setTransAmount(transReversalEO.getReversalTransAmount() * -1);
						overpaymentTransactionsRepository.save(overpaymentTransEO);
						callReverseOvpDistribution(transReversalEO,itsOvpDetailsEO);
						callReverseOvpRecvDistribution(transReversalEO,itsOvpDetailsEO);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Business Exception in OverpaymentServiceImpl.reverseOvpTransactionAndDistribution method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentServiceImpl.reverseOvpTransactionAndDistribution() method." + e.getMessage());
		}
	}

	private void callReverseOvpRecvDistribution(VITSTransactionReversalEO transReversalEO, ItsOverpaymentDetailsEO itsOvpDetailsEO) {
		if (itsOvpDetailsEO != null && itsOvpDetailsEO.getCbwkBweDt() != null && transReversalEO.getRecoveryDtlsId() != null) {
			reverseOvpRecvDistribution(transReversalEO,itsOvpDetailsEO);
		}
	}

	private void callReverseOvpDistribution(VITSTransactionReversalEO transReversalEO, ItsOverpaymentDetailsEO itsOvpDetailsEO) {
		if (itsOvpDetailsEO != null && itsOvpDetailsEO.getCbwkBweDt() != null && transReversalEO.getOvpdtlsId() != null && 
				transReversalEO.getRecoveryDtlsId() == null) {
			reverseOvpDistribution(transReversalEO,itsOvpDetailsEO);
		}
	}

	private void reverseOvpRecvDistribution(VITSTransactionReversalEO transReversalEO, ItsOverpaymentDetailsEO itsOvpDetailsEO) {
		try {
			List<VITSOvpRecvDstReversalEO> ovpRecvDstReversalEOList = vITSOvpRecvDstReversalRepository.findByRecoveryDtlsIdAndOvpdtlsId(transReversalEO.getRecoveryDtlsId(),transReversalEO.getOvpdtlsId());
			ItsRecoveryDetailsEO itsRecoveryDetailsEO = null;
			Optional<ItsRecoveryDetailsEO> itsRecoveryDtlsEOOpt = itsRecoveryDetailsRepository.findById(transReversalEO.getRecoveryDtlsId());
			if (itsRecoveryDtlsEOOpt.isPresent()) {
				itsRecoveryDetailsEO = itsRecoveryDtlsEOOpt.get();
			}
			if (ovpRecvDstReversalEOList != null && !ovpRecvDstReversalEOList.isEmpty() && itsRecoveryDetailsEO != null) {
				for (VITSOvpRecvDstReversalEO ovpRecvDstReversalEO : ovpRecvDstReversalEOList) {
					ItsOvpRecvDistributionsEO ovpRecvDistributionsEO = new ItsOvpRecvDistributionsEO();
					ovpRecvDistributionsEO.setOvpdtlsId(itsOvpDetailsEO);
					ovpRecvDistributionsEO.setRecoveryDtlsId(itsRecoveryDetailsEO);
					ovpRecvDistributionsEO.setPrgfndAcctNo(ovpRecvDstReversalEO.getPrgfndAcctNo());
					ovpRecvDistributionsEO.setRecdstAmount(ovpRecvDstReversalEO.getReversalRecdstAmount() * -1);
					itsOvpRecvDistributionsRepository.save(ovpRecvDistributionsEO);
				}
			}
		} catch (Exception e) {
			logger.error("Business Exception in OverpaymentServiceImpl.reverseOvpRecvDistribution method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentServiceImpl.reverseOvpRecvDistribution() method." + e.getMessage());
		}
	}

	private void reverseOvpDistribution(VITSTransactionReversalEO transReversalEO, ItsOverpaymentDetailsEO itsOvpDetailsEO) {
		try {
			List<VITSDistributionReversalEO> dstReversalEOList = vITSDistributionReversalRepository.findByOvpdtlsId(transReversalEO.getOvpdtlsId());
			if (dstReversalEOList != null && !dstReversalEOList.isEmpty()) {
				for (VITSDistributionReversalEO dstReversalEO : dstReversalEOList) {
					ItsOverpaymentDistributionsEO overpaymentDistributionsEO = new ItsOverpaymentDistributionsEO();
					overpaymentDistributionsEO.setPrgfndAcctNo(dstReversalEO.getPrgfndAcctNo());
					overpaymentDistributionsEO.setOvpdstAmount(dstReversalEO.getReversalDstAmount() * -1);
					overpaymentDistributionsEO.setOvpdtlsId(itsOvpDetailsEO);
					overpaymentDistributionsRepository.save(overpaymentDistributionsEO);
				}
			}
		} catch (Exception e) {
			logger.error("Business Exception in OverpaymentServiceImpl.reverseOvpDistribution method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentServiceImpl.reverseOvpDistribution() method." + e.getMessage());
		}
	}

	private List<ItsOverpaymentDetailsEO> updateExistingOverpaymentDetails(ItsOverpaymentVO itsOverpaymentVO, ItsOverpaymentEO itsOverpaymentEO, 
			List<ItsOverpaymentDetailsEO> itsOverpaymentDetailsList) {
		try {
			for (ItsOverpaymentDetailsEO itsOverpaymentDetailsEO : itsOverpaymentEO.getItsOverpaymentDtls()) {
				boolean isDetailsExist = false;
				if (itsOverpaymentDetailsEO.getCbwkBweDt() != null) {
					for (OverpaidWeeksVO opWeek : itsOverpaymentVO.getItsOverpaymentDtls()) {
						if (opWeek.getOvpdtlsId() != null && itsOverpaymentDetailsEO.getOvpdtlsId() != null
								&& itsOverpaymentDetailsEO.getOvpdtlsId().equals(opWeek.getOvpdtlsId())
								&& !isDetailsExist) {
							isDetailsExist = true;
						}
					}
					// update over payment detail data as delete indicator as Y
					updateExistingOvpDetails(itsOverpaymentDetailsEO, isDetailsExist);
				} else {
					// maintain ItsOverpaymentDetailsEO list for Penalty/COC/Interest and store payment amount
					itsOverpaymentDetailsList = maintainPenaltyAndCocAndInterest(itsOverpaymentDetailsEO,itsOverpaymentVO,itsOverpaymentDetailsList);
				}
			}
		} catch (Exception e) {
			logger.error("Business Exception in OverpaymentServiceImpl.updateExistingOverpaymentDetails method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentServiceImpl.updateExistingOverpaymentDetails() method." + e.getMessage());
		}
		return itsOverpaymentDetailsList;
	}

	private ItsOverpaymentDetailsEO maintainCoc(ItsOverpaymentDetailsEO itsOverpaymentDetailsEO, ItsOverpaymentVO itsOverpaymentVO) {
		ItsOverpaymentDetailsEO itsOvpDetailsC = null;
		try {
			itsOvpDetailsC = getItsOverpaymentDetailsEO(itsOverpaymentDetailsEO.getOvpdtlsId());
			if (itsOvpDetailsC != null) {
				if (itsOverpaymentVO.getCoc() == null)
					itsOverpaymentVO.setCoc(0.00);
				itsOvpDetailsC.setOvpAmt(itsOverpaymentVO.getCoc());
			}
		} catch (Exception e) {
			logger.error("Business Exception in OverpaymentServiceImpl.maintainCoc method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentServiceImpl.maintainCoc() method." + e.getMessage());
		}
		return itsOvpDetailsC;
	}

	private ItsOverpaymentDetailsEO maintainInterest(ItsOverpaymentDetailsEO itsOverpaymentDetailsEO, ItsOverpaymentVO itsOverpaymentVO) {
		ItsOverpaymentDetailsEO itsOvpDetailsI = null;
		try {
			itsOvpDetailsI = getItsOverpaymentDetailsEO(itsOverpaymentDetailsEO.getOvpdtlsId());
			if (itsOvpDetailsI != null) {
				if (itsOverpaymentVO.getInterest() == null)
					itsOverpaymentVO.setInterest(0.00);
				itsOvpDetailsI.setOvpAmt(itsOverpaymentVO.getInterest());
			}
		} catch (Exception e) {
			logger.error("Business Exception in OverpaymentServiceImpl.maintainInterest method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentServiceImpl.maintainInterest() method." + e.getMessage());
		}
		return itsOvpDetailsI;
	}

	private ItsOverpaymentDetailsEO maintainPenalty(ItsOverpaymentDetailsEO itsOverpaymentDetailsEO, ItsOverpaymentVO itsOverpaymentVO) {
		ItsOverpaymentDetailsEO itsOvpDetailsP = null;
		try {
			itsOvpDetailsP = getItsOverpaymentDetailsEO(itsOverpaymentDetailsEO.getOvpdtlsId());
			if (itsOvpDetailsP != null) {
				if (itsOverpaymentVO.getPenalty() == null)
					itsOverpaymentVO.setPenalty(0.00);
				itsOvpDetailsP.setOvpAmt(itsOverpaymentVO.getPenalty());
			}
		} catch (Exception e) {
			logger.error("Business Exception in OverpaymentServiceImpl.maintainPenalty method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentServiceImpl.maintainPenalty() method." + e.getMessage());
		}
		return itsOvpDetailsP;
	}

	private List<ItsOverpaymentDetailsEO> maintainPenaltyAndCocAndInterest(ItsOverpaymentDetailsEO itsOverpaymentDetailsEO,ItsOverpaymentVO itsOverpaymentVO,
			List<ItsOverpaymentDetailsEO> itsOverpaymentDetailsList) {
		try {
			if (itsOverpaymentDetailsEO.getOvpdtlsId() != null && itsOverpaymentDetailsEO.getOvptypCd().equalsIgnoreCase("P")) {
				ItsOverpaymentDetailsEO itsOvpDetailsP = maintainPenalty(itsOverpaymentDetailsEO,itsOverpaymentVO);
				if (itsOvpDetailsP != null)
					itsOverpaymentDetailsList.add(itsOvpDetailsP);
			}
			if (itsOverpaymentDetailsEO.getOvpdtlsId() != null && itsOverpaymentDetailsEO.getOvptypCd().equalsIgnoreCase("I")) {
				ItsOverpaymentDetailsEO itsOvpDetailsI = maintainInterest(itsOverpaymentDetailsEO,itsOverpaymentVO);
				if (itsOvpDetailsI != null)
					itsOverpaymentDetailsList.add(itsOvpDetailsI);
			}
			if (itsOverpaymentDetailsEO.getOvpdtlsId() != null && itsOverpaymentDetailsEO.getOvptypCd().equalsIgnoreCase("C")) {
				ItsOverpaymentDetailsEO itsOvpDetailsC = maintainCoc(itsOverpaymentDetailsEO,itsOverpaymentVO);
				if (itsOvpDetailsC != null)
					itsOverpaymentDetailsList.add(itsOvpDetailsC);
			}
		} catch (Exception e) {
			logger.error("Business Exception in OverpaymentServiceImpl.maintainPenaltyAndCocAndInterest method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentServiceImpl.maintainPenaltyAndCocAndInterest() method." + e.getMessage());
		}
		return itsOverpaymentDetailsList;
	}

	private void updateExistingOvpDetails(ItsOverpaymentDetailsEO itsOverpaymentDetailsEO,boolean isDetailsExist) {
		try {
			if (!isDetailsExist) {
				ItsOverpaymentDetailsEO itsOvpDetailsEO = null;
				Optional<ItsOverpaymentDetailsEO> itsOverpaymentDetailsEOOpt = overpaymentDetailsRepository.findById(itsOverpaymentDetailsEO.getOvpdtlsId());
				if (itsOverpaymentDetailsEOOpt.isPresent()) {
					itsOvpDetailsEO = itsOverpaymentDetailsEOOpt.get();
					itsOvpDetailsEO.setDeleteInd("Y");
					overpaymentDetailsRepository.save(itsOvpDetailsEO);
				}
			}
		} catch (Exception e) {
			logger.error("Business Exception in OverpaymentServiceImpl.updateExistingOvpDetails method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentServiceImpl.updateExistingOvpDetails() method." + e.getMessage());
		}
	}

	private ItsOverpaymentEO createOverpaymentDetailsData(ItsOverpaymentVO itsOverpaymentVO) {
		ItsOverpaymentEO itsOverpaymentTemp = new ItsOverpaymentEO();
		List<ItsOverpaymentDetailsEO> itsOverpaymentDetailsList = new ArrayList<>();
		try {
			itsOverpaymentTemp.setVictimBadActorXrefId(itsOverpaymentVO.getVictimBadActorXrefId());
			itsOverpaymentTemp.setOvpdisCd(itsOverpaymentVO.getOvpdisCd());
			itsOverpaymentTemp.setOvpclsCd(itsOverpaymentVO.getOvpclsCd());
			itsOverpaymentTemp.setOvpstsCd(itsOverpaymentVO.getOvpstsCd());
			itsOverpaymentTemp.setOvporgCd(itsOverpaymentVO.getOvporgCd());
			itsOverpaymentTemp.setOvpId(itsOverpaymentVO.getOvpId());
			itsOverpaymentTemp.setOvpdorgCd(itsOverpaymentVO.getOvpdorgCd());
			if (!itsOverpaymentVO.getItsOverpaymentDtls().isEmpty()) {
				for (OverpaidWeeksVO overpaidWeeksTemp : itsOverpaymentVO.getItsOverpaymentDtls()) {
					ItsOverpaymentDetailsEO itsOvpDetails = new ItsOverpaymentDetailsEO();
					itsOverpaymentVO.setProgCode(overpaidWeeksTemp.getPrgmCd());
					itsOvpDetails.setOvptypCd("S");
					itsOvpDetails.setCbwkBweDt(DateUtil.strDateToTs(overpaidWeeksTemp.getCbwkBweDt()));
					setClaimIdOrAddCompPrgDtlId(itsOverpaymentVO, itsOvpDetails);
					itsOvpDetails.setOvpAmt(overpaidWeeksTemp.getPaymentAmount());
					itsOvpDetails.setItsOverpayment(itsOverpaymentTemp);
					itsOverpaymentDetailsList.add(itsOvpDetails);
				}
			}
			// Penalty
			ItsOverpaymentDetailsEO itsOvpDetailsP = new ItsOverpaymentDetailsEO();
			itsOvpDetailsP.setOvptypCd("P");
			if (itsOverpaymentVO.getPenalty() == null)
				itsOverpaymentVO.setPenalty(0.00);
			itsOvpDetailsP.setOvpAmt(itsOverpaymentVO.getPenalty());
			setClaimIdOrAddCompPrgDtlId(itsOverpaymentVO, itsOvpDetailsP);
			itsOvpDetailsP.setItsOverpayment(itsOverpaymentTemp);
			itsOverpaymentDetailsList.add(itsOvpDetailsP);
			// Interest
			ItsOverpaymentDetailsEO itsOvpDetailsI = new ItsOverpaymentDetailsEO();
			itsOvpDetailsI.setOvptypCd("I");
			if (itsOverpaymentVO.getInterest() == null)
				itsOverpaymentVO.setInterest(0.00);
			itsOvpDetailsI.setOvpAmt(itsOverpaymentVO.getInterest());
			setClaimIdOrAddCompPrgDtlId(itsOverpaymentVO, itsOvpDetailsI);
			itsOvpDetailsI.setItsOverpayment(itsOverpaymentTemp);
			itsOverpaymentDetailsList.add(itsOvpDetailsI);
			// COC
			ItsOverpaymentDetailsEO itsOvpDetailsC = new ItsOverpaymentDetailsEO();
			itsOvpDetailsC.setOvptypCd("C");
			if (itsOverpaymentVO.getCoc() == null)
				itsOverpaymentVO.setCoc(0.00);
			itsOvpDetailsC.setOvpAmt(itsOverpaymentVO.getCoc());
			setClaimIdOrAddCompPrgDtlId(itsOverpaymentVO, itsOvpDetailsC);
			itsOvpDetailsC.setItsOverpayment(itsOverpaymentTemp);
			itsOverpaymentDetailsList.add(itsOvpDetailsC);
			
			itsOverpaymentTemp.setItsOverpaymentDtls(itsOverpaymentDetailsList);
		
		} catch (Exception e) {
			logger.error("Business Exception in OverpaymentServiceImpl createOverpaymentDetailsData method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentServiceImpl.createOverpaymentDetailsData() method." + e.getMessage());
		}
		
		return itsOverpaymentTemp;
	}

	private void setClaimIdOrAddCompPrgDtlId(ItsOverpaymentVO itsOverpaymentVO, ItsOverpaymentDetailsEO itsOvpDetails) {
		if ("FPUC".equalsIgnoreCase(itsOverpaymentVO.getProgCode()) || "LWA".equalsIgnoreCase(itsOverpaymentVO.getProgCode()) 
				|| "MEUC".equalsIgnoreCase(itsOverpaymentVO.getProgCode())) {
			itsOvpDetails.setAddCompPrgDtlId(itsOverpaymentVO.getClaimId());
		} else {
			itsOvpDetails.setClmId(itsOverpaymentVO.getClaimId());
		}
	}
	
	private ItsOverpaymentEO createOvpDstAndTransData(ItsOverpaymentEO itsOverpaymentEO) {
		List<ItsOverpaymentDetailsEO> itsOverpaymentDetailsEOList = new ArrayList<>();
		List<ItsOverpaymentDistributionsEO> itsOverpaymentDstList = new ArrayList<>();
		Long victimXrefId = itsOverpaymentEO.getVictimBadActorXrefId();
		try {
			for (ItsOverpaymentDetailsEO opweek : itsOverpaymentEO.getItsOverpaymentDtls()) {
				List<ItsOverpaymentTransactionsEO> itstransactionDetailsList = new ArrayList<>();
				ItsOverpaymentTransactionsEO transactionsEO = new ItsOverpaymentTransactionsEO();
				transactionsEO.setTransAmount(opweek.getOvpAmt());
				transactionsEO.setOvpdtlsId(opweek);
				itstransactionDetailsList.add(transactionsEO);
				opweek.setItsOverpaymentTranDtls(itstransactionDetailsList);
				if (opweek.getCbwkBweDt() != null) {
					List<VITSOvpDistributionEO> vITSOvpDistributionList = vITSOvpDistributionRepository.findByVictimBadActorXrefIdAndCbwkBweDtAndClmId(victimXrefId,opweek.getCbwkBweDt(),opweek.getClmId());
					if (!vITSOvpDistributionList.isEmpty()) {
						for (VITSOvpDistributionEO vITSOvpDistributionEO : vITSOvpDistributionList) {
							ItsOverpaymentDistributionsEO itsOverpaymentDsts = new ItsOverpaymentDistributionsEO();
							itsOverpaymentDsts.setPrgfndAcctNo(vITSOvpDistributionEO.getPrgfndAcctNo());
							itsOverpaymentDsts.setOvpdstAmount(vITSOvpDistributionEO.getEndstPdAmt());
							itsOverpaymentDsts.setOvpdtlsId(opweek);
							itsOverpaymentDstList.add(itsOverpaymentDsts);
						}
						opweek.setItsOverpaymentDstDtls(itsOverpaymentDstList);
					}
				}
				itsOverpaymentDetailsEOList.add(opweek);
			}
			itsOverpaymentEO.setItsOverpaymentDtls(itsOverpaymentDetailsEOList);
		} catch (Exception e) {
			logger.error("Business Exception in OverpaymentServiceImpl createOvpDstAndTransData method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentServiceImpl.createOvpDstAndTransData() method." + e.getMessage());
		}
	
		return itsOverpaymentEO;
	}

	@Override
	public List<ITSOvpSummaryVO> getITSOverpaymentSummaryList(Long victimBadActorXrefId) {
		List<VITSOvpSummaryEO> vITSOvpSummaryEOList = null;
		List<ITSOvpSummaryVO> itsOvpSummaryVOList = new ArrayList<>();
		try {
			vITSOvpSummaryEOList = vITSOvpSummaryRepository.findByVictimBadActorXrefId(victimBadActorXrefId);
			if (vITSOvpSummaryEOList != null && !vITSOvpSummaryEOList.isEmpty()) {
				for (VITSOvpSummaryEO vITSOvpSummaryObj : vITSOvpSummaryEOList) {
					if (vITSOvpSummaryObj != null) {
						ITSOvpSummaryVO itsOvpSummaryVOObj = new ITSOvpSummaryVO();
						BeanUtils.copyProperties(vITSOvpSummaryObj, itsOvpSummaryVOObj);
						itsOvpSummaryVOObj.setRecoveryAmount(itsOvpSummaryVOObj.getRecoveryAmount() * -1);
						itsOvpSummaryVOList.add(itsOvpSummaryVOObj);
					}
				}
			}
		} catch (BusinessException e) {
			logger.error("Business Exception in OverpaymentServiceImpl getITSOverpaymentSummaryList method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentServiceImpl.getITSOverpaymentSummaryList() method." + e.getMessage());
		}
		
		return itsOvpSummaryVOList;
	}

	@Override
	public ItsOverpaymentVO getITSOverpaymentAndOvpDetails(Long ovpId) {
		VITSOverpaymentUpdateEO vITSOverpaymentUpdateEO = null;
		ItsOverpaymentVO itsOverpaymentVO  = null;
		try {
			Optional<VITSOverpaymentUpdateEO> vITSOverpaymentUpdateEOOpt = vITSOverpaymentUpdateRepository.findById(ovpId);
			if (vITSOverpaymentUpdateEOOpt.isPresent()) {
				vITSOverpaymentUpdateEO = vITSOverpaymentUpdateEOOpt.get();
			}
			if (vITSOverpaymentUpdateEO != null) {
				itsOverpaymentVO = new ItsOverpaymentVO();
				itsOverpaymentVO = populateItsOverpaymentVOData(vITSOverpaymentUpdateEO,itsOverpaymentVO);
			}
		} catch (IllegalArgumentException ia) {
			throw new BusinessException("ERR_CODE1", "Given overpayment Id is null, Please provide valid overpayment Id." +ia.getMessage());
		} catch (java.util.NoSuchElementException e) {
			throw new BusinessException("ERR_CODE2", "Given overpayment Id does not exist in Database." +e.getMessage());
		}
		
		return itsOverpaymentVO;
	}

	private ItsOverpaymentVO populateItsOverpaymentVOData(VITSOverpaymentUpdateEO vITSOverpaymentUpdateEO, ItsOverpaymentVO itsOverpaymentVO) {
		itsOverpaymentVO.setOvpId(vITSOverpaymentUpdateEO.getOvpId());
		itsOverpaymentVO.setVictimBadActorXrefId(vITSOverpaymentUpdateEO.getVictimBadActorXrefId());
		itsOverpaymentVO.setDateCreated(vITSOverpaymentUpdateEO.getDateCreated());
		itsOverpaymentVO.setDateModified(DateUtil.getCurrentDateString());
		itsOverpaymentVO.setOvpdisCd(vITSOverpaymentUpdateEO.getOvpdisCd());
		itsOverpaymentVO.setOvpclsCd(vITSOverpaymentUpdateEO.getOvpclsCd());
		itsOverpaymentVO.setOvpstsCd(vITSOverpaymentUpdateEO.getOvpstsCd());
		itsOverpaymentVO.setOvporgCd(vITSOverpaymentUpdateEO.getOvporgCd());
		itsOverpaymentVO.setOvpdorgCd(vITSOverpaymentUpdateEO.getOvpdorgCd());
		itsOverpaymentVO.setAmount(vITSOverpaymentUpdateEO.getOvpAmount());
		itsOverpaymentVO.setInterest(vITSOverpaymentUpdateEO.getOvpInterest());
		itsOverpaymentVO.setCoc(vITSOverpaymentUpdateEO.getOvpCoc());
		itsOverpaymentVO.setPenalty(vITSOverpaymentUpdateEO.getOvpPenalty());
		return itsOverpaymentVO;
	}

	@Override
	public OverpaidWeeksVO getExistingProgramCodeDD(ContextDataVO contextData) {
		VITSOverpaidWeeksUpdateEO vITSOverpaidWeeksUpdateEO = null;
		OverpaidWeeksVO overpaidWeeksVO = null;
		try {
			logger.info("Starting to calling OverpaymentServiceImpl.getExistingProgramCodeDDList method");
			vITSOverpaidWeeksUpdateEO = commonEntityManagerRepository.getExistingProgramCodeDD(contextData.getVictimBadActorXrefId(),
					contextData.getOvpId(), contextData.getIsCancelled());
			if (vITSOverpaidWeeksUpdateEO != null && vITSOverpaidWeeksUpdateEO.getClmId() != null) {
				overpaidWeeksVO = new OverpaidWeeksVO();
				overpaidWeeksVO = convertUpdateEOToVO(vITSOverpaidWeeksUpdateEO, overpaidWeeksVO);
			}
		} catch (Exception e) {
			logger.error("Business Exception in OverpaymentServiceImpl getExistingProgramCodeDDList method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentServiceImpl.getExistingProgramCodeDDList() method." + e.getMessage());
		}
		
		return overpaidWeeksVO;
	}

	private OverpaidWeeksVO convertUpdateEOToVO(VITSOverpaidWeeksUpdateEO vITSOverpaidWeeksExisting, OverpaidWeeksVO overpaidWeeksVO) {
		if (vITSOverpaidWeeksExisting != null) {
			if (vITSOverpaidWeeksExisting.getClmId() != null)
				overpaidWeeksVO.setClaimId(vITSOverpaidWeeksExisting.getClmId());
			if (vITSOverpaidWeeksExisting.getCbwkBweDt() != null)
				overpaidWeeksVO.setCbwkBweDt(DateUtil.tsDateToStr(vITSOverpaidWeeksExisting.getCbwkBweDt()));
			if (vITSOverpaidWeeksExisting.getPaymentAmount() != null)
				overpaidWeeksVO.setPaymentAmount(vITSOverpaidWeeksExisting.getPaymentAmount());
			if (vITSOverpaidWeeksExisting.getPrgmCd() != null)
				overpaidWeeksVO.setPrgmCd(vITSOverpaidWeeksExisting.getPrgmCd());
			overpaidWeeksVO.setVictimBadActorXrefId(vITSOverpaidWeeksExisting.getVictimBadActorXrefId());
			overpaidWeeksVO.setOvpId(vITSOverpaidWeeksExisting.getOvpId());
			if (vITSOverpaidWeeksExisting.getOvpdtlsId() != null)
				overpaidWeeksVO.setOvpdtlsId(vITSOverpaidWeeksExisting.getOvpdtlsId());
			if (vITSOverpaidWeeksExisting.getRecoveryAmount() != null)
				overpaidWeeksVO.setRecoveryAmount(vITSOverpaidWeeksExisting.getRecoveryAmount() * -1);
			else
				overpaidWeeksVO.setRecoveryAmount(0.00);
		}
		return overpaidWeeksVO;
	}

	@Override
	public List<OverpaidWeeksVO> getExistingOverpaidWeeksList(ContextDataVO contextData) {
		List<VITSOverpaidWeeksUpdateEO> overpaidWeeksUpdateEOList = null;
		List<OverpaidWeeksVO> overpaidWeeksVOList = null;
		try {
			logger.info("Starting to calling OverpaymentServiceImpl.getExistingOverpaidWeeksList method");
			overpaidWeeksUpdateEOList = vITSOverpaidWeeksUpdateRepository.
					findByVictimBadActorXrefIdAndClmIdAndIsCancelledAndOvpIdOrderByCbwkBweDt(
							contextData.getVictimBadActorXrefId(),contextData.getInputClaimId(),contextData.getIsCancelled(),contextData.getOvpId());
			if (overpaidWeeksUpdateEOList != null && !overpaidWeeksUpdateEOList.isEmpty()) {
				overpaidWeeksVOList = overpaidWeeksUpdateEOList.stream()
						.filter(vopweekUpdate -> vopweekUpdate != null
								&& vopweekUpdate.getVictimBadActorXrefId() != null
								&& vopweekUpdate.getCbwkBweDt() != null && vopweekUpdate.getClmId() != null)
						.map(vopweekUpdate -> populateOverpaidWeeksVO(vopweekUpdate, new OverpaidWeeksVO()))
						.collect(Collectors.toList());
			}
		} catch (Exception e) {
			logger.error("Business Exception in OverpaymentServiceImpl getExistingOverpaidWeeksList method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentServiceImpl.getExistingOverpaidWeeksList() method." + e.getMessage());
		}
		
		return overpaidWeeksVOList;
	}

	private OverpaidWeeksVO populateOverpaidWeeksVO(VITSOverpaidWeeksUpdateEO vopweekUpdate, OverpaidWeeksVO overpaidWeeksVO) {
		List<VITSOvpDistributionEO> vITSOvpDistributionList = null;
		OverpaidWeeksVO ovpWeeks = new OverpaidWeeksVO();
		try {
			vITSOvpDistributionList =  vITSOvpDistributionRepository.findByVictimBadActorXrefIdAndCbwkBweDtAndClmId(
					vopweekUpdate.getVictimBadActorXrefId(),vopweekUpdate.getCbwkBweDt(),vopweekUpdate.getClmId());
			if (vITSOvpDistributionList != null && !vITSOvpDistributionList.isEmpty()) {
				ovpWeeks = setFundCodeData(overpaidWeeksVO, vITSOvpDistributionList);
			}
			overpaidWeeksVO = convertUpdateEOToVO(vopweekUpdate, ovpWeeks);
		} catch (Exception e) {
			logger.error("Business Exception in OverpaymentServiceImpl.populateOverpaidWeeksVO method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentServiceImpl.populateOverpaidWeeksVO() method." + e.getMessage());
		}
		return overpaidWeeksVO;
	}

	@Override
	public List<OverpaidWeeksVO> getOverpaidWeeksUpdatedList(ContextDataVO contextData) {
		List<VITSOverpaidWeeksUpdateEO> overpaidWeeksUpdateEOList = null;
		List<OverpaidWeeksVO> overpaidWeeksVOList = null;
		try {
			logger.info("Starting to calling OverpaymentServiceImpl.getExistingOverpaidWeeksList method");
			overpaidWeeksUpdateEOList = vITSOverpaidWeeksUpdateRepository.getOverpaidWeeksForUpdate(
					contextData.getVictimBadActorXrefId(),contextData.getInputClaimId(),contextData.getIsCancelled(),contextData.getOvpId());
			if (overpaidWeeksUpdateEOList != null && !overpaidWeeksUpdateEOList.isEmpty()) {
				overpaidWeeksVOList = overpaidWeeksUpdateEOList.stream()
						.filter(vopweekUpdate -> vopweekUpdate != null
								&& vopweekUpdate.getVictimBadActorXrefId() != null
								&& vopweekUpdate.getCbwkBweDt() != null && vopweekUpdate.getClmId() != null)
						.map(vopweekUpdate -> populateOverpaidWeeksVO(vopweekUpdate, new OverpaidWeeksVO()))
						.collect(Collectors.toList());
			}
		} catch (Exception e) {
			logger.error("Business Exception in OverpaymentServiceImpl getOverpaidWeeksUpdatedList method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentServiceImpl.getOverpaidWeeksUpdatedList() method." + e.getMessage());
		}
		
		return overpaidWeeksVOList;
	}

	@Override 
	public List<ITSOvpSummaryVO> getITSOverpaymentStatusHistoryList(Long selectedOverpaymentId) {
		List<VITSOvpStatusHistoryEO> vITSOvpStatusHistoryEOList = null;
		List<ITSOvpSummaryVO> itsOvpSummaryVOList = new ArrayList<>();
		String currentOverpaymentStatus = null;
		try {
			ItsOverpaymentEO itsOverpaymentEO = getItsOverpaymentEO(selectedOverpaymentId);
			if (itsOverpaymentEO != null) {
				currentOverpaymentStatus = itsOverpaymentEO.getOvpstsCd();
			}
			vITSOvpStatusHistoryEOList = vITSOvpStatusHistoryRepository.findByOvpId(selectedOverpaymentId);
			if (vITSOvpStatusHistoryEOList != null && !vITSOvpStatusHistoryEOList.isEmpty()) {
				for (VITSOvpStatusHistoryEO vITSOvpStatusHistoryEOObj : vITSOvpStatusHistoryEOList) {
					if (vITSOvpStatusHistoryEOObj != null) {
						ITSOvpSummaryVO itsOvpSummaryVOObj = new ITSOvpSummaryVO();
						BeanUtils.copyProperties(vITSOvpStatusHistoryEOObj, itsOvpSummaryVOObj);
						itsOvpSummaryVOObj.setDateCreated(DateUtil.tsDateToStr(vITSOvpStatusHistoryEOObj.getDateCreated()));
						itsOvpSummaryVOObj.setCurrentOverpaymentStatus(currentOverpaymentStatus);
						itsOvpSummaryVOList.add(itsOvpSummaryVOObj);
					}
				}
			}
		} catch (BusinessException e) {
			logger.error("Business Exception in OverpaymentServiceImpl.getITSOverpaymentStatusHistoryList method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentServiceImpl.getITSOverpaymentStatusHistoryList() method." + e.getMessage());
		}
		
		return itsOvpSummaryVOList;
	}

	@Override
	public List<ITSOvpSummaryVO> getOvpSearchBadActorData(SearchBadActorDataVO searchBadActorDataVO) {
		List<VITSOvpSummaryEO> vITSOvpSummaryEOList = null;
		List<ITSOvpSummaryVO> itsOvpSummaryVOList = new ArrayList<>();
		try {
			vITSOvpSummaryEOList = commonEntityManagerRepository.getOvpSearchBadActorData(searchBadActorDataVO);
			if (vITSOvpSummaryEOList != null && !vITSOvpSummaryEOList.isEmpty()) {
				itsOvpSummaryVOList = vITSOvpSummaryEOList.stream().map(vItsOvpSummaryEO -> {
					ITSOvpSummaryVO itsOvpSummaryVO = new ITSOvpSummaryVO();
					BeanUtils.copyProperties(vItsOvpSummaryEO, itsOvpSummaryVO);
					itsOvpSummaryVO.setDateCreated(DateUtil.convertDateToString(vItsOvpSummaryEO.getDateCreated()));
					itsOvpSummaryVO.setRecoveryAmount(vItsOvpSummaryEO.getRecoveryAmount() * -1);
					itsOvpSummaryVO.setCostCode(getCostCodeMapping(vItsOvpSummaryEO));
					return itsOvpSummaryVO;
				}).collect(Collectors.toList());
			}
		} catch (Exception e) {
			logger.error("Business Exception in commonEntityManagerRepository.getOvpSearchBadActorData method");
			throw new BusinessException(ERR_CODE,
					"Something went wrong in commonEntityManagerRepository.getOvpSearchBadActorData() method."
							+ e.getMessage());
		}
		return itsOvpSummaryVOList;
	}

	private String getCostCodeMapping(VITSOvpSummaryEO itsOvpSummaryEO) {
		String costCodeStr = "";
		try {
			List<String> costCodeList = commonEntityManagerRepository.getCostCodeMapping(itsOvpSummaryEO.getOvpId());
			if (costCodeList != null && !costCodeList.isEmpty()) {
				costCodeStr = costCodeList.stream().map(e -> e).collect(Collectors.joining(","));
			}
			return costCodeStr;
		} catch (BusinessException e) {
			logger.error("Business Exception in OverpaymentServiceImpl.getCostCode method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentServiceImpl.getCostCode() method." + e.getMessage());
		}
	}

	@Override
	public List<ITSOvpsearchDetailsVO> getITSOverpaymentDetailsList(Long selectedOverpaymentId) {
		List<VITSOverpaymentDetailsEO> vITSOverpaymentDetailsEOList = null;
		List<ITSOvpsearchDetailsVO> itsOvpSummaryVOList = new ArrayList<>();
		try {
			vITSOverpaymentDetailsEOList = vITSOverpaymentDetailsRepository.findByOvpIdAndOvptypCd(selectedOverpaymentId,"S");
			if (vITSOverpaymentDetailsEOList != null && !vITSOverpaymentDetailsEOList.isEmpty()) {
				itsOvpSummaryVOList = vITSOverpaymentDetailsEOList.stream().map(vITSOverpaymentDetailsEO -> {
					ITSOvpsearchDetailsVO itsOvpsearchDetailsVO = new ITSOvpsearchDetailsVO();
					BeanUtils.copyProperties(vITSOverpaymentDetailsEO, itsOvpsearchDetailsVO);
					itsOvpsearchDetailsVO.setCbwkBweDt(DateUtil.convertDateToString(vITSOverpaymentDetailsEO.getCbwkBweDt()));
					itsOvpsearchDetailsVO.setRecoveryAmt(vITSOverpaymentDetailsEO.getRecoveryAmt() * -1);
					return itsOvpsearchDetailsVO;
				}).collect(Collectors.toList());
			}
		} catch (BusinessException e) {
			logger.error("Business Exception in OverpaymentServiceImpl.getITSOverpaymentDetailsList method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentServiceImpl.getITSOverpaymentDetailsList() method." + e.getMessage());
		}
		
		return itsOvpSummaryVOList;
	}
	
	private ItsOverpaymentDetailsEO getItsOverpaymentDetailsEO(Long OvpdtlsId) {
		ItsOverpaymentDetailsEO itsOverpaymentDetailsEO = null;
		try {
			if (OvpdtlsId != null) {
				Optional<ItsOverpaymentDetailsEO> itsOverpaymentDetailsEOOpt = overpaymentDetailsRepository.findById(OvpdtlsId);
				if (itsOverpaymentDetailsEOOpt.isPresent()) {
					itsOverpaymentDetailsEO = itsOverpaymentDetailsEOOpt.get();
				}
			}
		} catch (Exception e) {
			logger.error("Business Exception in OverpaymentServiceImpl.getItsOverpaymentDetailsEO method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentServiceImpl.getItsOverpaymentDetailsEO() method." + e.getMessage());
		}
		
		return itsOverpaymentDetailsEO;
	}
	
	private ItsRecoveryDetailsEO getItsRecoveryDetailsEO(Long recoveryDtlsId) {
		ItsRecoveryDetailsEO itsRecoveryDetailsEO = null;
		try {
			if (recoveryDtlsId != null) {
				Optional<ItsRecoveryDetailsEO> itsRecoveryDtlsEOOpt = itsRecoveryDetailsRepository.findById(recoveryDtlsId);
				if (itsRecoveryDtlsEOOpt.isPresent()) {
					itsRecoveryDetailsEO = itsRecoveryDtlsEOOpt.get();
				}
			}
		} catch (Exception e) {
			logger.error("Business Exception in OverpaymentServiceImpl.getItsRecoveryDetailsEO method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentServiceImpl.getItsRecoveryDetailsEO() method." + e.getMessage());
		}
		
		return itsRecoveryDetailsEO;
	}
}
