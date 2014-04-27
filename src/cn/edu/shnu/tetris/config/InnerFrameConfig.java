package cn.edu.shnu.tetris.config;
/**
 * 封装xml中innerframe元素,既内部边框的属性
 * @author Bill
 *
 */
public class InnerFrameConfig {
	private String className;
	private int x;
	private int y;
	private int w;
	private int h;

	public InnerFrameConfig(String className, int x, int y, int w, int h) {
		this.className = className;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public String getClassName() {
		return className;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}


}
