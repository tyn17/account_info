# account_info

A flutter plugin to get Account information is logged-in on the device.

## Getting Started
```
dependencies:
  flutter:
    sdk: flutter

  carousel_pro:
    git:
      url: git://github.com/tyn17/account_info.git
      ref: master
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