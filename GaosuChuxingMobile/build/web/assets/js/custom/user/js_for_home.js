/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var currentPosition = [], currentAdcode = '';

var geolocation, map = new AMap.Map('map', {
   resizeEnable: true,
   center: [116.407394, 39.904211],
   zoom: 9
});

//map.setCity('110000');

map.plugin('AMap.Geolocation', function() {
    geolocation = new AMap.Geolocation({
        enableHighAccuracy: true,//是否使用高精度定位，默认:true
        timeout: 10000,          //超过10秒后停止定位，默认：无穷大
//        buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
        //zoomToAccuracy: true,      //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
        showButton: false,
        showCircle: false,
        panToLocation: false
//        buttonPosition:'RB'
    });
    
    map.addControl(geolocation);
    
    geolocation.getCityInfo(function(status, result) {
        if (status == 'complete') {
//            alert(result.adcode);
            currentAdcode = result.adcode;
                    
            $.ajax({
                cache: false,
                url: $('#get-station-list-url').val(),
                data: {beijing: 1},
                success: function(_data) {
                    if (_data != null && _data != '') {
                        var json = eval(_data);
                        var markerImgUrl = $('#get-marker-image-url').val();
                        for (var i=0; i < json.stations.length; i++) {
                            var station = json.stations[i];
                            if (station.status) {
                                var marker = new AMap.Marker({
                                    icon: new AMap.Icon({
                                        size: new AMap.Size(26, 34),
                                        image: markerImgUrl
                                    }),
                                    position: [station.longitude, station.latitude],
                                    extData: station.stationId
                                });
                                marker.setLabel({content: station.stationName, offset: new AMap.Pixel(0, 34)});
                                marker.setMap(map);
                                
                                marker.on('click', function(e) {
                                    onGoStation(e.target.F.extData);
                                })
                            }
                        }
                    }
                },
                error: function() {
                    
                }
            });
            
        } else {
//            alert(result.message);
        }
    });

    geolocation.getCurrentPosition(function(status, result) {
        if (status == 'complete') {
//          alert(result.formattedAddress);
            currentPosition = [result.position.lng, result.position.lat];
            startPosition = {lng: result.position.lng, lat: result.position.lat};
            $('#search-start-address').val(result.formattedAddress);
            $('#user-position-error').val('');
            
            $.ajax({
                url: "setLocation",
                data: {lng: result.position.lng, lat: result.position.lat},
                success: function(data) {}
            });
        } else {
//            alert(result.message);
            $('#user-position-error').val('error');
        }
    });
});

var autoCompleteStart, autoCompleteEnd, startPosition = [], endPosition = [];
var districtSearch;

AMap.plugin(['AMap.DistrictSearch','AMap.Autocomplete'], function(){
    autoCompleteStart = new AMap.Autocomplete({input: "search-start-address"});
    autoCompleteEnd = new AMap.Autocomplete({input: "search-end-address"});
    
    districtSearch = new AMap.DistrictSearch();
    
    AMap.event.addListener(autoCompleteStart, "select", function(e) {
        if (e.poi && e.poi.location) {
//            map.setZoom(15);
//            map.setCenter(e.poi.location);
//            
//            onClickPoint(false);
            startPosition = e.poi.location;
        } else if (e.poi && e.poi.adcode) {    
            districtSearch.search(e.poi.adcode, function(status, result) {
                if(status=='complete' && result.info == 'OK') {
                    startPosition = result.districtList[0].center;
                }
            });
        } else {
            startPosition = [];
        }
    });
    
    AMap.event.addListener(autoCompleteEnd, "select", function(e) {
        if (e.poi && e.poi.location) {
            endPosition = e.poi.location;
            
//            alert(endPosition);
            
//            $('#btn-search-route').show();

              showSearchRouteButton();  
        } else if (e.poi && e.poi.adcode) {
            districtSearch.search(e.poi.adcode, function(status, result) {
                if(status=='complete' && result.info == 'OK') {
                    endPosition = result.districtList[0].center;
                    showSearchRouteButton();
                }
            });
        } else {
            endPosition = [];
        }
    });
});

function showSearchRouteButton() {
//    alert('ok');
    $('#btn-search-route').removeClass("hide");
    $('#btn-search-route').show();
}

function gotoCurrentPosition() {
    
    if ($('#user-position-error').val('') != 'error' && currentPosition.length > 0 && currentAdcode != '') {
        map.setCenter(currentPosition);
        
        $.ajax({
            cache: false,
            url: $('#get-station-list-url').val(),
            data: {beijing: 0, adcode: currentAdcode},
            success: function(_data) {
                if (_data != null && _data != '') {
                    var json = eval(_data);
                    var markerImgUrl = $('#get-marker-image-url').val();
                    for (var i=0; i < json.stations.length; i++) {
                        var station = json.stations[i];
                        if (station.status) {
                            var marker = new AMap.Marker({
                                icon: new AMap.Icon({
                                    size: new AMap.Size(26, 34),
                                    image: markerImgUrl
                                }),
                                position: [station.longitude, station.latitude]
                            });
                            marker.setLabel({content: station.stationName, offset: new AMap.Pixel(0, 34)});
                            marker.setMap(map);
                        }
                    }
                }
            },
            error: function() {
                alert('error');
            }
        });
    }
}

function searchRoute() {
    if ($('#search-start-address').val() == '') {
        showMessage('输入起点');
        return;
    }
    
    if ($('#search-end-address').val() == '') {
        showMessage('输入终点');
        return;
    }
    
    if (startPosition.length == 0) {
        showMessage('起点不正确');
        return;
    }
    
    if (endPosition.length == 0) {
        showMessage('终点不正确');
        return;
    }
    
    var historyLength = 1;
    
    var form = $('<form action="searchRoute" method="get">' +
        '<input type="hidden" name="startLat" value="' + startPosition.lat + '" />' +
        '<input type="hidden" name="startLng" value="' + startPosition.lng + '" />' +
        '<input type="hidden" name="endLat" value="' + endPosition.lat + '" />' +
        '<input type="hidden" name="endLng" value="' + endPosition.lng + '" />' +
        '<input type="hidden" name="startAddress" value="' + $('#search-start-address').val() + '" />' +
        '<input type="hidden" name="endAddress" value="' + $('#search-end-address').val() + '" />' +
        '<input type="hidden" name="historyLength" value="' + historyLength + '" />' +
        '</form>');
    $('body').append(form);

    form.submit();
}

$(function() {
//    $('#btn-search-route').hide();
//    $('#welcome-modal').modal();
    
    $.ajax({
        url: "welcomeUser",
        cache: false,
        data: {id: $('#welcomeId').val()},
        success: function(result) {
            if (result != null && result == 'welcome') {
                $('#welcome-modal').modal();
            } else {
                $('#welcome-modal').modal('hide');            
            }
        }
    });
    
//    if (window.history && window.history.pushState) {
//
//        window.history.pushState('forward', null, '#forward');
//
//        $(window).on('popstate', function(e) {
////            alert('Back button was pressed.');
//            location.href = 'home';
//        });
//
//    }
});

function onGoStation(_stationId) {
//    $('#page').val(homePage);
//    $('#stationId').val(_stationId);
    var historyLength = 1;
    
    var form = $('<form action="station" method="get">' +
        '<input type="hidden" name="stationId" value="' + _stationId + '" />' +
        '<input type="hidden" name="page" value="' + homePage + '" />' +
        '<input type="hidden" name="historyLength" value="' + historyLength + '" />' +
        '</form>');
    $('body').append(form);

    form.submit();
}

function onGoCoupon() {    
//    $('#welcome-modal').modal('hide');
    
    var form = $('<form action="userCoupon" method="get">' +
        '<input type="hidden" name="page" value="' + homePage + '" />' +
        '</form>');
    $('body').append(form);

    form.submit();
}


