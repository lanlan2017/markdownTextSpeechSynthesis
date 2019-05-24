package replace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.iflytek.cloud.b.b.e;

public class RemoveMarkDownTags
{
	/**
	 * 移除markdown表格的对齐代码.
	 * 
	 * @param line
	 *            markdown文本.
	 * @return 没有对齐方式的markdown文本
	 */
	public static String removeMDTable(String line)
	{
		line = line.replaceAll("\\|(:?\\-+:?\\|)+\\n", "");
		line = line.replaceAll("(?m)^\\|", "");
		return line;
	}

	/**
	 * 移除markdown标题标记.
	 * 
	 * @param line
	 * @return
	 */
	public static String removeMDTitle(String line)
	{
		Pattern pattern = Pattern.compile("(#+\\s*?)([^#]+)(?:\\1)?");
		Matcher matcher = pattern.matcher(line);
		StringBuffer sb = new StringBuffer();
		String matcherStr;
		while (matcher.find())
		{
			// 获取匹配文本
			matcherStr = matcher.group(2);

			matcherStr = MDCodeReplace.replaceContainSpecialWords(matcherStr);
			matcherStr = matcherStr.replace("-", "杠");
			matcherStr = matcherStr.replace(".", "点");
			matcherStr = " " + matcherStr + " ";
			matcher.appendReplacement(sb, matcherStr);
		}
		matcher.appendTail(sb);
		return sb.toString();
		// return line.replaceAll("(#+\\s*?)([^#]+)(?:\\1)?", "$2");
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
			// 获取匹配文本
			matcherStr = matcher.group(1);
			// 替换容易读错的符号
			// 处理横线:负,杠
			// 负数的情况
			// 处理匹配特定正则表达式的情况
			matcherStr = MDCodeReplace.replaceMatcher(matcherStr);
			// 处理匹配特定单词的情况
			matcherStr = MDCodeReplace.replaceSpecialWords(matcherStr);
			// 处理匹配特定字符的情况
			matcherStr = MDCodeReplace.replaceSpecialChars(matcherStr);
			// 替换容易读出的单词
			matcherStr = MDCodeReplace.replaceContainSpecialWords(matcherStr);
			// 添加空格让机器人好识别
			// matcherStr = " " + matcherStr + " ";
			matcher.appendReplacement(sb, matcherStr);
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
	/**
	 * 移除markdown,超链接。
	 * 
	 * @param line
	 * @return
	 */
	public static String removeMDLink(String line)
	{
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
		Pattern codeBlockPattern = Pattern
				.compile("```(\\w*)\\r?\\n((?:.*\\r?\\n)+?)```");
		Matcher codeBlockMatcher = codeBlockPattern.matcher(line);
		StringBuffer sb = new StringBuffer();
		String sql;
		while (codeBlockMatcher.find())
		{
			// System.out.println(codeBlockMatcher.group());
			// System.out.println("-----------");
			// System.out.println(codeBlockMatcher.group(2));
			// if (codeBlockMatcher.group(1).equals("sql"))
			// {
			// sql = codeBlockMatcher.group(2);
			// sql = sql.replace("(", "括号开始");
			// sql = sql.replace(")", "括号结束");
			// sql = sql.replace(";", "分号");
			// codeBlockMatcher.appendReplacement(sb,
			// "sequel语句开始\r\n" + sql + "\r\nsequel语句结束");
			// } else
			// {
			// codeBlockMatcher.appendReplacement(sb, "");
			// }
			// 删除代码块
			codeBlockMatcher.appendReplacement(sb, "");
		}
		codeBlockMatcher.appendTail(sb);
		// 移除Markdown代码块标记.
		// line = line.replaceAll("``\\w*\\r?\\n(.*\\r?\\n)+?```", "");

		return sb.toString();
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
		input = removeMDTitle(input);
		// 去除markdown加粗: **xxxx**,或者斜体*xxxx*
		input = removeMDStrongOrItalic(input);
		// 去除Markdown文档中的图片标签
		input = removeMDIMG(input);
		// 移除Markdown文档中的超链接
		input = removeMDLink(input);
		// 移除markdown中的代码块标记部分
		input = removeMDCodeBlock(input);
		// 移除markdown中的代码段标记部分.
		input = removeMDCode(input);
		// 移除markdown代码中的引用块,不要读成大于符号
		input = removeMDQuoteBlock(input);
		// 移除markdown表格对齐符号,
		input = removeMDTable(input);
		System.out.println(input);
		System.out.println(
				"---------------------- 取出markdown标记 结束  ------------------------");
		return input;
	}
	public static void main(String[] args)
	{
		// String testStr = "可以通过下面的`URL`来访问这个`JSP`页面：\r\n"
		// +
		// "[http://localhost:8080/app08a/countries.jsp](http://localhost:8080/app08a/countries.jsp)";
		// String testStr = "# 12.1.2 实施安全约束 #\r\n"
		// +
		// "**`WEB-INF`目录下的资源客户端不能直接通过`URL`访问**,不过,我们可以通过`Servlet`或`JSP`页面访问`WEB-INF`目录下的资源。"
		// + "### web-resource-collection子元素 ###\r\n"
		// +
		// "**`web-resource-collection`元素表示需要限制访问的资源集合**。包括`web-resource-name`、`description`、`url-pattern`、`http-method`和`http-method-ommission`等子元素。\r\n"
		// + "- `web-resource-name`子元素用于设置与受保护资源相关联的名称。\r\n"
		// + "则该方法返回:`-1`\r\n" + "编码为:`ISO-8859-1`\r\n" + "获取内部的`id`值\r\n"
		// + "`url`\r\n" + "`MIME`\r\n" + "`GET`方法\r\n";
		String testStr = "修改表结构使用`alter table`，修改表结构包括`增加列定义`、`修改列定义`、`删除列`、`重命名列`等操作。增加列定义的语法如下:\r\n"
				+ "```java\r\n" + "alter table 表名\r\n" + "add\r\n" + "(\r\n"
				+ "    colum_nam1 datatype [default expr],\r\n" + "    ...\r\n"
				+ ");\r\n" + "```";
		System.out.println(testStr);
		System.out.println(
				"##################################### 替换结果: #####################################");
		// System.out.println(removeMDQuoteBlock(testStr));
		System.out.println(replaceMD(testStr));
	}
}
