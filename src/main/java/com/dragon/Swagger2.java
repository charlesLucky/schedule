package com.dragon;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dragon"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
//        ApiInfo apiInfo = new ApiInfo("Test相关接口",//大标题
//                    "Test相关接口，主要用于测试.",//小标题
//                    "1.0",//版本
//                    "http://412887952-qq-com.iteye.com/",
//                    "Angel",//作者
//                    "北京知远信息科技有限公司",//链接显示文字
//                    "http://www.kfit.com.cn/"//网站链接
//            );
//        return apiInfo;
        return new ApiInfoBuilder()
                .title("DreamGaming 平台网数据库接口文档")
                .description("默认方法名 http方法类型</br>新增(post)-add</br>删除(delete)-delete</br>修改(put)-update</br>根据ID查询单个(post)-getById</br>分页查询列表(post)-query</br>查询所有列表(post)-queryAll")
                .contact("songbo")
                .version("1.0")
                .license("DreamGaming")
                .licenseUrl("http://www.dg99.info/")
                .build();
    }
}
