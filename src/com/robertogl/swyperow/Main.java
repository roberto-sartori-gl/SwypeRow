package com.robertogl.swyperow;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import android.content.Context;
import android.content.res.XmlResourceParser;
import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Main implements IXposedHookLoadPackage, IXposedHookInitPackageResources  {
	private static String MODULE_PACKAGE = "com.robertogl.swyperow";
	private static String APP_PACKAGE = "com.nuance.swype.dtc";
	
	private int panlatin = 0;
	private int panlatin_sym = 0;
	
	public void handleInitPackageResources(InitPackageResourcesParam resparam) throws Throwable {
		if (!resparam.packageName.equals(APP_PACKAGE))
			return;
		
		panlatin = resparam.res.getIdentifier("kbd_qwerty_panlatin" , "xml" , APP_PACKAGE );
		
		panlatin_sym = resparam.res.getIdentifier( "kbd_qwerty_panlatin_sym" , "xml" , APP_PACKAGE);
	       
	}
	@Override
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
         if (!lpparam.packageName.equals(APP_PACKAGE))
         	return;
         
         findAndHookMethod("com.nuance.swype.input.KeyboardEx", lpparam.classLoader, "getKeyboardHeight", "com.nuance.swype.input.KeyboardStyle", int.class, int.class, new XC_MethodHook() {
        	 @Override
             protected void afterHookedMethod(MethodHookParam param) throws Throwable
             {
        		 int id_t = (Integer) XposedHelpers.callMethod(param.thisObject, "getKdbId");
        		 
        		 if( id_t == panlatin){
        			 double id = ((Integer)param.getResult())*1.25;
        			 int id_int = (int) id;
        			 param.setResult(id_int);
        		 }
        		 return;
             }
             });  
         
         findAndHookMethod("com.nuance.swype.input.KeyboardEx", lpparam.classLoader,"getKeyHeight", "com.nuance.swype.input.KeyboardStyle", int.class, int.class, new XC_MethodHook() {
        	 @Override
             protected void afterHookedMethod(MethodHookParam param) throws Throwable
             {
        		 int id_t = (Integer) XposedHelpers.callMethod(param.thisObject, "getKdbId");
        		 
        		 if( id_t == panlatin){
        			 double id = ((Integer)param.getResult())*1.08;
        			 int id_int = (int) id;
        			 param.setResult(id_int);
        		 }
        		 return;
             }
             });  
         findAndHookMethod("com.nuance.swype.input.KeyboardEx", lpparam.classLoader, "loadKeyboard", Context.class, XmlResourceParser.class, boolean.class,new XC_MethodHook() {
        	 @Override
             protected void beforeHookedMethod(MethodHookParam param) throws Throwable
             {
        		 Context mContext = (Context) XposedHelpers.callMethod(param.thisObject, "getContext");
        		 Context moduleContext = mContext.createPackageContext(MODULE_PACKAGE, Context.CONTEXT_IGNORE_SECURITY);

        		 int id = (Integer) XposedHelpers.callMethod(param.thisObject, "getKdbId");
        		 
        		 XmlResourceParser parser = moduleContext.getResources().getXml(R.raw.kbd_qwerty_panlatin);
        		 XmlResourceParser parser_sym = moduleContext.getResources().getXml(R.raw.kbd_qwerty_panlatin_sym);

        		 if( id == panlatin){
        			 param.args[1] = parser;
        		 }
        		 else if( id == panlatin_sym){
            		 param.args[1] = parser_sym;
            	}
             }
                 });     
     }
	
}