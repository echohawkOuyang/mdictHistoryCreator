package com.echohawk.demo;

import cn.echohawk.dict.DictFavoriteUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(scanBasePackages = "cn.echohawk", exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class DebugTestApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(DebugTestApplication.class, args);
		DictFavoriteUtil.parseArticle();
	}

}
