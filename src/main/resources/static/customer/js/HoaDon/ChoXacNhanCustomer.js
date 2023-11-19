$(document).ready(function () {
    let currentHoaDonId;

    $('.HuyDonCus').click(function () {
        currentHoaDonId = $(this).data('id');
        $('.huyModalCustomer[data-id="' + currentHoaDonId + '"]').modal('show');
    });

    $('.huyModalCustomer .btn-dong-y').click(function () {
        // Gửi yêu cầu xác nhận đơn hàng bằng Ajax
        $.get('/customer/updateHuyDon/' + currentHoaDonId, function (response) {
            Swal.fire({
                icon: 'success', title: 'Đã hủy thành công', showConfirmButton: false, timer: 2000
            }).then(function () {
                sessionStorage.setItem('isConfirmed', true);
                location.reload();
            });
        });

        $('.huyModalCustomer').modal('hide');
    });

    $('.huyModalCustomer .btn-khong').click(function () {
        $('.huyModalCustomer').modal('hide');
    });
});
