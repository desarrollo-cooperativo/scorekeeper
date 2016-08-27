package com.transition.scorekeeper.mobile.model.dto;

import java.io.Serializable;

/**
 * @author diego.rotondale
 * @since 04/06/16
 */
public class InfoModel implements Serializable {
    private final int title;
    private final int message;
    private final int minValue;
    private final int maxValue;
    private final int actualValue;

    public InfoModel(InfoModelBuilder builder) {
        this.title = builder.title;
        this.message = builder.message;
        this.minValue = builder.minValue;
        this.maxValue = builder.maxValue;
        this.actualValue = builder.actualValue;
    }

    public int getTitle() {
        return title;
    }

    public int getMessage() {
        return message;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public int getActualValue() {
        return actualValue;
    }

    public static class InfoModelBuilder {
        public int title;
        public int message;
        public int minValue;
        public int maxValue;
        public int actualValue;

        public InfoModel build() {
            return new InfoModel(this);
        }

        public InfoModelBuilder setTitle(int title) {
            this.title = title;
            return this;
        }

        public InfoModelBuilder setMessage(int message) {
            this.message = message;
            return this;
        }

        public InfoModelBuilder setMinValue(int minValue) {
            this.minValue = minValue;
            return this;
        }

        public InfoModelBuilder setMaxValue(int maxValue) {
            this.maxValue = maxValue;
            return this;
        }

        public InfoModelBuilder setActualValue(int actualValue) {
            this.actualValue = actualValue;
            return this;
        }
    }
}
