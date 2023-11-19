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


<!-- JS TÌM HÓA ĐƠN ĐÃ HỦY-->
$(document).ready(function () {
    $("#timKiem-DaHuyHang").click(function () {
        let input = $("#search-input-daHuy").val(); // Lấy giá trị từ input

        if (input) {
            // Thay đổi URL và chuyển hướng trang
            window.location.href = "/admin/DonHang/DaHuyHang/timKiem/" + input;
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
    $("#timKiemNgay-DaHuyHang").click(function () {
        let selectedDate = $("#search-input-date-DaHuy").val();
        if (selectedDate) {
            let formattedDate = formatDate(selectedDate);
            window.location.href = "/admin/DonHang/DaHuyHang/Ngay/" + formattedDate;
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
    $("#btn-tatCaHoaDonDaHuy").click(function () {
        window.location.href = "/admin/DonHang/DaHuyHang/danhSach";
    });
});
