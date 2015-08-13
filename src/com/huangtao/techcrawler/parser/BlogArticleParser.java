package com.huangtao.techcrawler.parser;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.huangtao.techcrawler.CrawlerConfig;
import com.huangtao.techcrawler.PageFetcher;
import com.huangtao.techcrawler.entity.ArticleParseData;
import com.huangtao.techcrawler.entity.Page;
import com.huangtao.techcrawler.entity.WebURL;

public class BlogArticleParser {

	private static final Logger logger = Logger
			.getLogger(BlogArticleParser.class);

	public static void parseArticle(ArticleParseData articleData)
			throws ParserException {
		Page articlePage = PageFetcher
				.fetchPage(articleData.getArticleWebUrl());

		Parser articleParser = new Parser(articlePage.getHtml());

		NodeFilter filter = new TagNameFilter("div");

		HasAttributeFilter attributeFilter = new HasAttributeFilter("class",
				"article_content");

		NodeFilter titleFilter = new AndFilter(filter, attributeFilter);

		NodeList nodes = articleParser.extractAllNodesThatMatch(titleFilter);

		if (nodes.size() > 0) {

			Node articleNode = nodes.elementAt(0);

			if (articleNode != null) {
                logger.info(articleNode.toHtml());
				articleData.setHtmlText(articleNode.toHtml());

				parseArticleImg(articleData);
			}
		}

	}

	public static void parseArticleImg(ArticleParseData articleData)
			throws ParserException {

		String articleText = articleData.getHtmlText();

		Parser imgParser = new Parser(articleText);

		NodeFilter imgFilter = new TagNameFilter("img");

		NodeList nodes = imgParser.extractAllNodesThatMatch(imgFilter);

		if (nodes.size() > 0) {

			Node node = nodes.elementAt(0);

			if (node instanceof TagNode) {
				TagNode imgNode = (TagNode) node;
				String imgPath = imgNode.getAttribute("src");
				WebURL imgUrl = new WebURL();
				imgUrl.setURL(imgPath);
				Page imgPage = PageFetcher.fetchPage(imgUrl);
				String contentType = imgPage.getContentType();
				String[] types = contentType.split("/");
				if (!types[0].equals("image")) {
					return;
				}
				String imgName = imgPath
						.substring(imgPath.lastIndexOf('/') + 1);
				if (imgName.indexOf(".") == -1) {
					imgName = imgName + "." + types[1];
				}
				logger.info(imgName);

				String prefix = CrawlerConfig.crawlStorageFolder;

				articleData.setIllustration(imgName);
				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(prefix + imgName);
					fos.write(imgPage.getContentData());
					logger.info(prefix + imgName);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					logger.error("FileNotFoundException" + e.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error("IOException" + e.toString());
				} finally {
					try {
						if (fos != null) {
							fos.close();
						}
					} catch (IOException e) {
						logger.error("close io exception" + e.toString());
					}
				}

			}

		}

	}
}
