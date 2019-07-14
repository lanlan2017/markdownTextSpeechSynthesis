package main;

import java.util.ArrayList;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SpeechUtility;
import com.iflytek.cloud.speech.SynthesizeToUriListener;

public class XunFei
{
	// 要处理的字符串.
	String input;
	// 生成的文件.
	String fileNamePart;
	// static boolean wait = true;
	// static ArrayList<String> fileNameList = null;
	// 1 设置合成监听器
	private SynthesizeToUriListener synthesizeToUriListener = new SynthesizeToUriListener()
	{
		// progress为合成进度0~100
		public void onBufferProgress(int progress)
		{
		}
		// 会话合成完成回调接口
		// uri为合成保存地址，error为错误信息，为null时表示合成会话成功
		public void onSynthesizeCompleted(String uri, SpeechError error)
		{
			if (error == null)
			{
				System.out.println("    合成:" + fileNamePart + "-->成功");
				// 8.使用auditon打开文件.
				// openFileUseAudition(fileName);
				// 第一次合成成功时,创建集合保存合成成功的文件名
				if (XunFeiTools.getFileNameList() == null)
				{
					XunFeiTools.setFileNameList(new ArrayList<String>());
				}
				XunFeiTools.getFileNameList().add(fileNamePart);
			} else
				System.out.println("    合成:" + fileNamePart + "-->失败");
			// 不再等待,继续合成下一个分组.
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
		// 从配置文件中读取APPID
		SpeechUtility.createUtility(SpeechConstant.APPID + "=" + "5c80ae6b");
		// 3.创建SpeechSynthesizer对象
		SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer();
		// 设置语速，范围0~100
		mTts.setParameter(SpeechConstant.SPEED, "50");
		// 设置语调，范围0~100
		mTts.setParameter(SpeechConstant.PITCH, "50");
		// 设置音量，范围0~100
		mTts.setParameter(SpeechConstant.VOLUME, "50");
		// 4.合成参数设置，详见《MSC Reference Manual》SpeechSynthesizer 类
		mTts.setParameter(SpeechConstant.VOICE_NAME, "vixy");// 小研
		return mTts;
	}
	public void xunfei()
	{
		// 讯飞机器人设置
		SpeechSynthesizer mTts = xunfeiSettings();
		// 合成音频
		mTts.synthesizeToUri(input, fileNamePart, synthesizeToUriListener);
	}
}
