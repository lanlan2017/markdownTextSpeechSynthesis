package tools.io.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * ��̬Properties������.
 */
public class PropertiesInstance
{
	/**
	 * ���ݸ����ı���������ļ�����Propertiesʵ��,�����ؼ�ֱ�Ե�ʵ����.
	 * 
	 * @param path
	 *            �����ļ���·��.
	 * @return ���غü�ֵ�Ե�Propertiesʵ��;
	 */
	public static Properties getPropertiesInstance(String path, String charset)
	{
		Properties instance = new Properties();
		try
		{
			instance.load(new InputStreamReader(
					new FileInputStream(new File(path)), charset));
		} catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return instance;
	}
	/**
	 * ����utf-8����������ļ�����Propertiesʵ��,�����ؼ�ֱ�Ե�ʵ����.
	 * 
	 * @param path
	 *            �����ļ���·��.
	 * @return ���غü�ֵ�Ե�Propertiesʵ��;
	 */
	public static Properties getPropertiesInstanceUTF8(String path)
	{
		return getPropertiesInstance(path, "utf-8");

	}
}
