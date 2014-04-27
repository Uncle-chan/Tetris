package cn.edu.shnu.tetris.control;


import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import cn.edu.shnu.tetris.config.DataInterfaceConfig;
import cn.edu.shnu.tetris.config.GameConfig;
import cn.edu.shnu.tetris.dao.Data;
import cn.edu.shnu.tetris.dto.GameDto;
import cn.edu.shnu.tetris.dto.Player;
import cn.edu.shnu.tetris.service.GameService;
import cn.edu.shnu.tetris.service.GameTetris;
import cn.edu.shnu.tetris.ui.window.FrameConfig;
import cn.edu.shnu.tetris.ui.window.GameFrame;
import cn.edu.shnu.tetris.ui.window.GamePanel;
import cn.edu.shnu.tetris.ui.window.JFrameSavePoint;
import cn.edu.shnu.tetris.util.GameFunction;

/**
 * 接受玩家键盘事件
 * 控制画面
 * 控制游戏逻辑
 * 由此类来决定游戏数据转向
 * @author Bill
 *
 */
public class GameControler {
	
	/*
	 * 数据访问接口A
	 */
	private Data dataA;
	/*
	 * 数据访问接口B
	 */
	private Data dataB;
	/*
	 * 游戏逻辑层
	 */
	private GameService gameService;
	/*
	 * 游戏界面层
	 */
	private GamePanel gamePanel;
	/*
	 * 游戏设置窗口
	 */
	private FrameConfig framConfig;
	/*
	 * 保存分数窗口
	 */
	private JFrameSavePoint framSavePoint;

	/*
	 * 游戏行为控制
	 */
	private Map<Integer,Method> actionList;
	
	/*
	 * 游戏下落方块线程
	 */
	private Thread gameThread=null;
	
	/*
	 * 游戏数据源
	 */
	private GameDto dto=null;
	
	public GameControler() {
		// 创建游戏DTO
		this.dto = new GameDto();
		// 创建游戏逻辑
		this.gameService = new GameTetris(dto);
		//从数据接口A获得数据库记录
		this.dataA = creatDataObject(GameConfig.getDataConfig().getDataA());		
		//设置数据库记录到游戏
		this.dto.setDbRecoder(dataA.loadData());
		//从数据接口B获得数据记录
		this.dataB = creatDataObject(GameConfig.getDataConfig().getDataB());
		//设置本地硬盘记录到游戏
		this.dto.setLocalRecoder(dataB.loadData());
		// 创建游戏面板
		this.gamePanel= new GamePanel(this,dto);
		//读取用户控制设置
		this.setControlConfig();
		//初始化用户配置窗口
		this.framConfig=new FrameConfig(this);
		//保存分数窗口
		this.framSavePoint=new JFrameSavePoint(this);
		//初始化游戏主窗口,安装游戏面板
		new GameFrame(this.gamePanel);
		
	}
	/**
	 * 读取用户控制设置
	 */
	private void setControlConfig(){
		//创建键盘与方法名的映射数组
		this.actionList=new HashMap<Integer, Method>();
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/control.dat"));
			HashMap<Integer, String> cfgSet=(HashMap)ois.readObject();
			Set<Entry<Integer,String>> entryset=cfgSet.entrySet();
			for(Entry<Integer,String> e:entryset){
				actionList.put(e.getKey(), this.gameService.getClass().getMethod(e.getValue()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

	/**
	 * 创建数据对象
	 * @param cfg
	 * @return
	 */
	private Data creatDataObject(DataInterfaceConfig cfg) {
		try {
			//获得类对象
			Class<?> cls=Class.forName(cfg.getClassName());
			//获得构造器
			Constructor<?> ctr=cls.getConstructor(HashMap.class);
			//创建对象
			return (Data) ctr.newInstance(cfg.getParam());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		
	}
	/**
	 * 更具玩家控制来行为
	 * @param keyCode
	 */
	public void actionByKeyCode(int keyCode) {
		try {
			if(this.actionList.containsKey(keyCode)){
			this.actionList.get(keyCode).invoke(this.gameService);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		this.gamePanel.repaint();
	}
	
	/**
	 * 显示玩家控制窗口
	 */
	public void showUserConfig() {
		this.framConfig.setVisible(true);
	}
	/**
	 * 子窗口关闭事件
	 */
	public void setOver() {
		this.gamePanel.repaint();
		this.setControlConfig();
	}
	
	/**
	 * 开始按钮事件
	 */
	public void start() {
		//面板按钮设置为不可点击
		this.gamePanel.buttonSwitch(false);
		//关闭窗口
		this.framConfig.setVisible(false);
		this.framSavePoint.setVisible(false);

		//游戏数据初始化
		this.gameService.startGame();
		//创建线程对象
		this.gameThread=new MainThread();
		this.gameThread.start();
		//刷新画面
		this.gamePanel.repaint();
	}
	/**
	 * 失败之后处理
	 */
	public void afterLose(){
		if(!this.dto.isCheat()){
		//显示保存得分窗口
		this.framSavePoint.show(this.dto.getNowPoint());
		}
		//使按钮可以点击
		this.gamePanel.buttonSwitch(true);
		
	}
	private class MainThread extends Thread{
		
			@Override
			public void run(){
				gamePanel.repaint();
				while(dto.isStart()){
					try {
						//线程睡眠
						Thread.sleep(dto.getSleepTime());
						//如果暂停，那么不执行主行为
						if(dto.isPause()){
							continue;
						}
						//方块下落(主行为)
						gameService.mainAction();
						gamePanel.repaint();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				afterLose();
				}
		}
	
	/*
	 * 保存名字
	 */
	public void savePoint(String name) {
		Player pla=new Player(name, this.dto.getNowPoint());
		//保存记录到数据库
		this.dataA.saveData(pla);
		//保存数据到本地磁盘
		this.dataB.saveData(pla);
		//设置数据库记录到游戏
		this.dto.setDbRecoder(dataA.loadData());
		//设置磁盘记录到游戏
		this.dto.setDbRecoder(dataB.loadData());
		this.gamePanel.repaint();
	}
	
	


	
}
