package tools.io.reader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader
{
	// 讯飞语音合成APPID
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
	 * 从.Properties配置文件中读取初始化参数.
	 * @param filepath 配置文件的字符串路径.
	 * @return 
	 */
	private static void initByProperties(String filePath)
	{
		// 1 实例化
		Properties properties = new Properties();
		try (InputStream inputStream = new FileInputStream(filePath);)
		{
			// 2加载配置文件到Properties对象中
			properties.load(inputStream);
			// 3读取配置文件中的站点信息
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
