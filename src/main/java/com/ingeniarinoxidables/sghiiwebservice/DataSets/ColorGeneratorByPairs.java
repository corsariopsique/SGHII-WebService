package com.ingeniarinoxidables.sghiiwebservice.DataSets;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ColorGeneratorByPairs {

    public ColorGeneratorByPairs() {
    }

    public List<ColorPair> generateColorPairs(int numPairs) {
        List<ColorPair> colorPairs = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numPairs; i++) {

            float red = random.nextFloat();
            float green = random.nextFloat();
            float blue = random.nextFloat();


            Color borderColor = new Color(red, green, blue, 1.0f);

            Color backgroundColor = new Color(red, green, blue, 0.5f);

            colorPairs.add(new ColorPair(borderColor, backgroundColor));
        }

        return colorPairs;
    }

    public static class ColorPair {
        private final Color borderColor;
        private final Color backgroundColor;

        public ColorPair(Color borderColor, Color backgroundColor) {
            this.borderColor = borderColor;
            this.backgroundColor = backgroundColor;
        }

        public String getBorderColorRGBA() {
            return String.format(Locale.US, "rgba(%d,%d,%d,%.2f)",
                    borderColor.getRed(),
                    borderColor.getGreen(),
                    borderColor.getBlue(),
                    borderColor.getAlpha() / 255.0);
        }

        public String getBackgroundColorRGBA() {
            return String.format(Locale.US, "rgba(%d,%d,%d,%.2f)",
                    backgroundColor.getRed(),
                    backgroundColor.getGreen(),
                    backgroundColor.getBlue(),
                    backgroundColor.getAlpha() / 255.0);
        }
    }
}

