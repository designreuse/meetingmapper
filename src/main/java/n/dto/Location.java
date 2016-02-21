package n.dto;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Location extends BaseDto {
    private String name;
    private String address;
    private String city;
    private String latitude;
    private String longitude;
    @OneToMany(mappedBy = "location", fetch= FetchType.EAGER)
    private List<Meeting> meetings;

    public String getAddressString() {

        StringBuilder addressLine = new StringBuilder();
        addressLine.append(address);
        if (city != null) {
            addressLine.append(",");
            addressLine.append(city);
        }
        addressLine.append(",");
        addressLine.append("NC");
        return addressLine.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }
}
