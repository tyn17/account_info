package com.seasolutions.accountinfo;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;

import androidx.core.content.ContextCompat;
import android.content.pm.PackageManager;
import java.util.ArrayList;
import java.util.List;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.GET_ACCOUNTS;

/** AccountInfoPlugin */
public class AccountInfoPlugin implements MethodCallHandler {
    /**
     * Plugin registration.
     */
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
            int permission = this.checkPermission();
            if (permission == 0) {
                List<String> ids = new ArrayList<>();
                Account[] accounts = AccountManager.get(this.activity).getAccountsByType("com.google");
                if (accounts.length > 0) {
                    for (Account account : accounts) {
                        ids.add(account.name);
                    }
                }
                result.success(ids);
            } else if (permission == 1) {
                result.error("GET_ACCOUNTS", "Not GET_ACCOUNTS Permission", null);
            } else if (permission == 2) {
                result.error("READ_CONTACTS", "Not READ_CONTACTS Permission", null);
            } else {
                result.error("[GET_ACCOUNTS, READ_CONTACTS]", "Not [GET_ACCOUNTS, READ_CONTACTS] Permissions", null);
            }
        } else {
            result.notImplemented();
        }
    }

    private int checkPermission() {
        int result = ContextCompat.checkSelfPermission(this.activity.getApplicationContext(), GET_ACCOUNTS);
        int result1 = ContextCompat.checkSelfPermission(this.activity.getApplicationContext(), READ_CONTACTS);
        if (result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED) {
            return 0;
        } else if (result != PackageManager.PERMISSION_GRANTED && result1 != PackageManager.PERMISSION_GRANTED) {
            return 3;
        } else if (result1 != PackageManager.PERMISSION_GRANTED) {
            return 2;
        } else {
            return 1;
        }
    }
}
