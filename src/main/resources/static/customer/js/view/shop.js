function chooseOptionLabel(labelName, labelId, inputName, inputId) {
  var lstName = document.getElementsByName(labelName);
  $(lstName).removeAttr("style");
  $(lstName).removeClass("label-active");
  $("#" + labelId).addClass("label-active");
  var inputIdName = document.getElementsByName("" + inputName);
  $(inputIdName).removeAttr("checked");
  $("#" + inputId).attr("checked", "true");

  flexUrlSubmit("/shop", "get");
}

function clearChooseOptionLabel(labelName, labelId, inputName, inputId) {
  var inputIdName = document.getElementsByName("" + inputName);
  $(inputIdName).removeAttr("checked");

  flexUrlSubmit("/shop", "get");
}

function chooseOptionLi(labelName, labelId, inputName, inputId) {
  var inputIdName = document.getElementsByName("" + inputName);
  $(inputIdName).removeAttr("checked");
  console.log(inputId);
  console.log(document.getElementById("" + labelId).style.color);
  if (document.getElementById("" + labelId).style.color == "#111111") {
    var name = document.getElementsByName("" + labelName);
    $(inputIdName).removeAttr("checked");
    $(name).removeAttr("style");
  } else {
    $("#" + inputId).attr("checked", "true");
    var name = document.getElementsByName("" + labelName);
    $(name).removeAttr("style");
    document.getElementById("" + labelId).style.color = "#111111";
  }

  flexUrlSubmit("/shop", "get");
}

function clearChooseOptionLi(labelName, labelId, inputName, inputId) {
  var inputIdName = document.getElementsByName("" + inputName);
  $(inputIdName).removeAttr("checked");
  if (document.getElementById("" + labelId).style.color == "#111111") {
    var name = document.getElementsByName("" + labelName);
    $(inputIdName).removeAttr("checked");
    $(name).removeAttr("style");
  } else {
    $("#" + inputId).attr("checked", "true");
    var name = document.getElementsByName("" + labelName);
    $(name).removeAttr("style");
    document.getElementById("" + labelId).style.color = "#111111";
  }

  flexUrlSubmit("/shop", "get");
}

function flexUrlSubmit(url, method) {
  $("#flexUrlTableFormShop").attr("action", "/customer" + url);
  $("#flexUrlTableFormShop").attr("method", method);
  document.getElementById("flexUrlTableFormShop").submit();
}

function flexUrlForm() {
  $("#flexUrlTableFormShop").submit();
}

function clearChooseOptionLabel(name, labelName) {
  var lstName = document.getElementsByName(labelName);
  $(lstName).removeClass("msActive");
  $("#allMauSacId").addClass("msActive");
  var mauSacIdName = document.getElementsByName(name);
  $(mauSacIdName).removeAttr("checked");
  flexUrlSubmit("/shop", "get");
}

function chooseOptionColorLabel(labelId, labelName, inputName, inputId) {
  var lstName = document.getElementsByName(labelName);
  $(lstName).removeClass("msActive");
  $("#" + labelId).addClass("msActive");
  var mauSacIdName = document.getElementsByName(inputName);
  $(mauSacIdName).removeAttr("checked");
  $("#" + inputId).attr("checked", "true");
  // var radiosChecked = $('input:radio[name=mauSacIds]');
  // radiosChecked.prop("checked", false);
  // // $(mauSacIdName).remove("checked");
  // if ($('#' + inputId).is(":checked") !== true) {
  //   $(this).prop("checked", false);
  // }

  // if ($('#' + inputId).is(':checked') === true) {
  //   console.log("1");
  //   $("#" + labelId).addClass("msActive");
  //   $("#" + inputId).attr("checked", "true");
  // } else if ($('#' + inputId).is(':checked') === false) {
  //   console.log("2");
  //   $(lstName).removeClass("msActive");
  //   $(mauSacIdName).removeAttr("checked");
  // } else {
  //   return;
  // }
  flexUrlSubmit("/shop", "get");
}

function chooseOptionColorLabelImg(
  labelId,
  labelName,
  inputName,
  inputId,
  spName,
  spId,
  imgboxName,
  imgboxId
) {
  console.log("🚀 ~ file: shop.js:113 ~ imgboxId:", imgboxId)
  console.log("🚀 ~ file: shop.js:113 ~ imgboxName:", imgboxName)
  console.log("🚀 ~ file: shop.js:113 ~ spId:", spId)
  console.log("🚀 ~ file: shop.js:113 ~ spName:", spName)
  console.log("🚀 ~ file: shop.js:113 ~ inputId:", inputId)
  console.log("🚀 ~ file: shop.js:113 ~ inputName:", inputName)
  console.log("🚀 ~ file: shop.js:113 ~ labelName:", labelName)
  console.log("🚀 ~ file: shop.js:113 ~ labelId:", labelId)
  var lstName = document.getElementsByName(labelName);
  console.log("🚀 ~ file: shop.js:114 ~ lstName:", lstName)
  $(lstName).removeClass("label-active");
  $("#" + labelId).addClass("label-active");
  var mauSacIdName = document.getElementsByName(inputName);
  console.log("🚀 ~ file: shop.js:118 ~ mauSacIdName:", mauSacIdName)
  $(mauSacIdName).removeAttr("checked");
  $("#" + inputId).attr("checked", "true");
  // choose san Pham id
  var sanPhamIdSPTQName = document.getElementsByName(spName);
  console.log("🚀 ~ file: shop.js:124 ~ sanPhamIdSPTQName:", sanPhamIdSPTQName)
  $(sanPhamIdSPTQName).removeAttr("checked");
  $("#" + spId).attr("checked", "true");

  var sanPhamIdSPTQName = document.getElementsByName(imgboxName);
  console.log("🚀 ~ file: shop.js:129 ~ sanPhamIdSPTQName:", sanPhamIdSPTQName)
  $(sanPhamIdSPTQName).removeClass("active");
  $("#" + imgboxId).addClass("active");
  console.log("---------------------------------------------")
}