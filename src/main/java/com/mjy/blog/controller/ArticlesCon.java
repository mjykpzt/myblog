package com.mjy.blog.controller;

import com.mjy.blog.Bean.Articles;
import com.mjy.blog.Bean.ResponseBean;
import com.mjy.blog.Bean.Role;
import com.mjy.blog.Service.ArticlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author mjy
 * @create 2020-03-08-23:15
 */
@RestController
@RequestMapping("/articles")
public class ArticlesCon {
    @Autowired
    private ArticlesService articlesService;

//    @GetMapping()
//    @Secured("ROLE_ADMIN")
//    public ResponseBean finaAll(HttpServletRequest request,
//                                @RequestParam(defaultValue = "1") Integer pageNum,
//                                @RequestParam(required = true) Integer pageSize) {
//        List<Role> roles = (List<Role>)request.getAttribute("role");
//            return articlesService.findAll(pageNum, pageSize);
//
//    }
    @GetMapping("/findArticles")
    public ResponseBean findArticles(HttpServletRequest request,
                                     @RequestParam(required = false) Integer iid,
                                     @RequestParam(required = false)String searchName,
                                     @RequestParam(defaultValue = "1") Integer pageNum,
                                     @RequestParam(defaultValue = "5") Integer pageSize) {
        if (!(searchName.length() > 0)){
            searchName=null;
        }else {
            searchName="%"+searchName+"%";
        }

        if (IsAdmin(request)){
            return articlesService.findArticles(null,iid,searchName,pageNum,pageSize);
        }else {
            return articlesService.findArticles((Integer)request.getAttribute("uid"),iid,searchName,pageNum,pageSize);
        }
    }

    @PostMapping("/addArticles")
    public ResponseBean addArticles(HttpServletRequest request, Articles articles) {
        articles.setCreate_user((Integer) request.getAttribute("uid"));
        return articlesService.addArticles(articles);
    }

    @PostMapping("/changeArticles")
    public ResponseBean changeArticles(HttpServletRequest request,Articles articles) {
        if ((Integer)request.getAttribute("uid")==articlesService.findAid(articles.getId())){
            return articlesService.changeArticles(articles);
        }
        return ResponseBean.getFailResponse("权限不足");
    }

//    @GetMapping("/findArticlesByIid")
//    public ResponseBean findArticlesByIid(@RequestParam(required = true) Integer iid,
//                                          @RequestParam(defaultValue = "1") Integer pageNum,
//                                          @RequestParam(defaultValue = "5") Integer pageSize) {
//
//        return articlesService.findArticlesByIid(iid,pageNum,pageSize);
//    }

    @GetMapping("/findArticlesByAid")
    public ResponseBean findArticlesByAid(HttpServletRequest request,@RequestParam(required = true) Integer aid) {
        if (IsAdmin(request)||(Integer)request.getAttribute("uid")==articlesService.findAid(aid)){
            return articlesService.findArticlesByAid(aid);
        }else {
            return ResponseBean.getFailResponse("权限不足");

        }
    }

    @PostMapping("/delArticle")
    public ResponseBean delArticle(HttpServletRequest request,@RequestParam(required = true) Integer aid, @RequestParam(required = true) Integer iid) {
        if (IsAdmin(request)||(Integer)request.getAttribute("uid")==articlesService.findAid(aid)){
            return articlesService.delArticle(aid, iid);
        }else {
            return ResponseBean.getFailResponse("权限不足");

        }

    }

    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    public ResponseBean uploadImg(HttpServletRequest req, MultipartFile image) {
        return articlesService.addImg(req, image);
    }


    private Boolean IsAdmin(HttpServletRequest request) {
        List<Role> roles = (List<Role>) request.getAttribute("role");
        for (Role r : roles){
            String role_name = r.getRole_name();
            if (role_name.contains("ROLE_ADMIN")){
                return true;
            }
        }
        return false;
    }



}

