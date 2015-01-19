package com.icebreak.p2p.front.controller.trade.download;


public abstract class Text {

    public abstract String getText();

    public static Text str(final String string) {
        return new Text() {
            @Override
            public String getText() {
                return string;
            }
        };
    }
}
