package lucene.day01.fy.action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lucene.day01.fy.entity.Page;
import lucene.day01.fy.service.ArticleService;



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
			String temp=request.getParameter("currPageNO");
			if(temp==null || temp.trim().length()==0){
				temp="1";
			}
			//调用业务层
			ArticleService articleService=new ArticleService();
			Page page=articleService.show(keyWords, Integer.parseInt(temp));
			//将Page对象绑定到request域对象中
			request.setAttribute("PAGE", page);
			//将keyWords变量绑定到request域对象中
			request.setAttribute("KEYWORDS", keyWords);
			//转发到list.jsp页面
			request.getRequestDispatcher("/list.jsp").forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
