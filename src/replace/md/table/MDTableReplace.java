package replace.md.table;

//导入正则表达式
import clipboard.util.SysClipboardUtil;
import replace.MDCodeReplace;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static regex.RegexJava.javaMethedRegex;
import static regex.RegexMarkdown.MDCodeInLineInMDTableColsRegex;
import static regex.RegexMarkdown.MDTableRegex;

public class MDTableReplace {
    /**
     * 朗读Markdown表格,当表格中有方法声明时.
     *
     * @param line markdown文档中的内容字符串.
     * @return 移除markdown表格后的字符串.
     */
    public static String MDTableSpeech(String line) {
        Pattern pattern = Pattern.compile(MDTableRegex);
        Matcher matcher = pattern.matcher(line);
        StringBuffer sb = new StringBuffer();
        String tableTitle;
        String tableBody;
        String table;
        while (matcher.find()) {
            // 获取匹配到的一个分组
            tableTitle = matcher.group(1);
            // System.out.println(tableTitle);
            // System.out.println("---------------------------------------");
            tableBody = matcher.group(2);
            // System.out.println(tableBody);
            // System.out.println("---------------------------------------");
            tableBody = MDTableCodeInLineRepalce(tableBody);
            table = tableTitle + tableBody;
            //转义字符,$在这里表示捕获组,\\$表示这个字符本身.
            table = table.replace("$", "\\$");
            matcher.appendReplacement(sb, table);
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 替换表格中的行内代码.
     *
     * @param tableBody 表格体中的字符串.
     * @return 正确的朗读方法.
     */
    private static String MDTableCodeInLineRepalce(String tableBody) {
        Pattern pattern = Pattern.compile(MDCodeInLineInMDTableColsRegex);
        Matcher matcher = pattern.matcher(tableBody);
        StringBuffer sb = new StringBuffer();
        String group1;
        while (matcher.find()) {
            // 删除反引号
            group1 = matcher.group(1);
            // 朗读Java方法
            group1 = replaceJavaMethod(group1);
            // 替换特殊字符
            group1 = "|" + MDCodeReplace.replaceSpecialChars(group1) + "|";
            // 替换原来匹配的文本
            matcher.appendReplacement(sb, group1);
        }
        // 添加后面没有匹配的文本
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * java方法的朗读规则.
     *
     * @param matcherStr java方法定义字符串.
     * @return 朗读后的java方法.
     */
    private static String replaceJavaMethod(String matcherStr) {
        Pattern pattern = Pattern.compile(javaMethedRegex);
        Matcher matcher = pattern.matcher(matcherStr);
        String group1;
        String group2;
        String group3;
        if (matcher.matches()) {
            // 获取匹配到的一个分组
            group1 = matcher.group(1);
            group2 = matcher.group(2);
            group3 = matcher.group(3);
            matcherStr = group2 + " 方法 ";
            if (!group1.equals(group2)) {
                group1 = group1.replace("<?>", "泛型");
                group1 = group1.replace("[]", "数组");
                matcherStr = matcherStr + " 该方法定义 " + group1;
            }
            if (!"".equals(group3)) {
                group3 = group3.replace("...", "变长参数");
                group3 = group3.replace("<?>", "泛型");
                group3 = group3.replace("[]", "数组");
                group3 = group3.replace(",", "逗号");
                matcherStr = matcherStr + " 该方法参数列表 " + group3;
            } else {
                matcherStr = matcherStr + " 该方法是无参方法 ";
            }
            matcherStr = matcherStr + " 该方法功能 ";
            // System.out.println(" 替换结果:" + matcherStr);
            // System.out.println("捕获到表格:处理结束----------");
        }
        return matcherStr;
    }

    public static void main(String[] args) {
        String input = SysClipboardUtil.getSysClipboardText();
        System.out.println(MDTableSpeech(input));
    }
}
