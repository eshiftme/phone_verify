/********* phone_verify.m Cordova Plugin Implementation *******/

#import <Cordova/CDV.h>

@interface phone_verify : CDVPlugin {
  // Member variables go here.
}

- (void)mob:(CDVInvokedUrlCommand*)command;
@end

@implementation phone_verify

- (void)mob:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult* pluginResult = nil;
    NSString* echo = [command.arguments objectAtIndex:0];

    if (echo != nil && [echo length] > 0) {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK [NSString initWithFormat:@"%@%@", @"eShift ios: ", messageAsString:echo]];
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

@end
