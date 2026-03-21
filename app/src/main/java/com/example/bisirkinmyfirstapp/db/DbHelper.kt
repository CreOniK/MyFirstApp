package com.example.bisirkinmyfirstapp.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.bisirkinmyfirstapp.db.PostContract.Columns

class DbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "myfirstapp.db"
        private const val DATABASE_VERSION = 1

        // SQL для создания таблицы
        private val SQL_CREATE_POSTS =
            "CREATE TABLE ${PostContract.TABLE_NAME} (" +
                    "${Columns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${Columns.AUTHOR} TEXT NOT NULL," +
                    "${Columns.AUTHOR_ID} INTEGER NOT NULL," +
                    "${Columns.CONTENT} TEXT NOT NULL," +
                    "${Columns.PUBLISHED} TEXT NOT NULL," +
                    "${Columns.LIKED_BY_ME} INTEGER NOT NULL DEFAULT 0," +
                    "${Columns.LIKES} INTEGER NOT NULL DEFAULT 0," +
                    "${Columns.SHARES} INTEGER NOT NULL DEFAULT 0," +
                    "${Columns.VIEWS} INTEGER NOT NULL DEFAULT 0," +
                    "${Columns.VIDEO} TEXT" +
                    ")"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Создаем таблицу при первом запуске
        db.execSQL(SQL_CREATE_POSTS)

        // Здесь можно добавить начальные данные
        insertInitialData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // При обновлении версии удаляем старую таблицу и создаем новую
        // В реальном проекте здесь должна быть миграция данных
        db.execSQL("DROP TABLE IF EXISTS ${PostContract.TABLE_NAME}")
        onCreate(db)
    }

    private fun insertInitialData(db: SQLiteDatabase) {
        // Вставляем начальные посты для демонстрации
        val contentValues = android.content.ContentValues().apply {
            put(Columns.AUTHOR, "⚛ Квантовая суперпозиция. Университет Квантовых взаимоотношений")
            put(Columns.AUTHOR_ID, 2)
            put(Columns.CONTENT,"Квантовая суперпозиция — это состояние, при котором частица (например, электрон) может находиться в нескольких состояниях одновременно, пока на неё не воздействуют измерением. Другими словами, частица существует сразу во всех возможных вариантах, которые «схлопываются» в один определённый результат только при наблюдении.\n" +
                    "Это явление — одно из ключевых отличий квантового мира от классического, где объект может находиться только в одном состоянии в данный момент времени.\n" +
                    "Принцип:Суперпозиция представляет собой сумму (наложение) всех возможных состояний, в которых может находиться система. Математически состояние такой системы описывается волновой функцией, которая содержит в себе всю информацию о системе. ")
            put(Columns.PUBLISHED, "21 мая в 18:36")
            put(Columns.LIKED_BY_ME, 1)
            put(Columns.LIKES, 1300000)
            put(Columns.SHARES, 25342)
            put(Columns.VIEWS, 5041245)
            put(Columns.VIDEO, "https://yandex.ru/video/preview/17658453747006452216")
        }
        db.insert(PostContract.TABLE_NAME, null, contentValues)

        // Второй пост с видео
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Кванты")
            put(Columns.AUTHOR_ID, 3)
            put(Columns.CONTENT, "Квант (от лат. quantum — «сколько») — неделимая часть какой-либо величины в физике; общее название определённых порций энергии (квант энергии), момента количества движения (углового момента), его проекции и других величин, которыми характеризуют физические свойства микро- (квантовых) систем. В основе понятия лежит представление квантовой механики о том, что некоторые физические величины могут принимать только определённые значения (говорят, что физическая величина квантуется). В некоторых важных частных случаях эта величина или шаг её изменения могут быть только целыми кратными некоторого фундаментального значения[1] — и последнее называют квантом. Например, энергия монохроматического")
            put(Columns.PUBLISHED, "28 мая в 13:32")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 1450435)
            put(Columns.SHARES, 250000)
            put(Columns.VIEWS, 1809434)
            putNull(Columns.VIDEO)
            db.insert(PostContract.TABLE_NAME, null, this)
        }
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "AstrologGG")
            put(Columns.AUTHOR_ID, 4)
            put(Columns.CONTENT, "Самый таинственный космический пришелец XX века — Тунгусский метеорит. Он упал в районе сибирской реки Тунгуска утром 30 июня 1908 года. В тот день небо осветило ярким сиянием, а последовавший за ним воздушный взрыв уничтожил огромный участок леса и выбил стекла домов в радиусе 200 км. Однако ни осколков метеорита, ни следов применения оружия массового поражения, ни обломков инопланетного корабля так никто и не нашел.",)
            put(Columns.PUBLISHED, "23 мая в 09:42")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 342345)
            put(Columns.SHARES, 89674)
            put(Columns.VIEWS, 2300525)
            putNull(Columns.VIDEO)
            db.insert(PostContract.TABLE_NAME, null, this)
        }
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "\uD83D\uDC80 Хорошее настроение гарантировано")
            put(Columns.AUTHOR_ID, 5)
            put(Columns.CONTENT, "Кто  сказал, что жизнь короткая? Она длится ровно столько, сколько мы успеваем насладиться её прелестями... ну и похоронить всех, кто мешает наслаждаться жизнью.")
            put(Columns.PUBLISHED, "22 мая в 10:15")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 1250412)
            put(Columns.SHARES, 42032)
            put(Columns.VIEWS, 2890054)
            putNull(Columns.VIDEO)
            db.insert(PostContract.TABLE_NAME, null, this)
        }
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Как отличить вегана от обычного зомби?")
            put(Columns.AUTHOR_ID, 6)
            put(Columns.CONTENT,"Обычный зомби кричит: «Мозги!» А веганский зомби тихо шепчет: «Фасоль»")
            put(Columns.PUBLISHED, "26 мая в 17:42")
            put(Columns.LIKED_BY_ME, 1)
            put(Columns.LIKES, 1250412)
            put(Columns.SHARES, 42032)
            put(Columns.VIEWS, 28900540)
            putNull(Columns.VIDEO)
            db.insert(PostContract.TABLE_NAME, null, this)
        }
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Кварки")
            put(Columns.AUTHOR_ID, 7)
            put(Columns.CONTENT, "Кварк — бесструктурная элементарная частица и фундаментальная составляющая материи. Кварки объединяются в составные частицы, называемые адронами, наиболее стабильными из которых являются протоны и нейтроны, компоненты атомных ядер. Всё обычно наблюдаемое вещество состоит из верхних кварков, нижних кварков и электронов. Из-за явления, известного как удержание цвета, кварки никогда не встречаются изолированно; их можно найти только в составе адронов, которые включают барионы (такие как протоны и нейтроны) и мезоны, или в кварк-глюонной плазме. По этой причине много информации о кварках было получено из наблюдений за адронами")
            put(Columns.PUBLISHED, "24 мая в 03:42")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 2450412)
            put(Columns.SHARES, 402032)
            put(Columns.VIEWS, 2090054)
            putNull(Columns.VIDEO)
            db.insert(PostContract.TABLE_NAME, null, this)
        }
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Антиматерия")
            put(Columns.AUTHOR_ID, 8)
            put(Columns.CONTENT, "Антиматерия (или антивещество) — это вещество, состоящее из античастиц. Как и любое другое вещество, антивещество состоит из атомов, которые состоят в свою очередь из протонов и нейтронов (ядро атома) и электронов (внешняя оболочка атома)\n" +
                    "\n" +
                    "Античастицы образуются в ядрах активных галактик как и на ускорителях — вместе с частицами. Но сразу после этого частицы и античастицы при встрече аннигилируют.\n")
            put(Columns.PUBLISHED, "25 мая в 16:42")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 2450412)
            put(Columns.SHARES, 402032)
            put(Columns.VIEWS, 2090054)
            putNull(Columns.VIDEO)
            db.insert(PostContract.TABLE_NAME, null, this)
        }
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "GitHub")
            put(Columns.AUTHOR_ID, 9)
            put(Columns.CONTENT, "-GitHub — крупнейший веб-сервис для хостинга IT-проектов и их совместной разработки.\n" +
                    "\n" +
                    "GitHub — веб-сервис, основанный на системе контроля версий Git и разработан на Ruby on Rails и Erlang компанией GitHub, Inc (ранее Logical Awesome). Сервис бесплатен для проектов с открытым исходным кодом и (с 2019 года) небольших частных проектов, предоставляя им все возможности (включая SSL), а для крупных корпоративных проектов предлагаются различные платные тарифные планы")
            put(Columns.PUBLISHED, "27 мая в 10:42")
            put(Columns.LIKED_BY_ME, 1)
            put(Columns.LIKES, 2450412)
            put(Columns.SHARES, 402032)
            put(Columns.VIEWS, 2090054)
            putNull(Columns.VIDEO)
            db.insert(PostContract.TABLE_NAME, null, this)
        }
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Протокол Ди́ффи — Хе́ллмана")
            put(Columns.AUTHOR_ID, 10)
            put(Columns.CONTENT, "Протокол Ди́ффи — Хе́ллмана - (англ. Diffie–Hellman key exchange protocol, DH) — криптографический протокол, позволяющий двум и более сторонам получить общий секретный ключ, используя не защищенный от прослушивания канал связи. Полученный ключ используется для шифрования дальнейшего обмена с помощью алгоритмов симметричного шифрования.\n" +
                    "\n" +
                    "Схема открытого распределения ключей, предложенная Диффи и Хеллманом, произвела настоящую революцию в мире шифрования, так как снимала основную проблему классической криптографии — проблему распределения ключей.\n" +
                    "\n" +
                    "В чистом виде алгоритм Диффи — Хеллмана уязвим для модификации данных в канале связи, в том числе для атаки «man-in-the-middle (человек посередине)», поэтому схемы с его использованием применяют дополнительные методы односторонней или двусторонней аутентификации.")
            put(Columns.PUBLISHED, "29 мая в 09:10")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 34220)
            put(Columns.SHARES, 1889)
            put(Columns.VIEWS, 23400)
            putNull(Columns.VIDEO)
            db.insert(PostContract.TABLE_NAME, null, this)
        }
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "SSL")
            put(Columns.AUTHOR_ID, 11)
            put(Columns.CONTENT, " - (англ. Secure Sockets Layer — уровень защищённых сокетов) — криптографический протокол, который подразумевает более безопасную связь между хостом и клиентом. Он использует асимметричную криптографию для аутентификации ключей обмена, симметричное шифрование для сохранения конфиденциальности, коды аутентификации сообщений для целостности сообщений. Протокол широко использовался для обмена мгновенными сообщениями и передачи голоса через IP (англ. Voice over IP — VoIP) в таких приложениях, как электронная почта, интернет-факс и др.")
            put(Columns.PUBLISHED, "22 мая в 10:15")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 567884)
            put(Columns.SHARES, 13452)
            put(Columns.VIEWS, 834000)
            putNull(Columns.VIDEO)
            db.insert(PostContract.TABLE_NAME, null, this)
        }

    }
}
