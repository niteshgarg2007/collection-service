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

import com.hcl.igovern.entity.ItsProsecutionEO;
import com.hcl.igovern.entity.ItsProsecutionsOverpaymentXrefEO;
import com.hcl.igovern.entity.VITSOvpSummaryEO;
import com.hcl.igovern.entity.VITSProsOvpxrefEO;
import com.hcl.igovern.entity.VITSProsecutionHistoryEO;
import com.hcl.igovern.entity.VITSProsecutionListEO;
import com.hcl.igovern.entity.VITSProsecutionSummaryEO;
import com.hcl.igovern.exception.BusinessException;
import com.hcl.igovern.repository.CommonEntityManagerRepository;
import com.hcl.igovern.repository.ItsProsecutionRepository;
import com.hcl.igovern.repository.ItsProsecutionsOverpaymentXrefRepository;
import com.hcl.igovern.repository.VITSOvpSummaryRepository;
import com.hcl.igovern.repository.VITSProsOvpxrefRepository;
import com.hcl.igovern.repository.VITSProsecutionHistoryRepository;
import com.hcl.igovern.repository.VITSProsecutionSummaryRepository;
import com.hcl.igovern.service.ProsecutionService;
import com.hcl.igovern.util.DateUtil;
import com.hcl.igovern.vo.ContextDataVO;
import com.hcl.igovern.vo.ITSOvpSummaryVO;
import com.hcl.igovern.vo.ITSProsecutionHistoryVO;
import com.hcl.igovern.vo.ITSProsecutionListVO;
import com.hcl.igovern.vo.ITSProsecutionSummaryVO;
import com.hcl.igovern.vo.ItsProsecutionVO;
import com.hcl.igovern.vo.ItsProsecutionsOverpaymentXrefVO;
import com.hcl.igovern.vo.SearchBadActorDataVO;

@Service
public class ProsecutionServiceImpl implements ProsecutionService {
	
	Logger logger = LoggerFactory.getLogger(ProsecutionServiceImpl.class);
	
	@Autowired
	private ItsProsecutionRepository itsProsecutionRepository;
	
	@Autowired
	private VITSProsecutionSummaryRepository vITSProsecutionSummaryRepository;
	
	@Autowired
	private VITSProsecutionHistoryRepository vITSProsecutionHistoryRepository;
	
	@Autowired
	private VITSOvpSummaryRepository vITSOvpSummaryRepository;
	
	@Autowired
	private ItsProsecutionsOverpaymentXrefRepository itsProsecutionsOverpaymentXrefRepository;
	
	@Autowired
    private CommonEntityManagerRepository commonEntityManagerRepository;
	
	@Autowired
    private VITSProsOvpxrefRepository vITSProsOvpxrefRepository;
	
	public static final String ERR_CODE = "ERR_CODE";

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
			logger.error("Business Exception in ProsecutionServiceImpl.getOverpaymentDetailsList method");
			throw new BusinessException(ERR_CODE, "Something went wrong in ProsecutionServiceImpl.getOverpaymentDetailsList() method." + e.getMessage());
		}
		
		return itsOvpSummaryVOList;
	}
	
	private List<ITSOvpSummaryVO> getAssociatedVITSOvpSummaryList(Long victimBadActorXrefId) {
		List<VITSOvpSummaryEO> vITSProsUpdateEOList = null;
		List<ITSOvpSummaryVO> itsProsUpdateVOList = new ArrayList<>();
		try {
			vITSProsUpdateEOList = vITSOvpSummaryRepository.findByVictimBadActorXrefId(victimBadActorXrefId);
			if (vITSProsUpdateEOList != null && !vITSProsUpdateEOList.isEmpty()) {
				itsProsUpdateVOList = vITSProsUpdateEOList.stream().map(vITSProsUpdateEOObj -> {
					ITSOvpSummaryVO itsProsUpdateVOObj = new ITSOvpSummaryVO();
					BeanUtils.copyProperties(vITSProsUpdateEOObj, itsProsUpdateVOObj);
					itsProsUpdateVOObj.setDateCreated(DateUtil.tsDateToStr(vITSProsUpdateEOObj.getDateCreated()));
					itsProsUpdateVOObj.setProsOvpXrefId(null);
					itsProsUpdateVOObj.setRecoveryAmount(itsProsUpdateVOObj.getRecoveryAmount() * -1);
					return itsProsUpdateVOObj;
				}).collect(Collectors.toList());
			}
		} catch (BusinessException e) {
			logger.error("Business Exception in ProsecutionServiceImpl.getAssociatedVITSOvpSummaryList method");
			throw new BusinessException(ERR_CODE, "Something went wrong in ProsecutionServiceImpl.getAssociatedVITSOvpSummaryList() method." + e.getMessage());
		}
		
		return itsProsUpdateVOList;
	}
	
	private List<ITSOvpSummaryVO> getBadActorVITSOvpSummaryList(Long badActorId) {
		List<VITSOvpSummaryEO> vITSProsUpdateEOList = null;
		List<ITSOvpSummaryVO> itsProsUpdateVOList = new ArrayList<>();
		try {
			vITSProsUpdateEOList = vITSOvpSummaryRepository.findByBadActorId(badActorId);
			if (vITSProsUpdateEOList != null && !vITSProsUpdateEOList.isEmpty()) {
				itsProsUpdateVOList = vITSProsUpdateEOList.stream().map(vITSProsUpdateEOObj -> {
					ITSOvpSummaryVO itsProsUpdateVOObj = new ITSOvpSummaryVO();
					BeanUtils.copyProperties(vITSProsUpdateEOObj, itsProsUpdateVOObj);
					itsProsUpdateVOObj.setDateCreated(DateUtil.tsDateToStr(vITSProsUpdateEOObj.getDateCreated()));
					itsProsUpdateVOObj.setProsOvpXrefId(null);
					itsProsUpdateVOObj.setRecoveryAmount(itsProsUpdateVOObj.getRecoveryAmount() * -1);
					return itsProsUpdateVOObj;
				}).collect(Collectors.toList());
			}
		} catch (BusinessException e) {
			logger.error("Business Exception in ProsecutionServiceImpl.getBadActorVITSOvpSummaryList method");
			throw new BusinessException(ERR_CODE, "Something went wrong in ProsecutionServiceImpl.getBadActorVITSOvpSummaryList() method." + e.getMessage());
		}
		return itsProsUpdateVOList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public ItsProsecutionVO addProsecutionAndDetails(ItsProsecutionVO itsProsecutionVO) {
		try {
			if (itsProsecutionVO.getProsId() == null) {
				ItsProsecutionEO itsProsecutionEO = createProsecutionDetailsData(itsProsecutionVO);
				itsProsecutionRepository.save(itsProsecutionEO);
				itsProsecutionVO.setStatusMessage("Prosecution has been successfully added.");
			}
			
			if (itsProsecutionVO.getProsId() != null) {
				updateProsecutionDetailsData(itsProsecutionVO);
				itsProsecutionVO.setStatusMessage("Prosecution has been successfully updated.");
			}
		} catch (Exception e) {
			logger.error("Business Exception in ProsecutionServiceImpl.addProsecutionAndDetails method");
			throw new BusinessException(ERR_CODE, "Something went wrong in ProsecutionServiceImpl.addProsecutionAndDetails() method." + e.getMessage());
		}
		return itsProsecutionVO;
	}

	private ItsProsecutionEO createProsecutionDetailsData(ItsProsecutionVO itsProsecutionVO) {
		ItsProsecutionEO itsProsecutionEOTemp = new ItsProsecutionEO();
		List<ItsProsecutionsOverpaymentXrefEO> itsProsOvpXrefEOList = null;
		try {
			itsProsecutionEOTemp.setBadActorId(itsProsecutionVO.getBadActorId());
			itsProsecutionEOTemp.setClaimantLocality(itsProsecutionVO.getClaimantLocality());
			itsProsecutionEOTemp.setProsVenueId(itsProsecutionVO.getProsVenueId());
			itsProsecutionEOTemp.setCaseNo(itsProsecutionVO.getCaseNo());
			if (itsProsecutionVO.getWarrantReqDt() != null) {
				itsProsecutionEOTemp.setWarrantReqDt(DateUtil.strDateHyphenToTs(itsProsecutionVO.getWarrantReqDt()));
			}
			
			itsProsecutionEOTemp.setWarrantNo(itsProsecutionVO.getWarrantNo());
			if (itsProsecutionVO.getIniCourtDt() != null) {
				itsProsecutionEOTemp.setIniCourtDt(DateUtil.strDateHyphenToTs(itsProsecutionVO.getIniCourtDt()));
			}
			
			itsProsecutionEOTemp.setBpcProsDispCd(itsProsecutionVO.getBpcProsDispCd());
			if (itsProsecutionVO.getContinuedDt() != null) {
				itsProsecutionEOTemp.setContinuedDt(DateUtil.strDateHyphenToTs(itsProsecutionVO.getContinuedDt()));
			}
			if (itsProsecutionVO.getConvictionDt() != null) {
				itsProsecutionEOTemp.setConvictionDt(DateUtil.strDateHyphenToTs(itsProsecutionVO.getConvictionDt()));
			}
			if (itsProsecutionVO.getRestitutionOrderedInd() != null && itsProsecutionVO.getRestitutionOrderedInd().equalsIgnoreCase("Y"))
				itsProsecutionEOTemp.setRestitutionOrderedInd("Y");
			else
				itsProsecutionEOTemp.setRestitutionOrderedInd(null);
			itsProsecutionEOTemp.setResiAmt(itsProsecutionVO.getResiAmt());
			itsProsecutionEOTemp.setSentence(itsProsecutionVO.getSentence());
			itsProsecutionEOTemp.setComments(itsProsecutionVO.getComments());
			if (itsProsecutionVO.getAllOvpInd() != null && itsProsecutionVO.getAllOvpInd().equalsIgnoreCase("true"))
				itsProsecutionEOTemp.setAllOvpInd("Y");
			else
				itsProsecutionEOTemp.setAllOvpInd(null);
			itsProsOvpXrefEOList = populateItsProsOvpXrefList(itsProsecutionEOTemp,itsProsecutionVO);
			itsProsecutionEOTemp.setItsProsecutionsOverpaymentXrefs(itsProsOvpXrefEOList);
		} catch (Exception e) {
			logger.error("Business Exception in ProsecutionServiceImpl.createProsecutionDetailsData method");
			throw new BusinessException(ERR_CODE, "Something went wrong in ProsecutionServiceImpl.createProsecutionDetailsData() method." + e.getMessage());
		}
		
		return itsProsecutionEOTemp;
	}

	private List<ItsProsecutionsOverpaymentXrefEO> populateItsProsOvpXrefList(ItsProsecutionEO itsProsecutionEOTemp, ItsProsecutionVO itsProsecutionVO) {
		List<ItsProsecutionsOverpaymentXrefEO> itsProsOvpXrefEOList = new ArrayList<>();
		try {
			if (!itsProsecutionVO.getItsProsecutionsOverpaymentXrefs().isEmpty()) {
				for (ItsProsecutionsOverpaymentXrefVO itsProsOvpXrefVOTemp : itsProsecutionVO.getItsProsecutionsOverpaymentXrefs()) {
					ItsProsecutionsOverpaymentXrefEO prosOvpXrefEO = new ItsProsecutionsOverpaymentXrefEO();
					prosOvpXrefEO.setProsId(itsProsecutionEOTemp);
					prosOvpXrefEO.setOvpId(itsProsOvpXrefVOTemp.getOvpId());
					prosOvpXrefEO.setResiAmt(itsProsOvpXrefVOTemp.getResiAmt());
					prosOvpXrefEO.setEndDt(null);
					itsProsOvpXrefEOList.add(prosOvpXrefEO);
				}
			}
		} catch (Exception e) {
			logger.error("Business Exception in ProsecutionServiceImpl.populateItsProsOvpXrefList method");
			throw new BusinessException(ERR_CODE, "Something went wrong in ProsecutionServiceImpl.populateItsProsOvpXrefList() method." + e.getMessage());
		}
		
		return itsProsOvpXrefEOList;
	}

	private ItsProsecutionEO updateProsecutionDetailsData(ItsProsecutionVO itsProsecutionVO) {
		ItsProsecutionEO itsProsecutionEOTemp = null;
		try {
			itsProsecutionEOTemp = getItsProsecutionEO(itsProsecutionVO.getProsId());
			if (itsProsecutionEOTemp != null) {
				itsProsecutionEOTemp = populateUpdatedProsecutionData(itsProsecutionEOTemp,itsProsecutionVO);
				if (itsProsecutionVO.getItsProsecutionsOverpaymentXrefs() != null && !itsProsecutionVO.getItsProsecutionsOverpaymentXrefs().isEmpty()) {
					updateProsOvpXrefEOList(itsProsecutionEOTemp,itsProsecutionVO);
				} else {
					detachAllProsOvpXrefEOList(itsProsecutionEOTemp);
				}
			
			}
		} catch (Exception e) {
			logger.error("Business Exception in ProsecutionServiceImpl.updateProsecutionDetailsData method");
			throw new BusinessException(ERR_CODE, "Something went wrong in ProsecutionServiceImpl.updateProsecutionDetailsData() method." + e.getMessage());
		}
		return itsProsecutionEOTemp;
	}

	private void detachAllProsOvpXrefEOList(ItsProsecutionEO itsProsecutionEO) {
		try {
			for (ItsProsecutionsOverpaymentXrefEO prosOvpXrefEOExist : itsProsecutionEO.getItsProsecutionsOverpaymentXrefs()) {
				if (prosOvpXrefEOExist.getProsOvpXrefId() != null) {
					// update over payment detail data as set end date
					detachExistingProsOverpaymentXref(prosOvpXrefEOExist);
				}
			}
		} catch (Exception e) {
			logger.error("Business Exception in ProsecutionServiceImpl.detachAllProsOvpXrefEOList method");
			throw new BusinessException(ERR_CODE, "Something went wrong in ProsecutionServiceImpl.detachAllProsOvpXrefEOList() method." + e.getMessage());
		}
	}

	private void updateProsOvpXrefEOList(ItsProsecutionEO itsProsecutionEO, ItsProsecutionVO itsProsecutionVO) {
		try {
			for (ItsProsecutionsOverpaymentXrefVO prosOvpXrefVO : itsProsecutionVO.getItsProsecutionsOverpaymentXrefs()) {
				if (prosOvpXrefVO.getProsOvpXrefId() == null || prosOvpXrefVO.getProsOvpXrefId() == 0) {
					// insert prosecution and over payment data
					ItsProsecutionsOverpaymentXrefEO prosOvpXrefEO = new ItsProsecutionsOverpaymentXrefEO();
					prosOvpXrefEO.setProsId(itsProsecutionEO);
					prosOvpXrefEO.setOvpId(prosOvpXrefVO.getOvpId());
					prosOvpXrefEO.setResiAmt(prosOvpXrefVO.getResiAmt());
					itsProsecutionsOverpaymentXrefRepository.save(prosOvpXrefEO);
				} else {
					// update ItsProsecutionsOverpaymentXrefEO object
					ItsProsecutionsOverpaymentXrefEO prosOvpXrefEOExist = getItsProsecutionsOverpaymentXrefEO(prosOvpXrefVO.getProsOvpXrefId());
					if (prosOvpXrefEOExist != null) {
						prosOvpXrefEOExist.setResiAmt(prosOvpXrefVO.getResiAmt());
						itsProsecutionsOverpaymentXrefRepository.save(prosOvpXrefEOExist);
					}
				}
			}
			// detach over payment data
			updateExistingProsOverpaymentXref(itsProsecutionEO,itsProsecutionVO);
		} catch (Exception e) {
			logger.error("Business Exception in ProsecutionServiceImpl.updateProsOvpXrefEOList method");
			throw new BusinessException(ERR_CODE, "Something went wrong in ProsecutionServiceImpl.updateProsOvpXrefEOList() method." + e.getMessage());
		}
	}

	private void updateExistingProsOverpaymentXref(ItsProsecutionEO itsProsecutionEO, ItsProsecutionVO itsProsecutionVO) {
		try {
			for (ItsProsecutionsOverpaymentXrefEO prosOvpXrefEOExist : itsProsecutionEO.getItsProsecutionsOverpaymentXrefs()) {
				boolean isDetailsExist = false;
				if (prosOvpXrefEOExist.getProsOvpXrefId() != null) {
					for (ItsProsecutionsOverpaymentXrefVO prosOvpXrefVO : itsProsecutionVO.getItsProsecutionsOverpaymentXrefs()) {
						if (prosOvpXrefVO.getProsOvpXrefId() != null && prosOvpXrefEOExist.getProsOvpXrefId() != null
								&& prosOvpXrefEOExist.getProsOvpXrefId().equals(prosOvpXrefVO.getProsOvpXrefId())
								&& !isDetailsExist) {
							isDetailsExist = true;
							break;
						}
					}
					// update over payment detail data as set end date
					if (!isDetailsExist) {
						detachExistingProsOverpaymentXref(prosOvpXrefEOExist);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Business Exception in ProsecutionServiceImpl.updateExistingProsOverpaymentXref method");
			throw new BusinessException(ERR_CODE, "Something went wrong in ProsecutionServiceImpl.updateExistingProsOverpaymentXref() method." + e.getMessage());
		}
	}

	private void detachExistingProsOverpaymentXref(ItsProsecutionsOverpaymentXrefEO prosOvpXrefEO) {
		try {
			Double restAmountZero = 0.00;
			ItsProsecutionsOverpaymentXrefEO prosOvpXrefEOExist = getItsProsecutionsOverpaymentXrefEO(prosOvpXrefEO.getProsOvpXrefId());
			if (prosOvpXrefEOExist != null) {
				prosOvpXrefEOExist.setEndDt(DateUtil.parseDateTime(DateUtil.getCurrentDateString()));
				prosOvpXrefEOExist.setResiAmt(restAmountZero);
				itsProsecutionsOverpaymentXrefRepository.save(prosOvpXrefEOExist);
			}
		} catch (Exception e) {
			logger.error("Business Exception in ProsecutionServiceImpl.detachExistingProsOverpaymentXref method");
			throw new BusinessException(ERR_CODE, "Something went wrong in ProsecutionServiceImpl.detachExistingProsOverpaymentXref() method." + e.getMessage());
		}
	}

	private ItsProsecutionEO populateUpdatedProsecutionData(ItsProsecutionEO itsProsecutionEOTemp, ItsProsecutionVO itsProsecutionVO) {
		try {
			itsProsecutionEOTemp.setClaimantLocality(itsProsecutionVO.getClaimantLocality());
			itsProsecutionEOTemp.setProsVenueId(itsProsecutionVO.getProsVenueId());
			itsProsecutionEOTemp.setCaseNo(itsProsecutionVO.getCaseNo());
			if (itsProsecutionVO.getWarrantReqDt() != null) {
				itsProsecutionEOTemp.setWarrantReqDt(DateUtil.strDateHyphenToTs(itsProsecutionVO.getWarrantReqDt()));
			}
			
			itsProsecutionEOTemp.setWarrantNo(itsProsecutionVO.getWarrantNo());
			if (itsProsecutionVO.getIniCourtDt() != null) {
				itsProsecutionEOTemp.setIniCourtDt(DateUtil.strDateHyphenToTs(itsProsecutionVO.getIniCourtDt()));
			}
			
			itsProsecutionEOTemp.setBpcProsDispCd(itsProsecutionVO.getBpcProsDispCd());
			if (itsProsecutionVO.getContinuedDt() != null) {
				itsProsecutionEOTemp.setContinuedDt(DateUtil.strDateHyphenToTs(itsProsecutionVO.getContinuedDt()));
			}
			if (itsProsecutionVO.getConvictionDt() != null) {
				itsProsecutionEOTemp.setConvictionDt(DateUtil.strDateHyphenToTs(itsProsecutionVO.getConvictionDt()));
			}
			if (itsProsecutionVO.getRestitutionOrderedInd() != null && (itsProsecutionVO.getRestitutionOrderedInd().equalsIgnoreCase("Y") || 
					itsProsecutionVO.getRestitutionOrderedInd().equalsIgnoreCase("true")))
				itsProsecutionEOTemp.setRestitutionOrderedInd("Y");
			else
				itsProsecutionEOTemp.setRestitutionOrderedInd(null);
			itsProsecutionEOTemp.setResiAmt(itsProsecutionVO.getResiAmt());
			itsProsecutionEOTemp.setSentence(itsProsecutionVO.getSentence());
			itsProsecutionEOTemp.setComments(itsProsecutionVO.getComments());
			if (itsProsecutionVO.getAllOvpInd() != null && (itsProsecutionVO.getAllOvpInd().equalsIgnoreCase("true") || 
					itsProsecutionVO.getAllOvpInd().equalsIgnoreCase("Y")))
				itsProsecutionEOTemp.setAllOvpInd("Y");
			else
				itsProsecutionEOTemp.setAllOvpInd(null);
			return itsProsecutionEOTemp;
		} catch (Exception e) {
			logger.error("Business Exception in ProsecutionServiceImpl.populateUpdatedProsecutionData method");
			throw new BusinessException(ERR_CODE, "Something went wrong in ProsecutionServiceImpl.populateUpdatedProsecutionData() method." + e.getMessage());
		}
	}

	@Override
	public List<ITSProsecutionSummaryVO> getITSProsecutionSummaryList(Long victimBadActorXrefId) {
		List<VITSProsecutionSummaryEO> vITSProsecutionSummaryEOList = null;
		List<ITSProsecutionSummaryVO> prosecutionSummaryVOList = new ArrayList<>();
		try {
			vITSProsecutionSummaryEOList = vITSProsecutionSummaryRepository.findByVictimBadActorXrefId(victimBadActorXrefId);
			if (vITSProsecutionSummaryEOList != null && !vITSProsecutionSummaryEOList.isEmpty()) {
				prosecutionSummaryVOList = vITSProsecutionSummaryEOList.stream().map(vITSProsecutionSummaryEOObj -> {
					ITSProsecutionSummaryVO itsProsecutionSummaryVOObj = new ITSProsecutionSummaryVO();
					BeanUtils.copyProperties(vITSProsecutionSummaryEOObj, itsProsecutionSummaryVOObj);
					itsProsecutionSummaryVOObj.setWarrantReqDt(DateUtil.tsDateToStr(vITSProsecutionSummaryEOObj.getWarrantReqDt()));
					itsProsecutionSummaryVOObj.setIniCourtDt(DateUtil.tsDateToStr(vITSProsecutionSummaryEOObj.getIniCourtDt()));
					itsProsecutionSummaryVOObj.setContinuedDt(DateUtil.tsDateToStr(vITSProsecutionSummaryEOObj.getContinuedDt()));
					itsProsecutionSummaryVOObj.setConvictionDt(DateUtil.tsDateToStr(vITSProsecutionSummaryEOObj.getConvictionDt()));
					return itsProsecutionSummaryVOObj;
				}).collect(Collectors.toList());
			}
		} catch (BusinessException e) {
			logger.error("Business Exception in ProsecutionServiceImpl getITSProsecutionSummaryList method");
			throw new BusinessException(ERR_CODE, "Something went wrong in ProsecutionServiceImpl.getITSProsecutionSummaryList() method." + e.getMessage());
		}
		
		return prosecutionSummaryVOList;
	}

	@Override
	public List<ITSProsecutionHistoryVO> getITSProsHistoryList(Long selectedProsId) {
		List<VITSProsecutionHistoryEO> vITSProseHistoryEOList = null;
		List<ITSProsecutionHistoryVO> prosHistoryVOList = new ArrayList<>();
		try {
			vITSProseHistoryEOList = vITSProsecutionHistoryRepository.findByProsId(selectedProsId);
			if (vITSProseHistoryEOList != null && !vITSProseHistoryEOList.isEmpty()) {
				prosHistoryVOList = vITSProseHistoryEOList.stream().map(vITSProsHistoryEOObj -> {
					ITSProsecutionHistoryVO itsProsHistoryVOObj = new ITSProsecutionHistoryVO();
					BeanUtils.copyProperties(vITSProsHistoryEOObj, itsProsHistoryVOObj);
					itsProsHistoryVOObj.setDateCreated(DateUtil.tsDateToStr(vITSProsHistoryEOObj.getDateCreated()));
					itsProsHistoryVOObj.setDateModified(DateUtil.tsDateToStr(vITSProsHistoryEOObj.getDateModified()));
					return itsProsHistoryVOObj;
				}).collect(Collectors.toList());
			}
		} catch (BusinessException e) {
			logger.error("Business Exception in ProsecutionServiceImpl.getITSProsHistoryList method");
			throw new BusinessException(ERR_CODE, "Something went wrong in ProsecutionServiceImpl.getITSProsHistoryList() method." + e.getMessage());
		}
		
		return prosHistoryVOList;
	}

	@Override
	public ItsProsecutionVO getITSProsDataByProsId(Long selectedProsId) {
		ItsProsecutionVO itsProsecutionVO  = new ItsProsecutionVO();
		ItsProsecutionEO itsProsecutionEO = null;
		try {
			itsProsecutionEO = getItsProsecutionEO(selectedProsId);
			if (itsProsecutionEO != null) {
				BeanUtils.copyProperties(itsProsecutionEO, itsProsecutionVO);
				itsProsecutionVO.setWarrantReqDt(DateUtil.convertDateToString(itsProsecutionEO.getWarrantReqDt()));
				itsProsecutionVO.setIniCourtDt(DateUtil.convertDateToString(itsProsecutionEO.getIniCourtDt()));
				itsProsecutionVO.setContinuedDt(DateUtil.convertDateToString(itsProsecutionEO.getContinuedDt()));
				itsProsecutionVO.setConvictionDt(DateUtil.convertDateToString(itsProsecutionEO.getConvictionDt()));
			}
		} catch (IllegalArgumentException ia) {
			logger.error("Business Exception in ProsecutionServiceImpl.getITSProsDataByProsId method");
			throw new BusinessException("ERR_CODE1", "Given Prosecution Id is null, Please provide valid Prosecution Id." +ia.getMessage());
		} catch (java.util.NoSuchElementException e) {
			logger.error("Business Exception in ProsecutionServiceImpl.getITSProsDataByProsId method");
			throw new BusinessException("ERR_CODE2", "Given Prosecution Id does not exist in Database." +e.getMessage());
		}
		
		return itsProsecutionVO;
	}

	@Override
	public List<ItsProsecutionsOverpaymentXrefVO> getExistingProsecutionOvpXref(ContextDataVO contextData) {
		List<ItsProsecutionsOverpaymentXrefVO> itsProsOvpXrefVOList = null;
		ItsProsecutionEO itsProsecutionEO = null;
		try {
			logger.info("Starting to calling ProsecutionServiceImpl.getExistingProsecutionOvpXref method");
			itsProsecutionEO = getItsProsecutionEO(contextData.getProsId());
			if (itsProsecutionEO != null) {
				itsProsOvpXrefVOList = populateExistingProsOvpXrefList(itsProsecutionEO);
			}
		} catch (Exception e) {
			logger.error("Business Exception in ProsecutionServiceImpl.getExistingProsecutionOvpXref method");
			throw new BusinessException(ERR_CODE, "Something went wrong in ProsecutionServiceImpl.getExistingProsecutionOvpXref() method." + e.getMessage());
		}
		
		return itsProsOvpXrefVOList;
	
	}

	private List<ItsProsecutionsOverpaymentXrefVO> populateExistingProsOvpXrefList(ItsProsecutionEO itsProsecutionEO) {
		List<ItsProsecutionsOverpaymentXrefVO> itsProsOvpXrefVOList = new ArrayList<>();
		try {
			if (itsProsecutionEO.getItsProsecutionsOverpaymentXrefs() != null && !itsProsecutionEO.getItsProsecutionsOverpaymentXrefs().isEmpty()) {
				for (ItsProsecutionsOverpaymentXrefEO ovpXrefEOObj : itsProsecutionEO.getItsProsecutionsOverpaymentXrefs()) {
					if (ovpXrefEOObj.getEndDt() == null) {
						ItsProsecutionsOverpaymentXrefVO prosOvpXrefVO = new ItsProsecutionsOverpaymentXrefVO();
						Optional<VITSOvpSummaryEO> vITSOvpSummaryEOOpt = vITSOvpSummaryRepository.findById(ovpXrefEOObj.getOvpId());
						if (vITSOvpSummaryEOOpt.isPresent()) {
							VITSOvpSummaryEO vITSOvpSummaryEO = vITSOvpSummaryEOOpt.get();
							prosOvpXrefVO.setOvpId(vITSOvpSummaryEO.getOvpId());
							prosOvpXrefVO.setDateCreated(DateUtil.convertDateToString(vITSOvpSummaryEO.getDateCreated()));
							prosOvpXrefVO.setOvpTotal(vITSOvpSummaryEO.getOvpTotal());
							prosOvpXrefVO.setRecoveryAmount(vITSOvpSummaryEO.getRecoveryAmount() * -1);
							prosOvpXrefVO.setOvpPenalty(vITSOvpSummaryEO.getOvpPenalty());
							prosOvpXrefVO.setOvpInterest(vITSOvpSummaryEO.getOvpInterest());
							prosOvpXrefVO.setOvpCoc(vITSOvpSummaryEO.getOvpCoc());
							prosOvpXrefVO.setOvpstsCd(vITSOvpSummaryEO.getOvpstsCd());
							prosOvpXrefVO.setOvpdisCd(vITSOvpSummaryEO.getOvpdisCd());
							prosOvpXrefVO.setBadActorSsn(vITSOvpSummaryEO.getBadActorSsn());
							prosOvpXrefVO.setPrtyTaxId(vITSOvpSummaryEO.getPrtyTaxId());
						}
						prosOvpXrefVO.setRestiAmount(ovpXrefEOObj.getResiAmt());
						prosOvpXrefVO.setResiAmt(ovpXrefEOObj.getResiAmt());
						prosOvpXrefVO.setProsOvpXrefId(ovpXrefEOObj.getProsOvpXrefId());
						itsProsOvpXrefVOList.add(prosOvpXrefVO);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Business Exception in ProsecutionServiceImpl.populateExistingProsOvpXrefList method");
			throw new BusinessException(ERR_CODE, "Something went wrong in ProsecutionServiceImpl.populateExistingProsOvpXrefList() method." + e.getMessage());
		}
		
		return itsProsOvpXrefVOList;
	}

	@Override
	public List<ItsProsecutionsOverpaymentXrefVO> getOverpaymentUpdateDetailsListByParams(ContextDataVO contextDataVO) {
		List<ItsProsecutionsOverpaymentXrefVO> prosOvpXrefVOList = null;
		ItsProsecutionEO itsProsecutionEO = null;
		try {
			logger.info("Starting to calling RecoveryServiceImpl.getOverpaymentUpdateDetailsListByParams method");
			if (contextDataVO.getAllOvpUpdateInd() == null) {
				itsProsecutionEO = getItsProsecutionEO(contextDataVO.getProsId());
				if (itsProsecutionEO != null) {
					if (itsProsecutionEO.getAllOvpInd() != null && itsProsecutionEO.getAllOvpInd().equalsIgnoreCase("Y")) {
						prosOvpXrefVOList = getProsOvpXrefListByProsIdAndBadActorId(contextDataVO);
					} else {
						prosOvpXrefVOList = getProsOvpXrefByProsIdAndAssociation(contextDataVO);
					}
				}
			} else {
				if (contextDataVO.getAllOvpUpdateInd() != null && contextDataVO.getAllOvpUpdateInd().equalsIgnoreCase("Y")) {
					prosOvpXrefVOList = getProsOvpXrefListByProsIdAndBadActorId(contextDataVO);
				} else {
					prosOvpXrefVOList = getProsOvpXrefByProsIdAndAssociation(contextDataVO);
				}
			}
			
		} catch (Exception e) {
			logger.error("Business Exception in ProsecutionServiceImpl.getOverpaymentUpdateDetailsListByParams method");
			throw new BusinessException(ERR_CODE, "Something went wrong in ProsecutionServiceImpl.getOverpaymentUpdateDetailsListByParams() method." + e.getMessage());
		}
		
		return prosOvpXrefVOList;
	
	}

	private List<ItsProsecutionsOverpaymentXrefVO> getProsOvpXrefListByProsIdAndBadActorId(ContextDataVO contextDataVO) {
		List<ItsProsecutionsOverpaymentXrefVO> prosOvpXrefVOList = null;
		try {
			if (contextDataVO.getProsId() != null && contextDataVO.getBadActorId() != null) {
				prosOvpXrefVOList = commonEntityManagerRepository.getVITSProsecutionUpdateByBadActorList(contextDataVO.getBadActorId(),contextDataVO.getProsId());
			}
		} catch (Exception e) {
			logger.error("Business Exception in ProsecutionServiceImpl.getProsOvpXrefListByProsIdAndBadActorId method");
			throw new BusinessException(ERR_CODE, "Something went wrong in ProsecutionServiceImpl.getProsOvpXrefListByProsIdAndBadActorId() method." + e.getMessage());
		}
		
		return prosOvpXrefVOList;
	}

	private List<ItsProsecutionsOverpaymentXrefVO> getProsOvpXrefByProsIdAndAssociation(ContextDataVO contextDataVO) {
		List<ItsProsecutionsOverpaymentXrefVO> vITSProsUpdateVOList = null;
		try {
			if (contextDataVO.getProsId() != null && contextDataVO.getVictimBadActorXrefId() != null) {
				vITSProsUpdateVOList = commonEntityManagerRepository.getVITSProsecutionUpdateByXrefList(contextDataVO.getVictimBadActorXrefId(),contextDataVO.getProsId());
			}
		} catch (Exception e) {
			logger.error("Business Exception in ProsecutionServiceImpl.getProsOvpXrefByProsIdAndAssociation method");
			throw new BusinessException(ERR_CODE, "Something went wrong in ProsecutionServiceImpl.getProsOvpXrefByProsIdAndAssociation() method." + e.getMessage());
		}
		
		return vITSProsUpdateVOList;
	}
	
	private ItsProsecutionEO getItsProsecutionEO(Long selectedProsId) {
		ItsProsecutionEO itsProsecutionEO = null;
		try {
			if (selectedProsId != null) {
				Optional<ItsProsecutionEO> itsProsecutionEOOpt = itsProsecutionRepository.findById(selectedProsId);
				if (itsProsecutionEOOpt.isPresent()) {
					itsProsecutionEO = itsProsecutionEOOpt.get();
				}
			}
		} catch (Exception e) {
			logger.error("Business Exception in ProsecutionServiceImpl.getItsProsecutionEO method");
			throw new BusinessException(ERR_CODE, "Something went wrong in ProsecutionServiceImpl.getItsProsecutionEO() method." + e.getMessage());
		}
		
		return itsProsecutionEO;
	}
	
	private ItsProsecutionsOverpaymentXrefEO getItsProsecutionsOverpaymentXrefEO(Long prosOvpXrefId) {
		ItsProsecutionsOverpaymentXrefEO itsProsecutionsOverpaymentXrefEO = null;
		try {
			if (prosOvpXrefId != null) {
				Optional<ItsProsecutionsOverpaymentXrefEO> itsProsOvpXrefEOOpt = itsProsecutionsOverpaymentXrefRepository.findById(prosOvpXrefId);
				if (itsProsOvpXrefEOOpt.isPresent()) {
					itsProsecutionsOverpaymentXrefEO = itsProsOvpXrefEOOpt.get();
				}
			}
		} catch (Exception e) {
			logger.error("Business Exception in ProsecutionServiceImpl.getItsProsecutionsOverpaymentXrefEO method");
			throw new BusinessException(ERR_CODE, "Something went wrong in ProsecutionServiceImpl.getItsProsecutionsOverpaymentXrefEO() method." + e.getMessage());
		}
		
		return itsProsecutionsOverpaymentXrefEO;
	}

	@Override
	public List<ITSProsecutionListVO> getSearchBadActorData(SearchBadActorDataVO searchBadActorDataVO) {

		List<VITSProsecutionListEO> vITSProsecutionListEO = null;
		List<ITSProsecutionListVO> itsProsecutionListVO = new ArrayList<>();
		try {
			vITSProsecutionListEO = commonEntityManagerRepository.getProsSearchBadActorData(searchBadActorDataVO);
			if (vITSProsecutionListEO != null && !vITSProsecutionListEO.isEmpty()) {
				itsProsecutionListVO = vITSProsecutionListEO.stream().map(vITSProsecutionEO -> {
					ITSProsecutionListVO itsProsecutionVO = new ITSProsecutionListVO();
					BeanUtils.copyProperties(vITSProsecutionEO, itsProsecutionVO);
					itsProsecutionVO.setWarrantReqDt(DateUtil.convertDateToString(vITSProsecutionEO.getWarrantReqDt()));
					itsProsecutionVO.setIniCourtDt(DateUtil.convertDateToString(vITSProsecutionEO.getIniCourtDt()));
					itsProsecutionVO.setContinuedDt(DateUtil.convertDateToString(vITSProsecutionEO.getContinuedDt()));
					itsProsecutionVO.setConvictionDt(DateUtil.convertDateToString(vITSProsecutionEO.getConvictionDt()));
					itsProsecutionVO.setDateCreated(DateUtil.convertDateToString(vITSProsecutionEO.getDateCreated()));
					return itsProsecutionVO;
				}).collect(Collectors.toList());
			}
		} catch (Exception e) {
			logger.error("Business Exception in ProsecutionServiceImpl.getSearchBadActorData method");
			throw new BusinessException(ERR_CODE,
					"Something went wrong in ProsecutionServiceImpl.getSearchBadActorData() method."
							+ e.getMessage());
		}
		return itsProsecutionListVO;
	
	}

	@Override
	public List<ItsProsecutionsOverpaymentXrefVO> getITSProsecutionDetailsList(Long selectedProsecutionId) {
		List<VITSProsOvpxrefEO> vITSProsOvpxrefEOList = null;
		List<ItsProsecutionsOverpaymentXrefVO> prosOvpXrefVOList = new ArrayList<>();
		try {
			vITSProsOvpxrefEOList = vITSProsOvpxrefRepository.findByProsId(selectedProsecutionId);
			if (vITSProsOvpxrefEOList != null && !vITSProsOvpxrefEOList.isEmpty()) {
				prosOvpXrefVOList = vITSProsOvpxrefEOList.stream().map(vITSProsOvpxrefEO -> {
					ItsProsecutionsOverpaymentXrefVO prosOvpXrefVO = new ItsProsecutionsOverpaymentXrefVO();
					BeanUtils.copyProperties(vITSProsOvpxrefEO, prosOvpXrefVO);
					prosOvpXrefVO.setDateCreated(DateUtil.convertDateToString(vITSProsOvpxrefEO.getDateCreated()));
					prosOvpXrefVO.setRecoveryAmount(prosOvpXrefVO.getRecoveryAmount() * -1);
					return prosOvpXrefVO;
				}).collect(Collectors.toList());
			}
		} catch (BusinessException e) {
			logger.error("Business Exception in ProsecutionServiceImpl.getITSProsecutionDetailsList method");
			throw new BusinessException(ERR_CODE, "Something went wrong in ProsecutionServiceImpl.getITSProsecutionDetailsList() method." + e.getMessage());
		}
		
		return prosOvpXrefVOList;
	}
	
}
