<!DOCTYPE HTML>
<html >
<head>
	<title>测试</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<script src="https://img.hcharts.cn/jquery/jquery-1.8.3.min.js"></script>
	<script src="https://img.hcharts.cn/highcharts/highcharts.js"></script>
</head>
<body>

SpringMVcweb  jsp 测试

<div id="container"></div>
<button id="button" class="autocompare">新增点</button>
<script>
    $(function() {
        var chart = Highcharts.chart('container', {
            series: [{
                data: [29.9, 71.5]
            }]
        });
        // the button action
        var i = 0;
        $('#button').click(function() {
            chart.series[0].addPoint(50 * (i % 3));
            chart.series[1].addPoint(50 * (i % 3));
        });
    });
</script>
</body>
</html>