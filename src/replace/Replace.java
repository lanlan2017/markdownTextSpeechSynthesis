package replace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Replace
{
	public static String replaceTitle(String text)
	{
		Pattern titlePattern = Pattern.compile("(?:\\w+)?(?:(\\.)\\w+)+");
		Matcher titleMatcher = titlePattern.matcher(text);
		StringBuffer sb = new StringBuffer();
		String line = null;
		boolean titleFind = false;
		int end=0;
		// find()������ǰ����ƥ����Ӵ�
		while ((titleFind = titleMatcher.find()))
		{
			if (titleFind)
			{
//				start=titleMatcher.start();
				end=titleMatcher.end();
//				System.out.println(text.charAt(end));
				//99.99999%���������Ҫ�滻���
				if('%'==text.charAt(end))
				{
					continue;
				}
//				System.out.println(text.charAt(end)+1);
				
				// ��ȡλ����ƥ���Ӵ�֮ǰ���ı�strbefore����ȡƥ����Ӵ�strmacther��Ȼ���ƥ����Ӵ�strmacther�滻Ϊreplacement��
				// Ȼ��(strbefore+replacement)׷�ӵ�StringBuffer��
				
				line = titleMatcher.group();
				line = line.replaceAll("\\.", "��");
				titleMatcher.appendReplacement(sb, line);
			}
		}
		// ���ı���û��ƥ���ʣ�µ��ı�Ҳ���뵽StringBuffer��
		titleMatcher.appendTail(sb);
		return sb.toString();
	}
	public static String replaceEnglish(String text)
	{
		Pattern englishPattern = Pattern.compile("[a-zA-Z]+");
		Matcher englishMatcher = englishPattern.matcher(text);

		StringBuffer sb = new StringBuffer();
		String line = null;
		// find()������ǰ����ƥ����Ӵ�
		boolean englishFind = false;
		while ((englishFind = englishMatcher.find()))
		{
			if (englishFind)
			{
				// ��ȡλ����ƥ���Ӵ�֮ǰ���ı�strbefore����ȡƥ����Ӵ�strmacther��Ȼ���ƥ����Ӵ�strmacther�滻Ϊreplacement��
				// Ȼ��(strbefore+replacement)׷�ӵ�StringBuffer��
//				line = "`".concat(englishMatcher.group().concat("`"));
//				line = "`".concat(englishMatcher.group());//���Ч������
//				line =" ".concat(englishMatcher.group());//���Ч��Ҳ����
				
				line =englishMatcher.group();//���Ч��Ҳ����
//				���java�ؼ���.
				line=ReplaceJavaKeyWord.replaceJavaKeyWord(line);
				
				englishMatcher.appendReplacement(sb, line);
			}

		}
		// ���ı���û��ƥ���ʣ�µ��ı�Ҳ���뵽StringBuffer��
		englishMatcher.appendTail(sb);
		return sb.toString();
	}
	public static void main(String[] args)
	{
		 String text = "99.9999%��servlet����HttpServlet�����ࡣ\r\n" + 
		 		"\r\n" + 
		 		"��ʵ����,99.9%��servlet���Ḳ��doGet()��doPost()������\r\n" + 
		 		"\r\n" + 
		 		"ע�⣬servletû��main()������servlet���������ڷ�������doGet()�������������á�\r\n" + 
		 		"\r\n" + 
		 		"servlet��doGet()�����Ĳ����б����õ������������������Ӧ��������á�";
		// StringBuffer sb=new StringBuffer(text);
		// replaceTitle(text);
		 System.out.println(replaceTitle(text));

//		String text = "java.lang.Object";
//		System.out.println(replaceTitle(text));

	}
}
