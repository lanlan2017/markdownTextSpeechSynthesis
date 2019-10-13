package replace;

import regex.RegexMarkdown;
import replace.md.table.MDTableReplace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemoveMarkDownTags {
    /**
     * 标记,是否已经处理了行内代码.
     * 标题,加粗中都可以嵌套行内代码,所以在处理标题和加粗之前,要先确保处理过行内代码.
     */
    private static boolean isRemoveCodeInLine;
    /**
     * 标记,是否已经处理了代码块.要先处理代码块,然后在处理行内代码.
     */
    private static boolean isRemoveCodeBlock;

    private static String removeMDTitle(String line) {
        // 因为标题中可以有行内代码,所以先确保移除掉了行内代码.
        if (!isRemoveCodeInLine)
            line = removeMDCodeInLine(line);
        // 先删除掉后面的井号
        Pattern pattern = Pattern.compile(RegexMarkdown.MDTitleRegex);
        Matcher matcher = pattern.matcher(line);
        StringBuffer sb = new StringBuffer();
        String matcherStr;
        while (matcher.find()) {
            // 获取匹配文本,并删除头部和尾部空白符
            matcherStr = matcher.group(2).trim();
            // 删除特殊字符
            matcherStr = MDCodeReplace.replaceSpecialChars(matcherStr);
            // 替换特殊字符
            matcherStr = MDCodeReplace.replaceContainSpecialWords(matcherStr);
            matcher.appendReplacement(sb, matcherStr);
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 移除字符串中的Markdown图片标记.
     *
     * @param line 要处理的字符串。
     * @return 没有Markdown图片标记的字符串.
     */
    private static String removeMDIMG(String line) {
        Pattern pattern = Pattern.compile(RegexMarkdown.MDImgRegex);
        Matcher matcher = pattern.matcher(line);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            // 把匹配到的图片替换成空字符串。
            matcher.appendReplacement(sb, "");
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 移除markdown加粗或者斜体标记.
     *
     * @param line 包含markdown加粗标记的字符串.
     * @return 没有加粗或者斜体的字符串.
     */
    private static String removeMDStrongOrItalic(String line) {
        // 因为加粗标记中可以包括行内代码,所以要先移除行内代码。
        if (!isRemoveCodeInLine)
            line = removeMDCodeInLine(line);
        return line.replaceAll(RegexMarkdown.MDStrongOrItalicRegex, "$2");
    }

    /**
     * 移除markdown代码段标记
     *
     * @param line 要处理的字符串.
     * @return 没有markdown行内代码的字符串.
     */
    private static String removeMDCodeInLine(String line) {
        // 要先移除代码块
        if (!isRemoveCodeBlock)
            line = removeMDCodeBlock(line);
        // 然后再移除行内代码.
        Pattern pattern = Pattern.compile(RegexMarkdown.MDCodeInLineRegex);
        Matcher matcher = pattern.matcher(line);
        StringBuffer sb = new StringBuffer();
        String matcherStr;
        while (matcher.find()) {
            // 获取匹配文本
            matcherStr = matcher.group(1);
            // System.out.println("----------------------------------");
            // System.out.println(matcherStr);
            // System.out.println("----------------------------------");
            // 处理匹配特定字符的情况
            matcherStr = MDCodeReplace.replaceSpecialChars(matcherStr);
            // 处理匹配特定单词的情况
            matcherStr = MDCodeReplace.replaceSpecialWords(matcherStr);
            // 处理匹配正则表达式的情况
            matcherStr = MDCodeReplace.replaceMatcher(matcherStr);
            // 替换容易读出的单词
            matcherStr = MDCodeReplace.replaceContainSpecialWords(matcherStr);
            // 添加空格让机器人好识别
            matcher.appendReplacement(sb, matcherStr);
        }
        matcher.appendTail(sb);
        // 设置标记为true,表示已经处理好行内代码了.
        isRemoveCodeInLine = true;
        return sb.toString();
    }

    /**
     * 移除markdown,超链接。
     *
     * @param line 要处理的字符串.
     * @return 没有markdown超链接的字符串。
     */
    private static String removeMDLink(String line) {
        return line.replaceAll(RegexMarkdown.MDLinkRegex, " $1 ");
    }

    /**
     * 移除markdown,代码块。
     *
     * @param line 要处理的字符串.
     * @return 没有markdown代码块的字符串.
     */
    private static String removeMDCodeBlock(String line) {
        Pattern codeBlockPattern = Pattern
                .compile(RegexMarkdown.MDCodeBlockRegex);
        Matcher codeBlockMatcher = codeBlockPattern.matcher(line);
        StringBuffer sb = new StringBuffer();
        while (codeBlockMatcher.find()) {
            codeBlockMatcher.appendReplacement(sb, "");
        }
        codeBlockMatcher.appendTail(sb);
        // 设置标记,表示已经移除了代码块,这样才可以移除行内代码.
        isRemoveCodeBlock = true;
        return sb.toString();
    }

    /**
     * 移除markdown,引用块。
     *
     * @param line 要处理的字符串.
     * @return 没有markdown引用块的字符串.
     */
    private static String removeMDQuoteBlock(String line) {
        return line.replaceAll(RegexMarkdown.MDQuoteBlockRegex, "").trim();
    }

    /**
     * 移除markdown,无序列表项。
     *
     * @param line 要处理的字符串.
     * @return 没有markdown无序列表标记的字符串.
     */
    private static String removeMDUnorderListBlock(String line) {
        return line.replaceAll(RegexMarkdown.MDUnOrderListBlockRegex, "")
                .trim();
    }

    /**
     * 删除字符串中的markdown标记.
     *
     * @param input 要处理的字符串.
     * @return 没有markdown标记的字符串.
     */
    public static String replaceMD(String input) {
        // 移除图片
        input = removeMDIMG(input);
        // 移除超链接
        input = removeMDLink(input);
        // 移除表格
        input = MDTableReplace.MDTableSpeech(input);
        // 移除代码块
        input = removeMDCodeBlock(input);
        // 移除行内代码
        input = removeMDCodeInLine(input);
        // 移除加粗 要放在 行内代码之后,因为加粗可以包括行内代码,行内代码可以有星号
        input = removeMDStrongOrItalic(input);
        // 移除标题,要放在 行内代码之后,以为标题中可以有海内代码。
        input = removeMDTitle(input);
        // 移除无序列表
        input = removeMDUnorderListBlock(input);
        // 移除引用块
        input = removeMDQuoteBlock(input);
        return input;
    }
}
