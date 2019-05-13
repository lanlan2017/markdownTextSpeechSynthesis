package tools.io.reader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader
{
	// Ѷ�������ϳ�APPID
	private static String APPID = null;
	// 
	public static String getAPPID()
	{
		if(APPID==null)
		{
			initByProperties("APPID.properties");
		}
		return APPID;
	}
	/**
	 * ��.Properties�����ļ��ж�ȡ��ʼ������.
	 * @param filepath �����ļ����ַ���·��.
	 * @return 
	 */
	private static void initByProperties(String filePath)
	{
		// 1 ʵ����
		Properties properties = new Properties();
		try (InputStream inputStream = new FileInputStream(filePath);)
		{
			// 2���������ļ���Properties������
			properties.load(inputStream);
			// 3��ȡ�����ļ��е�վ����Ϣ
			APPID = properties.getProperty("APPID");
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public static void main(String[] args)
	{
		System.out.println(getAPPID());
	}
	
}
