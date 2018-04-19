/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: EsUtils
 * Author:   apple
 * Date:     2018/4/19 14:04
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.news.util;

import com.news.dao.EsDao;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author apple
 * @create 2018/4/19
 * @since 1.0.0
 */
public class EsUtils {
    private static TransportClient client;

    public static final String CLUSTER_NAME = "elasticsearch";
    public static final String HOST_IP = "127.0.0.1";
    public static final int TCP_PORT = 9300;

    static Settings settings = Settings.builder().put("cluster.name", CLUSTER_NAME).build();

    /*
        创建一个单例的Client
     */
    public static TransportClient getSingleClient() {
        if (client == null) {
            synchronized (TransportClient.class) {
            }
            if (client == null) {
                try {
                    client = new PreBuiltTransportClient(settings).addTransportAddress(new TransportAddress(InetAddress.getByName(HOST_IP), TCP_PORT));
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }
        return client;
    }
    //获取索引管理的Admin
    public static IndicesAdminClient getAdminClient() {

        return getSingleClient().admin().indices();
    }

    //创建索引
    public static boolean createIndex(String indexName, int shards, int replicas) {

        Settings settings = Settings.builder()
                .put("index.number_of_shards", shards)
                .put("index.number_of_replicas", replicas).build();
        CreateIndexResponse createIndexResponse = getAdminClient()
                .prepareCreate(indexName.toLowerCase())
                .setSettings(settings)
                .execute().actionGet();
        boolean isIndexCreated = createIndexResponse.isShardsAcknowledged();
        if (isIndexCreated) {
            System.out.println("索引" + indexName + "创建成功");
        } else {
            System.out.println("索引" + indexName + "创建成功");
        }
        return isIndexCreated;
    }

    public static boolean setMapping(String indexName, String typeName, String mapping) {

        getAdminClient().preparePutMapping(indexName)
                .setType(typeName)
                .setSource(mapping, XContentType.JSON)
                .get();
        return false;
    }

    public static void main(String[] args){
        // 1.创建索引  相当于 创建一个数据库
        EsUtils.createIndex("testone", 3, 0);   //注意不能用大写

        // 2.设置Mapping 相当于 创建一个表
        try {
            XContentBuilder builder = jsonBuilder()
                    .startObject()
                    .startObject("properties")
                    .startObject("DOCTITLE")       // 要和你数据库中想提取数据的字段名一致
                    .field("type","text")   // 类型
                    .field("analyzer","ik_max_word") // 精确分词
                    .field("search_analyzer","ik_max_word") //精确匹配
                    .field("boost","2")
                    .endObject()
                    .startObject("DOCABSTRACT")
                    .field("type","text")
                    .field("analyzer","ik_max_word")
                    .field("search_analyzer","ik_max_word")
                    .endObject()
                    .startObject("DOCPUBURL")
                    .field("type","keyword")
                    .endObject()
                    .endObject()
                    .endObject();
            EsUtils.setMapping("testone", "new", builder.string());     //注意不能用大写
        }catch (IOException e){
                e.printStackTrace();
        }
        // 3.读取mysql
        EsDao dao = new EsDao();
        dao.getConnection();
        dao.mysqlToEs();
    }
}