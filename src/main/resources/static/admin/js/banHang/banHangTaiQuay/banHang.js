function quayLai() {
    window.location.href = "/admin/BanHangTaiQuay";
}

function flexUrlSubmitBanHangTaiQuay(url, method, formName) {
    $("#flexUrlTableForm" + formName).attr("action", url);
    $("#flexUrlTableForm" + formName).attr("method", method);
    document.getElementById("flexUrlTableForm" + formName).submit();
}

$(document).ready(function () {
    $('.xoaHDCT').click(function () {
        let hoaDonCTId = $(this).data('id');
        let modalId = $(this).data('target');

        // Hiển thị modal xác nhận
        $(modalId).modal('show');

        // Xử lý sự kiện khi bấm nút Đồng ý
        $(modalId + ' .btn-dong-y').click(function () {
            // Gửi yêu cầu xóa sản phẩm bằng Ajax
            $.get('/update-XoaSP/' + hoaDonCTId, function (response) {
                // Hiển thị thông báo xóa sản phẩm thành công với SweetAlert2
                Swal.fire({
                    icon: 'success', title: 'Đã xóa sản phẩm thành công', showConfirmButton: false, timer: 2000
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

$(document).ready(function () {
    $('.HuyDonBanTaiQuay').click(function () {
        let hoaDonId = $(this).data('id');

        $('.HuyDonTaiQuay').modal('show');

        $('.HuyDonTaiQuay .btn-dong-y').click(function () {
            $.get('/HuyDon/' + hoaDonId, function (response) {
                Swal.fire({
                    icon: 'success', title: 'Đã hủy hóa đơn thành công', showConfirmButton: false, timer: 2000
                }).then(function () {
                    sessionStorage.setItem('isConfirmed', true);

                    window.location.href = "/admin/BanHangTaiQuay";
                });
            });

            $('.HuyDonTaiQuay').modal('hide');
        });

        $('.HuyDonTaiQuay .btn-khong').click(function () {
            // Đóng modal
            $('.HuyDonTaiQuay').modal('hide');
        });
    });
});

$(document).ready(function () {
    $('#thanhToanHoaDonTaiQuay').click(function () {
        let hoaDonid = $(this).data('id');
        let tenGiamGiaa = $("#tenGiamGia-TaiQuay").text();
        let tienGiamSpan = document.getElementById('discount-taiQuay').innerText;
        let tienGiam = parseInt(tienGiamSpan.replace(/\D+/g, ''));
        let tongTienHoaDon = parseFloat($("#tongTienHoaDon-taiQuay").text().replace(/[^\d]/g, ""));
        let tienKhachDua = parseFloat($("#tienKhachDuaInput").val().replace(/[^\d]/g, ""));
        let hoTenKhachHang = $("#tenKhachHangTaiQuay").text();
        let SDTKhachHang = $("#SDTKhachHangTaiQuay").text();

        if (tienKhachDua >= tongTienHoaDon) {
            if (hoTenKhachHang === "Không có" && SDTKhachHang === "Không có") {
                Swal.fire({
                    icon: "warning",
                    title: "Vui lòng điền thông tin khách hàng",
                    showConfirmButton: false,
                    timer: 2000,
                });
            } else {
                $('.thanhToanTaiQuay').modal('show');

                $('.thanhToanTaiQuay .btn-dong-y').click(function () {
                    // Thực hiện thanh toán hóa đơn trước

                    $.ajax({
                        url: "/banHangtaiQuay/thanhToan/" + hoaDonid,
                        type: "POST",
                        data: {
                            khuyenMai: tenGiamGiaa,
                            tien_giam: tienGiam,
                            tongTienHoaDon: tongTienHoaDon
                        },
                        success: function (response) {
                            if (response.success) {
                                Swal.fire({
                                    icon: "success",
                                    title: "Thanh toán thành công",
                                    showConfirmButton: false,
                                    timer: 2000,
                                }).then(function () {
                                    // Nếu thanh toán thành công, thực hiện in hóa đơn
                                    printHoaDon(hoaDonid);
                                    sessionStorage.setItem("isConfirmed", true);

                                });
                            } else {
                                console.log("Lưu hóa đơn thất bại: " + response.error);
                            }
                        },
                        error: function (xhr, status, error) {
                            console.log("Lỗi khi gửi yêu cầu thanh toán: " + error);
                        },
                    });
                });
            }
        } else {
            Swal.fire({
                icon: "warning",
                title: "Tiền khách đưa không đủ",
                showConfirmButton: false,
                timer: 2000,
            })
        }

    });

    $('.thanhToanTaiQuay .btn-khong').click(function () {
        $('.thanhToanTaiQuay').modal('hide');
    });
});

// Hàm thực hiện in hóa đơn
function printHoaDon(hoaDonId) {
    // Tạo tên file PDF mới bằng UUID
    let pdfFileName = generateUuid() + '.pdf';

    // Gọi API để in hóa đơn và lưu file PDF vào thư mục dự án
    fetch('/in-hoa-don/' + hoaDonId + '?pdfFileName=' + pdfFileName)
        .then(response => response.blob())
        .then(pdfBlob => {
            let pdfUrl = URL.createObjectURL(pdfBlob);
            let newWindow = window.open(pdfUrl, '_blank'); // Mở trang mới chứa file PDF
            if (newWindow) {
                newWindow.document.title = 'Hóa đơn của bạn';
            } else {
                alert('Vui lòng cho phép trình duyệt mở popup để xem và lưu hóa đơn.');
            }

            // Sau khi in hóa đơn, chuyển đến trang "/admin/BanHangTaiQuay"
            window.location.href = "/admin/BanHangTaiQuay";
        })
        .catch(error => console.error('Lỗi khi tạo file PDF:', error));
}

// Hàm mở trang mới với file PDF
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

$(document).ready(function () {
    // Xử lý sự kiện khi trang được tải lần đầu
    $('.quantity-input').each(function () {
        $(this).data('initial-value', $(this).val());
    });

    $('.quantity-input').on('input', function () {
        let saveBtn = $(this).siblings('.save-btn');
        let cancelBtn = $(this).siblings('.cancel-btn');
        let soLuongSPCT = $(this).siblings('.soLuongSPCT');
        soLuongSPCT.show();
        saveBtn.show();
        cancelBtn.show();

        let soLuongCapNhatTable = parseInt($(this).val());
        let initialValue = parseInt($(this).data('initial-value'));
        let soLuongHienCoBanDau = parseInt($(this).data('so-luong-hien-co'));

        // Lấy số lượng hiện có từ phần tử span
        let soLuongHienCoTable = parseInt($('.soLuongHienCoTable').text());

        if (soLuongCapNhatTable > (initialValue + soLuongHienCoBanDau)) {
            Swal.fire({
                icon: 'error',
                title: 'Số lượng nhập vào vượt quá số lượng hiện có của sản phẩm.',
                showConfirmButton: false,
                timer: 2000
            });

            // Đặt lại giá trị trong ô input là số lượng hiện có
            $(this).val(initialValue);

            // Cập nhật giá trị số lượng nhập vào trong log
            soLuongCapNhatTable = soLuongHienCoTable;
        } else if (soLuongCapNhatTable < 1) {
            // Nếu số lượng nhập vào không phải là một số hợp lệ hoặc nhỏ hơn 1, đặt lại giá trị trong ô input là 1
            Swal.fire({
                icon: 'error', title: 'Số lượng nhập vào không được nhỏ hơn 0', showConfirmButton: false, timer: 2000
            });

            $(this).val(initialValue);
        }
    });

    // Xử lý sự kiện khi nhấn nút lưu
    $('.save-btn').on('click', function () {
        let hoaDonCTId = $(this).attr('data-id');
        let quantityInput = $(this).siblings('.quantity-input');
        let quantity = parseInt(quantityInput.val());

        // Gửi Ajax request để lưu thay đổi số lượng
        $.ajax({
            url: '/update-SoLuong/' + hoaDonCTId,
            type: 'POST',
            data: {quantity: quantity},
            success: function (response) {
                // Xử lý thành công
                Swal.fire({
                    icon: 'success', title: 'Cập nhật số lượng thành công', showConfirmButton: false, timer: 2000
                }).then(function () {
                    sessionStorage.setItem('isConfirmed', true);
                    location.reload();
                });
            },
            error: function (error) {
                // Xử lý lỗi
                console.log('Lỗi khi lưu số lượng');
            }
        });
    });

    // Xử lý sự kiện khi nhấn nút hủy
    $('.cancel-btn').on('click', function () {
        let hoaDonCTId = $(this).attr('data-id');
        let quantityInput = $(this).siblings('.quantity-input');
        let saveBtn = $(this).siblings('.save-btn');
        let cancelBtn = $(this);
        let soLuongSPCT = $(this).siblings('.soLuongSPCT');

        // Trả lại giá trị ban đầu của ô input
        let initialValue = quantityInput.data('initial-value');
        quantityInput.val(initialValue);
        soLuongSPCT.hide();
        saveBtn.hide();
        cancelBtn.hide();
    });
});

function openModalSanPham() {
    $('#modalSanPham').modal('show');
}

$(document).ready(function () {
    $('[data-toggle="popover"]').popover();
});

function openAddProduct() {
    $('#banHangTaiQuayModal').modal("show");
}

function openModalAddDetailProduct(id) {
    $('#modalAddDetailProduct' + id).modal("show");
}

function clearDataChoose(mauSacInputName, kichCoInputName, mauSacLabelName, kichCoLabelName) {
    var kichCoIdName = document.getElementsByName("kichCoId");
    $(kichCoIdName).removeAttr("checked");
    var mauSacIdName = document.getElementsByName("mauSacId");
    $(mauSacIdName).removeAttr("checked");
    var kichCoLabel = document.getElementsByName(kichCoLabelName);
    $(kichCoLabel).removeClass("label-active");

    resetModalData();
}

function labelActive(labelId, labelName, inputName, inputId) {
    var lstName = document.getElementsByName(labelName);
    $(lstName).removeClass("label-active");
    $('#' + labelId).addClass("label-active");
    var lstInputName = document.getElementsByName(inputName);
    $(lstInputName).removeAttr("checked");
    $('#' + inputId).attr("checked", "true");
}

function chooseOptionColorLabel(labelId, labelName, inputName, inputId, spName, spId) {
    var lstName = document.getElementsByName(labelName);
    $(lstName).removeClass("label-active");
    $('#' + labelId).addClass("label-active");
    var lstInputName = document.getElementsByName(inputName);
    $(lstInputName).removeAttr("checked");
    $('#' + inputId).attr("checked", "true");
    // choose san Pham id
    var lstSPName = document.getElementsByName(spName);
    $(lstInputName).removeAttr("checked");
    $('#' + spId).attr("checked", "true");
}

function getSoLuongInput(id, name) {
    var valueSoLuongName = document.getElementsByName(id);
    $(valueSoLuongName).val(0);
    var valueSoLuongId = document.getElementById(id).value;
    var soLuong = document.getElementsByName('soLuong');
    $(soLuong).val(valueSoLuongId);
}

window.onload = function () {
    var messageSuccess = '[[${messageSuccess}]]';
    var messageDanger = '[[${messageDanger}]]';
    $('#toastsCustomCss').attr("style", "position: absolute; top: 70px; right: 0;z-index: 1;");
    if (messageSuccess.length !== 0) {
        $("#messageSuccess").toast("show");
    }
    if (messageDanger.length !== 0) {
        $("#messageDanger").toast("show");
    }
}

$(document).ready(function () {
    $('#themMaGiamGia').click(function (e) {
        e.preventDefault();

        // Kiểm tra xem trong hóa đơn có sản phẩm nào chưa
        let productsCount = $('#table-BanHangTaiQuay tbody tr').length;
        if (productsCount === 0) {
            Swal.fire({
                icon: "warning",
                title: "Lỗi",
                text: "Hóa đơn chưa có sản phẩm, không thể thêm mã giảm giá",
            });
            return;
        } else {
            let couponCode = $('select[name="couponCode"]').val();

            $.post(
                "/banHang/themMaGiamGia-taiQuay" + "?couponCode=" + couponCode,
                function (response) {
                    Swal.fire({
                        icon: "success",
                        title: "Thành công",
                        text: "Đã thêm mã giảm giá " + couponCode,
                    });
                    // Hiển thị giá trị giảm giá dưới dạng số + %
                    $("#tenGiamGia-TaiQuay").text(response.tenGiamGia);
                    // $('#discount').text(response.tienGiam + '%');
                    tinhTien(response.tienGiam, response.tienGiamToiDa);
                }
            ).fail(function (error) {
                Swal.fire({
                    icon: "error",
                    title: "Lỗi",
                    text: "Sai mã giảm giá, hãy kiểm tra và thử lại",
                });
            });
        }
    });
});

function tinhTien(tienGiam, tienGiamToiDa) {
    let tongTien = parseFloat($("#tongTienHang-taiQuay").text().replace(/[^\d]/g, ""));
    let discount;
    let discountByPercent;
    let tienTienChuoi;
    let tienThanhToan;
    let tienKhachDua;
    let tienThua;
    let tienThieu;

    let tienGiamm = tongTien * (tienGiam / 100);

    if (tienGiamm > tienGiamToiDa) {
        discount = tienGiamToiDa;
        let discountIndex = Math.round(discount);
        $("#discount-taiQuay").text(
            discountIndex.toLocaleString("vi-VN", {
                style: "currency",
                currency: "VND",
            })
        );
    } else {
        discountByPercent = tongTien * (tienGiam / 100);
        discount = discountByPercent;
        $("#discount-taiQuay").text(tienGiam + "%");
    }

    let total = tongTien - discount;

    if (isNaN(total) || !total) {
        total = 0;
    }

    let totalIndex = Math.round(total);
    $("#tongTienHoaDon-taiQuay").text(
        totalIndex.toLocaleString("vi-VN", {style: "currency", currency: "VND"})
    );

    tienTienChuoi = $("#tongTienHoaDon-taiQuay").text();
    tienThanhToan = parseFloat(tienTienChuoi.replace(/[^\d]/g, '').trim());
    tienKhachDua = parseFloat($("#tienKhachDuaInput").val().replace(/[^\d]/g, ""));
    tienThua = parseFloat($("#tienTralai").text().replace(/[^\d]/g, ""));
    tienThieu = parseFloat($("#tienThieu").text().replace(/[^\d]/g, ""));

    if (!isNaN(tienKhachDua)) {
        tienThua = tienKhachDua - tienThanhToan;
        tienThieu = tienThanhToan - tienKhachDua;
        if (tienThua >= 0) {
            $("#tienTralai").text(
                tienThua.toLocaleString("vi-VN", {style: "currency", currency: "VND"})
            );
            $("#tienThieu").text(
                "0 VNĐ"
            );
        } else if (tienThua < 0) {
            $("#tienThieu").text(
                tienThua.toLocaleString("vi-VN", {style: "currency", currency: "VND"})
            );
            $("#tienTralai").text(
                "0 VNĐ"
            );
        }
    } else {
        tienKhachDua = 0;
        tienThua = tienKhachDua - tienThanhToan;
        tienThieu = tienThanhToan - tienKhachDua;
        if (tienThua >= 0) {
            $("#tienTralai").text(
                tienThua.toLocaleString("vi-VN", {style: "currency", currency: "VND"})
            );
            $("#tienThieu").text(
                "0 VNĐ"
            );
        } else if (tienThua < 0) {
            $("#tienThieu").text(
                tienThua.toLocaleString("vi-VN", {style: "currency", currency: "VND"})
            );
            $("#tienTralai").text(
                "0 VNĐ"
            );
        }
    }
}

$(document).ready(function () {
    $('#themThongTinKhachHang').click(function (e) {
        let idHoaDon = $("#idHoaDonBanHangTaiQuay").val();
        let idKhachHang = $("#KhachHang-select").val();
        let hoTenKhachHang = $("#hoTenKhachHang").val();
        let SDTKhachHang = $("#SDTKhachHang").val();
        if (!hoTenKhachHang && !SDTKhachHang) {
            hoTenKhachHang = "";
            SDTKhachHang = "";
        }
        if (!idKhachHang) {
            idKhachHang = 0;
        }

        if (idKhachHang || hoTenKhachHang && SDTKhachHang) {
            $.post(
                "/ThemThongTinKhachHang?IdHoaDon=" + idHoaDon + "&IDKhachHang=" + idKhachHang + "&TenKhachHang=" + hoTenKhachHang + "&SDTKhachHang=" + SDTKhachHang,
                function (response) {
                    // Xử lý thành công ở đây
                    Swal.fire({
                        icon: "success",
                        title: "Thành công",
                        text: "Thêm thông tin khách hàng thành công",
                    }).then(function () {
                        sessionStorage.setItem('isConfirmed', true);
                        location.reload();
                    });
                }
            ).fail(function (xhr) {
                // Xử lý lỗi ở đây
                if (xhr.status === 400) {
                    const errorResponse = JSON.parse(xhr.responseText);
                    const errorMessage = errorResponse.error;
                    Swal.fire({
                        icon: "error",
                        title: "Lỗi",
                        text: errorMessage,
                    });
                    return;
                } else {
                    // Xử lý các lỗi khác
                    Swal.fire({
                        icon: "error",
                        title: "Lỗi",
                        text: "Có lỗi xảy ra khi thực hiện yêu cầu",
                    });
                }
            });

        } else {
            Swal.fire({
                icon: "warning",
                title: "Lỗi",
                text: "Vui lòng điền thông tin khách hàng"
            });
        }
    });
});

document.addEventListener("DOMContentLoaded", function () {
    let tienTienChuoi = $("#tongTienHoaDon-taiQuay").text();
    let tienThanhToan = parseFloat(tienTienChuoi.replace(/[^\d]/g, '').trim());
    let tienKhachDua = parseFloat($("#tienKhachDuaInput").val().replace(/[^\d]/g, ""));
    let tienThua = parseFloat($("#tienTralai").text().replace(/[^\d]/g, ""));
    let tienThieu = parseFloat($("#tienThieu").text().replace(/[^\d]/g, ""));

    if (!isNaN(tienKhachDua)) {
        tienThua = tienKhachDua - tienThanhToan;
        tienThieu = tienThanhToan - tienKhachDua;
        if (tienThua >= 0) {
            $("#tienTralai").text(
                tienThua.toLocaleString("vi-VN", {style: "currency", currency: "VND"})
            );
            $("#tienThieu").text(
                "0 VNĐ"
            );
        } else if (tienThua < 0) {
            $("#tienThieu").text(
                tienThua.toLocaleString("vi-VN", {style: "currency", currency: "VND"})
            );
            $("#tienTralai").text(
                "0 VNĐ"
            );
        }
    } else {
        tienKhachDua = 0;
        tienThua = tienKhachDua - tienThanhToan;
        tienThieu = tienThanhToan - tienKhachDua;
        if (tienThua >= 0) {
            $("#tienTralai").text(
                tienThua.toLocaleString("vi-VN", {style: "currency", currency: "VND"})
            );
            $("#tienThieu").text(
                "0 VNĐ"
            );
        } else if (tienThua < 0) {
            $("#tienThieu").text(
                tienThua.toLocaleString("vi-VN", {style: "currency", currency: "VND"})
            );
            $("#tienTralai").text(
                "0 VNĐ"
            );
        }
    }
});