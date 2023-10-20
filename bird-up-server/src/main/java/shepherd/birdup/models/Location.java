package shepherd.birdup.models;

import shepherd.birdup.validation.PostalCode;
import shepherd.birdup.validation.StateExists;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Location {

    private int locationId;
    @NotNull
    @NotEmpty
    @Size(max = 250)
    private String city;

    @StateExists
    private State state;
    @Size(max = 5, min = 5)
    @PostalCode
    private String postalCode;

    public Location() {
    }

    public Location(int locationId, String city, State state, String postalCode) {
        this.locationId = locationId;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return locationId == location.locationId && Objects.equals(postalCode, location.postalCode) && Objects.equals(city, location.city) && Objects.equals(state, location.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationId, city, state, postalCode);
    }

    @Override
    public String toString() {
        return "Location{" +
                "locationId=" + locationId +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postalCode=" + postalCode +
                '}';
    }
}
