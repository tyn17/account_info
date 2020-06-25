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
  void getAccounts() {
    AccountInfo.getAccountIds.then((res) {
      setState(() {
        _accountIds = res.toString();
      });
    }).catchError((err) {
      setState(() {
        _accountIds = "Error. " + err.toString();
      });
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
            getAccounts();
          }
        )
      ),
    );
  }
}
