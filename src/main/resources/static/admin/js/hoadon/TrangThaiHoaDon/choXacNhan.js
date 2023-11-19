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

<!-- JS XÁC CHỜ XÁC NHẬN -> CHỜ LẤY HÀNG-->
$(document).ready(function () {
    $('.XacNhanDon').click(function () {
        let hoaDonId = $(this).data('id');
        let modalId = $(this).data('target');

        // Hiển thị modal xác nhận
        $(modalId).modal('show');

        // Xử lý sự kiện khi bấm nút Đồng ý
        $(modalId + ' .btn-dong-y').click(function () {
            // Gửi yêu cầu xác nhận đơn hàng bằng Ajax
            $.get('/updateXacNhan/' + hoaDonId, function (response) {
                // Hiển thị thông báo xác nhận thành công với SweetAlert2
                Swal.fire({
                    icon: 'success',
                    title: 'Đã xác nhận thành công',
                    showConfirmButton: false,
                    timer: 1500
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


<!-- JS XÁC NHẬN TẤT CẢ-->
$(document).ready(function () {
    $('#XacNhanTatCa').click(function () {
        // Kiểm tra số lượng hàng trong bảng
        let rowCount = $('#user-table-ChoXacNhan tbody tr').length;

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

        // Các lệnh xử lý khác khi có dữ liệu trong bảng
        let hoaDonId = $(this).data('id');

        // Hiển thị modal xác nhận
        $('.xacNhanTatCaModal').modal('show');

        // Xử lý sự kiện khi bấm nút Đồng ý
        $('.xacNhanTatCaModal .btn-dong-y').click(function () {
            // Gửi yêu cầu xác nhận đơn hàng bằng Ajax
            $.get('/updateXacNhanAll', function (response) {
                // Lưu trạng thái đã xác nhận vào sessionStorage
                Swal.fire({
                    icon: 'success',
                    title: 'Đã xác nhận tất cả thành công',
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
            $('.xacNhanTatCaModal').modal('hide');
        });

        // Xử lý sự kiện khi bấm nút Không
        $('.xacNhanTatCaModal .btn-khong').click(function () {
            // Đóng modal
            $('.xacNhanTatCaModal').modal('hide');
        });
    });
});

<!-- JS CHỜ CHỜ XÁC NHẬN -> ĐÃ HỦY -->
$(document).ready(function () {
    $('.HuyDon').click(function () {
        let hoaDonId = $(this).data('id');
        let modalId = $(this).data('target');

        // Hiển thị modal xác nhận
        $(modalId).modal('show');

        // Xử lý sự kiện khi bấm nút Đồng ý
        $(modalId + ' .btn-dong-y').click(function () {
            // Gửi yêu cầu hủy đơn hàng bằng Ajax
            $.get('/updateHuyDon/' + hoaDonId, function (response) {
                // Hiển thị thông báo hủy thành công với SweetAlert2
                Swal.fire({
                    icon: 'error',
                    title: 'Đã hủy thành công',
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


<!-- JS HỦY TẤT CẢ-->
$(document).ready(function () {
    $('#HuyTatCa').click(function () {
        // Kiểm tra số lượng hàng trong bảng
        let rowCount = $('#user-table-ChoXacNhan tbody tr').length;

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
        $('.huyTatCaModal').modal('show');

        // Xử lý sự kiện khi bấm nút Đồng ý
        $('.huyTatCaModal .btn-dong-y').click(function () {
            // Gửi yêu cầu hủy tất cả bằng Ajax
            $.get('/updateHuyAll', function (response) {
                // Hiển thị thông báo thành công
                Swal.fire({
                    icon: 'success',
                    title: 'Đã hủy tất cả thành công',
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
            $('.huyTatCaModal').modal('hide');
        });

        // Xử lý sự kiện khi bấm nút Không
        $('.huyTatCaModal .btn-khong').click(function () {
            // Đóng modal
            $('.huyTatCaModal').modal('hide');
        });
    });
});

// TÌM KIẾM HOÁ ĐƠN THEO NGÀY
$(document).ready(function () {
    $("#timKiemNgay-ChoXacNhanDonHang").click(function () {
        let selectedDate = $("#search-input-date-choXacNhan").val();
        if (selectedDate) {
            let formattedDate = formatDate(selectedDate);
            window.location.href = "/admin/DonHang/ChoXacNhanDonHang/Ngay/" + formattedDate;
        }else {
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
    $("#btn-tatCaHoaDonChoGiaoHang").click(function () {
        window.location.href = "/admin/DonHang/ChoGiaoHang/danhSach";
    });
});

<!-- JS TÌM HÓA ĐƠN CHỜ XÁC NHẬN-->
$(document).ready(function () {
    $("#timKiem-choXacNhanDonHang").click(function () {
        let input = $("#search-input-choXacNhan").val(); // Lấy giá trị từ input

        if (input) {
            // Thay đổi URL và chuyển hướng trang
            window.location.href = "/admin/DonHang/ChoXacNhanDonHang/timKiem/" + input;
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
    $("#btn-tatCaHoaDonChoXacNhan").click(function () {
        window.location.href = "/admin/DonHang/ChoXacNhanDonHang/danhSach";
    });
});
