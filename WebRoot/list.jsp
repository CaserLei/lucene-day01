<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>同步分页</title>
		<link rel="stylesheet" href="themes/icon.css" type="text/css"></link>
		<link rel="stylesheet" href="themes/default/easyui.css" type="text/css"></link>
		<!-- 引入easyui的js文件，先引入jquery，再引入easyui -->  
		<script type="text/javascript" src="js/jquery-1.8.2.js"></script>
		<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="js/easyui-lang-zh_CN.js"></script>
	</head>
	<body>
	  <!-- 输入区 -->
	  <form id="myformID">
	            输入关键字：<input type="text" value="" id="keywordID"/>
	    <input type="button" value="站内搜索" id="findID">
	  </form>
	  <script type="text/javascript">
	    //定位"站内搜索按钮"
	    $("#findID").click(function(){
	    	//获取关键字
	    	var keyword=$("#keywordID").val();
	    	//去空格
	    	keyword=$.trim(keyword);
	    	//判断
	    	if(keyword.length==0){
	    		//提示
	    		alert("请输入关键字！！！");
	    		$("#keywordID").focus();
	    	}else{
	    		//异步发送请求到服务器端
	    		$('#dg').datagrid('load', {    
	    		    "keyWords":keyword
	    		});  
	    	}
	    });
	  </script>  
	  
	  <!-- 显示区 -->
	  <table id="dg"></table>
	  <script type="text/javascript">
	     $('#dg').datagrid({    
	        url:"${pageContext.request.contextPath}/ArticleServlet?time="+new Date().getTime(),    
			columns:[[    
			    {field:'id',title:'编号',width:100},    
			    {field:'title',title:'标题',width:100},    
			    {field:'content',title:'内容',width:100},
			]],
			fitColumns:true,
			singleSelect:true,
			pagination:true,
			pageSize:2,
			pageList:[2,4,6]
		 });  
	  </script>
	  
	</body>
</html>