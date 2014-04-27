package cn.edu.shnu.tetris.config;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 封装frame元素
 * 
 * @author Bill
 * 
 */
public class GameConfig {
	
	private static FrameConfig FRAME_CONFIG;
	
	private static DataConfig DATA_CONFIG;
	
	private static SystemConfig SYSTEM_CONFIG;
	
	static {
		
		try {
			// xml读取器
			SAXReader reader = new SAXReader();
			Document doc = reader.read("data/cfg.xml");
			// 读取根节点
			Element game = doc.getRootElement();
			//创建界面配置对象
			FRAME_CONFIG=new FrameConfig(game.element("frame"));
			//创建数据访问配置对象
			DATA_CONFIG=new DataConfig(game.element("data"));
			//配置系统对象
			SYSTEM_CONFIG=new SystemConfig(game.element("system"));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		

	
	}

	/**
	 * 构造器私有化
	 */
	private GameConfig(){
		
	}
	/**
	 * 获得数据访问配置
	 * @return
	 */
	public static DataConfig getDataConfig(){
		return DATA_CONFIG;
	}
	/**
	 * 获得窗口配置
	 * @return
	 */
	public static FrameConfig getFrameConfig(){
		return FRAME_CONFIG;
	}
	/**
	 * 获得系统配置
	 * @return
	 */
	public static SystemConfig getSystemConfig (){
		return SYSTEM_CONFIG;
	}

}
