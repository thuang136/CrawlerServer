package com.huangtao.techcrawler.entity;

import java.nio.charset.Charset;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;

public class Page {

	public WebURL getUrl() {
		return url;
	}

	public void setUrl(WebURL url) {
		this.url = url;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public void load(HttpEntity entity) throws Exception {

		contentType = null;
		Header type = entity.getContentType();
		if (type != null) {
			contentType = type.getValue();
		}

		contentEncoding = null;
		Header encoding = entity.getContentEncoding();
		if (encoding != null) {
			contentEncoding = encoding.getValue();
		}

		Charset charset = ContentType.getOrDefault(entity).getCharset();
		if (charset != null) {
			contentCharset = charset.displayName();
		}

		contentData = EntityUtils.toByteArray(entity);
	}

	protected WebURL url;

	protected String html;

	public Page(WebURL url) {
		this.url = url;
	}

	public byte[] getContentData() {
		return contentData;
	}

	public void setContentData(byte[] contentData) {
		this.contentData = contentData;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentEncoding() {
		return contentEncoding;
	}

	public void setContentEncoding(String contentEncoding) {
		this.contentEncoding = contentEncoding;
	}

	public String getContentCharset() {
		return contentCharset;
	}

	public void setContentCharset(String contentCharset) {
		this.contentCharset = contentCharset;
	}

	/**
	 * The content of this page in binary format.
	 */
	protected byte[] contentData;

	/**
	 * The ContentType of this page. For example: "text/html; charset=UTF-8"
	 */
	protected String contentType;

	/**
	 * The encoding of the content. For example: "gzip"
	 */
	protected String contentEncoding;

	/**
	 * The charset of the content. For example: "UTF-8"
	 */
	protected String contentCharset;
}
