function redirectToDaThanhToan() {
    window.location.href = "/admin/DaThanhToan/danhSach";
}

function redirectToDahuyTaiQuay() {
    window.location.href = "/admin/DaHuyTaiQuay/danhSach";
}

$(document).ready(function () {
    $(".btnTimKiem").click(function () {
        const input = $("#search-input-daHuy").val();
        if (input) {
            // Thay đổi URL và chuyển hướng trang
            window.location.href = "/admin/DaHuyTaiQuay/TimKiem/" + input;
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
    $("#search-button-ngayTao-daHuy").click(function () {
        const selectedDate = $("#search-input-date-daHuyTaiQuay").val();
        if (selectedDate) {
            const formattedDate = formatDate(selectedDate);
            window.location.href = "/admin/DaHuyTaiQuay/Ngay/" + formattedDate;
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
        const date = new Date(dateString);
        const year = date.getFullYear();
        const month = ("0" + (date.getMonth() + 1)).slice(-2);
        const day = ("0" + date.getDate()).slice(-2);
        return year + "-" + month + "-" + day;
    }
});

$(document).ready(function () {
    $("#btn-tatCaHoaDonDaHuy").click(function () {
        window.location.href = "/admin/DaHuyTaiQuay/danhSach";
    });
});
