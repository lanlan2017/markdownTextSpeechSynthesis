package replace.md.table;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import clipboard.util.SysClipboardUtil;
import static regex.RegexJava.javaMethedRegex;
//导入正则表达式
import static regex.RegexMarkdown.MDTableRegex;
import static regex.RegexMarkdown.MDCodeInLineRegex;

public class MDTableReplace
{
	/**
	 * 朗读Markdown表格,当表格中有方法声明时.
	 * 
	 * @param line
	 *            markdown文档中的内容字符串.
	 * @return
	 */
	public static String MDTableSpeech(String line)
	{
		Pattern pattern = Pattern.compile(MDTableRegex);
		Matcher matcher = pattern.matcher(line);
		StringBuffer sb = new StringBuffer();
		String tableTitle;
		// String tableAlign;
		String tableBody;
		String table;
		while (matcher.find())
		{
			// 获取匹配到的一个分组
			tableTitle = matcher.group(1);
			tableBody = matcher.group(2);
			tableBody = MDTableCodeInLineRepalce(tableBody);
			table = tableTitle + tableBody;
			matcher.appendReplacement(sb, table);
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
	/**
	 * 替换表格中的行内代码.
	 * 
	 * @param tableBody
	 * @return
	 */
	public static String MDTableCodeInLineRepalce(String tableBody)
	{
		Pattern pattern = Pattern.compile(MDCodeInLineRegex);
		Matcher matcher = pattern.matcher(tableBody);
		StringBuffer sb = new StringBuffer();
		String group1;
		while (matcher.find())
		{
			// 删除反引号
			group1 = matcher.group(1);
			// 朗读Java方法
			group1 = replaceJavaMethod(group1);
			// 替换原来匹配的文本
			matcher.appendReplacement(sb, group1);
		}
		// 添加后面没有匹配的文本
		matcher.appendTail(sb);
		return sb.toString();
	}
	/**
	 * java方法的朗读规则.
	 * 
	 * @param matcherStr
	 *            java方法定义字符串.
	 * @return 朗读后的java方法.
	 */
	private static String replaceJavaMethod(String matcherStr)
	{
		Pattern pattern = Pattern.compile(javaMethedRegex);
		Matcher matcher = pattern.matcher(matcherStr);
		String group1;
		String group2;
		String group3;
		if (matcher.matches())
		{
			// 获取匹配到的一个分组
			group1 = matcher.group(1);
			group2 = matcher.group(2);
			group3 = matcher.group(3);
			// System.out.println("捕获到表格:开始处理----------");
			// System.out.println(" group1-->" + group1);
			// System.out.println(" group2-->" + group2);
			// System.out.println(" group3-->" + group3);
			// matcherStr = group2 + " 方法 ";
			if (!group1.equals(group2))
			{
				matcherStr = matcherStr + " 该方法定义 " + group1;
			}
			if (!"".equals(group3))
			{
				matcherStr = matcherStr + " 该方法参数列表 "
						+ group3.replace(",", "逗号");
			} else
			{
				matcherStr = matcherStr + " 该方法是无参方法 ";
			}
			matcherStr = matcherStr + " 该方法功能 ";
			// System.out.println(" 替换结果:" + matcherStr);
			// System.out.println("捕获到表格:处理结束----------");
		}
		return matcherStr;
	}
	public static void main(String[] args)
	{
		String input = SysClipboardUtil.getSysClipboardText();
		System.out.println(MDTableSpeech(input));
	}
}
