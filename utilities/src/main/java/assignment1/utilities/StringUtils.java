/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package assignment1.utilities;

import assignment1.list.LinkedList;

public class StringUtils {
    public static String join(LinkedList source) {
        return JoinUtils.join(source);
    }

    public static LinkedList split(String source) {
        return SplitUtils.split(source);
    }
}
