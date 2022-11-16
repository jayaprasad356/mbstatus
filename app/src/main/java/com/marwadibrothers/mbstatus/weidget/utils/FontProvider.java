package com.marwadibrothers.mbstatus.weidget.utils;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * extracting Typeface from Assets is a heavy operation,
 * we want to make sure that we cache the typefaces for reuse
 */
public class FontProvider {

    private static final String DEFAULT_FONT_NAME = "Normal";

    private final Map<String, Typeface> typefaces;
    private final Map<String, String> fontNameToTypefaceFile;
    private final Resources resources;
    private final List<String> fontNames;

    public FontProvider(Resources resources) {
        this.resources = resources;

        typefaces = new HashMap<>();

        // populate fonts
        fontNameToTypefaceFile = new HashMap<>();
        fontNameToTypefaceFile.put("Normal", "Arial.ttf");
        fontNameToTypefaceFile.put("Ajay Normal Bold", "Ajay Normal Bold.ttf");
        fontNameToTypefaceFile.put("Akshar Unicode", "Akshar Unicode.ttf");
        fontNameToTypefaceFile.put("Chandra Head Regular", "Chandra Head Regular.ttf");
        fontNameToTypefaceFile.put("Halant-Bold", "Halant-Bold.ttf");
        fontNameToTypefaceFile.put("Kalam-Bold", "Kalam-Bold.ttf");
        fontNameToTypefaceFile.put("Kalam-Light", "Kalam-Light.ttf");
        fontNameToTypefaceFile.put("Karma-Bold", "Karma-Bold.ttf");
        fontNameToTypefaceFile.put("Kokila", "Kokila.ttf");
        fontNameToTypefaceFile.put("Laila-Bold", "Laila-Bold.ttf");
        fontNameToTypefaceFile.put("Laila-Medium", "Laila-Medium.ttf");
        fontNameToTypefaceFile.put("lohit_ne", "lohit_ne.ttf");
        fontNameToTypefaceFile.put("Poppins-Light", "Poppins-Light.ttf");
        fontNameToTypefaceFile.put("Poppins-SemiBold", "Poppins-SemiBold.ttf");
        fontNameToTypefaceFile.put("Poppins-Thin", "Poppins-Thin.ttf");
        fontNameToTypefaceFile.put("Samyak-Devanagari", "Samyak-Devanagari.ttf");
        fontNameToTypefaceFile.put("Sanskrit2003", "Sanskrit2003.ttf");
        fontNameToTypefaceFile.put("Sarai_07", "Sarai_07.ttf");

        fontNames = new ArrayList<>(fontNameToTypefaceFile.keySet());
    }

    /**
     * @param typefaceName must be one of the font names provided from {@link FontProvider#getFontNames()}
     * @return the Typeface associated with {@code typefaceName}, or {@link Typeface#DEFAULT} otherwise
     */
    public Typeface getTypeface(@Nullable String typefaceName) {
        Log.e("typefaceName", typefaceName);
        if (TextUtils.isEmpty(typefaceName)) {
            return Typeface.DEFAULT;
        } else {
            //noinspection Java8CollectionsApi
            if (typefaces.get(typefaceName) == null) {
                typefaces.put(typefaceName,
                        Typeface.createFromAsset(resources.getAssets(), "fonts/" + fontNameToTypefaceFile.get(typefaceName)));
            }
            return typefaces.get(typefaceName);
        }
    }

    /**
     * use {@link FontProvider#getTypeface(String) to get Typeface for the font name}
     *
     * @return list of available font names
     */
    public List<String> getFontNames() {
        return fontNames;
    }

    /**
     * @return Default Font Name - <b>Helvetica</b>
     */
    public String getDefaultFontName() {
        return DEFAULT_FONT_NAME;
    }
}