package com.marwadibrothers.mbstatus.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WatermarkData {

    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("watermark_text")
    @Expose
    public String watermark_text;

    @SerializedName("watermark_image")
    @Expose
    public String watermark_image;

    @SerializedName("watermark_position")
    @Expose
    public String watermark_position;

    @SerializedName("watermark_opacity")
    @Expose
    public String watermark_opacity;

    @SerializedName("watermark_status")
    @Expose
    public String watermark_status;
    @SerializedName("font_color")
    @Expose
    public String font_color;
    @SerializedName("font_size")
    @Expose
    public String font_size;
    @SerializedName("font_style")
    @Expose
    public String font_style;

    @SerializedName("font_family")
    @Expose
    public String font_family;

    @SerializedName("margin")
    @Expose
    public String margin;

    public String getMargin() {
        return margin;
    }

    public void setMargin(String margin) {
        this.margin = margin;
    }

    public String getFont_color() {
        return font_color;
    }

    public void setFont_color(String font_color) {
        this.font_color = font_color;
    }

    public String getFont_size() {
        return font_size;
    }

    public void setFont_size(String font_size) {
        this.font_size = font_size;
    }

    public String getFont_style() {
        return font_style;
    }

    public void setFont_style(String font_style) {
        this.font_style = font_style;
    }

    public String getFont_family() {
        return font_family;
    }

    public void setFont_family(String font_family) {
        this.font_family = font_family;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWatermark_text() {
        return watermark_text;
    }

    public void setWatermark_text(String watermark_text) {
        this.watermark_text = watermark_text;
    }

    public String getWatermark_image() {
        return watermark_image;
    }

    public void setWatermark_image(String watermark_image) {
        this.watermark_image = watermark_image;
    }

    public String getWatermark_position() {
        return watermark_position;
    }

    public void setWatermark_position(String watermark_position) {
        this.watermark_position = watermark_position;
    }

    public String getWatermark_opacity() {
        return watermark_opacity;
    }

    public void setWatermark_opacity(String watermark_opacity) {
        this.watermark_opacity = watermark_opacity;
    }

    public String getWatermark_status() {
        return watermark_status;
    }

    public void setWatermark_status(String watermark_status) {
        this.watermark_status = watermark_status;
    }
}
