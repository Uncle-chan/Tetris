package cn.edu.shnu.tetris.dao;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.edu.shnu.tetris.dto.Player;

public class DataTest implements Data{

	public DataTest(HashMap<String, String> param) {
		
	}
	@Override
	public List<Player> loadData() {
		List<Player> players=new ArrayList<Player>();
		players.add(new Player("小明", 100));
		players.add(new Player("小王", 1000));
		players.add(new Player("小明", 300));
		players.add(new Player("小明", 3000));
	//	players.add(new Player("小明", 4000));
		return players;
	}

	@Override
	public void saveData(Player players) {
//		System.out.println();
	}
	
//	public static void main(String[] args) throws FileNotFoundException, IOException {
//		List<Player> players=new ArrayList<Player>();
//		players.add(new Player("小明", 100));
//		players.add(new Player("小王", 1000));
//		players.add(new Player("小红", 300));
//		players.add(new Player("小天", 3000));
//		players.add(new Player("小雷", 4000));
//		
//		ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("data/recoder.dat"));
//		oos.writeObject(players);
//	}

}
