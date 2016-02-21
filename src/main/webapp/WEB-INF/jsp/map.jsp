<!DOCTYPE html>
<html>
<head>
    <title>Raleigh AA Meeting Map</title>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
    <style type="text/css">
        html {
            height: 100%
        }

        body {
            height: 100%;
            margin: 0;
            padding: 0
        }

        #map-canvas {
            height: 100%
        }
    </style>
    <script type="text/javascript"
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBlbFAo2J8f6f3ziny08UA0uAdsOvyrwwc&sensor=false">
    </script>
    <script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
    <script type="text/javascript">
        function initialize() {

            var latRaleigh = 35.7795897;
            var lonRaleigh = -78.6381787;
            var mapOptions = {
                center: new google.maps.LatLng(latRaleigh, lonRaleigh),
                zoom: 12,
                scaleControl: true
            };
            var map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);

            var url = 'http://meetingmapper.herokuapp.com/locations';
//            var url = 'http://localhost:8080/meetingmapper/locations';

            $.getJSON(url, function (data) {
                $.each(data, function (locationId, location) {
                    var meetingLatLng = new google.maps.LatLng(location.latitude, location.longitude);
                    var marker = new google.maps.Marker({
                        position: meetingLatLng,
                        map: map,
                        title: location.name + ', ' + location.address
                    });

                    var contentString = location.name + '<br/>' +
                            location.address + ', ' + location.city + '<br/><br/>';
                    $.each(location.meetings, function (meetingId, meeting) {
                        contentString += meeting.day + ', ' + meeting.time + ', ' + meeting.name + '<br/>';
                    });

                    var infowindow = new google.maps.InfoWindow({
                        content: contentString
                    });
                    google.maps.event.addListener(marker, 'click', function () {
                        infowindow.open(map, marker);
                    });
                });
            });
        }
        google.maps.event.addDomListener(window, 'load', initialize);
    </script>
</head>
<body>
<div id="map-canvas"/>
</body>
</html>
