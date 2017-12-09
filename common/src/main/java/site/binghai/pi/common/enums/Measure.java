package site.binghai.pi.common.enums;

/**
 * Created by IceSea on 2017/12/9.
 * 通用度量
 * GitHub: https://github.com/IceSeaOnly
 */
public enum Measure {
    VERY_LOW(1),
    LESS(3),
    NORMAL(5),
    MORE(7),
    VERY(9),;

    private int value;

    Measure(int value) {
        this.value = value;
    }

    public String getValue() {
        return String.valueOf(value);
    }
}
