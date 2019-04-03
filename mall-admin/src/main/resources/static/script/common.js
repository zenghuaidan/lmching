function dialog(title, message) {
	var myDialog = "<div title='" + title + "'><p>" + message + "</p></div>";
    $(myDialog).dialog({
        resizable: false,
        modal: true,
        buttons: {
          Ok: function() {
            $(this).dialog("close");
          }
        }
    });
}

function valid(email) {
	var reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
    return value != "" && reg.test(value);
}