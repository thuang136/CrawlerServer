package com.huangtao.techcrawler;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

import com.huangtao.FirstServlet;
import com.huangtao.techcrawler.entity.Page;
import com.huangtao.techcrawler.entity.WebURL;

public class PageFetcher {

	private static final Logger logger = Logger.getLogger(PageFetcher.class);

	public static Page fetchPage(WebURL webUrl) {

		Page page = new Page(webUrl);

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

		HttpGet httpGet = new HttpGet(webUrl.getURL());
		httpGet.setHeader("User-Agent",
				"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)");

		try {
			HttpResponse httpResponse = closeableHttpClient.execute(httpGet);
			HttpEntity entity = httpResponse.getEntity();

			if (entity != null) {
				try {
					page.load(entity);
					logger.info(page.getContentCharset());
					byte[] contentData = page.getContentData();
					String entityStr = new String(contentData, "UTF-8");
					page.setHtml(entityStr);

				} catch (Exception e) {

				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			try {
				closeableHttpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return page;
	}
}
