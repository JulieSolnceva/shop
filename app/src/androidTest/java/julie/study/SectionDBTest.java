package julie.study;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.HashMap;

public class SectionDBTest extends AndroidTestCase {
    public SectionDB sectionDB;
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        sectionDB =new SectionDB(getContext());
    }




    public void testInit() {
        sectionDB.id=2;
        assertTrue(sectionDB.dbActive.isOpen());
        Section section=sectionDB.init();
        assertEquals("книги", section.title);
    }

    public void testGetChildren(){
        ArrayList<Section> children= sectionDB.getChildren(2);
        assertEquals(2, children.size());
        assertEquals("детективы", children.get(0).title);
        assertEquals("детская", children.get(1).title);
    }

    public void testGetParents(){
        sectionDB.id=6;
        ArrayList<Section> parents= sectionDB.getParents(6);
        assertEquals(2, parents.size());
        assertEquals("книги", parents.get(0).title);
        assertEquals("2", String.valueOf(parents.get(0).id));
        assertEquals("детская", parents.get(1).title);

    }



}
