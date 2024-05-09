package com.example.SaveFile;

public class size {
    public static double size() {
        String size = SaveSetting.loadOneSettingFromFile(14);
        if ("NORMAL".equals(size)){
            return 1.2;
        } else if ("BIG".equals(size)) {
            return 1.4;
        } else if ("SMALL".equals(size)) {
            return 1.0;
        }
        return 1.0;
    }
}
