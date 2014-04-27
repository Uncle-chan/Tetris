package cn.edu.shnu.tetris.util;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;


public class FrameUtil {

	/**
	 * 窗口居中
	 * @param jf
	 */
	public static void setFrameCenter(JFrame  jf){
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension din = toolkit.getScreenSize();
		int x = (din.width - jf.getWidth()) / 2;
		int y = (din.height - jf.getHeight()) / 2 -32;
		jf.setLocation(x, y);

		
	}
}
