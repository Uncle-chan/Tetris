package cn.edu.shnu.tetris.ui.window;


import javax.swing.JFrame;

import cn.edu.shnu.tetris.config.FrameConfig;
import cn.edu.shnu.tetris.config.GameConfig;
import cn.edu.shnu.tetris.util.FrameUtil;

public class GameFrame extends JFrame {
	public GameFrame(GamePanel gamePanel)  {
		FrameConfig fCFG=GameConfig.getFrameConfig();
		// 设置标题
		this.setTitle(fCFG.getTitle());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(fCFG.getWidth(), fCFG.getHeight());
		// 静止放大缩小窗口
		this.setResizable(false);
		// 居中
		FrameUtil.setFrameCenter(this);
		//设置Panel
		this.setContentPane(gamePanel);
		this.setVisible(true);
	}
}
