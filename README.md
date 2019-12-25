# 得实打印机Cordova插件

## 蓝牙链接
```
document.getElementById('connect').addEventListener('click', function() {
    cordova.plugins.DascomPrinter.connectPrinter('00:15:83:E5:5C:1E', function(connectData) {
        debug('蓝牙链接', connectData);
    }, function(err) {
        debug('链接失败', err);
    });
});
```

## 打印图片
```
document.getElementById('printImg').addEventListener('click', function() {
    var file = document.getElementById('image').files[0];
    var reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = (e) => {
        debug('Base64图片', e.target.result);
        cordova.plugins.DascomPrinter.printImage(e.target.result, function(data) {
            debug('打印图片', data);
        }, function(err) {
            debug('打印图片失败', err);
        });
    }
});
```

## 断开蓝牙
```
document.getElementById('disconnect').addEventListener('click', function() {
    cordova.plugins.DascomPrinter.disconnectPrinter(function(data) {
        debug('断开蓝牙', data);
    });
});
```

## 蓝牙设备搜索

推荐使用蓝牙插件cordova-plugin-bluetoothle
```
cordova plugin add cordova-plugin-bluetoothle
```

扫描dascom蓝牙打印机
```
document.getElementById('start2').addEventListener('click', function() {
    bluetoothle.hasPermission(function(has) {
        debug('检测权限', has);
        if (has.hasPermission) {
            bluetoothle.startScan(function(scanData) {
                debug('扫描蓝牙', scanData);
                if (scanData.status === 'scanResult') {
                    if (scanData.address === '00:15:83:E5:5C:1E') {
                        debug('扫描到打印机');
                    }
                }
            }, function() {}, {
                scanMode: bluetoothle.SCAN_MODE_BALANCED
            });
            return false;
        }
        bluetoothle.requestPermission(function(requestPermission) {
            debug('请求权限', requestPermission);
        }, function(err) {
            debug('请求权限异常', err);
        });
    });
});
document.getElementById('cancel2').addEventListener('click', function() {
    bluetoothle.stopScan(function(stopData) {
        debug('停止扫描', stopData);
    }, function(err) {
        debug('停止扫描异常', err);
    });
});
```
