import Flutter
import UIKit
import CloudKit

public class SwiftAccountInfoPlugin: NSObject, FlutterPlugin {
    public static func register(with registrar: FlutterPluginRegistrar) {
        let channel = FlutterMethodChannel(name: "account_info", binaryMessenger: registrar.messenger())
        let instance = SwiftAccountInfoPlugin()
        registrar.addMethodCallDelegate(instance, channel: channel)
    }
    public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
        var ids = Array<String>()
        CKContainer.default().fetchUserRecordID(completionHandler: {
            (recordId, error) in
            if (recordId != nil) {
                ids.append(recordId!.recordName)
            }
            result(ids)
        })
    }
}
