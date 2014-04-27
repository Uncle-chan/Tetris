package cn.edu.shnu.tetris.ui;

import java.awt.Graphics;
import java.awt.Image;
import java.util.List;

import cn.edu.shnu.tetris.config.GameConfig;
import cn.edu.shnu.tetris.dto.Player;

public abstract class DataInner extends InnerFrame{

	/*
	 * 最大数据行
	 */
	private static final int MAX_ROW=GameConfig.getDataConfig().getMaxRow();
	/**
	 * 起始Y坐标
	 */
	private static  int START_Y=0;
	/**
	 * 间距
	 */
	private static int SPA=0;

	private static final int RECT_H=IMG_RECT_H + 4;
	
	public DataInner(int x, int y, int w, int h) {
		super(x, y, w, h);
		SPA=(this.h-(IMG_RECT_H+4)*5-(PADDING<<1)-Img.DB.getHeight(null))/MAX_ROW;
		START_Y=PADDING+Img.DB.getHeight(null)+SPA;	}
	
	@Override
	abstract public void paint(Graphics g) ;
	/**
	 * 绘制所有值槽
	 * @param imgTitle 标题图片
	 * @param players 数据源
	 * @param g 画笔
	 */
	public void showData(Image imgTitle,List<Player> players,Graphics g){
		//绘制标题
		g.drawImage(imgTitle, this.x + PADDING, this.y + PADDING, null);
		//获得现在分数
		int nowPoint = this.dto.getNowPoint();
		//循环绘制记录
		for(int i=0;i<MAX_ROW;i++){
			Player pla=players.get(i);
			int recoderPoint=pla.getPoint();
			double percent=(double)nowPoint/pla.getPoint();
			percent=percent>1?1.0:percent;
			String strPoint=recoderPoint==0?null:Integer.toString(recoderPoint);
			this.drawRect(START_Y+i*(RECT_H+SPA), pla.getName(),strPoint,percent, g);
		}
		
	}
}
