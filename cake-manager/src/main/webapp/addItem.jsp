<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <script type="text/javascript" src="resources/jquery-2.1.1.min.js"></script>
</head>
<body>

<h3>Add Cake and press submit!</h3>
	    <h4>Title: </h4><input type="text" id="title-input" value=""><br>
        <h4>Description: </h4><input type="text" id="desc-input" value=""><br>
        <h4>Image: </h4><input type="text" id="image-input" value=""><br>
        <input type="submit" id="submit-cake" value="Submit" onClick="updateData();">
        
        <script type="text/javascript">
        function updateData()
        {
        	
        	
	        var title = $("#title-input").val();

	        var description = $("#desc-input").val();
	        
	        var image = $("#image-input").val();


	        if (title == null || isEmpty(title) || description == null || isEmpty(description)) {

	            alert("Not enough information for an insertion! Please enter title and description");
	            $('input[type="text"],textarea').val('');

	            return;

	        }
        	
        	$.ajax({
        	type: "POST",
            url: "cakes",
            data: {"title-input":title,"desc-input":description, "image-input":image},
            
            error : function(jqXHR, textStatus, errorThrown) {

                alert("Error Occured \n"+ errorThrown);
            },
            success : function(json) {
            	
            	alert("Insertion successful! Please Refresh to see the cake appended to the list");
            	$('input[type="text"],textarea').val('');
            	
            }
        	
        	});
        	
        }
        
        function isEmpty(str){
            return !str.replace(/\s+/, '').length;
        }
        </script>

</body>
</html>