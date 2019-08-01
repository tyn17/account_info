package com.seasolutions.accountinfo;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** AccountInfoPlugin */
public class AccountInfoPlugin implements MethodCallHandler {
  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "account_info");
    channel.setMethodCallHandler(new AccountInfoPlugin(registrar.activity()));
  }

  private Activity activity;
  public AccountInfoPlugin(Activity activity) {
    this.activity = activity;
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("getAccountIds")) {
      List<String> ids = new ArrayList<>();
      Account[] accounts = AccountManager.get(this.activity).getAccounts();
      if(accounts.length > 0) {
        for (Account account : accounts) {
          ids.add(account.name);
        }
      }
      result.success(ids);
    } else {
      result.notImplemented();
    }
  }
}
