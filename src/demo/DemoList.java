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

		// 5����Ҫ�ϳɵ��ı�
		System.out.println(
				"######################################## Ѷ�������ϳ�ϵͳ ########################################");
		System.out.println("����Ҫ�ϳɵ�����(��: \"#exit\"��Ϊ������):");
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
				"---------------------------------- �����ַ����� ----------------------------------");
		// ���������ı�,�滻���׶���������ַ�.
		String Input = Replace.replaceTitle(sbBuffer.toString());
		Input = Replace.replaceEnglish(Input);
		System.out.println(Input);
		System.out.println(
				"---------------------------------- �����ַ����� ----------------------------------");

		String[] fileNameArr = new String[]
		{"С��xiaoyan.pcm", "С��vixy.pcm", "С��vixq.pcm", "С��xiaoyu.pcm",
				"С��vixf.pcm"};
		String[] voiceNameArr = new String[]
		{"xiaoyan", "vixy", "vixq", "xiaoyu", "vixf"};
		for (int i = 0; i < fileNameArr.length; i++)
		{
			DemoListRunable runable=new DemoListRunable(voiceNameArr[i], fileNameArr[i], Input);
			DemoListRunable.isEnd=false;
			Thread thread=new Thread(runable);
			thread.start();
			//�ȴ��������
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
