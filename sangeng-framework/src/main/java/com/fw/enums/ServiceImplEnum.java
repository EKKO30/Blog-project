package com.fw.enums;

public class ServiceImplEnum {

    //根评论不存在根评论id
    public final static int COMMENT_ROOT_ID=-1;
    //文章评论
    public final static String ARTICLE_COMMENT="0";
    //友链评论
    public final static String LINK_COMMENT="1";

    //权限以菜单显示
    public final static String MENU_MENU="C";

    //权限以按钮显示
    public final static String MENU_BUTTON="F";

    //目录
    public final static String MENU_contents="M";

    //根菜单
    public final static Long MENU_ROOT= Long.valueOf(0);

    //管理员
    public final static String SUPER_USER="1";
}
