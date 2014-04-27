package cn.edu.shnu.tetris.ui;

import java.awt.Graphics;


/**
 * 背景
 * 
 * @author Bill
 * 
 */
public class BackgroundInner extends InnerFrame {
	// TODO

	public BackgroundInner(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	@Override
	public void paint(Graphics g) {
		int bgIdx=this.dto.getLevel()%Img.BG_LIST.size();
		g.drawImage(Img.BG_LIST.get(bgIdx), 0, 0, 1195, 675, null);
	}
}
