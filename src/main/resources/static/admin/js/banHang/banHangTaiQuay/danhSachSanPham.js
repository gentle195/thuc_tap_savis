function flexUrlSubmitBanHangTaiQuay(url, method, formName) {
    $("#flexUrlTableForm" + formName).attr("action", url);
    $("#flexUrlTableForm" + formName).attr("method", method);
    document.getElementById("flexUrlTableForm" + formName).submit();
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
    const valueSoLuongName = document.getElementsByName(id);
    $(valueSoLuongName).val(0);
    const valueSoLuongId = document.getElementById(id).value;
    const soLuong = document.getElementsByName("soLuong");
    $(soLuong).val(valueSoLuongId);
}

window.onload = function () {
    var messageSuccess = "[[${messageSuccess}]]";
    var messageDanger = "[[${messageDanger}]]";
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
    let soLuongInput = $(".tyuiop").val();
    const soLuongHienCoCus = $(".soLuongHienCoChinhSuaHoaDon").val();

    $(".img-thumbnail").click(function () {
        $(".tyuiop").val("")
        tenKichCo = $(this).text().trim();
        $(".img-thumbnail").removeClass("active");
        $(this).addClass("active");

        let sanPhamId = $(this)
            .closest(".modal-content")
            .find(".modal-title")
            .data("id");


        if (mauSacId != "") {
            getSoLuongSanPhamChiTiettt(tenKichCo, mauSacId, sanPhamId);
        }

        if (soLuongInput > soLuongHienCoCus) {
            soLuongInput = soLuongHienCoCus;
        }
    });

    $(".aduluon").click(function () {
        mauSacId = $(this).find("input[type=radio]").val();
        $(".tyuiop").val("")
        let sanPhamId = $(this)
            .closest(".modal-content")
            .find(".modal-title")
            .data("id");

        if (tenKichCo != "") {
            getSoLuongSanPhamChiTiettt(tenKichCo, mauSacId, sanPhamId);
        }

        if (soLuongInput > soLuongHienCoCus) {
            soLuongInput = soLuongHienCoCus;
        }
    });

    $(".tyuiop").on("input", function () {
        soLuongInput = parseInt($(this).val());
        let sanPhamId = $(this)
            .closest(".modal-content")
            .find(".modal-title")
            .data("id");
        let soLuongHienCoSP = parseInt($("#soLuongHienCo" + sanPhamId).text());

        if (soLuongInput > soLuongHienCoSP) {
            Swal.fire({
                icon: "error",
                title: "Số lượng nhập vào vượt quá số lượng hiện có của sản phẩm.",
                showConfirmButton: false,
                timer: 2000,
            });
            $(this).val(soLuongHienCoSP);
            soLuongInput = soLuongHienCoSP;
        } else if (soLuongInput < 1) {
            Swal.fire({
                icon: "error", title: "Số lượng nhập vào không được nhỏ hơn 0", showConfirmButton: false, timer: 2000,
            });
            $(this).val(1);
        }
    });
});

function getSoLuongSanPhamChiTiettt(tenKichCo, mauSacId, sanPhamId) {
    $.ajax({
        type: "GET", url: "/banHang/laySoLuongSanPhamChiTiet", data: {
            sanPhamId: sanPhamId, tenKichCo: tenKichCo, mauSacId: mauSacId,
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

function quayLaiTrangBanHang() {
    let idHoaDon = $("#idHoaDon").val();
    window.location.href = "/admin/banHang/" + idHoaDon;
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
                    url: "/banHang/themSanPhamVaoHoaDon",
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
                                window.location.href = "/admin/banHang/" + hoaDonID;
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

