package com.hcl.igovern.service;

import java.util.List;

import com.hcl.igovern.vo.ITSRefundsDataVO;
import com.hcl.igovern.vo.SpecialChecksVO;

public interface RefundsService {

	List<ITSRefundsDataVO> getITSRefundsListList(Long badActorId);

	ITSRefundsDataVO getITSRefundInfo(Long selectedRefundsId);

	SpecialChecksVO getBadActorInfo(Long badActorId);

	SpecialChecksVO confirmSpecialCheck(SpecialChecksVO specialChecksVO);

}
