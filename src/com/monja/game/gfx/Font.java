package com.monja.game.gfx;

public class Font {

    private static String chars = "" +
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ_     " +
            "0123456789!%$&'(.+/,-*)=?@:;<>| ";
    private static String fullChars = "" +
            "MNQTWXY" +
            "%&@";

    private static final int xBorderOffset = 20;
    private static final int yBorderOffset = 20;

    public static void render(String msg, Screen screen, int x, int y, int colour, int scale) {
        msg = msg.toUpperCase();
        int offset = 0;

        for (int i = 0; i < msg.length(); i++) {
            int charIndex = chars.indexOf(msg.charAt(i));
            if (charIndex >= 0) {
                screen.render(x + i * 8 + offset, y, charIndex + 30 * 32,
                        colour, 0x00, scale);
                if (fullChars.indexOf(msg.charAt(i)) >= 0) offset++;
            }
        }
    }

    public static int getxBorderOffset() {
        return xBorderOffset;
    }

    public static int getyBorderOffset() {
        return xBorderOffset;
    }
}
