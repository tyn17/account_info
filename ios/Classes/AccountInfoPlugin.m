#import "AccountInfoPlugin.h"
#import <account_info/account_info-Swift.h>

@implementation AccountInfoPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftAccountInfoPlugin registerWithRegistrar:registrar];
}
@end
