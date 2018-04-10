package com.theforum.util;

/**
 * @author Uliana and David
 */
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.theforum.shell.QuartzShell;
// class that make possible run sheldure on server starting, added in web.xml
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