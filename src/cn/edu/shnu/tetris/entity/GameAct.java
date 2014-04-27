package cn.edu.shnu.tetris.entity;

import java.awt.Point;
import java.util.List;

import cn.edu.shnu.tetris.config.GameConfig;

/**
 * 活动块类
 * 
 * @author Bill
 * 
 */
public class GameAct {
	/*
	 * 方块数组
	 */
	private Point[] actPionts=null;

	/*
	 * 方块编号
	 */
	private int typeCode;
	private final static int MIN_X = GameConfig.getSystemConfig().getMinX();
	private final static int MIN_Y = GameConfig.getSystemConfig().getMinY();
	private final static int MAX_X = GameConfig.getSystemConfig().getMaxX();
	private final static int MAX_Y = GameConfig.getSystemConfig().getMaxY();
	
	private final static List<Point[]> TYPE_CONFIG=GameConfig.getSystemConfig().getTypeConfig(); 

	private final static List<Boolean> TYPE_ROUND=GameConfig.getSystemConfig().getTypeRound(); 
	
	public GameAct(int typeCode) {
		this.init(typeCode);
		//TODO配置文件
	}
/**
 * actCode:方块的种类
 * @param actCode
 */
	public void init(int typeCode) {
		this.typeCode=typeCode;
		//生成新对象
		Point[] points=TYPE_CONFIG.get(typeCode);
		actPionts=new Point[points.length];
		for (int i = 0; i < points.length; i++) {
			actPionts[i]=new Point(points[i].x,points[i].y);
		}
	}
	
	
	public Point[] getActionPoints() {
		return actPionts;
	}


	/**
	 * 方块移动,gameMap布尔数组判断是否层叠
	 * @param moveX
	 * @param moveY
	 * @param gameMap
	 * @return
	 */
	public boolean move(int moveX, int moveY,boolean[][] gameMap) {
		for (int i = 0; i < actPionts.length; i++) {
			int newX = actPionts[i].x + moveX;
			int newY = actPionts[i].y + moveY;
			if (isOverZone(newX, newY,gameMap)) {
				return false;
			}
		}
		for (int i = 0 ;i < actPionts.length; i++) {
			actPionts[i].x += moveX;
			actPionts[i].y += moveY;
		}
		return true;
	}

	/**
	 * 方块旋转,gameMap布尔数组判断是否层叠
	 * 
	 * 顺时针 笛卡尔坐标系旋转公式: A.x=O.y+O.x-B.y A.y=O.y-O.x-B.x
	 */
	public void round(boolean[][] gameMap) {
		//正方形不转
		if(!TYPE_ROUND.get(this.typeCode)){
			return;
		}
		for (int i = 1; i < actPionts.length; i++) {
			int newX = actPionts[0].y + actPionts[0].x - actPionts[i].y;
			int newY = actPionts[0].y - actPionts[0].x + actPionts[i].x;
			if (isOverZone(newX, newY,gameMap)) {
				return;
			}
		}
		for (int i = 1; i < actPionts.length; i++) {
			int newX = actPionts[0].y + actPionts[0].x - actPionts[i].y;
			int newY = actPionts[0].y - actPionts[0].x + actPionts[i].x;
			actPionts[i].x = newX;
			actPionts[i].y = newY;
		}

	}

	
	/**
	 * 判断是否超出边界,gameMap布尔数组判断是否层叠
	 * @param x
	 * @param y
	 * @param gameMap
	 * @return
	 */
	private boolean isOverZone(int x, int y,boolean[][] gameMap) {
		return x < MIN_X || x > MAX_X || y < MIN_Y || y > MAX_Y||gameMap[x][y];
	}
	
	/*
	 * 获得方块编号
	 */
	public int getTypeCode() {
		return typeCode;
	}
}
