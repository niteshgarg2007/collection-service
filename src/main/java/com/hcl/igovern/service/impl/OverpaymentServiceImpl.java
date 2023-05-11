package com.hcl.igovern.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;

import com.hcl.igovern.entity.ItsOverpaymentDetailsEO;
import com.hcl.igovern.entity.ItsOverpaymentDistributionsEO;
import com.hcl.igovern.entity.ItsOverpaymentEO;
import com.hcl.igovern.entity.ItsOverpaymentTransactionsEO;
import com.hcl.igovern.entity.VITSOverpaidWeeksEO;
import com.hcl.igovern.entity.VITSOverpaidWeeksUpdateEO;
import com.hcl.igovern.entity.VITSOverpaymentUpdateEO;
import com.hcl.igovern.entity.VITSOvpDistributionEO;
import com.hcl.igovern.entity.VITSOvpDistributionExistingEO;
import com.hcl.igovern.entity.VITSOvpStatusHistoryEO;
import com.hcl.igovern.entity.VITSOvpSummaryEO;
import com.hcl.igovern.exception.BusinessException;
import com.hcl.igovern.repository.CommonEntityManagerRepository;
import com.hcl.igovern.repository.OverpaymentRepository;
import com.hcl.igovern.repository.VITSOverpaidWeeksRepository;
import com.hcl.igovern.repository.VITSOverpaidWeeksUpdateRepository;
import com.hcl.igovern.repository.VITSOverpaymentUpdateRepository;
import com.hcl.igovern.repository.VITSOvpDistributionExistingRepository;
import com.hcl.igovern.repository.VITSOvpDistributionRepository;
import com.hcl.igovern.repository.VITSOvpStatusHistoryRepository;
import com.hcl.igovern.repository.VITSOvpSummaryRepository;
import com.hcl.igovern.service.OverpaymentService;
import com.hcl.igovern.util.DateUtil;
import com.hcl.igovern.util.UIDateRoutines;
import com.hcl.igovern.vo.ContextDataVO;
import com.hcl.igovern.vo.ITSOvpSummaryVO;
import com.hcl.igovern.vo.ItsOverpaymentVO;
import com.hcl.igovern.vo.OverpaidWeeksVO;


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
	private VITSOvpDistributionExistingRepository vITSOvpDistributionExistingRepository;
	
	@Autowired
	private VITSOvpStatusHistoryRepository vITSOvpStatusHistoryRepository;
	
	public static final String ERR_CODE = "ERR_CODE";

	@Override
	public List<OverpaidWeeksVO> getProgramCodeDDList(Long victimBadActorXrefId) {
		List<VITSOverpaidWeeksEO> programCodeEOList = null;
		List<OverpaidWeeksVO> programCodeVOList = null;
		OverpaidWeeksVO overpaidWeeksVO = null;
		try {
			logger.info("Starting to calling OverpaymentServiceImpl method");
			programCodeEOList = commonEntityManagerRepository.getProgramCodeDDList(victimBadActorXrefId);
			if (programCodeEOList != null && !programCodeEOList.isEmpty()) {
				programCodeVOList = new ArrayList<>();
				for (VITSOverpaidWeeksEO vITSOverpaidWeeks : programCodeEOList) {
					overpaidWeeksVO = new OverpaidWeeksVO();
					overpaidWeeksVO = convertEOToVO(vITSOverpaidWeeks, overpaidWeeksVO);
					programCodeVOList.add(overpaidWeeksVO);
				}
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
		for (int i = 0; i < vovpDdstList.size(); i++) {
			if (vovpDdstList.get(i) != null && vovpDdstList.get(i).getPrgfndAcctNo() != null) {
				fundCode = fundCode.concat(vovpDdstList.get(i).getPrgfndAcctNo());
				if (i < vovpDdstList.size() - 1) {
					fundCode = fundCode.concat(",");
				}
			}
		}
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
		}
		return overpaidWeeksVO;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public ItsOverpaymentVO addOverpaymentAndDetails(ItsOverpaymentVO itsOverpaymentVO) {
		if (itsOverpaymentVO.getItsOverpaymentDtls().isEmpty())
			throw new BusinessException(ERR_CODE, "Please select at-least one Overpaid Weeks");
		if ("C".equalsIgnoreCase(itsOverpaymentVO.getOvpdisCd()) || "CAN".equalsIgnoreCase(itsOverpaymentVO.getOvpstsCd())) {
			throw new BusinessException(ERR_CODE, "Cannot create a cancelled overpayment.");
		}
		try {
			ItsOverpaymentEO itsOverpaymentEO = createOverpaymentDetailsData(itsOverpaymentVO);
			// temporary added below condition to prevent over payment details data to update in the table, will remove
			if (itsOverpaymentVO.getOvpId() == null) {
				if (!itsOverpaymentEO.getItsOverpaymentDtls().isEmpty()) {
					itsOverpaymentEO = createOvpDstAndTransData(itsOverpaymentEO);
				}
			}
			overpaymentRepository.save(itsOverpaymentEO);
			if (itsOverpaymentVO.getOvpId() != null)
				itsOverpaymentVO.setStatusMessage("Overpayment has been successfully updated.");
			else
				itsOverpaymentVO.setStatusMessage("Overpayment has been successfully added.");
		} catch (Exception e) {
			logger.error("Business Exception in OverpaymentServiceImpl addOverpaymentAndDetails method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentServiceImpl.addOverpaymentAndDetails() method." + e.getMessage());
		}
		
		return itsOverpaymentVO;
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
			// temporary added below condition to prevent over payment details data to update in the table, will remove
			if (itsOverpaymentVO.getOvpId() == null) {
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
				if (itsOverpaymentVO.getPenalty() != null && itsOverpaymentVO.getPenalty() > 0) {
					ItsOverpaymentDetailsEO itsOvpDetails = new ItsOverpaymentDetailsEO();
					itsOvpDetails.setOvptypCd("P");
					itsOvpDetails.setOvpAmt(itsOverpaymentVO.getPenalty());
					setClaimIdOrAddCompPrgDtlId(itsOverpaymentVO, itsOvpDetails);
					itsOvpDetails.setItsOverpayment(itsOverpaymentTemp);
					itsOverpaymentDetailsList.add(itsOvpDetails);
				}
				if (itsOverpaymentVO.getInterest() != null && itsOverpaymentVO.getInterest() > 0) {
					ItsOverpaymentDetailsEO itsOvpDetails = new ItsOverpaymentDetailsEO();
					itsOvpDetails.setOvptypCd("I");
					itsOvpDetails.setOvpAmt(itsOverpaymentVO.getInterest());
					setClaimIdOrAddCompPrgDtlId(itsOverpaymentVO, itsOvpDetails);
					itsOvpDetails.setItsOverpayment(itsOverpaymentTemp);
					itsOverpaymentDetailsList.add(itsOvpDetails);
				}
				if (itsOverpaymentVO.getCoc() != null && itsOverpaymentVO.getCoc() > 0) {
					ItsOverpaymentDetailsEO itsOvpDetails = new ItsOverpaymentDetailsEO();
					itsOvpDetails.setOvptypCd("C");
					itsOvpDetails.setOvpAmt(itsOverpaymentVO.getCoc());
					setClaimIdOrAddCompPrgDtlId(itsOverpaymentVO, itsOvpDetails);
					itsOvpDetails.setItsOverpayment(itsOverpaymentTemp);
					itsOverpaymentDetailsList.add(itsOvpDetails);
				}
				
				itsOverpaymentTemp.setItsOverpaymentDtls(itsOverpaymentDetailsList);
			}
		
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
		itsOverpaymentVO.setDateModified(UIDateRoutines.getSysdate()!=null?UIDateRoutines.getDateString(UIDateRoutines.getSysdate(),null):null);
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
			vITSOverpaidWeeksUpdateEO = commonEntityManagerRepository.getExistingProgramCodeDD(contextData);
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
			if (vITSOverpaidWeeksExisting.getVictimBadActorXrefId() != null)
				overpaidWeeksVO.setVictimBadActorXrefId(vITSOverpaidWeeksExisting.getVictimBadActorXrefId());
			if (vITSOverpaidWeeksExisting.getOvpId() != null)
				overpaidWeeksVO.setOvpId(vITSOverpaidWeeksExisting.getOvpId());
		}
		return overpaidWeeksVO;
	}

	@Override
	public List<OverpaidWeeksVO> getExistingOverpaidWeeksList(ContextDataVO contextData) {
		List<VITSOverpaidWeeksUpdateEO> overpaidWeeksUpdateEOList = null;
		List<OverpaidWeeksVO> overpaidWeeksVOList = null;
		OverpaidWeeksVO overpaidWeeksVO = null;
		try {
			logger.info("Starting to calling OverpaymentServiceImpl.getExistingOverpaidWeeksList method");
			overpaidWeeksUpdateEOList = vITSOverpaidWeeksUpdateRepository.
					findByVictimBadActorXrefIdAndClmIdAndOvpIdOrderByCbwkBweDt(
							contextData.getVictimBadActorXrefId(),contextData.getInputClaimId(),contextData.getOvpId());
			if (overpaidWeeksUpdateEOList != null && !overpaidWeeksUpdateEOList.isEmpty()) {
				overpaidWeeksVOList = new ArrayList<>();
				for (VITSOverpaidWeeksUpdateEO vopweekUpdate : overpaidWeeksUpdateEOList) {
					overpaidWeeksVO = new OverpaidWeeksVO();
					if (vopweekUpdate != null && vopweekUpdate.getVictimBadActorXrefId() != null && vopweekUpdate.getCbwkBweDt() != null && 
							vopweekUpdate.getClmId() != null) {
						overpaidWeeksVO = populateOverpaidWeeksVO(vopweekUpdate, overpaidWeeksVO);
					}
					overpaidWeeksVOList.add(overpaidWeeksVO);
				}
			}
		} catch (Exception e) {
			logger.error("Business Exception in OverpaymentServiceImpl getExistingOverpaidWeeksList method");
			throw new BusinessException(ERR_CODE, "Something went wrong in OverpaymentServiceImpl.getExistingOverpaidWeeksList() method." + e.getMessage());
		}
		
		return overpaidWeeksVOList;
	}

	private OverpaidWeeksVO populateOverpaidWeeksVO(VITSOverpaidWeeksUpdateEO vopweekUpdate, OverpaidWeeksVO overpaidWeeksVO) {
		List<VITSOvpDistributionEO> vITSOvpDistributionList = null;
		List<VITSOvpDistributionExistingEO> vITSOvpDistributionExistingList = null;
		OverpaidWeeksVO ovpWeeks = new OverpaidWeeksVO();
		if (vopweekUpdate.getOvpId() == null) {
			vITSOvpDistributionList =  vITSOvpDistributionRepository.findByVictimBadActorXrefIdAndCbwkBweDtAndClmId(
					vopweekUpdate.getVictimBadActorXrefId(),vopweekUpdate.getCbwkBweDt(),vopweekUpdate.getClmId());
			if (vITSOvpDistributionList != null && !vITSOvpDistributionList.isEmpty()) {
				ovpWeeks = setFundCodeData(overpaidWeeksVO, vITSOvpDistributionList);
			}
		} else {
			vITSOvpDistributionExistingList =  vITSOvpDistributionExistingRepository.findByOvpIdAndCbwkBweDtAndClmId(
					vopweekUpdate.getOvpId(),vopweekUpdate.getCbwkBweDt(),vopweekUpdate.getClmId());
			if (vITSOvpDistributionExistingList != null && !vITSOvpDistributionExistingList.isEmpty()) {
				ovpWeeks = setExistingFundCodeData(overpaidWeeksVO, vITSOvpDistributionExistingList);
			}
		}
		overpaidWeeksVO = convertUpdateEOToVO(vopweekUpdate, ovpWeeks);
		return overpaidWeeksVO;
	}

	private OverpaidWeeksVO setExistingFundCodeData(OverpaidWeeksVO overpaidWeeksVO, List<VITSOvpDistributionExistingEO> itsOvpDstsEOList) {
		String fundCode = "";
		for (int i = 0; i < itsOvpDstsEOList.size(); i++) {
			if (itsOvpDstsEOList.get(i) != null && itsOvpDstsEOList.get(i).getPrgfndAcctNo() != null) {
				fundCode = fundCode.concat(itsOvpDstsEOList.get(i).getPrgfndAcctNo());
				if (i < itsOvpDstsEOList.size() - 1) {
					fundCode = fundCode.concat(",");
				}
			}
		}
		overpaidWeeksVO.setFundCode(fundCode);
		return overpaidWeeksVO;
	}

	@Override
	public List<OverpaidWeeksVO> getOverpaidWeeksUpdatedList(ContextDataVO contextData) {
		List<VITSOverpaidWeeksUpdateEO> overpaidWeeksUpdateEOList = null;
		List<OverpaidWeeksVO> overpaidWeeksVOList = null;
		OverpaidWeeksVO overpaidWeeksVO = null;
		try {
			logger.info("Starting to calling OverpaymentServiceImpl.getExistingOverpaidWeeksList method");
			overpaidWeeksUpdateEOList = vITSOverpaidWeeksUpdateRepository.
					findByVictimBadActorXrefIdAndClmIdOrderByCbwkBweDt(
							contextData.getVictimBadActorXrefId(),contextData.getInputClaimId());
			if (overpaidWeeksUpdateEOList != null && !overpaidWeeksUpdateEOList.isEmpty()) {
				overpaidWeeksVOList = new ArrayList<>();
				for (VITSOverpaidWeeksUpdateEO vopweekUpdate : overpaidWeeksUpdateEOList) {
					overpaidWeeksVO = new OverpaidWeeksVO();
					if (vopweekUpdate != null && vopweekUpdate.getVictimBadActorXrefId() != null && vopweekUpdate.getCbwkBweDt() != null && 
							vopweekUpdate.getClmId() != null) {
						overpaidWeeksVO = populateOverpaidWeeksVO(vopweekUpdate, overpaidWeeksVO);
					}
					overpaidWeeksVOList.add(overpaidWeeksVO);
				}
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
		try {
			vITSOvpStatusHistoryEOList = vITSOvpStatusHistoryRepository.findByOvpId(selectedOverpaymentId);
			if (vITSOvpStatusHistoryEOList != null && !vITSOvpStatusHistoryEOList.isEmpty()) {
				for (VITSOvpStatusHistoryEO vITSOvpStatusHistoryEOObj : vITSOvpStatusHistoryEOList) {
					if (vITSOvpStatusHistoryEOObj != null) {
						ITSOvpSummaryVO itsOvpSummaryVOObj = new ITSOvpSummaryVO();
						BeanUtils.copyProperties(vITSOvpStatusHistoryEOObj, itsOvpSummaryVOObj);
						itsOvpSummaryVOObj.setDateCreated(DateUtil.tsDateToStr(vITSOvpStatusHistoryEOObj.getDateCreated()));
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
}
