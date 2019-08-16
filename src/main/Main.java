package main;

import clipboard.util.SysClipboardUtil;
import system.call.cmd.Command;

import java.io.File;

public class Main {

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
     * 当有一个命令参数的情况.
     * @param args 命令行参数.
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
     * 没有参数的情况.
     */
    private static void noArgs() {
        // 从剪贴板获取文件路径
        String path = SysClipboardUtil.getSysClipboardText();
        if (path.contains("source\\_posts")) {
            File file = new File(path);
            // 如果是文件的地址的话
            if (file.isFile()) {
                //合成一个文件
                XunFeiTools.xunfeiOneFile(path);
            } else if (file.isDirectory()) {
                XunFeiTools.xunfeiDir(file);
            }
        } else {
            // 直接合成文本
            XunFeiTools.xunfeiByContext(path);
        }
    }
}
