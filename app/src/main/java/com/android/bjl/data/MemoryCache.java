package com.android.bjl.data;
/**
 * 
 * @author wudongze 
 * 
 * @Package com.example.data
 * 
 * @ClassName: MemoryCache   
 * 
 * @description 缓存
 * 
 * @date 2016-3-24 下午6:17:07
 */
public class MemoryCache {

	private static MemoryCache sInstance;
	private String example;
	private boolean mersacn;
	private MemoryCache() {
	}

	public static void init() {
		sInstance = new MemoryCache();
	}

	public static MemoryCache getInstance() {
		return sInstance;
	}

	public String getExample(){
		return example;
	}
	public void setExample(String example){
		this.example = example;
	}

	public boolean getMersacn(){
		return mersacn;
	}
	public void setMersacn(boolean mersacn){
		this.mersacn = mersacn;
	}
}

