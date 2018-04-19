/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: EsDao
 * Author:   apple
 * Date:     2018/4/19 14:20
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.news.dao;

import com.news.util.EsUtils;
import org.elasticsearch.client.transport.TransportClient;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author apple
 * @create 2018/4/19
 * @since 1.0.0
 */
public class EsDao {
    private Connection conn;

    public void getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String user = "root";
            String password = "admin";
            String url = "jdbc:mysql://localhost:3306/NEWS?serverTimezone=UTC";
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                System.out.println("mysql 连接成功!");
            } else {
                System.out.println("mysql 连接失败");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        //将数据库中的数据导入到Es中
    public void mysqlToEs() {
        String sql = "SELECT * FROM wcmdocument";
        TransportClient client = EsUtils.getSingleClient();
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery();
            Map<String, Object> map = new HashMap<String, Object>();
            int i = 1;
            while (resultSet.next()) {
                map.put("DOCTITLE", resultSet.getString(5));
                map.put("DOCABSTRACT", resultSet.getString(12));
                map.put("DOCPUBURL", resultSet.getString(22));
                System.out.println(map);
                client.prepareIndex("testone", "new", String.valueOf(i++))
                        .setSource(map)
                        .execute()
                        .actionGet();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}