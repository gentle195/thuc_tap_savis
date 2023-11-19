function flexUrlSubmitchinhSuaHoaDon(url, method, formName) {
    $("#flexUrlTableForm" + formName).attr("action", url);
    $("#flexUrlTableForm" + formName).attr("method", method);
    document.getElementById("flexUrlTableForm" + formName).submit();
}

function openModalAddDetailProduct(id) {
    $("#modalAddDetailProduct" + id).modal("show");
}

$(document).ready(function () {
    $('[data-toggle="popover"]').popover();
});

function clearDataChoose(mauSacInputName, kichCoInputName, mauSacLabelName, kichCoLabelName) {
    var kichCoIdName = document.getElementsByName("kichCoId");
    $(kichCoIdName).removeAttr("checked");
    var mauSacIdName = document.getElementsByName("mauSacId");
    $(mauSacIdName).removeAttr("checked");
    var soLuong = document.getElementsByName("soLuong");
    soLuong.values = 0;
    var kichCoLabel = document.getElementsByName(kichCoLabelName);
    $(kichCoLabel).removeClass("label-active");
    var mauSacLabel = document.getElementsByName(mauSacLabelName);
    $(mauSacLabel).removeClass("msActive");
}

function labelActive(labelId, labelName, inputName, inputId) {
    var lstName = document.getElementsByName(labelName);
    $(lstName).removeClass("label-active");
    $("#" + labelId).addClass("label-active");
    var lstInputName = document.getElementsByName("kichCoId");
    $(lstInputName).removeAttr("checked");
    $("#" + inputId).attr("checked", "true");
}

function chooseOptionColorLabel(labelId, labelName, inputName, inputId, spName, spId, imgboxName, imgboxId) {
    var lstName = document.getElementsByName(labelName);
    $(lstName).removeClass("msActive");
    $("#" + labelId).toggleClass("msActive");
    var mauSacIdName = document.getElementsByName("mauSacId");
    $(mauSacIdName).removeAttr("checked");
    $("#" + inputId).attr("checked", "true");
    // choose san Pham id
    var sanPhamIdSPTQName = document.getElementsByName("sanPhamIdSPTQ");
    $(sanPhamIdSPTQName).removeAttr("checked");
    $("#" + spId).attr("checked", "true");

    var sanPhamIdSPTQName = document.getElementsByName(imgboxName);
    $(sanPhamIdSPTQName).removeClass("active");
    $("#" + imgboxId).addClass("active");
}

function getSoLuongInput(id, name) {
    var valueSoLuongName = document.getElementsByName(id);
    $(valueSoLuongName).val(0);
    var valueSoLuongId = document.getElementById(id).value;
    var soLuong = document.getElementsByName("soLuong");
    $(soLuong).val(valueSoLuongId);
}

window.onload = function () {
    const messageSuccess = "[[${messageSuccess}]]";
    const messageDanger = "[[${messageDanger}]]";
    $("#toastsCustomCss").attr("style", "position: absolute; top: 70px; right: 0;z-index: 1;");
    if (messageSuccess.length !== 0) {
        $("#messageSuccess").toast("show");
    }
    if (messageDanger.length !== 0) {
        $("#messageDanger").toast("show");
    }
};

$(document).ready(function () {
    let tenKichCo = "";
    let mauSacId = "";

    $(".img-thumbnail").click(function () {
        tenKichCo = $(this).text().trim();
        $(".img-thumbnail").removeClass("active");
        $(this).addClass("active");

        const sanPhamId = $(this)
            .closest(".modal-content")
            .find(".modal-title")
            .data("id");


        if (mauSacId != "") {
            getSoLuongSanPhamChiTiett(tenKichCo, mauSacId, sanPhamId);
        }

        let soLuongInput = $(".tyuiop").val();
        const soLuongHienCoCus = $(".soLuongHienCoChinhSuaHoaDon").val();
        if (soLuongInput > soLuongHienCoCus) {
            soLuongInput = soLuongHienCoCus;
        }
    });

    $(".aduluon").click(function () {
        mauSacId = $(this).find("input[type=radio]").val();
        const sanPhamId = $(this)
            .closest(".modal-content")
            .find(".modal-title")
            .data("id");

        if (tenKichCo != "") {
            getSoLuongSanPhamChiTiett(tenKichCo, mauSacId, sanPhamId);
        }

        let soLuongInput = $(".tyuiop").val();
        const soLuongHienCoCus = $(".soLuongHienCoChinhSuaHoaDon").val();
        if (soLuongInput > soLuongHienCoCus) {
            soLuongInput = soLuongHienCoCus;
        }
    });

    $(".tyuiop").on("input", function () {
        let soLuongInput = parseInt($(this).val());
        const sanPhamId = $(this).closest(".modal-content").find(".modal-title").data("id");
        let soLuongHienCo = parseInt($("#soLuongHienCo" + sanPhamId).text());

        if (soLuongInput > soLuongHienCo) {
            Swal.fire({
                icon: "error",
                title: "Số lượng nhập vào vượt quá số lượng hiện có của sản phẩm.",
                showConfirmButton: false,
                timer: 2000,
            });
            $(this).val(soLuongHienCo);
            soLuongInput = soLuongHienCo;
        } else if (soLuongInput < 1) {
            Swal.fire({
                icon: "error", title: "Số lượng nhập vào không được nhỏ hơn 0", showConfirmButton: false, timer: 2000,
            });
            $(this).val(1);
        }
    });
});

function getSoLuongSanPhamChiTiett(tenKichCo, mauSacId, sanPhamId) {
    $.ajax({
        type: "GET",
        url: "/banHang/laySoLuongSanPhamChiTiet",
        data: {
            sanPhamId: sanPhamId,
            tenKichCo: tenKichCo,
            mauSacId: mauSacId,
        }, success: function (response) {
            let soLuongSanPhamChiTiet = response.soLuongSanPhamChiTiet;

            if (soLuongSanPhamChiTiet == null || isNaN(soLuongSanPhamChiTiet)) {
                soLuongSanPhamChiTiet = 0;
            }

            $("#soLuongHienCo" + sanPhamId).text(soLuongSanPhamChiTiet);
        }, error: function () {
            alert("Đã xảy ra lỗi khi gửi yêu cầu đến server.");
        },
    });
}


$(document).ready(function () {
    $('.quayLaiCTHD').on('click', function () {
        const hoaDonCTId = $(this).attr('data-id');
        window.location.href = "/ChiTietHoaDon/ChoXacNhan/hoa-don-id=" + hoaDonCTId;
    });
});

function handleThemSanPham() {
    // let inputValue = parseInt($(".tyuiop").val());
    const selectedMauSac = $(".aduluon input[type=radio]:checked");
    const selectedKichCo = $(".img-thumbnail input[type=radio]:checked");

    // Kiểm tra màu sắc
    if (!selectedMauSac.length) {
        Swal.fire({
            icon: 'error', title: 'Lỗi', text: 'Vui lòng chọn màu sắc', showConfirmButton: false, timer: 2000
        });
        return;
    }

    // Kiểm tra kích cỡ
    if (!selectedKichCo.length) {
        Swal.fire({
            icon: 'error',
            title: 'Lỗi',
            text: 'Vui lòng chọn kích cỡ',
            showConfirmButton: false,
            timer: 2000
        });
        return;
    }

    // Tiếp tục thực hiện flexUrlSubmitchinhSuaHoaDon() nếu số lượng, màu sắc và kích cỡ hợp lệ
    flexUrlSubmitchinhSuaHoaDon('/admin/hoaDon/luuThongTinChinhSua', 'post', 'BanHangTaiQuay');
}

$(".themSanPhamCTHD").click(function () {
    let inputValue = parseInt($(".tyuiop").val());
    // Kiểm tra số lượng
    if (isNaN(inputValue) || inputValue === 0) {
        Swal.fire({
            icon: 'error', title: 'Lỗi', text: 'Vui lòng nhập số lượng', showConfirmButton: false, timer: 2000
        });
        return;
    }
});

function quayLaiTrangCTHD() {
    let idHoaDon = $("#idHoaDon").val();
    window.location.href = "/ChiTietHoaDon/ChoXacNhan/hoa-don-id=" + idHoaDon;
}

$(document).ready(function () {
    $('.chonSanPhamChiTiet').click(function () {
        let modalId = $(this).data('target');
        $(modalId).modal('show');

        $(modalId + ' .btn-dong-y').click(function () {
            let hoaDonID = $("#idHoaDon").val();
            let sanPhamID = $(modalId + ' #sanPhamIDD').val();
            let soLuongSanPham = $(modalId + ' #soLuongInputId' + sanPhamID).val();
            let kichCoId = $(modalId + ' .product__details__option__size input:checked').val();
            let mauSacId = $(modalId + ' .product__details__option__color input:checked').val();
            let input = $(modalId + ' .tyuiop').val()

            if (input !== "") {
                $.ajax({
                    url: "/HoaDon/themSanPhamVaoHoaDonChoXacNhan",
                    type: "POST",
                    data: {
                        kichThuocId: kichCoId,
                        mauSacId: mauSacId,
                        sanPhamId: sanPhamID,
                        hoaDonId: hoaDonID,
                        soLuongSanPham: soLuongSanPham
                    },
                    success: function (response) {
                        if (response.success) {
                            Swal.fire({
                                icon: "success",
                                title: "Thêm sản phẩm thành công",
                                showConfirmButton: false,
                                timer: 2000,
                            }).then(function () {
                                // Nếu thanh toán thành công, thực hiện in hóa đơn
                                sessionStorage.setItem("isConfirmed", true);
                                window.location.href = "/ChiTietHoaDon/ChoXacNhan/hoa-don-id=" + hoaDonID;
                            });
                        } else {
                            console.log("Lưu hóa đơn thất bại: " + response.error);
                        }
                    },
                    error: function (xhr, status, error) {
                        console.log("Lỗi khi gửi yêu cầu thanh toán: " + error);
                    },
                });
            } else {
                Swal.fire({
                    icon: "error",
                    title: "Nhập vào số lượng",
                    showConfirmButton: false,
                    timer: 2000,
                });
            }
        });
        $(modalId + ' .btn-dong').click(function () {
            $(modalId).modal('hide');
        });
    });
});