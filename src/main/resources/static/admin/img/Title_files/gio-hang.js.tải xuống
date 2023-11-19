$('#xoaGioHang').click(function (e) {
		var id = $('#cartId').val();
		if(id != null){
			xoaGioHang(id);
		}
});


function xoaGioHang(id) {
	$.ajax({
		url : '' ,
		type : 'DELETE' ,
		contentType : 'application/json' ,
		data : JSON.stringify(ids),
		success : function(result) {
			window.location.href = "${NewURL}?page=1&limit=2&message=delete_success" ;
		},
		error : function (error) {
			window.location.href = "${NewURL}?page=1&limit=2&message=error_system" ;
		}
	});
};