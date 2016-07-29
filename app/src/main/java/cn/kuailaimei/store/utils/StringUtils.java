package cn.kuailaimei.store.utils;

import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ymh on 2016/6/29 0029.
 * 字符处理相关工具类
 */
public class StringUtils {

    private static final String PATTERN_PHONE = "^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$";
    private static final String PATTERN_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 检测是否为手机号
     * @param str
     * @return
     */
    public static boolean isPhoneNum(String str) {
        Pattern p = Pattern.compile(PATTERN_PHONE);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 检测密码
     *
     * ^ 匹配一行的开头位置
     * (?![0-9]+$) 预测该位置后面不全是数字
     * (?![a-zA-Z]+$) 预测该位置后面不全是字母
     * [0-9A-Za-z] {6,15} 由6-15位数字或这字母组成
     * `~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）&mdash;—|{}【】‘；：”“'。，、？ 允许输入特殊字符
     */
    public static boolean passWordCheck(String pwd) {
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[`~!@#$^&*()=|{}':;',\\\\[\\\\].<>/?~！@#￥……&*（）&mdash;—|{}【】‘；：”“'。，、？0-9A-Za-z]{6,15}$";
        return pwd.matches(regex);
    }

    /**
     * 判断是否为纯数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否为纯字母
     *
     * @param str
     * @return
     */
    public static boolean isChart(String str) {
        Pattern pattern = Pattern.compile("[A-za-z]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断用户输入的内容是否只含有换行符/n
     * @param input
     * @return true -是  false-否
     */
    public static boolean isWrapOnly(String input) {
        String regEx = "[\n]+";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(input);
        return m.matches();
    }

    /**
     * 判断用户输入的内容是否只含有空格
     * @param input
     * @return
     */
    public static boolean isSpaceOnly(String input){
        String regEx = "[ ]+";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(input);
        return m.matches();
    }

    /**
     * 检查EditText中的内容是否为空
     *
     * @return true表示有为空的EditText
     */
    public static boolean etvIsEmpty(EditText... editTexts) {
        for (int i = 0, len = editTexts.length; i < len; i++) {
            if (editTexts[i].getText().toString().trim().length() == 0)
                return true;
        }
        return false;
    }

    /**
     * 只显示前面的文字，后面的用一个*代替
     * @param str 需要处理的文本
     * @param hideStart 显示到前面第几个文字
     * @return
     */
    public static String hideEnd(String str,int hideStart){
        if(str == null) return "***";
        if(str.length() <= hideStart) return str;
        str = str.substring(0,hideStart);
        return str+"*";
    }

    /**
     * 只显示第一个文字，后面的用一个*代替
     * @param str 需要处理的文本
     * @return
     */
    public static String hideEnd(String str){
        return hideEnd(str,1);
    }

    /**
     * 只显示前三位和后四位
     * @param str 需要处理的文本
     * @return
     */
    public static String hideMiddle(String str){
        if(str == null) return "***";
        if(str.length() <= 7) return str;
        String strFirst = str.substring(0,3);
        String strEnd = str.substring(str.length()-4,str.length());
        return strFirst+"****"+strEnd;
    }

    /**
     * 隐藏Email 显示前两位和@及之后的文本，其余用****代替
     * @return
     */
    public static String hideEmail(String str){
        if(str == null) return "***";
        if(!str.contains("@")) return hideMiddle(str);
        String[] arrayStr = str.split("@");
        if(arrayStr.length>1){
            String first = "";
            String end = "@"+arrayStr[1];
            if(arrayStr[0].length()>1){
                first = arrayStr[0].substring(0,2);
            }
            return first+"****"+end;
        }
        return "***";
    }

    public static boolean isEmpty(String s) {
        if (null == s)
            return true;
        if (s.length() == 0)
            return true;
        if (s.trim().length() == 0)
            return true;
        return false;
    }

}
