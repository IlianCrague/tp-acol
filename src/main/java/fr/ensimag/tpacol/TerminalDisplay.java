package fr.ensimag.tpacol;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

public class TerminalDisplay {
    private final String[][] buffer;
    @Getter
    @Setter
    @NonNull
    private Input input = Input.EMPTY;

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

    public void write(String string, int x, int y) {
        int currentX = x;

        for (int i = 0; i < string.length(); ) {
            int codePoint = string.codePointAt(i);
            String symbol = new String(Character.toChars(codePoint));

            // 1. Get the visual width using our new static method
            int visualWidth = getStringWidth(symbol);

            if (currentX >= 0 && currentX < buffer[0].length && y >= 0 && y < buffer.length) {
                // 2. Place the symbol
                buffer[y][currentX] = symbol;

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

    public static class Input {
        private static final Scanner SCANNER = new Scanner(System.in);
        private final String prompt;
        private final Function<String, String> predicate;

        /**
         * @param predicate A callback function that takes the raw user input and returns a string error message if the
         *                  input is invalid, or null if the input is valid.
         */
        public Input(String prompt, Function<String, String> predicate) {
            // prompts should not be multiline to avoid shifting the printed game
            if (prompt != null && prompt.contains("\n")) {
                throw new IllegalArgumentException("Prompt cannot contain newline characters.");
            }
            this.prompt = prompt;
            this.predicate = predicate;
        }

        /**
         * Prints the prompt to the terminal and waits for user input. Validates the input using the provided
         * callback and returns the corresponding value of type T if valid.
         */
        public void prompt() {
            if (prompt == null && predicate == null) {
                System.out.println("> Press Enter to continue...");
                readLine();
                return;
            }

            while (true) {
                if (prompt != null) {
                    System.out.print("> " + prompt + " ");
                }
                String userInput = readLine();
                if (userInput == null) {
                    System.err.println("No input available (EOF). Skipping prompt.");
                    return;
                }
                if (predicate == null) return;

                String errorMessage = predicate.apply(userInput);
                if (errorMessage == null) return; // Input is valid
                System.err.println("Invalid input: " + errorMessage);
            }
        }

        private static String readLine() {
            if (System.console() != null) {
                String line = System.console().readLine();
                return line;
            }
            if (SCANNER.hasNextLine()) {
                return SCANNER.nextLine();
            }
            return null;
        }

        /**
         * Creates an Input instance for validating integer inputs within a specified range.
         */
        public static Input integer(String prompt, int min, int max, Consumer<Integer> onValid) {
            return new Input(prompt, input -> {
                try {
                    int value = Integer.parseInt(input);
                    if (value < min || value > max) {
                        return "Input must be an integer between " + min + " and " + max + ".";
                    }
                    onValid.accept(value);
                    return null; // Valid input
                } catch (NumberFormatException e) {
                    return e.getMessage();
                }
            });
        }

        /**
         * Creates an Input instance for validating non-empty string inputs with a maximum length.
         */
        public static Input nonEmptyString(String prompt, int maxLength, Consumer<String> onValid) {
            return new Input(prompt, input -> {
                if (input.trim().isEmpty()) return "Input cannot be empty.";
                if (input.length() > maxLength) return "Input cannot exceed " + maxLength + " characters.";
                onValid.accept(input);
                return null; // Valid input
            });
        }

        /**
         * Creates an Input instance for validating yes/no inputs, returning true for "yes" and false for "no".
         */
        public static Input yesNo(String prompt, Consumer<Boolean> onValid) {
            return new Input(prompt + " (y/n)", input -> {
                String normalized = input.trim().toLowerCase();
                if (normalized.equals("yes") || normalized.equals("y")) {
                    onValid.accept(true);
                    return null; // Valid input
                } else if (normalized.equals("no") || normalized.equals("n")) {
                    onValid.accept(false);
                    return null; // Valid input
                } else {
                    return "Input must be 'yes' or 'no'.";
                }
            });
        }

        /**
         * A special Input instance that simply waits for the user to press Enter without validating any input.
         * This can be used for "Press Enter to continue..." prompts where no actual input is required.
         */
        public static Input EMPTY = new Input(null, null);
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
