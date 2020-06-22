package com.mjy.blog.Service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mjy.blog.Bean.Articles;
import com.mjy.blog.Bean.ResponseBean;
import com.mjy.blog.Bean.SysArticles;
import com.mjy.blog.Service.ArticlesService;
import com.mjy.blog.Utils.TextXssUtils;
import com.mjy.blog.mapper.ArticlesDao;
import com.mjy.blog.mapper.ItemDao;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author mjy
 * @create 2020-03-08-22:55
 */
@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class ArticlesServiceImpl implements ArticlesService {
    @Value("${imgPath}")
    private String imgFolderPath;

    @Resource
    private ArticlesDao articlesDao;

    @Resource
    private ItemDao itemDao;


    @Override
    @Transactional(readOnly = true)
    public ResponseBean findArticlesInformation(Integer uid, Integer iid, String searchName, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysArticles> articles = articlesDao.findArticlesInformation(uid, iid, searchName);
        PageInfo<SysArticles> sysArticlesPageInfo = new PageInfo<>(articles);
        return ResponseBean.getSuccessResponse("查询成功", sysArticlesPageInfo);
    }

    @Override
    public ResponseBean changeArticles(Articles articles) {
        if (!itemDao.isCanUse(articles.getItem_id())) {
            return ResponseBean.getFailResponse("该条目已被禁用");
        }
        int OldIid = articlesDao.findIid(articles.getId());
        if (OldIid != articles.getItem_id()) {
            itemDao.addNumber(articles.getItem_id());
            itemDao.subNumber(OldIid);
        }

        Articles articles_safe = articlesXss(articles);

        String s = articles_safe.getHtml_text();
        articles.setSource_text(s.substring(0, Math.min(s.length(), 50)));

        int i = articlesDao.changeArticleInformation(articles);
        int i1 = articlesDao.changeArticleText(articles);
        if (i > 0 && i1 > 0) {
            return ResponseBean.getSuccessResponse("改变成功");
        }
        return ResponseBean.getFailResponse("改变失败");
    }

    @Override
    public ResponseBean addArticles(Articles articles) {
        if (!itemDao.isCanUse(articles.getItem_id())) {
            return ResponseBean.getFailResponse("该条目已被禁用");
        }

        Articles articles_safe = articlesXss(articles);

        String s = articles_safe.getHtml_text();
        articles.setSource_text(s.substring(0, Math.min(s.length(), 50)));
        int i = articlesDao.addArticleInformation(articles);
        int i1 = articlesDao.addArticleText(articles);
        Integer id = articles.getItem_id();
        itemDao.addNumber(id);
        if (i > 0 && i1 > 0) {
            return ResponseBean.getSuccessResponse("添加文章成功");
        }
        return ResponseBean.getFailResponse("添加文章失败");
    }


    @Override
    public ResponseBean findArticlesByAid(Integer aid) {
        SysArticles articles = articlesDao.findArticlesByAid(aid);
        if (articles != null) {
            articlesDao.ChangeReadNums(aid, 1);
            return ResponseBean.getSuccessResponse("查询成功", articles);
        }
        return ResponseBean.getFailResponse("查询失败");
    }

    @Override
    public ResponseBean delArticle(Integer aid, Integer iid) {
        int i = articlesDao.delArticle(aid);
        itemDao.subNumber(iid);
        if (i > 0) {
            return ResponseBean.getSuccessResponse("删除成功");
        }
        return ResponseBean.getFailResponse("删除失败");
    }

    @Override
    public ResponseBean addImg(HttpServletRequest req, MultipartFile image) {
        StringBuilder url = new StringBuilder();
        String userpath = "" + req.getAttribute("username");
        String path = imgFolderPath + userpath;
        File imgFolder = new File(path);
        if (!imgFolder.exists()) {
            boolean mkdirs = imgFolder.mkdirs();
            if (!mkdirs){
                return ResponseBean.getFailResponse("创建文件夹失败");
            }
        }
        url.append(req.getScheme())
                .append("://")
                .append(req.getServerName())
                .append(":")
                .append(req.getServerPort())
                .append(req.getContextPath())
                .append("/imgsave/")
                .append(userpath);
        String imgName = UUID.randomUUID() + "_" + Objects.requireNonNull(image.getOriginalFilename()).replaceAll(" ", "");
        articlesDao.addImUrl(path + "/" + imgName, (Integer) req.getAttribute("uid"));
        try {
            IOUtils.write(image.getBytes(), new FileOutputStream(new File(imgFolder, imgName)));
            url.append("/").append(imgName);
            return ResponseBean.getSuccessResponse("图片上传成功", url.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseBean.getFailResponse("图片上传失败");
    }

    @Override
    public int findAid(Integer aid) {
        return articlesDao.findAid(aid);
    }


    /**
     * @param articles  文章POJO对象
     * @return: com.mjy.blog.Bean.Articles
     * @author: 0205
     * <p>
     * 过滤html，防止xss
     */
    private Articles articlesXss(Articles articles) {

        String unsafe_html_text = articles.getHtml_text();
        String html_text = TextXssUtils.FilterXss(unsafe_html_text);
        articles.setHtml_text(html_text);

        String unsafe_md_text = articles.getMd_text();
        String md_text = TextXssUtils.FilterXss(unsafe_md_text);
        articles.setMd_text(md_text);

        return articles;
    }


}
