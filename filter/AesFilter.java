package com.changhong.hotpot.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@WebFilter(filterName = "aesFilter", urlPatterns = "/*")
public class AesFilter implements Filter {
	private Logger log = LoggerFactory.getLogger(AesFilter.class);

	@Autowired
	private String getAesKey;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("-----------filter加/解密方式启动---------------");

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		WrapperedResponse wrapperedResponse = new WrapperedResponse((HttpServletResponse) response,getAesKey);
		HttpServletRequest req = (HttpServletRequest) request;
		if (req.getRequestURI().startsWith("/v1/mobile/file/") || req.getRequestURI().startsWith("/v1/mobile/equpment/upload")) {
				chain.doFilter(request, response);
		} else {
			chain.doFilter(new WrapperedRequest((HttpServletRequest) request,getAesKey), wrapperedResponse);
			try (PrintWriter printWriter = response.getWriter()) {
				printWriter.print(wrapperedResponse.returnMsg());
				printWriter.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void destroy() {
		log.info("-----------filter加/解密方式销毁---------------");
	}

}
