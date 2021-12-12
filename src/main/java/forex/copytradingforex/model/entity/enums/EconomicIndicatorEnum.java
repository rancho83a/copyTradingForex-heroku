package forex.copytradingforex.model.entity.enums;

public enum EconomicIndicatorEnum {
    CPI("Consumer Price Index"),
    GDP("Gross domestic product"),
    INTEREST_RATE("Interest Rate Decision"),
    NFP("Non-farm Payrolls"),
    NBRNS("Norges Bank regional network Survey"),
    SPEECH("Press-Conference");

    private String name;

    EconomicIndicatorEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
