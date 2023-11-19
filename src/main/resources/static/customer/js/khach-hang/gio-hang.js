$('#checkAll').click(function (event) {
    if (this.checked) {
        $('.form-checkbox').each(function () {
            this.checked = true;
        });
    } else {
        $('.form-checkbox').each(function () {
            this.checked = false;
        });
    }
    tinhTienGioHang();
});


$('.form-checkbox').click(function (event) {
    tinhTienGioHang();
});

function formatNumber(nStr, decSeperate, groupSeperate) {
    nStr += '';
    x = nStr.split(decSeperate);
    x1 = x[0];
    x2 = x.length > 1 ? '.' + x[1] : '';
    var rgx = /(\d+)(\d{3})/;
    while (rgx.test(x1)) {
        x1 = x1.replace(rgx, '$1' + groupSeperate + '$2');
    }
    return x1 + x2;
}

function tinhTienGioHang() {
    //Lấy id của giỏ hàng thì cứ gõ lại cái lệnh var này là được
    var gioHangChiTietIds = $('tbody input[type=checkbox]:checked').map(function name() {
        return $(this).val();
    }).get();
    console.log(gioHangChiTietIds)

    $.ajax({
        url: 'http://localhost:8080/customer/api/gio-hang/tinh-tien',
        type: 'DELETE',
        contentType: 'application/json',
        data: JSON.stringify(gioHangChiTietIds),
        success: function (result) {
            //Tổng tiền khi chọn combobox trả về ở đây muốn lấy thì var tongTien = $('#tongTienGioHang').val();
            $('#tongTienGioHang').text(formatNumber(result, '.', ','));
        },
        error: function (error) {
            console.log(error);
        }
    });
};

function xoaSachGiohang() {
    let id = $("#idGioHang").val();
    $.ajax({
        url: "/customer/gio-hang-chi-tiet/xoa-sach-gio-hang",
        type: "GET",
        data: {
            id: id
        },
        success: function (response) {
            $('.modalXoaSachGioHang').modal('hide');
            if (response.success) {
                console.log(response)
                Swal.fire({
                    icon: "success",
                    title: "Xóa tất cả thành công",
                    showConfirmButton: false,
                    timer: 2000,
                }).then(function () {
                    sessionStorage.setItem("isConfirmed", true);
                    window.location.reload();
                });
            } else {
                console.log("Xóa thất bại: " + response.message);
            }
        },
        error: function (xhr, status, error) {
            console.log("Lỗi khi gửi yêu cầu: " + error);
        },
    });
}

