$(document).ready(function () {
    var sdt = $('#sdtKhachHang').val();
    if(sdt == null || sdt == ''){
    	document.getElementById("sdtKhachHang").disabled = false;
    }else{
    	document.getElementById("sdtKhachHang").disabled = true;
    }
});

$('#password, #confirm_password').on('keyup', function() {
			if ($('#password').val() == $('#confirm_password').val()) {
				$('#message').html('Hợp lệ ').css('color', 'green');
			} else
				$('#message').html('Chưa khớp').css('color', 'red');

});
$('#btnDoiMatKhauTaiKhoan').click(function (e) {
	e.preventDefault();
	//Ko co cai nay la no se submit vao url no dang dung
		var data = {};
		var formData = $('#formDoiMatKhauTaiKhoan').serializeArray();
		$.each(formData,function(i,v){
			data[""+v.name+""] = v.value;
		});
		capNhatMatKhau(data);
});

function capNhatMatKhau(data) {
	$.ajax({
		url : 'http://localhost:8080/customer/api/tai-khoan/doi-mat-khau' ,
		type : 'PUT' ,
		contentType : 'application/json' ,
		data : JSON.stringify(data) ,
		success : function(result) {
			 Swal.fire({
            icon: 'success',
            title: 'Đổi mật khẩu thành công !',
            showConfirmButton: false,
            timer: 1500
        });
			 console.log(result);
//     		window.location.href = "http://localhost:8080/customer/quan-ly-tai-khoan?page=1&message=insert_success" ;
     		setTimeout("location.href = ' http://localhost:8080/customer/quan-ly-tai-khoan?page=1'   ", 2000);
		},
		error : function (error) {
			Swal.fire({
	            icon: 'warning',
	            title: error.responseText,
	            showConfirmButton: false,
	            timer: 1500
	        });
		}
	});
};



$('#formThemMoiDiaChiDangNhap').validate({
	rules: {
		diaChi : "required" ,
		/*hoTen : "required",
		soDienThoai : {
			required: true ,
		}*/
	},
	messages: {
		diaChi : "Vui lòng nhập địa chỉ !",
		/*hoTen : "Vui lòng nhập họ tên !",
		soDienThoai : {
			required : "Vui lòng nhập số điện thoại !",
		}*/
	}
});

$('#btnThemMoiDiaChiDangNhap').click(function (e) {
	e.preventDefault();
	//Ko co cai nay la no se submit vao url no dang dung
	if(!$('#formThemMoiDiaChiDangNhap').valid()){
		return ;
	}
	var data = {};
	var formData = $('#formThemMoiDiaChiDangNhap').serializeArray();
	$.each(formData,function(i,v){
		data[""+v.name+""] = v.value;
	});
	themDiaChi(data);
});

function themDiaChi(data) {
	$.ajax({
		url : 'http://localhost:8080/customer/api/dia-chi/khach-hang-dang-nhap' ,
		type : 'POST' ,
		contentType : 'application/json' ,
		data : JSON.stringify(data) ,
		success : function(result) {
			 Swal.fire({
            icon: 'success',
            title: 'Thêm mới địa chỉ thành công !',
            showConfirmButton: false,
            timer: 1500
        });
//     		window.location.href = "http://localhost:8080/customer/quan-ly-tai-khoan?page=1&message=insert_success" ;
     		setTimeout("location.href = ' http://localhost:8080/customer/quan-ly-tai-khoan?page=1&message=insert_success'   ", 2000);
		},
		error : function (error) {
			$('#themMoiDiaChiKhachHangDangNhapModal').modal('hide');
			$('#liveToast').html('<div class="toast-header"><strong class="mr-auto">Thông báo !</strong><small>1 giây trước </small><button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close"><span aria-hidden="true">&times;</span></button></div><div class="toast-body"><p class="fw-bold text-danger">Thêm mới  địa chỉ thất bại !</p></div>');
			$('#liveToast').toast('show');
		}
	});
};

$('#checkAllDiachi').click(function(event) {
	if(this.checked) {
		// Iterate each checkbox
		$(':checkbox').each(function() {
			this.checked = true;
		});
	} else {
		$(':checkbox').each(function() {
			this.checked = false;
		});
	}
});
function xacNhanXoaDiaChiDangNhap() {
	Swal.fire({
		title: 'Xác nhận xóa địa chỉ',
		text: "Bạn có chắc chắn muốn xóa các địa chỉ đã chọn ?",
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: 'Xóa'
	}).then((result) =>
	{
		if (result.isConfirmed) {
			var ids = $('tbody input[type=checkbox]:checked').map(function name() {
				return $(this).val();
			}).get();
			if(ids != ''){
				if(result.value){
					xoaDiaChi(ids);
				}
			}else{
				
				$('#liveToast').html('<div class="toast-header"><strong class="mr-auto">Thông báo !</strong><small>1 giây trước </small><button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close"><span aria-hidden="true">&times;</span></button></div><div class="toast-body"><p class="fw-bold text-danger">Bạn chưa chọn địa chỉ !</p></div>');
				$('#liveToast').toast('show');
			}
		}
	})
};
function xoaDiaChi(ids) {
	$.ajax({
		url : 'http://localhost:8080/customer/api/dia-chi' ,
		type : 'DELETE' ,
		contentType : 'application/json' ,
		data : JSON.stringify(ids),
		success : function(result) {
				 Swal.fire({
	            icon: 'success',
	            title: 'Xóa địa chỉ thành công !',
	            showConfirmButton: false,
	            timer: 1500
	        });
			setTimeout("location.href = ' http://localhost:8080/customer/quan-ly-tai-khoan?page=1&message=delete_success'   ", 2000);
		},
		error : function (error) {
			$('#liveToast').html('<div class="toast-header"><strong class="mr-auto">Thông báo !</strong><small>1 giây trước </small><button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close"><span aria-hidden="true">&times;</span></button></div><div class="toast-body"><p class="fw-bold text-danger">Xóa địa chỉ thất bại !</p></div>');
			$('#liveToast').toast('show');
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
            url: "/customer/api/update-dia-chi-mac-dinh",
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
