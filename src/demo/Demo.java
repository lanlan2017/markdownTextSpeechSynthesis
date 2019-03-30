package demo;

import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SpeechUtility;
import com.iflytek.cloud.speech.SynthesizerListener;
public class Demo
{
	public static void main()
	{
		// ����XXXXXXXX���滻���������APPID
		SpeechUtility.createUtility(SpeechConstant.APPID + "=XXXXXXXX ");

		// 1.����SpeechSynthesizer����
		SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer();
		// 2.�ϳɲ������ã������MSC Reference Manual��SpeechSynthesizer ��
		mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");// ���÷�����
		mTts.setParameter(SpeechConstant.SPEED, "50");// ��������
		mTts.setParameter(SpeechConstant.VOLUME, "80");// ������������Χ0~100
		// ���úϳ���Ƶ����λ�ã����Զ��屣��λ�ã��������ڡ�./tts_test.pcm��
		// �������Ҫ����ϳ���Ƶ��ע�͸��д���
		mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./tts_test.pcm");

		// 3.��ʼ�ϳ�
		mTts.startSpeaking("�����ϳɲ��Գ���", mSynListener);
	}
	// �ϳɼ�����
	private static SynthesizerListener mSynListener = new SynthesizerListener()
	{
		// �Ự�����ص��ӿڣ�û�д���ʱ��errorΪnull
		public void onCompleted(SpeechError error)
		{
		}
		// ������Ȼص�
		// percentΪ�������0~100��beginPosΪ������Ƶ���ı��п�ʼλ�ã�endPos��ʾ������Ƶ���ı��н���λ�ã�infoΪ������Ϣ��
		public void onBufferProgress(int percent, int beginPos, int endPos,
				String info)
		{
		}
		// ��ʼ����
		public void onSpeakBegin()
		{
		}
		// ��ͣ����
		public void onSpeakPaused()
		{
		}
		// ���Ž��Ȼص�
		// percentΪ���Ž���0~100,beginPosΪ������Ƶ���ı��п�ʼλ�ã�endPos��ʾ������Ƶ���ı��н���λ��.
		public void onSpeakProgress(int percent, int beginPos, int endPos)
		{
		}
		// �ָ����Żص��ӿ�
		public void onSpeakResumed()
		{
		}
		@Override
		public void onEvent(int arg0, int arg1, int arg2, int arg3, Object arg4,
				Object arg5)
		{
			// TODO Auto-generated method stub

		}
	};
}
