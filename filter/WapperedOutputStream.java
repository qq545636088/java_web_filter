package com.changhong.hotpot.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

public class WapperedOutputStream extends ServletOutputStream {
	private ByteArrayOutputStream bos = null;

	public WapperedOutputStream(ByteArrayOutputStream bos) {
		super();
		this.bos = bos;
	}

	@Override
	public boolean isReady() {
		return false;
	}

	@Override
	public void setWriteListener(WriteListener listener) {

	}

	@Override
	public void write(int b) throws IOException {
		bos.write(b);
	}

}
