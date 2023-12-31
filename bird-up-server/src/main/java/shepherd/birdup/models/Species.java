package shepherd.birdup.models;

import java.util.Objects;

public class Species {
    private int speciesId;
    // read only and autogenerated all
    private String speciesShortName;
    private String speciesLongName;

    public Species() {
    }

    public Species(int speciesId, String speciesShortName, String speciesLongName) {
        this.speciesId = speciesId;
        this.speciesShortName = speciesShortName;
        this.speciesLongName = speciesLongName;
    }

    public int getSpeciesId() {
        return speciesId;
    }

    public void setSpeciesId(int speciesId) {
        this.speciesId = speciesId;
    }

    public String getSpeciesShortName() {
        return speciesShortName;
    }

    public void setSpeciesShortName(String speciesShortName) {
        this.speciesShortName = speciesShortName;
    }

    public String getSpeciesLongName() {
        return speciesLongName;
    }

    public void setSpeciesLongName(String speciesLongName) {
        this.speciesLongName = speciesLongName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Species species = (Species) o;
        return speciesId == species.speciesId && Objects.equals(speciesShortName, species.speciesShortName) && Objects.equals(speciesLongName, species.speciesLongName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(speciesId, speciesShortName, speciesLongName);
    }

    @Override
    public String toString() {
        return "Species{" +
                "speciesId=" + speciesId +
                ", speciesShortName='" + speciesShortName + '\'' +
                ", speciesLongName='" + speciesLongName + '\'' +
                '}';
    }
}
