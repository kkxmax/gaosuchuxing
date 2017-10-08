/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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