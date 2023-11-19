<!-- JS CHỜ GIAO HÀNG -> ĐANG GIAO HÀNG -->
$(document).ready(function () {
    $('.GiaoHang').click(function () {
        const hoaDonId = $(this).data('id');

        // Hiển thị modal xác nhận
        $('.giaoHangModal').modal('show');

        // Xử lý sự kiện khi bấm nút Đồng ý
        $('.giaoHangModal .btn-dong-y').click(function () {
            // Gửi yêu cầu xác nhận đơn hàng bằng Ajax
            $.get('/updateGiaoHang/' + hoaDonId, function (response) {
                // Lưu trạng thái đã xác nhận vào sessionStorage
                Swal.fire({
                    icon: 'success',
                    title: 'Đã chuyển hàng cho đơn vị vận chuyển thành công',
                    showConfirmButton: false,
                    timer: 2000
                }).then(function () {
                    // Lưu trạng thái đã xác nhận vào sessionStorage
                    sessionStorage.setItem('isConfirmed', true);

                    window.location.href = "/admin/DonHang/ChoGiaoHang/danhSach";
                });
            });

            // Đóng modal
            $('.giaoHangModal').modal('hide');
        });

        // Xử lý sự kiện khi bấm nút Không
        $('.giaoHangModal .btn-khong').click(function () {
            // Đóng modal
            $('.giaoHangModal').modal('hide');
        });
    });
});

function quayLai() {
    window.location.href = "/admin/DonHang/ChoGiaoHang/danhSach";
}

$(document).ready(function () {
    $(".inHoaDonChiTiet").click(function () {
        $('.inHoaDonModal').modal('show');
        $('.inHoaDonModal .btn-dong-y').click(function () {
            let hoaDonID = $("#idChiTietHoaDon").val();
            printHoaDon(hoaDonID);
            $('.inHoaDonModal').modal('hide');
        });

        $('.inHoaDonModal .btn-khong').click(function () {
            $('.inHoaDonModal').modal('hide');
        });
    });
});

function printHoaDon(hoaDonId) {
    // Tạo tên file PDF mới bằng UUID
    let pdfFileName = generateUuid() + '.pdf';

    // Gọi API để in hóa đơn và lưu file PDF vào thư mục dự án
    fetch('/in-hoa-don-don-dat-hang/' + hoaDonId + '?pdfFileName=' + pdfFileName)
        .then(response => response.blob())
        .then(pdfBlob => {
            let pdfUrl = URL.createObjectURL(pdfBlob);
            let newWindow = window.open(pdfUrl, '_blank'); // Mở trang mới chứa file PDF
            if (newWindow) {
                newWindow.document.title = 'Hóa đơn của bạn';
            } else {
                alert('Vui lòng cho phép trình duyệt mở popup để xem và lưu hóa đơn.');
            }
        })
        .catch(error => console.error('Lỗi khi tạo file PDF:', error));
}

function generateUuid() {
    let uuid = '', i, random;
    for (i = 0; i < 32; i++) {
        random = Math.random() * 36 | 0; // Thay đổi thành toString(36)
        if (i === 8 || i === 12 || i === 16 || i === 20) {
            uuid += '-';
        }
        uuid += (i === 12 ? 4 : (i === 16 ? (random & 3 | 8) : random))
            .toString(36);
    }
    return uuid;
}