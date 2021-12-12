package forex.copytradingforex.model.entity.enums;

public enum CountryEnum {
    USA ("United States of America"),
    UK("United Kingdom of Great Britain and Northern Ireland"),
    JAPAN("Japan"),
    EU("European Union"),
    CANADA("Canada"),
    AUSTRALIA("Australia"),
    NZ("New Zealand"),
    NORWAY("Norway"),
    SWEDEN("Sweden"),
    SWITZERLAND("Switzerland");

    private  String country;

    CountryEnum(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }
}
