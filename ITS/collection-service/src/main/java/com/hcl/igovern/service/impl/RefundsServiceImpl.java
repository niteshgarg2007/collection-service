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

import com.hcl.igovern.entity.ChecksEO;
import com.hcl.igovern.entity.ItsRefundsEO;
import com.hcl.igovern.entity.SpecialChecksEO;
import com.hcl.igovern.entity.VITSBadActorAddressEO;
import com.hcl.igovern.entity.VITSRefundsDataEO;
import com.hcl.igovern.exception.BusinessException;
import com.hcl.igovern.repository.ItsRefundsRepository;
import com.hcl.igovern.repository.SpecialChecksRepository;
import com.hcl.igovern.repository.VITSBadActorAddressRepository;
import com.hcl.igovern.repository.VITSRefundsDataRepository;
import com.hcl.igovern.service.RefundsService;
import com.hcl.igovern.util.DateUtil;
import com.hcl.igovern.vo.ITSRefundsDataVO;
import com.hcl.igovern.vo.SpecialChecksVO;

@Service
public class RefundsServiceImpl implements RefundsService {
	
	Logger logger = LoggerFactory.getLogger(RefundsServiceImpl.class);
	
	@Autowired
	private VITSRefundsDataRepository vITSRefundsDataRepository;
	
	@Autowired
	private ItsRefundsRepository itsRefundsRepository;
	
	@Autowired
	private VITSBadActorAddressRepository vITSBadActorAddressRepository;
	
	@Autowired
	private SpecialChecksRepository specialChecksRepository;
	
	public static final String ERR_CODE = "ERR_CODE";

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
					itsRefundsDataVOObj.setDateCreated(DateUtil.convertDateToString(vITSRefundsDataEOObj.getDateCreated()) );
					itsRefundsDataVOObj.setRefIssuedDate(DateUtil.convertDateToString(vITSRefundsDataEOObj.getRefIssuedDate()));
					itsRefundsDataVOList.add(itsRefundsDataVOObj);
				}
			}
		} catch (BusinessException e) {
			logger.error("Business Exception in RefundsServiceImpl getITSRefundsListList method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RefundsServiceImpl.getITSRefundsListList() method." + e.getMessage());
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

	@Override
	public ITSRefundsDataVO getITSRefundInfo(Long selectedRefundsId) {
		ITSRefundsDataVO itsRefundsDataVO  = new ITSRefundsDataVO();
		ItsRefundsEO itsRefundsEO = null;
		try {
			itsRefundsEO = getItsRefundsEO(selectedRefundsId);
			if (itsRefundsEO != null) {
				BeanUtils.copyProperties(itsRefundsEO, itsRefundsDataVO);
			}
		} catch (IllegalArgumentException ia) {
			logger.error("Business Exception in RefundsServiceImpl.getITSRefundInfo method");
			throw new BusinessException("ERR_CODE1", "Given Refund Id is null, Please provide valid Refund Id." +ia.getMessage());
		} catch (java.util.NoSuchElementException e) {
			logger.error("Business Exception in RefundsServiceImpl.getITSRefundInfo method");
			throw new BusinessException("ERR_CODE2", "Given Refund Id does not exist in Database." +e.getMessage());
		}
		
		return itsRefundsDataVO;
	}
	
	private ItsRefundsEO getItsRefundsEO(Long selectedRefundsId) {
		ItsRefundsEO itsRefundsEO = null;
		try {
			if (selectedRefundsId != null) {
				Optional<ItsRefundsEO> itsRefundsEOOpt = itsRefundsRepository.findById(selectedRefundsId);
				if (itsRefundsEOOpt.isPresent()) {
					itsRefundsEO = itsRefundsEOOpt.get();
				}
			}
		} catch (Exception e) {
			logger.error("Business Exception in RefundsServiceImpl.getItsRefundsEO method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RefundsServiceImpl.getItsRefundsEO() method." + e.getMessage());
		}
		
		return itsRefundsEO;
	}

	@Override
	public SpecialChecksVO getBadActorInfo(Long badActorId) {
		SpecialChecksVO specialChecksVO  = new SpecialChecksVO();
		VITSBadActorAddressEO vITSBadActorAddressEO = null;
		try {
			Optional<VITSBadActorAddressEO> badActorAddressEOOpt = vITSBadActorAddressRepository.findById(badActorId);
			if (badActorAddressEOOpt.isPresent()) {
				vITSBadActorAddressEO = badActorAddressEOOpt.get();
				specialChecksVO.setPayToOrderOf(vITSBadActorAddressEO.getBadActorName());
				specialChecksVO.setAddrLine1(vITSBadActorAddressEO.getAddressLine1());
				specialChecksVO.setAddrLine2(vITSBadActorAddressEO.getAddressLine2());
				specialChecksVO.setCityNm(vITSBadActorAddressEO.getCity());
				specialChecksVO.setSttCd(vITSBadActorAddressEO.getStt());
				specialChecksVO.setZipCd(vITSBadActorAddressEO.getZipCd());
			}
			
		} catch (IllegalArgumentException ia) {
			logger.error("Business Exception in RefundsServiceImpl.getBadActorInfo method");
			throw new BusinessException("ERR_CODE1", "Given bad actor Id is null, Please provide valid bad actor Id." +ia.getMessage());
		} catch (java.util.NoSuchElementException e) {
			logger.error("Business Exception in RefundsServiceImpl.getBadActorInfo method");
			throw new BusinessException("ERR_CODE2", "Given bad actor Id does not exist in Database." +e.getMessage());
		}
		
		return specialChecksVO;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public SpecialChecksVO confirmSpecialCheck(SpecialChecksVO specialChecksVO) {
		try {
			if (specialChecksVO.getSplChckId() == null) {
				ChecksEO checksEO = createChecksData(specialChecksVO);
				SpecialChecksEO specialChecksEO = createSpecialChecksData(specialChecksVO);
				specialChecksEO.setChecksEO(checksEO);
				specialChecksRepository.save(specialChecksEO);
				updateIssuedRefunds(specialChecksEO);
				specialChecksVO.setStatusMessage("Special Check has been initiated for Excess Refund.");
			}
		} catch (Exception e) {
			logger.error("Business Exception in RefundsServiceImpl.confirmSpecialCheck method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RefundsServiceImpl.confirmSpecialCheck() method." + e.getMessage());
		}
		return specialChecksVO;
	}

	private ChecksEO createChecksData(SpecialChecksVO specialChecksVO) {
		ChecksEO checksEO = new ChecksEO();
		try {
			checksEO.setChckAmt(specialChecksVO.getChkAmt());
			// "M" is for manual check
			checksEO.setPymtTypCd("M");
		} catch (Exception e) {
			logger.error("Business Exception in RefundsServiceImpl.createChecksData method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RefundsServiceImpl.createChecksData() method." + e.getMessage());
		}
		return checksEO;
	}

	private void updateIssuedRefunds(SpecialChecksEO specialChecksEO) {
		try {
			if (specialChecksEO != null && specialChecksEO.getItsRefundId() != null) {
				ItsRefundsEO itsRefundsEO = getItsRefundsEO(specialChecksEO.getItsRefundId());
				if (itsRefundsEO != null) {
					itsRefundsEO.setRefAmtIssued(specialChecksEO.getChkAmt());
					itsRefundsEO.setRefDspn("I");
					itsRefundsEO.setRefIssuedDate(DateUtil.parseDateTime(DateUtil.getCurrentDateString()));
					itsRefundsRepository.save(itsRefundsEO);
				}
			}
		} catch (BusinessException e) {
			logger.error("Business Exception in RefundsServiceImpl.updateIssuedRefunds method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RefundsServiceImpl.updateIssuedRefunds() method." + e.getMessage());
		}
	}

	private SpecialChecksEO createSpecialChecksData(SpecialChecksVO specialChecksVO) {
		SpecialChecksEO specialChecksEO = new SpecialChecksEO();
		try {
			specialChecksEO.setPayToOrderOf(specialChecksVO.getPayToOrderOf());
			specialChecksEO.setItsRefundId(specialChecksVO.getItsRefundId());
			specialChecksEO.setChkAmt(specialChecksVO.getChkAmt());
			specialChecksEO.setGlctrlSubCd(specialChecksVO.getGlctrlSubCd());
			specialChecksEO.setReasons(specialChecksVO.getReasons());
			specialChecksEO.setComments(specialChecksVO.getComments());
			specialChecksEO.setAddrLine1(specialChecksVO.getAddrLine1());
			specialChecksEO.setAddrLine2(specialChecksVO.getAddrLine2());
			specialChecksEO.setCityNm(specialChecksVO.getCityNm());
			specialChecksEO.setSttCd(specialChecksVO.getSttCd());
			specialChecksEO.setZipCd(specialChecksVO.getZipCd());
			specialChecksEO.setZipPlusFour(specialChecksVO.getZipPlusFour());
		} catch (Exception e) {
			logger.error("Business Exception in RefundsServiceImpl.createSpecialChecksData method");
			throw new BusinessException(ERR_CODE, "Something went wrong in RefundsServiceImpl.createSpecialChecksData() method." + e.getMessage());
		}
		return specialChecksEO;
	}

}
