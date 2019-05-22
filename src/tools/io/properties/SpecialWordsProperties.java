package tools.io.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * ƥ���ض��ĵ���.
 */
public class SpecialWordsProperties
{
	// 1.ʵ���������ļ�����
	private static Properties properties = null;
	/**
	 * ���������ļ�֮����û����ص�key.
	 * 
	 * @param keyToFind
	 *            ��Ҫ���ҵ�key.
	 * @return ����ҵ�����true,���û���ҵ�����false.
	 */
	public static String replace(String keyToFind)
	{
		if (properties == null)
		{
			try
			{
				// 1.���������ļ��б�
				properties = new Properties();
				// 2.���������ļ�
				properties
						.load(new InputStreamReader(
								new FileInputStream(
										new File("SpecialWords.properties")),
								"utf-8"));
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return properties.getProperty(keyToFind, keyToFind);
	}
}
