package net.zhuoweizhang.goom;

import java.nio.*;

public final class Goom {

	private long pluginInfoPointer;

	private Goom(long pluginInfoPointer) {
		this.pluginInfoPointer = pluginInfoPointer;
	}

	public void setResolution(int resx, int resy) {
		nativeSetResolution(pluginInfoPointer, resx, resy);
	}

	/*
	 * @param data audio data, 2 x 512 shorts
	 */

	public void update(short[] data, int forceMode, float fps, String songTitle, String message) {
		nativeGoomUpdate(pluginInfoPointer, data, forceMode, fps, songTitle, message);
	}

	/*
	 * @param buffer a ByteBuffer holding pixel data - 4 bytes per pixel, so resx * resy * 4
	 */

	public void setScreenbuffer(ByteBuffer buffer) {
		nativeGoomSetScreenbuffer(pluginInfoPointer, buffer);
	}

	public void close() {
		nativeGoomClose(pluginInfoPointer);
	}

	public static Goom goomInit(int resx, int resy) {
		long pointer = nativeGoomInit(resx, resy);
		return new Goom(pointer);
	}

	private static native long nativeGoomInit(int resx, int resy);
	private static native void nativeSetResolution(long pluginInfo, int resx, int resy);
	private static native int nativeGoomUpdate(long pluginInfo, short[] data, int forceMode, float fps, String songTitle, String message);
	private static native int nativeGoomSetScreenbuffer(long pluginInfo, ByteBuffer buffer);
	private static native void nativeGoomClose(long pluginInfo);

	static {
		System.loadLibrary("androidgoom");
	}
}

		
