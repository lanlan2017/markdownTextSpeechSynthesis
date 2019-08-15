package tools.io.properties;

import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class ChinesePolysyllabicWordsProperties
{
	// 创建Properties
	static Properties properties = PropertiesInstance
			.getPropertiesInstanceUTF8("ChinesePolysyllabicWords.properties");
	// 获取所有的key
	static Set<Object> keySet = properties.keySet();

	public static String replaceByValue(String input)
	{
		for (Iterator<Object> iterator = keySet.iterator(); iterator.hasNext();)
		{
			String key = (String) iterator.next();
			String value = properties.getProperty(key);
			// 替换该中文多音词
			input = input.replace(key, value);
		}
		return input;
	}
}
