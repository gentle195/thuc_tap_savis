function flexUrlSubmit(url, method, formName) {
    $("#flexUrlTableForm" + formName).attr("action", "/admin/loai-san-pham/" + url);
    $("#flexUrlTableForm" + formName).attr("method", method);
    document.getElementById("flexUrlTableForm" + formName).submit();
}

function openPopupCreateOrUpdate(createOrUpdateType, chatLieuId, tenChatLieu) {
    if (createOrUpdateType === "create") {
        $('#CreateOrUpdateTitle').text("Thêm mới loại sản phẩm");
        $('#loaiSanPhamIdCreateOrUpdate').val(-1);
        $('#tenLoaiSanPhamCreateOrUpdate').val("");
        $('#yesOptionCreateOrUpdateModalId').text("Thêm mới");
    } else if (createOrUpdateType === "update") {
        $('#CreateOrUpdateTitle').text("Cập nhật loại sản phẩm");
        $('#loaiSanPhamIdCreateOrUpdate').val(chatLieuId);
        $('#tenLoaiSanPhamCreateOrUpdate').val(tenChatLieu);
        $('#yesOptionCreateOrUpdateModalId').text("Cập nhật");
    } else {
        return;
    }
    $('#createOrUpdateModalId').modal("show");
}

function showConfirmModalDeleteDialog(id, name) {
    $("#productName").text(name);
    $("#yesOptionDeleteModalId").attr("cl-id", id);
    $("#deleteModalId").modal("show");
}

function submitDeleteProduct() {
    var productId = $("#yesOptionDeleteModalId").attr("cl-id");
    flexUrlSubmit("delete/" + productId, "get", "LoaiSanPham");
}

function submitCreateOrUpdate() {
    $('#formCreateOrUpdate').submit();
}