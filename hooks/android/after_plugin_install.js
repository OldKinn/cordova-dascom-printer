const fs = require('fs-extra');
const path = require('path');

const source = path.join(__dirname, '../../src/android/libs');
const target = path.join(__dirname, '../../../../../app/libs');

fs.copy(source, target, function(err) {
    if (err) {
        console.log(err);
    } else {
        console.log('cordova-plugin-dascomprinter install success');
    }
});