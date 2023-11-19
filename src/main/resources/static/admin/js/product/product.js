// VirtualSelect.init({
//   ele: "select",
// });

function showConfirmModalDeleteDialog(id, name) {
  $("#productName").text(name);
  // $("#yesOptionDeleteModalId").attr(
  //   "href",
  //   "/admin/product/delete/" + id + "/" + pageName
  // );
  $("#yesOptionDeleteModalId").attr("product-id", id);
  $("#deleteModalId").modal("show");
}

function submitDeleteProduct() {
  var productId = $("#yesOptionDeleteModalId").attr("product-id");
  flexUrlSubmit("delete/" + productId, "get", "ProductManage");
}

function submitDeleteProductDetail() {
  var productId = $("#yesOptionDeleteModalId").attr("product-id");
  flexUrlSubmit("deleteProductDetail/" + productId, "get", "AddProduct");
}

// function submitDeleteProductDetail() {
//   var productId = $("#yesOptionDeleteModalId").attr("product-id");
//   flexUrlSubmit(
//     "delete/" + productId + "/productManage",
//     "get",
//     "ProductManage"
//   );
// }

function openPopupChangeIsShowFormAddProduct(idItem) {
  $.ajax({
    url:
      "http://localhost:8080/admin/product/changeIsShowFormAddProduct/" + idItem,
    type: "GET",
    contentType: "application/json",
    success: function (result) {
      var html =
        "<div class='toast-header'> <svg class='bd-placeholder-img rounded mr-2' width='20' height='20' xmlns='http://www.w3.org/2000/svg' preserveAspectRatio='xMidYMid slice' focusable='false' role='img'><rect width='100%' height='100%' fill='blue'></rect></svg><strong class='mr-auto'>Thông báo hệ thống</strong><small>Now</small><button type='button' class='ml-2 mb-1 close' data-dismiss='toast' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div><div class='toast-body'>Cập nhật có hiển thị sản phẩm thành công</div>";
      $("#toastsCustomCss").attr(
        "style",
        "position: fixed; top: 10px; right: 0;z-index: 1;"
      );
      $("#toastAjax").html(html);
      $("#toastAjax").toast("show");
    },
    error: function (result) {
      var html =
        "<div class='toast-header'> <svg class='bd-placeholder-img rounded mr-2' width='20' height='20' xmlns='http://www.w3.org/2000/svg' preserveAspectRatio='xMidYMid slice' focusable='false' role='img'><rect width='100%' height='100%' fill='red'></rect></svg><strong class='mr-auto'>Thông báo hệ thống</strong><small>Now</small><button type='button' class='ml-2 mb-1 close' data-dismiss='toast' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div><div class='toast-body'>Cập nhật có hiển thị sản phẩm thất bại</div>";
      $("#toastsCustomCss").attr(
        "style",
        "position: fixed; top: 10px; right: 0;z-index: 1;"
      );
      $("#toastAjax").html(html);
      $("#toastAjax").toast("show");
    },
  });
}

function showConfirmModalDialogDeleteAllbyId() {
  $("#confirmationDeleteIds").modal("show");
}

function flexUrlSubmit(url, method, formName) {
  $("#flexUrlTableForm" + formName).attr("action", "/admin/product/" + url);
  $("#flexUrlTableForm" + formName).attr("method", method);
  document.getElementById("flexUrlTableForm" + formName).submit();
}

function toggleProductIds(source) {
  checkboxes = document.getElementsByName("productIds");
  for (var i = 0, n = checkboxes.length; i < n; i++) {
    checkboxes[i].checked = source.checked;
  }
}

function toggleProductDetailIds(source) {
  checkboxes = document.getElementsByName("productDetailIds");
  for (var i = 0, n = checkboxes.length; i < n; i++) {
    checkboxes[i].checked = source.checked;
  }
}

// function openBoxCreateImgs(id) {
//   if (document.getElementById(id).isCheck === true) {
//   }
// }

function openPopupIsShowSpeedAddProduct(tenThuocTinh, tenField) {
  document.getElementById("tenThuocTinh").innerHTML = tenThuocTinh;
  document.getElementById("fieldthuocTinhInput").value = tenField;
  var htmlObj = document.getElementById("maMauSacDiv");
  html = "";
  htmlObj.innerHTML = html;
  if (tenField === "mauSac") {
    var htmlObj = document.getElementById("maMauSacDiv");
    html =
      "<label for='maMauSacInput'>Mã màu sắc:</label><input type='color' class='form-control' name='maMauSac' id='maMauSacInput' />";
    htmlObj.innerHTML = html;
  }
  $("#iShowSpeedModalId").modal("show");
}

// $("#multiple-select-field").select2({
//   theme: "bootstrap-5",
//   width: $(this).data("width")
//     ? $(this).data("width")
//     : $(this).hasClass("w-100")
//     ? "100%"
//     : "style",
//   placeholder: $(this).data("placeholder"),
//   closeOnSelect: false,
// });

function changeIsShowFormAddProduct() {
  flexUrlSubmit("changeIsShowFormAddProduct", "post", "AddProduct");
}

function changeCoHienThi(imgName, id) {
  $.ajax({
    url:
      "http://localhost:8080/admin/product/changeCoHienThi/" +
      imgName,
    type: "GET",
    contentType: "application/json",
    success: function (result) {
      var html =
        "<div class='toast-header'> <svg class='bd-placeholder-img rounded mr-2' width='20' height='20' xmlns='http://www.w3.org/2000/svg' preserveAspectRatio='xMidYMid slice' focusable='false' role='img'><rect width='100%' height='100%' fill='blue'></rect></svg><strong class='mr-auto'>Thông báo hệ thống</strong><small>Now</small><button type='button' class='ml-2 mb-1 close' data-dismiss='toast' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div><div class='toast-body'>Cập nhật có hiển thị ảnh cho sản phẩm thành công</div>";
      $("#toastsCustomCss").attr(
        "style",
        "position: fixed; top: 10px; right: 0;z-index: 1;"
      );
      $("#toastAjax").html(html);
      $("#toastAjax").toast("show");
      if ($('#' + id).hasClass("active") == true) {
        $('#' + id).removeClass("active");
      } else $('#' + id).addClass("active");
    },
    error: function (result) {
      var html =
        "<div class='toast-header'> <svg class='bd-placeholder-img rounded mr-2' width='20' height='20' xmlns='http://www.w3.org/2000/svg' preserveAspectRatio='xMidYMid slice' focusable='false' role='img'><rect width='100%' height='100%' fill='red'></rect></svg><strong class='mr-auto'>Thông báo hệ thống</strong><small>Now</small><button type='button' class='ml-2 mb-1 close' data-dismiss='toast' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div><div class='toast-body'>Cập nhật có hiển thị ảnh cho sản phẩm thất bại</div>";
      $("#toastsCustomCss").attr(
        "style",
        "position: fixed; top: 10px; right: 0;z-index: 1;"
      );
      $("#toastAjax").html(html);
      $("#toastAjax").toast("show");
    },
  });
}

function changeLaAnhChinh(imgName, id, name) {
  $.ajax({
    url:
      "http://localhost:8080/admin/product/changeLaAnhChinh/" +
      imgName,
    type: "GET",
    contentType: "application/json",
    success: function (result) {
      var html =
        "<div class='toast-header'> <svg class='bd-placeholder-img rounded mr-2' width='20' height='20' xmlns='http://www.w3.org/2000/svg' preserveAspectRatio='xMidYMid slice' focusable='false' role='img'><rect width='100%' height='100%' fill='blue'></rect></svg><strong class='mr-auto'>Thông báo hệ thống</strong><small>Now</small><button type='button' class='ml-2 mb-1 close' data-dismiss='toast' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div><div class='toast-body'>Cập nhật ảnh chính cho sản phẩm thành công</div>";
      $("#toastsCustomCss").attr(
        "style",
        "position: fixed; top: 10px; right: 0;z-index: 10000;"
      );
      $("#toastAjax").html(html);
      $("#toastAjax").toast("show");
      var nameAC = document.getElementsByName(name);
      $(nameAC).removeClass("active");
      $('#' + id).addClass("active")
    },
    error: function (result) {
      var html =
        "<div class='toast-header'> <svg class='bd-placeholder-img rounded mr-2' width='20' height='20' xmlns='http://www.w3.org/2000/svg' preserveAspectRatio='xMidYMid slice' focusable='false' role='img'><rect width='100%' height='100%' fill='red'></rect></svg><strong class='mr-auto'>Thông báo hệ thống</strong><small>Now</small><button type='button' class='ml-2 mb-1 close' data-dismiss='toast' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div><div class='toast-body'>Cập nhật ảnh chính cho sản phẩm thất bại</div>";
      $("#toastsCustomCss").attr(
        "style",
        "position: fixed; top: 10px; right: 0;z-index: 1;"
      );
      $("#toastAjax").html(html);
      $("#toastAjax").toast("show");
    },
  });
}

function openPopupRemoveHinhAnh(imgName, imgBox) {
  $('#yesOptionDeleteImageModalId').attr("imgName", imgName);
  $('#yesOptionDeleteImageModalId').attr("imgBox", imgBox);
  $('#deleteImageModalId').modal("show");
}

function confirmRemoveHinhAnh() {
  var imgName = $('#yesOptionDeleteImageModalId').attr("imgName");
  var imgBox = $('#yesOptionDeleteImageModalId').attr("imgBox");
  removeHinhAnh(imgName, imgBox);
  $('#deleteImageModalId').modal("hide");
}

function openPopupNoteImg() {
  $('#imageNoteModalId').modal("show");
}

function removeHinhAnh(imgName, imgBox) {
  $.ajax({
    url:
      "http://localhost:8080/admin/product/removeHinhAnh/" +
      imgName,
    type: "GET",
    contentType: "application/json",
    success: function (result) {
      var html =
        "<div class='toast-header'> <svg class='bd-placeholder-img rounded mr-2' width='20' height='20' xmlns='http://www.w3.org/2000/svg' preserveAspectRatio='xMidYMid slice' focusable='false' role='img'><rect width='100%' height='100%' fill='blue'></rect></svg><strong class='mr-auto'>Thông báo hệ thống</strong><small>Now</small><button type='button' class='ml-2 mb-1 close' data-dismiss='toast' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div><div class='toast-body'>Xóa hình ảnh sản phẩm thành công</div>";
      $("#toastsCustomCss").attr(
        "style",
        "position: fixed; top: 10px; right: 0;z-index: 10000;"
      );
      $("#toastAjax").html(html);
      $("#toastAjax").toast("show");
      $('#' + imgBox).remove();
    },
    error: function (result) {
      var html =
        "<div class='toast-header'> <svg class='bd-placeholder-img rounded mr-2' width='20' height='20' xmlns='http://www.w3.org/2000/svg' preserveAspectRatio='xMidYMid slice' focusable='false' role='img'><rect width='100%' height='100%' fill='red'></rect></svg><strong class='mr-auto'>Thông báo hệ thống</strong><small>Now</small><button type='button' class='ml-2 mb-1 close' data-dismiss='toast' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div><div class='toast-body'>Xóa hình ảnh sản phẩm thất bại</div>";
      $("#toastsCustomCss").attr(
        "style",
        "position: fixed; top: 10px; right: 0;z-index: 1;"
      );
      $("#toastAjax").html(html);
      $("#toastAjax").toast("show");
    },
  });
}

$(document).ready(function () {
  $('[data-toggle="popover"]').popover();
  $('[data-toggle="popover"]').mousedown(function () {
    $('[data-toggle="popover"]').popover('hide');
  });
});

function openPopupCreateProductDetail() {
  $('#createProductDetailForm').modal('show');
}

function previewImages(index) {
  var files = document.getElementById("file-input-" + index).files;
  if (files.length > 0) {
    for (var i = 0; i < files.length; i++) {
      readAndPreview(index, files[i]);
    }
  }
}

function readAndPreview(indexPreview, file) {
  var $preview = $("#preview-img-" + indexPreview).empty();
  if (!/\.(jpe?g|png|jfif)$/i.test(file.name)) {
    var html =
      "<div class='toast-header'> <svg class='bd-placeholder-img rounded mr-2' width='20' height='20' xmlns='http://www.w3.org/2000/svg' preserveAspectRatio='xMidYMid slice' focusable='false' role='img'><rect width='100%' height='100%' fill='blue'></rect></svg><strong class='mr-auto'>Thông báo hệ thống</strong><small>Now</small><button type='button' class='ml-2 mb-1 close' data-dismiss='toast' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div><div class='toast-body'>Định dạng file ảnh có tên: " + file.name + " không hợp lệ!</div>";
    $("#toastsCustomCss").attr(
      "style",
      "position: fixed; top: 10px; right: 0;z-index: 10000;"
    );
    $("#toastAjax").html(html);
    $("#toastAjax").toast("show");
    $("#file-input-" + indexPreview).value = "";
    return;
  }
  var reader = new FileReader();

  reader.readAsDataURL(file);

  $(reader).on("load", function (e) {
    var html =
      " <div id='img-box-" +
      file.name + "-" + indexPreview +
      "' class='file-preview-frame krajee-default kv-preview-thumb rotatable'> " +
      " <div class='kv-file-content' > " +
      " <img src=' " +
      e.target.result +
      " ' class='file-preview-image kv-preview-data' " +
      " style='width: auto; height: auto; max-width: 100%; max-height: 100%; image-orientation: from-image;'> " +
      " </div> " +
      " <div class='file-thumbnail-footer'> " +
      " <div class='file-footer-caption' " +
      " title=' " +
      file.name +
      " '> " +
      " <div class='file-caption-info'> " +
      file.name +
      " </div> " +
      " <div class='file-size-info'> <samp>( " +
      file.size +
      " KB)</samp> " +
      " </div> " +
      " </div> " +
      " <div class='file-thumb-progress kv-hidden'> " +
      " <div class='progress'> " +
      " <div class='progress-bar bg-info progress-bar-info progress-bar-striped active progress-bar-animated' " +
      " role='progressbar' aria-valuenow='101' " +
      " aria-valuemin='0' aria-valuemax='100' " +
      " style='width: 101%;'> " +
      " Initializing … " +
      " </div> " +
      " </div> " +
      " </div> " +
      " <div class='file-upload-indicator' " +
      " title='Not uploaded yet'><i " +
      " class='bi-plus-lg text-warning'></i></div> " +
      " <div class='file-actions'>" +
      // "  <div class='file-footer-buttons'> " +
      // " <button type='button' " +
      // " class='kv-file-rotate btn btn-sm btn-kv btn-default btn-outline-secondary' " +
      // "  title='Rotate 90 deg. clockwise'><i " +
      // " class='bi-arrow-clockwise'></i></button> " +
      // " <button type='button' " +
      // " class='kv-file-upload btn btn-sm btn-kv btn-default btn-outline-secondary' " +
      // " title='Upload file'><i " +
      // " class='bi-upload'></i></button> " +
      // " <button type='button' " +
      // " class='kv-file-remove btn btn-sm btn-kv btn-default btn-outline-secondary' onclick=\"removeImg('" +
      // file.name + "-" + indexPreview +
      // "','" +
      // indexPreview +
      // "')\"" +
      // " title='Remove file'><i " +
      // " class='bi-trash'></i></button> " +
      // " <button type='button' " +
      // " class='kv-file-zoom btn btn-sm btn-kv btn-default btn-outline-secondary' " +
      // " title='View Details'><i " +
      // "  class='bi-zoom-in'></i></button> " +
      // " </div> " +
      " </div> " +
      " <div class='clearfix'></div> " +
      " </div> " +
      " <div class='kv-zoom-cache'> " +
      " <div class='file-preview-frame krajee-default  kv-zoom-thumb rotatable' " +
      " id='zoom-thumb-inp-add-2-21238_63ea4beff683aa1e4b6dec64e1ae8e60.jfif' " +
      " data-fileindex='-1' " +
      " data-fileid='21238_63ea4beff683aa1e4b6dec64e1ae8e60.jfif' " +
      " data-filename='63ea4beff683aa1e4b6dec64e1ae8e60.jfif.jpg' " +
      " data-template='image' data-zoom='{zoomData}'> " +
      " <div class='kv-file-content'> " +
      " </div> " +
      " </div> " +
      " </div> " +
      " </div> ";
    $preview.append(html);
  });
}
