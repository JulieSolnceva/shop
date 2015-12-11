package study.Adapters;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AdapterForTestDB {

        private static final String DATABASE_NAME = "test.db";
        private static final int DATABASE_VERSION = 1;
        private final Context mCtx;
        public DatabaseHelper adapterDbHelper;
        public SQLiteDatabase db;

        /**
         * Database queries
         */
        private static final String DATABASE_SECTION_CREATE_STATEMENT = "CREATE TABLE section (" +
                "_id INTEGER not null PRIMARY KEY AUTOINCREMENT," +
                "title char(100) not null," +
                "left_id int(10) not null," +
                "right_id int(10) not null" +
                ");";

    private static final String DATABASE_PUBLISH_CREATE_STATEMENT = " CREATE TABLE publish " +
            " (_id INTEGER not null PRIMARY KEY AUTOINCREMENT, title char(100) not null);";

    private static final String DATABASE_AUTHOR_CREATE_STATEMENT = "CREATE TABLE author " +
            " (_id INTEGER not null PRIMARY KEY AUTOINCREMENT, title char(100) not null, about text default null);";

    private static final String DATABASE_GOOD_CREATE_STATEMENT = "CREATE TABLE good ("+
            "_id INTEGER not null PRIMARY KEY AUTOINCREMENT,  section_id int(3) not null default 0,"+
            " publish_id int(4) not null default 0,    author_id int(4) not null default 0,"+
            "title char(255) not null default '',    pages int(10) not null default 0,"+
            " publish_year  int(4) not null default 0,    price float(5,2) not null default 0,"+
            "about text not null default '');";

        private static class DatabaseHelper extends SQLiteOpenHelper {

            public DatabaseHelper(Context context) {
                super(context, DATABASE_NAME, null, DATABASE_VERSION);
            }

            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL(DATABASE_SECTION_CREATE_STATEMENT);
                db.execSQL(DATABASE_PUBLISH_CREATE_STATEMENT);
                db.execSQL(DATABASE_AUTHOR_CREATE_STATEMENT);
                db.execSQL(DATABASE_GOOD_CREATE_STATEMENT);
                db.execSQL("INSERT INTO `section` VALUES (1,'root',1,16);");
                db.execSQL("INSERT INTO `section` VALUES(2,'книги','2','7')");
                db.execSQL("INSERT INTO `section` VALUES(3,'детективы','3','4')");
                db.execSQL("INSERT INTO `section` VALUES(4,'канцтовары','8','15')");
                db.execSQL("INSERT INTO `section` VALUES(5,'тетради','9','14')");
                db.execSQL("INSERT INTO `section` VALUES(6,'детская','5','6')");

                db.execSQL("INSERT INTO `section` VALUES(7,'тетради в клетку','10','11')");
                db.execSQL("INSERT INTO `section` VALUES(8,'тетради в линейку','12','13')");



                db.execSQL("INSERT INTO `author` VALUES(1,'Агата Кристи','Автор детективов')");
                db.execSQL("INSERT INTO `author` VALUES(2,'Корней Чуковский',null)");
                db.execSQL("INSERT INTO `author` VALUES(3,'Агния Барто','Детская писательница')");
                db.execSQL("INSERT INTO `author` VALUES(4,'Джеймс Чейз',null)");

                db.execSQL("INSERT INTO `publish` VALUES(1,'Эксмо')");
                db.execSQL("INSERT INTO `publish` VALUES(2,'АСТ')");

                db.execSQL("INSERT INTO `good` VALUES (1,6,2,2,'Дюжина сказок',0,0,0.0,'Сборник из двенадцати сказок для детей дошкольного и младшего школьного возраста.');");
                db.execSQL("INSERT INTO `good` VALUES (2,6,2,2,'Муха-Цокотуха',0,0,0.0,'Добрые и весёлые стихи и сказки любимого детского писателя Корнея Чуковского - это море счастья и удовольствия от чтения. Но такого вы ещё не видели: мы предлагаем взглянуть на известные с детства истории немножко по-другому - в сопровождении замечательных иллюстраций современных молодых художников');");

                db.execSQL("INSERT INTO `good` VALUES (3,3,1,1,'Человек в коричневом костюме. Тайна замка Чимниз',240,2013,867.0,'Две смерти - обыденная и загадочная. Пассажир метро, упавший под колеса поезда и странное убийство туристки в старинном английском замке. Казалось бы, как могут быть связаны между собой столь разные происшествия? Ключ к разгадке тайны - человек в коричневом костюме!');");
                db.execSQL("INSERT INTO `good` VALUES (4,3,2,1,'Черный кофе',0,0,0.0,'Черный кофе - роман, написанный по мотивам одноименной пьесы Агаты Кристи. Впервые пьеса появилась на театральных подмостках в 1930 году. К Пуаро обращается за помощью знаменитый физик сэр Клод Эмори. Однако увидеться с ним Пуаро и Гастингс не успевают - ученый отравлен. При этом исчезла открытая им формула сверхмощного взрывчатого вещества.');");
                db.execSQL("INSERT INTO `good` VALUES (5,3,2,4,'Однажды ясным летним утром',0,0,0.0,'Увязший в долгах страховой агент Джон Энсон становится убийцей ради быстрого обогащения. Казалось, все идет по плану, но Джон не догадывается, что с ним ведут жестокую игру');");

                db.execSQL("INSERT INTO `good` VALUES (6,5,0,0,'тетрадь для записи иностранных слов',18,0,0.0,'');");
                db.execSQL("INSERT INTO `good` VALUES (7,5,0,0,'тетрадь в косую линейку',12,0,0.0,'');");





            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }


        }

        /**
         * Constructor - takes the provided context to allow for the database to be
         * opened/created.
         *
         * @param context the Context within which to work.
         */
        public AdapterForTestDB(Context context) {
            mCtx = context;
        }

        /**
         * Open the last.fm database. If it cannot be opened, try to create a new
         * instance of the database. If it cannot be created, throw an exception to
         * signal the failure.
         *
         * @return this (self reference, allowing this to be chained in an
         *         initialization call)
         * @throws SQLException if the database could be neither opened or created
         */
        public AdapterForTestDB open() throws SQLException {


            adapterDbHelper = new DatabaseHelper(mCtx);
            db = adapterDbHelper.getWritableDatabase();

                Log.d("AdapterForTestDB", "  open");
            return this;
        }

        public void close() {
            adapterDbHelper.close();
        }

}
