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
				System.out.println("    �ϳ�:" + fileNamePart + "-->�ɹ�");
				// 8.ʹ��auditon���ļ�.
				// openFileUseAudition(fileName);
				// ��һ�κϳɳɹ�ʱ,�������ϱ���ϳɳɹ����ļ���
				if (XunFeiTools.getFileNameList() == null)
				{
					XunFeiTools.setFileNameList(new ArrayList<String>());
				}
				XunFeiTools.getFileNameList().add(fileNamePart);
			} else
				System.out.println("    �ϳ�:" + fileNamePart + "-->ʧ��");
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
	public void xunfei()
	{
		// Ѷ�ɻ���������
		SpeechSynthesizer mTts = xunfeiSettings();
		// �ϳ���Ƶ
		mTts.synthesizeToUri(input, fileNamePart, synthesizeToUriListener);
	}
}
