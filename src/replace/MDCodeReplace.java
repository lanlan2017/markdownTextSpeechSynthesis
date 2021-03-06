package replace;

import regex.RegexMath;
import tools.io.properties.ContainSpecialWordsProperties;
import tools.io.properties.SpecialWordsProperties;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MDCodeReplace {
    /**
     * 处理包含这些单词的情况.
     *
     * @param matcherStr 包含特殊单词的字符串.
     * @return 正确朗读特殊单词.
     */
    static String replaceContainSpecialWords(String matcherStr) {
        matcherStr = ContainSpecialWordsProperties.repalceByProperties(matcherStr);
        return matcherStr;
    }


    public static String replaceSpecialChars(String matcherStr) {
        matcherStr = matcherStr.replace("!=", "不等于");
        matcherStr = matcherStr.replace("=", "等于");
        matcherStr = matcherStr.replace("-", "杠");
        matcherStr = matcherStr.replace("&", "单与符");
        matcherStr = matcherStr.replace("?", "问号");
        matcherStr = matcherStr.replace("*", "星号");
        matcherStr = matcherStr.replace("/", "斜杠");
        matcherStr = matcherStr.replace("\\", "反斜杠");
        matcherStr = matcherStr.replace(":", "冒号");
        matcherStr = matcherStr.replace("$", "美元符");
        matcherStr = matcherStr.replace("#", "井号");
        matcherStr = matcherStr.replace(".", "点");
        matcherStr = matcherStr.replace("|", "竖杠");
        matcherStr = matcherStr.replace("!", "感叹号");
        matcherStr = matcherStr.replace("~", "波浪号");
        matcherStr = matcherStr.replace("^", "脱字符");
        matcherStr = matcherStr.replace("<", "小于");
        matcherStr = matcherStr.replace(">", "大于");
        matcherStr = matcherStr.replace(";", "分号");
        matcherStr = matcherStr.replace("_", "下划线");
        // 删除单引号免得读多了
        matcherStr = matcherStr.replace("'", "");
        return matcherStr;
    }

    /**
     * 处理匹配特定正则表达式的情况.
     *
     * @param matcherStr 包含匹配特定正则表达式的情况.
     * @return 正确的读法.
     */
    private static String replaceMatcher(String matcherStr) {
        // HTML代码
        Pattern htmlTagPattern = Pattern.compile("<(.+?)/?>");
        Matcher htmlTagMatcher = htmlTagPattern.matcher(matcherStr);
        // 如果是负数的情况
//		if (matcherStr.matches("-\\d+"))
        if (matcherStr.matches(RegexMath.negativeNumber)) {
            // System.out.println("匹配到负数: " + matcherStr);
            matcherStr = matcherStr.replace("-", "负");
        }
        // 朗读全大写字符串
        matcherStr = replaceStringAllUpperCase(matcherStr);

        // 如果是html标签
        if (htmlTagMatcher.matches()) {
            // 移除html标签的`<`和`/?>`
            matcherStr = htmlTagMatcher.replaceAll("$1");
        }
        return matcherStr;
    }

    /**
     * 朗读全大写的字符串.
     *
     * @param matcherStr 全部大写的字符串.
     * @return 各个大写分开的字符串.
     */
    private static String replaceStringAllUpperCase(String matcherStr) {
        Pattern pattern = Pattern.compile("[A-Z]+");
        Matcher matcher = pattern.matcher(matcherStr);
        if (matcher.matches()) {
            StringBuilder sbBuffer = new StringBuilder(matcherStr.length() * 2);
            for (int i = 0, length = matcherStr.length(); i < length; i++) {
                // sbBuffer.append(matcherStr.charAt(i) + " ");
                sbBuffer.append(matcherStr.charAt(i)).append(" ");
            }
            return sbBuffer.toString();
        }
        return matcherStr;
    }

    /**
     * 替换容易读错的单词.
     *
     * @param matcherStr 容易读错的单词.
     * @return 正确读法.
     */
    private static String replaceSpecialWords(String matcherStr) {
        matcherStr = SpecialWordsProperties.replaceByVlaue(matcherStr);
        return matcherStr;
    }

    static String replaceMdCode(String matcherStr) {
        // 处理匹配正则表达式的情况
        matcherStr = MDCodeReplace.replaceMatcher(matcherStr);
        // 朗读特殊字符
        matcherStr = MDCodeReplace.replaceSpecialChars(matcherStr);
        // 处理匹配特定单词的情况
        matcherStr = MDCodeReplace.replaceSpecialWords(matcherStr);
        // 替换容易读出的单词
        matcherStr = MDCodeReplace.replaceContainSpecialWords(matcherStr);
        return matcherStr;
    }
}
