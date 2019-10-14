package replace;

/**
 * @author francis
 * create at 2019/10/15-0:27
 */
public class Deletor {
    /**
     * 删除空行。
     *
     * @param input
     * @return
     */
    public static String deleteBlankLine(String input) {
        input = input.replaceAll("(?m)(?:^\\s*$(?:\\n|\\r\\n))", "");
        return input.replaceAll("(?m)(?:\\n|\\r\\n)(?:^\\s*$)", "");
    }
}
