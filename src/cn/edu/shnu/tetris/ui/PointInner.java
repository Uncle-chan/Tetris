package cn.edu.shnu.tetris.ui;

import java.awt.Graphics;

import cn.edu.shnu.tetris.config.GameConfig;


public class PointInner extends InnerFrame {
	/*
	 * 分数的位数上限
	 */
	private  static final int POINT_BIT =5;


	

	/*
	 * 标题高度(消行)
	 */
	private  final static int IMG_REMOVE_H=Img.REMOVE.getHeight(null);
	/**
	 * 升级所需消除的行数
	 */
	private static final int LEVEL_UP=GameConfig.getSystemConfig().getLevelUp();

	
	
	/*
	 * 消行Y坐标
	 */
	private  final int rmLineY;
	/**
	 * 消行X坐标
	 */
	private final int comX;
	/**
	 * 窗口标题高度（分数）
	 */
	private final int pointY;
	/**
	 * 经验值Y坐标
	 */
	private int expY;
	/**
	 * 经验值的宽度
	 */
	private int expW;
	
	
	
	public PointInner(int x, int y, int w, int h) {
		super(x, y, w, h);
		//初始化共通X坐标
		 this.comX=this.w-IMG_NUMBER_W*POINT_BIT-PADDING;
		 //初始化分数显示的Y坐标
		 this.pointY = PADDING;
		//初始化消行的Y坐标
		 this.rmLineY=this.pointY+Img.SCORE.getHeight(null)+PADDING;
		//初始化exp的Y坐标 
		 this.expY=this.rmLineY+Img.REMOVE.getHeight(null)+PADDING;
		 this.expW=this.w-(PADDING<<1);
	}

	@Override
	public void paint(Graphics g) {
		this.creatInnerFrame(g);
		//窗口标题(分数)
		g.drawImage(Img.SCORE,this.x+PADDING, this.y + pointY, null);
		this.drawNumberLeftPad(comX, pointY, this.dto.getNowPoint(), POINT_BIT, g);
		//窗口标题(消行)
		g.drawImage(Img.REMOVE,this.x+PADDING, this.y+rmLineY, null);
		this.drawNumberLeftPad(comX, rmLineY, this.dto.getNowReMoveLine(), POINT_BIT, g);
		//绘制经验值
		int rmLine=this.dto.getNowReMoveLine();
		drawRect(expY,"下一级",null,(double)(rmLine%LEVEL_UP)/(double)LEVEL_UP,g);
		//TOD临时
		
	}


}
