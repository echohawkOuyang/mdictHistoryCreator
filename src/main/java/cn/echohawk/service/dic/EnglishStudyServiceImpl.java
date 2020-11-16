package cn.echohawk.service.dic;

import cn.echohawk.EnglishStudy;
import cn.echohawk.service.dic.mapper.EnglishStudyMapper;
import cn.echohawk.sever.EnglishStudyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnglishStudyServiceImpl implements EnglishStudyServer {
	@Autowired
	private EnglishStudyMapper englishStudyMapper;
	@Override
	public void save(EnglishStudy bean) {
		englishStudyMapper.save(bean);
	}

	@Override
	public EnglishStudy findByPk(String word) {
		return englishStudyMapper.findByPk(word);
	}

	@Override
	public void updateByPk(EnglishStudy bean) {
		englishStudyMapper.updateByPk(bean);
	}

	@Override
	public List<EnglishStudy> findReviewWord(String dateStr) {
		return englishStudyMapper.findReviewWord(dateStr);
	}

	@Override
	public void batchUpdateByPk(List<EnglishStudy> reviewWordList) {
		englishStudyMapper.batchUpdateByPk(reviewWordList);
	}
}
