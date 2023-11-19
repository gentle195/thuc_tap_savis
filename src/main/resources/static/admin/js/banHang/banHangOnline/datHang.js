function taoHoaDon() {
    // Lấy tất cả các ô checkbox có class "form-checkbox" (lớp dùng cho các ô checkbox giỏ hàng)
    const checkboxes = document.querySelectorAll('.form-checkbox');

    // Kiểm tra xem có checkbox nào được chọn hay không
    let hasCheckedItems = false;
    checkboxes.forEach(checkbox => {
        if (checkbox.checked) {
            hasCheckedItems = true;
            return;
        }
    });

    if (!hasCheckedItems) {
        Swal.fire({
            icon: 'error',
            title: 'Lỗi',
            text: 'Vui lòng chọn sản phẩm',
            showConfirmButton: false,
            timer: 2000
        });
        return;
    }

    const selectedCartItemIds = [];
    checkboxes.forEach(checkbox => {
        if (checkbox.checked) {
            selectedCartItemIds.push(checkbox.value);
        }
    });

    fetch('/customer/gio-hang-chi-tiet/tao-hoa-don', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(selectedCartItemIds),
    })
        .then(response => {
            window.location.href = response.url;
        })
        .catch(error => {
            console.error('Lỗi lưu chi tiết hóa đơn:', error);
        });
}
