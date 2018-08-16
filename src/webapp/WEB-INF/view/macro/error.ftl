<#macro layout summary>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>${summary}&nbsp;-&nbsp;${project_name!}</title>
<style type="text/css">
body {
	background-color: #fff;
	margin: 40px;
	font: 14px normal "Microsoft YaHei", 微软雅黑, Helvetica, Arial, sans-serif;
	color: #4F5155;
}
h1 {
	color: #444;
	background-color: transparent;
	border-bottom: 1px solid #D0D0D0;
	font-size: 20px;
	font-weight: normal;
	margin: 0 0 14px 0;
	padding: 14px 15px 10px 15px;
}
#body{
	margin: 0 15px;
}
p.footer{
	text-align: right;
	font-size: 12px;
	border-top: 1px solid #D0D0D0;
	line-height: 32px;
	padding: 0 10px 0 10px;
	margin: 20px 0 0 0;
}
#container{
	margin: 10px;
	border: 1px solid #D0D0D0;
	-webkit-box-shadow: 0 0 8px #D0D0D0;
	border-radius: 3px;
}
</style>
</head>
<body>
<div id="container">
	<h1>${summary}</h1>
	<div id="body">
		<p><#nested /></p>
	</div>
	<p class="footer">技术支持: ${project_name!}</p>
</div>
</body>
</html>
</#macro>