package regex;

public class RegexMarkdown
{
	/**
	 * 匹配Markdown 表格 正则表达式.
	 */
	public final static String MDTableRegex = "(\\|(?:.+\\|)+)(?:(?:\\r\\n|\\n)\\|(?:[:-]+\\|)+)((?:(?:\\r\\n|\\n)\\|(?:.+\\|)+)+)";
	/**
	 * 匹配Markdown 行内代码 正则表达式.
	 */
	public final static String MDCodeInLineRegex = "`(.+?)`";
	/**
	 * 匹配Markdown 表格内的 行内代码 正则表达式 
	 */
	public final static String MDCodeInLineInMDTableColsRegex = "(?m)^\\|`(.+?)`\\|";
	/**
	 * 匹配markdown 加粗或斜体 的正则表达式.
	 */
	public final static String MDStrongOrItalicRegex = "(\\*{1,2})([^*]+?)(?:\\1)?";
	/**
	 * 匹配markdown 超链接 的正则表达式.
	 */
	public final static String MDLinkRegex = "\\[(.*)\\]\\(.+\\)";
	/**
	 * 匹配Markdown 代码块 的正则表达式.
	 */
	public final static String MDCodeBlockRegex = "```\\w*(?:(?:\\r\\n|\\n).*?)+?```";
	/**
	 * 匹配markdown 引用块 的正则表达式.
	 */
	public final static String MDQuoteBlockRegex = "(?m)^> ";
	/**
	 * 匹配markdown 无序列表 的正则表达式.
	 */
	public final static String MDUnOrderListBlockRegex = "(?m)^\\s*- ";
	/**
	 * 匹配markdown 标题 的正则表达式.
	 */
	public final static String MDTitleRegex = "(#+)(\\s+)([^#]+)(?:\\2)?(?:\\1)?";

}
