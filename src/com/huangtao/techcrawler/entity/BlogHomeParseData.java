package com.huangtao.techcrawler.entity;

import java.util.ArrayList;

public class BlogHomeParseData {

	private ArrayList<ArticleParseData> articleDataList = new ArrayList<ArticleParseData>();

	private WebURL blogHomeUrl;

	public BlogHomeParseData(WebURL blogHomeUrl) {
		this.blogHomeUrl = blogHomeUrl;
	}

	public ArrayList<ArticleParseData> getArticleDataList() {
		return articleDataList;
	}

	public void setArticleDataList(ArrayList<ArticleParseData> articleDataList) {
		this.articleDataList = articleDataList;
	}

	public void addArticleData(ArticleParseData articleData) {

		String homePrefix = blogHomeUrl.getPrefix();

		String relativePath = articleData.getArticleRelativePath();

		WebURL articleUrl = new WebURL();
		articleUrl.setURL(homePrefix + relativePath);
		articleUrl.setDepth(blogHomeUrl.getDepth() + 1);
		articleData.setArticleWebUrl(articleUrl);

		articleDataList.add(articleData);

	}

}
