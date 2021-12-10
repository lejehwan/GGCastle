// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#858796';

// Pie Chart Example
function getView2() {

	$.ajax({
		url: "/getFailUsers",
		type: "GET",
		success: function(data) {
			let obj = JSON.parse(data);
			let myData2 = [];
			let myLabel2 = [];
			$.each(obj, function(k, v) {
				myLabel2.push(v.user_id);
				myData2.push(v.count);
			});
			let ctx = document.getElementById('myPieChart').getContext('2d');
			makePieChart(ctx, 'pie', myLabel2, myData2);
		}
	});
}
function makePieChart(ctx, type, label, data) {
	let myPieChart = new Chart(ctx, {
		type: type,
		data: {
			labels: label,
			datasets: [{
				data: data,
				backgroundColor: ['#4e73df', '#1cc88a', '#36b9cc'],
				hoverBackgroundColor: ['#2e59d9', '#17a673', '#2c9faf'],
				hoverBorderColor: "rgba(234, 236, 244, 1)",
			}]
		},
		options: {
		maintainAspectRatio: false,
		tooltips: {
			backgroundColor: "rgb(255,255,255)",
			bodyFontColor: "#858796",
			borderColor: '#dddfeb',
			borderWidth: 1,
			xPadding: 15,
			yPadding: 15,
			displayColors: false,
			caretPadding: 10,
		},
		legend: {
			display: false
		},
		cutoutPercentage: 80,
	},
	});
}

