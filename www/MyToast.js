var exec = require('cordova/exec');

function Hello() {} 

/*
Hello.prototype.show = function (message, duration, successCallback, errorCallback){
    // create anonym object to send to native
    var obj = {};
    obj.message = message;
    obj.duration = duration;
    cordova.exec(successCallback, errorCallback, 'MyToast', 'show', [obj]);
}*/

Hello.prototype.getSerialNumber = function (successCallback, errorCallback){
    cordova.exec(successCallback, errorCallback, 'MyToast', 'serialNumber', []);
}

Hello.prototype.getBatteryLevel = function (successCallback, errorCallback){
    cordova.exec(successCallback, errorCallback, 'MyToast', 'batterylevel', []);
}

Hello.prototype.getModelName = function (successCallback, errorCallback){
    cordova.exec(successCallback, errorCallback, 'MyToast', 'modelname', []);
}

Hello.prototype.getOSVersion = function (successCallback, errorCallback){
    cordova.exec(successCallback, errorCallback, 'MyToast', 'osversion', []);
}

Hello.prototype.getDeviceName = function (successCallback, errorCallback){
    cordova.exec(successCallback, errorCallback, 'MyToast', 'devicename', []);
}

Hello.prototype.getManufacturer = function (successCallback, errorCallback){
    cordova.exec(successCallback, errorCallback, 'MyToast', 'manufacturer', []);
}

Hello.prototype.getBuildNumber = function (successCallback, errorCallback){
    cordova.exec(successCallback, errorCallback, 'MyToast', 'buildnumber', []);
}

Hello.prototype.getKernelVersion = function (successCallback, errorCallback){
    cordova.exec(successCallback, errorCallback, 'MyToast', 'kernelversion', []);
}

Hello.prototype.getWifiStatus = function (successCallback, errorCallback){
    cordova.exec(successCallback, errorCallback, 'MyToast', 'wifistatus', []);
}

Hello.prototype.getMobileNetworkStatus = function (successCallback, errorCallback){
    cordova.exec(successCallback, errorCallback, 'MyToast', 'mobilenetwork', []);
}

Hello.prototype.getBluetoothStatus = function (successCallback, errorCallback){
    cordova.exec(successCallback, errorCallback, 'MyToast', 'bluetoothstatus', []);
}

Hello.prototype.getImeiDefault = function (successCallback, errorCallback){
    cordova.exec(successCallback, errorCallback, 'MyToast', 'imei', []);
}

Hello.prototype.getImeiFirst = function (successCallback, errorCallback){
    cordova.exec(successCallback, errorCallback, 'MyToast', 'imei1', []);
}

Hello.prototype.getImeiSecond = function (successCallback, errorCallback){
    cordova.exec(successCallback, errorCallback, 'MyToast', 'imei2', []);
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
