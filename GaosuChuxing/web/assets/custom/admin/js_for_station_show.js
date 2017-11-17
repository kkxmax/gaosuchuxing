/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function() {    
    setMarker();
});

function setMarker() {
    if ($('#longitude').val() != '' && $('#latitude').val() != '') {
        addMarker($('#longitude').val(), $('#latitude').val());
        
        map.setZoom(15);
        map.setCenter([parseFloat($('#longitude').val()), parseFloat($('#latitude').val())]);
    }
}

function onMapSearch(_cityCode) {    
    districtSearch.search(_cityCode, function(status, result) {
        if(status=='complete'){
            map.setZoom(11);
            map.setCenter(result.districtList[0].center);
        }
    });

    autoComplete.setCity(_cityCode);
    addressSearch.setCity(_cityCode);
    
    $('#search-address').val('');
    
    onClickPoint(false);
}

function onSearchAddress() {
    var searchKey = $('#search-address').val();
    
    if (searchKey != '') {
        addressSearch.search(searchKey, function(status, result){
            if(status=='complete'){
                map.setZoom(15);
                map.setCenter(result.districtList[0].center);                
            }
        });
    }
    
    onClickPoint(false);
    
    return false;
}

function addMarker(lng, lat) {
    if (marker != null) {
        marker.setMap(null);
        marker = null;
    }
    
    marker = new AMap.Marker({
        icon: "http://webapi.amap.com/theme/v1.3/markers/n/mark_r.png",
        position: [lng, lat]
    });
        
    var markerContent = document.createElement("div");    
        
    var markerImg = document.createElement("img");
    markerImg.className = "markerlnglat";
    markerImg.src = "http://webapi.amap.com/theme/v1.3/markers/n/mark_r.png";
    markerContent.appendChild(markerImg);
        
    if ($('#name').val() != '') {
        var markerSpan = document.createElement("span");
        markerSpan.className = 'marker';
        markerSpan.innerHTML = $('#name').val();
        markerContent.appendChild(markerSpan);    
        marker.setContent(markerContent);        
    }
    
    marker.setMap(map);
    
    $('#longitude').val(lng);
    $('#latitude').val(lat);
}

var marker, map = new AMap.Map('station-map', {
   resizeEnable: true,
   zoom:11
});

var opts = {
    level: "city",
    subdistrict: 1,   //返回下一级行政区
    showbiz:false  //最后一级返回街道信息
};

var districtSearch, autoComplete, addressSearch;
AMap.plugin(['AMap.DistrictSearch','AMap.Autocomplete'],function(){
    districtSearch = new AMap.DistrictSearch(opts);
    autoComplete = new AMap.Autocomplete({input: "search-address"});
    addressSearch = new AMap.Autocomplete();
    
    AMap.event.addListener(autoComplete, "select", function(e) {
        if (e.poi && e.poi.location) {
            map.setZoom(15);
            map.setCenter(e.poi.location);
        }
    });
});






