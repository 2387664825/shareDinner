package com.hfy.dinner.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.helpers.MessageFormatter;

import java.util.UUID;

/**
 * @author hfy
 * @date 2021/5/2 15:32
 */
public final class StringKit {

    /**
     * 字符串转 integer，防止 NPE.
     *
     * @param value 字符串
     * @return integer
     */
    public static Integer parseInteger(String value) {
        if(StringUtils.isBlank(value)){
            return null;
        }

        return Integer.parseInt(value);
    }

    /**
     * 将字符串的首字母转大写.
     *
     * @param str 需要转换的字符串
     * @return 转换后字符串
     */
    public static String captureName(String str) {
        // 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作.
        char[] cs=str.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

    /**
     * 获取最新的UUID.
     *
     * @return 字符串
     */
    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-","");
    }

    /**
     * 使用 Slf4j 中的字符串格式化方式来格式化字符串.
     *
     * @param pattern 待格式化的字符串
     * @return 格式化后的字符串
     */
    public static String format(String pattern) {
        return pattern == null ? "" : pattern;
    }

    /**
     * 使用 Slf4j 中的字符串格式化方式来格式化字符串.
     *
     * @param pattern 待格式化的字符串
     * @param args 参数
     * @return 格式化后的字符串
     */
    public static String format(String pattern, Object... args) {
        return pattern == null ? "" : MessageFormatter.arrayFormat(pattern, args).getMessage();
    }
}