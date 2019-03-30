package demo;

import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SynthesizeToUriListener;

public class DemoListRunable implements Runnable
{
	public static boolean isEnd=false;
	String fileName;
	String Input;
	String voiceName;
	public DemoListRunable(String voiceName, String fileName, String Input)
	{
		this.voiceName = voiceName;
		this.fileName = fileName;
		this.Input = Input;
	}
	public void run()
	{
		// 3.����һ��SpeechSynthesizer����
		SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer();
		// 4.�ϳɲ������ã������MSC Reference Manual��SpeechSynthesizer ��
		// ���÷�����
		// С�� ����Ů�� ��Ӣ�ģ���ͨ���� xiaoyan Ĭ��
		// С�� ����Ů�� ��Ӣ�ģ���ͨ���� vixy
		// С�� ����Ů�� ��Ӣ�ģ���ͨ���� vixq xiaoqi
		// С�� �������� ��Ӣ�ģ���ͨ���� xiaoyu
		// С�� �������� ��Ӣ�ģ���ͨ���� vixf
		// 4-1 ���÷�����
		mTts.setParameter(SpeechConstant.VOICE_NAME, voiceName);//
		// ����Ҫ���ص��ļ���
		mTts.setParameter(SpeechConstant.SPEED, "50");
		// �����������Χ0~100
		mTts.setParameter(SpeechConstant.PITCH, "50");
		// ������������Χ0~100
		mTts.setParameter(SpeechConstant.VOLUME, "50");
		// TODO Auto-generated method stub
		System.out.println(
				"    ���ںϳ� " + fileName.substring(0, fileName.lastIndexOf("."))
						+ " �ʶ�����" + " ����λ��:" + fileName);
		mTts.synthesizeToUri(Input, fileName, synthesizeToUriListener);
	}
	// 1 ���úϳɼ�����
	static SynthesizeToUriListener synthesizeToUriListener = new SynthesizeToUriListener()
	{
		// progressΪ�ϳɽ���0~100
		public void onBufferProgress(int progress)
		{
		}
		// �Ự�ϳ���ɻص��ӿ�
		// uriΪ�ϳɱ����ַ��errorΪ������Ϣ��Ϊnullʱ��ʾ�ϳɻỰ�ɹ�
		public void onSynthesizeCompleted(String uri, SpeechError error)
		{
			//�����������
			isEnd=true;
			if(error==null)
				System.out.println("        �ϳɳɹ�");
			else 
				System.out.println("        �ϳ�ʧ��");
		}
		@Override
		public void onEvent(int arg0, int arg1, int arg2, int arg3, Object arg4,
				Object arg5)
		{
			// TODO Auto-generated method stub

		}
	};
}
