/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fiis.sppp.controller;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Danny
 */
@ManagedBean
@SessionScoped
public class NavigatorController implements Serializable {
    
    private String content = "./view/home.xhtml";
    private String menu = "./view/template/menu-left.xhtml";
    private String subContent = "./view/home.xhtml";

    public String getContent() {
        System.out.println(content);
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getSubContent() {
        return subContent;
    }

    public void setSubContent(String subContent) {
        this.subContent = subContent;
    }
    
    
    
}
