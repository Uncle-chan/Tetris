package cn.edu.shnu.tetris.ui;

import java.awt.Graphics;


public class DiskInner extends DataInner {

	

	public DiskInner(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	@Override
	public void paint(Graphics g) {
		this.creatInnerFrame(g);//Img.DISK
		this.showData(Img.DISK, this.dto.getLocalRecoder(), g);
	}
}
