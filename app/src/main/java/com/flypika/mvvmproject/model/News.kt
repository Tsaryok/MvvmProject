package com.flypika.mvvmproject.model

// почему не data класс?
// по стилю: каждое поле с новой строки, куча рандомных отступов
// Юзай Ctrl + Alt + L по кд, чтобы сделать красиво.
// Избегай использовать var  по возможности. В этом случае можно без него.
class News( val author: String, val title: String,
                 val description: String, val urlToImage: String){
    var isMarked = false
}
