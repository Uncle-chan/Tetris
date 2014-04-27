package cn.edu.shnu.tetris.ui;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import cn.edu.shnu.tetris.config.GameConfig;

public class Img {
	/**
	 *ABOUT
	 */
	public  static Image ABOUT=new ImageIcon(
			"graphics/string/about.png").getImage();
	

	// 边框图片
	public static  Image INNERFRAME = new ImageIcon(
			"graphics/window/Window.png").getImage();
	
	/**
	 * 数字图片 260 36
	 */
	public  static Image NUMBER=new ImageIcon(
			"graphics/string/num.png").getImage();
	
	/**
	 * 值槽图
	 */
	public static  Image  RECT = new ImageIcon(
			"graphics/window/rect.png").getImage();
	
	
	/**
	 * 等级两个字
	 */
	public static  Image LEVEL = new ImageIcon(
			"graphics/string/level.png").getImage();
	/*
	 * DB
	 */
	public static final Image DB = new ImageIcon("graphics/string/Db.png")
	.getImage();
	/*
	 * Disk
	 */
	public static final Image  DISK = new ImageIcon(
			"graphics/string/disk.png").getImage();
	
	/*
	 * GameInner的图
	 */
	public static final Image  ACT = new ImageIcon("graphics/game/rect.png")
	.getImage();
	
	/*
	 * backgound图
	 */
	public static final Image BK = new ImageIcon(
			"graphics/background/light.jpg").getImage();
	
	/**
	 * 窗口标题（分数）
	 */
	public  static final Image  SCORE = new ImageIcon(
			"graphics/string/score.png").getImage();

	/**
	 * 窗口标题（消行）
	 */
	public static final Image   REMOVE = new ImageIcon(
			"graphics/string/remove.png").getImage();
	
	/**
	 * 阴影（消行）
	 */
	public static final Image   SHADOW = new ImageIcon(
			"graphics/game/shadow.png").getImage();
	/**
	 * 开始按钮
	 */
	public static final ImageIcon BTN_START=new ImageIcon(
			"graphics/string/开始.png");
	/**
	 * 设置按钮
	 */
	public static final ImageIcon BTN_CONFIG=new ImageIcon(
			"graphics/string/设置.png");
	/**
	 * 暂停按钮 
	 */
	public static final Image PAUSE=new ImageIcon("graphics/string/暂停.png").getImage();
	/**
	 * 下一个方块图
	 */
	public static  Image[] NEXT_ACT;
	
	public static List<Image> BG_LIST;
	static {
		//TODO硬编码
		//下一个方块图片
		NEXT_ACT=new Image[GameConfig.getSystemConfig().getTypeConfig().size()];
		for (int i = 0; i < NEXT_ACT.length; i++) {
			NEXT_ACT[i] = new ImageIcon("graphics/game/"+i+".png")
				.getImage();
		}
		//背景图片数组
		File dir=new File("graphics/background");
		File[] files=dir.listFiles();
		BG_LIST=new ArrayList<Image>();
		for (File file:files) {
			if(!file.isDirectory()){
				BG_LIST.add(new ImageIcon(file.getPath()).getImage());
			}
		}
		
	}
	private Img(){
	}
}
