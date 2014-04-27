package cn.edu.shnu.tetris.ui.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cn.edu.shnu.tetris.control.GameControler;
import cn.edu.shnu.tetris.util.FrameUtil;

public class JFrameSavePoint extends JFrame{

	private JTextField txName=null;
	private JLabel  lbpont=null;
	private JButton btnOk=null;
	private JLabel errMsg=null;
	
	private GameControler gameControl=null;
	
	public JFrameSavePoint(GameControler gameControl){
		this.gameControl=gameControl;
		this.setTitle("保存记录");
		this.setSize(256,128);
		FrameUtil.setFrameCenter(this);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.creatCom();
		this.creatAction();
	}
	/*
	 * 显示窗口
	 */
	public void show(int point){
		this.lbpont.setText("您的得分:"+point);
		this.setVisible(true);
	}
	/*
	 * 创建事件监听
	 */
	private void creatAction() {
		this.btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String name=txName.getText();
				if(name.length()>16||(name.equals("")||name==null)){
					errMsg.setText("请输入16位以下名字");
				}
				else{
					setVisible(false);
					gameControl.savePoint(name);
				}
			}
		});
	}
	
	/*
	 * 初始化控件
	 */
	private void creatCom(){
		JPanel north=new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.lbpont=new JLabel("");
		
		north.add(lbpont);
		//创建错误信息控件
		this.errMsg=new JLabel();
		this.errMsg.setForeground(Color.red);
		//添加错误到北部面板
		north.add(this.errMsg);
		this.add(north,BorderLayout.NORTH);
		
		JPanel center=new JPanel(new FlowLayout(FlowLayout.LEFT));
		//创建文本框
		this.txName=new JTextField(10);
		//设置最大长度
		center.add(new JLabel("您的名字"));
		center.add(this.txName);
		this.add(center,BorderLayout.CENTER);
		
		
		//创建确定按钮
		this.btnOk=new JButton("确定");
		//创建南部面板（流式布局）
		JPanel south=new JPanel(new FlowLayout(FlowLayout.CENTER));
		//按钮添加到南部面板
		south.add(btnOk);
		//南部面板添加进主面板
		this.add(south,BorderLayout.SOUTH);
	}

}
