package me.eshift.phone_verify;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.widget.Toast;

//import com.mob.MobSDK;
import com.mob.*;
import cn.smssdk.*;
//import cn.smssdk.gui.*;

/**
 * This class echoes a string called from JavaScript.
 */
public class phone_verify extends CordovaPlugin
{
  private EventHandler m_eh;
  private long m_lastverify;
  public static CallbackContext g_cbc;
  
  @Override
//  public void privateInitialize(String serviceName, CordovaInterface cordova, CordovaWebView webView, CordovaPreferences preferences)
  public void pluginInitialize()
  {
    Context context = this.cordova.getActivity().getApplicationContext();
    MobSDK.init(context);
    
    this.m_lastverify = 0;
    
    this.m_eh = new EventHandler()
    {
      @Override
			public void afterEvent(int event, int result, Object data)
			{
			  if (result == SMSSDK.RESULT_COMPLETE)
			  {
  				if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES)
  				{
  				  ////////
          }
  				else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE)
  				{
  			    phone_verify.g_cbc.success();
  				}
  				else if (event == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE)
  				{
  			    phone_verify.g_cbc.success();
  				}
  				else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE)
  				{
            ////////
  				}
          else
          {
//            ((Throwable)data).printStackTrace(); 
          }
        }
        else
        {
//          ((Throwable)data).printStackTrace(); 
          phone_verify.g_cbc.error(result);
        }
      }
    };
    
    SMSSDK.registerEventHandler(this.m_eh);
    
//Toast.makeText(context,"pluginInitialize",Toast.LENGTH_SHORT).show();
  }
  
  @Override
  public void onDestroy()
  {
    super.onDestroy();
    SMSSDK.unregisterEventHandler(this.m_eh);
  }
  
  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException
  {
    if (action.equals("getCountries"))
    {
      this.getCountries(callbackContext);
      return true;
    }
    else if (action.equals("getSMS"))
    {
      String country = args.getString(0);
      String phone = args.getString(1);
      long tm = System.currentTimeMillis();
      
      if (tm <= this.m_lastverify + 58*1000)
      {
        callbackContext.error(-1);
        return true;
      }
      this.m_lastverify = tm;
      
      this.getSMS(country, phone, callbackContext);
      return true;
    }
    else if (action.equals("getVoice"))
    {
      String country = args.getString(0);
      String phone = args.getString(1);
      long tm = System.currentTimeMillis();
      
      if (tm <= this.m_lastverify + 58*1000)
      {
        callbackContext.error(-1);
        return true;
      }
      this.m_lastverify = tm;
      
      this.getVoice(country, phone, callbackContext);
      return true;
    }
    else if (action.equals("mob"))
    {
      String message = args.getString(0);
      this.mob(message, callbackContext);
      return true;
    }
    
    return false;
  }

  private void getCountries(CallbackContext callbackContext)
  {
    String str = "";
    
    for (Entry<Character, ArrayList<String[]>> ent : SMSSDK.getGroupedCountryList().entrySet())
    {
      ArrayList<String[]> cl = ent.getValue();
      for (String[] paire : cl)
      {
        str += (paire[0] + " (" + paire[1] + ")<br>");
      }
    }
    
    callbackContext.success(str);
  }
  
  private void getSMS(String country, String phone, CallbackContext callbackContext)
  {
    phone_verify.g_cbc = callbackContext;
    
//    SMSSDK.setAskPermisionOnReadContact(true);      // info use when trying to read phone contacts
    SMSSDK.getVerificationCode(country, phone);
  }
  
  private void getVoice(String country, String phone, CallbackContext callbackContext)
  {
    phone_verify.g_cbc = callbackContext;
    
//    SMSSDK.setAskPermisionOnReadContact(true);      // info use when trying to read phone contacts
    SMSSDK.getVoiceVerifyCode(country, phone);
  }
  
  private void mob(String message, CallbackContext callbackContext)
  {
//    SMSSDK.setAskPermisionOnReadContact(true);      // info use when trying to read phone contacts
                  
/** use Mob GUI **
    Context context = this.cordova.getActivity().getApplicationContext();
    
    RegisterPage page = new RegisterPage();
    
    page.setTempCode(null);
    page.setRegisterCallback(new EventHandler()
    {
      public void afterEvent(int event, int result, Object data)
      {
        if (result == SMSSDK.RESULT_COMPLETE)
        {
          HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
          String country = (String) phoneMap.get("country");
          String phone = (String) phoneMap.get("phone");
          
          callbackContext.success("eShift android: 00"+country+"-"+phone);
        }
        else
        {
          // TODO err
        }
      }
    });
    
    page.show(context);

**/
  }
}
