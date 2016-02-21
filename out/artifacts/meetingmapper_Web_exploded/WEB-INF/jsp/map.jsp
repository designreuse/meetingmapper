<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <style type="text/css">
        html { height: 100% }
        body { height: 100%; margin: 0; padding: 0 }
        #map-canvas { height: 100% }
    </style>
    <script type="text/javascript"
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBlbFAo2J8f6f3ziny08UA0uAdsOvyrwwc&sensor=false">
    </script>
    <script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
    <script type="text/javascript">
        function initialize() {
            var lat = 35.7795897;
            var lon = -78.6381787;

            //$.getJSON(
            //	"http://maps.googleapis.com/maps/api/geocode/json",
            //	{
            //		address: "Raleight,NC",
            //		sensor: false,
            //		callback: "?"
            //	},
            //	function(data, textStatus){
            //		lat = data.results[0].geometry.location.lat;
            //		lon = data.results[0].geometry.location.lng;
            //		console.log(lat+","+lon);
            //	}
            //);

            var mapOptions = {
                center: new google.maps.LatLng(lat,lon),
                zoom: 12
            };
            var map = new google.maps.Map(document.getElementById("map-canvas"),
                    mapOptions);
        }
        google.maps.event.addDomListener(window, 'load', initialize);
    </script>
</head>
<body>
<div id="map-canvas"/>
</body>
<!-- http://maps.googleapis.com/maps/api/geocode/json?address=Raleigh,NC&sensor=false -->
</html>
