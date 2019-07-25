package tools.io.properties;

import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import clipboard.util.SysClipboardUtil;

public class ContainSpecialWordsProperties
{
	// 1.创建配置文件对象
	private static Properties pr = null;
	private static Set<String> keySet;
	// 3.使用当前配置文件中的文本替换匹配的文本
	public static String repalceByProperties(String input)
	{
		if (pr == null)
		{
			pr = PropertiesInstance.getPropertiesInstanceUTF8(
					"ContainSpecialWords.properties");
			keySet = pr.stringPropertyNames();
		}
		for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();)
		{
			String key = (String) iterator.next();
			if (input.contains(key))
			{
				input = input.replaceAll("\\b" + key + "\\b",
						pr.getProperty(key));
			}
		}
		return input;
	}
	public static void main(String[] args)
	{
		String input = SysClipboardUtil.getSysClipboardText();
		System.out.println(repalceByProperties(input));
	}
}
