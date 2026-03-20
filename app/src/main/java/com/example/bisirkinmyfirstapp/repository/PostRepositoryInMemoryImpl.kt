package com.example.bisirkinmyfirstapp.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bisirkinmyfirstapp.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {

    // Теперь это список, а не один пост
    private var posts = listOf(
        Post(
            id = 1,
            author = "⚛ Квантовая суперпозиция. Университет Квантовых взаимоотношений",
            content = "Квантовая суперпозиция — это состояние, при котором частица (например, электрон) может находиться в нескольких состояниях одновременно, пока на неё не воздействуют измерением. Другими словами, частица существует сразу во всех возможных вариантах, которые «схлопываются» в один определённый результат только при наблюдении.\n" +
                    "Это явление — одно из ключевых отличий квантового мира от классического, где объект может находиться только в одном состоянии в данный момент времени.\n" +
                    "Принцип:Суперпозиция представляет собой сумму (наложение) всех возможных состояний, в которых может находиться система. Математически состояние такой системы описывается волновой функцией, которая содержит в себе всю информацию о системе. ",
            published = "21 мая в 18:36",
            likedByMe = false,
            likes = 1300000,
            shares = 25,
            views = 5041245
        ),
        Post(
            id = 2,
            author = "\uD83D\uDD25 ШаурмаNews",
            content = "Бухмеджан офециально заявил что не будет есть люля приготовльный украинцем так как по его словам он:***** как ***** готовит ****** люля - после этих слов он выкинул мангал в окно в месте с украинцем",
            published = "28 мая в 13:32",
            likedByMe = false,
            likes = 1450435,
            shares = 250000,
            views = 1809434
        ),
        Post(
            id = 3,
            author = "AstrologGG",
            content = "Вчера на орбите земли был замечен странный объект неестественной формы, так же по имеющийся информации мысмеем заевлять что в этом виноват дед максим ",
            published = "22 мая в 10:15",
            likedByMe = false,
            likes = 342345,
            shares = 89674,
            views = 2300525
        ),
        Post(
            id = 4,
            author = "\uD83D\uDC80 Хорошее настроение гарантировано",
            content = "Кто сказал, что жизнь короткая? Она длится ровно столько, сколько мы успеваем насладиться её прелестями... ну и похоронить всех, кто мешает наслаждаться жизнью.",
            published = "23 мая в 09:42",
            likedByMe = true,
            likes = 1250412,
            shares = 42032,
            views = 2890054
        ),
        Post(
            id = 4,
            author = "Как отличить вегана от обычного зомби?",
            content = "Обычный зомби кричит: «Мозги!» А веганский зомби тихо шепчет: «Фасоль»",
            published = "26 мая в 17:42",
            likedByMe = true,
            likes = 1250412,
            shares = 42032,
            views = 2890054
        ),
        Post(
            id = 4,
            author = "Встреча выпускников",
            content = "Бывший одноклассник подходит к однокашнику:\n" +
                    "\n" +
                    "— Как успехи?\n" +
                    "\n" +
                    "— Отлично, наконец нашел дело своей мечты стал кладбищенским садовником.\n" +
                    "\n" +
                    "— Нравится работа?\n" +
                    "\n" +
                    "— Очень, цветы растут сами, клиенты никогда не жалуются, конкуренция минимальная.",
            published = "24 мая в 03:42",
            likedByMe = true,
            likes = 2450412,
            shares = 402032,
            views = 2090054
        ),Post(
            id = 4,
            author = "Нууу Мааам",
            content = "- Мам а что такое черный юмор\n" +
                    "\n" +
                    "— Сынок видешь там мужчину без ручек? попроси его похлопать в ладоши\n" +
                    "\n" +
                    "— Мама но я же слепой .\n" +
                    "\n" +
                    "— Вот именно",
            published = "25 мая в 16:42",
            likedByMe = true,
            likes = 2450412,
            shares = 402032,
            views = 2090054
        ),Post(
            id = 4,
            author = "Ага щас",
            content = "- будешь выходить труп вынеси \n" +
                    "\n" +
                    "— может быть мусор?\n" +
                    "\n" +
                    "— Можут мусор, может сантехник, бог его знает.\n",
            published = "27 мая в 10:42",
            likedByMe = true,
            likes = 2450412,
            shares = 402032,
            views = 2090054
        ),Post(
            id = 4,
            author = "Парашют",
            content = "- Я боюсь прыгать - вдруг парашют не откроется? \n" +
                    "\n" +
                    "— Еще не кто не жаловался что у него не раскрылся парашют.\n",
            published = "29 мая в 09:10",
            likedByMe = true,
            likes = 2450412,
            shares = 402032,
            views = 2090054
        ),
        Post(
            id = 5,
            author = "☠\uFE0F зебра",
            content = "Жизнь похожа на зебру: сначала белая полоса, потом черная, потом снова белая… Но главное, вовремя успеть перейти дорогу, пока светофор зелёный!",
            published = "30 мая в 20:00",
            likedByMe = false,
            likes = 567884,
            shares = 13452,
            views = 2650000
        )
    )

    private val _data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = _data

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
    }
}

