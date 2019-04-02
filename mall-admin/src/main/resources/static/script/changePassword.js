function changePassword() {
	$(".change-password").dialog({
        resizable: false,
        height: "auto",
        width: 500,
        modal: true
	});
}

function cancelChangePassword() {
	$(".change-password").dialog('close');
}

function doChangePassword() {
	$.post({
		url: changePasswordUrl,
		data: {"password" : $.trim($("#inputPassword").val())},
		success: function(data) {
			$("#inputPassword").val("");
			$(".change-password").dialog('close');
			if(data) dialog("Congration", "Password have been reset");
		},
		error: function() {
			$(".change-password").dialog('close');
		}
	});
}