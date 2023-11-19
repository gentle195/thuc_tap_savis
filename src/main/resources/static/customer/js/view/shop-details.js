function chooseOptionLabel(labelName, labelId, inputName, inputId) {
    var lstName = document.getElementsByName(labelName);
    $(lstName).removeClass("label-active");
    $("#" + labelId).addClass("label-active");
    var inputIdName = document.getElementsByName("" + inputName);
    $(inputIdName).removeAttr("checked");
    $("#" + inputId).attr("checked", "true");
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

function flexUrlSubmit(url, method) {
    $("#flexUrlTableFormShopDetails").attr("action", "/customer/" + url);
    $("#flexUrlTableFormShopDetails").attr("method", method);
    document.getElementById("flexUrlTableFormShopDetails").submit();
}

function flexUrlForm() {
    $("#flexUrlTableFormShop").submit();
}

function chooseOptionColorLabel(labelId, labelName, inputName, inputId) {
    var lstName = document.getElementsByName(labelName);
    $(lstName).removeClass("msActive");
    $("#" + labelId).toggleClass("msActive");
    // $(lstName).removeAttr("style");
    // $("#" + labelId).attr("style", "border: 1px solid black;");

    var mauSacIdName = document.getElementsByName(inputName);
    $(mauSacIdName).removeAttr("checked");
    $("#" + inputId).attr("checked", "true");
}


function changeTab(tabId, navParentId) {
    var tabName = document.getElementsByName("tab-pane-img");
    var navName = document.getElementsByName("nav-item-img");
    $(tabName).removeClass("active");
    $(navName).removeAttr("style");

    $("#" + tabId).addClass("active");
    $("#" + navParentId).attr("style", "border: 2px black;");
}
