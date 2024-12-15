package br.com.actionsys.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CodeUtil {

    private static final Map<String, String> CODE_MAP = new HashMap<>();

    static {
        CODE_MAP.put("002", "RB_130_SC");
        CODE_MAP.put("003", "RB_340_SC");
        CODE_MAP.put("004", "RB_280_SC");
        CODE_MAP.put("005", "RB_190_SC");
        CODE_MAP.put("006", "RB_120_SC");
        CODE_MAP.put("008", "RB_300_SC");
        CODE_MAP.put("009", "RB_115_SC");
        CODE_MAP.put("014", "RB_220_SC");
        CODE_MAP.put("016", "RB_290_SC");
        CODE_MAP.put("018", "RB_240_SC");
        CODE_MAP.put("019", "RB_360_SC");
        CODE_MAP.put("021", "RB_150_SC");
        CODE_MAP.put("022", "RB_360_SC");
        CODE_MAP.put("024", "RB_360_SC");
        CODE_MAP.put("026", "RB_270_SC");
        CODE_MAP.put("028", "RB_010_SC");
        CODE_MAP.put("033", "RB_115_SC");
        CODE_MAP.put("034", "RB_070_SC");
        CODE_MAP.put("044", "RB_180_SC");
        CODE_MAP.put("045", "RB_060_SC");
        CODE_MAP.put("047", "RB_150_SC");
        CODE_MAP.put("051", "RB_230_SC");
        CODE_MAP.put("053", "RB_115_SC");
        CODE_MAP.put("058", "RB_330_SC");
        CODE_MAP.put("069", "RB_410_SC");
        CODE_MAP.put("070", "RB_390_SC");
        CODE_MAP.put("071", "RB_080_SC");
        CODE_MAP.put("076", "RB_170_SC");
        CODE_MAP.put("078", "RB_050_SC");
        CODE_MAP.put("082", "RB_400_SC");
        CODE_MAP.put("084", "RB_380_SC");
        CODE_MAP.put("085", "RB_030_SC");
        CODE_MAP.put("091", "RB_140_SC");
        CODE_MAP.put("092", "RB_010_SC");
        CODE_MAP.put("101", "RB_330_SC");
        CODE_MAP.put("103", "RB_260_SC");
        CODE_MAP.put("104", "RB_335_SC");
        CODE_MAP.put("112", "RB_340_SC");
        CODE_MAP.put("140", "RB_010_SC");
        CODE_MAP.put("142", "RB_010_SC");
        CODE_MAP.put("990", "RB_350_SC");
        CODE_MAP.put("991", "RB_015_SC");
        CODE_MAP.put("992", "RB_335_SC");
        CODE_MAP.put("993", "RB_331_SC");
        CODE_MAP.put("994", "RB_260_SC");
        CODE_MAP.put("995", "RB_054_SC");
        CODE_MAP.put("996", "RB_056_SC");
        CODE_MAP.put("997", "RB_380_SC");
        CODE_MAP.put("998", "RB_195_SC");
    }

    public String transformCode(String inputCode) {
        return CODE_MAP.getOrDefault(inputCode, "Código desconhecido");
    }
}
