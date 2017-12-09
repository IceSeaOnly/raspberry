package site.binghai.pi.common.enums;

/**
 * Created by IceSea on 2017/12/9.
 * GitHub: https://github.com/IceSeaOnly
 */
public enum SpeakerEnum {
    WOMAN(0),
    MAN(1),
    EMOTIONAL_WOMAN(2),
    EMOTIONAL_MAN(3),
    ;

    private int id;

    SpeakerEnum(int id) {
        this.id = id;
    }

    public String getId() {
        return String.valueOf(id);
    }
}
