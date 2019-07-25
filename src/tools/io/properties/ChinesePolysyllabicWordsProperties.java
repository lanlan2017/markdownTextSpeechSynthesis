package tools.io.properties;

import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class ChinesePolysyllabicWordsProperties
{
	// ����Properties
	static Properties properties = PropertiesInstance
			.getPropertiesInstanceUTF8("ChinesePolysyllabicWords.properties");
	// ��ȡ���е�key
	static Set<Object> keySet = properties.keySet();

	public static String replaceByValue(String input)
	{
		for (Iterator<Object> iterator = keySet.iterator(); iterator.hasNext();)
		{
			String key = (String) iterator.next();
			String value = properties.getProperty(key);
			// �滻�����Ķ�����
			input = input.replace(key, value);
		}
		return input;
	}
}
