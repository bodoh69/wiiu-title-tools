package bodoh69.wiiu.titles;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class HexerTest {

    @Test
    public void testWrite() {
        char[] buffer = new char[32];
        for (int b = 248; b < 256; b++)
            Hexer.write((byte) b, buffer, (b - 240) * 2);
        for (byte b = 0; b < 8; b++)
            Hexer.write(b, buffer, b * 2);
        Assert.assertEquals("0001020304050607f8f9fafbfcfdfeff", String.valueOf(buffer));
    }
}
