/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: SpringbootEsNews
 * Author:   apple
 * Date:     2018/4/19 15:08
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.news;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author apple
 * @create 2018/4/19
 * @since 1.0.0
 */
@SpringBootApplication
//异步调用
@EnableScheduling
public class SpringbootEsNews {


    public static void main(String[] args) {
        SpringApplication.run(SpringbootEsNews.class, args);
    }


}