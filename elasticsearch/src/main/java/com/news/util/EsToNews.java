/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: EsToNews
 * Author:   apple
 * Date:     2018/4/19 15:37
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.news.util;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.util.ArrayList;
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
public class EsToNews {
    public List<Map<String,Object>> index(String query){
        return searchSpnes(query);
    }

    private List<Map<String,Object>> searchSpnes(String query) {
        // 获取client
        TransportClient client = EsUtils.getSingleClient();
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders
                .multiMatchQuery(query, "DOCTITLE", "DOCABSTRACT");
        SearchResponse response = client.prepareSearch("testone")
                .setTypes("new")
                .setQuery(multiMatchQueryBuilder)
                .setFrom(0)
                .setSize(10)
                .execute()
                .actionGet();

        SearchHits hits = response.getHits();

        ArrayList<Map<String, Object>> newslist = new ArrayList<>();

        for (SearchHit hit : hits) {
            Map<String, Object> news = hit.getSourceAsMap();
            newslist.add(news);
        }
        int length = (int) hits.getTotalHits();

        return newslist;

    }
}