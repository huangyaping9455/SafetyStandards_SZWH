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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author hyp
 * @description: gps3.0数据源
 * @projectName SafetyStandards
 * @date 2019/10/1615:38
 */
@Configuration
//指定该SqlSession对象对应的dao(basePackages , dao扫包  sqlSessionFactoryRef: SqlSessionFactory对象注入到该变量中)
@MapperScan(basePackages = "org.springblade.gps", sqlSessionFactoryRef = "GPS3Factory")
public class GPS3DataSourceConfig {


    /**
     * 封装数据源对象创建, 该方法就已经将数据源的各个数据封装到该对象中
     * @return
     */
    @Bean(name = "GPS3dataSource")
    @Primary //必须要有, 说明该数据源是默认数据源
    @ConfigurationProperties(prefix = "spring.datasource.gps3") //读取的数据源前缀, 跟yml文件对应
    public DataSource DB1dataSource(){
        return new DruidDataSource();
    }

    /**
     * SqlSession对象创建
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "GPS3Factory")
    @Primary
    public SqlSessionFactory DB1Factory(@Qualifier("GPS3dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        //指定起别名的包, 这里注意, 设置该参数时打成jar启动会找不到该包下的类,目前未找到解决方案
        bean.setTypeAliasesPackage("org.springblade.gps");
        bean.setDataSource(dataSource);
        //指定该SqlSession对应的mapper.xml文件位置
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:org/springblade/gps/**/mapper/*Mapper.xml"));
		MybatisConfiguration configuration = new MybatisConfiguration();
		// 配置打印sql语句
		configuration.setLogImpl(StdOutImpl.class);

		bean.setConfiguration(configuration);
		return bean.getObject();
    }
}
