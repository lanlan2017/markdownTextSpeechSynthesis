package main;

import clipboard.util.SysClipboardUtil;
import com.iflytek.cloud.speech.*;
import replace.RemoveHtmlTags;
import replace.RemoveMarkDownTags;
import replace.ReplaceEnglishString;
import replace.ReplaceSpaceInChineses;
import system.call.cmd.Command;
import tools.io.markdown.reader.MyMarkdownReader;
import tools.io.properties.ChinesePolysyllabicWordsProperties;
import tools.io.properties.ContainSpecialWordsProperties;
import tools.io.properties.PerorationProperties;
import tools.io.properties.SpeechSynthesisProperties;
import tools.io.reader.PropertiesReader;
import tools.io.writer.MyStringWriter;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static String fileName = null;
    // 一次支持合成的最大字符数量
    static int maxSize = 4000;

    public static void main(String[] args) {
        switch (args.length) {
            case 0:
                noArgs();
                break;
            case 1:
                oneArgs(args);
            default:
                break;
        }

    }

    /**
     * @param args
     */
    private static void oneArgs(String[] args) {
        switch (args[0]) {
            case "setting":
                Command.exeCmd(
                        "explorer.exe " + System.getProperty("user.dir"));
                break;
            default:
                break;
        }
    }

    /**
     *
     */
    private static void noArgs() {
        // 从剪贴板获取文件路径
        String path = SysClipboardUtil.getSysClipboardText();
        if (path.contains("source\\_posts")) {
            File file = new File(path);
            // 如果是文件的地址的话
            if (file.isFile()) {
                //合成一个文件
                xunfeiOneFile(path);
            } else if (file.isDirectory()) {
                xunfeiDir(file);
            }
        } else {
            // 直接合成文本
            xunfeiByContext(path);
        }
    }



    /**
     * 直接合成文本.
     *
     * @param context 文本内容.
     */
    private static void xunfeiByContext(String context) {
        // 为了阅读方便
        String input = context;
        // 处理输入的文本
        input = inputProcessing(input);
        System.out.println("---------------- 直接合成 --------------------");
        System.out.println(input);
        System.out.println("---------------- 直接合成 --------------------");
        fileName = System.getProperty("user.dir") + File.separator
                + "直接合成.pcm";
        // 合成所有的文本
        XunFeiTools.xunfeiAll(input, maxSize, fileName);
    }



    /**
     *
     */
    public static void openFileUseAudition(String filePath) {
        String audition = SpeechSynthesisProperties.getAuditionPath();
        // String filePath = "G:\\Desktop\\语音合成\\疯狂Java讲义第三版\\第13章\\13.2.4 DDL语句
        // 2.修改表结构的语法.pcm";
        audition += "\"" + filePath + "\"";
        Command.exeCmd("cmd /c start " + audition);
    }
    /**
     * 处理用户熟人的Markdown文本.
     *
     * @return
     */
    public static String inputProcessing(String input) {
        System.out.println(
                "######################################## 讯飞语音合成系统 ########################################");
        // 移除中文之间的一个或多个空格
        input = ReplaceSpaceInChineses.replaceSpaceInChineses(input);
        // 移除类似`<center><strong>表19.3input标签的属性</strong></center>`这样的标签
        input = RemoveHtmlTags.removeHtmlDoubleTags(input);
        // 替换多音词,如重载,长度,机器可能会读错
        input = ChinesePolysyllabicWordsProperties.replaceByValue(input);
        // 替换容易读错的单词
        input = ContainSpecialWordsProperties.repalceByProperties(input);
        // 移除markdown标记
        input = RemoveMarkDownTags.replaceMD(input);
        // 拆分java驼峰命名法
        input = ReplaceEnglishString.replaceEnglish(input);
        //
        input = input.replaceAll("(?m)[ ]+$", "");
        return input;
    }
    /**
     * 生成音频文件的绝对路径.
     *
     * @param path hexo博客中markdown文件的路径.
     * @return 生成的音频文件的路径.
     */
    public static String filePath(String path) {
        // 存放语音文件的根目录
        // String fileName = "G:\\Desktop\\语音合成";
        String fileName = SpeechSynthesisProperties.getRootDir();
        // markdown文件相对于博客文章目录的相对路径
        String relativePath = path.substring(
                path.indexOf("source\\_posts") + "source\\_posts".length());
        // 语音文件的根路径拼接上相对路径得到语音文件的绝对路径
        fileName = fileName + relativePath.replace(".md", ".pcm");
        return fileName;
    }
    /**
     * 合成一个文件.
     *
     * @param path 文件的路径.
     */
    public static void xunfeiOneFile(String path) {
        // 根据markdown文件的路径生成音频文件的路径
        fileName = filePath(path);
        // 从文件中读取markdown文本
        String input = MyMarkdownReader.readerMyMarkdownFile(path)
                .toString();
        // 处理输入的文本
        input = inputProcessing(input);
        // 添加结束语
        input = input + PerorationProperties.getPeroration();
        // 合成所有的文本
        XunFeiTools.xunfeiAll(input, maxSize, fileName);
        // 7.给出提示
        MyStringWriter.writerString(input);
    }
    /**
     * 合成整个目录.
     *
     * @param file
     */
    public static void xunfeiDir(File file) {
        System.out.println("-------------------------合成整个目录-----------------");
        // 获取该目录下的所有markdown文件,或者子目录
        File[] fileDirList = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".md") || new File(dir, name).isDirectory();
            }
        });
        for (int i = 0; i < fileDirList.length; i++) {
            if (fileDirList[i].isFile()) {
                System.out.println("文件:" + file.getAbsolutePath());
                xunfeiOneFile(fileDirList[i].getAbsolutePath());
            } else if (fileDirList[i].isDirectory()) {
                xunfeiDir(fileDirList[i]);
            }

        }
    }

    /**
     * @return
     */
    public static SpeechSynthesizer xunfeiSettings() {
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
        return mTts;
    }

    // 1 设置合成监听器
    static SynthesizeToUriListener synthesizeToUriListener = new SynthesizeToUriListener() {
        // progress为合成进度0~100
        public void onBufferProgress(int progress) {
        }

        // 会话合成完成回调接口
        // uri为合成保存地址，error为错误信息，为null时表示合成会话成功
        public void onSynthesizeCompleted(String uri, SpeechError error) {
            if (error == null) {
                System.out.println("    合成成功");
                // 8.使用auditon打开文件.
                openFileUseAudition(fileName);
            } else
                System.out.println("    合成失败");

        }

        @Override
        public void onEvent(int arg0, int arg1, int arg2, int arg3, Object arg4,
                            Object arg5) {

        }
    };
}
