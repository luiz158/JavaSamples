<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Upload</title>
</head>
<body>
	<h1>Upload</h1>

	<p>${ message }</p>


	<form action="${ pageContext.request.contextPath }/uploadFile" method="post" enctype="multipart/form-data">
		<p>
	 		<label for="file">Arquivo para fazer upload:</label><br/>
	 		<input type="file" name="file" /><br/>
	 		<input type="submit" name="submit" value="Upload" /><br/>
		</p>
	</form>
</body>
</html>
