package tools.io.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MyStringWriter
{
	/**
	 * @param input
	 */
	public static void writerString(String input)
	{
		try (BufferedWriter bufferedWriter = new BufferedWriter(
				new FileWriter(new File("�ı��滻���.txt"))))
		{
			bufferedWriter.write(input);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
