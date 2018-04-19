/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: SearchAction
 * Author:   apple
 * Date:     2018/4/19 15:17
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.news.control;

import com.news.util.EsToNews;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author apple
 * @create 2018/4/19
 * @since 1.0.0
 */
@Controller
public class SearchAction {


    @RequestMapping(value = "/EsGetNews")
    @ResponseBody           // 返回JSON数据
    public List<Map<String, Object>> EsGetAction(@RequestParam("textVal") String query, ModelMap modelMap) {

        EsToNews dao = new EsToNews();
        List<Map<String, Object>> map = dao.index(query);
        return map;
    }


}