package julie.study;

import android.test.AndroidTestCase;

import study.DBClasses.AuthorDB;
import study.DataClasses.Author;

public class AuthorDBTest extends AndroidTestCase {
    AuthorDB authorDB;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        authorDB =new AuthorDB(getContext());
    }

    public void testGetAuthor() throws Exception {
        authorDB.id=3;
        Author author =authorDB.init();
        assertEquals("Агния Барто", author.title);
    }

    public void testGetAuthorNullAbout() throws Exception {
        authorDB.id=4;
        Author author =authorDB.init();
        assertNull(author.about);
    }
}