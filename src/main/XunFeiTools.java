package main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import system.call.cmd.Command;

public class XunFeiTools
{
	static boolean wait = true;
	static ArrayList<String> fileNameList = null;
	/**
	 * 生成部分语音的路径.
	 * 
	 * @param fileName
	 * @param count
	 * @return
	 */
	public static String fileNamePart(String fileName, int count)
	{
		String fileNamePart;
		// 第一个部分使用原来的文件名
		fileNamePart = fileName.substring(0, fileName.lastIndexOf(".pcm"))
				+ (count) + fileName.substring(fileName.lastIndexOf(".pcm"));
		return fileNamePart;
	}
	/**
	 * 获取保存的所有文件名列表.
	 * 
	 * @return
	 */
	public static ArrayList<String> getFileNameList()
	{
		return fileNameList;
	}

	public static void setFileNameList(ArrayList<String> fileNameList)
	{
		XunFeiTools.fileNameList = fileNameList;
	}
	/**
	 * 使用Auditon打开音频.
	 * 
	 * @param filePath
	 */
	public static void openFileUseAudition(String filePath)
	{
		String audition = "F:\\软件\\安装包_音频处理\\Adobe\" \"Audition\" \"CS6\\Adobe\" \"Audition\" \"CS6.exe ";
		// String filePath = "G:\\Desktop\\语音合成\\疯狂Java讲义第三版\\第13章\\13.2.4 DDL语句
		// 2.修改表结构的语法.pcm";
		audition += "\"" + filePath + "\"";
		Command.exeCmd("cmd /c start " + audition);
	}
	/**
	 * 阻塞线程.
	 */
	public static void waitFor()
	{
		while (wait)
		{
			try
			{
				Thread.sleep(1000);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	/**
	 * 调用讯飞语音合成所有的输入文本.
	 * 
	 * @param input
	 *            要合成的文本.
	 * @param maxSize
	 *            讯飞SDK每次最多接收的字数上限,我测试了,这个设为4000没有问题.
	 * @param fileName
	 *            要合成生成的文件.
	 */
	public static void xunfeiAll(String input, int maxSize, String fileName)
	{
		System.out.println("字符串总长度:" + input.length());
		// 整个字符串的长度
		int inputLength = input.length();
		// 当前朗读的文本.
		String now;
		// 计数器
		int count = 0;
		// 拆分的文件名
		String fileNamePart;
		while (inputLength > maxSize)
		{
			// 除以2
			int splitIndex = 0;
			char end;
			for (int i = maxSize; i > 0; i--)
			{
				end = input.charAt(i);
				if ('\n' == end || '。' == end)
				{
					splitIndex = i;
					break;
				}
			}
			// 获取当前的文本.
			now = input.substring(0, splitIndex + 1);
			System.out.println(
					"------------------------------------------------");
			System.out.println(now);
			// 动态生成文件名
			fileNamePart = fileNamePart(fileName, count++);
			// 把当前的合成当前的文本now,保存到fileNamePart
			xunfeiOnes(now, fileNamePart);

			// 更新剩下的文本
			input = input.substring(splitIndex + 1);
			// 更新剩下文本的长度
			inputLength = input.length();
		}
		// 输出剩下的文本
		System.out.println("------------------------------------------------");
		System.out.println(input);
		fileNamePart = fileNamePart(fileName, count);
		xunfeiOnes(input, fileNamePart);
		// 合并为一个文件
		heBing(fileName);
	}
	/**
	 * @param input
	 * @param fileName
	 */
	public static void xunfeiOnes(String input, String fileNamePart)
	{
		new XunFei(input, fileNamePart).xunfei();
		wait = true;
		// 等待上次执行完毕
		waitFor();
	}
	/**
	 * 合并生成的文件.
	 * 
	 * @param fileName
	 */
	public static void heBing(String fileName)
	{
		ArrayList<String> fileNameList = XunFeiTools.getFileNameList();
		System.out.println("一共合成了如下文件:");
		if (fileNameList.size() > 0)
		{
			File file = new File(fileName);
			if (!file.exists())
			{
				try
				{
					file.createNewFile();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			BufferedOutputStream out = null;
			try
			{
				out = new BufferedOutputStream(new FileOutputStream(file));
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			if (out != null)
			{
				BufferedInputStream in;
				try
				{
					for (Iterator<String> iterator = fileNameList
							.iterator(); iterator.hasNext();)
					{
						String partFileName = iterator.next();
						System.out.println("    " + partFileName);
						File partFile = new File(partFileName);
						in = new BufferedInputStream(
								new FileInputStream(partFile));
						byte[] inBytes = new byte[1024];
						int inSize = -1;
						while ((inSize = in.read(inBytes)) != -1)
						{
							out.write(inBytes, 0, inSize);
						}
						// 删除临时文件
						partFile.delete();
					}
				} catch (IOException e)
				{
					e.printStackTrace();
				}
				// 打开合并后的文件
				System.out.println("打开合并后的文件:" + fileName);
				XunFeiTools.openFileUseAudition(fileName);
			}
		}
	}
}
