package com.yuntao.pluginlib;

import dalvik.system.DexClassLoader;

/**
 * Created by pengyuntao on 16/8/23.
 */
public class PluginClassLoader extends DexClassLoader {
    public PluginClassLoader(String dexPath, String optimizedDirectory, String libraryPath, ClassLoader parent) {
        super(dexPath, optimizedDirectory, libraryPath, parent);
    }

    @Override
    protected Class<?> loadClass(String className, boolean resolve) throws ClassNotFoundException {
        //如果vm已经加载了,返回该类,否则返回null
        Class<?> clazz = findLoadedClass(className);
        if (clazz == null) {
            //如果vm没有加载让该类的父加载器加载
            try {
                clazz = this.getParent().loadClass(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (clazz == null) {
                //当前加载器加载
                clazz = findClass(className);
            }
        }
        return clazz;
    }
}
