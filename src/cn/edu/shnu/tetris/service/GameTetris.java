package cn.edu.shnu.tetris.service;

import java.awt.Point;
import java.util.Map;
import java.util.Random;

import cn.edu.shnu.tetris.config.GameConfig;
import cn.edu.shnu.tetris.dto.GameDto;
import cn.edu.shnu.tetris.entity.GameAct;

/**
 * 游戏逻辑层(游戏算法)
 * 
 * @author Bill
 * 
 */
public class GameTetris implements GameService{
	/**
	 * 数据传输对象
	 */
	private GameDto dto;
	/**
	 * 类型随机数生成器
	 */
	private Random random = new Random();
	/**
	 * 方块种类个数
	 */
	private static final int MAX_TYPE = GameConfig.getSystemConfig().getTypeConfig().size()-1;
	
	/**
	 * 升级所需消除的行数
	 */
	private static final int LEVEL_UP=GameConfig.getSystemConfig().getLevelUp();
	/**
	 * 连续消行分数
	 */
	private static final Map<Integer,Integer> PLUS_POINT=GameConfig.getSystemConfig().getPlusPoint();

	public GameTetris(GameDto dto) {
		this.dto = dto;
	}

	/**
	 * 控制方向键上
	 */
	public boolean keyUp() {
		if(this.dto.isPause()){
			return true;
		}
		synchronized (this.dto) {
			this.dto.getGameAct().round(this.dto.getGameMap());
		}
		return true;
	}

	/**
	 * 控制方向键下
	 */
	public boolean keyDown() {
		if(this.dto.isPause()){
			return true;
		}
		synchronized (this.dto) {
		if (this.dto.getGameAct().move(0, 1, this.dto.getGameMap())) {
			// 不满足
			return false;
		}
		// 获得游戏地图对象
		boolean[][] map = this.dto.getGameMap();
		Point[] act = this.dto.getGameAct().getActionPoints();
		for (int i = 0; i < act.length; i++) {
			map[act[i].x][act[i].y] = true;

		}
		//判断消行，并获得经验值
		int plusExp=this.plusExp();
		//如果消行
		if(plusExp>0){
		//增加经验
		this.plusPoint(plusExp);}
		// 消行
		// 算分
		// 是否升级
		// 升级
		// 刷新一个新的方块
		// 创建下一个方块
		//刷新的方块
		this.dto.getGameAct().init(this.dto.getNext());
		// 随机生成下一个方块
		this.dto.setNext(random.nextInt(MAX_TYPE));
		//检查游戏是否失败
		if(this.isLose()){
			this.dto.setStart(false);
		}
	}
		return true;
		
	}



	/**
	 * 检查是否失败(新刷的方块与已有重合则失败)
	 */
	private boolean isLose() {
		Point[] actPoints=this.dto.getGameAct().getActionPoints();
		boolean[][] map=this.dto.getGameMap();
		for (int i = 0; i < actPoints.length; i++) {
			if(map[actPoints[i].x][actPoints[i].y]){
				return true;
			}
		}
		return false;
	}

	/**
	 * 升级操作
	 */
	private void plusPoint(int plusExp) {

		int lv=this.dto.getLevel();
		int rmLine=this.dto.getNowReMoveLine();
		int point=this.dto.getNowPoint();
		if(rmLine%LEVEL_UP+plusExp>=LEVEL_UP){
			this.dto.setLevel(++lv);
		}
		this.dto.setNowReMoveLine(rmLine+plusExp);
		this.dto.setNowPoint(point+PLUS_POINT.get(plusExp));
		
	}

	/**
	 * 消行操作
	 */
	private int plusExp() {
		//获得游戏地图
		boolean[][] map=this.dto.getGameMap();
		int exp=0;
		//逐行扫描是否有可消除行
		for (int y = 0; y < GameDto.GAME_H; y++) {
			//判断是否可消行
			if(this.isCanRemoveLine(y, map)){
				this.removeLine(y,map);
				//增加经验
				exp++;
			}
		}
		return exp;
	}
	/**
	 * 消行处理
	 * @param rowNumber
	 * @param map
	 */
	private void removeLine(int rowNumber, boolean[][] map) {
		for (int x = 0; x < GameDto.GAME_W; x++) {
			for (int y = rowNumber; y > 0 ; y--) {
				map[x][y]=map[x][y-1];
			}
			map[x][0]=false;
		}
	}

	/**
	 * 判断某一行是否可消除
	 * @param y
	 * @return
	 */
	private boolean isCanRemoveLine(int y,boolean[][] map){
		//逐行扫描是否有可消除行
		for (int x = 0; x < GameDto.GAME_W; x++) {
			if(!map[x][y]){
				//如果有一个方块为false则直接跳到下一行
				return false;
			}
		}
		return true;
	}
	/**
	 * 控制方向键左
	 */
	public boolean keyLeft() {
		if(this.dto.isPause()){
			return true;
		}
		synchronized (this.dto) {
		this.dto.getGameAct().move(-1, 0, this.dto.getGameMap());
		}
		return true;
	}

	/**
	 * 控制方向键右
	 */
	public boolean keyRight() {
		if(this.dto.isPause()){
			return true;
		}
		synchronized (this.dto) {
		this.dto.getGameAct().move(1, 0, this.dto.getGameMap());
		}
		return true;
	}


	@Override
	public boolean keyFunUp() {
		//作弊按键
		this.dto.setCheat(true);
		this.plusPoint(4);
		return true;
	}

	@Override
	public boolean keyFunDown() {
		if(this.dto.isPause()){
			return true;
		}
		//快速下落
		while(!this.keyDown());
		return true;
	}

	@Override
	public boolean keyFunLeft() {
		//阴影开关
		this.dto.changeShowShadow();
		return true;
	}

	@Override
	public boolean keyFunRight() {
		if(this.dto.isStart()){
		this.dto.changePause();
		}
		return true;
	}
	
	
	@Override
	public void startGame() {
		//设置随机生成下一个方块
		dto.setNext(random.nextInt(MAX_TYPE));
		//随机生成现在方块
		dto.setGameAct(new GameAct(random.nextInt(MAX_TYPE)));		
		//把游戏状态设为开始
		dto.setStart(true);
		//dto初始化,分数清0
		this.dto.dtoInit();
	}

	@Override
	public void mainAction() {
		this.keyDown();
	}

}
