package ru.unfortunately.school.abstractionsunittestsandmore.data.model;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class InstalledPackageModelTest {

    private InstalledPackageModel m1;
    private InstalledPackageModel m2;
    private InstalledPackageModel diff;


    @Before
    public void setUp() throws Exception {
        m1 = new InstalledPackageModel("App1", "Package1", null, true);
        m2 = new InstalledPackageModel("App1", "Package1", null, true);
        diff = new InstalledPackageModel("App2", "Package2", null, true);
    }

    @Test
    public void equalsTest() {
        boolean trueAns = m1.equals(m2);
        boolean falseAns = m1.equals(diff);
        assertEquals(trueAns, true);
        assertEquals(falseAns, false);
    }

}