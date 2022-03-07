package com.itheima.sys.auth.config.dataorigins;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.itheima.sys.corebase.handler.ScMateObjectHandler;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * 多数据源
 * mysql数据源
 * @author 10445
 */
@Configuration
@MapperScan(basePackages = {"com.itheima.sys.auth.mapper"}, sqlSessionFactoryRef = "mysqlSqlSessionFactory")
public class MysqlConfig {
    //将这个对象放入Spring容器中
    @Bean(name = "mysqlDataSource")
    //读取配置文件中的配置参数映射成为一个对象
    //prefix表示参数前缀
    @ConfigurationProperties(prefix = "spring.datasource.mysql")
    public DataSource getDataSourceMysql() {
        return DataSourceBuilder.create().build();
    }
    @Bean(name = "mysqlSqlSessionFactory")
    //@Qualifier表示查找Spring容器中名字为mysqlDataSource的对象
    public SqlSessionFactory mysqlSqlSessionfactory(@Qualifier("mysqlDataSource") DataSource dataSource) throws Exception {
        //SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        //spring boot多数据源mybatis-plus的baseMapper的里面的方法无法使用
        //SqlSessionFactoryBean改为MybatisSqlSessionFactoryBean即可，否则baseMapper自带的方法不能访问，但能访问*Mapper.xml中定义的方法

        GlobalConfig globalConfig = new GlobalConfig();
        //对象字段填充控制器
        globalConfig.setMetaObjectHandler(new ScMateObjectHandler());
        //是否控制台打印mybatis-plus logo
        globalConfig.setBanner(true);
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(
                //设置mybatis的xml所造位置
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/**/*Mapper.xml")
        );
        bean.setGlobalConfig(globalConfig);
        return bean.getObject();
    }
    @Bean("mysqlSqlSessionTemplate")
    //表示这个数据源是默认数据源
    @Primary
    public SqlSessionTemplate mysqlSqlSessionTemplate(
            @Qualifier("mysqlSqlSessionFactory") SqlSessionFactory sqlSessionFactory
    ) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
