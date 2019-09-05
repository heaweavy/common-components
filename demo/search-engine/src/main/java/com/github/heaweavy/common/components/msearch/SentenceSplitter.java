package com.github.heaweavy.common.components.msearch;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.collection.AhoCorasick.AhoCorasickDoubleArrayTrie;
import com.hankcs.hanlp.corpus.dictionary.item.Item;
import com.hankcs.hanlp.dictionary.BaseSearcher;
import com.hankcs.hanlp.dictionary.CoreDictionary;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.suggest.Suggester;
import com.hankcs.hanlp.tokenizer.IndexTokenizer;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import com.hankcs.hanlp.tokenizer.SpeedTokenizer;

import java.util.List;
import java.util.Map;

/**
 * Created by caimb on 2017/2/9.
 */
public class SentenceSplitter {
    public SentenceSplitter(){
    }

    public List<Term> getSegment(String text){
        Segment segment =HanLP.newSegment().enableCustomDictionary(true);
        return segment.seg(text);
    }
    public List<String> getKeyWord(String content ,int size){
        return HanLP.extractKeyword(content, size);
    }

    public List<String> suggest(String key,String content, int sum){
        Suggester suggester = new Suggester();
        String[] titleArray =content.split("\\n");
        for (String title : titleArray)
        {
            suggester.addSentence(title);
        }
        return suggester.suggest(key, sum);
    }
}
