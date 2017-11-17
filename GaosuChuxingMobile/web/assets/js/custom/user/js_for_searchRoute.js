/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {
//    if (window.history && window.history.pushState) {
//
//        window.history.pushState('forward', null, '#forward');
//
//        $(window).on('popstate', function() {
////            alert('Back button was pressed.');
//            location.href = 'home';
//        });
//
//    }
    
    $('#arrow_up').click(function() {
        if($('#arrow_up').attr('pos') == undefined || $('#arrow_up').attr('pos') == 'top') {
            $('#animate_part').animate({height: '30%'});
            $('#arrow_up').attr('src', $('#get-slider-image-url').val());
            $('#arrow_up').attr('pos', 'bottom');
        }
        else {
            $('#animate_part').animate({height: '80%'});
            $('#arrow_up').attr('src', $('#get-slider-image2-url').val());
            $('#arrow_up').attr('pos', 'top');
        }
    });
    
    startLng = parseFloat($('#start-lng').val());
    startLat = parseFloat($('#start-lat').val());
    endLng = parseFloat($('#end-lng').val());
    endLat = parseFloat($('#end-lat').val());
    
    driving.search(new AMap.LngLat(startLng,startLat), new AMap.LngLat(endLng,endLat), function(status, result){
        if (status == 'complete' && result.info == 'OK') {
            totalStationCount = 0;
            openStationCount = 0;
            
            var time = result.routes[0].time;
            var distance = result.routes[0].distance;
                        
            if (time >= 60 * 60) { //hour
                var hour = parseInt(time / 3600);
                var min = parseInt((time - hour*3600) / 60);
                $('#time-msg').text(hour + '小时' + min + '分钟');
            } else if (time >= 60) {
                var min = parseInt(time / 60);
                $('#time-msg').text(min + '分钟');
            } else {
                $('#time-msg').text(time + '秒钟');
            }
            
            if (distance > 1000) { //km
                var km = parseInt(distance / 1000);
                $('#distance-msg').text(km + '公里');
            } else {
                $('#distance-msg').text(distance + '米');
            }
                        
            var startMarkerImgUrl = $('#get-start-marker-image-url').val();
            var endMarkerImgUrl = $('#get-end-marker-image-url').val();

            var startMarker = new AMap.Marker({
                icon: new AMap.Icon({
                    size: new AMap.Size(25, 30),
                    image: startMarkerImgUrl
                }),
                position: [startLng, startLat]
            });

            startMarker.setTitle($('#start-address').val());
            startMarker.setMap(map);

            var endMarker = new AMap.Marker({
                icon: new AMap.Icon({
                    size: new AMap.Size(25, 30),
                    image: endMarkerImgUrl
                }),
                position: [endLng, endLat]
            });

            endMarker.setTitle($('#end-address').val());
            endMarker.setMap(map);
            
////            var params = {};
//            var steps = result.routes[0].steps;
////            var index = 0;            
//            for (var i=0; i < steps.length; i++) {
//                var step = steps[i];
//                var params = {};
//                var index = 0;
////                var startLocation = step.start_location;
////                var endLocation = step.end_location;
////                params["startLng_" + i] = startLocation.lng;                
////                params["startLat_" + i] = startLocation.lat;                
////                params["endLng_" + i] = endLocation.lng;                
////                params["endLat_" + i] = endLocation.lat;                
////                params["distance_" + i] = step.distance;
//
//                for (var j=0; j < step.path.length; j++) {
//                    var path = step.path[j];                    
//                    params["pointLat_" + index] = path.lat;
//                    params["pointLng_" + index] = path.lng;
//                    index++;
//                }
//                
//                sendStepInfo(params);
//            }
                        
//            $.ajax({
//                cache: false,
//                url: "getStationListByNear2",
//                type : 'POST',
//                data: params,
//                success: function(_data) {
//                    if (_data != null && _data != "") {
//                        var json = eval(_data);
//                        
//                        var markerImgUrl = $('#get-marker-image-url').val();
//                        
//                        $('#station-total-count').text(json.stations.length);
//                        var stationCount = 0;
//                        
//                        for (var i=0; i < json.stations.length; i++) {
//                            var station = json.stations[i];
//                            if (station.status) {
//                                stationCount++;
//                                var marker = new AMap.Marker({
//                                    icon: new AMap.Icon({
//                                        size: new AMap.Size(26, 34),
//                                        image: markerImgUrl
//                                    }),
//                                    position: [station.longitude, station.latitude],
//                                    extData: station.stationId
//                                });
////                                marker.setLabel({content: station.stationName, offset: new AMap.Pixel(0, 34)});
//                                marker.setMap(map);
//                                                                
//                                marker.on('click', function(e) {
////                                    alert(e.target.Pg.extData);
//                                    onGoStation(e.target.Pg.extData);
//                                });
//                                
//                            }                            
//                            
//                            appendTimeline(station, marker);
//                            
//                            
//                        }
//                        
//                        $('#station-count').text(stationCount);
//                    }
//                }
//            });
        }
    });
    
//    $('#loading-modal').modal();
            
    $.ajax({
        cache: false,
        url: "getStationListByNear3",
        data: {startLng: startLng, startLat: startLat, endLng: endLng, endLat: endLat},
        success: function(_data) {
//            $('#loading-modal').modal('hide');

            if (_data != null && _data != 'error') {
                try {
                    var json = eval(_data);
                    var markerImgUrl = $('#get-marker-image-url').val();
                    $('#station-total-count').text(json.stations.length);
                    var stationCount = 0;

                    if (json.stations.length > 0) {
                        $('#routeNotify').removeClass("hide");
                        $('#routeNotify').show();
                    } 
//                    else {
//                        showInformation("没有路径规划结果");
//                        return;
//                    }

                    for (var i=0; i < json.stations.length; i++) {
                       var station = json.stations[i];
                       if (station.status) {
                           stationCount++;
                           var marker = new AMap.Marker({
                               icon: new AMap.Icon({
                                   size: new AMap.Size(26, 34),
                                   image: markerImgUrl
                               }),
                               position: [station.longitude, station.latitude],
                               extData: station.stationId
                           });
//                                marker.setLabel({content: station.stationName, offset: new AMap.Pixel(0, 34)});
                           marker.setLabel({content: station.stationName + " " + station.time, offset: new AMap.Pixel(0, 34)});

                           marker.setMap(map);

                           marker.on('click', function(e) {
                               onGoStation(e.target.F.extData);
                           });

                       }                            

                       appendTimelineStation(station);
                   }                           

                   $('#station-count').text(stationCount);

                } catch(e) {
                    alert("json parse error");
                }
            } else {
                alert("路径规划错误");
            }
        },
        error: function() {
//            $('#loading-modal').modal('hide');
        }
    });
    
//    var startMarkerImgUrl = $('#get-start-marker-image-url').val();
//    var endMarkerImgUrl = $('#get-end-marker-image-url').val();
//
//    var startMarker = new AMap.Marker({
//        icon: new AMap.Icon({
//            size: new AMap.Size(25, 30),
//            image: startMarkerImgUrl
//        }),
//        position: [startLng, startLat]
//    });
//    
//    startMarker.setTitle($('#start-address').val());
//    startMarker.setMap(map);
//    
//    var endMarker = new AMap.Marker({
//        icon: new AMap.Icon({
//            size: new AMap.Size(25, 30),
//            image: endMarkerImgUrl
//        }),
//        position: [endLng, endLat]
//    });
//    
//    endMarker.setTitle($('#end-address').val());
//    endMarker.setMap(map);
});

var totalStationCount = 0;
var openStationCount = 0;

function sendStepInfo(params) {
    $.ajax({
        cache: false,
        url: "getStationListByNear2",
        type : 'POST',
        data: params,
        success: function(_data) {
            if (_data != null && _data != "") {
                var json = eval(_data);

                var markerImgUrl = $('#get-marker-image-url').val();

                totalStationCount += json.stations.length;
//                $('#station-total-count').text(json.stations.length);
                $('#station-total-count').text(totalStationCount);
                var stationCount = 0;

                for (var i=0; i < json.stations.length; i++) {
                    var station = json.stations[i];
                    if (station.status) {
                        stationCount++;
                        var marker = new AMap.Marker({
                            icon: new AMap.Icon({
                                size: new AMap.Size(26, 34),
                                image: markerImgUrl
                            }),
                            position: [station.longitude, station.latitude],
                            extData: station.stationId
                        });
//                                marker.setLabel({content: station.stationName, offset: new AMap.Pixel(0, 34)});
                        marker.setMap(map);

                        marker.on('click', function(e) {
                            onGoStation(e.target.F.extData);
                        });

                    }                            

                    appendTimeline(station, marker);


                }

//                $('#station-count').text(stationCount);
                openStationCount += stationCount;
                $('#station-count').text(openStationCount);
            }
        }
    });
}

function appendTimelineStation(station) {
    var div;
    
    if (station.status) {
        div = '<div class="timeline-item">' +
                '<div class="timeline-badge">' +
                '<img class="timeline-badge-userpic" src="'+$('#get-map-icon-red-url').val()+'" style="width: 17px;">' +
                '</div>' +
                '<div class="timeline-body">' +
                '<div class="timeline-body-head">' +
                '<div class="timeline-body-head-caption">' +
                '<div class="col-xs-12 init-div-horizontal">' +
                '<div class="col-xs-7 init-div-horizontal">' +
                '<div class="row init-div-horizontal">' +
                '<span class="timeline-body-time" style="color: black;">' +
                station.stationName +
                '</span>' +
                '</div>' +
                '<div class="row init-div-horizontal">' +
                '<span class="timeline-body-time" style="color: black;">' +
                station.distance + ', ' + station.time +
                '</span>' +
                '</div>' +
                '</div>' +
                '<div class="col-xs-4 init-div-horizontal">' +
                '<label class="btn btn-transparent btn-circle btn-red btn-sm col-xs-12 init-div-horizontal" onclick="onGoStation(\'' + station.stationId + '\')"> 进入服务区 </label>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>';
    } else {
        div = '<div class="timeline-item">'+
                '<div class="timeline-badge">' +
                '<img class="timeline-badge-userpic" src="'+$('#get-map-icon-grey-url').val()+'" style="width: 17px;">' +
                '</div>' +
                '<div class="timeline-body">' +
                '<div class="timeline-body-head">' +
                '<div class="timeline-body-head-caption">' +
                '<div class="col-xs-12 init-div-horizontal">' +
                '<div class="col-xs-7 init-div-horizontal">' +
                '<div class="row init-div-horizontal">' +
                '<span class="timeline-body-time" style="color: black;">' +
                station.stationName +
                '</span>' +
                '</div>' +
                '<div class="row init-div-horizontal">' +
                '<span class="timeline-body-time" style="color: black;">' +
                station.distance + ', ' + station.time +
                '</span>' +
                '</div>' +
                '</div>' +
                '<div class="col-xs-4 init-div-horizontal">' +
                '<label class="btn btn-transparent btn-circle btn-grey btn-sm col-xs-12 init-div-horizontal" onclick="onGoStationInfo(\'' + station.stationId + '\')"> 进入服务区 </label>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>';
    }
    
    $('#timeline').append(div);
}

function appendTimeline(station, marker) {
    drivingSearch.search(new AMap.LngLat(startLng,startLat), new AMap.LngLat(station.longitude, station.latitude), function(status, result){
        if (status == 'complete' && result.info == 'OK') {
            var time = result.routes[0].time;
            var distance = result.routes[0].distance;            
            var tt, dd;                                        
            if (time >= 60 * 60) { //hour
                var hour = parseInt(time / 3600);
                var min = parseInt((time - hour*3600) / 60);
                tt = hour + '小时' + min + '分钟';
            } else if (time >= 60) {
                var min = parseInt(time / 60);
                tt = min + '分钟';                                            
            } else {
                tt = time + '秒钟';
            }

            if (distance > 1000) { //km
                var km = parseInt(distance / 1000);
                dd = km + '公里';
            } else {
                dd = distance + '米';
            }

            var div;

            if (station.status) {
                $('#routeNotify').removeClass("hide");
                $('#routeNotify').show();
                marker.setLabel({content: station.stationName + " " + tt, offset: new AMap.Pixel(0, 34)});

                div = '<div class="timeline-item">' +
                        '<div class="timeline-badge">' +
                        '<img class="timeline-badge-userpic" src="'+$('#get-map-icon-red-url').val()+'" style="width: 17px;">' +
                        '</div>' +
                        '<div class="timeline-body">' +
                        '<div class="timeline-body-head">' +
                        '<div class="timeline-body-head-caption">' +
                        '<div class="col-xs-12 init-div-horizontal">' +
                        '<div class="col-xs-7 init-div-horizontal">' +
                        '<div class="row init-div-horizontal">' +
                        '<span class="timeline-body-time" style="color: black;">' +
                        station.stationName +
                        '</span>' +
                        '</div>' +
                        '<div class="row init-div-horizontal">' +
                        '<span class="timeline-body-time" style="color: black;">' +
                        dd + ', ' + tt +
                        '</span>' +
                        '</div>' +
                        '</div>' +
                        '<div class="col-xs-4 init-div-horizontal">' +
                        '<label class="btn btn-transparent btn-circle btn-red btn-sm col-xs-12 init-div-horizontal" onclick="onGoStation(\'' + station.stationId + '\')"> 进入服务区 </label>' +
                        '</div>' +
                        '</div>' +
                        '</div>' +
                        '</div>' +
                        '</div>' +
                        '</div>';

            } else {
                $('#routeNotify').removeClass("hide");
                $('#routeNotify').show();
                div = '<div class="timeline-item">'+
                        '<div class="timeline-badge">' +
                        '<img class="timeline-badge-userpic" src="'+$('#get-map-icon-grey-url').val()+'" style="width: 17px;">' +
                        '</div>' +
                        '<div class="timeline-body">' +
                        '<div class="timeline-body-head">' +
                        '<div class="timeline-body-head-caption">' +
                        '<div class="col-xs-12 init-div-horizontal">' +
                        '<div class="col-xs-7 init-div-horizontal">' +
                        '<div class="row init-div-horizontal">' +
                        '<span class="timeline-body-time" style="color: black;">' +
                        station.stationName +
                        '</span>' +
                        '</div>' +
                        '<div class="row init-div-horizontal">' +
                        '<span class="timeline-body-time" style="color: black;">' +
                        dd + ', ' + tt +
                        '</span>' +
                        '</div>' +
                        '</div>' +
                        '<div class="col-xs-4 init-div-horizontal">' +
                        '<label class="btn btn-transparent btn-circle btn-grey btn-sm col-xs-12 init-div-horizontal" onclick="onGoStationInfo(\'' + station.stationId + '\')"> 进入服务区 </label>' +
                        '</div>' +
                        '</div>' +
                        '</div>' +
                        '</div>' +
                        '</div>' +
                        '</div>';
            }

            $('#timeline').append(div);

        }
    });    
}

var driveSteps = [];

var startLng, startLat, endLng, endLat;

var map = new AMap.Map('map', {
   resizeEnable: true   
});

var driving = new AMap.Driving({
   map: map,
   hideMarkers: true,
   extensions: 'all',
   policy: AMap.DrivingPolicy.LEAST_TIME
});

var drivingSearch = new AMap.Driving({
//   map: map,
   hideMarkers: true,
   policy: AMap.DrivingPolicy.LEAST_TIME
});

function onGoStation(_stationId) {
//    $('#startLat').val(startLat);
//    $('#startLng').val(startLng);
//    $('#endLat').val(endLat);
//    $('#endLng').val(endLng);
//    $('#page').val(searchRoutePage);
//    $('#stationId').val(_stationId);
    
    var form = $('<form action="station" method="get">' +
        '<input type="hidden" name="stationId" value="' + _stationId + '" />' +
        '<input type="hidden" name="startLat" value="' + startLat + '" />' +
        '<input type="hidden" name="startLng" value="' + startLng + '" />' +
        '<input type="hidden" name="endLat" value="' + endLat + '" />' +
        '<input type="hidden" name="endLng" value="' + endLng + '" />' +
        '<input type="hidden" name="page" value="' + searchRoutePage + '" />' +
        '<input type="hidden" name="historyLength" value="' + getHistoryLength() + '" />' +
        '</form>');
    $('body').append(form);

    form.submit();
}


