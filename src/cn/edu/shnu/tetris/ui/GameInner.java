package cn.edu.shnu.tetris.ui;

import java.awt.Graphics;
import java.awt.Point;

import cn.edu.shnu.tetris.config.GameConfig;
import cn.edu.shnu.tetris.entity.GameAct;


public class GameInner extends InnerFrame {

	
	/**
	 * 左移量
	 */
	private static final int SIZE_ROL=GameConfig.getFrameConfig().getSizeRol();
	
	private static final int LEFT_SIDE=0;
	
	private static final int RIGHT_SIDE=GameConfig.getSystemConfig().getMaxX();
	
	private static final int Lose_IDX=GameConfig.getFrameConfig().getLoseIdx();

	public GameInner(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	@Override
	public void paint(Graphics g) {
		this.creatInnerFrame(g);
		//获得方块对象
		GameAct act=this.dto.getGameAct();
		if(act!=null){
		Point[] points=this.dto.getGameAct().getActionPoints();
		//绘制阴影
		this.drawShadow(points,g);
		//绘制活动方块
		this.drawMainAct(points,g);
		}
		//绘制游戏地图
		this.drawMap(g);
		//暂停绘制
		if(this.dto.isPause()){
			this.drawImageOfAtCenter(Img.PAUSE, g);
		}
	}
	
	/**
	 * 绘制地图
	 * @param g
	 */
	private void drawMap(Graphics g) {
		//打印地图(白框)
		boolean[][] map=this.dto.getGameMap();
		//计算当前图片（累计的方块）的颜色
		int lv=this.dto.getLevel();
		int imageIdx=lv==0?0:(lv-1)%7+1;
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[x].length; y++) {
				if(map[x][y]){
					drawActPoint( x, y,imageIdx, g);
				}
			}
		}
	}
	/**
	 * 绘制活动方块
	 * @param g
	 */
	private void drawMainAct(Point[] points,Graphics g) {
		//获得方块类型编码
		int typeCode=this.dto.getGameAct().getTypeCode();

		Point[] ponits = this.dto.getGameAct().getActionPoints();
		//绘制方块
		for (int i = 0; i < ponits.length; i++) {
			 drawActPoint(ponits[i].x,ponits[i].y, typeCode+1 , g);
		}		
	}

	/**
	 * 绘制阴影
	 * @param points
	 * @param isShowShadow
	 * @param Graphics g
	 */
	private void drawShadow(Point[] points,Graphics g) {
		if(!this.dto.isShowShadow()){
			return;
		}
		//TODO硬编码
		int leftX=RIGHT_SIDE;
		int rightX=LEFT_SIDE;
		for (Point p:points) {
			leftX=p.x<leftX?p.x:leftX;
			rightX=p.x>rightX?p.x:rightX;
		}
		g.drawImage(Img.SHADOW,this.x+BORDER+(leftX<<SIZE_ROL), this.y+BORDER, (rightX-leftX+1)<<SIZE_ROL, this.h-(BORDER<<1),null);
	}

	/**
	 * 绘制正方形块
	 * @param x
	 * @param y
	 * @param imgIdx
	 * @param g
	 */
	private void drawActPoint(int x,int y, int imgIdx,Graphics g){
		imgIdx=this.dto.isStart()?imgIdx:Lose_IDX;
		g.drawImage(Img.ACT,
		this.x + (x <<SIZE_ROL)+BORDER, 
		this.y+  (y <<SIZE_ROL)+BORDER, 
		this.x + (x + 1 <<SIZE_ROL)+BORDER,
	    this.y + (y + 1 <<SIZE_ROL)+BORDER,
	    imgIdx<<SIZE_ROL,0, (imgIdx+1)<<SIZE_ROL,1<<SIZE_ROL,null);
		
	}
}
