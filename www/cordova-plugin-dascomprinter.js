var exec = require('cordova/exec');
// 链接打印机
exports.connectPrinter = function(address, success, error) {
    exec(success, error, 'DascomPrinter', 'connectPrinter', [address]);
};
// 取消链接
exports.disconnectPrinter = function(success, error) {
    exec(success, error, 'DascomPrinter', 'disconnectPrinter');
};
// 打印BASE64图片
exports.printImage = function(img, success, error) {
    exec(success, error, 'DascomPrinter', 'printImage', [img]);
};