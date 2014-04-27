package cn.edu.shnu.tetris.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.edu.shnu.tetris.config.GameConfig;
import cn.edu.shnu.tetris.entity.GameAct;
import cn.edu.shnu.tetris.util.GameFunction;

/**
 * 数据传输对象DTO，用于游戏逻辑和画面的交互数据传输
 * 
 * @author Bill
 * 
 */
public class GameDto {
	/*
	 * 游戏宽度
	 */
	public static final int GAME_W = GameConfig.getSystemConfig().getMaxX() + 1;
	/*
	 * 游戏高度
	 */
	public static final int GAME_H = GameConfig.getSystemConfig().getMaxY() + 1;

	/*
	 * 数据库
	 */
	private List<Player> dbRecoder;
	/*
	 * 本地记录
	 */
	private List<Player> localRecoder;
	/*
	 * 10*18方块组
	 */
	private boolean[][] gameMap;
	/*
	 * 下落方块
	 */
	private GameAct gameAct;
	/*
	 * 下一个方块
	 */
	private int next;
	/*
	 * 等级
	 */
	private int nowLevel;
	/*
	 * 现在分数
	 */
	private int nowPoint;
	/*
	 * 消行
	 */
	private int nowRemoveLine;

	/*
	 * 游戏是否是开始这状态
	 */
	private boolean start;
	/*
	 * 是否显示阴影
	 */
	private boolean showShadow;

	/*
	 * 暂停
	 */
	private boolean pause;
	/*
	 * 是否使用作弊按键
	 */
	private boolean cheat;
	/*
	 * 线程等待时间
	 */
	private long sleepTime;

	/*
	 * 构造函数
	 */
	public GameDto() {
		dtoInit();
	}

	/*
	 * dto初始化
	 */
	public void dtoInit() {
		// TODO硬编码
		this.gameMap = new boolean[GAME_W][GAME_H];
		// 初始化所有游戏对象
		this.nowLevel=1;
		this.nowPoint=0;
		this.nowRemoveLine=0;
		this.pause=false;
		this.cheat=false;
		this.sleepTime=GameFunction.getSleeepTimmeByLevel(this.nowLevel);
	}

	public List<Player> getDbRecoder() {
		return dbRecoder;
	}

	public void setDbRecoder(List<Player> dbRecoder) {
		this.dbRecoder = setFillRecoder(dbRecoder);
	}

	public List<Player> getLocalRecoder() {
		return localRecoder;
	}

	public void setLocalRecoder(List<Player> localRecoder) {
		this.localRecoder = setFillRecoder(localRecoder);
	}

	private List<Player> setFillRecoder(List<Player> players) {
		// 如果进来的是空，那么创建
		if (players == null) {
			players = new ArrayList<Player>();
		}
		// 如果记录数小于5则加到5条记录
		while (players.size() < 5) {
			players.add(new Player("No Data", 0));
		}
		// 排序之前player已经实现了Comparable接口所以没有报错
		Collections.sort(players);
		return players;
	}

	public boolean[][] getGameMap() {
		return gameMap;
	}

	public void setGameMap(boolean[][] gameMap) {
		this.gameMap = gameMap;
	}

	public GameAct getGameAct() {
		return gameAct;
	}

	public void setGameAct(GameAct gameAct) {
		this.gameAct = gameAct;
	}

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}

	public int getLevel() {
		return nowLevel;
	}

	public void setLevel(int level) {
		this.nowLevel = level;
		//计算线程睡眠时间(下落速度)
		this.sleepTime=GameFunction.getSleeepTimmeByLevel(this.nowLevel);
	}

	public int getNowPoint() {
		return nowPoint;
	}

	public void setNowPoint(int nowPoint) {
		this.nowPoint = nowPoint;
	}

	public int getNowReMoveLine() {
		return nowRemoveLine;
	}

	public void setNowReMoveLine(int nowNowRemoveLine) {
		this.nowRemoveLine = nowNowRemoveLine;
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public boolean isShowShadow() {
		return showShadow;
	}

	public void changeShowShadow() {
		this.showShadow = !this.showShadow;
	}

	public boolean isPause() {
		return pause;
	}

	public void changePause() {
			this.pause = !pause;
	}

	public boolean isCheat() {
		return cheat;
	}

	public void setCheat(boolean cheat) {
		this.cheat = cheat;
	}

	public long getSleepTime() {
		return sleepTime;
	}

}
