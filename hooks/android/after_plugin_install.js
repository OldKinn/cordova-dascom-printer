const fs = require('fs-extra');
const path = require('path');

module.exports = function(ctx) {
    if (!ctx.opts.platforms.includes('android')) return;

    const source = path.join(__dirname, '../../src/android/libs');
    const target = path.join(ctx.opts.projectRoot, '/platforms/android/app/libs');

    fs.copy(source, target, function(err) {
        if (err) {
            console.log(err);
        } else {
            console.log(`libs-dir:${source}`);
            console.log(`libs-dest:${target}`);
            console.log('^_^ cordova-plugin-dascomprinter install success');
        }
    });
};