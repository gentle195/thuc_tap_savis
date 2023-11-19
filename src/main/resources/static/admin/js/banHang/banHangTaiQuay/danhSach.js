$(document).ready(function () {
    $('.HuyDonDanhSach').click(function () {
        let hoaDonId = $(this).data('id');
        let modalId = $(this).data('target');

        $(modalId).modal('show');

        $(modalId + ' .btn-dong-y').click(function () {
            $.get('/HuyDon/' + hoaDonId, function (response) {
                Swal.fire({
                    icon: 'success', title: 'Đã hủy hóa đơn thành công', showConfirmButton: false, timer: 2000
                }).then(function () {
                    sessionStorage.setItem('isConfirmed', true);
                    window.location.href = "/admin/BanHangTaiQuay";
                });
            });

            $(modalId).modal('hide');
        });

        $(modalId + ' .btn-khong').click(function () {
            // Đóng modal
            $(modalId).modal('hide');
        });
    });
});

$(document).ready(function () {
    $('.banHang').click(function () {
        let hoaDonId = $(this).data('id');
        window.location.href = "/admin/banHang/" + hoaDonId;
    });
});


$(document).ready(function () {
    $('#TaoHoaDonMoi').click(function () {
        fetch('/KiemTraSoLuongHoaDonChoHienCo')
            .then(response => response.json())
            .then(data => {
                console.log(data + "data")
                if (data === true) {
                    // Hiển thị thông báo lỗi
                    Swal.fire({
                        icon: 'error',
                        title: 'Lỗi',
                        text: 'Số lượng hóa đơn chờ đã đạt giới hạn',
                        timer: 2000
                    });
                } else {
                    // Gửi yêu cầu POST tạo hóa đơn
                    $.post(
                        "/TaoHoaDon",
                        function (response) {
                            let idHoaDon = response.idHoaDonVuaTao;
                            Swal.fire({
                                icon: "success",
                                title: "Thành công",
                                text: "Tạo hóa đơn thành công",
                                timer: 2000
                            }).then(function () {
                                // Nếu thanh toán thành công, thực hiện in hóa đơn
                                sessionStorage.setItem("isConfirmed", true);
                                window.location.href = "/admin/banHang/" + idHoaDon;
                            });
                        }
                    ).fail(function (error) {
                        Swal.fire({
                            icon: "error",
                            title: "Lỗi",
                            text: "Lỗi",
                        });
                        console.log(error.responseText);
                    });
                }
            })
            .catch(error => {
                console.error('Lỗi khi gọi API kiểm tra hóa đơn chờ:', error);
            });
    });
});