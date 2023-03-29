package org.springblade.gps.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author hyp
 * @description: gps2.0数据源
 * @projectName SafetyStandards
 * @date 2019/10/1615:56
 */
@Configuration
@MapperScan(basePackages = "org.springblade.gps2", sqlSessionFactoryRef = "GPS2Factory")
public class GPS2DataSourceConfig {
    @Bean(name = "GPS2dataSource")
    @ConfigurationProperties(prefix = "spring.datasource.gps2")
    public DataSource DB1dataSource(){
        return new DruidDataSource();
    }

    @Bean(name = "GPS2Factory")
    public SqlSessionFactory DB1Factory(@Qualifier("GPS2dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        //这里注意, 设置该参数时打成jar启动会找不到该包下的类,目前未找到解决方案
        bean.setTypeAliasesPackage("org.springblade.gps");
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:org/springblade/gps2/**/mapper/*Mapper.xml"));
		MybatisConfiguration configuration = new MybatisConfiguration();
		// 配置打印sql语句
		configuration.setLogImpl(StdOutImpl.class);

		bean.setConfiguration(configuration);
        return bean.getObject();
    }
}

