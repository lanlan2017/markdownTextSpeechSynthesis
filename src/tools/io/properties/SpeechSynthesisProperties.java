package tools.io.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class SpeechSynthesisProperties
{
	private static String rootDir = null;
	public static String getRootDir()
	{
		if (rootDir == null)
		{
			Properties pr = new Properties();
			try
			{
				pr.load(new InputStreamReader(
						new FileInputStream(
								new File("SpeechSynthesis.properties")),
						"utf-8"));
				rootDir = (String) pr.get("audioFileRootDir");
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return rootDir;
	}
}
