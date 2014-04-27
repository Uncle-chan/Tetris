package cn.edu.shnu.tetris.service;

import java.util.List;

import cn.edu.shnu.tetris.dto.Player;

public interface GameService {
	
	public boolean keyUp();
	
	public boolean keyDown();
	
	public boolean keyLeft();
	
	public boolean keyRight();
	/*
	 * 三角按键
	 */
	public boolean keyFunUp();
	/*
	 * X按键
	 */
	public boolean keyFunDown();
	/*
	 * 方块按键
	 */
	public boolean keyFunLeft();
	/*
	 * 圆按键
	 */
	public boolean keyFunRight();
	/**
	 * 启动主线程开始游戏
	 */
	public void startGame();
	/**
	 * 游戏主要行为
	 */
	public void mainAction();


}
