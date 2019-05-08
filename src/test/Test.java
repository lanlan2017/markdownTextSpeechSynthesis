package test;

import java.util.Scanner;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SpeechUtility;
import com.iflytek.cloud.speech.SynthesizeToUriListener;
import clipboard.util.SysClipboardUtil;
import replace.RemoveMarkDownTags;
import replace.Replace;
import replace.ReplaceSpaceInChineses;
import tools.io.reader.PropertiesReader;
import tools.io.writer.MyStringWriter;
/**
 * 测试文本处理的类,该类是Main类的一个副本. 该类用于测试字符替换效果,没有在最后一句调用语音合成API.
 */
public class Test
{
	static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args)
	{
		// 2 将“XXXXXXXX”替换成您申请的APPID
		String fileName;
		// 从配置文件中读取APPID
		SpeechUtility.createUtility(
				SpeechConstant.APPID + "=" + PropertiesReader.getAPPID());
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
		fileName = "小研vixy.pcm";
		// 5设置要合成的文本
		// String text = "测试语音合成";
		System.out.println(
				"######################################## 讯飞语音合成系统 ########################################");
		// System.out.println("输入要合成的文字(以: \"#\"作为结束符):");
		// //从控制台读取输入.
		// String input = getInput();
		// 从剪贴板中获取数据
		String input = SysClipboardUtil.getSysClipboardText();
		// 移除中文之间的一个或多个空格
		System.out.println(
				"---------------------------------- 移除中文中的空格 ----------------------------------");
		input = ReplaceSpaceInChineses.replaceSpaceInChineses(input);
		System.out.println(input);
		// 把没有中文空格的字符写回剪贴板.
		SysClipboardUtil.setSysClipboardText(input);
		System.out.println(
				"---------------------------------- 移除中文中的空格 ----------------------------------");
		System.out.println(
				"---------------------------------- 特殊字符处理 ----------------------------------");
		// 去除中文点号
		input = Replace.replaceTitle(input);
		// 移除markdown标记
		input = RemoveMarkDownTags.replaceMD(input);
		// 移除xml标记
		// input = ReplaceHtmlXmlTags.replaceHtmlXmlTags(input);
		// 拆分驼峰命名法,
		input = Replace.replaceEnglish(input);
		// 替换斜杠/,反斜杠\,单与符号&这样的特殊字符.
		// input = ReplaceSpecialCharacter.replaceSpecialCharacter(input);
		System.out.println(input);
		System.out.println(
				"---------------------------------- 特殊字符处理 ----------------------------------");

		// 6.开始合成 //设置合成音频保存位置（可自定义保存位置），默认保存在“./tts_test.pcm”
		// mTts.synthesizeToUri(input, fileName, synthesizeToUriListener);
		//写入处理结果,便于后续分析
		MyStringWriter.writerString(input);
	}

	/**
	 * @return
	 */
	public static String getInput()
	{
		StringBuffer sbBuffer = new StringBuffer(10240);
		String line = null;
		while (scanner.hasNext())
		{
			line = scanner.nextLine();
			if ("#".equals(line))
			{
				break;
			}
			sbBuffer.append(line.concat("\r\n"));
		}
		// 获取输入的数据.
		String input = sbBuffer.toString();
		return input;
	}

	// 1 设置合成监听器
	static SynthesizeToUriListener synthesizeToUriListener = new SynthesizeToUriListener()
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
				System.out.println("    合成成功");
			else
				System.out.println("    合成失败");

		}
		@Override
		public void onEvent(int arg0, int arg1, int arg2, int arg3, Object arg4,
				Object arg5)
		{
			// TODO Auto-generated method stub

		}
	};
}
