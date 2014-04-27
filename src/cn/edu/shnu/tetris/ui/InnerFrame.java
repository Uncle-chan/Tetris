package cn.edu.shnu.tetris.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;





import cn.edu.shnu.tetris.config.FrameConfig;
import cn.edu.shnu.tetris.config.GameConfig;
import cn.edu.shnu.tetris.dto.GameDto;

/**
 * Description:内部边框图具体画法的抽象类
 * 
 * @author Bill
 * 
 */
public abstract class InnerFrame {
	protected static final int PADDING;
	protected static final int BORDER;
	
	static {
		//获得界面配置
		FrameConfig fCFG=GameConfig.getFrameConfig();
		// 字体与边框间隔
		PADDING=fCFG.getPadding();
		// 边框角长度
		BORDER=fCFG.getBorder();
	}

	// 取图片长宽
	private static final int imgWidth = Img.INNERFRAME.getWidth(null);
	private static final int imgHeight = Img.INNERFRAME.getHeight(null);

	
	/**
	 * 数字切片的宽度
	 */
	protected static int IMG_NUMBER_W=Img.NUMBER.getWidth(null)/10;
	/**
	 * 数字切片的高度
	 */
	private static int IMG_NUMBER_H=Img.NUMBER.getHeight(null);
	
	
	// 起始坐标
	protected int x;
	protected int y;
	// 需要的长宽
	protected int w;
	protected int h;
	//游戏数据
	protected GameDto dto=null;
	
	
	
	/**
	 * 值槽图的高度 
	 */
	protected static final int IMG_RECT_H=Img.RECT.getHeight(null);
	/**
	 * 值槽图的宽度
	 */
	private static final int IMG_RECT_W=Img.RECT.getWidth(null);
	/*
	 * 值槽字体
	 */
	private static final Font DEF_FONT=new Font("黑体", Font.BOLD, 20);
	/*
	 * 值槽宽度
	 */
	private final int rectW;
	
	
	
	
	protected InnerFrame(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.rectW=this.w-(PADDING<<1);
	}

	/**
	 * 绘制内部边框
	 */
	protected void creatInnerFrame(Graphics g) {
		// 左上
		g.drawImage(Img.INNERFRAME, x, y, x + BORDER, y + BORDER, 0, 0, BORDER, BORDER,
				null);
		// 中上
		g.drawImage(Img.INNERFRAME, x + BORDER, y, x + w - BORDER, y + BORDER, BORDER,
				0, imgWidth - BORDER, BORDER, null);
		// 右上
		g.drawImage(Img.INNERFRAME, x + w - BORDER, y, x + w, y + BORDER, imgWidth
				- BORDER, 0, imgWidth, BORDER, null);
		// 左中
		g.drawImage(Img.INNERFRAME, x, y + BORDER, x + BORDER, y + h - BORDER, 0,
				BORDER, BORDER, imgHeight - BORDER, null);
		// 中
		g.drawImage(Img.INNERFRAME, x + BORDER, y + BORDER, x + w - BORDER, y + h
				- BORDER, BORDER, BORDER, imgWidth - BORDER, imgHeight - BORDER, null);
		// 右中
		g.drawImage(Img.INNERFRAME, x + w - BORDER, y + BORDER, x + w,
				y + h - BORDER, imgWidth - BORDER, BORDER, imgWidth,
				imgHeight - BORDER, null);
		// 左下
		g.drawImage(Img.INNERFRAME, x, y + h - BORDER, x + BORDER, y + h, 0,
				imgHeight - BORDER, BORDER, imgHeight, null);
		// 中下
		g.drawImage(Img.INNERFRAME, x + BORDER, y + h - BORDER, x + w - BORDER,
				y + h, BORDER, imgHeight - BORDER, imgWidth - BORDER, imgHeight, null);
		// 右下
		g.drawImage(Img.INNERFRAME, x + w - BORDER, y + h - BORDER, x + w, y + h,
				imgWidth - BORDER, imgHeight - BORDER, imgWidth, imgHeight, null);
	}

	/**
	 * 左对齐显示数字
	 * @param x 左上角x坐标
	 * @param y	右上角Y坐标
	 * @param num 要显示的数字
	 * @param maxBit 显示的最大位数
	 * @param g	画笔对象
	 */
	protected void drawNumberLeftPad(int x,int y,int num,int maxBit,Graphics g){
		//把数字Number中的每一位取出
		String strNum=Integer.toString(num);
		//循环绘制数字并右对齐
		for (int i = 0; i < maxBit; i++) {
			//判断是否满足绘制条件
			if(maxBit-i<=strNum.length()){
			//获得数字在字符串中的下标
			int idx=i-maxBit+strNum.length(); 
			//把数字number中的每一位取出
			int bit=strNum.charAt(idx)-'0';
			//绘制数字
			g.drawImage(Img.NUMBER, 
				this.x+ x +IMG_NUMBER_W*i, this.y+y, 
				this.x+ x +IMG_NUMBER_W*(i+1),  this.y+y+IMG_NUMBER_H,
				bit*IMG_NUMBER_W, 0,
				(bit+1)*IMG_NUMBER_W, IMG_NUMBER_H, null);
			}
		}
	}
	
	/**
	 * 绘制值槽
	 * @param title
	 * @param number
	 * @param value
	 * @param maxValue
	 * @param g
	 */
	
	protected void drawRect(int y,String title,String number,double percent,Graphics g){
		//初始化值
		int rect_x=this.x+PADDING;
		int rect_y=this.y+y;
		//绘制
		g.setColor(Color.BLACK);
		g.fillRect(rect_x, rect_y, this.rectW, IMG_RECT_H+4);
		g.setColor(Color.WHITE);
		g.fillRect(rect_x+1, rect_y+1, this.rectW-2, IMG_RECT_H+2);
		g.setColor(Color.BLACK);
		g.fillRect(rect_x+2, rect_y+2, this.rectW-4,IMG_RECT_H);
		//求出宽度
		int w=(int)(percent*(this.rectW-4));
		//求出颜色
		int subIdx=(int)(percent*IMG_RECT_W) - 1;
		//绘制值槽
		g.drawImage(Img.RECT, rect_x+2, rect_y+2,
				rect_x+2+w,rect_y+2+IMG_RECT_H, 
				subIdx, 0, subIdx+1, IMG_RECT_H, null);
		//显示“下一级”
		g.setColor(Color.WHITE);
		g.setFont(DEF_FONT);
		g.drawString(title, rect_x+4, rect_y+22);
		if(number!=null){
			g.drawString(number, rect_x+248, rect_y+22);
		}
	}
	
	
	/**
	 * 正中绘图
	 */
	protected void drawImageOfAtCenter(Image img,Graphics g){
		int imgW=img.getWidth(null);
		int imgH=img.getHeight(null);
		g.drawImage(img,this.x + (this.w-imgW>>1), this.y+(this.h-imgH>>1), null);
	}
	public void setDto(GameDto dto) {
		this.dto = dto;
	}

	public abstract void paint(Graphics g);

}
