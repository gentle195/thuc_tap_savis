$(document).ready(function () {
    $('.custom-control-input').click(function () {
        const role = $('#role').val();
        var userId = $(this).val();
        var status = $(this).is(':checked') ? 1 : 0;
        var idLogin = $('#idLogin').val();

        if(userId != idLogin){
        	if (role == 'ROLE_STAFF' || role == 'ROLE_CUSTOMER') {
                Swal.fire({
                    icon: 'error',
                    title: 'Bạn không có quyền !',
                    timer: 3000
                });
                setTimeout("location.href = 'http://localhost:8080/admin/khach-hang/danh-sach/1' ", 2000);
            } else {
                $.post("/admin/api/cap-nhat-trang-thai", {userId: userId, status: status}, function (data) {
                    Swal.fire({
                        icon: 'success',
                        title: 'Cập nhật trạng thái thành công',
                        showConfirmButton: false,
                        timer: 1500
                    });
                }).fail(function (error) {
                    Swal.fire({
                        icon: 'error',
                        title: 'Có lỗi xảy ra',
                        timer: 1500
                    });
                });
            }
        }else{
        	Swal.fire({
                icon: 'error',
                title: 'Bạn không được tắt hoạt động của chính mình !',
                timer: 1500
            });
        	setTimeout("location.href = 'http://localhost:8080/admin/khach-hang/danh-sach/1' ", 2000);
        }
        
        
    });
});


$('#input').on('change', function () {
    var limit = $('#limitForSearch').val();
    var trangThai = $('#trangThaiForSearch').val();
    var input = $('#input').val();


    if (input == "") {
//		window.location.href=""+trangThai+"&limit="+limit;
        setTimeout("location.href = 'http://localhost:8080/admin/khach-hang/danh-sach/tim-kiem/1?trangThai=" + trangThai + "&limit=" + limit + " ' ", 2000);
    } else {
        $('#liveToast').html('<div class="toast-header"><strong class="mr-auto">Thông báo !</strong><small>1 giây trước </small><button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close"><span aria-hidden="true">&times;</span></button></div><div class="toast-body"><p class="fw-bold text-primary">Đang tìm kiếm...</p></div>');
        $('#liveToast').toast('show');
        setTimeout("location.href = 'http://localhost:8080/admin/khach-hang/danh-sach/tim-kiem/1?input=" + input + "&trangThai=" + trangThai + "&limit=" + limit + "     ' ", 1000);
//		window.location.href="http://localhost:8080/admin/khach-hang/danh-sach/tim-kiem/1?input="+input+"&trangThai="+trangThai+"&limit="+limit;
    }
});


//function fun()
//{
//    if(document.getElementById("trangThaiSelect").value!="" || document.getElementById("limit").value!="")
//        document.getElementById("loc").disabled=false;
//
//    else
//    document.getElementById("loc").disabled=true;
//
//}


$('#trangThaiSelect').on('change', function fun(e) {
    var optionTrangThai = $('#trangThaiSelect').find(":selected").val();
    $('#trangThai').val(optionTrangThai);
});
$('#limitSelect').on('change', function fun(e) {
    var optionLimit = $('#limitSelect').find(":selected").val();
    $('#limit').val(optionLimit);
});

$('#checkAll').click(function (event) {
    if (this.checked) {
        // Iterate each checkbox
        $('.form-checkbox').each(function () {
            this.checked = true;
        });
    } else {
        $('.form-checkbox').each(function () {
            this.checked = false;
        });
    }
});


function xacNhanChuyenDoiTrangThaiDaChonThanhHoatDong() {
    Swal.fire({
        title: 'Xác nhận chuyển đổi',
        text: "Bạn có chắc chắn muốn chuyển đổi các trạng thái ?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Chuyển đổi'
    }).then((result) => {
        if (result.isConfirmed) {
            var ids = $('tbody input[type=checkbox][class=form-checkbox]:checked').map(function name() {
                return $(this).val();
            }).get();
            var pageCurrent = $('#pageCurrent').val();
            if (ids != '') {
                if (result.value) {
                    const role = $('#role').val();
                    if (role == 'ROLE_STAFF' || role == 'ROLE_CUSTOMER') {
                        Swal.fire({
                            icon: 'error',
                            title: 'Bạn không có quyền !',
                            timer: 1500
                        });
                        window.location.href = "http://localhost:8080/admin/khach-hang/danh-sach/1";
                    } else {
                        capNhatTrangThaiThanhHoatDong(ids, pageCurrent);
                    }

                }
            } else {
                Swal.fire({
                    icon: "danger",
                    title: "Cảnh báo",
                    text: "Bạn chưa chọn khách hàng !",
                });
            }
        }
    })
};

function capNhatTrangThaiThanhHoatDong(ids, pageCurrent) {
    $.ajax({
        url: 'http://localhost:8080/admin/api/khach-hang/trang-thai-dang-hoat-dong',
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(ids),
        success: function (result) {
            $('#liveToast').html('<div class="toast-header"><strong class="mr-auto">Thông báo !</strong><small>1 giây trước </small><button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close"><span aria-hidden="true">&times;</span></button></div><div class="toast-body"><p class="fw-bold text-success">Chuyển đổi thành công !</p></div>');
            $('#liveToast').toast('show');

            setTimeout("location.href = 'http://localhost:8080/admin/khach-hang/danh-sach/" + pageCurrent + "' ", 2000);
        },
        error: function (error) {
            setTimeout("location.href = 'http://localhost:8080/admin/khach-hang/danh-sach/" + pageCurrent + "' ", 2000);
        }
    });
}


function xacNhanChuyenDoiTrangThaiDaChonThanhKhongHoatDong() {
    Swal.fire({
        title: 'Xác nhận chuyển đổi',
        text: "Bạn có chắc chắn muốn chuyển đổi các trạng thái ?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Chuyển đổi'
    }).then((result) => {
        if (result.isConfirmed) {
            var ids = $('tbody input[type=checkbox][class=form-checkbox]:checked').map(function name() {
                return $(this).val();
            }).get();
            var pageCurrent = $('#pageCurrent').val();
            if (ids != '') {
                if (result.value) {
                    const role = $('#role').val();
                    if (role == 'ROLE_STAFF' || role == 'ROLE_CUSTOMER') {
                        Swal.fire({
                            icon: 'error',
                            title: 'Bạn không có quyền !',
                            timer: 1500
                        });
                        window.location.href = "http://localhost:8080/admin/khach-hang/danh-sach/1";
                    } else {
                        capNhatTrangThaiThanhKhongHoatDong(ids, pageCurrent);
                    }

                }
            } else {
                Swal.fire({
                    icon: "danger",
                    title: "Cảnh báo",
                    text: "Bạn chưa chọn khách hàng !",
                });
            }
        }
    })
};

function capNhatTrangThaiThanhKhongHoatDong(ids, pageCurrent) {
    $.ajax({
        url: 'http://localhost:8080/admin/api/khach-hang/trang-thai-khong-hoat-dong',
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(ids),
        success: function (result) {
            $('#liveToast').html('<div class="toast-header"><strong class="mr-auto">Thông báo !</strong><small>1 giây trước </small><button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close"><span aria-hidden="true">&times;</span></button></div><div class="toast-body"><p class="fw-bold text-success">Chuyển đổi thành công !</p></div>');
            $('#liveToast').toast('show');
            setTimeout("location.href = 'http://localhost:8080/admin/khach-hang/danh-sach/" + pageCurrent + "' ", 2000);
        },
        error: function (error) {
            setTimeout("location.href = 'http://localhost:8080/admin/khach-hang/danh-sach/" + pageCurrent + "' ", 2000);
        }
    });
}


$('#formSubmitKhachHang').validate({
    rules: {
        email: {
            required: true,
            email: true
        },
        matKhau: "required",
        hoTen: "required",
        soDienThoai: {
            required: true,
            number: true,
            minlength: 10,
            maxlength: 10
        }
    },
    messages: {
        email: {
            required: "Vui lòng nhập email !",
            email: "Email không đúng định dạng !"
        },
        matKhau: "Vui lòng nhập mật khẩu !",
        hoTen: "Vui lòng nhập họ tên !",
        soDienThoai: {
            required: "Vui lòng nhập số điện thoại !",
            number: "Số điện thoại không được chứa ký tự !",
            minlength: "Số điện thoại phải 10 số ! "
        }
    }
});

$('#btnCapNhatHoacThemMoiKhachHang').click(function (e) {
    //Ko co cai nay la no se submit vao url no dang dung
    if ($('#formSubmitKhachHang').valid()) {
        e.preventDefault();
        var data = {};
        var formData = $('#formSubmitKhachHang').serializeArray();
        $.each(formData, function (i, v) {
            data["" + v.name + ""] = v.value;
        });
        var id = $('#khachHangId').val();
        if (id == "") {
            themKhachHang(data);
        } else {
            capNhatKhachHang(data);
        }
    }
});


function themKhachHang(data) {
    $.ajax({
        url: 'http://localhost:8080/admin/api/khach-hang',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        dataType: 'json',
        success: function (result) {
            $('#liveToast').html('<div class="toast-header"><strong class="mr-auto">Thông báo !</strong><small>1 giây trước </small><button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close"><span aria-hidden="true">&times;</span></button></div><div class="toast-body"><p class="fw-bold text-success">Thêm mới  thành công !</p></div>');
            $('#liveToast').toast('show');
//			window.location.href = "http://localhost:8080/admin/khach-hang/danh-sach/chinh-sua?id="+result.id+"&page=1&message=create_success" ;
            Swal.fire({
                icon: 'success',
                title: 'Thêm mới khách hàng thành công !',
                showConfirmButton: false,
                timer: 1500
            });
            setTimeout("location.href = 'http://localhost:8080/admin/khach-hang/danh-sach/chinh-sua?id=" + result.id + "&page=1'      ", 2000);
        },
        error: function (error) {
            $('#liveToast').html('<div class="toast-header"><strong class="mr-auto">Thông báo !</strong><small>1 giây trước </small><button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close"><span aria-hidden="true">&times;</span></button></div><div class="toast-body"><p class="fw-bold text-danger">Thêm mới thất bại !</p></div>');
            $('#liveToast').toast('show');
            Swal.fire({
                icon: 'error',
                title: 'Thêm mới khách thất bại !',
                showConfirmButton: false,
                timer: 1500
            });
            //window.location.href = "http://localhost:8080/admin/khach-hang/danh-sach/chinh-sua?message=duplicate_email&email="+data.email ;
            setTimeout("location.href = 'http://localhost:8080/admin/khach-hang/danh-sach/chinh-sua?email=" + data.email + "&message=duplicate_email '      ", 2000);
        }
    });
};

function capNhatKhachHang(data) {
    $.ajax({
        url: "http://localhost:8080/admin/api/khach-hang",
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(data),
        dataType: 'json',
        success: function (result) {
            $('#liveToast').html('<div class="toast-header"><strong class="mr-auto">Thông báo !</strong><small>1 giây trước </small><button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close"><span aria-hidden="true">&times;</span></button></div><div class="toast-body"><p class="fw-bold text-success">Cập nhật  thành công !</p></div>');
            $('#liveToast').toast('show');
//			window.location.href = "http://localhost:8080/admin/khach-hang/danh-sach/chinh-sua?id="+result.id+"&page=1&message=update_success"  ;
            Swal.fire({
                icon: 'success',
                title: 'Cập nhật khách hàng thành công !',
                showConfirmButton: false,
                timer: 1500
            });
            setTimeout("location.href = 'http://localhost:8080/admin/khach-hang/danh-sach/chinh-sua?id=" + result.id + "&page=1 '      ", 2000);
        },
        error: function (error) {
            $('#liveToast').html('<div class="toast-header"><strong class="mr-auto">Thông báo !</strong><small>1 giây trước </small><button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close"><span aria-hidden="true">&times;</span></button></div><div class="toast-body"><p class="fw-bold text-danger">Cập nhật thất bại !</p></div>');
            $('#liveToast').toast('show');
            Swal.fire({
                icon: 'error',
                title: 'Cập nhật khách thất bại !',
                showConfirmButton: false,
                timer: 1500
            });
//			window.location.href = "http://localhost:8080/admin/khach-hang/danh-sach/chinh-sua?id="+data.id+"&message=update_duplicate_email&email="+data.email ;
            setTimeout("location.href = 'http://localhost:8080/admin/khach-hang/danh-sach/chinh-sua?id=" + data.id + "&email=" + data.email + "&message=update_duplicate_email'     ", 2000);
        }
    });
}

$(document).ready(function () {
    $(".diaChiRadio").change(function () {
        if ($(this).is(":checked")) {
            $(".diaChiRadio").not(this).prop("checked", false);

            let idKhachHang = $("#idKhachHang").val();
            let idDiaChi = $(this).data("id");
            updateDiaChiMacDinh(idDiaChi, idKhachHang);
        }
    });

    function updateDiaChiMacDinh(diaChiId, khachHangId) {
        $.ajax({
            url: "/admin/khach-hang/update-dia-chi-mac-dinh",
            type: "POST",
            data: {
                DiaChiID: diaChiId,
                KhachHangID: khachHangId,
            },
            success: function (response) {
                if (response.success) {
                    Swal.fire({
                        icon: "success",
                        title: "Thiết lập địa chỉ mặc định thành công",
                        showConfirmButton: false,
                        timer: 2000,
                    }).then(function () {
                        sessionStorage.setItem("isConfirmed", true);
                    });
                } else {
                    console.log("Lỗi: " + response.error);
                }
            },
            error: function (xhr, status, error) {
                console.log("Lỗi khi gửi yêu cầu: " + error);
            },
        });
    }
});




