package replace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
			// System.out.println("-----------------------------------------------");
			// System.out.println(matcher.group());
			// System.out.println("-----------------------------------------------");
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
		// Pattern pattern=Pattern.compile("(\\*\\*)([^*]+)(?:\\1)?");
		// Matcher matcher=pattern.matcher(line);
		// while(matcher.find())
		// {
		// System.out.println(matcher.group(2));
		// }
		return line.replaceAll("(\\*{1,2})([^*]+)(?:\\1)?", "$2");
	}
	/**
	 * �Ƴ�markdown����α��
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
//		line = line.replace(",", "����");
//		line = line.replace("#", "����");
//		line = line.replace(".", "��");
//		line = line.replace(";", "�ֺ�");
//		line = line.replace("%", "�ٷֺ�");
//		line = line.replace("?", "�ʺ�");
		return line;
	}
	/**
	 * �Ƴ�markdown���������.
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
	 * �Ƴ�markdown,�����ӡ�
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
//			link = link.replaceAll(":", "ð��");
//			link = link.replaceAll("/", "б��");
//			matcher.appendReplacement(sb, link);
//		}
//		matcher.appendTail(sb);
//		// line = line.replaceAll("\\[.*?]\\((https?://.+?)\\)", "$1").trim();
//		// line = line.replaceAll(":", "ð��");
//		// line = line.replaceAll("/", "б��");
//		return sb.toString();
//		line=line.replaceAll("\\[.*?]\\((https?://.+?)\\)", "");
		line=line.replaceAll("\\[.*?]\\((.+?)\\)", "");
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
		// Pattern pattern=Pattern.compile("(\\*\\*)([^*]+)(?:\\1)?");
		// Matcher matcher=pattern.matcher(line);
		// while(matcher.find())
		// {
		// System.out.println(matcher.group(2));
		// }
		// �Ƴ�Markdown�������.
		line = line.replaceAll("```\\w*\\r?\\n(.+\\r?\\n)+?```", "");
		return line;
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
				"---------------------- ȡ��markdown��� ��ʼ  ------------------------");
		// ȥ��markdown����# h1 #,## h2 ##,### h3 ###,#### h4 ####,......
		input = RemoveMarkDownTags.removeMDTitle(input);
		// ȥ��markdown�Ӵ�: **xxxx**,����б��*xxxx*
		input = RemoveMarkDownTags.removeMDStrongOrItalic(input);
		// ȥ��Markdown�ĵ��е�ͼƬ��ǩ
		input = RemoveMarkDownTags.removeMDIMG(input);
		// �Ƴ�Markdown�ĵ��еĳ�����
		input = RemoveMarkDownTags.removeMDLink(input);
		// System.out.println(input);
		// �Ƴ�markdown�еĴ�����ǲ���
		input = RemoveMarkDownTags.removeMDCodeBlock(input);
		// System.out.println("-------------------------");
		// System.out.println(input);
		// �Ƴ�markdown�еĴ���α�ǲ���.
		input = RemoveMarkDownTags.removeCode(input);
		// �Ƴ�markdown�����е����ÿ�,��Ҫ���ɴ��ڷ���
		input = RemoveMarkDownTags.removeMDQuoteBlock(input);
		System.out.println(input);
		System.out.println(
				"---------------------- ȡ��markdown��� ��ʼ  ------------------------");
		return input;
	}
	public static void main(String[] args)
	{
		// String testStr="# HTTP��� # ";
		// String testStr="## HTTP��� ## ";
		// String testStr="#### HTTP��� #### ";
		// String testStr="##### HTTP��� ##### ";
		// String testStr = "###### HTTP��� ###### ";
		// System.out.println(testStr);
		// System.out.println(removeMDTitle(testStr));

		// String testStr =
		// "*TCP*����ȷ����һ������ڵ�����һ������ڵ�**���͵��ļ�����Ϊһ���������ļ�����Ŀ�ĵ�**,�����ھ��崫�͹���������ļ����ܻ�ֽ�ΪС�鴫�䡣\r\n"
		// + "IP��һ���ײ�Э��**,��������ݿ�(���ݰ�)��·�ƶ�/·�ɵ�Ŀ�ĵ�**��";
		// String testStr =
		// "*HTTP*��TCP/IP��*�ϲ�Э��*����������Щ����Э�黹��̫��Ϥ,������ṩһ���ǳ��򵥵Ľ���:";
		// System.out.println(testStr);
		// System.out.println(removeMDStrongOrItalic(testStr));

		// String testStr = "![������һ��ͼƬ](https://www.baidu.com/baidu.png)";
		// String testStr = "�������ָ���������Ӳ���������ҵ�һ��TCP�˿ڡ�\r\n" +
		// "һ����,����������65536���˿�,�˿ں�Ϊ0~65535����һ����,�˿ڲ�����ʾһ�����Բ��������豸��λ�á�����ֻ�Ǳ�ʾ������Ӧ�õġ��߼��������ѡ�\r\n"
		// +
		// "![������һ��ͼƬ](https://image-1257720033.cos.ap-shanghai.myqcloud.com/blog/readbooknote/HeadFirstServletsAndJSP/DiYiZhang/ChangYongDuanKou.png)\r\n"
		// +
		// "\r\n" +
		// "���ÿ���˿���ʹ��һ��������Ӧ��,��һ��������������������65536����ͬ�ķ�����Ӧ�á�";
		// String testStr = "> **ע��**\r\n" +
		// "> 2011�꣬��׼����֯IETF������WebSocketЭ�飬��RFC
		// 6455�淶����Э������һ��HTTP��������ΪWebSocket���ӣ�֧��˫��ͨ�ţ����ʹ�÷���˿���ͨ��WebSocketЭ����������ͬ�ͻ��˵ĻỰͨ�š�";
		// String testStr = "��¼C�� ��SSL֤�顱 �������������KeyTool������ �ɹ�Կ/˽Կ�ԣ�����������֤�顣";
		// String testStr = "�������е�ʾ��Ӧ��ѹ��������ͨ�����µ�ַ��
		// �أ�[http://books.brainysoftware.com/download](http://books.brainysoftware.com/download)";
//		String testStr = "���磬�����`set`��ǩ�������ַ�����`Hello World`���������������´�����ҳ�淶Χ����`hello`��\r\n"
//				+ "```jsp\r\n"
//				+ "<c:set  value=\"Hello World\" var=\"hello\"/>\r\n"
//				+ "```\r\n"
//				+ "�����`set `��ǩ�򴴽���һ����Ϊ`job`���н����������������Χ��`position`�����õĶ��󡣱���`job `�ķ�ΧΪ`page`��\r\n"
//				+ "```jsp\r\n"
//				+ "<c:set var=\"job\" value=\"${requestScope.position}\" scope=\"page\"/>\r\n"
//				+ "```\r\n" + "";
		String testStr="����ͨ�������`URL`���������`JSP`ҳ�棺\r\n" + 
				"[http://localhost:8080/app08a/countries.jsp](http://localhost:8080/app08a/countries.jsp)";
		System.out.println(testStr);
		System.out.println(
				"##################################### �滻���: #####################################");
		// System.out.println(removeMDQuoteBlock(testStr));
		System.out.println(replaceMD(testStr));
	}
}
