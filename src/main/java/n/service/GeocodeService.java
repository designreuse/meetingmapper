package n.service;


import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.GeocoderStatus;
import n.dto.Location;
import n.persistence.LocationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GeocodeService {

    @Autowired
    private LocationDao locationDao;

    @Transactional
    public Location geocodeLocation(final Location location) {
        try {
            //need to sleep to avoid exceeding google usage limit
            Thread.sleep(100);
            final Geocoder geocoder = new Geocoder();
            final GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(location.getAddressString()).setLanguage("en").getGeocoderRequest();
            final GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
            if (geocoderResponse.getStatus().equals(GeocoderStatus.OK) && geocoderResponse.getResults() != null && !geocoderResponse.getResults().isEmpty()) {
                final GeocoderResult geocoderResult = geocoderResponse.getResults().get(0);
                location.setLatitude(geocoderResult.getGeometry().getLocation().getLat().toString());
                location.setLongitude(geocoderResult.getGeometry().getLocation().getLng().toString());
                locationDao.update(location);
                return location;
            } else {
                //todo: log bad response from google
                System.out.println("bad response from Google geocoder");
            }
        } catch (Exception e) {
            //todo: log exection
            System.out.println("exception from Google geocoder");
            e.printStackTrace();
        }
        return null;
    }


}
