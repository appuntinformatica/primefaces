import grails.test.AbstractCliTestCase

class PfGenerateAllTests extends AbstractCliTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testPfGenerateAll() {

        execute(["pf-generate-all"])

        assertEquals 0, waitForProcess()
        verifyHeader()
    }
}
