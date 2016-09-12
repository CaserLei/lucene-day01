package lucene.day02.bean2json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import net.sf.json.JSONArray;

/**'
 * 演示用第三方工具类，将JavaBean,List,Map<String,Object>转换成json文本
 * @author leifeng
 * 2016年9月12日
 */
public class TestJson {
	@Test
	public void javabean2json(){
		User user=new User(1,"哈哈",7000);
		JSONArray jsonArray=JSONArray.fromObject(user);
		String jsonJava=jsonArray.toString();
		System.out.println(jsonJava);//[{"name":"哈哈","id":1,"sal":7000}]
	}
	
	@Test
	public void list2Json(){
		List<User> userList=new ArrayList<User>();
		userList.add(new User(1,"班长",7000));
		userList.add(new User(2,"班长老婆",5000));
		userList.add(new User(3,"班长小孩",20000));
		JSONArray jsonArray=JSONArray.fromObject(userList);
		String jsonJava=jsonArray.toString();
		//[{"name":"班长","id":1,"sal":7000},{"name":"班长老婆","id":2,"sal":5000},{"name":"班长小孩","id":3,"sal":20000}]
		System.out.println(jsonJava);

	}
	@Test
	public void map2Json(){
		List<User> userList=new ArrayList<User>();
		userList.add(new User(1,"班长",7000));
		userList.add(new User(2,"班长老婆",5000));
		userList.add(new User(3,"班长小孩",20000));
		userList.add(new User(3,"班长小孩的小孩",20000));
		
		Map<String,Object> map=new HashMap<String,Object>();
		//total表示集合的长度
		map.put("total",userList.size());
		//rows表示集合内容
		map.put("rows",userList);
		
		
		JSONArray jsonArray=JSONArray.fromObject(map);
		String jsonJava=jsonArray.toString();
		//[{"name":"班长","id":1,"sal":7000},{"name":"班长老婆","id":2,"sal":5000},{"name":"班长小孩","id":3,"sal":20000}]
		System.out.println(jsonJava);

	}

}
