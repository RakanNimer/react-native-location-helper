#import "RNOpenSettings.h"
#import <CoreLocation/CoreLocation.h>

@implementation RNOpenSettings

RCT_EXPORT_MODULE(RNOpenSettings);

RCT_EXPORT_METHOD(openLocationSettings) {
  [[UIApplication sharedApplication] openURL:[NSURL URLWithString:UIApplicationOpenSettingsURLString]];
}

RCT_EXPORT_METHOD(openAppPermissionsSettings) {
    [[UIApplication sharedApplication] openURL:[NSURL URLWithString:UIApplicationOpenSettingsURLString]];
}

RCT_EXPORT_METHOD(isLocationEnabled: (RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject) {
    if([CLLocationManager locationServicesEnabled] &&
       [CLLocationManager authorizationStatus] != kCLAuthorizationStatusDenied) {
        // NSString *a = @"1";
        // BOOL *initialBroadcast = YES;
        resolve([NSNumber numberWithBool:YES]);
    } else {
        resolve([NSNumber numberWithBool:NO]);
    }
}

@end
