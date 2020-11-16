package cn.echohawk.dict;

import cn.echohawk.EnglishStudy;
import cn.echohawk.common.SpringUtils;
import cn.echohawk.service.dic.EnglishStudyServiceImpl;
import cn.echohawk.sever.EnglishStudyServer;
import cn.echohawk.util.DateUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DictFavoriteUtil {

	private static final  Logger LOG = LoggerFactory.getLogger(DictFavoriteUtil.class);

	private static EnglishStudyServer englishStudyServer = SpringUtils.getBean(EnglishStudyServiceImpl.class);
	private static Environment env = (Environment)SpringUtils.getBean(Environment.class);
	private static String newStudyDay = DateUtils.getDateStr(DateUtils.addDay(new Date(), 2),"yyyyMMdd");
	private static com.knziha.plod.dictionary.mdict md;

	static {
		try {
			md = new com.knziha.plod.dictionary.mdict(env.getProperty("dictPath"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<String[]> articleList = new LinkedList<>();
	public static List<EnglishStudy> favoriteList = new ArrayList<>();

	public static void parseArticle() throws Exception{
		//读取文章
		readArticle();
		LOG.info("文章" + JSON.toJSONString(articleList));
		//进行解析
		for (String[] strings : articleList) {
			for (String s : strings) {
				if (StringUtils.isNotBlank(s)) {
					//先查询是否存在记录
					EnglishStudy recrod = englishStudyServer.findByPk(s);
					if (recrod == null) {
						int index = md.lookUp(s, true);
						if (index > 0) {
							favoriteList.add(new EnglishStudy(s, "" + index));
							initEnglishStudy(s, "" + index, null);
						} else {
							LOG.info("【查询失败】出现查询不到情况" + JSON.toJSONString(s) + " 开启终极匹配测试。");
							finalSeek(s);
						}
					} else {
						LOG.info("出现库中原有的单词,加频次" + s);
						//处理之前学习的逻辑
						recrod.setUpdateTime(new Date());
						recrod.setFrequency(recrod.getFrequency() + 1);
						englishStudyServer.updateByPk(recrod);
					}
				} else {
					LOG.info("出现切割字符为空的情况");
				}
			}
		}
		LOG.info("结果" + JSON.toJSONString(favoriteList));
		//查找出来的结果放进xml文件
		addXml(favoriteList);
		LOG.info("-------------完美谢幕-------------");;
	}

	private static void finalSeek(String s) {
		int result = -1;
		String testWord = null;
		//加s的去s
		if (s.endsWith("ies")) {
			testWord = s.substring(0, s.length() - 3) + "y";
			result = md.lookUp(testWord, true);
		}
		if (result < 0 && s.endsWith("es")) {
			testWord = s.substring(0, s.length() - 2);
			result = md.lookUp(testWord, true);
		}
		if (result < 0 && s.endsWith("s")) {
			testWord = s.substring(0, s.length() - 1);
			result = md.lookUp(testWord, true);
		}
		if (result < 0 && s.endsWith("ed")) {
			testWord = s.substring(0, s.length() - 1);
			result = md.lookUp(testWord, true);
			if (result < 0) {
				testWord = s.substring(0, s.length() - 2);
				result = md.lookUp(testWord, true);
			}
		}
		if (result < 0 && s.endsWith("ing")) {
			testWord = s.substring(0, s.length() - 3);
			result = md.lookUp(testWord, true);
			if (result < 0) {
				testWord = s.substring(0, s.length() - 3) + "e";
				result = md.lookUp(testWord, true);
			}
		}
		if (result < 0 && s.endsWith("pped")) {
			testWord = s.substring(0, s.length() - 3);
			result = md.lookUp(testWord, true);
		}
		if (result > 0) {
			favoriteList.add(new EnglishStudy(testWord, "" + result));
			initEnglishStudy(s, "" + result, testWord);
			return;
		}
		initEnglishStudy(s, null, null);
	}

	private static void readArticle() throws Exception {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(env.getProperty("articlePath")));
			String s = br.readLine();
			while (s != null) {
				if (StringUtils.isNotBlank(s)) {
					String[] s1 = s.split(" ");
					for (int i = 0; i < s1.length; i++) {
						s1[i] = s1[i].toLowerCase().replaceAll("/\\.|\\.|\\,|\\，|\\-|\\-/g"," ").trim();
					}
					articleList.add(s1);
				}
				s = br.readLine();
			}
		} catch (Exception e) {
			LOG.error("读取文件报错", e);
		} finally {
			if (br != null) {
				br.close();
			}
		}
	}



	private static void initEnglishStudy(String word, String index, String orgin) {
		EnglishStudy study = new EnglishStudy();
		study.setUpdateTime(new Date());
		study.setCreateTime(new Date());
		study.setStudyFrequency(1);
		study.setLastStudyDay(newStudyDay);
		study.setFrequency(1);
		study.setWord(word);
		study.setIndexId(index);
		study.setOriginalWord(orgin);
		englishStudyServer.save(study);
	}

	private static void addXml(List<EnglishStudy> list) throws Exception {
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(env.getProperty("favoritePath")));
		Element rootElement = document.getRootElement();
		//如果覆盖的话 操作删除节点
		if ("Y".equals(env.getProperty("coverFlag"))) {
			for (Element entry : (List<Element>)rootElement.elements("Entry")) {
				rootElement.remove(entry);
			}
		}
		if ("Y".equals(env.getProperty("reviewFlag"))) {
			//查询存在的需要复习的单词
			List<EnglishStudy> reviewWordList = englishStudyServer.findReviewWord(DateUtils.getNowDayStr());
			for(EnglishStudy study : reviewWordList) {
				favoriteList.add(new EnglishStudy(study.getWord(), "" + study.getIndexId()));
				study.setStudyFrequency(study.getStudyFrequency() + 1);
				study.setLastStudyDay(DateUtils.getDateStr(DateUtils.addDay(DateUtils.toDate(study.getLastStudyDay(), "yyyyMMdd"), (1 << study.getStudyFrequency())), "yyyyMMdd"));
			}
			//一次10 批量更新
			for (int index = 0; index < reviewWordList.size(); index += 10) {
				englishStudyServer.batchUpdateByPk(reviewWordList.subList(index, index + 10 > reviewWordList.size() ? reviewWordList.size() : index + 10));
			}
		}
		//偏移量一天
		int offset = 86400;
		for (EnglishStudy favoriteBean : list) {
			if (StringUtils.isNotBlank(favoriteBean.getIndexId())) {
				Element entry = rootElement.addElement("Entry");
				entry.setText(favoriteBean.getWord());
				entry.addAttribute("LibID", "103");
				entry.addAttribute("Index", ""+favoriteBean.getIndexId());
				if (StringUtils.isNotBlank(favoriteBean.getOriginalWord())) {
					entry.setText(favoriteBean.getOriginalWord());
				}
				if ("History".equals(rootElement.getName())) {
					entry.addAttribute("Time", "" + ((System.currentTimeMillis() / 1000) - offset));
					offset--;
				}
			}
		}
		OutputFormat prettyPrint = OutputFormat.createPrettyPrint();
		OutputStream out = new BufferedOutputStream(new FileOutputStream(env.getProperty("favoritePath")));
		XMLWriter writer = new XMLWriter(out, prettyPrint);
		writer.write(document);
		writer.close();
	}
}
