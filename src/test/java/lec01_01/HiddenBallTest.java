package lec01_01;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertNotNull;

public class HiddenBallTest {

    @Test
    public void testCreateWithInvalidStringVolume() {
        String volume = "2l.4e2";
        assertNotNull("new(" + volume + ")", new Ball(volume, Color.WHITE).getVolume());
    }

}
