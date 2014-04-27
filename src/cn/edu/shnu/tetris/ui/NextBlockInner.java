package cn.edu.shnu.tetris.ui;

import java.awt.Graphics;


public class NextBlockInner extends InnerFrame{

	
	public NextBlockInner(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	@Override
	public void paint(Graphics g){
		this.creatInnerFrame(g);
		//如果是开始状态才绘制下一个方块
		if(this.dto.isStart()){
		this.drawImageOfAtCenter(Img.NEXT_ACT[this.dto.getNext()], g);
		}
	}
	
}
