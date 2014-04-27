package cn.edu.shnu.tetris.ui;

import java.awt.Graphics;


public class LevelInner extends InnerFrame {


	/**
	 * 等级两字的宽度
	 */
	private static final int IMG_LEVEL_W=Img.LEVEL.getWidth(null);
	
	
	public LevelInner(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	@Override
	public void paint(Graphics g) {
		this.creatInnerFrame(g);
		//窗口标题（等级2字）
		int centerX=(this.w-IMG_LEVEL_W>>1);
		g.drawImage(Img.LEVEL,this.x+centerX, this.y + PADDING, null);
		//显示等级
		this.drawNumberLeftPad(centerX,64,this.dto.getLevel(),2,g);
	}
	

}
