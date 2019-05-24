package replace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.iflytek.cloud.b.b.e;

public class RemoveMarkDownTags
{
	/**
	 * �Ƴ�markdown���Ķ������.
	 * 
	 * @param line
	 *            markdown�ı�.
	 * @return û�ж��뷽ʽ��markdown�ı�
	 */
	public static String removeMDTable(String line)
	{
		line = line.replaceAll("\\|(:?\\-+:?\\|)+\\n", "");
		line = line.replaceAll("(?m)^\\|", "");
		return line;
	}

	/**
	 * �Ƴ�markdown������.
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
			// ��ȡƥ���ı�
			matcherStr = matcher.group(2);

			matcherStr = MDCodeReplace.replaceContainSpecialWords(matcherStr);
			matcherStr = matcherStr.replace("-", "��");
			matcherStr = matcherStr.replace(".", "��");
			matcherStr = " " + matcherStr + " ";
			matcher.appendReplacement(sb, matcherStr);
		}
		matcher.appendTail(sb);
		return sb.toString();
		// return line.replaceAll("(#+\\s*?)([^#]+)(?:\\1)?", "$2");
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
		return line.replaceAll("(\\*{1,2})([^*]+)(?:\\1)?", "$2");
	}
	/**
	 * �Ƴ�markdown����α��
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
			// ��ȡƥ���ı�
			matcherStr = matcher.group(1);
			// �滻���׶���ķ���
			// �������:��,��
			// ���������
			// ����ƥ���ض�������ʽ�����
			matcherStr = MDCodeReplace.replaceMatcher(matcherStr);
			// ����ƥ���ض����ʵ����
			matcherStr = MDCodeReplace.replaceSpecialWords(matcherStr);
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
		line = line.replaceAll("\\[([\\u4e00-\\u9fa5]*?)\\]\\(/blog/.+?/\\)",
				" $1 ");
		// �滻վ�ⳬ����
		line = line.replaceAll("\\[.*?]\\((https?://.+?)\\)", "");
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
			// sql = sql.replace("(", "���ſ�ʼ");
			// sql = sql.replace(")", "���Ž���");
			// sql = sql.replace(";", "�ֺ�");
			// codeBlockMatcher.appendReplacement(sb,
			// "sequel��俪ʼ\r\n" + sql + "\r\nsequel������");
			// } else
			// {
			// codeBlockMatcher.appendReplacement(sb, "");
			// }
			// ɾ�������
			codeBlockMatcher.appendReplacement(sb, "");
		}
		codeBlockMatcher.appendTail(sb);
		// �Ƴ�Markdown�������.
		// line = line.replaceAll("``\\w*\\r?\\n(.*\\r?\\n)+?```", "");

		return sb.toString();
		// return line.replaceAll("```(\\w+)?", "").trim();
	}
	/**
	 * �Ƴ�markdown,���ÿ顣
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
				"---------------------- ȡ��markdown��� ��ʼ  ------------------------");
		// ȥ��markdown����# h1 #,## h2 ##,### h3 ###,#### h4 ####,......
		input = removeMDTitle(input);
		// ȥ��markdown�Ӵ�: **xxxx**,����б��*xxxx*
		input = removeMDStrongOrItalic(input);
		// ȥ��Markdown�ĵ��е�ͼƬ��ǩ
		input = removeMDIMG(input);
		// �Ƴ�Markdown�ĵ��еĳ�����
		input = removeMDLink(input);
		// �Ƴ�markdown�еĴ�����ǲ���
		input = removeMDCodeBlock(input);
		// �Ƴ�markdown�еĴ���α�ǲ���.
		input = removeMDCode(input);
		// �Ƴ�markdown�����е����ÿ�,��Ҫ���ɴ��ڷ���
		input = removeMDQuoteBlock(input);
		// �Ƴ�markdown���������,
		input = removeMDTable(input);
		System.out.println(input);
		System.out.println(
				"---------------------- ȡ��markdown��� ����  ------------------------");
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
		String testStr = "�޸ı�ṹʹ��`alter table`���޸ı�ṹ����`�����ж���`��`�޸��ж���`��`ɾ����`��`��������`�Ȳ����������ж�����﷨����:\r\n"
				+ "```java\r\n" + "alter table ����\r\n" + "add\r\n" + "(\r\n"
				+ "    colum_nam1 datatype [default expr],\r\n" + "    ...\r\n"
				+ ");\r\n" + "```";
		System.out.println(testStr);
		System.out.println(
				"##################################### �滻���: #####################################");
		// System.out.println(removeMDQuoteBlock(testStr));
		System.out.println(replaceMD(testStr));
	}
}
