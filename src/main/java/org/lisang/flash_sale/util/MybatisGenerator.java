package org.lisang.flash_sale.util;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import org.lisang.flash_sale.controller.base.BaseController;

public class MybatisGenerator {
    public static void main(String[] args) {
        String url = "jdbc:mysql://121.43.40.44:3306/flash_sale?serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "root1234";
        String outDir = "E:\\workstation\\src\\github\\private\\flash_sale\\src\\main\\java";
        String authour = "lisang";
        FastAutoGenerator
                .create(url, username, password)
                .globalConfig(builder -> {
                    builder.outputDir(outDir)
                            .author(authour)
                            .enableSwagger()
                            .dateType(DateType.TIME_PACK)
                            .commentDate("yyyy-MM-dd")
                            .build();
                })
                .packageConfig(builder -> {
                    builder.parent("org.lisang.flash_sale")
                            .entity("domain.po")
                            .service("service")
                            .serviceImpl("service.impl")
                            .xml("mapper.xml")
                            .mapper("mapper")
                            .controller("controller")
                            .build();
                })
                .strategyConfig(builder -> {
                    builder
                        .controllerBuilder()
                            .superClass(BaseController.class)
                            .enableRestStyle()
                        .entityBuilder()
                            .formatFileName("%sPO")
                            .logicDeleteColumnName("del_flag")
                            .logicDeletePropertyName("deleted")
                            .idType(IdType.AUTO)
                            .enableLombok()
                        .serviceBuilder()
                        .mapperBuilder()
                            .enableBaseColumnList()
                            .enableBaseResultMap()
                            .build();
                })
                .execute();

    }

}
