package forex.copytradingforex.model.service;

public class PictureAddServiceModel {
    private String title;


    private String url;


    private String publicId;


    private String traderUsername;

    private Long positionId;


    public String getTitle() {
        return title;
    }

    public PictureAddServiceModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public PictureAddServiceModel setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getPublicId() {
        return publicId;
    }

    public PictureAddServiceModel setPublicId(String publicId) {
        this.publicId = publicId;
        return this;
    }

    public String getTraderUsername() {
        return traderUsername;
    }

    public PictureAddServiceModel setTraderUsername(String traderUsername) {
        this.traderUsername = traderUsername;
        return this;
    }

    public Long getPositionId() {
        return positionId;
    }

    public PictureAddServiceModel setPositionId(Long positionId) {
        this.positionId = positionId;
        return this;
    }
}
