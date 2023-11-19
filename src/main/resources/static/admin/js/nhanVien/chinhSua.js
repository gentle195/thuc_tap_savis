$(document).ready(function () {

    $("#changeImageButton").click(function () {
        $("#imageInput").click(); // Trigger file input click
    });

    $("#imageInput").change(function () {
        let file = this.files[0];
        let reader = new FileReader();

        reader.onload = function (e) {
            $("#profileImage").attr("src", e.target.result).show(); // Hiển thị ảnh khi đã chọn
            $("#profileImageTrong").attr("src", e.target.result).show(); // Hiển thị ảnh khi đã chọn
            $("#tenAnhChinhSua").val(file.name); // Lưu tên ảnh vào trường input ẩn
        };

        reader.readAsDataURL(file);
    });

    $("#huyThemAnh").click(function () {
        $("#profileImage").attr("src", "url-to-default-image.jpg").hide(); // Hiển thị ảnh mặc định và ẩn ảnh
        $("#tenAnhChinhSua").val(""); // Xóa tên ảnh khỏi trường input ẩn
        $("#imageInput").val(""); // Xóa giá trị của input file
    });
});

// Chỉnh sửa nhân viên
$(document).ready(function () {
    $("#ChinhSuaNhanVien").click(function () {
        let hoTen = $("#hoTenNhanVien").val();
        let email = $("#emailNhanVienChinhSua").val();
        let diaChi = $("#diaChiNhanVienChinhSua").val();
        let soDienThoai = $("#soDienThoaiNhanVienChinhSua").val();
        let vaiTro = $("#selectVaiTroChinhSua").val();
        let anhNhanVien = $("#tenAnhChinhSua").val();
        let idNhanVien = $("#idNhanVien").val();

        if (!hoTen || !email || !diaChi || !soDienThoai || !vaiTro) {
            Swal.fire({
                icon: "error",
                title: "Vui lòng nhập đầy đủ thông tin và chọn chức vụ",
                showConfirmButton: false,
                timer: 2000,
            });
            return;
        }

        $.post("/LuuThongTinChinhSua", {
            idNhanVien: idNhanVien,
            hoTen: hoTen,
            email: email,
            diaChi: diaChi,
            soDienThoai: soDienThoai,
            ChucVu: vaiTro,
            anhNhanVien: anhNhanVien
        }, function (response) {
            if (response.success) {
                Swal.fire({
                    icon: "success",
                    title: "Sửa thông tin nhân viên thành công",
                    showConfirmButton: false,
                    timer: 2000,
                }).then(() => {
                    window.location.href = "/admin/NhanVien/danhSach";
                });
            } else {
                Swal.fire({
                    icon: "error",
                    title: response.error,
                    showConfirmButton: false,
                    timer: 2000,
                });
            }
        });
    });
});

$(document).ready(function () {
    // Xử lý sự kiện khi ấn nút "Xóa tất cả"
    $("#xoaTatCaInputNhanVien").click(function () {
        // Xóa nội dung trong tất cả các input
        $("input[type='text']").val("");
        $("input[type='email']").val("");
        $("input[type='tel']").val("");
        $("select").val("");
    });
});

function quayLaiNhanVien() {
    window.location.href = "/admin/NhanVien/danhSach";
}