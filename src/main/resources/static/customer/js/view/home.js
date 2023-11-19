function chooseOptionColorLabel(
  labelId,
  labelName,
  inputName,
  inputId,
  spName,
  spId,
  imgboxName,
  imgboxId
) {
  var lstName = document.getElementsByName(labelName);
  $(lstName).removeClass("label-active");
  $("#" + labelId).addClass("label-active");
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
