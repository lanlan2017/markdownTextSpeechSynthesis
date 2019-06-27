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
	 * ���ɲ���������·��.
	 * 
	 * @param fileName
	 * @param count
	 * @return
	 */
	public static String fileNamePart(String fileName, int count)
	{
		String fileNamePart;
		// ��һ������ʹ��ԭ�����ļ���
		fileNamePart = fileName.substring(0, fileName.lastIndexOf(".pcm"))
				+ (count) + fileName.substring(fileName.lastIndexOf(".pcm"));
		return fileNamePart;
	}
	/**
	 * ��ȡ����������ļ����б�.
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
	 * ʹ��Auditon����Ƶ.
	 * 
	 * @param filePath
	 */
	public static void openFileUseAudition(String filePath)
	{
		String audition = "F:\\���\\��װ��_��Ƶ����\\Adobe\" \"Audition\" \"CS6\\Adobe\" \"Audition\" \"CS6.exe ";
		// String filePath = "G:\\Desktop\\�����ϳ�\\���Java���������\\��13��\\13.2.4 DDL���
		// 2.�޸ı�ṹ���﷨.pcm";
		audition += "\"" + filePath + "\"";
		Command.exeCmd("cmd /c start " + audition);
	}
	/**
	 * �����߳�.
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
	 * ����Ѷ�������ϳ����е������ı�.
	 * 
	 * @param input
	 *            Ҫ�ϳɵ��ı�.
	 * @param maxSize
	 *            Ѷ��SDKÿ�������յ���������,�Ҳ�����,�����Ϊ4000û������.
	 * @param fileName
	 *            Ҫ�ϳ����ɵ��ļ�.
	 */
	public static void xunfeiAll(String input, int maxSize, String fileName)
	{
		System.out.println("�ַ����ܳ���:" + input.length());
		// �����ַ����ĳ���
		int inputLength = input.length();
		// ��ǰ�ʶ����ı�.
		String now;
		// ������
		int count = 0;
		// ��ֵ��ļ���
		String fileNamePart;
		while (inputLength > maxSize)
		{
			// ����2
			int splitIndex = 0;
			char end;
			for (int i = maxSize; i > 0; i--)
			{
				end = input.charAt(i);
				if ('\n' == end || '��' == end)
				{
					splitIndex = i;
					break;
				}
			}
			// ��ȡ��ǰ���ı�.
			now = input.substring(0, splitIndex + 1);
			System.out.println(
					"------------------------------------------------");
			System.out.println(now);
			// ��̬�����ļ���
			fileNamePart = fileNamePart(fileName, count++);
			// �ѵ�ǰ�ĺϳɵ�ǰ���ı�now,���浽fileNamePart
			xunfeiOnes(now, fileNamePart);

			// ����ʣ�µ��ı�
			input = input.substring(splitIndex + 1);
			// ����ʣ���ı��ĳ���
			inputLength = input.length();
		}
		// ���ʣ�µ��ı�
		System.out.println("------------------------------------------------");
		System.out.println(input);
		fileNamePart = fileNamePart(fileName, count);
		xunfeiOnes(input, fileNamePart);
		// �ϲ�Ϊһ���ļ�,����audition������ļ�.
		mergeAndOpenFile(fileName, XunFeiTools.getFileNameList());
	}
	/**
	 * @param input
	 * @param fileName
	 */
	public static void xunfeiOnes(String input, String fileNamePart)
	{
		new XunFei(input, fileNamePart).xunfei();
		wait = true;
		// �ȴ��ϴ�ִ�����
		waitFor();
	}
	/**
	 * �ϲ��ļ�ArrayList�еĶ��Դ�ļ�Ϊһ���ļ�.
	 * 
	 * @param targetFilePath
	 *            Ŀ���ļ�·�������ַ���.
	 * @param sourceFilePathList
	 *            ���Դ�ļ���·�������ַ�����ArrayList����.
	 */
	public static void merge2TargetFileDeleteSourceFile(String targetFilePath,
			ArrayList<String> sourceFilePathList)
	{
		// ���ArrayList���ж���
		if (sourceFilePathList.size() > 0)
		{
			BufferedInputStream in;
			try (BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream(new File(targetFilePath)));)
			{

				System.out.println("Դ�ļ��б�:");
				for (Iterator<String> iterator = sourceFilePathList
						.iterator(); iterator.hasNext();)
				{
					String sourceFilePath = iterator.next();
					System.out.println("    " + sourceFilePath);

					File sourceFile = new File(sourceFilePath);
					in = new BufferedInputStream(
							new FileInputStream(sourceFile));
					// ��������
					byte[] buffer = new byte[2048];
					// ÿ�ζ�����ֽ�����
					int inSize = -1;
					// ���������ֽڵ�buffer������,�����ض�����Լ�������inSize
					while ((inSize = in.read(buffer)) != -1)
					{
						// ��buffer�����е��ֽ�д�������(Ҳ����Ŀ���ļ�)
						out.write(buffer, 0, inSize);
					}
					// �ر�Դ�ļ�
					in.close();
					// ɾ�����Դ�ļ�
					sourceFile.delete();
				}
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	public static void mergeAndOpenFile(String targetFilePath,
			ArrayList<String> sourceFilePathList)
	{
		// �ϲ�list�е�Դ�ļ���Ŀ���ļ�,��ɾ��Դ�ļ�
		merge2TargetFileDeleteSourceFile(targetFilePath, sourceFilePathList);
		// �򿪺ϲ�����ļ�
		System.out.println("�򿪺ϲ�����ļ�:" + targetFilePath);
		XunFeiTools.openFileUseAudition(targetFilePath);
	}
}
