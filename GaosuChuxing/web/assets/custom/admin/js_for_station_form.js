/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var validateOptions = {
    errorElement: 'span', //default input error message container
    errorClass: 'help-block help-block-error', // default input error message class
    focusInvalid: false, // do not focus the last invalid input
    ignore: "",  // validate all fields including form hidden input
    messages: {required: "不允许为空"},
    rules: {
        minlength: 1,
        required: true
    },
    errorPlacement: function (error, element) { // render error placement for each input type
        if (element.parent(".input-group").size() > 0) {
            error.insertAfter(element.parent(".input-group"));
        } else if (element.attr("data-error-container")) { 
            error.appendTo(element.attr("data-error-container"));
        } else if (element.parents('.radio-list').size() > 0) { 
            error.appendTo(element.parents('.radio-list').attr("data-error-container"));
        } else if (element.parents('.radio-inline').size() > 0) { 
            error.appendTo(element.parents('.radio-inline').attr("data-error-container"));
        } else if (element.parents('.checkbox-list').size() > 0) {
            error.appendTo(element.parents('.checkbox-list').attr("data-error-container"));
        } else if (element.parents('.checkbox-inline').size() > 0) { 
            error.appendTo(element.parents('.checkbox-inline').attr("data-error-container"));
        } else {
            error.insertAfter(element); // for other inputs, just perform default behavior
        }
    },
    highlight: function (element) { // hightlight error inputs
        $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
    },
    unhighlight: function (element) { // revert the change done by hightlight
        $(element).closest('.form-group').removeClass('has-error'); // set error class to the control group
    },
    success: function (label) {
        label.closest('.form-group').removeClass('has-error'); // set success class to the control group
    } 
};

validateSelectOptions = {
    errorElement: 'span', //default input error message container
    errorClass: 'help-block help-block-error', // default input error message class
    focusInvalid: false, // do not focus the last invalid input
    ignore: "",  // validate all fields including form hidden input
    messages: {required: "不允许为空", min: "不允许为空"},
    rules: {
        required: true,
        min: 1,
        number: true
    },
    errorPlacement: function (error, element) { // render error placement for each input type
        if (element.parent(".input-group").size() > 0) {
            error.insertAfter(element.parent(".input-group"));
        } else if (element.attr("data-error-container")) { 
            error.appendTo(element.attr("data-error-container"));
        } else if (element.parents('.radio-list').size() > 0) { 
            error.appendTo(element.parents('.radio-list').attr("data-error-container"));
        } else if (element.parents('.radio-inline').size() > 0) { 
            error.appendTo(element.parents('.radio-inline').attr("data-error-container"));
        } else if (element.parents('.checkbox-list').size() > 0) {
            error.appendTo(element.parents('.checkbox-list').attr("data-error-container"));
        } else if (element.parents('.checkbox-inline').size() > 0) { 
            error.appendTo(element.parents('.checkbox-inline').attr("data-error-container"));
        } else {
            error.insertAfter(element); // for other inputs, just perform default behavior
        }
    },
    highlight: function (element) { // hightlight error inputs
        $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
    },
    unhighlight: function (element) { // revert the change done by hightlight
        $(element).closest('.form-group').removeClass('has-error'); // set error class to the control group
    },
    success: function (label) {
        label.closest('.form-group').removeClass('has-error'); // set success class to the control group
    } 
};

var handleValidateStationForm = function() {
    var form1 = $('#stationForm');
    var error1 = $('.alert-danger', form1);
    var success1 = $('.alert-success', form1);

    //IMPORTANT: update CKEDITOR textarea with actual content before submit
    form1.on('submit', function() {
        for(var instanceName in CKEDITOR.instances) {
            CKEDITOR.instances[instanceName].updateElement();
        }
    })
    
    form1.validate({
        errorElement: 'span', //default input error message container
        errorClass: 'help-block help-block-error', // default input error message class
        focusInvalid: false, // do not focus the last invalid input
        ignore: "",  // validate all fields including form hidden input
        messages: {
            select_multi: {
                maxlength: jQuery.validator.format("Max {0} items allowed for selection"),
                minlength: jQuery.validator.format("At least {0} items must be selected")
            },            
            name: { required: "不允许为空" },
            status: { required: "不允许为空" },
            deliverId: { required: "不允许为空", min: "不允许为空" }
        },
        rules: {
            name: {
                minlength: 1,
                required: true 
            },             
            status: {
                required: true
            },
            deliverId: {
                required: true,
                min: 1,
                number: true
            }
        },

        errorPlacement: function (error, element) { // render error placement for each input type
            if (element.parent(".input-group").size() > 0) {
                error.insertAfter(element.parent(".input-group"));
            } else if (element.attr("data-error-container")) { 
                error.appendTo(element.attr("data-error-container"));
            } else if (element.parents('.radio-list').size() > 0) { 
                error.appendTo(element.parents('.radio-list').attr("data-error-container"));
            } else if (element.parents('.radio-inline').size() > 0) { 
                error.appendTo(element.parents('.radio-inline').attr("data-error-container"));
            } else if (element.parents('.checkbox-list').size() > 0) {
                error.appendTo(element.parents('.checkbox-list').attr("data-error-container"));
            } else if (element.parents('.checkbox-inline').size() > 0) { 
                error.appendTo(element.parents('.checkbox-inline').attr("data-error-container"));
            } else {
                error.insertAfter(element); // for other inputs, just perform default behavior
            }
        },

        invalidHandler: function (event, validator) { //display error alert on form submit              
            success1.hide();
            error1.show();
            Metronic.scrollTo(error1, -200);
        },

        highlight: function (element) { // hightlight error inputs
            $(element)
                .closest('.form-group').addClass('has-error'); // set error class to the control group
        },

        unhighlight: function (element) { // revert the change done by hightlight
            $(element)
                .closest('.form-group').removeClass('has-error'); // set error class to the control group
        },

        success: function (label) {
            label
                .closest('.form-group').removeClass('has-error'); // set success class to the control group
        },

        submitHandler: function (form) {
            success1.show();
            error1.hide();

            if ($('#districtId').val() == null || $('#districtId').val() == '') {
                showMessage('请选择所属城市');
            } else if ($('#longitude').val() == '' && $('#latitude').val() == '' || isNaN($('#longitude').val()) || isNaN($('#latitude').val()) || parseFloat($('#longitude').val()) == 0 || parseFloat($('#latitude').val()) == 0) {
                showMessage('请选择服务区位置');
            } else if ($('#img_file').val() == '' && $('#id').val() == '0') {
                showMessage('请选择服务区图片');
            } else {
                $.ajax({
                    url: $('#validate-station-name-url').val(),
                    type: 'POST',
                    data: {stationId: $('#id').val(), stataionName: $('#name').val()},
                    success: function(_result) { 
                        if (_result != null && _result == 'success') {
                            if ($('#img_file').val() != '') {
                                var file = $('#img_file')[0].files[0];
                                var fileName = file.name;
                                var ext = fileName.split('.')[fileName.split('.').length - 1].toLowerCase();
                                if (file.size > 1024 * 1024 * 1024) {
                                    showMessage('图片不超过1M');
                                } else if (ext != 'jpg' && ext != 'jpeg' && ext != 'png') {
                                    showMessage('图片格式错误，要求是jpg、jpeg、png格式');
                                } else {
                                    form.submit();
                                }
                                
//                                var fd = new FormData();
//                                var file = $('#img_file')[0].files[0];
//                                fd.append('file', file);
//                                
//                                $.ajax({
//                                    url: $('#validate-station-file-url').val(),
//                                    type: "POST",
//                                    data: fd,
//                                    processData: false,
//                                    contentType: false,
//                                    enctype: 'multipart/form-data',
//                                    success: function(_response) {
//                                        // .. do something
//                                        if (_response == 'success')
//                                            form.submit();
//                                        else if (_response == 'invalidSize')
//                                            showMessage('图片不超过1M');
//                                        else
//                                            showMessage('图片格式错误，要求是jpg、jpeg、png格式');
//                                    },
//                                    error: function() {
//                                        showMessage('图片格式错误，要求是jpg、jpeg、png格式');
//                                    }
//                                });
                            } else {
                                form.submit();
                            }
                        } else {
                            showMessage('服务区名称重复');
                        }
                    },
                    error: function() {
                        showMessage('服务区名称重复');
                    }
                });
            }
            
        }


    });

//        $('.date-picker').datepicker({
//            rtl: Metronic.isRTL(),
//            autoclose: true
//        });

    $('.date-picker .form-control').change(function() {
        form1.validate().element($(this)); //revalidate the chosen dropdown value and show error or success 
        //message for the input 
    });
}

var validationName = function() {
    var element = $('#name');    
    element.validate(validateOptions);
}

var validationStatus = function() {
    var element = $('#status');    
    element.validate(validateSelectOptions);
}

var validationDeliverId = function() {
    var element = $('#deliverId');    
    element.validate(validateSelectOptions);
}

$(function() {
    handleValidateStationForm();
    
    validationName();
    validationStatus();
    validationDeliverId();
    
    $('.validation-control').on('blur', function() {
        $(this).valid();
    });
    
    $('#districtParentId').live('change', function() {
        $.ajax({
            cache: false,
            url: $('#get-district-list-url').val(),
            data: {parentId: $(this).val()},
            dataType: "html",
            success: function (_html) {
                $('.district-opt').empty().append(_html);
                
                var cityCode = $('#districtId option:selected').attr("data-code");
                if (cityCode != '') {
                    $('#station-map-container').show();
                    onMapSearch(cityCode);
                } else {
                    $('#station-map-container').hide();
                }
            }
        });
    });
    
    $('#districtId').live('change', function() {
        var cityCode = $('#districtId option:selected').attr("data-code");

        if (cityCode != '') {
            $('#station-map-container').show();            
            onMapSearch(cityCode);
        } else {
            $('#station-map-container').hide();
        }
    });
    
    if ($('#districtParentId').val() == '' || $('#districtId').val() == null || $('#districtId').val() == '') {
        $('#station-map-container').hide();
    }
    
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
                map.setCenter(result.districtList[0].location);                
            }
        });
    }
    
    onClickPoint(false);
    
    return false;
}

function onClickPoint(_isClickPoint) {    
    isClickPoint = _isClickPoint;
    
//    if (isClickPoint) {
//        map.setDoubleClickZoom(false);
//        map.setDragEnable(false);
//    } else {
//        map.setDoubleClickZoom(true);
//        map.setDragEnable(true);
//    }
}

function addMarker(lng, lat) {
    if (marker != null) {
        marker.setMap(null);
        marker = null;
    }
    
    marker = new AMap.Marker({
        icon: "http://webapi.amap.com/theme/v1.3/markers/n/mark_r.png",
        position: [lng, lat],
        draggable: true,
        cursor: 'move'
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
        
    marker.on('mouseup', function(e) {
        console.log('mouseup' + ", " + e.lnglat.getLng() +", " + e.lnglat.getLat());
        $('#longitude').val(e.lnglat.getLng());
        $('#latitude').val(e.lnglat.getLat());
    });
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

var districtSearch, autoComplete, addressSearch, isClickPoint;
AMap.plugin(['AMap.DistrictSearch','AMap.Autocomplete'],function(){
    districtSearch = new AMap.DistrictSearch(opts);
    autoComplete = new AMap.Autocomplete({input: "search-address"});
    addressSearch = new AMap.Autocomplete();
    
    AMap.event.addListener(autoComplete, "select", function(e) {
        if (e.poi && e.poi.location) {
            map.setZoom(15);
            map.setCenter(e.poi.location);
            
            onClickPoint(false);
        }
    });
});

map.on('click', function(e){
    if (isClickPoint) {
        confirmYesNoMessage("确定选择该位置？", function(msg) {
            if (msg == true) {
                onClickPoint(false);
                
                addMarker(e.lnglat.getLng(), e.lnglat.getLat());
            }
        })
    }
}); 






