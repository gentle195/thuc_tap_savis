function showConfirmModalDeleteDialogVoucher(id) {
    $("#yesOptionDeleteModalIdVoucher").attr("voucher-id", id);
    $("#deleteModalIdVoucher").modal("show");
}
function submitDeleteVoucher() {
    var voucherId = $("#yesOptionDeleteModalIdVoucher").attr("voucher-id");
    var link = document.createElement('a');
    link.href = "voucher/delete/" + voucherId;
    link.click();
}

function submit(pageNum) {
    var page = pageNum || 1;
    var size = document.getElementById('size').value || 10;
    var keyword = document.getElementById('search').value;
    var status = document.getElementById('status').value || "ALL";
    var discountStart = Math.floor(document.getElementById('startRange').value);
    var discountEnd = Math.floor(document.getElementById('endRange').value);
    var dateFrom = document.getElementById('dateFrom').value;
    var dateTo = document.getElementById('dateTo').value;

    var url = '/admin/voucher?' +
        'page=' + encodeURIComponent(page) +
        '&size=' + encodeURIComponent(size) +
        '&keyword=' + encodeURIComponent(keyword) +
        '&status=' + encodeURIComponent(status) +
        '&discountStart=' + encodeURIComponent(discountStart) +
        '&discountEnd=' + encodeURIComponent(discountEnd) +
        '&dateFrom=' + encodeURIComponent(dateFrom) +
        '&dateTo=' + encodeURIComponent(dateTo);

    var link = document.createElement('a');
    link.href = url;
    link.click();
}
// Create the range slider
function createSlider(){
    var discountRange = document.getElementById('discountRange');
    var discountStart = document.getElementById('startRange');
    var discountEnd = document.getElementById('endRange');
    var start = discountStart.value || 0;
    var end = discountEnd.value || 30;
    noUiSlider.create(discountRange, {
        start: [start, end], // Initial range values
        connect: true, // Connect the handles with a colored line
        range: {
            'min': 0,
            'max': 100
        }
    });

// Get the selected range values and display them
    var selectedRange = document.getElementById('selectedRange');
    discountRange.noUiSlider.on('update', function (values, handle) {
        selectedRange.textContent = values.join(' - ');
        discountStart.value = Math.floor(values[0]);
        discountEnd.value = Math.floor(values[1]);
    });
}
window.onload(createSlider());