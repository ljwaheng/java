/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: GoToUrl
 * Author:   apple
 * Date:     2018/4/19 15:14
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.news.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author apple
 * @create 2018/4/19
 * @since 1.0.0
 */
@Controller
public class GoToUrl {

    //跳转到主界面
    @RequestMapping("/index")
    public String index(){
        return "news";
    }
}