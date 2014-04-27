package cn.edu.shnu.tetris.ui;

import java.awt.Graphics;

public class ButtonInner extends InnerFrame {

	public ButtonInner(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	@Override
	public void paint(Graphics g){
		this.creatInnerFrame(g);
	}

}
