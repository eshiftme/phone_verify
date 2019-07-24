var exec = require('cordova/exec');

exports.mob = function (msg, success, error) {
  exec(success, error, 'phone_verify', 'mob', [msg]);
};

exports.getCountries = function (success, error) {
  exec(success, error, 'phone_verify', 'getCountries', []);
};

exports.getSMS = function (country, phone, success, error) {
  exec(success, error, 'phone_verify', 'getSMS', [''+country,''+phone]);
};

exports.getVoice = function (country, phone, success, error) {
  exec(success, error, 'phone_verify', 'getVoice', [''+country,''+phone]);
};
