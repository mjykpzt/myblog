package com.mjy.blog.utils;

import com.mjy.blog.bean.Articles;

public class ArticlesXssFilter {
    public static Articles ArticlesXss(Articles articles){
        String unsafe_html_text = articles.getHtml_text();
        String html_text = TextXssUtils.FilterXss(unsafe_html_text);
        articles.setHtml_text(html_text);

        String unsafe_md_text = articles.getMd_text();
        String md_text = TextXssUtils.FilterXss(unsafe_md_text);
        articles.setMd_text(md_text);

        return articles;
    }
}
