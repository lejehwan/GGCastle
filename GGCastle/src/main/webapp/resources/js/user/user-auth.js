let index = {

	init: function() {
		$("#btn-auth").on("click", () => {
			this.auth();
		})
	},

	auth: function() {
		let authKey = $("#authKey").val();
		$.ajax({
			type: "POST",
			url: "/api/auth/text",
			data: { authKey: authKey },
			contentType: "application/x-www-form-urlencoded; charset=utf-8",// 보내는 자원의 형
		}).done(function(resp) {
			if (resp.status === 401) {
				alert("not validation");
			} else {
				location.href = "/home";
			}
		}).fail(function(error) {
			console.log(JSON.stringify(error));
			location.href = "/errorPage";
		});
	},

	auth: function() {
		let data = {
			authKey: $("#authKey").val()
		};
		console.log(JSON.stringify(data));
		$.ajax({
			type: "POST",
			url: "/api/auth/json",
			data: JSON.stringify(data),// JSON으로 변환
			contentType: "application/json",
		}).done(function(resp) {
			if (resp.status === 401) {
				alert("not validation");
			} else {
				location.href = "/home";
			}
		}).fail(function(error) {
			console.log(JSON.stringify(error));
			location.href = "/errorPage";
		});
	}
}
index.init();




