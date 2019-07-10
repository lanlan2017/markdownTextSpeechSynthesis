package tools.io.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * 静态Properties生成器.
 */
public class PropertiesInstance
{
	/**
	 * 根据给定的编码的配置文件生成Properties实例,并加载简直对到实例中.
	 * 
	 * @param path
	 *            配置文件的路径.
	 * @return 加载好键值对的Properties实例;
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
	 * 根据utf-8编码的配置文件生成Properties实例,并加载简直对到实例中.
	 * 
	 * @param path
	 *            配置文件的路径.
	 * @return 加载好键值对的Properties实例;
	 */
	public static Properties getPropertiesInstanceUTF8(String path)
	{
		return getPropertiesInstance(path, "utf-8");

	}
}
