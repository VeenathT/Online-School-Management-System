(function () {
  'use strict'

  feather.replace({ 'aria-hidden': 'true' })
  

  var myChart = new Chart(ctx, {
   
    
    options: {
      scales: {
        yAxes: [{
          ticks: {
            beginAtZero: false
          }
        }]
      },
      legend: {
        display: false
      }
    }
  })
})()

