package julie.study;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.HashMap;

public class GoodDBTest extends AndroidTestCase {
    GoodDB goodDB;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        goodDB =new GoodDB(getContext());
    }


    public void testGetSectionGoods() {
        goodDB.id_section=3;
        ArrayList<Good> goods= goodDB.getSectionGoods();
        assertEquals(3, goods.size());

        assertEquals("Джеймс Чейз", goods.get(0).author);
        assertEquals("Однажды ясным летним утром", goods.get(0).title);

        assertEquals("Агата Кристи", goods.get(1).author);
        assertEquals("Человек в коричневом костюме. Тайна замка Чимниз", goods.get(1).title);

        assertEquals("Агата Кристи", goods.get(2).author);
        assertEquals("Черный кофе", goods.get(2).title);
    }

    public void testGetSectionGoodsNOGoods() {
        goodDB.id_section=1;
        ArrayList<Good> goods= goodDB.getSectionGoods();
        assertEquals(0, goods.size());
    }
    public void testGetGood(){
        goodDB.id=1;
        Good good = goodDB.getGood();
        assertEquals("Дюжина сказок", good.title);
        assertEquals("Корней Чуковский", good.author);
        assertEquals("АСТ", good.publish);
    }

    public void testGetGoodNull(){
        goodDB.id=101;
        Good good = goodDB.getGood();
        assertNull(good);
    }
}