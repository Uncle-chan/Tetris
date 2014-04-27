package cn.edu.shnu.tetris.ui;

import java.awt.Graphics;

public class DbInner extends DataInner {



	
	public DbInner(int x, int y, int w, int h) {
		super(x, y, w, h);
	
	}

	@Override
	public void paint(Graphics g) {
		this.creatInnerFrame(g);//Img.DISK
		this.showData(Img.DB, this.dto.getDbRecoder(), g);
	}
}
