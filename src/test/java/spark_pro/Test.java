package spark_pro;

/**
 * 测试类
 * @author zhangchenguang
 *
 */
public class Test {

	public static void main(String[] args) {
		String str="1,t,45,wer,fg,";
		String[] ss=null;
		System.out.println(str.split(","));
		
		
		ss = str.split(",",-1);
		for (String s : ss) {
			System.out.println(s);
		}
	}
}
