package main;

import java.util.ArrayList;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SpeechUtility;
import com.iflytek.cloud.speech.SynthesizeToUriListener;

public class XunFei
{
	// Ҫ������ַ���.
	String input;
	// ���ɵ��ļ�.
	String fileNamePart;
	// static boolean wait = true;
	// static ArrayList<String> fileNameList = null;
	// 1 ���úϳɼ�����
	private SynthesizeToUriListener synthesizeToUriListener = new SynthesizeToUriListener()
	{
		// progressΪ�ϳɽ���0~100
		public void onBufferProgress(int progress)
		{
		}
		// �Ự�ϳ���ɻص��ӿ�
		// uriΪ�ϳɱ����ַ��errorΪ������Ϣ��Ϊnullʱ��ʾ�ϳɻỰ�ɹ�
		public void onSynthesizeCompleted(String uri, SpeechError error)
		{
			if (error == null)
			{
				System.out.println("�ϳ�:" + fileNamePart + "-->�ɹ�");
				// 8.ʹ��auditon���ļ�.
				// openFileUseAudition(fileName);
				// ��һ�κϳɳɹ�ʱ,�������ϱ���ϳɳɹ����ļ���
				if (XunFeiTools.getFileNameList() == null)
				{
					XunFeiTools.setFileNameList(new ArrayList<String>());
				}
				XunFeiTools.getFileNameList().add(fileNamePart);
			} else
				System.out.println("�ϳ�:" + fileNamePart + "-->ʧ��");
			// ���ٵȴ�,�����ϳ���һ������.
			XunFeiTools.wait = false;
		}
		@Override
		public void onEvent(int arg0, int arg1, int arg2, int arg3, Object arg4,
				Object arg5)
		{
		}
	};
	/**    
	 *  
	 */
	public XunFei(String input, String fileNamePart)
	{
		this.input = input;
		this.fileNamePart = fileNamePart;
	}

	// /**
	// * ��ȡ����������ļ����б�.
	// *
	// * @return
	// */
	// public static ArrayList<String> getFileNameList()
	// {
	// return fileNameList;
	// }

	/**
	 * @return
	 */
	public SpeechSynthesizer xunfeiSettings()
	{
		// �������ļ��ж�ȡAPPID
		SpeechUtility.createUtility(SpeechConstant.APPID + "=" + "5c80ae6b");
		// 3.����SpeechSynthesizer����
		SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer();
		// �������٣���Χ0~100
		mTts.setParameter(SpeechConstant.SPEED, "50");
		// �����������Χ0~100
		mTts.setParameter(SpeechConstant.PITCH, "50");
		// ������������Χ0~100
		mTts.setParameter(SpeechConstant.VOLUME, "50");
		// 4.�ϳɲ������ã������MSC Reference Manual��SpeechSynthesizer ��
		mTts.setParameter(SpeechConstant.VOICE_NAME, "vixy");// С��
		return mTts;
	}
	// /**
	// * ʹ��Auditon����Ƶ.
	// *
	// * @param filePath
	// */
	// public static void openFileUseAudition(String filePath)
	// {
	// String audition = "F:\\���\\��װ��_��Ƶ����\\Adobe\" \"Audition\" \"CS6\\Adobe\"
	// \"Audition\" \"CS6.exe ";
	// // String filePath = "G:\\Desktop\\�����ϳ�\\���Java���������\\��13��\\13.2.4 DDL���
	// // 2.�޸ı�ṹ���﷨.pcm";
	// audition += "\"" + filePath + "\"";
	// Command.exeCmd("cmd /c start " + audition);
	// }
	public void xunfei()
	{
		// Ѷ�ɻ���������
		SpeechSynthesizer mTts = xunfeiSettings();
		// �ϳ���Ƶ
		mTts.synthesizeToUri(input, fileNamePart, synthesizeToUriListener);
	}
	// /**
	// * @param input
	// * @param fileName
	// */
	// public static void xunfeiOnes(String input, String fileNamePart)
	// {
	// new XunFei(input, fileNamePart).xunfei();
	// wait = true;
	// // �ȴ��ϴ�ִ�����
	// waitFor();
	// }
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
	// public static void xunfeiAll(String input, int maxSize, String fileName)
	// {
	// System.out.println("�ַ����ܳ���:" + input.length());
	// // �����ַ����ĳ���
	// int inputLength = input.length();
	// // ��ǰ�ʶ����ı�.
	// String now;
	// // ������
	// int count = 0;
	// // ��ֵ��ļ���
	// String fileNamePart;
	// while (inputLength > maxSize)
	// {
	// // ����2
	// int splitIndex = 0;
	// char end;
	// for (int i = maxSize; i > 0; i--)
	// {
	// end = input.charAt(i);
	// if ('\n' == end || '��' == end)
	// {
	// splitIndex = i;
	// break;
	// }
	// }
	// // ��ȡ��ǰ���ı�.
	// now = input.substring(0, splitIndex + 1);
	// System.out.println(
	// "------------------------------------------------");
	// System.out.println(now);
	// // ��̬�����ļ���
	// fileNamePart = fileNamePart(fileName, count++);
	// // �ѵ�ǰ�ĺϳɵ�ǰ���ı�now,���浽fileNamePart
	// XunFei.xunfeiOnes(now, fileNamePart);
	//
	// // ����ʣ�µ��ı�
	// input = input.substring(splitIndex + 1);
	// // ����ʣ���ı��ĳ���
	// inputLength = input.length();
	// }
	// // ���ʣ�µ��ı�
	// System.out.println("------------------------------------------------");
	// System.out.println(input);
	// fileNamePart = fileNamePart(fileName, count);
	// XunFei.xunfeiOnes(input, fileNamePart);
	// }
	/**
	 * ���ɲ���������·��.
	 * 
	 * @param fileName
	 * @param count
	 * @return
	 */
	// public static String fileNamePart(String fileName, int count)
	// {
	// String fileNamePart;
	// // ��һ������ʹ��ԭ�����ļ���
	// fileNamePart = fileName.substring(0, fileName.lastIndexOf(".pcm"))
	// + (count) + fileName.substring(fileName.lastIndexOf(".pcm"));
	// return fileNamePart;
	// }
	// /**
	// * �����߳�.
	// */
	// public static void waitFor()
	// {
	// while (wait)
	// {
	// try
	// {
	// Thread.sleep(1000);
	// } catch (InterruptedException e)
	// {
	// e.printStackTrace();
	// }
	// }
	// }

}
