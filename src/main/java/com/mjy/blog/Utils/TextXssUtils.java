package com.mjy.blog.Utils;

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

    static {
        Whitelist relaxed = Whitelist.relaxed();
        relaxed.addAttributes("code","class");
        relaxed.addAttributes("span","class");
        relaxed.addAttributes("div","class");
        relaxed.addTags("hr");
        wlist=relaxed;
    }

    public static String FilterXss(String text){
        HashMap<String, String> stringStringMap = new HashMap<String, String>();
        Pattern compile = Pattern.compile("```[\\s\\S]*?```");
        Matcher matcher = compile.matcher(text);
        while (matcher.find()){
            String group = matcher.group();
            String s = group.hashCode() + "" + UUID.randomUUID();
            String replace = text.replace(group, s);
            text=replace;
            stringStringMap.put(s,group);
        }
        String clean = Jsoup.clean(text,"", wlist,new Document.OutputSettings().prettyPrint(false));
        for (String key:stringStringMap.keySet()){
            String replace = clean.replace(key, "\n"+stringStringMap.get(key)+"\n");
            clean=replace;
        }
        return clean;
    }


}
