package top.zcg.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.apache.hadoop.hive.ql.parse.HiveParser.resource_return;

/**
 * 获取csv
 * @author zhangchenguang
 *
 */
public class ReadCsv {

	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(
				new InputStreamReader(
						new FileInputStream("/Users/zhangchenguang/Desktop/1.csv")
						,"GBK"
					)
			);
		StringBuffer result= new StringBuffer();
		String str = null;
        while ((str = in.readLine()) != null) {
//            System.out.println(str);
            //写入相关文件
            result.append(str);
        }
        in.close();
        
        System.out.println(result);
        String[] ss = result.toString().split(",");
        for (String s : ss) {
			System.out.println(s.substring(1, s.length()-1));
		}
        
        System.out.println(ss.length);
	}
}
