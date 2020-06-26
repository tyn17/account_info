package com.seasolutions.accountinfo;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.pm.PackageManager;
import java.util.ArrayList;
import java.util.List;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;

import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.GET_ACCOUNTS;

/** AccountInfoPlugin */
public class AccountInfoPlugin implements MethodCallHandler, PluginRegistry.RequestPermissionsResultListener {
    final private int myPermissionCode = 1010;
    private Result result;
    /**
     * Plugin registration.
     */
    public static void registerWith(Registrar registrar) {
        final AccountInfoPlugin plugin = new AccountInfoPlugin(registrar.activity());
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "account_info");
        channel.setMethodCallHandler(plugin);
        registrar.addRequestPermissionsResultListener(plugin);
    }

    private Activity activity;

    public AccountInfoPlugin(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        if (call.method.equals("getAccountIds")) {
            this.result = result;
            if (this.checkPermission()) {
                this.updatePluginState();
            } else {
                ActivityCompat.requestPermissions(this.activity, new String[] {GET_ACCOUNTS, READ_CONTACTS}, myPermissionCode);
            }
        } else {
            result.notImplemented();
        }
    }

    private void updatePluginState() {
        List<String> ids = new ArrayList<>();
        Account[] accounts = AccountManager.get(this.activity).getAccountsByType("com.google");
        if (accounts.length > 0) {
            for (Account account : accounts) {
                ids.add(account.name);
            }
        }
        this.result.success(ids);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this.activity.getApplicationContext(), GET_ACCOUNTS);
        int result1 = ContextCompat.checkSelfPermission(this.activity.getApplicationContext(), READ_CONTACTS);
        return (result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public boolean onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        String denied = "";
        if (requestCode == myPermissionCode && grantResults != null && grantResults.length > 0) {
            boolean granted = true;
            for (int index = 0; index < grantResults.length; index ++) {
                if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                    denied += (denied.length() > 0) ? ", " + permissions[index] : permissions[index];
                    granted = false;
                }
            }
            if (granted) {
                updatePluginState();
                return true;
            }
        }
        this.result.error("PERMISSION_DENIED", denied, "");
        return false;
    }
}
