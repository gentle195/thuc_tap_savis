document.addEventListener("DOMContentLoaded", function () {
    let inputBirthday = document.getElementById("inputBirthday");

    let today = new Date();
    let year = today.getFullYear();
    let month = (today.getMonth() + 1).toString().padStart(2, "0");
    let day = today.getDate().toString().padStart(2, "0");

    let formattedDate = year + "-" + month + "-" + day;
    inputBirthday.value = formattedDate;
});

$(document).ready(function () {

    $("#changeImageButton").click(function () {
        $("#imageInput").click(); // Trigger file input click
    });

    $("#imageInput").change(function () {
        let file = this.files[0];
        let reader = new FileReader();

        reader.onload = function (e) {
            $("#profileImage").attr("src", e.target.result).show(); // Hiển thị ảnh khi đã chọn
            $("#tenAnh").val(file.name); // Lưu tên ảnh vào trường input ẩn
        };

        reader.readAsDataURL(file);
    });

    $("#huyThemAnh").click(function () {
        $("#profileImage").attr("src", "url-to-default-image.jpg").hide(); // Hiển thị ảnh mặc định và ẩn ảnh
        $("#tenAnh").val(""); // Xóa tên ảnh khỏi trường input ẩn
        $("#imageInput").val(""); // Xóa giá trị của input file
    });
});

//Thêm nhân viên
$(document).ready(function () {
    $("#themNhanVien").click(function () {
        let ho = $("#hoNhanVien").val();
        let ten = $("#tenNhanVien").val();
        let email = $("#emailNhanVien").val();
        let diaChi = $("#diaChiNhanVien").val();
        let soDienThoai = $("#soDienThoaiNhanVien").val();
        let vaiTro = $("#selectVaiTro").val();
        let anhNhanVien = $("#tenAnh").val();

        if (!ho || !ten || !email || !diaChi || !soDienThoai || !vaiTro) {
            Swal.fire({
                icon: "error",
                title: "Vui lòng nhập đầy đủ thông tin và chọn chức vụ",
                showConfirmButton: false,
                timer: 2000,
            });
            return;
        }

        $.post("http://localhost:8080/ThemMoiNhanVien", {
            ho: ho,
            ten: ten,
            email: email,
            diaChi: diaChi,
            soDienThoai: soDienThoai,
            ChucVu: vaiTro,
            anhNhanVien: anhNhanVien
        }, function (response) {
            if (response.success) {
                Swal.fire({
                    icon: "success",
                    title: "Thêm nhân viên thành công",
                    showConfirmButton: false,
                    timer: 2000,
                }).then(() => {
                    window.location.href = "/admin/NhanVien/danhSach";
                });
            } else if (response.warning) {
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
