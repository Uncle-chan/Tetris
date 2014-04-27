package cn.edu.shnu.tetris.ui.window;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import cn.edu.shnu.tetris.config.FrameConfig;
import cn.edu.shnu.tetris.config.GameConfig;
import cn.edu.shnu.tetris.config.InnerFrameConfig;
import cn.edu.shnu.tetris.control.GameControler;
import cn.edu.shnu.tetris.control.PlayControler;
import cn.edu.shnu.tetris.dto.GameDto;
import cn.edu.shnu.tetris.service.GameTetris;
import cn.edu.shnu.tetris.ui.Img;
import cn.edu.shnu.tetris.ui.InnerFrame;

public class GamePanel extends JPanel {

	private static final int BTN_SIZE_W=GameConfig.getFrameConfig().getButtonConfig().getButtonW();
	
	private static final int BTN_SIZE_H=GameConfig.getFrameConfig().getButtonConfig().getButtonH();
	
	private List<InnerFrame> inFrames;

	
	private JButton btnStart;
	
	private JButton btnConfig;
	
	private GameControler gameControler=null;
	public GamePanel(GameControler gameControler,GameDto gameDto) {
		//连接游戏控制器
		this.gameControler=gameControler;
		//设置布局管理器为自由布局
		this.setLayout(null);
		//初始化组件
		this.initComponent();
		//初始化内部边框
		this.initInnerFrame(gameDto);
		//按键键盘监听器
		this.addKeyListener(new PlayControler(gameControler));
	}
	/**
	 * 安装游戏控制器
	 * @param controler
	 */
	public void setGameControl(PlayControler controler){
		this.addKeyListener(controler);
	}
	
	/**
	 * 初始化组件
	 */
	private void initComponent(){
		//初始化开始按钮和设置按钮以及位置s
		this.btnStart=new JButton(Img.BTN_START);
		this.btnConfig=new JButton(Img.BTN_CONFIG);
		btnStart.setBounds(GameConfig.getFrameConfig().getButtonConfig().getStartX(), 
				GameConfig.getFrameConfig().getButtonConfig().getStartY(), 
				BTN_SIZE_W, BTN_SIZE_H);
		this.btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				gameControler.start();
			}
		});
		this.add(btnStart);
		btnConfig.setBounds(GameConfig.getFrameConfig().getButtonConfig().getUserConfigX(),
				GameConfig.getFrameConfig().getButtonConfig().getUserConfigY(),
				BTN_SIZE_W, BTN_SIZE_H);
		this.btnConfig.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				gameControler.showUserConfig();
				
			}
		});
		this.add(btnConfig);
	}
	/**
	 * 初始化内部边框,使用反射和xml获得静态配置参数
	 */
	private void initInnerFrame(GameDto dto) {
		
		try {// 获得游戏配置
			FrameConfig fCFG=GameConfig.getFrameConfig();
			// 获得内部边框设置
			List<InnerFrameConfig> ifcs = fCFG.getInnerFramsConfig();
			// 内部边框数组,加入参数提高效率
			inFrames = new ArrayList<InnerFrame>(ifcs.size());
			// 反射
			for (InnerFrameConfig ifc : ifcs) {
				// 获得类对象
				Class<?> cls = Class.forName(ifc.getClassName());
				// 获得构造函数
				Constructor<?> ctr;
				ctr = cls.getConstructor(int.class, int.class, int.class,
						int.class);
				// 调用构造函数创建对象
				InnerFrame innerframe = (InnerFrame) ctr.newInstance(
						ifc.getX(), ifc.getY(), ifc.getW(), ifc.getH());
				//设置数据DTO
				innerframe.setDto(dto);
				// 把生成的innerframe对象放入Innerframe
				inFrames.add(innerframe);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * 绘制游戏界面
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < inFrames.size(); i++) {
			inFrames.get(i).paint(g);
		}
		//返回焦点，使得事件监听生效
		this.requestFocus();
	}
	
	/**
	 * 控制按钮是否可点击
	 */
	public void  buttonSwitch(boolean onOff){
		this.btnConfig.setEnabled(onOff);
		this.btnStart.setEnabled(onOff);
	}
	



}
