package cn.edu.shnu.tetris.ui.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import cn.edu.shnu.tetris.control.GameControler;
import cn.edu.shnu.tetris.util.FrameUtil;

public class FrameConfig extends JFrame{
	
	private JButton btnOk=new JButton("确定");
	private JButton btnCancel=new JButton("取消");
	private JButton btnUse=new JButton("应用");
	
	private TextCtrl[] keyText=new TextCtrl[8];
	
	private JLabel errorMsg=new JLabel();
	
	private JList skinList=null;
	
	private JPanel skinView=null;
	
	private DefaultListModel  skinData=new DefaultListModel();
	
	private GameControler gameControler;
	
	private static final Image IMG_PSP=new ImageIcon("data/psp.jpg").getImage();
	
	private static final Image SKIN_DEFAULT=new ImageIcon("graphics/skin/skin.png").getImage();
	
	private final static String[] METHOD_NAMES={
		"keyRight","keyUp","keyLeft","keyDown",
		"keyFunLeft","keyFunUp","keyFunRight","keyFunDown"
	};
	
	private static final String PATH="data/control.dat";
	public FrameConfig(GameControler gameControler) {
		//获得游戏控制器对象
		this.gameControler=gameControler;
		//设置布局管理器为边界布局
		this.setLayout(new BorderLayout());
		this.setTitle("设置");
		//初始化按键输入框
		this.initKeyText();
		//添加主面板
		this.add(creatMainPanel(),BorderLayout.CENTER);
		//添加按钮面板
		this.add(this.creatButtonPanel(),BorderLayout.SOUTH);
		//设置不能改变大小
		this.setResizable(false);
		//设置窗口大小
		this.setSize(650,450);
		//居中
		FrameUtil.setFrameCenter(this);
	
	}

	/**
	 * 初始化按键输入框
	 */
	private void initKeyText() {
		int x=10;
		int y=100;
		int w=45;
		int h=20;
		for (int i = 0; i < 4; i++) {
			keyText[i]=new TextCtrl(x,y,w,h,METHOD_NAMES[i]);
			y+=32;
		}
		x=580;
		y=115;
		for (int i = 4; i < 8; i++) {
			keyText[i]=new TextCtrl(x,y,w,h,METHOD_NAMES[i]);
			y+=27;
		}
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PATH));
			HashMap<Integer, String> cfgSet=(HashMap)ois.readObject();
			ois.close();
			Set<Entry<Integer,String>> entryset=cfgSet.entrySet();
			for(Entry<Integer,String> e:entryset){
				for(TextCtrl tc:keyText){
					
					if(tc.getMethodName().equals(e.getValue())){
						tc.setKeyCode(e.getKey());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	/**
	 *  创建按钮面板
	 * @return
	 */
	private JPanel creatButtonPanel() {
		//创建按钮面板,流式布局
		JPanel jp=new JPanel(new FlowLayout(FlowLayout.RIGHT));
		//给确定按钮增加事件监听
		this.btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(writeConfig()){
					setVisible(false);
					gameControler.setOver();
				}
			}
		});
		this.errorMsg.setForeground(Color.RED);
		jp.add(this.errorMsg);
		jp.add(this.btnOk);
		this.btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				gameControler.setOver();
			}
		});
		jp.add(this.btnCancel);
		this.btnUse.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					writeConfig();
			}
		});
		jp.add(this.btnUse);
		return jp;
	}
	/**
	 * 创建主面板(选项卡面板)
	 * @return
	 */
	private JTabbedPane creatMainPanel() {
		JTabbedPane jtp=new JTabbedPane();
		jtp.addTab("控制设置", this.creaControlPanel());
		jtp.addTab("皮肤设置", this.creatSkinPanel());
		
		
		return jtp;
	}
	/**
	 * 玩家皮肤面板
	 */
	private Component creatSkinPanel() {
		JPanel panel=new JPanel(new BorderLayout());
		
		//TODO添加列表
		this.skinData.addElement("默认");		
		this.skinData.addElement("皮肤1");	
		this.skinData.addElement("皮肤2");	
		this.skinData.addElement("皮肤3");	
		this.skinList=new JList(this.skinData);
		
		this.skinView=new JPanel(){
			@Override
			protected void paintComponent(Graphics g) {
				g.drawImage(SKIN_DEFAULT,0,0,null);
			}
			
		};
		panel.add(new JScrollPane(this.skinList),BorderLayout.WEST);
		panel.add(this.skinView,BorderLayout.CENTER);
		return panel;
	}

	/**
	 * 玩家控制设置面板
	 * @return
	 */
	private JPanel creaControlPanel() {
		JPanel jp=new JPanel(){
			@Override
			protected void paintComponent(Graphics g) {
				g.drawImage(IMG_PSP,0,0,null);
			}
		};
		jp.setLayout(null);
		
		for (int i = 0; i < keyText.length; i++) {
			jp.add(keyText[i]);
		}
		return jp;
	}

	
	/**
	 * 写入游戏配置
	 */
	private boolean writeConfig(){
		HashMap<Integer, String> keySet=new HashMap<Integer, String>();
		for (int i = 0; i < this.keyText.length; i++) {
			int keyCode=this.keyText[i].getKeyCode();
			if(keyCode==0){
				this.errorMsg.setText("错误按键");
				return false;
			}
			keySet.put(keyCode, this.keyText[i].getMethodName());
		}
		if(keySet.size()!=8){
			this.errorMsg.setText("重复按键");
			return false;
		}
		try {
			ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(PATH));
			oos.writeObject(keySet);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
			this.errorMsg.setText(e.getMessage());
			return false;
		} 
		this.errorMsg.setText(null);
		return true;
	}

}
