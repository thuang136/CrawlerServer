package com.huangtao.techcrawler.entity;

public class ArticleParseData {

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArticleRelativePath() {
		return articleRelativePath;
	}

	public void setArticleRelativePath(String relativePath) {
		this.articleRelativePath = relativePath;
	}

	public String getIllustration() {
		return illustration;
	}

	public void setIllustration(String illustration) {
		this.illustration = illustration;
	}

	public String getHtmlText() {
		return htmlText;
	}

	public void setHtmlText(String htmlText) {
		this.htmlText = htmlText;
	}

	public WebURL getArticleWebUrl() {
		return articleWebUrl;
	}

	public void setArticleWebUrl(WebURL articleWebUrl) {
		this.articleWebUrl = articleWebUrl;
	}

	public String getPulishDate() {
		return pulishDate;
	}

	public void setPulishDate(String pulishDate) {
		this.pulishDate = pulishDate;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	private String title;

	private String pulishDate;

	private String articleRelativePath;

	private String illustration;

	private String htmlText;

	private WebURL articleWebUrl;

	private int viewCount;

}
