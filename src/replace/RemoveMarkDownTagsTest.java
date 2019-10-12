package replace;

import clipboard.util.SysClipboardUtil;
import tools.io.markdown.reader.MyMarkdownReader;

/**
 * 测试类.
 */
public class RemoveMarkDownTagsTest {
    public static void main(String[] args) {
        String path = SysClipboardUtil.getSysClipboardText();
        StringBuffer input = MyMarkdownReader.readerMyMarkdownFile(path);
        System.out.println(RemoveMarkDownTags.replaceMD(input.toString()));
    }
}
