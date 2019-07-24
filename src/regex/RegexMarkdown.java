package regex;

public class RegexMarkdown
{
	/**
	 * ƥ��Markdown ��� ������ʽ.
	 */
	public final static String MDTableRegex = "(\\|(?:.+\\|)+)(?:(?:\\r\\n|\\n)\\|(?:[:-]+\\|)+)((?:(?:\\r\\n|\\n)\\|(?:.+\\|)+)+)";
	/**
	 * ƥ��Markdown ���ڴ��� ������ʽ.
	 */
	public final static String MDCodeInLineRegex = "`(.+?)`";
	/**
	 * ƥ��Markdown ����ڵ� ���ڴ��� ������ʽ 
	 */
	public final static String MDCodeInLineInMDTableColsRegex = "(?m)^\\|`(.+?)`\\|";
	/**
	 * ƥ��markdown �Ӵֻ�б�� ��������ʽ.
	 */
	public final static String MDStrongOrItalicRegex = "(\\*{1,2})([^*]+?)(?:\\1)?";
	/**
	 * ƥ��markdown ������ ��������ʽ.
	 */
	public final static String MDLinkRegex = "\\[(.*)\\]\\(.+\\)";
	/**
	 * ƥ��Markdown ����� ��������ʽ.
	 */
	public final static String MDCodeBlockRegex = "```\\w*(?:(?:\\r\\n|\\n).*?)+?```";
	/**
	 * ƥ��markdown ���ÿ� ��������ʽ.
	 */
	public final static String MDQuoteBlockRegex = "(?m)^> ";
	/**
	 * ƥ��markdown �����б� ��������ʽ.
	 */
	public final static String MDUnOrderListBlockRegex = "(?m)^\\s*- ";
	/**
	 * ƥ��markdown ���� ��������ʽ.
	 */
	public final static String MDTitleRegex = "(#+)(\\s+)([^#]+)(?:\\2)?(?:\\1)?";

}
