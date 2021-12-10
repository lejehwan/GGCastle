function newKey() {
	/*let data = {
		loginId :$("#user_id").val()
	};*/
	let loginId = $("#user_id").val();
	$.ajax({
		url: '/newAPIKey/'+loginId,
		type: "PUT",
		success: function(data) {
			alert("generate new APIKey");
			location.href="/search?searchId="+loginId;
		}
	});
}

function lock() {
	let loginId = $("#user_id").val();
	$.ajax({
		url: '/userLock/'+loginId,
		type: "PUT",
		success: function(data) {
			alert("successful");
			location.href="/search?searchId="+loginId;
		}
	});
}