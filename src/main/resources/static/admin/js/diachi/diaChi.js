$('#formSubmitDiaChi').validate({
	rules: {
		city : {
			required :true ,
		} ,
		ward : "required",
		hoTen : "required",
		district : {
			required: true ,
		},
		soNha : {
			required: true ,
		},
		soDienThoai : {
			required: true ,
		}
	},
	messages: {
		city : {
			required :"Vui lòng chọn thành phố !" ,
		},
		ward : "Vui lòng chọn xã !",
		hoTen : "Vui lòng nhập họ tên !",
		district : {
			required : "Vui lòng chọn huyện !",
		},
		soNha : {
			required : "Vui lòng nhập số nhài !",
		},
		soDienThoai : {
			required : "Vui lòng nhập số điện thoại !",
		}
	}
});

$('#btnThemMoiDiaChi').click(function (e) {
	e.preventDefault();
	//Ko co cai nay la no se submit vao url no dang dung
	if($('#formSubmitDiaChi').valid()){
		var data = {};
		var formData = $('#formSubmitDiaChi').serializeArray();
		$.each(formData,function(i,v){
			data[""+v.name+""] = v.value;
		});
		themDiaChi(data);
	}
});

function themDiaChi(data) {
	$.ajax({
		url : 'http://localhost:8080/admin/api/dia-chi' ,
		type : 'POST' ,
		contentType : 'application/json' ,
		data : JSON.stringify(data) ,
		dataType : 'json' ,
		success : function(result) {
//				$('#liveToast').html('<div class="toast-header"><strong class="mr-auto">Thông báo !</strong><small>1 giây trước </small><button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close"><span aria-hidden="true">&times;</span></button></div><div class="toast-body"><p class="fw-bold text-success">Thêm mới  địa chỉ thành công !</p></div>');
//				$('#liveToast').toast('show');
			Swal.fire({
	            icon: 'success',
	            title: 'Thêm mới địa chỉ thành công !',
	            showConfirmButton: false,
	            timer: 1500
	        });
			setTimeout("location.href = ' http://localhost:8080/admin/khach-hang/danh-sach/chinh-sua?id="+result.khachHangId+"&page=1'   ", 2000);
//			window.location.href = "http://localhost:8080/admin/khach-hang/danh-sach/chinh-sua?id="+result.khachHangId+"&page=1&message=create_address_success" ;

		},
		error : function (error) {
			$('#liveToast').html('<div class="toast-header"><strong class="mr-auto">Thông báo !</strong><small>1 giây trước </small><button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close"><span aria-hidden="true">&times;</span></button></div><div class="toast-body"><p class="fw-bold text-danger">Thêm mới  địa chỉ thất bại !</p></div>');
			$('#liveToast').toast('show');

//			window.location.href = "http://localhost:8080/admin/khach-hang/danh-sach/1?message=error_system" ;
			setTimeout("location.href = ' http://localhost:8080/admin/khach-hang/danh-sach/1?message=error_system'   ", 2000);
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



function xacNhanXoaDiaChi() {
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
		url : 'http://localhost:8080/admin/api/dia-chi' ,
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
			setTimeout("location.href = ' http://localhost:8080/admin/khach-hang/danh-sach/1?message=delete_address_success'   ", 2000);
		},
		error : function (error) {

		}
	});
}