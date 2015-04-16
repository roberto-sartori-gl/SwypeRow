package com.robertogl.BlackTwitter; 

import android.content.res.XModuleResources;
import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;

public class Main implements IXposedHookZygoteInit, IXposedHookInitPackageResources {
    private static String MODULE_PATH = null;

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        MODULE_PATH = startupParam.modulePath;
    }
   
 
    @Override
    public void handleInitPackageResources(InitPackageResourcesParam resparam) throws Throwable {
        if (!resparam.packageName.equals("com.twitter.android"))
            return;

        XModuleResources modRes = XModuleResources.createInstance(MODULE_PATH, resparam.res);
        resparam.res.setReplacement("com.twitter.android", "color", "white", modRes.fwd(R.color.white));
        resparam.res.setReplacement("com.twitter.android", "color", "faint_transparent_white", modRes.fwd(R.color.faint_transparent_white));
        resparam.res.setReplacement("com.twitter.android", "color", "common_signin_btn_default_background", modRes.fwd(R.color.common_signin_btn_default_background));
        resparam.res.setReplacement("com.twitter.android", "color", "primary_text", modRes.fwd(R.color.primary_text));
        resparam.res.setReplacement("com.twitter.android", "color", "secondary_text", modRes.fwd(R.color.secondary_text));
        resparam.res.setReplacement("com.twitter.android", "color", "text", modRes.fwd(R.color.text));
        resparam.res.setReplacement("com.twitter.android", "color", "btn_default_fill", modRes.fwd(R.color.btn_default_fill));
       
     
        
    }
}