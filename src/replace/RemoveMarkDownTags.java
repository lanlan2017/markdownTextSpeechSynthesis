package replace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import clipboard.util.SysClipboardUtil;
import regex.RegexMarkdown;
import replace.md.table.MDTableReplace;

public class RemoveMarkDownTags
{
	/**
	 * �Ƴ�markdown������.
	 * 
	 * @param line
	 * @return
	 */
	public static String removeMDTitle(String line)
	{
		// Pattern pattern =
		// Pattern.compile("(#+)(\\s+)([^#]+)(?:\\2)?(?:\\1)?");
		Pattern pattern = Pattern.compile(RegexMarkdown.MDTitleRegex);
		Matcher matcher = pattern.matcher(line);
		StringBuffer sb = new StringBuffer();
		String matcherStr;
		while (matcher.find())
		{
			// ��ȡƥ���ı�,��ɾ��ͷ����β���հ׷�
			matcherStr = matcher.group(3).trim();
			// System.out.println("��׽������:��ʼ����------");
			// System.out.println(" ����:" + matcherStr);

			matcherStr = MDCodeReplace.replaceContainSpecialWords(matcherStr);
			// �滻�����ַ�
			matcherStr = MDCodeReplace.replaceSpecialChars(matcherStr);
			// System.out.println("������:" + matcherStr);
			// System.out.println("��׽������:�������------");
			matcher.appendReplacement(sb, matcherStr);
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
	/**
	 * �Ƴ�markdownͼƬ���.
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
			// ��ƥ�䵽��ͼƬ�滻�ɿ��ַ�����
			matcher.appendReplacement(sb, "");
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * �Ƴ�markdownб����,�Ƴ�markdown�Ӵֱ�ǡ�
	 * 
	 * @param line
	 * @return
	 */
	public static String removeMDStrongOrItalic(String line)
	{
		return line.replaceAll(RegexMarkdown.MDStrongOrItalicRegex, "$2");
	}
	/**
	 * �Ƴ�markdown����α��
	 * 
	 * @param line
	 * @return
	 */
	public static String removeMDCode(String line)
	{
		// Pattern pattern = Pattern.compile("`(.+?)`");
		Pattern pattern = Pattern.compile(RegexMarkdown.MDCodeInLineRegex);
		Matcher matcher = pattern.matcher(line);
		StringBuffer sb = new StringBuffer();
		String matcherStr;
		while (matcher.find())
		{
			// ��ȡƥ���ı�
			matcherStr = matcher.group(1);

			// ����ƥ���ض����ʵ����
			matcherStr = MDCodeReplace.replaceSpecialWords(matcherStr);
			// ����ƥ��������ʽ�����
			matcherStr = MDCodeReplace.replaceMatcher(matcherStr);
			// ����ƥ���ض��ַ������
			matcherStr = MDCodeReplace.replaceSpecialChars(matcherStr);
			// �滻���׶����ĵ���
			matcherStr = MDCodeReplace.replaceContainSpecialWords(matcherStr);
			// ��ӿո��û����˺�ʶ��
			// matcherStr = " " + matcherStr + " ";
			matcher.appendReplacement(sb, matcherStr);
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
	/**
	 * �Ƴ�markdown,�����ӡ�
	 * 
	 * @param line
	 * @return
	 */
	public static String removeMDLink(String line)
	{
		// return sb.toString();
		// �滻����վ�ڳ�����
		// line = line.replaceAll("\\[(.*)\\]\\(.+\\)", " $1 ");
		line = line.replaceAll(RegexMarkdown.MDLinkRegex, " $1 ");
		// �滻վ�ⳬ����
		// line = line.replaceAll("\\[.*?]\\((https?://.+?)\\)", "");
		// line=line.replaceAll("\\[.*?]\\((.+?)\\)", "");
		return line;
	}
	/**
	 * �Ƴ�markdown,����顣
	 * 
	 * @param line
	 * @return
	 */
	public static String removeMDCodeBlock(String line)
	{
		Pattern codeBlockPattern = Pattern
				.compile(RegexMarkdown.MDCodeBlockRegex);
		Matcher codeBlockMatcher = codeBlockPattern.matcher(line);
		StringBuffer sb = new StringBuffer();
		while (codeBlockMatcher.find())
		{
			// System.out.println("���񵽴����:��ʼ-----------");
			// System.out.println(codeBlockMatcher.group());
			// System.out.println("���񵽴����:����-----------");
			codeBlockMatcher.appendReplacement(sb, "");
		}
		codeBlockMatcher.appendTail(sb);
		return sb.toString();
	}
	/**
	 * �Ƴ�markdown,���ÿ顣
	 * 
	 * @param line
	 * @return
	 */
	public static String removeMDQuoteBlock(String line)
	{
		return line.replaceAll(RegexMarkdown.MDQuoteBlockRegex, "").trim();
	}
	/**
	 * �Ƴ�markdown,�����б��
	 * 
	 * @param line
	 * @return
	 */
	public static String removeMDUnorderListBlock(String line)
	{
		return line.replaceAll(RegexMarkdown.MDUnOrderListBlockRegex, "")
				.trim();
	}
	/**
	 * @param input
	 * @return
	 */
	public static String replaceMD(String input)
	{
//		System.out.println(
//				"---------------------- ȡ��markdown��� ��ʼ  ------------------------");
		// ȥ��markdown�Ӵ�: **xxxx**,����б��*xxxx*
		input = removeMDStrongOrItalic(input);
		// �Ƴ�markdown�����е� ���ÿ�,��Ҫ���ɴ��ڷ���
		input = removeMDQuoteBlock(input);
		// �Ƴ�markdown�����е� �����б���.
		input = removeMDUnorderListBlock(input);

		// ȥ��Markdown�ĵ��� ��ͼƬ��ǩ
		input = removeMDIMG(input);
		// �Ƴ�Markdown�ĵ��е� ������
		input = removeMDLink(input);

		// �Ƴ�markdown�е� ���ڴ���
		input = removeMDCodeBlock(input);
		// ȥ��markdown����# h1 #,## h2 ##,### h3 ###,#### h4 ####,......
		input = removeMDTitle(input);

		// �ʶ�Markdown���,���Ҫ����removeMDCode(input)֮ǰ
		input = MDTableReplace.MDTableSpeech(input);
		// �Ƴ�markdown�еĴ���α�ǲ���.
		input = removeMDCode(input);
		// System.out.println(input);
		// System.out.println(
		// "---------------------- ȡ��markdown��� ���� ------------------------");
		return input;
	}
	public static void main(String[] args)
	{
		// String testStr = "����ͨ�������`URL`���������`JSP`ҳ�棺\r\n"
		// +
		// "[http://localhost:8080/app08a/countries.jsp](http://localhost:8080/app08a/countries.jsp)";
		// String testStr = "# 12.1.2 ʵʩ��ȫԼ�� #\r\n"
		// +
		// "**`WEB-INF`Ŀ¼�µ���Դ�ͻ��˲���ֱ��ͨ��`URL`����**,����,���ǿ���ͨ��`Servlet`��`JSP`ҳ�����`WEB-INF`Ŀ¼�µ���Դ��"
		// + "### web-resource-collection��Ԫ�� ###\r\n"
		// +
		// "**`web-resource-collection`Ԫ�ر�ʾ��Ҫ���Ʒ��ʵ���Դ����**������`web-resource-name`��`description`��`url-pattern`��`http-method`��`http-method-ommission`����Ԫ�ء�\r\n"
		// + "- `web-resource-name`��Ԫ�������������ܱ�����Դ����������ơ�\r\n"
		// + "��÷�������:`-1`\r\n" + "����Ϊ:`ISO-8859-1`\r\n" + "��ȡ�ڲ���`id`ֵ\r\n"
		// + "`url`\r\n" + "`MIME`\r\n" + "`GET`����\r\n";
		// String testStr = "�޸ı�ṹʹ��`alter
		// table`���޸ı�ṹ����`�����ж���`��`�޸��ж���`��`ɾ����`��`��������`�Ȳ����������ж�����﷨����:\r\n"
		// + "```java\r\n" + "alter table ����\r\n" + "add\r\n" + "(\r\n"
		// + " colum_nam1 datatype [default expr],\r\n" + " ...\r\n"
		// + ");\r\n" + "```";
		String testStr = SysClipboardUtil.getSysClipboardText();
		System.out.println(testStr);
		System.out.println(
				"##################################### �滻���: #####################################");
		// System.out.println(removeMDQuoteBlock(testStr));
		System.out.println(replaceMD(testStr));
	}
}
