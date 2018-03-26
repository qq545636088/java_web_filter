package com.changhong.hotpot.filter;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

public class AesServletInputStream extends ServletInputStream {
	private InputStream in;

	public AesServletInputStream(InputStream in) {
		this.in = in;
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public boolean isReady() {
		return false;
	}

	@Override
	public void setReadListener(ReadListener listener) {

	}

	@Override
	public int read() throws IOException {
		return this.in.read();
	}
}
