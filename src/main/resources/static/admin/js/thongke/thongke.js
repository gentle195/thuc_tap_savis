let statisticChart = null; // Declare myChart as a global variable
function fetchDataChart(){
    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;
    const url = `/api/doanh-so/chart?startDate=${startDate}&endDate=${endDate}`;

    // Using the fetch() API to make a GET request
    return fetch(url)
        .then((response) => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        });
}
function fetchDataSale(){
    const startDate = document.getElementById('startDateData').value;
    const endDate = document.getElementById('endDateData').value;
    const url = `/api/doanh-so/data?startDate=${startDate}&endDate=${endDate}`;

    // Using the fetch() API to make a GET request
    return fetch(url)
        .then((response) => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        });
}
function updateChart() {
    if (statisticChart) {
        statisticChart.destroy();
    }
    fetchDataChart()
        .then((res) => {
            // Update the chart with the fetched data
            createChart(res);
        })
        .catch((error) => {
            console.error('Error fetching chart data:', error);
        });
}

function createSale(data) {
    if (data == null) return;
    const formatter = new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' , maximumFractionDigits: 3})
    const doanhSo = document.getElementById('doanhSo');
    const doanhSoNgay = document.getElementById('doanhSoNgay');
    const hangBanDuoc = document.getElementById('hangBanDuoc');
    doanhSo.innerText = data.donHangThang + ' đơn hàng / ' + formatter.format(data.doanhSoThang);
    doanhSoNgay.innerText = data.donHangNgay + ' đơn hàng / ' + formatter.format(data.doanhSoNgay);
    hangBanDuoc.innerText = data.hangBanDuocThang + ' chiếc';
}

function updateSale() {
    fetchDataSale()
        .then((res) => {
            // Update the chart with the fetched data
            createSale(res);
        })
        .catch((error) => {
            console.error('Error fetching chart data:', error);
        });
}

// Add event listeners to date inputs
const startDateInput = document.getElementById('startDate');
startDateInput.addEventListener('change', updateChart);
const endDateInput = document.getElementById('endDate');
endDateInput.addEventListener('change', updateChart);


const startDateSaleInput = document.getElementById('startDateData');
startDateSaleInput.addEventListener('change', updateSale);
const endDateSaleInput = document.getElementById('endDateData');
endDateSaleInput.addEventListener('change', updateSale);

window.onload = () => {
    const today = new Date();
    const thirtyDaysAgo = new Date();
    thirtyDaysAgo.setDate(today.getDate() - 30);
    const firstDay = new Date(today.getFullYear(), today.getMonth(), 2);

    const startDateInput = document.getElementById('startDate');
    const startDateDataInput = document.getElementById('startDateData');
    const endDateInput = document.getElementById('endDate');
    const endDateDataInput = document.getElementById('endDateData');
    startDateInput.valueAsDate = thirtyDaysAgo;
    startDateDataInput.valueAsDate = firstDay;
    endDateInput.valueAsDate = today;
    endDateDataInput.valueAsDate = today;
    updateChart();
    updateSale();
}
// Create the Bar Chart
function createChart(chartData) {
    const ctx = document.getElementById('myChart').getContext('2d');
    statisticChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: chartData.map(row => row.date),
            datasets: [{
                label: 'Doanh số',
                data: chartData.map(row => row.value),
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1,
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}
