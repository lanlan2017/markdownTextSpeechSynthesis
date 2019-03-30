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
		return line.replaceAll("(#+\\s*?)([^#]+)(?:\\1)?", "$2").trim();
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
		return sb.toString().trim();
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
		return line.replaceAll("(\\*{1,2})([^*]+)(?:\\1)?", "$2").trim();
	}
	/**
	 * �Ƴ�markdown����α��
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
	 * �Ƴ�markdown,�����ӡ�
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
			link = link.replaceAll(":", "ð��");
			link = link.replaceAll("/", "б��");
			matcher.appendReplacement(sb, link);
		}
		matcher.appendTail(sb);
		// line = line.replaceAll("\\[.*?]\\((https?://.+?)\\)", "$1").trim();
		// line = line.replaceAll(":", "ð��");
		// line = line.replaceAll("/", "б��");
		return sb.toString();
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
		return line.replaceAll("```(\\w+)?", "").trim();
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
		// ȥ��markdown����# h1 #,## h2 ##,### h3 ###,#### h4 ####,......
		input = RemoveMarkDownTags.removeMDTitle(input);
		// ȥ��markdown�Ӵ�: **xxxx**,����б��*xxxx*
		input = RemoveMarkDownTags.removeMDStrongOrItalic(input);
		// ȥ��Markdown�ĵ��е�ͼƬ��ǩ
		input = RemoveMarkDownTags.removeMDIMG(input);
		// ֻ���������Ӳ���,�������ֲ���
		input = RemoveMarkDownTags.removeMDLink(input);
		// �Ƴ�markdown�еĴ�����ǲ���
		input = RemoveMarkDownTags.removeMDCodeBlock(input);
		// �Ƴ�markdown�еĴ���α�ǲ���.
		input = RemoveMarkDownTags.removeCode(input);
		// �Ƴ�markdown�����е����ÿ�,��Ҫ���ɴ��ڷ���
		input = RemoveMarkDownTags.removeMDQuoteBlock(input);
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
//		String testStr = "��¼C�� ��SSL֤�顱 �������������KeyTool������ �ɹ�Կ/˽Կ�ԣ�����������֤�顣";
		// String testStr = "�������е�ʾ��Ӧ��ѹ��������ͨ�����µ�ַ��
		// �أ�[http://books.brainysoftware.com/download](http://books.brainysoftware.com/download)";
		String testStr = "## 4.3.4 paramValues��ʽ���� ##\r\n" + 
				"������ʽ����`paramValues`���Ի�ȡһ��**�������**��**���ֵ**����������ʾһ�������������������`Map`���ϣ�����������Ϊ`key`��ÿ��`key`�Ķ�Ӧֵ��һ��`�ַ�������`�������а�����ָ���������Ƶ�����ֵ����ʹ�ò���ֻ��һ��ֵ����Ҳ��Ȼ����һ��ֻ����һ��Ԫ�ص����顣\r\n" + 
				"\r\n" + 
				"���磬Ϊ�˻��`selectedOptions`�����ĵ�һ��ֵ�͵ڶ���ֵ������ʹ�����±��ʽ��";
		System.out.println(testStr);
		System.out.println(
				"##################################### �滻���: #####################################");
		// System.out.println(removeMDQuoteBlock(testStr));
		System.out.println(removeCode(testStr));
	}
}
