package com.example.bisirkinmyfirstapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.bisirkinmyfirstapp.dto.Post
import java.text.SimpleDateFormat
import java.util.Date
import java.util.List;
import java.util.Locale

class PostRepositorySharedPrefsImpl(
    private val context: Context
) : PostRepository {

    private val gson = Gson()
    private val prefs = context.getSharedPreferences("posts_repo", Context.MODE_PRIVATE)
    private val type = object : TypeToken<List<Post>>() {}.type
    private val key = "posts"

    private var nextId = 1L
    private val currentUserId = 1L
    private val currentUserName = "Я"

    private var posts = emptyList<Post>()
    private val _data = MutableLiveData(posts)

    init {
        loadData()
    }

    override fun getAll(): LiveData<kotlin.collections.List<Post>> = _data

    override fun likeById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(
                    likedByMe = !post.likedByMe,
                    likes = if (post.likedByMe) post.likes - 1 else post.likes + 1
                )
            } else {
                post
            }
        }
        _data.value = posts
        saveData()
    }

    override fun shareById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(shares = post.shares + 1)
            } else {
                post
            }
        }
        _data.value = posts
        saveData()
    }

    override fun increaseViews(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(views = post.views + 1)
            } else {
                post
            }
        }
        _data.value = posts
        saveData()
    }

    override fun save(post: Post): Post {
        posts = if (post.id == 0L) {
            val newPost = post.copy(
                id = nextId++,
                author = currentUserName,
                authorId = currentUserId,
                published = formatDate(Date()),
                likedByMe = false,
                likes = 0,
                shares = 0,
                views = 0
            )
            listOf(newPost) + posts
        } else {
            posts.map { existingPost ->
                if (existingPost.id == post.id) {
                    existingPost.copy(content = post.content)
                } else {
                    existingPost
                }
            }
        }
        _data.value = posts
        saveData()
        return TODO("Provide the return value")
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        _data.value = posts
        saveData()
    }

    private fun loadData() {
        val json = prefs.getString(key, null)
        if (json != null) {
            try {
                val loadedPosts: List<Post> = gson.fromJson(json, type)
                if (loadedPosts.isNotEmpty()) {
                    posts = loadedPosts as kotlin.collections.List<Post>
                    nextId = (posts.maxOfOrNull { it.id } ?: 0) + 1
                    _data.value = posts
                    return
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        createInitialData()
        saveData()
    }

    private fun saveData() {
        prefs.edit().putString(key, gson.toJson(posts)).apply()
    }

    private fun createInitialData() {
        // Аналогично файловой реализации
        posts = listOf(
            Post(
                id = 1,
                authorId = 2,
                author = "⚛ Квантовая суперпозиция. Университет Квантовых взаимоотношений",
                content = "Квантовая суперпозиция — это состояние, при котором частица (например, электрон) может находиться в нескольких состояниях одновременно, пока на неё не воздействуют измерением. Другими словами, частица существует сразу во всех возможных вариантах, которые «схлопываются» в один определённый результат только при наблюдении.\n" +
                        "Это явление — одно из ключевых отличий квантового мира от классического, где объект может находиться только в одном состоянии в данный момент времени.\n" +
                        "Принцип:Суперпозиция представляет собой сумму (наложение) всех возможных состояний, в которых может находиться система. Математически состояние такой системы описывается волновой функцией, которая содержит в себе всю информацию о системе. ",
                published = "21 мая в 18:36",
                likedByMe = false,
                likes = 1300000,
                shares = 25,
                views = 5041245,
                video = "https://yandex.ru/video/preview/17658453747006452216"
            ),
            Post(
                id = 2,
                author = "Кванты",
                authorId = 3,
                content = "Квант (от лат. quantum — «сколько») — неделимая часть какой-либо величины в физике; общее название определённых порций энергии (квант энергии), момента количества движения (углового момента), его проекции и других величин, которыми характеризуют физические свойства микро- (квантовых) систем. В основе понятия лежит представление квантовой механики о том, что некоторые физические величины могут принимать только определённые значения (говорят, что физическая величина квантуется). В некоторых важных частных случаях эта величина или шаг её изменения могут быть только целыми кратными некоторого фундаментального значения[1] — и последнее называют квантом. Например, энергия монохроматического",
                published = "28 мая в 13:32",
                likedByMe = true,
                likes = 1450435,
                shares = 250000,
                views = 1809434,
                video = null

            ),
            Post(
                id = 3,
                author = "AstrologGG",
                authorId = 4,
                content = "Самый таинственный космический пришелец XX века — Тунгусский метеорит. Он упал в районе сибирской реки Тунгуска утром 30 июня 1908 года. В тот день небо осветило ярким сиянием, а последовавший за ним воздушный взрыв уничтожил огромный участок леса и выбил стекла домов в радиусе 200 км. Однако ни осколков метеорита, ни следов применения оружия массового поражения, ни обломков инопланетного корабля так никто и не нашел.",
                published = "22 мая в 10:15",
                likedByMe = false,
                likes = 342345,
                shares = 89674,
                views = 2300525,
                video = null
            ),
            Post(
                id = 4,
                author = "\uD83D\uDC80 Хорошее настроение гарантировано",
                authorId = 5,
                content = "Кто  сказал, что жизнь короткая? Она длится ровно столько, сколько мы успеваем насладиться её прелестями... ну и похоронить всех, кто мешает наслаждаться жизнью.",
                published = "23 мая в 09:42",
                likedByMe = false,
                likes = 1250412,
                shares = 42032,
                views = 2890054,
                video = null
            ),
            Post(
                id = 5,
                author = "Как отличить вегана от обычного зомби?",
                authorId = 6,
                content = "Обычный зомби кричит: «Мозги!» А веганский зомби тихо шепчет: «Фасоль»",
                published = "26 мая в 17:42",
                likedByMe = false,
                likes = 1250412,
                shares = 42032,
                views = 2890054,
                video = null
            ),
            Post(
                id = 6,
                authorId = 7,
                author = "Кварки",
                content = "Кварк — бесструктурная элементарная частица и фундаментальная составляющая материи. Кварки объединяются в составные частицы, называемые адронами, наиболее стабильными из которых являются протоны и нейтроны, компоненты атомных ядер. Всё обычно наблюдаемое вещество состоит из верхних кварков, нижних кварков и электронов. Из-за явления, известного как удержание цвета, кварки никогда не встречаются изолированно; их можно найти только в составе адронов, которые включают барионы (такие как протоны и нейтроны) и мезоны, или в кварк-глюонной плазме. По этой причине много информации о кварках было получено из наблюдений за адронами",
                published = "24 мая в 03:42",
                likedByMe = true,
                likes = 2450412,
                shares = 402032,
                views = 2090054,
                video = null
            ),Post(
                id = 7,
                authorId = 8,
                author = "Антиматерия",
                content = "Антиматерия (или антивещество) — это вещество, состоящее из античастиц. Как и любое другое вещество, антивещество состоит из атомов, которые состоят в свою очередь из протонов и нейтронов (ядро атома) и электронов (внешняя оболочка атома)\n" +
                        "\n" +
                        "Античастицы образуются в ядрах активных галактик как и на ускорителях — вместе с частицами. Но сразу после этого частицы и античастицы при встрече аннигилируют.\n",
                published = "25 мая в 16:42",
                likedByMe = false,
                likes = 2450412,
                shares = 402032,
                views = 2090054,
                video = null
            ),Post(
                id = 8,
                authorId = 9,
                author = "GitHub",
                content = "-GitHub — крупнейший веб-сервис для хостинга IT-проектов и их совместной разработки.\n" +
                        "\n" +
                        "GitHub — веб-сервис, основанный на системе контроля версий Git и разработан на Ruby on Rails и Erlang компанией GitHub, Inc (ранее Logical Awesome). Сервис бесплатен для проектов с открытым исходным кодом и (с 2019 года) небольших частных проектов, предоставляя им все возможности (включая SSL), а для крупных корпоративных проектов предлагаются различные платные тарифные планы",
                published = "27 мая в 10:42",
                likedByMe = false,
                likes = 2450412,
                shares = 402032,
                views = 2090054,
                video = null
            ),Post(
                id = 9,
                authorId = 10,
                author = "Протокол Ди́ффи — Хе́ллмана",
                content = "Протокол Ди́ффи — Хе́ллмана - (англ. Diffie–Hellman key exchange protocol, DH) — криптографический протокол, позволяющий двум и более сторонам получить общий секретный ключ, используя не защищенный от прослушивания канал связи. Полученный ключ используется для шифрования дальнейшего обмена с помощью алгоритмов симметричного шифрования.\n" +
                        "\n" +
                        "Схема открытого распределения ключей, предложенная Диффи и Хеллманом, произвела настоящую революцию в мире шифрования, так как снимала основную проблему классической криптографии — проблему распределения ключей.\n" +
                        "\n" +
                        "В чистом виде алгоритм Диффи — Хеллмана уязвим для модификации данных в канале связи, в том числе для атаки «man-in-the-middle (человек посередине)», поэтому схемы с его использованием применяют дополнительные методы односторонней или двусторонней аутентификации.",
                published = "29 мая в 09:10",
                likedByMe = false,
                likes = 2450412,
                shares = 402032,
                views = 2090054,
                video = null
            ),
            Post(
                id = 10,
                author = "SSL",
                authorId = 11,
                content = "(англ. Secure Sockets Layer — уровень защищённых сокетов) — криптографический протокол, который подразумевает более безопасную связь между хостом и клиентом. Он использует асимметричную криптографию для аутентификации ключей обмена, симметричное шифрование для сохранения конфиденциальности, коды аутентификации сообщений для целостности сообщений. Протокол широко использовался для обмена мгновенными сообщениями и передачи голоса через IP (англ. Voice over IP — VoIP) в таких приложениях, как электронная почта, интернет-факс и др.",
                published = "30 мая в 20:00",
                likedByMe = false,
                likes = 567884,
                shares = 13452,
                views = 2650000,
                video = null
            )
        )
        _data.value = posts
    }

    private fun formatDate(date: Date): String {
        val format = SimpleDateFormat("d MMM в HH:mm", Locale("ru"))
        return format.format(date)
    }
}
