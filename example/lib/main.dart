import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:account_info/account_info.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  var _accountIds = null;

  @override
  void initState() {
    super.initState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    dynamic accountIds;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      accountIds = await AccountInfo.getAccountIds;
    } on PlatformException {
      accountIds = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _accountIds = accountIds;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text(this._accountIds == null ? 'Unknow' : 'Data: ${this._accountIds}\n'),
        ),
        floatingActionButton: FloatingActionButton(
          child: Text('Click me'),
          onPressed: () {
            initPlatformState();
          }
        )
      ),
    );
  }
}
