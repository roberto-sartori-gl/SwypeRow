package com.robertogl.swyperow;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;



import org.apache.commons.io.IOUtils;
import org.xmlpull.v1.XmlPullParserException;

import com.robertogl.swyperow.Preferences;

import java.lang.Object;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.res.XModuleResources;
import android.content.res.XmlResourceParser;
import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.util.Base64;
public class Main implements IXposedHookZygoteInit, IXposedHookLoadPackage, IXposedHookInitPackageResources  {
	private static String MODULE_PACKAGE = "com.robertogl.swyperow";
	private static String APP_PACKAGE = "com.nuance.swype.dtc";
	private static String AMAZON_APP_PACKAGE = "com.nuance.swype.amz";
	@SuppressLint("UseSparseArrays")
	private Map<Integer, String> keyboard = new HashMap<Integer, String>();
	private String keyboard_name = null;
	private int keyboard_int = 0;
    private static String MODULE_PATH = null;
    private String PACKAGE_NAME = null;
    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        MODULE_PATH = startupParam.modulePath;
    }
	private boolean DEBUG = false;
	public void handleInitPackageResources(InitPackageResourcesParam resparam) throws Throwable {
		if (!resparam.packageName.contains(APP_PACKAGE) && !resparam.packageName.contains(AMAZON_APP_PACKAGE))
			return;
		PACKAGE_NAME = resparam.packageName;
		if(DEBUG){
		XposedBridge.log("App" + PACKAGE_NAME);}
		//XposedBridge.log("Nome" + Preferences.height());
		Field fields[] = R.raw.class.getDeclaredFields();
		for( int i=0; i<fields.length; i++ ) {
		      keyboard_name = fields[i].getName();
		      
		      if(!keyboard_name.contains("n_kbd") && !keyboard_name.contains("new_n_kbd"))  //n_kdb has no arrow keys, new_n_ is for new version
		      {
		    	  keyboard_int = resparam.res.getIdentifier(keyboard_name, "xml" , resparam.packageName );
		    	  
		    	  keyboard.put(keyboard_int, keyboard_name);
  				
		      }
		      
				
		}
	}
	@Override
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
         if (!lpparam.packageName.contains(APP_PACKAGE) && !lpparam.packageName.contains(AMAZON_APP_PACKAGE))
         	return;
         
         
         /*findAndHookMethod("com.nuance.swype.input.KeyboardViewEx", lpparam.classLoader,"displaysAltSymbol", "com.nuance.swype.input.KeyboardEx$Key",new XC_MethodReplacement() {
    	 @Override
            	protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                    return true;
                }
         });*/
         
         
         findAndHookMethod("com.nuance.swype.input.KeyboardEx", lpparam.classLoader, "getKeyboardHeight", "com.nuance.swype.input.KeyboardStyle", int.class, int.class, new XC_MethodHook() {
        	 @Override
             protected void afterHookedMethod(MethodHookParam param) throws Throwable
             {
        		 int id_t = (Integer) XposedHelpers.callMethod(param.thisObject, "getKdbId");
        		 
        		 if( keyboard.containsKey(id_t)){
        			 double id = ((Integer)param.getResult())*1.25*Float.parseFloat(Preferences.height())/100;
        			 //XposedBridge.log("id: " + id);
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
        			 double id = ((Integer)param.getResult())*1.08*Float.parseFloat(Preferences.height())/100;
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
   			     if(DEBUG){
   				     XposedBridge.log("Swype Row. KeyBoard name: " + keyboard.get(id));
   			     }
        		 if (keyboard.containsKey(id)){
        			 
        			 Context mContext = (Context) XposedHelpers.callMethod(param.thisObject, "getContext");
            		 Context moduleContext = mContext.createPackageContext(MODULE_PACKAGE, Context.CONTEXT_IGNORE_SECURITY);
            		 
            		 String kdb_name = keyboard.get(id);

        			 PackageInfo pInfo = mContext.getPackageManager().getPackageInfo(PACKAGE_NAME, 0);
        			 int verCode = pInfo.versionCode;
        			 //XposedBridge.log("Version: " + verCode);
            		 if (!Preferences.hasArrowKeys())
            		 {
            			 kdb_name = "n_" + kdb_name;
        			 }
            		 if(verCode>2000200)
            		 {
            			 kdb_name = "new_" + kdb_name;
            		 }
        			 int id_t =  moduleContext.getResources().getIdentifier(kdb_name, "raw" , MODULE_PACKAGE );
        			 
        			 XmlResourceParser parser = moduleContext.getResources().getXml(id_t);
        			 
        			 
        				
        			 param.args[1] = parser;
        		 }
             }
                 });     
     }
	
}
