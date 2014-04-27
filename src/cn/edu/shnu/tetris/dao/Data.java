package cn.edu.shnu.tetris.dao;

import java.util.List;

import cn.edu.shnu.tetris.dto.Player;

/**
 * 数据持久层接口
 * @author Bill
 *
 */
public interface Data {
	/**
	 * 读取数据
	 * @return 
	 */
	public List<Player> loadData();

	/**
	 *  存储数据
	 * @param players
	 */
	public void saveData(Player players);
}
