package cn.echohawk;

import java.io.Serializable;
import java.util.Date;


public class EnglishStudy implements Serializable {
	private String word;
	private String indexId;
	private Date createTime;
	private Date updateTime;
	private int frequency;
	private int studyFrequency;
	private String lastStudyDay;
	private String originalWord;

	public EnglishStudy() {
		super();
	}

	public EnglishStudy(String word, String indexId) {
		this.word = word;
		this.indexId = indexId;
	}

	public String getOriginalWord() {
		return originalWord;
	}

	public void setOriginalWord(String originalWord) {
		this.originalWord = originalWord;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getIndexId() {
		return indexId;
	}

	public void setIndexId(String index) {
		this.indexId = index;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public int getStudyFrequency() {
		return studyFrequency;
	}

	public void setStudyFrequency(int studyFrequency) {
		this.studyFrequency = studyFrequency;
	}

	public String getLastStudyDay() {
		return lastStudyDay;
	}

	public void setLastStudyDay(String lastStudyDay) {
		this.lastStudyDay = lastStudyDay;
	}
}
