<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
    <h2>普通文件上传</h2>
    <form action="/manage/product/uploadFile.do" name="fileForm" method="post" enctype="multipart/form-data">
        <input name="uploadFile" type="file"/>
        <input type="submit" value="SpringMVC上传"/>
    </form>

    <h2>富文本文件上传</h2>
    <form action="/manage/product/richTextFileUpload.do" name="fileForm" method="post" enctype="multipart/form-data">
        <input name="uploadFile" type="file"/>
        <input type="submit" value="富文本文件上传"/>
    </form>
</body>
</html>
