package demo;

import java.util.Scanner;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechUtility;
import replace.Replace;
import tools.io.reader.PropertiesReader;

public class DemoList
{
	static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args)
	{
		SpeechUtility.createUtility(SpeechConstant.APPID + "="+PropertiesReader.getAPPID());

		// 5设置要合成的文本
		System.out.println(
				"######################################## 讯飞语音合成系统 ########################################");
		System.out.println("输入要合成的文字(以: \"#exit\"作为结束符):");
		StringBuffer sbBuffer = new StringBuffer(10240);
		String line = null;
		while (scanner.hasNext())
		{
			line = scanner.nextLine();
			if ("#exit".equals(line))
			{
				break;
			}
			sbBuffer.append(line.concat("\r\n"));
		}
		System.out.println(
				"---------------------------------- 特殊字符处理 ----------------------------------");
		// 处理输入文本,替换容易读错的特殊字符.
		String Input = Replace.replaceTitle(sbBuffer.toString());
		Input = Replace.replaceEnglish(Input);
		System.out.println(Input);
		System.out.println(
				"---------------------------------- 特殊字符处理 ----------------------------------");

		String[] fileNameArr = new String[]
		{"小燕xiaoyan.pcm", "小研vixy.pcm", "小琪vixq.pcm", "小宇xiaoyu.pcm",
				"小峰vixf.pcm"};
		String[] voiceNameArr = new String[]
		{"xiaoyan", "vixy", "vixq", "xiaoyu", "vixf"};
		for (int i = 0; i < fileNameArr.length; i++)
		{
			DemoListRunable runable=new DemoListRunable(voiceNameArr[i], fileNameArr[i], Input);
			DemoListRunable.isEnd=false;
			Thread thread=new Thread(runable);
			thread.start();
			//等待下载完毕
			while(!DemoListRunable.isEnd)
			{
				try
				{
					Thread.sleep(1000);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
