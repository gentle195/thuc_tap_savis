function redirectToChoxacNhan() {
    window.location.href = "/admin/DonHang/ChoXacNhanDonHang/danhSach";
}

function redirectToChoGiaoHang() {
    window.location.href = "/admin/DonHang/ChoGiaoHang/danhSach";
}

function redirectToDangGiao() {
    window.location.href = "/admin/DonHang/DangGiaoHang/danhSach";
}

function redirectToDaGiao() {
    window.location.href = "/admin/DonHang/DaGiaoHang/danhSach";
}

function redirectToDahuy() {
    window.location.href = "/admin/DonHang/DaHuyHang/danhSach";
}

<!-- JS ĐANG GIAO -> ĐÃ GIAO -->
$(document).ready(function () {
    $('.DaGiao').click(function () {
        let hoaDonId = $(this).data('id');
        let modalId = $(this).data('target');

        // Hiển thị modal xác nhận
        $(modalId).modal('show');

        // Xử lý sự kiện khi bấm nút Đồng ý
        $(modalId + ' .btn-dong-y').click(function () {
            // Gửi yêu cầu xác nhận đơn hàng đã giao bằng Ajax
            $.get('/updateGiaoHangThanhCong/' + hoaDonId, function (response) {
                // Hiển thị thông báo xác nhận thành công với SweetAlert2
                Swal.fire({
                    icon: 'success',
                    title: 'Xác nhận đơn hàng đã giao thành công',
                    showConfirmButton: false,
                    timer: 2000
                }).then(function () {
                    // Lưu trạng thái đã xác nhận vào sessionStorage
                    sessionStorage.setItem('isConfirmed', true);

                    // Tải lại trang
                    location.reload();
                });
            });

            // Đóng modal
            $(modalId).modal('hide');
        });

        // Xử lý sự kiện khi bấm nút Không
        $(modalId + ' .btn-khong').click(function () {
            // Đóng modal
            $(modalId).modal('hide');
        });
    });
});


<!-- JS HOÀN THÀNH TẤT CẢ-->
$(document).ready(function () {
    $('#HoanThanhTatCa').click(function () {
        // Kiểm tra số lượng hàng trong bảng
        let rowCount = $('#user-table-DangGiao tbody tr').length;

        // Kiểm tra nếu không có dữ liệu
        if (rowCount === 0) {
            Swal.fire({
                icon: 'error',
                title: 'Lỗi',
                text: 'Không có dữ liệu',
                showConfirmButton: false,
                timer: 2000
            });
            return; // Dừng thực hiện các lệnh tiếp theo
        }

        let hoaDonId = $(this).data('id');

        // Hiển thị modal xác nhận
        $('.hoanThanhTatCaModal').modal('show');

        // Xử lý sự kiện khi bấm nút Đồng ý
        $('.hoanThanhTatCaModal .btn-dong-y').click(function () {
            // Gửi yêu cầu hoàn thành tất cả bằng Ajax
            $.get('/updateThanhCongAll', function (response) {
                // Hiển thị thông báo thành công
                Swal.fire({
                    icon: 'success',
                    title: 'Xác nhận đã giao thành công tất cả các đơn',
                    showConfirmButton: false,
                    timer: 2000
                }).then(function () {
                    // Lưu trạng thái đã xác nhận vào sessionStorage
                    sessionStorage.setItem('isConfirmed', true);

                    // Tải lại trang
                    location.reload();
                });
            });

            // Đóng modal
            $('.hoanThanhTatCaModal').modal('hide');
        });

        // Xử lý sự kiện khi bấm nút Không
        $('.hoanThanhTatCaModal .btn-khong').click(function () {
            // Đóng modal
            $('.hoanThanhTatCaModal').modal('hide');
        });
    });
});


<!-- JS TÌM HÓA ĐƠN ĐANG GIAO HÀNG-->
$(document).ready(function () {
    $("#timKiem-DangGiaoHang").click(function () {
        let input = $("#search-input-dangGiao").val(); // Lấy giá trị từ input

        if (input) {
            // Thay đổi URL và chuyển hướng trang
            window.location.href = "/admin/DonHang/DangGiaoHang/timKiem/" + input;
        } else {
            Swal.fire({
                icon: 'warning',
                title: 'Vui lòng nhập vào ô tìm kiếm',
                showConfirmButton: false,
                timer: 2000
            });
        }
    });
});

$(document).ready(function () {
    $("#timKiemNgay-DangGiaoHang").click(function () {
        let selectedDate = $("#search-input-date-DangGiao").val();
        if (selectedDate) {
            let formattedDate = formatDate(selectedDate);
            window.location.href = "/admin/DonHang/DangGiaoHang/Ngay/" + formattedDate;
        } else {
            Swal.fire({
                icon: 'warning',
                title: 'Vui lòng nhập vào ngày',
                showConfirmButton: false,
                timer: 2000
            });
        }
    });

    function formatDate(dateString) {
        let date = new Date(dateString);
        let year = date.getFullYear();
        let month = ("0" + (date.getMonth() + 1)).slice(-2);
        let day = ("0" + date.getDate()).slice(-2);
        return year + "-" + month + "-" + day;
    }
});

$(document).ready(function () {
    $("#btn-tatCaHoaDonDangGiao").click(function () {
        window.location.href = "/admin/DonHang/DangGiaoHang/danhSach";
    });
});
