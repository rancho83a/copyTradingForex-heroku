package forex.copytradingforex.model.entity.enums;

public enum CentralBankEnum {
    FED("Federal Reserve System"),
    BOE("Bank of England"),
    BOJ("Bank of Japan"),
    BOC("Bank of Canada"),
    ECB("European Central Bank"),
    CBN("Central bank of Norway"),
    SRB("Swedish Royal Bank"),
    SNB("Swiss National Bank"),
    RBA("Reserve Bank of Australia"),
    RBNZ("Reserve Bank of New Zealand");

    private String centralBank;

    CentralBankEnum(String centralBank) {
        this.centralBank = centralBank;
    }

    public String getCentralBank() {
        return centralBank;
    }
}
