
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;


import java.util.*;
import model.GuestData;
import model.UsageRecord;

public class ProcessorTest {

    public Processor proc;

    @BeforeEach
    public void setUp() {
        proc = new Processor();
    }

    @Test
    public void testParseLineValid() {
        String line = "9000000001|TowerA|1000|2000|No";
        UsageRecord record = proc.parseLine(line);
        assertEquals("9000000001", record.getMobileNumber());
        assertEquals(1000, record.getData4G());
        assertEquals(2000, record.getData5G());
        assertFalse(record.isRoaming());
    }

    @Test
    public void testParseLineInvalidFormat() {
        String line = "badinputlinewithoutseparator";
        assertThrows(IllegalArgumentException.class, () -> proc.parseLine(line));
    }

    @Test
    public void testAddRecordAndCalculateCost() {
        UsageRecord r1 = new UsageRecord("9000000001", "TowerA", 1000, 2000, false);
        proc.addRecord(r1);
        GuestData data = proc.getUserDataMap().get("9000000001");
        assertNotNull(data);
        double cost = proc.calculateCost(data);
        assertTrue(cost > 0);
    }

    @Test
    public void testProcessFiles() {
        List<String> files = Arrays.asList("input/input1.txt", "input/input2.txt");
        proc.processFiles(files);
        assertFalse(proc.getUserDataMap().isEmpty());
    }
}
