/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huangtao.techcrawler.entity;

import java.io.Serializable;

public class WebURL implements Serializable {

	private static final long serialVersionUID = 1L;

	private String url;
	private String parentUrl;
	private int depth;
	private String domain;

	private String path;

	private String prefix;

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return Url string
	 */
	public String getURL() {
		return url;
	}

	public void setURL(String url) {
		this.url = url;

		int domainStartIdx = url.indexOf("//") + 2;
		int domainEndIdx = url.indexOf('/', domainStartIdx);
		domainEndIdx = (domainEndIdx > domainStartIdx) ? domainEndIdx : url
				.length();
		domain = url.substring(domainStartIdx, domainEndIdx);
		prefix = url.substring(0, domainEndIdx);
		path = url.substring(domainEndIdx);
		int pathEndIdx = path.indexOf('?');
		if (pathEndIdx >= 0) {
			path = path.substring(0, pathEndIdx);
		}
	}

	/**
	 * @return url of the parent page. The parent page is the page in which the
	 *         Url of this page is first observed.
	 */
	public String getParentUrl() {
		return parentUrl;
	}

	public void setParentUrl(String parentUrl) {
		this.parentUrl = parentUrl;
	}

	/**
	 * @return crawl depth at which this Url is first observed. Seed Urls are at
	 *         depth 0. Urls that are extracted from seed Urls are at depth 1,
	 *         etc.
	 */
	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	/**
	 * @return domain of this Url. For 'http://www.example.com/sample.htm',
	 *         domain will be 'example.com'
	 */
	public String getDomain() {
		return domain;
	}

	@Override
	public int hashCode() {
		return url.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}

		WebURL otherUrl = (WebURL) o;
		return (url != null) && url.equals(otherUrl.getURL());

	}

	@Override
	public String toString() {
		return url;
	}
}