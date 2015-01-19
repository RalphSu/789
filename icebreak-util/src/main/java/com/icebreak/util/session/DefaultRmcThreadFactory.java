package com.icebreak.util.session;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultRmcThreadFactory implements ThreadFactory {
	 /**
	  * 线程组
	  */
     private ThreadGroup group = null;
     /**
      * 线程计数器
      */
     private AtomicInteger threadNumber = new AtomicInteger(1);
     /**
      * 线程名称前缀
      */
     private String namePrefix = null;
     /**
      * 初始化线程工厂
      * @param prefix 线程名称前缀
      */
     public DefaultRmcThreadFactory(String prefix) {
         SecurityManager s = System.getSecurityManager();
         group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
         namePrefix = prefix;
     }
     
     /**
      * 初始化线程工厂
      * @param prefix 线程名称前缀
      * @param group 线程组
      */
     public DefaultRmcThreadFactory(String prefix, ThreadGroup group) {
         this.group = group;
         namePrefix = prefix;
     }

     public Thread newThread(Runnable r) {
         Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
         if (t.isDaemon()){
             t.setDaemon(false);
         }
         if (t.getPriority() != Thread.NORM_PRIORITY){
             t.setPriority(Thread.NORM_PRIORITY);
         }
         return t;
     }
}
