<!-- Xử lý phân trang -->
function changePage(event, pageNumber) {
    event.preventDefault();
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/admin/NguoiDung?page=" + pageNumber);
    xhr.onload = function () {
        if (xhr.status === 800) {
            // Cập nhật nội dung trang mới
            var newPageContent = xhr.responseText;
            document.getElementById("table-container").innerHTML = newPageContent;
        }
    };
    xhr.send();
}

<!-- Lọc Trạng Thái -->
$(document).ready(function () {
    $('#status').change(function () {
        let selectedValue = $(this).val();
        let trangThaiValue;

        if (selectedValue === 'active') {
            trangThaiValue = 0;
        } else if (selectedValue === 'inactive') {
            trangThaiValue = 1;
        } else {
            trangThaiValue = null; // Không có giá trị trạng thái được chọn
        }

        if (trangThaiValue !== null) {
            window.location.href = '/admin/NhanVien/TrangThai/' + trangThaiValue;
        }
    });
});


$(document).ready(function () {
    $("#timKiemNhanVien-TatCa").click(function () {
        let input = $("#search-input-allHD-name").val(); // Lấy giá trị từ input

        if(input){
            // Thay đổi URL và chuyển hướng trang
            window.location.href = "/admin/NhanVien/timKiem/" + input;
        }else {
            Swal.fire({
                icon: 'warning', title: 'Vui lòng nhập vào ô tìm kiếm', showConfirmButton: false, timer: 2000
            });
        }
    });
});

<!-- Tìm kiếm ngày tạo -->
$(document).ready(function () {
    $("#timKiemNgay-NhanVien").click(function () {
        var selectedDate = $("#search-input-allHD-date").val();
        if (selectedDate) {
            var formattedDate = formatDate(selectedDate);
            window.location.href = "/admin/NhanVien/Ngay/" + formattedDate;
        }
    });

    function formatDate(dateString) {
        var date = new Date(dateString);
        var year = date.getFullYear();
        var month = ("0" + (date.getMonth() + 1)).slice(-2);
        var day = ("0" + date.getDate()).slice(-2);
        return year + "-" + month + "-" + day;
    }
});

<!-- Nút bật/tắt trạng thái -->
$(document).ready(function () {
    $('.custom-control-input').click(function () {
        var userId = $(this).closest('tr').data('user-id');
        var statusCheckbox = $(this);
        var status = statusCheckbox.is(':checked') ? 0 : 1;

        $.post("/updateStatus", {userId: userId, status: status}, function (data) {
            Swal.fire({

                icon: 'success', title: 'Cập nhật thành công', showConfirmButton: false, timer: 1500
            });
        }).fail(function (error) {
            Swal.fire({
                icon: 'error', title: 'Có lỗi xảy ra', text: error.responseText
            });
        });
    });
});

$(document).ready(function () {
    $('.xoaNhanVien').click(function () {
        let idNhanVien = $(this).data('id');
        let modalId = $(this).data('target');

        // Hiển thị modal xác nhận
        $(modalId).modal('show');

        // Xử lý sự kiện khi bấm nút Đồng ý
        $(modalId + ' .btn-dong-y').click(function () {
            // Gửi yêu cầu xóa sản phẩm bằng Ajax
            $.get('/xoaNhanVien/' + idNhanVien, function (response) {
                // Hiển thị thông báo xóa sản phẩm thành công với SweetAlert2
                Swal.fire({
                    icon: 'success', title: 'Đã xóa nhân viên thành công', showConfirmButton: false, timer: 2000
                }).then(function () {
                    // Lưu trạng thái đã xác nhận vào sessionStorage
                    sessionStorage.setItem('isConfirmed', true);

                    // Tải lại trang
                    window.location.href = "/admin/NhanVien/danhSach";
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