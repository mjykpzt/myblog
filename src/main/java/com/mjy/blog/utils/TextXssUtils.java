package com.mjy.blog.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author mjy
 * @create 2020-05-10-14:43
 */
public class TextXssUtils {

    private static Whitelist wlist;

    private static final Pattern COMPILE;

    static {
        wlist = Whitelist.relaxed();
        COMPILE = Pattern.compile("```[\\s\\S]*?```");
        wlist.addAttributes("code","class");
        wlist.addAttributes("span","class");
        wlist.addAttributes("div","class");
        wlist.addTags("hr");
    }

    public static String FilterXss(String sourceText){
        HashMap<String, String> stringStringMap = new HashMap<>();
        Matcher matcher = COMPILE.matcher(sourceText);
        while (matcher.find()){
            String ProtectedText = matcher.group();
            String textHashId = ProtectedText.hashCode() + "" + UUID.randomUUID();
            sourceText = sourceText.replace(ProtectedText, textHashId);
            stringStringMap.put(textHashId,ProtectedText);
        }
        String clean = Jsoup.clean(sourceText,"", wlist,new Document.OutputSettings().prettyPrint(false));
        for (String key:stringStringMap.keySet()){
            clean = clean.replace(key, "\n"+stringStringMap.get(key)+"\n");

        }
        return clean;
    }


}
