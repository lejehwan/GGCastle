
function getView3() {

	$.ajax({
		url: "/getSuccessUsers",
		type: "GET",
		success: function(data) {
			let obj = JSON.parse(data);
			let myData3 = [];
			let myLabel3 = [];
			$.each(obj, function(k, v) {
				myLabel3.push(v.user_id);
				myData3.push(v.count);
			});
			let ctx = document.getElementById('myBarChart').getContext('2d');
			makeBarChart(ctx, 'bar', myLabel3, myData3);
		}
	});
}

function randomColor(labels) {
	let colors = [];
	for (let i = 0; i < labels.length; i++) {
		colors.push("#" + Math.round(Math.random() * 0xffffff).toString(16));
	}
	return colors;
}

function makeBarChart(ctx, type, label, data) {
	let myBarChart = new Chart(ctx, {
		type: type,
		data: {
			labels: label,
			datasets: [{
				label: 'count',
				data: data,
				backgroundColor: randomColor(label),
				borderColor: randomColor(label),
			}]
		},
		options: {
			maintainAspectRatio: false,
			scales: {
        yAxes: [{
            display: true,
            ticks: {
                suggestedMin: 0,    // minimum will be 0, unless there is a lower value.
                // OR //
                beginAtZero: true   // minimum value will be 0.
            }
        }]
    }
		}
	});
}

