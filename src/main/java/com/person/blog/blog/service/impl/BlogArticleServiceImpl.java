package com.person.blog.blog.service.impl;

import com.person.blog.blog.domain.BlogArticle;
import com.person.blog.blog.mapper.BlogArticleMapper;
import com.person.blog.blog.service.IBlogArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Xiong
 * @since 2019-05-26
 */
@Service
public class BlogArticleServiceImpl extends ServiceImpl<BlogArticleMapper, BlogArticle> implements IBlogArticleService {

}
