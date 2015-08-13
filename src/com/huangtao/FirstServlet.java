package com.huangtao;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.htmlparser.util.ParserException;

import com.huangtao.techcrawler.CrawlerConfig;
import com.huangtao.techcrawler.PageFetcher;
import com.huangtao.techcrawler.entity.ArticleParseData;
import com.huangtao.techcrawler.entity.BlogHomeParseData;
import com.huangtao.techcrawler.entity.Page;
import com.huangtao.techcrawler.entity.WebURL;
import com.huangtao.techcrawler.parser.BlogArticleParser;
import com.huangtao.techcrawler.parser.BlogHomeParser;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FirstServlet extends HttpServlet {

	Logger logger = Logger.getLogger(FirstServlet.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -6086646959681830786L;

	List<ArticleParseData> articleDatas;

	String prefix;

	public void init() {
		// NOOP by default
		logger.info("First Servlet init");

		String filePath = this.getServletConfig().getServletContext()
				.getRealPath("/");

		logger.info(filePath);

		prefix = this.getInitParameter("server")
				+ this.getServletConfig().getServletContext().getContextPath()
				+ "/";

		CrawlerConfig.crawlStorageFolder = filePath;

		WebURL webUrl = new WebURL();
		webUrl.setURL("http://blog.csdn.net/lmj623565791");

		PageFetcher pageFetcher = new PageFetcher();
		Page page = pageFetcher.fetchPage(webUrl);

		BlogHomeParser parser = new BlogHomeParser();

		try {
			BlogHomeParseData blogHomeData = parser.parse(page);
			articleDatas = blogHomeData.getArticleDataList();
			Iterator it = articleDatas.iterator();
			while (it.hasNext()) {
				ArticleParseData articleData = (ArticleParseData) (it.next());
				BlogArticleParser.parseArticle(articleData);
				logger.info(prefix + articleData.getIllustration());
				logger.info(articleData.getTitle());
				logger.info(articleData.getArticleWebUrl().getDomain());
			}

		} catch (ParserException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
		}

	}

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		logger.info("日志信息开始");

		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();

		JSONObject json = new JSONObject();

		JSONArray members = new JSONArray();

		Iterator<ArticleParseData> it = articleDatas.iterator();

		String ip = request.getServerName() + ":" + request.getServerPort();

		logger.info(ip);

		int count = 0;
		while (it.hasNext()) {
			ArticleParseData articleData = (ArticleParseData) (it.next());
			JSONObject member = new JSONObject();
			member.put("title", articleData.getTitle());
			member.put("domain", articleData.getArticleWebUrl().getDomain());
			member.put("imgUrl", prefix + articleData.getIllustration());

			member.put("html", articleData.getHtmlText());

			members.add(count, member);
			count++;

		}

		json.put("articles", members);

		out.write(json.toString());
	}
}