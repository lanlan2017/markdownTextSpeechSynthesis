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
		return line.replaceAll("(#+\\s*?)([^#]+)(?:\\1)?", "$2").trim();
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
		return sb.toString().trim();
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
		return line.replaceAll("(\\*{1,2})([^*]+)(?:\\1)?", "$2").trim();
	}
	/**
	 * 移除markdown代码段标记
	 * 
	 * @param line
	 * @return
	 */
	public static String removeCode(String line)
	{
		// Pattern pattern=Pattern.compile("(\\*\\*)([^*]+)(?:\\1)?");
		// Matcher matcher=pattern.matcher(line);
		// while(matcher.find())
		// {
		// System.out.println(matcher.group(2));
		// }
		return line.replaceAll("`(.+?)`", " $1 ");
	}
	/**
	 * 移除markdown,超链接。
	 * 
	 * @param line
	 * @return
	 */
	public static String removeMDLink(String line)
	{
		Pattern pattern = Pattern.compile("\\[.*?]\\((https?://.+?)\\)");
		Matcher matcher = pattern.matcher(line);
		String link = null;
		StringBuffer sb = new StringBuffer(10240);
		while (matcher.find())
		{
			link = matcher.group(0);
			link = link.replaceAll(":", "冒号");
			link = link.replaceAll("/", "斜杠");
			matcher.appendReplacement(sb, link);
		}
		matcher.appendTail(sb);
		// line = line.replaceAll("\\[.*?]\\((https?://.+?)\\)", "$1").trim();
		// line = line.replaceAll(":", "冒号");
		// line = line.replaceAll("/", "斜杠");
		return sb.toString();
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
		return line.replaceAll("```(\\w+)?", "").trim();
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
		// 去除markdown标题# h1 #,## h2 ##,### h3 ###,#### h4 ####,......
		input = RemoveMarkDownTags.removeMDTitle(input);
		// 去除markdown加粗: **xxxx**,或者斜体*xxxx*
		input = RemoveMarkDownTags.removeMDStrongOrItalic(input);
		// 去除Markdown文档中的图片标签
		input = RemoveMarkDownTags.removeMDIMG(input);
		// 只保留超链接部分,丢弃文字部分
		input = RemoveMarkDownTags.removeMDLink(input);
		// 移除markdown中的代码块标记部分
		input = RemoveMarkDownTags.removeMDCodeBlock(input);
		// 移除markdown中的代码段标记部分.
		input = RemoveMarkDownTags.removeCode(input);
		// 移除markdown代码中的引用块,不要读成大于符号
		input = RemoveMarkDownTags.removeMDQuoteBlock(input);
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
//		String testStr = "附录C： “SSL证书” ，介绍了如何用KeyTool工具生 成公钥/私钥对，并生成数字证书。";
		// String testStr = "本书所有的示例应用压缩包可以通过如下地址下
		// 载：[http://books.brainysoftware.com/download](http://books.brainysoftware.com/download)";
		String testStr = "## 4.3.4 paramValues隐式对象 ##\r\n" + 
				"利用隐式对象`paramValues`可以获取一个**请求参数**的**多个值**。这个对象表示一个包含所有请求参数的`Map`集合，参数名称作为`key`。每个`key`的对应值是一个`字符串数组`，数组中包含了指定参数名称的所有值。即使该参数只有一个值，它也仍然返回一个只带有一个元素的数组。\r\n" + 
				"\r\n" + 
				"例如，为了获得`selectedOptions`参数的第一个值和第二个值，可以使用以下表达式：";
		System.out.println(testStr);
		System.out.println(
				"##################################### 替换结果: #####################################");
		// System.out.println(removeMDQuoteBlock(testStr));
		System.out.println(removeCode(testStr));
	}
}
