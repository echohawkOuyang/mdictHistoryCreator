package cn.echohawk.sever;

import cn.echohawk.EnglishStudy;
import org.springframework.stereotype.Service;

import java.util.List;


public interface EnglishStudyServer {
	public void save(EnglishStudy bean);

	public EnglishStudy findByPk(String word);

	public void updateByPk(EnglishStudy bean);

	public List<EnglishStudy> findReviewWord(String dateStr);

	public void batchUpdateByPk(List<EnglishStudy> reviewWordList);
}
