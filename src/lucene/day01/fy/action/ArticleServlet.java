package lucene.day01.fy.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lucene.day01.fy.entity.Page;
import lucene.day01.fy.service.ArticleService;
import net.sf.json.JSONArray;



public class ArticleServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			//获取关键字
			String keyWords=request.getParameter("keyWords");
			if(keyWords==null || keyWords.trim().length()==0){
				keyWords="培训";
			}
			//获取当前页号
			String temp=request.getParameter("page");
			if(temp==null || temp.trim().length()==0){
				temp="1";
			}
			
			//调用业务层
			ArticleService articleService=new ArticleService();
			Page page=articleService.show(keyWords, Integer.parseInt(temp));
			
			//构造Map对象
			Map<String,Object> map=new LinkedHashMap<String,Object>();
			map.put("total", page.getAllRecordNo());
			map.put("rows", page.getArticleList());
			
			JSONArray jsonArray=new JSONArray().fromObject(map);
			String jsonJava=jsonArray.toString();
			
			System.out.println(jsonJava);
			//去掉两边的[] 符号
			jsonJava=jsonJava.substring(1, jsonJava.length()-1);
			System.out.println(jsonJava);
			//以IO流的方式将数据放回到dataGrid组件
			response.setContentType("text/html;charset=utf-8");
			PrintWriter pw=response.getWriter();
			pw.write(jsonJava);
			pw.flush();
			pw.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
