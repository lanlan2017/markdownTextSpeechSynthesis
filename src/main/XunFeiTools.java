package main;

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
import tools.io.writer.MyStringWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class XunFeiTools {
    /**
     * 等待标记.如果为true则主线程等待.
     */
    static boolean wait = true;
    /**
     * 部分临时文件路径列表.
     */
    static ArrayList<String> fileNameList = null;
    /**
     * 传入的文件名称.
     */
    static String fileName = null;
    // 一次支持合成的最大字符数量
    static int maxSize = 4000;

    /**
     * 生成部分语音的路径.
     *
     * @param fileName 文件路径.
     * @param count    计数器
     * @return 部分音频的路径.
     */
    private static String fileNamePart(String fileName, int count) {
        String fileNamePart;
        // 第一个部分使用原来的文件名
        fileNamePart = fileName.substring(0, fileName.lastIndexOf(".pcm"))
                + (count) + fileName.substring(fileName.lastIndexOf(".pcm"));
        return fileNamePart;
    }

    /**
     * 获取保存的所有文件名列表.
     *
     * @return 保存音频的文件列表.
     */
    static ArrayList<String> getFileNameList() {
        return fileNameList;
    }

    static void setFileNameList(ArrayList<String> fileNameList) {
        XunFeiTools.fileNameList = fileNameList;
    }

    /**
     * 使用Auditon打开音频.
     *
     * @param filePath 音频文件的路径.
     */
    private static void openFileUseAudition(String filePath) {
        String audition = "F:\\软件\\安装包_音频处理\\Adobe\" \"Audition\" \"CS6\\Adobe\" \"Audition\" \"CS6.exe ";
        // String filePath = "G:\\Desktop\\语音合成\\疯狂Java讲义第三版\\第13章\\13.2.4 DDL语句
        // 2.修改表结构的语法.pcm";
        audition += "\"" + filePath + "\"";
        Command.exeCmd("cmd /c start " + audition);
    }

    /**
     * 阻塞线程.
     */
    private static void waitFor() {
        while (wait) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 调用讯飞语音合成所有的输入文本.
     *
     * @param input    要合成的文本.
     * @param maxSize  讯飞SDK每次最多接收的字数上限,我测试了,这个设为4000没有问题.
     * @param fileName 要合成生成的文件.
     */
    private static void xunfeiAll(String input, int maxSize, String fileName) {
        // System.out.println("字符串总长度:" + input.length());
        // 整个字符串的长度
        int inputLength = input.length();
        // 当前朗读的文本.
        String now;
        // 计数器
        int count = 0;
        // 拆分的文件名
        String fileNamePart;
        while (inputLength > maxSize) {
            // 除以2
            int splitIndex = 0;
            char end;
            for (int i = maxSize; i > 0; i--) {
                end = input.charAt(i);
                if ('\n' == end || '。' == end) {
                    splitIndex = i;
                    break;
                }
            }
            // 获取当前的文本.
            now = input.substring(0, splitIndex + 1);
            // System.out.println(
            // "-----------------------中间分段-------------------------");
            System.out.println(now);
            // 动态生成文件名
            fileNamePart = fileNamePart(fileName, count++);
            // 把当前的合成当前的文本now,保存到fileNamePart
            xunfeiOnes(now, fileNamePart);

            // 更新剩下的文本
            input = input.substring(splitIndex + 1);
            // 更新剩下文本的长度
            inputLength = input.length();
        }
        // 输出剩下的文本
        // System.out.println(
        // "-----------------------最后分段-------------------------");
        System.out.println(input);
        fileNamePart = fileNamePart(fileName, count);
        xunfeiOnes(input, fileNamePart);
        // 合并为一个文件,并用audition打开这个文件.
        mergeAndOpenFile(fileName, XunFeiTools.getFileNameList());
    }

    /**
     * 合成一部分.
     *
     * @param input        输入文本.
     * @param fileNamePart 部分文件路径.
     */
    private static void xunfeiOnes(String input, String fileNamePart) {
        new XunFei(input, fileNamePart).xunfei();
        wait = true;
        // 等待上次执行完毕
        waitFor();
    }

    /**
     * 合并文件ArrayList中的多个源文件为一个文件.
     *
     * @param targetFilePath     目标文件路径名称字符串.
     * @param sourceFilePathList 存放源文件的路径名称字符串的ArrayList集合.
     */
    private static void merge2TargetFileDeleteSourceFile(String targetFilePath,
                                                         ArrayList<String> sourceFilePathList) {
        // 如果ArrayList中有东西
        if (sourceFilePathList.size() > 0) {
            BufferedInputStream in;
            try (BufferedOutputStream out = new BufferedOutputStream(
                    new FileOutputStream(new File(targetFilePath)))) {

                System.out.println("源文件列表:");
                for (Iterator<String> iterator = sourceFilePathList
                        .iterator(); iterator.hasNext(); ) {
                    String sourceFilePath = iterator.next();
                    System.out.println("    " + sourceFilePath);

                    File sourceFile = new File(sourceFilePath);
                    in = new BufferedInputStream(
                            new FileInputStream(sourceFile));
                    // 缓存数组
                    byte[] buffer = new byte[2048];
                    // 每次读入的字节数量
                    int inSize = -1;
                    // 批量读入字节到buffer缓存中,并返回读入的自己数量给inSize
                    while ((inSize = in.read(buffer)) != -1) {
                        // 把buffer缓存中的字节写入输出流(也就是目标文件)
                        out.write(buffer, 0, inSize);
                    }
                    // 关闭源文件
                    in.close();
                    // 删除这个源文件
                    sourceFile.delete();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void mergeAndOpenFile(String targetFilePath,
                                         ArrayList<String> sourceFilePathList) {
        // 合并list中的源文件到目标文件,并删除源文件
        merge2TargetFileDeleteSourceFile(targetFilePath, sourceFilePathList);
        // 清空List
        fileNameList.clear();
        // 打开合并后的文件
        System.out.println("打开合并后的文件:" + targetFilePath);
        XunFeiTools.openFileUseAudition(targetFilePath);
    }

    /**
     * 生成音频文件的绝对路径.
     *
     * @param path hexo博客中markdown文件的路径.
     * @return 生成的音频文件的路径.
     */
    private static String filePath(String path) {
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
        // 遍历当前目录列表
        for (int i = 0; i < fileDirList.length; i++) {
            if (fileDirList[i].isFile()) {
                System.out.println("文件:" + file.getAbsolutePath());
                // 合成文件
                xunfeiOneFile(fileDirList[i].getAbsolutePath());
            } else if (fileDirList[i].isDirectory()) {
                // 递归目录
                xunfeiDir(fileDirList[i]);
            }
        }
    }

    /**
     * 处理用户输入的Markdown文本.
     *
     * @return 移除Markdown标记之后的文本.
     */
    private static String inputProcessing(String input) {
        System.out.println(
                "######################################## 讯飞语音合成系统 ########################################");
        // 移除中文之间的一个或多个空格
        input = ReplaceSpaceInChineses.replaceSpaceInChineses(input);
        // 移除类似`<center><strong>表19.3input标签的属性</strong></center>`这样的标签
        input = RemoveHtmlTags.removeHtmlDoubleTags(input);
        // 移除markdown标记
        input = RemoveMarkDownTags.replaceMD(input);
        // 替换多音词,如重载,长度,机器可能会读错
        input = ChinesePolysyllabicWordsProperties.replaceByValue(input);
        // 拆分java驼峰命名法
        input = ReplaceEnglishString.replaceEnglish(input);
        // 替换容易读错的单词
        input = ContainSpecialWordsProperties.repalceByProperties(input);
        //
        input = input.replaceAll("(?m)[ ]+$", "");
        return input;
    }

    /**
     * 直接合成文本.
     *
     * @param context 文本内容.
     */
    public static void xunfeiByContext(String context) {
        String input = context;
        // 处理输入的文本
        input = XunFeiTools.inputProcessing(input);
        System.out.println("---------------- 直接合成 --------------------");
        System.out.println(input);
        System.out.println("---------------- 直接合成 --------------------");
        fileName = System.getProperty("user.dir") + File.separator
                + "直接合成.pcm";
        // 合成所有的文本
        XunFeiTools.xunfeiAll(input, maxSize, fileName);
    }
}
