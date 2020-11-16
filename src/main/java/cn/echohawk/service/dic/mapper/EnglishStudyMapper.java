package cn.echohawk.service.dic.mapper;

import cn.echohawk.EnglishStudy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnglishStudyMapper {
	public void save(EnglishStudy bean);

	public EnglishStudy findByPk(@Param("word") String word);

	public void updateByPk(EnglishStudy bean);

	public List<EnglishStudy> findReviewWord(@Param("dateStr") String dateStr);

	public void batchUpdateByPk(@Param("list") List<EnglishStudy> reviewWordList);
}
