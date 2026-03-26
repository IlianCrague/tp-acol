package fr.ensimag.tpacol;

import java.util.Arrays;

public class TerminalDisplay {
    private static final String ANSI_RESET = "\u001B[0m";
    private final String[][] buffer;

    public TerminalDisplay(int xSize, int ySize) {
        buffer = new String[ySize][xSize];
//        String charsetOut = System.out.charset().displayName();
//        if (!"UTF-8".equals(charsetOut)) {
//            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8));
//            System.out.println("Warning: Terminal output charset was not UTF-8, switched to UTF-8 for proper symbol rendering.");
//        }
    }

    /**
     * Calculates the true visual width of a string in the terminal,
     * automatically accounting for emojis taking up 2 spaces.
     */
    public static int getStringWidth(String string) {
        int width = 0;
        for (int i = 0; i < string.length(); ) {
            int ansiEnd = findAnsiEnd(string, i);
            if (ansiEnd != -1) {
                i = ansiEnd;
                continue;
            }

            int codePoint = string.codePointAt(i);

            // Most standard emojis and wide characters have a code point > 0xFFFF.
            // We assign them a visual width of 2 terminal cells.
            if (codePoint > 0xFFFF) {
                width += 2;
            } else {
                width += 1; // Standard characters take 1 cell
            }

            i += Character.charCount(codePoint);
        }
        return width;
    }

    private static int findAnsiEnd(String string, int startIndex) {
        if (startIndex + 2 >= string.length()) {
            return -1;
        }
        if (string.charAt(startIndex) != '\u001B' || string.charAt(startIndex + 1) != '[') {
            return -1;
        }

        int i = startIndex + 2;
        while (i < string.length()) {
            char c = string.charAt(i);
            if (c == 'm') {
                return i + 1;
            }
            if (!Character.isDigit(c) && c != ';') {
                return -1;
            }
            i++;
        }

        return -1;
    }

    private static String applyStyle(String symbol, String activeStyle) {
        if (activeStyle.isEmpty()) {
            return symbol;
        }
        return activeStyle + symbol + ANSI_RESET;
    }

    public void write(String string, int x, int y) {
        int currentX = x;
        String activeStyle = "";

        for (int i = 0; i < string.length(); ) {
            int ansiEnd = findAnsiEnd(string, i);
            if (ansiEnd != -1) {
                String ansiCode = string.substring(i, ansiEnd);
                if (ANSI_RESET.equals(ansiCode)) {
                    activeStyle = "";
                } else {
                    activeStyle += ansiCode;
                }
                i = ansiEnd;
                continue;
            }

            int codePoint = string.codePointAt(i);
            String symbol = new String(Character.toChars(codePoint));

            // 1. Get the visual width using our new static method
            int visualWidth = getStringWidth(symbol);

            if (currentX >= 0 && currentX < buffer[0].length && y >= 0 && y < buffer.length) {
                // 2. Place the symbol
                buffer[y][currentX] = applyStyle(symbol, activeStyle);

                // 3. Fill the extra visual footprint with empty strings
                for (int w = 1; w < visualWidth; w++) {
                    if (currentX + w < buffer[0].length) {
                        buffer[y][currentX + w] = "";
                    }
                }
            }

            // 4. Advance X and string index
            currentX += visualWidth;
            i += Character.charCount(codePoint);
        }
    }

    public void write(char c, int x, int y) {
        if (x >= 0 && x < buffer[0].length && y >= 0 && y < buffer.length) {
            buffer[y][x] = String.valueOf(c);
        }
    }

    public void draw_rectangle(int x, int y, int width, int height, boolean doubleEdges) {
        if (width <= 0 || height <= 0) return;

        char tl, tr, bl, br, h, v;

        if (doubleEdges) {
            tl = '╔';
            tr = '╗';
            bl = '╚';
            br = '╝';
            h = '═';
            v = '║';
        } else {
            tl = '┌';
            tr = '┐';
            bl = '└';
            br = '┘';
            h = '─';
            v = '│';
        }

        // Draw corners
        write(tl, x, y);
        write(tr, x + width - 1, y);
        write(bl, x, y + height - 1);
        write(br, x + width - 1, y + height - 1);

        // Draw horizontal lines
        for (int i = 1; i < width - 1; i++) {
            write(h, x + i, y);
            write(h, x + i, y + height - 1);
        }

        // Draw vertical lines
        for (int i = 1; i < height - 1; i++) {
            write(v, x, y + i);
            write(v, x + width - 1, y + i);
        }

        // Fill center with spaces
        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {
                write(' ', x + i, y + j);
            }
        }
    }

    public void draw_rectangle_centered(int width, int height, boolean doubleEdges) {
        int x = (buffer[0].length - width) / 2;
        int y = (buffer.length - height) / 2;
        draw_rectangle(x, y, width, height, doubleEdges);
    }

    public void render() {
        for (String[] row : buffer) {
            for (String cell : row) {
                System.out.print(cell != null ? cell : " ");
            }
            System.out.println();
        }
        clean();
    }

    public void clean() {
        for (String[] strings : buffer) {
            Arrays.fill(strings, null);
        }
    }

    // Small self-test to visually verify draw_rectangle variations.
    public static void main(String[] args) {
        TerminalDisplay td = new TerminalDisplay(60, 20);

        // Full single-edge outer box
        td.draw_rectangle(0, 0, 60, 20, false);

        // Double-edge small box
        td.draw_rectangle(2, 2, 16, 6, true);

        // Single-row
        td.draw_rectangle(22, 2, 12, 1, false);

        // Single-column
        td.draw_rectangle(40, 2, 1, 8, false);

        // 1x1
        td.draw_rectangle(30, 10, 1, 1, false);

        // Partially off-screen clipped box
        td.draw_rectangle(-2, 12, 8, 6, false);

        // non-merging box with cleared interior
        td.draw_rectangle(20, 10, 12, 6, false);

        td.render();
    }
}
