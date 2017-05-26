import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sun.jvm.hotspot.utilities.Bits;

import java.util.BitSet;

import static org.junit.Assert.*;

public class LightSwitcherTest
{

    private BitSet baseByte = new BitSet();
    private BitSet flippedBaseByte = new BitSet();
    BitSet allOn;
    BitSet allOff;

    @Before
    public void setUp()
    {
        allOn = LightSwitcher.fromString("11111111");
        allOff = LightSwitcher.fromString("00000000");
        flippedBaseByte = LightSwitcher.fromString("1010101");
        baseByte = LightSwitcher.fromString("10101010"); //0b10101010

    }


    @Test
    public void testTurnOnSwitches()
    {
        BitSet switchOn = LightSwitcher.fromString("11110000");
        BitSet actual = LightSwitcher.turnOnSwitches(baseByte, switchOn);
        BitSet expected = LightSwitcher.fromString("11111010"); //250

        BitSet actual2 = LightSwitcher.turnOnSwitches(flippedBaseByte, switchOn);
        BitSet expected2 = LightSwitcher.fromString("11110101");//245

        BitSet switchOn3 = LightSwitcher.fromString("1111"); //0b00001111
        BitSet actual3 = LightSwitcher.turnOnSwitches(baseByte, switchOn3);
        BitSet expected3 = BitSet.valueOf(new long[]{175});//0b10101111;

        BitSet actual4 = LightSwitcher.turnOnSwitches(flippedBaseByte, switchOn3);
        BitSet expected4 = LightSwitcher.fromString("01011111");
        assertEquals(expected, actual);
        assertEquals(expected2, actual2);
        assertEquals(expected3, actual3);
        assertEquals(expected4, actual4);
    }

    @Test
    public void testTurnOnAllSwitches() {
        BitSet result1 = LightSwitcher.turnOnAllSwitches(baseByte);
        BitSet result2 = LightSwitcher.turnOnAllSwitches(flippedBaseByte);

        BitSet result3 = LightSwitcher.turnOnAllSwitches(allOn);//0b11111111)
        BitSet result4 = LightSwitcher.turnOnAllSwitches(LightSwitcher.fromString("00000000"));//0b00000000)
        assertEquals(result1, allOn);//0b11111111)
        assertEquals(result2, allOn);
        assertEquals(result3, allOn);
        assertEquals(result4, allOn);
    }

    @Test
    public void testTurnOffSwitches()
    {
        BitSet bit1 = LightSwitcher.fromString("11110000");
        BitSet bit2 = LightSwitcher.fromString("00001111");

        BitSet result1 = LightSwitcher.turnOffSwitches(baseByte, bit1);//0b10101010
        BitSet expected1 = LightSwitcher.fromString("00001010");//0b00001010;

        BitSet result2 = LightSwitcher.turnOffSwitches(flippedBaseByte, bit1);
        BitSet expected2 = LightSwitcher.fromString("00000101");//0b00000101;

        BitSet result3 = LightSwitcher.turnOffSwitches(baseByte, bit2);
        BitSet expected3 = LightSwitcher.fromString("10100000");//0b10100000;

        BitSet result4 = LightSwitcher.turnOffSwitches(flippedBaseByte, bit2);
        BitSet expected4 = LightSwitcher.fromString("01010000");//0b01010000;

        assertEquals(result1, expected1);
        assertEquals(result2, expected2);
        assertEquals(result3, expected3);
        assertEquals(result4, expected4);
    }

    @Test
    public void testTurnOffAllSwitches() {

        BitSet result1 = LightSwitcher.turnOffAllSwitches(baseByte);
        BitSet result2 = LightSwitcher.turnOffAllSwitches(flippedBaseByte);
        BitSet result3 = LightSwitcher.turnOffAllSwitches(allOn);
        BitSet result4 = LightSwitcher.turnOffAllSwitches(allOff);
        assertEquals(result1, allOff);
        assertEquals(result2, allOff);
        assertEquals(result3, allOff);
        assertEquals(result4, allOff);
    }

    @Test
    public void testFlipSwitches()
    {
        BitSet bit1 = LightSwitcher.fromString("11110000");
        BitSet bit2 = LightSwitcher.fromString("00001111");
        BitSet result1 = LightSwitcher.flipSwitches(baseByte, bit1);
        BitSet expected1 = LightSwitcher.fromString("01011010");//0b01011010;

        BitSet result2 = LightSwitcher.flipSwitches(flippedBaseByte, bit1);
        BitSet expected2 = LightSwitcher.fromString("10100101");//0b10100101;

        BitSet result3 = LightSwitcher.flipSwitches(baseByte, bit2);
        BitSet expected3 = LightSwitcher.fromString("10100101");//0b10100101;

        BitSet result4 = LightSwitcher.flipSwitches(flippedBaseByte, bit2);
        BitSet expected4 = LightSwitcher.fromString("01011010");//0b01011010;

        assertEquals(result1, expected1);
        assertEquals(result2, expected2);
        assertEquals(result3, expected3);
        assertEquals(result4, expected4);
    }

    @Test
    public void testFlipAllSwitches() {
        BitSet result1 = LightSwitcher.flipAllSwitches(baseByte);
        BitSet result2 = LightSwitcher.flipAllSwitches(flippedBaseByte);
        BitSet result3 = LightSwitcher.flipAllSwitches(allOn);
        BitSet result4 = LightSwitcher.flipAllSwitches(allOff);
        assertEquals(result1, LightSwitcher.fromString("01010101"));//0b01010101);
        assertEquals(result2, LightSwitcher.fromString("10101010"));
        assertEquals(result3, allOff);
        assertEquals(result4, allOn);
    }

    @Test
    public void testGetSwitchPositionAt() {
        for(int i = 0; i < 8; i++) {
            assertEquals(i%2, LightSwitcher.getSwitchPositionAt(baseByte, i));
        }
        for(int i = 0; i < 8; i++) {
            assertEquals((i + 1) % 2, LightSwitcher.getSwitchPositionAt(flippedBaseByte, i));
        }
        BitSet bitSet1 = LightSwitcher.fromString("11111110");
        assertEquals(0, LightSwitcher.getSwitchPositionAt(bitSet1, 0));
        assertEquals(1, LightSwitcher.getSwitchPositionAt(bitSet1, 1));
        assertEquals(1, LightSwitcher.getSwitchPositionAt(bitSet1, 7));
    }

    @Test
    public void testMoveRightBy() {
        BitSet result1 = LightSwitcher.moveRightBy(baseByte, 1);
        BitSet result2 = LightSwitcher.moveRightBy(baseByte, 2);
        BitSet result3 = LightSwitcher.moveRightBy(baseByte, 3);
        BitSet result4 = LightSwitcher.moveRightBy(baseByte, 4);
        BitSet result5 = LightSwitcher.moveRightBy(baseByte, 5);
        BitSet result6 = LightSwitcher.moveRightBy(baseByte, 6);
        BitSet result7 = LightSwitcher.moveRightBy(baseByte, 7);
        BitSet result8 = LightSwitcher.moveRightBy(baseByte, 8);
        BitSet expected1 = LightSwitcher.fromString("01010101");
        BitSet expected2 = LightSwitcher.fromString("00101010");
        BitSet expected3 = LightSwitcher.fromString("00010101");
        BitSet expected4 = LightSwitcher.fromString("00001010");
        BitSet expected5 = LightSwitcher.fromString("00000101");
        BitSet expected6 = LightSwitcher.fromString("00000010");
        BitSet expected7 = LightSwitcher.fromString("0000001");
        BitSet expected8 = allOff;
        assertEquals(result1, expected1);
        assertEquals(result2, expected2);
        assertEquals(result3, expected3);
        assertEquals(result4, expected4);
        assertEquals(result5, expected5);
        assertEquals(result6, expected6);
        assertEquals(result7, expected7);
        assertEquals(result8, expected8);
    }

    @Test
    public void testMoveLeftBy() {
        BitSet result1 = LightSwitcher.moveLeftBy(baseByte, 1);
        BitSet result2 = LightSwitcher.moveLeftBy(baseByte, 2);
        BitSet result3 = LightSwitcher.moveLeftBy(baseByte, 3);
        BitSet result4 = LightSwitcher.moveLeftBy(baseByte, 4);
        BitSet result5 = LightSwitcher.moveLeftBy(baseByte, 5);
        BitSet result6 = LightSwitcher.moveLeftBy(baseByte, 6);
        BitSet result7 = LightSwitcher.moveLeftBy(baseByte, 7);
        BitSet result8 = LightSwitcher.moveLeftBy(baseByte, 8);
        BitSet expected1 = LightSwitcher.fromString("01010100");
        BitSet expected2 = LightSwitcher.fromString("10101000");
        BitSet expected3 = LightSwitcher.fromString("01010000");//0b01010000;
        BitSet expected4 = LightSwitcher.fromString("10100000");//0b10100000;
        BitSet expected5 = LightSwitcher.fromString("01000000");//0b01000000
        BitSet expected6 = LightSwitcher.fromString("10000000");//0b10000000;
        BitSet expected7 = allOff;
        BitSet expected8 = allOff;
        assertEquals(result1, expected1);
        assertEquals(result2, expected2);
        assertEquals(result3, expected3);
        assertEquals(result4, expected4);
        assertEquals(result5, expected5);
        assertEquals(result6, expected6);
        assertEquals(result7, expected7);
        assertEquals(result8, expected8);
    }

    @Test
    public void testViewSwitches() {
        Assert.assertEquals("11111111", LightSwitcher.viewSwitches(0b111111111111));
        Assert.assertEquals("00000000", LightSwitcher.viewSwitches(0));
        Assert.assertEquals("01010101", LightSwitcher.viewSwitches(0b111101010101));
    }

    @Test
    public void stepsToShiftLeftTest()
    {
            BitSet bit1 = LightSwitcher.fromString("10101010");
            BitSet bit2 = LightSwitcher.fromString("00001111");
        System.out.println(bit2);

            int actual = LightSwitcher.shiftTrueLeft(bit1);
            int expected = 6;

            int actual2 = LightSwitcher.shiftTrueLeft(bit2);
            int expected2 = 19;


            Assert.assertEquals(expected, actual);
            Assert.assertEquals(expected2, actual2);
    }



}