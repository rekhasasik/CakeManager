<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.List"%>
<%@ page import="com.waracle.cakemgr.CakeEntity"%>
<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	    <title>Cake Manager</title>
	    
	 
	</head>
	
	<body>
	    
	    
	    
	    <div id="cake-results">
	    <table border="1px" id="cake_table">
	    	<tr>
	    		<th>Image</th>
	    		<th>Title</th>
	    		<th>Description</th>
	    	</tr>
	    
	    
	    <% 
	    
	    List<CakeEntity> cakeList = (List<CakeEntity>)request.getAttribute("cakeList");
	    
	    for (CakeEntity cakeObj : cakeList) {
	    	
	    %>
	    			<tr>
	    			
	    			<td> <img src="<%=cakeObj.getImage() %>" width="200"></td> 
	    			<td><%=cakeObj.getTitle() %></td>
	    			<td><%=cakeObj.getDesc() %></td>
	    			</tr>
	     <%}%>		
	   
	    </table>	
	    </div>
	    </br></br>
	    
	  
	</body>
	
</html>
<%@ include file = "addItem.jsp" %>