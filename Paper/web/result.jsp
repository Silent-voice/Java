<%@ page import="java.io.File" %><%--
  Created by IntelliJ IDEA.
  User: Billy
  Date: 2016/8/6
  Time: 11:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>论文格式校正</title>
</head>
<body>
    <%
        String fileName = (String) request.getAttribute("fileName");
        String dirPath = "data/";
        String txtPath = dirPath + fileName + "/" + "check_out.txt";
        String wordPath = dirPath + fileName + "/" + "result.docx";
//        out.print("<div align='center'>");
//        out.print("查看结果");
//        out.print("<br>");
//        out.print("<a href=\'" + txtPath + "\'>");
//        out.print("查看错误信息");
//        out.print("</a>");
//        out.print("<br>");
//        out.print("<a href=\'" + wordPath + "\'>");
//        out.print("下载Word文档");
//        out.print("</a>");
//        out.print("</div>");
    %>
    <div align="center">
        查看结果<br>
        <a href="<%=txtPath%>">查看错误信息</a><br>
        <a href="<%=wordPath%>">下载Word文档</a>
    </div>
</body>
</html>
