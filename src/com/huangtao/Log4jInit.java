package com.huangtao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.PropertyConfigurator;

public class Log4jInit extends HttpServlet{
	
	public Log4jInit()
	{
		super();
	}
	
	
	public void init() throws ServletException
	{
		String file = this.getInitParameter("log4j");
		if(file != null)
		{
			PropertyConfigurator.configure(file);
		}
	}

}
