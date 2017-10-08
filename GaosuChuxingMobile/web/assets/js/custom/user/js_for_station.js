/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function onNotSupport() {
    $('#myModal').modal();
}

function onGoTakeout(_stationId) {
    var form = $('<form action="takeout" method="post">' +
        '<input type="hidden" name="stationId" value="' + _stationId + '" />' +
        '</form>');
    $('body').append(form);

    form.submit();
}

function onGoPharmacy(_stationId) {
    var form = $('<form action="pharmacy" method="post">' +
        '<input type="hidden" name="stationId" value="' + _stationId + '" />' +
        '</form>');
    $('body').append(form);

    form.submit();
}
