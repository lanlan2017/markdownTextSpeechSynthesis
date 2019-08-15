package tools.io.markdown.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.Set;

import clipboard.util.SysClipboardUtil;
import regex.RegexHTML;
import tools.io.properties.PropertiesInstance;
import read.IOFlag;

public class MyMarkdownReader {
    public static void main(String[] args) {
        String path = SysClipboardUtil.getSysClipboardText();
        System.out.print(readerMyMarkdownFile(path));
    }

    /**
     * @param path
     */
    public static StringBuffer readerMyMarkdownFile(String path) {
        boolean fileStart = false;
        boolean read = false;
        HashMap<String, String> repalceMap = new HashMap<String, String>();
        StringBuffer sbBuffer = new StringBuffer();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(new File(path)), "utf-8"));) {

            String line = null;
            String[] replaceEntry;
            String key;
            String value;
            while ((line = reader.readLine()) != null) {
                // 如果文件开始了
                if (!fileStart && "<!--end-->".equals(line)) {
                    fileStart = true;
                    continue;
                }
                if (IOFlag.SSTStart.equals(line)) {
                    read = true;
                    continue;
                } else if (IOFlag.SSTStop.equals(line)) {
                    read = false;
                    continue;
                }
                // 如果扫描到正确读音标记
                else if (line.matches(RegexHTML.correctReading)) {
                    // 取出读音文件
                    line = line.replaceAll(RegexHTML.correctReading, "$1");
                    replaceEntry = line.split("&");
                    for (int i = 0; i < replaceEntry.length; i++) {
                        key = replaceEntry[i].substring(0,
                                replaceEntry[i].indexOf("="));
                        value = replaceEntry[i]
                                .substring(replaceEntry[i].indexOf("=") + 1);
                        repalceMap.put(key, value);
                    }
                    continue;
                }
                if (fileStart && read) {
                    // System.out.print(line + "\r\n");
                    // 保存到缓冲字符串中
                    sbBuffer.append(line + "\r\n");
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // System.out.println(
        // "------------------------------------------------------------");
        // 删除最后一行,这行带着文章的超链接
        deleteLastLine(sbBuffer);
        if (repalceMap.size() > 0) {
            // 替换特殊单词,以便正确朗读.
            sbBuffer = replaceKeyToValue(sbBuffer.toString(), repalceMap);
        }

        return sbBuffer;
    }

    /**
     * 使用Map中的key,替换value.
     *
     * @param input      需要替换的字符串
     * @param repalceMap 保存key和value的map集合
     * @return 保存替换后的字符串的StringBuffer.
     */
    public static StringBuffer replaceKeyToValue(String input,
                                                 HashMap<String, String> repalceMap) {
        //
        Properties properties = PropertiesInstance
                .getPropertiesInstanceUTF8("ContainSpecialWords.properties");
        Set<String> keySet = properties.stringPropertyNames();

        // 1 获取Map.Entry对象的Set集合
        Set<Entry<String, String>> mapEntry = repalceMap.entrySet();
        // 2 Map.Entry对象的Set集合迭代器
        Iterator<Entry<String, String>> mapEntryIt = mapEntry.iterator();
        while (mapEntryIt.hasNext()) {
            // 2 从Set集合中取出一个 Map.Entry实例
            Entry<String, String> mapEntryElement = mapEntryIt.next();
            // 3 分别取出键和值
            String key = mapEntryElement.getKey();
            String value = mapEntryElement.getValue();
            System.err.println("key=" + key + ",value=" + value);
            // 使用key替换value
            // input = input.replace(key, value);
            // 替换该单词,而是不替换其他单词中出现的子串。
            input = input.replaceAll("\\b" + key + "\\b", value);
            // 如果属性文件中没有这个key的话
            if (!keySet.contains(key)) {
                // 保存到属性文件对象中
                properties.setProperty(key, value);
            }
        }
        try {
            // 保存到磁盘上的属性文件
            properties.store(
                    new FileOutputStream(
                            new File("ContainSpecialWords.properties")),
                    "utf-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuffer sb = new StringBuffer(input);
        return sb;
    }

    /**
     * 删除最后一行文本.
     *
     * @param sbBuffer
     */
    public static void deleteLastLine(StringBuffer sbBuffer) {
        String lastLine = null;
        for (int length = sbBuffer.length() - 1, i = length; i > 0; i--) {
            char ch = sbBuffer.charAt(i);
            // 倒数第二个换行符之后的就是换行符.
            if (i < length && ch == '\n') {
                lastLine = sbBuffer.substring(i + 1, length);
                // System.out.println(lastLine.startsWith(">原文链接: "));
                // 如果最后一行是版权信息,则删除掉它
                if (lastLine.startsWith(">原文链接: ")) {
                    // 删除版权声明
                    sbBuffer.delete(i + 1, length);
                }
                break;
            }
        }
    }
}
