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
//		line= line.replaceAll("(#+\\s*?)([^#]+)(?:\\1)?", "$2");
		Pattern pattern = Pattern.compile("(#+\\s*?)([^#]+)(?:\\1)?");
		Matcher matcher = pattern.matcher(line);
		StringBuffer sb = new StringBuffer();
		String matcherStr;
		while (matcher.find())
		{
			//获取匹配文本
			matcherStr=matcher.group(2);
			matcherStr=matcherStr.replace("-", "杠");
			matcherStr=matcherStr.replace(".", "点");
			matcherStr=" "+matcherStr+" ";
			matcher.appendReplacement(sb, matcherStr);
		}
		matcher.appendTail(sb);
		return sb.toString();
//		return line.replaceAll("(#+\\s*?)([^#]+)(?:\\1)?", "$2");
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
	public static String removeMDCode(String line)
	{
		Pattern pattern = Pattern.compile("`(.+?)`");
		Matcher matcher = pattern.matcher(line);
		StringBuffer sb = new StringBuffer();
		String matcherStr;
		while (matcher.find())
		{
			//获取匹配文本
			matcherStr=matcher.group(1);
			//替换容易读错的符号
			matcherStr=matcherStr.replace("-", " 杠");
			matcherStr=matcherStr.replace("=", "等于");
			matcherStr=matcherStr.replace("&", "单与符号");
			matcherStr=matcherStr.replace("?", "问号");
			matcherStr=matcherStr.replace("*", "星号");
			matcherStr=matcherStr.replace("/", "斜杠");
			matcherStr=matcherStr.replace(":", "冒号");
			//替换容易读出的单词
			matcherStr=matcherStr.replace("javax", "java x");
			matcherStr=matcherStr.replace("https", "http s");
			matcherStr=matcherStr.replace("url", "URL");
			matcherStr=" "+matcherStr+" ";
			matcher.appendReplacement(sb, matcherStr);
		}
		matcher.appendTail(sb);
		return sb.toString();
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
		// Pattern pattern = Pattern.compile("\\[.*?]\\((https?://.+?)\\)");
		// Matcher matcher = pattern.matcher(line);
		// String link = null;
		// StringBuffer sb = new StringBuffer(10240);
		// while (matcher.find())
		// {
		// link = matcher.group(0);
		// link = link.replaceAll(":", "冒号");
		// link = link.replaceAll("/", "斜杠");
		// matcher.appendReplacement(sb, link);
		// }
		// matcher.appendTail(sb);
		// // line = line.replaceAll("\\[.*?]\\((https?://.+?)\\)",
		// "$1").trim();
		// // line = line.replaceAll(":", "冒号");
		// // line = line.replaceAll("/", "斜杠");
		// return sb.toString();
		// 替换中文站内超链接
		line = line.replaceAll("\\[([\\u4e00-\\u9fa5]*?)\\]\\(/blog/.+?/\\)",
				" $1 ");
		// 替换站外超链接
		line = line.replaceAll("\\[.*?]\\((https?://.+?)\\)", "");
		// line=line.replaceAll("\\[.*?]\\((.+?)\\)", "");
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
		input = RemoveMarkDownTags.removeMDCode(input);
		// 移除markdown代码中的引用块,不要读成大于符号
		input = RemoveMarkDownTags.removeMDQuoteBlock(input);
		System.out.println(input);
		System.out.println(
				"---------------------- 取出markdown标记 开始  ------------------------");
		return input;
	}
	public static void main(String[] args)
	{
//		String testStr = "可以通过下面的`URL`来访问这个`JSP`页面：\r\n"
//				+ "[http://localhost:8080/app08a/countries.jsp](http://localhost:8080/app08a/countries.jsp)";
		String testStr="# 12.1.2 实施安全约束 #\r\n" + 
				"**`WEB-INF`目录下的资源客户端不能直接通过`URL`访问**,不过,我们可以通过`Servlet`或`JSP`页面访问`WEB-INF`目录下的资源。"
				+ "### web-resource-collection子元素 ###\r\n" + 
				"**`web-resource-collection`元素表示需要限制访问的资源集合**。包括`web-resource-name`、`description`、`url-pattern`、`http-method`和`http-method-ommission`等子元素。\r\n" + 
				"- `web-resource-name`子元素用于设置与受保护资源相关联的名称。";
		System.out.println(testStr);
		System.out.println(
				"##################################### 替换结果: #####################################");
		// System.out.println(removeMDQuoteBlock(testStr));
		System.out.println(replaceMD(testStr));
	}
}
