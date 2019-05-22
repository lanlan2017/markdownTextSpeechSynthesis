package tools.io.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class ContainSpecialWordsProperties
{
	// 1.���������ļ�����
	private static Properties pr = null;
	private static Set<String> keySet;
	// 3.ʹ�õ�ǰ�����ļ��е��ı��滻ƥ����ı�
	public static String repalceByProperties(String input)
	{
		if (pr == null)
		{
			try
			{
				pr = new Properties();
				// 2.���������ļ�
				pr.load(new InputStreamReader(
						new FileInputStream(
								new File("ContainSpecialWords.properties")),
						"utf-8"));
				// ��ȡ���е�key
				keySet = pr.stringPropertyNames();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();)
		{
			String key = (String) iterator.next();
			if (input.contains(key))
			{
				input = input.replace(key, pr.getProperty(key));
			}
		}
		return input;
	}
}
