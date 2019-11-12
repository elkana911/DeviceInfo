var exec = require('cordova/exec');

function Hello() {} 

/*
Hello.prototype.show = function (message, duration, successCallback, errorCallback){
    // create anonym object to send to native
    var obj = {};
    obj.message = message;
    obj.duration = duration;
    cordova.exec(successCallback, errorCallback, 'MyHello', 'show', [obj]);
}*/

Hello.prototype.getSerialNumber = function (successCallback, errorCallback){
    cordova.exec(successCallback, errorCallback, 'MyHello', 'serialNumber', []);
}

Hello.install = function() {
    if (!window.plugins) {
        window.plugins = {};
    }

    window.plugins.deviceInfo = new Hello();
    return window.plugins.deviceInfo;
}

cordova.addConstructor(Hello.install);

/*
exports.coolMethod = function (arg0, success, error) {
    exec(success, error, 'MyToast', 'coolMethod', [arg0]);
};*/
