package com.changhong.hotpot.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.changhong.hotpot.utils.AESUtils;

public class WrapperedRequest extends HttpServletRequestWrapper {

	private String aesKey;

	public WrapperedRequest(HttpServletRequest request, String aesKey) {
		super(request);
		this.aesKey = aesKey;

	}

	@Override
	public String getQueryString() {
		String queryString = super.getQueryString();
		String requestStr = AESUtils.aesDecrypt(queryString, aesKey);
		return requestStr;
	}

	@Override
	public String getParameter(String name) {
		// TODO Auto-generated method stub
		return super.getParameter(name);
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		// TODO Auto-generated method stub
		return super.getParameterMap();
	}

	@Override
	public Enumeration<String> getParameterNames() {
		// TODO Auto-generated method stub
		return super.getParameterNames();
	}

	@Override
	public String[] getParameterValues(String name) {
		String str = getQueryString();
		String params[] = str.split("&");
		for (String nvs : params) {
			String nv[] = nvs.split("=");
			if (name.equals(nv[0])) {
				return new String[] { nv[1] };
			}
		}
		return super.getParameterValues(name);
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(super.getInputStream(), "UTF-8"))) {
			StringBuilder str = new StringBuilder();
			char b[] = new char[1024];
			int len = -1;
			while ((len = bufferedReader.read(b)) != -1) {
				if (str.length() > 100000) {
					return null;
				}
				str.append(b);
			}
			String requestStr = AESUtils.aesDecrypt(str.toString(), aesKey);
			return new AesServletInputStream(new ByteArrayInputStream(requestStr.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
