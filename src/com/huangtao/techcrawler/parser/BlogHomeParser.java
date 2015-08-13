package com.huangtao.techcrawler.parser;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.Span;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.huangtao.techcrawler.entity.ArticleParseData;
import com.huangtao.techcrawler.entity.BlogHomeParseData;
import com.huangtao.techcrawler.entity.Page;

public class BlogHomeParser {

	Logger logger = Logger.getLogger(BlogHomeParser.class);

	String charset;

	public BlogHomeParseData parse(Page page) throws ParserException {

		BlogHomeParseData blogHomeData = new BlogHomeParseData(page.getUrl());

		Parser htmlParser = new Parser(page.getHtml());

		NodeFilter filter = new TagNameFilter("div");

		HasAttributeFilter attributeFilter = new HasAttributeFilter("class",
				"list_item article_item");

		NodeFilter titleFilter = new AndFilter(filter, attributeFilter);

		NodeList nodes = htmlParser.extractAllNodesThatMatch(titleFilter);

		if (nodes != null) {
			for (Node node : nodes.toNodeArray()) {
				ArticleParseData articleData = parseArticleItem(node.toHtml());
				blogHomeData.addArticleData(articleData);
			}
		}

		return blogHomeData;

	}

	private ArticleParseData parseArticleItem(String itemHtml)
			throws ParserException {

		ArticleParseData articleData = new ArticleParseData();
		Parser itemParser = new Parser(itemHtml);

		NodeFilter filter = new TagNameFilter("span");

		NodeList nodes = itemParser.extractAllNodesThatMatch(filter);

		if (nodes != null) {

			for (Node node : nodes.toNodeArray()) {
				Span spanNode = (Span) node;

				switch (spanNode.getAttribute("class")) {

				case "link_title":

					for (Node childNode : spanNode.getChildren().toNodeArray()) {
						if (childNode instanceof LinkTag) {
							LinkTag link = (LinkTag) childNode;
							articleData.setArticleRelativePath(link.getLink());
							String title = link.getLinkText().trim();
							logger.info(title);
							articleData.setTitle(title);
						}
					}

					break;

				case "link_postdate":
					String dateStr = spanNode.getStringText();
					articleData.setPulishDate(dateStr);
					break;

				case "link_view":

					String viewText = spanNode.getStringText();
					int startIndex = viewText.indexOf('(') + 1;
					int endIndex = viewText.indexOf(')');
					int viewCount = Integer.parseInt(viewText.substring(
							startIndex, endIndex));
					articleData.setViewCount(viewCount);
					break;

				}
			}

		}

		return articleData;

	}
}
