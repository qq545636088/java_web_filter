package com.changhong.hotpot.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.alibaba.druid.util.StringUtils;
import com.changhong.hotpot.SmsUiApplication;
import com.changhong.hotpot.utils.AESUtils;

public class WrapperedResponse extends HttpServletResponseWrapper {

	StringWriter sw = new StringWriter();
	PrintWriter pw = new PrintWriter(sw);
	ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	WapperedOutputStream out = new WapperedOutputStream(buffer);
	private String aesKey;

	public WrapperedResponse(HttpServletResponse response,String aesKey) {
		super(response);
		this.aesKey=aesKey;
	}

	@Override
	public ServletResponse getResponse() {
		return this;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		this.setContentType("text/html;charset=UTF-8");
		return out;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		this.setContentType("text/html;charset=UTF-8");
		return pw;
	}

	public String returnMsg() {
		String returnMsg = sw.toString();
		String requestStr = "";
		if (!StringUtils.isEmpty(returnMsg)) {
			requestStr = returnMsg;
		} else {
			requestStr = buffer.toString();
		}
		return StringUtils.isEmpty(requestStr) ? "" : AESUtils.aesEncryp(requestStr, aesKey);
	}
}
