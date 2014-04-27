package cn.edu.shnu.tetris.config;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

public class FrameConfig {
	
	private final String title;
	
	private final int windowUp;
	
	private final int width;
	
	private final int height;
	
	private final int padding;
	
	private final int border;
	
	private final int sizeRol;

	private final int loseIdx;
	
	/*
	 * 内部边框
	 */
	private List<InnerFrameConfig> innerFramsConfig;
	
	/**
	 * 按钮属性
	 */
	private final ButtonConfig buttonConfig;
	
	
	public FrameConfig(Element frame) {
		this.title=frame.attributeValue("title");
		//获取窗口宽度
		this.width = Integer.parseInt(frame.attributeValue("width"));
		//获取窗口高度
		this.height = Integer.parseInt(frame.attributeValue("height"));
		//获取边框粗细
		this.border = Integer.parseInt(frame.attributeValue("border"));
		//获取边框内边距
		this.padding = Integer.parseInt(frame.attributeValue("padding"));
		//获取窗口拔高
		this.windowUp = Integer.parseInt(frame.attributeValue("windowUp"));
		//图块尺寸
		this.sizeRol = Integer.parseInt(frame.attributeValue("sizeRol"));
		//失败图片号
		this.loseIdx = Integer.parseInt(frame.attributeValue("loseIdx"));
		innerFramsConfig = new ArrayList<InnerFrameConfig>();
		// innnerframe的值赋值
		@SuppressWarnings("unchecked")
		List<Element> innerframs = frame.elements("innerframe");
		
		for (Element innerframe : innerframs) {
			InnerFrameConfig temp = new InnerFrameConfig(
					innerframe.attributeValue("class"),
					Integer.parseInt((innerframe.attributeValue("x"))),
					Integer.parseInt((innerframe.attributeValue("y"))),
					Integer.parseInt((innerframe.attributeValue("w"))),
					Integer.parseInt((innerframe.attributeValue("h"))));
			innerFramsConfig.add(temp);
		}	
		//初始化按钮属性
		buttonConfig=new ButtonConfig(frame.element("button"));
	}


	public String getTitle() {
		return title;
	}


	public int getWindowUp() {
		return windowUp;
	}


	public int getWidth() {
		return width;
	}


	public int getHeight() {
		return height;
	}


	public int getPadding() {
		return padding;
	}


	public int getBorder() {
		return border;
	}


	public int getSizeRol() {
		return sizeRol;
	}


	public int getLoseIdx() {
		return loseIdx;
	}


	public List<InnerFrameConfig> getInnerFramsConfig() {
		return innerFramsConfig;
	}


	public ButtonConfig getButtonConfig() {
		return buttonConfig;
	}
	
	
}
