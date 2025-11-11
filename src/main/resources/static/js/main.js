"use strict";

document.addEventListener('DOMContentLoaded', function () {
	// Ha nincs adat vagy nincs Chart, semmi dolgom
	if (!window.soapRates || !Array.isArray(window.soapRates) || window.soapRates.length === 0) return;
	if (typeof Chart === 'undefined') return;

	const labels = window.soapRates.map(r => r.date);
	const data = window.soapRates.map(r => {
		let v = (r.value || '').toString().trim();
		v = v.replace(',', '.'); // Kezeljük a tizedes vesszőt (pl. "372,45")
		const n = parseFloat(v);
		return isNaN(n) ? null : n;
	});

	const canvas = document.getElementById('ratesChart');
	if (!canvas) return;

	// Ha már volt korábbi chart, töröljük
	if (window._ratesChart) {
		try { window._ratesChart.destroy(); } catch (e) { /* ignore */ }
	}

	window._ratesChart = new Chart(canvas.getContext('2d'), {
		type: 'line',
		data: {
			labels: labels,
			datasets: [{
				label: (window.soapRates[0] && window.soapRates[0].curr) ? window.soapRates[0].curr : 'Árfolyam',
				data: data,
				borderColor: '#2563eb',
				backgroundColor: 'rgba(37,99,235,0.15)',
				tension: 0.25,
				fill: true,
				pointRadius: 3
			}]
		},
		options: {
			responsive: true,
			plugins: {
				legend: { display: true }
			},
			scales: {
				y: {
					beginAtZero: false,
					ticks: { callback: function(v){ return v; } }
				}
			}
		}
	});
});
