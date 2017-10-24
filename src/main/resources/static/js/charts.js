var chartData;
var chart;

var numPage = localStorage.getItem('page');
if (numPage === null) {
    numPage = 0;
}
localStorage.setItem('page', numPage);

var allPoints = localStorage.getItem('allPoints');
if (allPoints === null) {
    allPoints = 0;
}
localStorage.setItem('allPoints', allPoints);

// var numPage = 2;
// var allPoints = 10000;

newCharts();

function newCharts() {
    $("#numPage").val(numPage);
    $("#allPoints").val(allPoints);

    // if (numPage === 0) startTimer();
    chartData = generateChartData();

    chart = AmCharts.makeChart("chartdiv", {
        "type": "serial",
        "theme": "light",
        "fontSize": 15,
        "mouseWheelZoomEnabled": true,
        "autoMarginOffset": 5,
        "legend": {
            "useGraphSettings": true,
            "valueAlign": "right",
            "position": "right"
        },
        "dataProvider": chartData,
        "synchronizeGrid": false, //автомасштабирование по вертикали
        "titles": [
            {
                "id": "Title-1",
                "size": 20,
                "text": "Автоклав №1"
            }
        ],
        //шкалы графиков
        "valueAxes": [{
            "id": "v1",
            "axisColor": "#ff0302",
            "axisThickness": 2,
            "axisAlpha": 1,
            "position": "left",
            "title": "Температура",
            "fontSize": 12,
            "titleFontSize": 12,
            "maximum": 150,
            "minimum": 0
        }, {
            "id": "v2",
            "axisColor": "#0a0ffc",
            "axisThickness": 2,
            "axisAlpha": 1,
            "offset": 0,
            "position": "right",
            "title": "Давление в автоклаве",
            "fontSize": 12,
            "titleFontSize": 12,
            "precision": 2, //кол-во знаков после запятой
            "maximum": 2,
            "minimum": 0
        }, {
            "id": "v3",
            "axisColor": "#15de0f",
            "axisThickness": 2,
            "gridAlpha": 0,
            "offset": 60,
            "axisAlpha": 1,
            "position": "left",
            "title": "Уровень воды в автоклаве",
            "fontSize": 12,
            "titleFontSize": 12,
            "maximum": 2,
            "minimum": 0
        }],
        //графики
        "graphs": [{
            "id": "v1",
            "valueAxis": "v1",
            "balloonText": "[[title]]: [[value]]\n[[date]]", //формат всплывающей подсказки
            "lineColor": "#ff0302",
            "bullet": "round",
            "bulletBorderThickness": 1,
            "hideBulletsCount": 30,
            "title": "T,грС",
            "valueField": "t1",
            "fillAlphas": 0
        }, {
            "valueAxis": "v2",
            "lineColor": "#0a0ffc",
            "balloonText": "[[title]]: [[value]]\n[[date]]",
            "bullet": "round",
            "bulletBorderThickness": 1,
            "hideBulletsCount": 30,
            "title": "Р,атм",
            "valueField": "p3",
            "fillAlphas": 0
        }, {
            "valueAxis": "v3",
            "lineColor": "#15de0f",
            "balloonText": "[[title]]: [[value]]\n[[date]]",
            "bullet": "round",
            "bulletBorderThickness": 1,
            "hideBulletsCount": 30,
            "title": "L,мм",
            "valueField": "p1",
            "fillAlphas": 0,
            "hidden": true
        }],
        //шкала истории
        "chartScrollbar": {
            "graph": "v1",
            "oppositeAxis": false,
            "offset": 50,
            "scrollbarHeight": 80,
            "backgroundAlpha": 0,
            "selectedBackgroundAlpha": 0.3,
            "selectedBackgroundColor": "#888888",
            "graphFillAlpha": 0.6,
            "graphLineAlpha": 0.5,
            "selectedGraphFillAlpha": 0,
            "selectedGraphLineAlpha": 1,
            "autoGridCount": true,
            "color": "#AAAAAA",
            "fontSize": 10
        },
        //горизонтальный визир
        "chartCursor": {
            "pan": true,
            "valueLineEnabled": true,
            "valueLineBalloonEnabled": true,
            "cursorAlpha": 1,
            "cursorColor": "#258cbb",
            "limitToGraph": "g1",
            "valueLineAlpha": 0.2,
            "valueZoomable": true,
            "categoryBalloonDateFormat": "YYYY-MM-DD JJ:NN:SS"
        },
        "categoryField": "date",
        "categoryAxis": {
            "parseDates": true,
            "minPeriod": "ss",
            "axisColor": "#DADADA",
            "minorGridEnabled": true,
            "minorTickLength": 1,
            "autoRotateCount": 0,
            "autoRotateAngle": 90,
            "gridCount": 50,
            "autoGridCount": false,
            "fontSize": 10
        },
        "dataDateFormat": "YYYY-MM-DD HH:NN:SS",
        //шкала вертикального масштабирования
        "valueScrollbar": {
            "oppositeAxis": false,
            "offset": 0,
            "scrollbarHeight": 5
        },
        "export": {
            "enabled": true,
            "position": "bottom-right"
        }
    });
    updateDelay();

    chart.addListener("dataUpdated", zoomChart);
    zoomChart();

    var num = 400;

    function zoomChart() {
        $("#numPoints").keyup(function (key) {
            if (key.which === 13) {
                num = $(this).val();
                updateDelay();
            }
        });
        $("#numPage").keyup(function (key) {
            if (key.which === 13) {
                numPage = $(this).val();
                localStorage.setItem('page', numPage);
                updateDelay();
            }
        });
        $("#allPoints").keyup(function (key) {
            if (key.which === 13) {
                allPoints = $(this).val();
                localStorage.setItem('allPoints', allPoints);
                window.location.reload();
                updateDelay();
            }
        });
        chart.zoomToIndexes(chart.dataProvider.length - num, chart.dataProvider.length - 1);
    }
}

function updateDelay() {
    setTimeout(updatePage, 1000);
}


var timer;
var timerOn;

function trigTimer() {
    if (timerOn === null) timerOn = false;
    if (timerOn === true) {
        stopTimer();
        $("#onlineBtn").html("Режим реального времени выключен");
    } else {
        startTimer();
        $("#onlineBtn").html("Режим реального времени включен");
    }
}

function startTimer() {
    timerOn = true;
    timer = setInterval(update, 1000);
}

function stopTimer() {
    timerOn = false;
    clearInterval(timer);
}

function next() {
    stopTimer();
    numPage--;
    if (numPage < 0) {
        numPage = 0;
    }
    newCharts();
    localStorage.setItem('page', numPage);
    // window.location.reload();
    updateDelay();
}

function prev() {
    stopTimer();
    numPage++;
    newCharts();
    localStorage.setItem('page', numPage);
    // window.location.reload();
    updateDelay();
}


function update() {
    $.get("/charts/data/online/" + "1",
        function (point) {
            chartData.push({
                date: point.date,
                t1: point.t1 !== null ? point.t1 : 0.0,
                p1: point.p1 !== null ? point.p1 : 0.0,
                p2: point.p2 !== null ? point.p2 : 0.0,
                p3: point.p3 !== null ? point.p3 : 0.0
            });
            chart.validateData();
        }, "json");
}

function updatePage() {
    chart.validateData();
}

function generateChartData() {
    var result = [];
    $.get("/charts/data/" + "1" + "?count=" + allPoints + "&page=" + numPage,
        function (data) {
            if (data.length === 0) alert("Нет данных");

            $.each(data, function (index, point) {
                result.push({
                    date: point.date,
                    t1: point.t1 !== null ? point.t1 : 0.0,
                    p1: point.p1 !== null ? point.p1 : 0.0,
                    p2: point.p2 !== null ? point.p2 : 0.0,
                    p3: point.p3 !== null ? point.p3 : 0.0
                });
            });

        }, "json");
    return result;
}