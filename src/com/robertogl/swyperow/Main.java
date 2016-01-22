package com.robertogl.swyperow;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import android.annotation.SuppressLint;
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
	private static String APP_PACKAGE = "com.nuance.swype";
	
	@SuppressLint("UseSparseArrays")
	Map<Integer, String> keyboard = new HashMap<Integer, String>();
	private String keyboard_name = null;
	private int keyboard_int = 0;
	
	public void handleInitPackageResources(InitPackageResourcesParam resparam) throws Throwable {
		if (!resparam.packageName.contains(APP_PACKAGE))
			return;
		
		Field fields[] = R.raw.class.getDeclaredFields();
		
		for( int i=0; i<fields.length; i++ ) {
		      keyboard_name = fields[i].getName();
		      keyboard_int = resparam.res.getIdentifier(keyboard_name, "xml" , resparam.packageName );
		      keyboard.put(keyboard_int, keyboard_name);
		}
	}
	@Override
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
         if (!lpparam.packageName.contains(APP_PACKAGE))
         	return;
         
         findAndHookMethod("com.nuance.swype.input.KeyboardEx", lpparam.classLoader, "getKeyboardHeight", "com.nuance.swype.input.KeyboardStyle", int.class, int.class, new XC_MethodHook() {
        	 @Override
             protected void afterHookedMethod(MethodHookParam param) throws Throwable
             {
        		 int id_t = (Integer) XposedHelpers.callMethod(param.thisObject, "getKdbId");
        		 
        		 if( keyboard.containsKey(id_t)){
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
        		 
        		 if( keyboard.containsKey(id_t)){
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
        		 int id = (Integer) XposedHelpers.callMethod(param.thisObject, "getKdbId");
        		 
        		 if (keyboard.containsKey(id)){
        			 
        			 Context mContext = (Context) XposedHelpers.callMethod(param.thisObject, "getContext");
            		 Context moduleContext = mContext.createPackageContext(MODULE_PACKAGE, Context.CONTEXT_IGNORE_SECURITY);
            		 
        			 int id_t =  moduleContext.getResources().getIdentifier(keyboard.get(id) , "raw" , MODULE_PACKAGE );
        			 XmlResourceParser parser = moduleContext.getResources().getXml(id_t);
        			 
        			 param.args[1] = parser;
        		 }
             }
                 });     
     }
	
}