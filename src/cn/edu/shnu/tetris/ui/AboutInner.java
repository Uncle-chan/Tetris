package cn.edu.shnu.tetris.ui;

import java.awt.Graphics;


public class AboutInner extends InnerFrame{
	
	
	public  AboutInner(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	@Override
	public void paint(Graphics g) {
		this.creatInnerFrame(g);
		this.drawImageOfAtCenter(Img.ABOUT, g);
	}

}
