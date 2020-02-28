package spark_pro;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

/**
 * 测试json串的解析逻辑
 * @author zhangchenguang
 *
 */
public class getJsonStr {

	public static void main(String[] args) throws Exception {
		JSONObject json =null;
		String res=null;
		BufferedReader br = new BufferedReader(new FileReader(new File("/Users/zhangchenguang/Desktop/1.txt")));
		String str = null;
		while((str = br.readLine()) != null){
			json=new JSONObject();
			json=JSONObject.fromObject(str);
			if(json.containsKey("onPageStartEvent")){
				res = (String) json.get("onPageStartEvent");
			}else{
				if(json.containsKey("action") && json.containsKey("domain")){
					String tmp1 = (String) json.get("action");
					String tmp2 = (String) json.get("domain");
					if(tmp1.equals("1") && tmp2.equals("static.scms.sztv.com.cn")){
						res = "1";
					}
				}
			}
			System.out.println(json.get("ip"));
		}
		br.close();
	}
}
