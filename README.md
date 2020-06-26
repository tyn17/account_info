# account_info

A flutter plugin to get Account information is logged-in on the device.

## Getting Started
```
dependencies:
  flutter:
    sdk: flutter

  account_info:
    git:
      url: git://github.com/tyn17/account_info.git
      ref: v_1_0
```

```
AccountInfo.getAccountIds.then((res) {
  setState(() {
    _accountIds = res.toString();
  });
}).catchError((err) {
  setState(() {
    _accountIds = "Error. " + err.toString();
  });
});
```