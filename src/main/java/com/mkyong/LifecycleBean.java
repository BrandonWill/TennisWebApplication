/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Startup;
import javax.ejb.Singleton;

@Singleton
@Startup
public class LifecycleBean {

  @PostConstruct
  public void init() {
    new PlayerBean().loadPlayerData();
  }

  @PreDestroy
  public void destroy() {
    /* Shutdown stuff here */
  }

}
