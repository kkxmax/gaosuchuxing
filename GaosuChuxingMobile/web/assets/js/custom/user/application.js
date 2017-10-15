/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function showMessage(_msg) {
    $('#confirm-message-msg').html(_msg);
    $('#confirm-message-modal').modal({show: true});
}

function confirmYesNoMessage(_msg, _callback) {
    $('#confirm-yes-no-msg').html(_msg);
    $('#confirm-yes-no-modal').modal({show: true});
    
    $('#button-yes').unbind('click').click(function() {
        $('#confirm-yes-no-modal').modal('hide');
        if (_callback) {
            _callback(true);
        }
    });
}

function showInformation(_msg) {
    $('#information-message-msg').html(_msg);
    $('#information-message-modal').modal({show: true});
}

function onGoStationInfo(_stationId) {
    var form = $('<form action="stationInfo" method="post">' +
        '<input type="hidden" name="stationId" value="' + _stationId + '" />' +
        '</form>');
    $('body').append(form);

    form.submit();
}

function onGoStation(_stationId) {
    var form = $('<form action="station" method="post">' +
        '<input type="hidden" name="stationId" value="' + _stationId + '" />' +
        '</form>');
    $('body').append(form);

    form.submit();
}

function onGoShop(_shopId) {
    var form = $('<form action="shop" method="post">' +
        '<input type="hidden" name="shopId" value="' + _shopId + '" />' +
        '</form>');
    $('body').append(form);

    form.submit();
}

function isJson(str) {
    try {
        JSON.parse(str);
    } catch (e) {
        return false;
    }
    return true;
}