function flexUrlSubmit(url, method, formName) {
    $("#flexUrlTableForm" + formName).attr("action", "/admin/mau-sac/" + url);
    $("#flexUrlTableForm" + formName).attr("method", method);
    document.getElementById("flexUrlTableForm" + formName).submit();
}

function openPopupCreateOrUpdate(createOrUpdateType, chatLieuId, tenChatLieu, maMauSac) {
    if (createOrUpdateType === "create") {
        $('#CreateOrUpdateTitle').text("Thêm mới màu sắc");
        $('#mauSacIdCreateOrUpdate').val(-1);
        $('#tenMauSacCreateOrUpdate').val("");
        $('#maMauSacCreateOrUpdate').val("ffffff");
        $('#yesOptionCreateOrUpdateModalId').text("Thêm mới");
    } else if (createOrUpdateType === "update") {
        $('#CreateOrUpdateTitle').text("Cập nhật màu sắc");
        $('#mauSacIdCreateOrUpdate').val(chatLieuId);
        $('#tenMauSacCreateOrUpdate').val(tenChatLieu);

        $('#maMauSacCreateOrUpdate').val(maMauSac);
        console.log("🚀 ~ file: mauSac.js:20 ~ openPopupCreateOrUpdate ~ maMauSac:", maMauSac)
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
    var kcId = $("#yesOptionDeleteModalId").attr("cl-id");
    flexUrlSubmit("delete/" + kcId, "get", "MauSac");
}

function submitCreateOrUpdate() {
    $('#formCreateOrUpdate').submit();
}