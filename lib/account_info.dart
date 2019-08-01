import 'dart:async';

import 'package:flutter/services.dart';

class AccountInfo {
  static const MethodChannel _channel =
      const MethodChannel('account_info');

  static Future<dynamic> get getAccountIds async {
    final ids = await _channel.invokeMethod('getAccountIds');
    return ids;
  }
}
