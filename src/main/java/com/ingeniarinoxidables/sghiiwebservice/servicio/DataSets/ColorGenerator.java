package com.ingeniarinoxidables.sghiiwebservice.servicio.DataSets;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ColorGenerator {

    public ColorGenerator() {
    }

    public List<String> generateRGBAColors(int numColors) {
        List<String> colors = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numColors; i++) {

            float red = random.nextFloat();
            float green = random.nextFloat();
            float blue = random.nextFloat();
            float alpha = random.nextFloat();

            Color color = new Color(red, green, blue, alpha);

            String rgba = String.format(Locale.US,"rgba(%d,%d,%d,%.2f)",
                    color.getRed(),
                    color.getGreen(),
                    color.getBlue(),
                    color.getAlpha() / 255.0);

            colors.add(rgba);
        }

        return colors;
    }

}

