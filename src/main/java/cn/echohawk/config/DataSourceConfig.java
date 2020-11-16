package cn.echohawk.config;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@MapperScan(value="cn.echohawk.service.dic.mapper",sqlSessionTemplateRef="dictSqlSessionTemplate")
public class DataSourceConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfig.class);

	@Bean(name = "dictDatasource")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.dic") // application.properteis中对应属性的前缀
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "dictTransactionManager")
	@Primary
	public DataSourceTransactionManager devTransactionManager(@Qualifier("dictDatasource") DataSource dataSource)
			throws SQLException {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean(name = "dictSqlSessionFactory")
	public SqlSessionFactory bossSqlSessionFactory(@Qualifier("dictDatasource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		//添加XML目录
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		try {
			bean.setMapperLocations(resolver.getResources("classpath*:mapping/dic/*.xml"));
			return bean.getObject();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			throw new RuntimeException(e);
		}
	}

	@Bean(name = "dictSqlSessionTemplate")
	public SqlSessionTemplate bossSqlSessionTemplate(@Qualifier("dictSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory); // 使用上面配置的Factory
		return template;
	}
}
