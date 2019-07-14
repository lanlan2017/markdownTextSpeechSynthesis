package replace.md.table;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import clipboard.util.SysClipboardUtil;
import static regex.RegexJava.javaMethedRegex;
//����������ʽ
import static regex.RegexMarkdown.MDTableRegex;
import static regex.RegexMarkdown.MDCodeInLineRegex;

public class MDTableReplace
{
	/**
	 * �ʶ�Markdown���,��������з�������ʱ.
	 * 
	 * @param line
	 *            markdown�ĵ��е������ַ���.
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
			// ��ȡƥ�䵽��һ������
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
	 * �滻����е����ڴ���.
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
			// ɾ��������
			group1 = matcher.group(1);
			// �ʶ�Java����
			group1 = replaceJavaMethod(group1);
			// �滻ԭ��ƥ����ı�
			matcher.appendReplacement(sb, group1);
		}
		// ��Ӻ���û��ƥ����ı�
		matcher.appendTail(sb);
		return sb.toString();
	}
	/**
	 * java�������ʶ�����.
	 * 
	 * @param matcherStr
	 *            java���������ַ���.
	 * @return �ʶ����java����.
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
			// ��ȡƥ�䵽��һ������
			group1 = matcher.group(1);
			group2 = matcher.group(2);
			group3 = matcher.group(3);
			// System.out.println("���񵽱��:��ʼ����----------");
			// System.out.println(" group1-->" + group1);
			// System.out.println(" group2-->" + group2);
			// System.out.println(" group3-->" + group3);
			// matcherStr = group2 + " ���� ";
			if (!group1.equals(group2))
			{
				matcherStr = matcherStr + " �÷������� " + group1;
			}
			if (!"".equals(group3))
			{
				matcherStr = matcherStr + " �÷��������б� "
						+ group3.replace(",", "����");
			} else
			{
				matcherStr = matcherStr + " �÷������޲η��� ";
			}
			matcherStr = matcherStr + " �÷������� ";
			// System.out.println(" �滻���:" + matcherStr);
			// System.out.println("���񵽱��:�������----------");
		}
		return matcherStr;
	}
	public static void main(String[] args)
	{
		String input = SysClipboardUtil.getSysClipboardText();
		System.out.println(MDTableSpeech(input));
	}
}
