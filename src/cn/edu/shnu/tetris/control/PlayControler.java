package cn.edu.shnu.tetris.control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PlayControler extends KeyAdapter {

	private GameControler gameControler;

	public PlayControler(GameControler gameControler) {
		this.gameControler = gameControler;
	}

	/**
	 * 键盘按下事件
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		this.gameControler.actionByKeyCode(e.getKeyCode());
	}

}
