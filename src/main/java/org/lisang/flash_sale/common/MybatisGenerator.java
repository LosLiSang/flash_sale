package org.lisang.flash_sale.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.INameConvert;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import org.lisang.flash_sale.controller.base.BaseController;
import org.lisang.flash_sale.domain.base.BaseModel;

public class MybatisGenerator {
    public static void main(String[] args) {
        String url = "jdbc:mysql://121.43.40.44:3306/flash_sale?serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "root1234";
        String outDir = "E:\\workstation\\src\\github\\private\\flash_sale\\src\\main\\java";
//        String outDir = "E:\\workstation";
        String author = "lisang";
        FastAutoGenerator
                .create(url, username, password)
                .globalConfig(builder -> {
                    builder.outputDir(outDir)
                            .author(author)
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
                            .controller("controller.admin")
                            .build();
                })
                .strategyConfig(builder -> {
                    builder
                        .controllerBuilder()
                            .superClass(BaseController.class)
                            .enableRestStyle()
//                            .formatFileName("%sAdminController")
                            .enableFileOverride()
                        .entityBuilder()
                            .formatFileName("%sPO")
                            .superClass(BaseModel.class)
                            .logicDeleteColumnName("del_flag")
                            .logicDeletePropertyName("deleted")
                            .idType(IdType.AUTO)
                            .enableActiveRecord()
                            .enableTableFieldAnnotation()
                            .addSuperEntityColumns("id", "del_flag", "create_time", "update_time", "create_user_id", "update_user_id")
                            .enableLombok()
//                            .enableFileOverride()
                        .serviceBuilder()
                            .formatServiceFileName("%sService")
//                            .enableFileOverride()
                        .mapperBuilder()
                            .enableMapperAnnotation()
                            .enableBaseColumnList()
                            .enableBaseResultMap()
//                            .enableFileOverride()
                            .build();
                })
                .templateConfig(builder -> {
                    builder.controller("template/controller.java.vm")
                    .build();
                })
                .execute();

    }

}
