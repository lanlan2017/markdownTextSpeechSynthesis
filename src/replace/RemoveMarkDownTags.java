package replace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemoveMarkDownTags
{
	/**
	 * 移除markdown标题标记.
	 * 
	 * @param line
	 * @return
	 */
	public static String removeMDTitle(String line)
	{
		// Pattern pattern=Pattern.compile("(#+)([^#]+)(?:\\1)?");
		// Matcher matcher=pattern.matcher(line);
		// while(matcher.find())
		// {
		// System.out.println(matcher.group(0));
		// System.out.println(matcher.group(1));
		// System.out.println(matcher.group(2));
		// }
		return line.replaceAll("(#+\\s*?)([^#]+)(?:\\1)?", "$2");
	}
	/**
	 * 移除markdown图片标记.
	 * 
	 * @param line
	 * @return
	 */
	public static String removeMDIMG(String line)
	{
		Pattern pattern = Pattern.compile("!\\[.*\\]\\(.*\\)");
		Matcher matcher = pattern.matcher(line);
		StringBuffer sb = new StringBuffer();
		while (matcher.find())
		{
			// System.out.println("-----------------------------------------------");
			// System.out.println(matcher.group());
			// System.out.println("-----------------------------------------------");
			// 把匹配到的图片替换成空字符串。
			matcher.appendReplacement(sb, "");
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 移除markdown斜体标记,移除markdown加粗标记。
	 * 
	 * @param line
	 * @return
	 */
	public static String removeMDStrongOrItalic(String line)
	{
		// Pattern pattern=Pattern.compile("(\\*\\*)([^*]+)(?:\\1)?");
		// Matcher matcher=pattern.matcher(line);
		// while(matcher.find())
		// {
		// System.out.println(matcher.group(2));
		// }
		return line.replaceAll("(\\*{1,2})([^*]+)(?:\\1)?", "$2");
	}
	/**
	 * 移除markdown代码段标记
	 * 
	 * @param line
	 * @return
	 */
	public static String removeCode(String line)
	{
//		Pattern pattern = Pattern.compile("`(.+?)`");
//		Matcher matcher = pattern.matcher(line);
//		while (matcher.find())
//		{
//			line = matcher.replaceAll(" $1 ");
//		}
		line = line.replaceAll("`(.+?)`", " $1 ");
//		line = line.replace(",", "逗号");
//		line = line.replace("#", "井号");
//		line = line.replace(".", "点");
//		line = line.replace(";", "分号");
//		line = line.replace("%", "百分号");
//		line = line.replace("?", "问号");
		return line;
	}
	/**
	 * 移除markdown表格对齐符号.
	 * 
	 * @param line
	 * @return
	 */
	public static String removeMDTableAlign(String line)
	{
		// |:---|:---|
		return line;
	}
	/**
	 * 移除markdown,超链接。
	 * 
	 * @param line
	 * @return
	 */
	public static String removeMDLink(String line)
	{
//		Pattern pattern = Pattern.compile("\\[.*?]\\((https?://.+?)\\)");
//		Matcher matcher = pattern.matcher(line);
//		String link = null;
//		StringBuffer sb = new StringBuffer(10240);
//		while (matcher.find())
//		{
//			link = matcher.group(0);
//			link = link.replaceAll(":", "冒号");
//			link = link.replaceAll("/", "斜杠");
//			matcher.appendReplacement(sb, link);
//		}
//		matcher.appendTail(sb);
//		// line = line.replaceAll("\\[.*?]\\((https?://.+?)\\)", "$1").trim();
//		// line = line.replaceAll(":", "冒号");
//		// line = line.replaceAll("/", "斜杠");
//		return sb.toString();
//		line=line.replaceAll("\\[.*?]\\((https?://.+?)\\)", "");
		line=line.replaceAll("\\[.*?]\\((.+?)\\)", "");
		return line;
	}
	/**
	 * 移除markdown,代码块。
	 * 
	 * @param line
	 * @return
	 */
	public static String removeMDCodeBlock(String line)
	{
		// Pattern pattern=Pattern.compile("(\\*\\*)([^*]+)(?:\\1)?");
		// Matcher matcher=pattern.matcher(line);
		// while(matcher.find())
		// {
		// System.out.println(matcher.group(2));
		// }
		// 移除Markdown代码块标记.
		line = line.replaceAll("```\\w*\\r?\\n(.+\\r?\\n)+?```", "");
		return line;
		// return line.replaceAll("```(\\w+)?", "").trim();
	}
	/**
	 * 移除markdown,引用块。
	 * 
	 * @param line
	 * @return
	 */
	public static String removeMDQuoteBlock(String line)
	{
		// Pattern pattern=Pattern.compile("(\\*\\*)([^*]+)(?:\\1)?");
		// Matcher matcher=pattern.matcher(line);
		// while(matcher.find())
		// {
		// System.out.println(matcher.group(2));
		// }
		return line.replaceAll("(?m)^> ", "").trim();
	}
	/**
	 * @param input
	 * @return
	 */
	public static String replaceMD(String input)
	{
		System.out.println(
				"---------------------- 取出markdown标记 开始  ------------------------");
		// 去除markdown标题# h1 #,## h2 ##,### h3 ###,#### h4 ####,......
		input = RemoveMarkDownTags.removeMDTitle(input);
		// 去除markdown加粗: **xxxx**,或者斜体*xxxx*
		input = RemoveMarkDownTags.removeMDStrongOrItalic(input);
		// 去除Markdown文档中的图片标签
		input = RemoveMarkDownTags.removeMDIMG(input);
		// 移除Markdown文档中的超链接
		input = RemoveMarkDownTags.removeMDLink(input);
		// System.out.println(input);
		// 移除markdown中的代码块标记部分
		input = RemoveMarkDownTags.removeMDCodeBlock(input);
		// System.out.println("-------------------------");
		// System.out.println(input);
		// 移除markdown中的代码段标记部分.
		input = RemoveMarkDownTags.removeCode(input);
		// 移除markdown代码中的引用块,不要读成大于符号
		input = RemoveMarkDownTags.removeMDQuoteBlock(input);
		System.out.println(input);
		System.out.println(
				"---------------------- 取出markdown标记 开始  ------------------------");
		return input;
	}
	public static void main(String[] args)
	{
		// String testStr="# HTTP简介 # ";
		// String testStr="## HTTP简介 ## ";
		// String testStr="#### HTTP简介 #### ";
		// String testStr="##### HTTP简介 ##### ";
		// String testStr = "###### HTTP简介 ###### ";
		// System.out.println(testStr);
		// System.out.println(removeMDTitle(testStr));

		// String testStr =
		// "*TCP*负责确保从一个网络节点向另一个网络节点**发送的文件能作为一个完整的文件到达目的地**,尽管在具体传送过程中这个文件可能会分解为小块传输。\r\n"
		// + "IP是一个底层协议**,负责把数据块(数据包)沿路移动/路由到目的地**。";
		// String testStr =
		// "*HTTP*是TCP/IP的*上层协议*。如果你对这些网络协议还不太熟悉,下面就提供一个非常简单的解释:";
		// System.out.println(testStr);
		// System.out.println(removeMDStrongOrItalic(testStr));

		// String testStr = "![这里有一张图片](https://www.baidu.com/baidu.png)";
		// String testStr = "所以你别指望能在你的硬件主机里找到一个TCP端口。\r\n" +
		// "一方面,服务器上有65536个端口,端口号为0~65535。另一方面,端口并不表示一个可以插入物理设备的位置。它们只是表示服务器应用的“逻辑”数而已。\r\n"
		// +
		// "![这里有一张图片](https://image-1257720033.cos.ap-shanghai.myqcloud.com/blog/readbooknote/HeadFirstServletsAndJSP/DiYiZhang/ChangYongDuanKou.png)\r\n"
		// +
		// "\r\n" +
		// "如果每个端口上使用一个服务器应用,则一个服务器上最多可以运行65536个不同的服务器应用。";
		// String testStr = "> **注意**\r\n" +
		// "> 2011年，标准化组织IETF发布了WebSocket协议，即RFC
		// 6455规范。该协议允许一个HTTP连接升级为WebSocket连接，支持双向通信，这就使得服务端可以通过WebSocket协议主动发起同客户端的会话通信。";
		// String testStr = "附录C： “SSL证书” ，介绍了如何用KeyTool工具生 成公钥/私钥对，并生成数字证书。";
		// String testStr = "本书所有的示例应用压缩包可以通过如下地址下
		// 载：[http://books.brainysoftware.com/download](http://books.brainysoftware.com/download)";
//		String testStr = "例如，下面的`set`标签创建了字符串“`Hello World`”，并将它赋给新创建的页面范围变量`hello`：\r\n"
//				+ "```jsp\r\n"
//				+ "<c:set  value=\"Hello World\" var=\"hello\"/>\r\n"
//				+ "```\r\n"
//				+ "下面的`set `标签则创建了一个名为`job`的有界变量，它引用请求范围中`position`所引用的对象。变量`job `的范围为`page`：\r\n"
//				+ "```jsp\r\n"
//				+ "<c:set var=\"job\" value=\"${requestScope.position}\" scope=\"page\"/>\r\n"
//				+ "```\r\n" + "";
		String testStr="可以通过下面的`URL`来访问这个`JSP`页面：\r\n" + 
				"[http://localhost:8080/app08a/countries.jsp](http://localhost:8080/app08a/countries.jsp)";
		System.out.println(testStr);
		System.out.println(
				"##################################### 替换结果: #####################################");
		// System.out.println(removeMDQuoteBlock(testStr));
		System.out.println(replaceMD(testStr));
	}
}
