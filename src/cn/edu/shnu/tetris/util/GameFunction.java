package cn.edu.shnu.tetris.util;

public class GameFunction {

	/**
	 * 计算线程时间
	 */
	public static long getSleeepTimmeByLevel(int level){
			long sleep=(-40*level+740);
			sleep=sleep<100?100:sleep;
			return sleep;
	}
}
