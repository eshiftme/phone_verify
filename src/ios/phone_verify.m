/********* phone_verify.m Cordova Plugin Implementation *******/

#import <Cordova/CDV.h>
#import <SMS_SDK/SMSSDK.h>

@interface phone_verify : CDVPlugin {
  // Member variables go here.
}

- (void)mob:(CDVInvokedUrlCommand*)command;
- (void)getCountries:(CDVInvokedUrlCommand*)command;
- (void)getSMS:(CDVInvokedUrlCommand*)command;
- (void)getVoice:(CDVInvokedUrlCommand*)command;
@end

@implementation phone_verify

- (void)mob:(CDVInvokedUrlCommand*)command
{
  CDVPluginResult* pluginResult = nil;
  NSString* echo = [command.arguments objectAtIndex:0];
  echo = [NSString stringWithFormat:@"%@%@", @"eShift ios: ", echo];

  if (echo != nil && [echo length] > 0)
  {
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:echo];
  }
  else
  {
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
  }

  [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)getCountries:(CDVInvokedUrlCommand*)command
{
}

- (void)getSMS:(CDVInvokedUrlCommand*)command
{
  CDVPluginResult* pluginResult = nil;
  NSString* country = [command.arguments objectAtIndex:0];
  NSString* phone = [command.arguments objectAtIndex:1];
  
  [SMSSDK getVerificationCodeByMethod:SMSGetCodeMethodSMS phoneNumber:phone zone:country result:^(NSError *error)
  {
    if (!error)
    {
      pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    }
    else
    {
      pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }
  }];
}

- (void)getVoice:(CDVInvokedUrlCommand*)command
{
  CDVPluginResult* pluginResult = nil;
  NSString* country = [command.arguments objectAtIndex:0];
  NSString* phone = [command.arguments objectAtIndex:1];
  
  [SMSSDK getVerificationCodeByMethod:SMSGetCodeMethodVoice phoneNumber:phone zone:country result:^(NSError *error)
  {
    if (!error)
    {
      pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    }
    else
    {
      pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }
  }];
}

@end
