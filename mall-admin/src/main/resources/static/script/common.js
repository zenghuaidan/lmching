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