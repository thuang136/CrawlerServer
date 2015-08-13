package com.huangtao.techcrawler.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.huangtao.techcrawler.entity.ArticleParseData;

public class ArticleDBHelper {
	private static final String url = "jdbc:mysql://127.0.0.1/articleInfo";
	private static final String name = "com.mysql.jdbc.Driver";
	private static final String user = "root";
	private static final String password = "samsung";

	private static Connection conn = null;

	static {
		try {
			Class.forName(name);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {

		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return conn;

	}

	public static boolean insert(ArticleParseData articleData) {
		Connection conn = getConnection();

		String sql = "insert into Article(title,domain,article_text) values(?,?,?)";

		PreparedStatement pstmt;
		int affectNum = 0;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, articleData.getTitle());
			pstmt.setString(2, articleData.getArticleWebUrl().getDomain());
			pstmt.setString(3, articleData.getHtmlText());

			affectNum = pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return affectNum != 0;

	}

}
