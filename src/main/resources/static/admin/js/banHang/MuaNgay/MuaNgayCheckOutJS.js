//Api Giao Hàng Nhanh
$(document).ready(function () {
    $.ajax({
        url: "https://online-gateway.ghn.vn/shiip/public-api/master-data/province",
        type: "POST",
        dataType: "json",
        headers: {
            "Content-Type": "application/json",
            Token: "ab5e296c-25a3-11ee-b394-8ac29577e80e",
        },
        success: function (response) {
            const provinces = response.data;
            const provinceSelect = $("#province");

            provinces.forEach(function (province) {
                provinceSelect.append(
                    "<option value='" +
                    province.ProvinceID +
                    "'>" +
                    province.ProvinceName +
                    "</option>"
                );
            });
        },
        error: function (xhr, status, error) {
            console.log("API Request Failed:", error);
        },
    });

    $("#province").change(function () {
        const selectedProvinceId = $(this).val();

        $("#district")
            .prop("disabled", true)
            .empty()
            .append("<option value=''>Chọn Quận/Huyện</option>");
        $("#ward")
            .prop("disabled", true)
            .empty()
            .append("<option value=''>Chọn Phường/Xã</option>");

        if (selectedProvinceId) {
            $.ajax({
                url: "https://online-gateway.ghn.vn/shiip/public-api/master-data/district",
                type: "GET",
                dataType: "json",
                headers: {
                    "Content-Type": "application/json",
                    Token: "ab5e296c-25a3-11ee-b394-8ac29577e80e",
                },
                data: {
                    province_id: selectedProvinceId,
                },
                success: function (response) {
                    const districts = response.data;
                    const districtSelect = $("#district");
                    districts.forEach(function (district) {
                        districtSelect.append(
                            "<option value='" +
                            district.DistrictID +
                            "'>" +
                            district.DistrictName +
                            "</option>"
                        );
                    });

                    districtSelect.prop("disabled", false);
                },
                error: function (xhr, status, error) {
                    console.log("API Request Failed:", error);
                },
            });
        }
    });

    $("#district").change(function () {
        const selectedDistrictId = $(this).val();

        $("#ward")
            .prop("disabled", true)
            .empty()
            .append("<option value=''>Chọn Phường/Xã</option>");

        if (selectedDistrictId) {
            // Populate wards based on selected district
            $.ajax({
                url: "https://online-gateway.ghn.vn/shiip/public-api/master-data/ward",
                type: "GET",
                dataType: "json",
                headers: {
                    "Content-Type": "application/json",
                    Token: "ab5e296c-25a3-11ee-b394-8ac29577e80e",
                },
                data: {
                    district_id: selectedDistrictId,
                },
                success: function (response) {
                    const wards = response.data;
                    const wardSelect = $("#ward");
                    wards.forEach(function (ward) {
                        wardSelect.append(
                            "<option value='" +
                            ward.WardCode +
                            "'>" +
                            ward.WardName +
                            "</option>"
                        );
                    });

                    // Enable ward selection
                    wardSelect.prop("disabled", false);
                },
                error: function (xhr, status, error) {
                    console.log("API Request Failed:", error);
                },
            });
        }
    });

    $("#province, #district, #ward").change(function () {
        calculateShippingFee();
    });

    function calculateShippingFee() {
        const toDistrictId = parseInt($("#district").val());
        const toWardCode = $("#ward").val();

        if (toDistrictId && toWardCode) {
            $.ajax({
                url: "https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/available-services",
                type: "POST",
                dataType: "json",
                headers: {
                    "Content-Type": "application/json",
                    Token: "ab5e296c-25a3-11ee-b394-8ac29577e80e",
                },
                data: JSON.stringify({
                    shop_id: 4365806,
                    from_district: 1454,
                    to_district: toDistrictId,
                }),
                success: function (response) {
                    const availableServices = response.data;
                    if (availableServices.length > 0) {
                        const serviceId = availableServices[0].service_id;

                        $.ajax({
                            url: "https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee",
                            type: "POST",
                            dataType: "json",
                            headers: {
                                "Content-Type": "application/json",
                                Token: "ab5e296c-25a3-11ee-b394-8ac29577e80e",
                                ShopId: 4365806,
                            },
                            data: JSON.stringify({
                                from_district_id: 1454,
                                from_ward_code: "21211",
                                service_id: serviceId,
                                to_district_id: toDistrictId,
                                to_ward_code: toWardCode,
                                weight: 200,
                            }),
                            success: function (response) {
                                const shippingFee = response.data.total;

                                // Format the shipping fee with commas and "VNĐ" before updating the label
                                const formattedShippingFee = shippingFee.toLocaleString("vi-VN", {
                                    style: "currency",
                                    currency: "VND",
                                });

                                // Update shipping fee in the label
                                $("#shippingFee").text(formattedShippingFee);
                                $("#tienShip").text(formattedShippingFee);
                                calculateTotal();
                            },
                            error: function (xhr, status, error) {
                                console.log("API Request Failed:", error);
                            },
                        });
                    } else {
                        console.log("No available services.");
                    }
                },
                error: function (xhr, status, error) {
                    console.log("API Request Failed:", error);
                },
            });
        }
    }

    function calculateTotal() {
        const subtotal = parseFloat($("#subtotal").text().replace(/[^\d]/g, ""));
        const discountText = $("#discount").text();
        let discountType = "percentage";
        let discountValue = 0;

        if (discountText.indexOf("%") !== -1) {
            // Giảm giá dưới dạng phần trăm
            discountType = "percentage";
            discountValue = parseFloat(discountText.replace("%", ""));
        } else {
            // Giảm giá dưới dạng số tiền
            discountType = "amount";
            discountValue = parseFloat(discountText.replace(/[^\d]/g, ""));
        }

        const shippingFee = parseFloat(
            $("#shippingFee").text().replace(/[^\d]/g, "")
        );

        // Tính toán giá trị giảm giá dựa vào loại giảm giá và giá trị giảm giá
        const discountAmount =
            discountType === "percentage"
                ? (subtotal + shippingFee) * (discountValue / 100)
                : discountValue;

        // Tính tổng tiền sau giảm giá
        let total = subtotal + shippingFee - discountAmount;

        if (isNaN(total) || !total) {
            total = 0;
        }

        // Chuyển đổi giá trị tổng tiền thành chỉ số (số nguyên)
        const totalIndex = Math.round(total);

        // Hiển thị chỉ số tổng tiền trong nhãn có id="total"
        $("#total").text(
            totalIndex.toLocaleString("vi-VN", {style: "currency", currency: "VND"})
        );

        // Gán giá trị chỉ số tổng tiền vào trường input ẩn có name="amount"
        $("input[name='amount']").val(totalIndex);
    }
});

function banHangOnlQuayLai() {
    window.location.href = "/customer/gio-hang-chi-tiet";
}

$(document).ready(function () {
    // Khi trang được tải, kiểm tra phương thức thanh toán mặc định và ẩn/hiện các nút tương ứng
    const paymentMethod = $('input[name="paymentMethod"]:checked').val();
    if (paymentMethod === "method1") {
        $("#buttonDatHang").show();
        $("#buttonThanhToan").hide();
    } else if (paymentMethod === "method2") {
        $("#buttonDatHang").hide();
        $("#buttonThanhToan").show();
    }

    // Gán sự kiện change cho radio buttons
    $('input[name="paymentMethod"]').change(function () {
        const selectedMethod = $(this).val();
        if (selectedMethod === "method1") {
            $("#buttonDatHang").show();
            $("#buttonThanhToan").hide();
        } else if (selectedMethod === "method2") {
            $("#buttonDatHang").hide();
            $("#buttonThanhToan").show();
        }
    });

    // $("#buttonThanhToan").click(function () {
    //     // Lấy các giá trị từ các trường thông tin
    //     let diaChiGiaoHang = getFullAddress();
    //     let nguoiNhan = $("#hoTen").val();
    //     let sdtNguoiNhan = $("#sdt").val();
    //     let ghiChu = $("#note").val();
    //     let shippingFee = parseFloat(
    //         $("#shippingFee").text().replace(/[^\d]/g, "")
    //     );
    //
    //     let tien_giam = parseFloat($("#discount").text().replace(/[^\d]/g, ""));
    //     let nameGiamGia = $("#tenGiamGia").text();
    //     let emailNguoiNhan = $("#nhapEmail").val();
    //
    //     // Lấy giá trị tiền giảm và tiền ship dưới dạng số nguyên (chỉ số)
    //     const tienGiamIndex = Math.round(tien_giam);
    //     const shippingFeeIndex = Math.round(shippingFee);
    //
    //     // Gán giá trị vào các trường input ẩn để truyền vào from -> VNPay
    //     $("#diaChiGiaoHang").val(diaChiGiaoHang);
    //     $("#nguoiNhan").val(nguoiNhan);
    //     $("#sdtNguoiNhan").val(sdtNguoiNhan);
    //     $("#ghiChu").val(ghiChu);
    //     $("#tienShipHD").val(shippingFeeIndex);
    //     $("#tienGiamGia").val(tienGiamIndex);
    //     $("#nameGiamGia").val(nameGiamGia);
    //     $("#emailNguoiNhann").val(emailNguoiNhan);
    //
    //     let tienShipCheck = $("#shippingFee").text();
    //
    //     if (nguoiNhan === "" || sdtNguoiNhan === "" || emailNguoiNhan === "" || tienShipCheck === "") {
    //         Swal.fire({
    //             icon: "error",
    //             title: "Vui lòng điền đầy đủ thông tin",
    //             showConfirmButton: false,
    //             timer: 2000,
    //         });
    //     } else {
    //         $("#paymentForm").submit();
    //     }
    // });
    $("#buttonThanhToan").click(function () {
        $(".thanhToanVNPAYMuaNgay").modal('show');

        $(".thanhToanVNPAYMuaNgay .btn-dong-y").click(function () {
            // Lấy các giá trị từ các trường thông tin
            let diaChiGiaoHang = getFullAddress();
            let nguoiNhan = $("#hoTen").val();
            let sdtNguoiNhan = $("#sdt").val();
            let ghiChu = $("#note").val();
            let shippingFee = parseFloat(
                $("#shippingFee").text().replace(/[^\d]/g, "")
            );

            let tien_giam = parseFloat($("#discount").text().replace(/[^\d]/g, ""));
            let nameGiamGia = $("#tenGiamGia").text();
            let emailNguoiNhan = $("#nhapEmail").val();

            // Lấy giá trị tiền giảm và tiền ship dưới dạng số nguyên (chỉ số)
            const tienGiamIndex = Math.round(tien_giam);
            const shippingFeeIndex = Math.round(shippingFee);

            // Gán giá trị vào các trường input ẩn để truyền vào from -> VNPay
            $("#diaChiGiaoHang").val(diaChiGiaoHang);
            $("#nguoiNhan").val(nguoiNhan);
            $("#sdtNguoiNhan").val(sdtNguoiNhan);
            $("#ghiChu").val(ghiChu);
            $("#tienShipHD").val(shippingFeeIndex);
            $("#tienGiamGia").val(tienGiamIndex);
            $("#nameGiamGia").val(nameGiamGia);
            $("#emailNguoiNhann").val(emailNguoiNhan);

            let tienShipCheck = $("#shippingFee").text();

            if (nguoiNhan === "" || sdtNguoiNhan === "" || emailNguoiNhan === "" || tienShipCheck === "") {
                Swal.fire({
                    icon: "error",
                    title: "Vui lòng điền đầy đủ thông tin",
                    showConfirmButton: false,
                    timer: 2000,
                });
            } else {
                $("#paymentForm").submit();
            }
        });

        $(".thanhToanVNPAYMuaNgay .btn-khong").click(function () {
            $('.thanhToanVNPAYMuaNgay').modal('hide');
        });
    });
});

// $(document).ready(function () {
//     $("#buttonDatHang").click(function () {
//         const orderId = $(this).data("id");
//         let tenGiamGiaa = $("#tenGiamGia").text();
//         let totalAmount = parseFloat($("#total").text().replace(/[^\d]/g, ""));
//         let shippingFee = parseFloat($("#shippingFee").text().replace(/[^\d]/g, ""));
//         let tien_giam = parseFloat($("#discount").text().replace(/[^\d]/g, ""));
//         let khuyenMai = $("#tenGiamGia").text();
//         let diaChiGiaoHang = getFullAddress();
//         let nguoiNhan = $("#hoTen").val();
//         let sdtNguoiNhan = $("#sdt").val();
//         let ghiChu = $("#note").val();
//         let emailNguoiNhan = $("#nhapEmail").val();
//
//         let tienShipCheck = $("#shippingFee").text();
//
//         if (nguoiNhan === "" || sdtNguoiNhan === "" || emailNguoiNhan === "" || tienShipCheck === "") {
//             Swal.fire({
//                 icon: "error",
//                 title: "Vui lòng điền đầy đủ thông tin",
//                 showConfirmButton: false,
//                 timer: 2000,
//             });
//         } else {
//             $.ajax({
//                 url: "/MuaNgay/save-order/" + orderId,
//                 type: "POST",
//                 data: {
//                     totalAmount: totalAmount,
//                     shippingFee: shippingFee,
//                     tien_giam: tien_giam,
//                     tenGiamGia: tenGiamGiaa,
//                     khuyenMai: khuyenMai,
//                     diaChiGiaoHang: diaChiGiaoHang,
//                     nguoiNhan: nguoiNhan,
//                     sdtNguoiNhan: sdtNguoiNhan,
//                     ghiChu: ghiChu,
//                     emailNguoiNhan: emailNguoiNhan,
//                 },
//                 success: function (response) {
//                     if (response.success) {
//                         Swal.fire({
//                             icon: "success",
//                             title: "Đặt hàng thành công",
//                             showConfirmButton: false,
//                             timer: 2000,
//                         }).then(function () {
//                             sessionStorage.setItem("isConfirmed", true);
//                             window.location.href = "/customer/home";
//                         });
//                     } else {
//                         // Xử lý khi lưu hóa đơn không thành công
//                         console.log("Lưu hóa đơn thất bại: " + response.error);
//                     }
//                 },
//                 error: function (xhr, status, error) {
//                     console.log("Lỗi khi gửi yêu cầu: " + error);
//                 },
//             });
//         }
//     });
// });
$(document).ready(function () {
    $("#buttonDatHang").click(function () {
        const orderId = $(this).data("id");
        let tenGiamGiaa = $("#tenGiamGia").text();
        let totalAmount = parseFloat($("#total").text().replace(/[^\d]/g, ""));
        let shippingFee = parseFloat($("#shippingFee").text().replace(/[^\d]/g, ""));
        let tien_giam = parseFloat($("#discount").text().replace(/[^\d]/g, ""));
        let khuyenMai = $("#tenGiamGia").text();
        let diaChiGiaoHang = getFullAddress();
        let nguoiNhan = $("#hoTen").val();
        let sdtNguoiNhan = $("#sdt").val();
        let ghiChu = $("#note").val();
        let emailNguoiNhan = $("#nhapEmail").val();
        let tienShipCheck = $("#shippingFee").text();

        $(".DatHangShipCodMuaNgay").modal('show');

        $(".DatHangShipCodMuaNgay .btn-dong-y").click(function () {
            if (nguoiNhan === "" || sdtNguoiNhan === "" || emailNguoiNhan === "" || tienShipCheck === "") {
                Swal.fire({
                    icon: "error",
                    title: "Vui lòng điền đầy đủ thông tin",
                    showConfirmButton: false,
                    timer: 2000,
                });
            } else {
                $.ajax({
                    url: "/MuaNgay/save-order/" + orderId,
                    type: "POST",
                    data: {
                        totalAmount: totalAmount,
                        shippingFee: shippingFee,
                        tien_giam: tien_giam,
                        tenGiamGia: tenGiamGiaa,
                        khuyenMai: khuyenMai,
                        diaChiGiaoHang: diaChiGiaoHang,
                        nguoiNhan: nguoiNhan,
                        sdtNguoiNhan: sdtNguoiNhan,
                        ghiChu: ghiChu,
                        emailNguoiNhan: emailNguoiNhan,
                    },
                    success: function (response) {
                        if (response.success) {
                            Swal.fire({
                                icon: "success",
                                title: "Đặt hàng thành công",
                                showConfirmButton: false,
                                timer: 2000,
                            }).then(function () {
                                sessionStorage.setItem("isConfirmed", true);
                                window.location.href = "/customer/home";
                            });
                        } else {
                            // Xử lý khi lưu hóa đơn không thành công
                            console.log("Lưu hóa đơn thất bại: " + response.error);
                        }
                    },
                    error: function (xhr, status, error) {
                        console.log("Lỗi khi gửi yêu cầu: " + error);
                    },
                });
            }
        });

        $(".DatHangShipCodMuaNgay .btn-khong").click(function () {
            $('.DatHangShipCodMuaNgay').modal('hide');
        });
    });
});

function getFullAddress() {
    const address = $("#addressInput").val();
    const province = $("#province option:selected").text();
    const district = $("#district option:selected").text();
    const ward = $("#ward option:selected").text();

    const fullAddress = address + ", " + ward + ", " + district + ", " + province;
    return fullAddress;
}

$(document).ready(function () {
    $(".themMaGiamGiaOnline").click(function () {
        // Get the value from the input field
        let couponCode = $("#maGiamGia").val();

        $.post(
            "/banHang/themMaGiamGiaOnline/" + "?couponCode=" + couponCode,
            function (response) {
                // Kiểm tra nếu ngày kết thúc khuyến mãi đã qua so với ngày hiện tại
                let endDate = new Date(response.ngayKetThuc); // Chuyển đổi ngày kết thúc thành đối tượng Date
                let currentDate = new Date(); // Lấy ngày hiện tại

                if (endDate < currentDate) {
                    Swal.fire({
                        icon: "error",
                        title: "Lỗi",
                        text: "Khuyến mãi đã kết thúc, không thể sử dụng mã giảm giá",
                    });
                    return;
                }

                Swal.fire({
                    icon: "success",
                    title: "Thành công",
                    text: "Đã thêm mã giảm giá " + couponCode,
                });
                // Hiển thị giá trị giảm giá dưới dạng số + %
                $("#tenGiamGia").text(response.tenGiamGia);
                // $('#discount').text(response.tienGiam + '%');
                calculateTotall(response.tienGiam, response.tienGiamToiDa);
            }
        ).fail(function (error) {
            Swal.fire({
                icon: "error",
                title: "Lỗi",
                text: "Sai mã giảm giá, hãy kiểm tra và thử lại",
            });
            console.log(error.responseText);
        });
    });
});


function calculateTotall(tienGiam, tienGiamToiDa) {
    let discount;
    const subtotal = parseFloat($("#subtotal").text().replace(/[^\d]/g, ""));
    const shippingFee = parseFloat($("#shippingFee").text().replace(/[^\d]/g, ""));
    const tongTienTamTinh = parseFloat($("#total").text().replace(/[^\d]/g, ""));

    if (tongTienTamTinh === 0) {
        Swal.fire({
            icon: "error",
            title: "Lỗi",
            text: "Vui lòng thêm địa chỉ giao hàng trước khi thêm mã giảm giá",
        });
        $("#tenGiamGia").text("");
        return;
    }

    const tienDuocGiam = (subtotal + shippingFee) * (tienGiam / 100);

    if (tienDuocGiam > tienGiamToiDa) {
        discount = tienGiamToiDa;
        const discountIndex = Math.round(discount);
        $("#discount").text(
            discountIndex.toLocaleString("vi-VN", {
                style: "currency",
                currency: "VND",
            })
        );
    } else {
        const discountPercent = tienGiam / 100;
        const discountByPercent = (subtotal + shippingFee) * discountPercent;
        discount = discountByPercent;
        $("#discount").text(tienGiam + "%");
    }

    let total = subtotal + shippingFee - discount;

    if (isNaN(total) || !total) {
        total = 0;
    }

    const totalIndex = Math.round(total);

    $("#total").text(
        totalIndex.toLocaleString("vi-VN", {style: "currency", currency: "VND"})
    );

    $("input[name='amount']").val(totalIndex);
}
