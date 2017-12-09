package site.binghai.pi.common.enums;

/**
 * Created by IceSea on 2017/12/9.
 * GitHub: https://github.com/IceSeaOnly
 */
public enum SpeakLanguage {

    CHINESE("zh"),
    ENGLISH("en"),
    ;
    private String language;

    SpeakLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }
}
