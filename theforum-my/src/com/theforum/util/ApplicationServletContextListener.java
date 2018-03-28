package com.theforum.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.theforum.shell.QuartzShell;

public class ApplicationServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        System.out.println("Application started");  
        QuartzShell.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {

    }
}