package fr.ensimag.tpacol;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TerminalDisplayAnsiTest {

    @Test
    void getStringWidthIgnoresAnsiSequences() {
        String red = "\u001B[31m";
        String reset = "\u001B[0m";

        assertEquals(3, TerminalDisplay.getStringWidth(red + "abc" + reset));
        assertEquals(4, TerminalDisplay.getStringWidth(red + "a" + reset + "bcd"));
        assertEquals(2, TerminalDisplay.getStringWidth(red + "🚀" + reset));
    }

    @Test
    void writePlacesAnsiColoredSymbolsAtExpectedColumns() throws Exception {
        TerminalDisplay display = new TerminalDisplay(5, 1);
        display.write("\u001B[31mA\u001B[0mB", 0, 0);

        Field bufferField = TerminalDisplay.class.getDeclaredField("buffer");
        bufferField.setAccessible(true);
        String[][] buffer = (String[][]) bufferField.get(display);

        assertTrue(buffer[0][0].contains("A"));
        assertEquals("B", buffer[0][1]);
    }
}

