/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var currentPosition = [], currentAdcode = '';

var geolocation, map = new AMap.Map('map', {
   resizeEnable: true   
});

map.setCity('110000');

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
                                    position: [station.longitude, station.latitude]
                                });
                                marker.setLabel({content: station.stationName, offset: new AMap.Pixel(0, 34)});
                                marker.setMap(map);
                                
                                marker.setExtData(station.stationId);
                                
                                marker.on('click', function(e) {
//                                    alert(marker.getExtData());
                                    onGoStation(marker.getExtData());
                                })
                            }
                        }
                    }
                },
                error: function() {
                    
                }
            });
            
        } else {
            alert(result.message);
        }
    });

    geolocation.getCurrentPosition(function(status, result) {
        if (status == 'complete') {
//          alert(result.formattedAddress);
            currentPosition = [result.position.lng, result.position.lat];
            startPosition = {lng: result.position.lng, lat: result.position.lat};
            $('#search-start-address').val(result.formattedAddress);
            $('#user-position-error').val('');
        } else {
            alert(result.message);
            $('#user-position-error').val('error');
        }
    });
});

var autoCompleteStart, autoCompleteEnd, startPosition = [], endPosition = [];

AMap.plugin(['AMap.Autocomplete'], function(){
    autoCompleteStart = new AMap.Autocomplete({input: "search-start-address"});
    autoCompleteEnd = new AMap.Autocomplete({input: "search-end-address"});
    
    AMap.event.addListener(autoCompleteStart, "select", function(e) {
        if (e.poi && e.poi.location) {
//            map.setZoom(15);
//            map.setCenter(e.poi.location);
//            
//            onClickPoint(false);
            startPosition = e.poi.location;
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
        alert('输入起点');
        return;
    }
    
    if ($('#search-end-address').val() == '') {
        alert('输入终点');
        return;
    }
    
    if (startPosition.length == 0) {
        alert('起点不正确');
        return;
    }
    
    if (endPosition.length == 0) {
        alert('终点不正确');
        return;
    }
    
    var form = $('<form action="searchRoute" method="post">' +
        '<input type="hidden" name="startLat" value="' + startPosition.lat + '" />' +
        '<input type="hidden" name="startLng" value="' + startPosition.lng + '" />' +
        '<input type="hidden" name="endLat" value="' + endPosition.lat + '" />' +
        '<input type="hidden" name="endLng" value="' + endPosition.lng + '" />' +
        '<input type="hidden" name="startAddress" value="' + $('#search-start-address').val() + '" />' +
        '<input type="hidden" name="endAddress" value="' + $('#search-end-address').val() + '" />' +
        '</form>');
    $('body').append(form);

    form.submit();
}

$(function() {
//    $('#btn-search-route').hide();
});


