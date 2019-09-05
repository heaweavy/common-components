package com.github.heaweavy.common.components.msearch.test;

import com.github.heaweavy.common.components.msearch.SentenceSplitter;
import org.junit.Test;

/**
 * Created by caimb on 2017/2/9.
 */
public class SentenceSplitterTest {
    @Test
    public void testSplitter(){
        SentenceSplitter sp = new SentenceSplitter();
        sp.getSegment("丁中福攻城狮逆袭单身狗，工程师迎娶白富美，走上人生巅峰");
        System.out.println(sp.getKeyWord("丁中福攻城狮逆袭单身狗，工程师迎娶白富美，走上人生巅峰",5));
//        System.out.println(sp.getKeyWord("丁中福攻城狮逆袭单身狗，迎娶白富美，走上人生巅峰",10));
        /*System.out.println(sp.suggest("mayun","威廉王子发表演说 呼吁保护野生动物\n" +
                "《时代》年度人物最终入围名单出炉 普京马云入选\n" +
                "“黑格比”横扫菲：菲吸取“海燕”经验及早疏散\n" +
                "日本保密法将正式生效 日媒指其损害国民知情权\n" +
                "英报告说空气污染带来“公共健康危机”\n" +
                "丁中福攻城狮逆袭单身狗，迎娶白富美，走上人生巅峰",1));*/
    }
}
