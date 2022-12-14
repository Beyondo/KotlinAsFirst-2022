@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import lesson3.task1.digitNumber
import java.io.File
import java.lang.Math.max
import java.lang.Math.pow
import java.util.*
import kotlin.math.pow

// Урок 7: работа с файлами
// Урок интегральный, поэтому его задачи имеют сильно увеличенную стоимость
// Максимальное количество баллов = 55
// Рекомендуемое количество баллов = 20
// Вместе с предыдущими уроками (пять лучших, 3-7) = 55/103

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    var currentLineLength = 0
    fun append(word: String) {
        if (currentLineLength > 0) {
            if (word.length + currentLineLength >= lineLength) {
                writer.newLine()
                currentLineLength = 0
            } else {
                writer.write(" ")
                currentLineLength++
            }
        }
        writer.write(word)
        currentLineLength += word.length
    }
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            writer.newLine()
            if (currentLineLength > 0) {
                writer.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(Regex("\\s+"))) {
            append(word)
        }
    }
    writer.close()
}

/**
 * Простая (8 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Некоторые его строки помечены на удаление первым символом _ (подчёркивание).
 * Перенести в выходной файл с именем outputName все строки входного файла, убрав при этом помеченные на удаление.
 * Все остальные строки должны быть перенесены без изменений, включая пустые строки.
 * Подчёркивание в середине и/или в конце строк значения не имеет.
 *
 */
fun deleteMarked(inputName: String, outputName: String) {
    File(outputName).bufferedWriter().use { writer ->
        File(inputName).forEachLine { line ->
            if (line.isNotEmpty() && line[0] != '_') writer.write(line + "\n")
        }
    }
}

/**
 * Средняя (14 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val result = mutableMapOf<String, Int>()
    val text = File(inputName).readText().lowercase(Locale.getDefault())
    for (substring in substrings) {
        var count = 0
        var index = 0
        while (index < text.length) {
            index = text.indexOf(substring.lowercase(Locale.getDefault()), index)
            if (index == -1) break
            count++
            index++
        }
        result[substring] = count
    }
    return result
}

/**
 * Средняя (12 баллов)
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    val sibilants = listOf('ж', 'ч', 'ш', 'щ')
    val correctedVowels = mapOf('ы' to 'и', 'я' to 'а', 'ю' to 'у', 'Ы' to 'И', 'Я' to 'А', 'Ю' to 'У')
    var text = File(inputName).readText().toCharArray()
    text.forEachIndexed { i, char ->
        if (i < text.size - 1) {
            val word = text.slice(i until text.size).toString().split(" ").first()
            if (char.lowercaseChar() in sibilants) {
                val nextChar = text[i + 1]
                if (nextChar in correctedVowels.keys) {
                    text[i + 1] = correctedVowels[nextChar]!!
                }
            }
        }
    }
    writer.write(text)
    writer.close()
}

/**
 * Средняя (15 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    val lines = File(inputName).readLines()
    val maxLength = lines.maxByOrNull { it.trim().length }?.trim()?.length ?: 0
    for (line in lines) {
        val spaces = " ".repeat((maxLength - line.trim().length) / 2)
        writer.write(spaces + line.trim())
        writer.newLine()
    }
    writer.close()
}

/**
 * Сложная (20 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов между словами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    val lines = File(inputName).readLines().map { it.trim() }
    val writer = File(outputName).bufferedWriter()
    val maxLength = lines.maxByOrNull { it.length }?.length ?: 0
    for (line in lines) {
        val symbols = line.split(" ")
        if (symbols.size == 1) {
            writer.write(line)
            writer.newLine()
            continue
        }
        val neededSpaces = maxLength - line.length
        val numSpacesInBetween = (neededSpaces / (symbols.size - 1)) + 1
        val numExtraSpaces = neededSpaces % (symbols.size - 1)
        val spaces = " ".repeat(numSpacesInBetween)
        // TODO
        val extraSpaces = " ".repeat(numExtraSpaces)
        var newLine = "";
        for (i in 0 until symbols.size - 1) {
            newLine += symbols[i] + spaces
            if (i < numExtraSpaces) {
                newLine += " "
            }
        }
        writer.write(newLine)
        writer.newLine()
    }
    writer.close()
}

/**
 * Средняя (14 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 * Вернуть ассоциативный массив с числом слов больше 20, если 20-е, 21-е, ..., последнее слова
 * имеют одинаковое количество вхождений (см. также тест файла input/onegin.txt).
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> {
    // TODO
    val words = File(inputName).readText().lowercase(Locale.getDefault()).split(Regex("""[^а-яА-ЯёЁa-zA-Z]+"""))
    val wordCount = mutableMapOf<String, Int>()
    for (word in words) {
        if (word != "") {
            wordCount[word] = wordCount.getOrDefault(word, 0) + 1
        }
    }
    return wordCount.toList().sortedByDescending { it.second }.take(20).toMap()
}

/**
 * Средняя (14 баллов)
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    val text = File(inputName).readText()
    val writer = File(outputName).bufferedWriter()
    for (char in text) {
        if (char.lowercaseChar() in dictionary.keys) {
            if (char.isUpperCase()) writer.write(dictionary[char.lowercaseChar()]!!.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            })
            else writer.write(dictionary[char.lowercaseChar()]!!)
        } else writer.write(char.toString())
    }
    writer.close()
}

/**
 * Средняя (12 баллов)
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    val words = File(inputName).readLines()
    val max = words.maxBy { it.length }!!.length
    val result = mutableListOf<String>()
    for (word in words) {
        if (word.length == max) {
            var flag = true
            for (i in 0 until word.length - 1) {
                for (j in i + 1 until word.length) {
                    if (word[i].lowercaseChar() == word[j].lowercaseChar()) {
                        flag = false
                        break
                    }
                }
                if (!flag) break
            }
            if (flag) result.add(word)
        }
    }
    writer.write(result.joinToString())
    writer.close()
}

/**
 * Сложная (22 балла)
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * При решении этой и двух следующих задач полезно прочитать статью Википедии "Стек".
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
    <body>
        <p>
            Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
            Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
        </p>
        <p>
            Suspendisse <s>et elit in enim tempus iaculis</s>.
        </p>
    </body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    // TODO
    val input = File(inputName).readText()
    val output = File(outputName).bufferedWriter()
    val stack = Stack<String>()
    var isParagraph = false
    output.write("<html><body><p>")
    for (line in input.lines()) {
        if (line.isEmpty()) {
            if (isParagraph) {
                while (stack.isNotEmpty()) {
                    output.write("</${stack.pop()}>")
                }
                output.write("</p><p>")
            }
            isParagraph = false
        } else {
            isParagraph = true
            var i = 0
            while (i < line.length) {
                when {
                    line[i] == '*' && i + 1 < line.length && line[i + 1] == '*' -> {
                        if (stack.isNotEmpty() && stack.peek() == "b") {
                            output.write("</b>")
                            stack.pop()
                        } else {
                            output.write("<b>")
                            stack.push("b")
                        }
                        i += 2
                    }
                    line[i] == '*' -> {
                        if (stack.isNotEmpty() && stack.peek() == "i") {
                            output.write("</i>")
                            stack.pop()
                        } else {
                            output.write("<i>")
                            stack.push("i")
                        }
                        i++
                    }

                    line[i] == '~' && i + 1 < line.length && line[i + 1] == '~' -> {
                        if (stack.isNotEmpty() && stack.peek() == "s") {
                            output.write("</s>")
                            stack.pop()
                        } else {
                            output.write("<s>")
                            stack.push("s")
                        }
                        i += 2
                    }

                    else -> {
                        output.write(line[i].toString())
                        i++
                    }
                }
            }
        }
        output.newLine()
    }
    if (isParagraph) {
        while (stack.isNotEmpty()) {
            output.write("</${stack.pop()}>")
        }
        output.write("</p>")
    }
    output.write("</body></html>")
    output.close()
}

/**
 * Сложная (23 балла)
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body><p>...</p></body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
* Утка по-пекински
    * Утка
    * Соус
* Салат Оливье
    1. Мясо
        * Или колбаса
    2. Майонез
    3. Картофель
    4. Что-то там ещё
* Помидоры
* Фрукты
    1. Бананы
    23. Яблоки
        1. Красные
        2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
  <body>
    <p>
      <ul>
        <li>
          Утка по-пекински
          <ul>
            <li>Утка</li>
            <li>Соус</li>
          </ul>
        </li>
        <li>
          Салат Оливье
          <ol>
            <li>Мясо
              <ul>
                <li>Или колбаса</li>
              </ul>
            </li>
            <li>Майонез</li>
            <li>Картофель</li>
            <li>Что-то там ещё</li>
          </ol>
        </li>
        <li>Помидоры</li>
        <li>Фрукты
          <ol>
            <li>Бананы</li>
            <li>Яблоки
              <ol>
                <li>Красные</li>
                <li>Зелёные</li>
              </ol>
            </li>
          </ol>
        </li>
      </ul>
    </p>
  </body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    val input = File(inputName).readLines()
    val output = File(outputName).bufferedWriter()
    output.write("<html>\n  <body>\n    <p>\n")
    var list = 0
    var listType = 0
    for (line in input) {
        val space = line.takeWhile { it == ' ' }.length
        val text = line.dropWhile { it == ' ' }
        if (text.startsWith("* ")) {
            if (space > list) {
                listType = 1
                output.write("      ".repeat(list) + "<ul>\n")
            }
            if (space < list) {
                output.write("      ".repeat(list) + "</ul>\n")
            }
            output.write("      ".repeat(space) + "<li>${text.drop(2)}</li>\n")
            list = space
        } else if (text.matches(Regex("""\d+\.\s.*"""))) {
            if (space > list) {
                listType = 2
                output.write("      ".repeat(list) + "<ol>\n")
            }
            if (space < list) {
                output.write("      ".repeat(list) + "</ol>\n")
            }
            output.write("      ".repeat(space) + "<li>${text.dropWhile { it != ' ' }.drop(1)}</li>\n")
            list = space
        } else {
            if (listType == 1) output.write("      ".repeat(list) + "</ul>\n")
            if (listType == 2) output.write("      ".repeat(list) + "</ol>\n")
            output.write("      ".repeat(space) + "$text\n")
            list = 0
            listType = 0
        }
    }
    output.write("    </p>\n  </body>\n</html>")
    output.close()
}

/**
 * Очень сложная (30 баллов)
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    val input = File(inputName).readLines()
    val output = File(outputName).bufferedWriter()
    output.write("<html>\n  <body>\n")
    var list = 0
    var listType = 0
    var paragraph = false
    for (line in input) {
        val space = line.takeWhile { it == ' ' }.length
        val text = line.dropWhile { it == ' ' }
        if (text.startsWith("* ")) {
            if (space > list) {
                listType = 1
                output.write("      ".repeat(list) + "<ul>\n")
            }
            if (space < list) {
                output.write("      ".repeat(list) + "</ul>\n")
            }
            output.write("      ".repeat(space) + "<li>${text.drop(2)}</li>\n")
            list = space
        } else if (text.matches(Regex("""\d+\.\s.*"""))) {
            if (space > list) {
                listType = 2
                output.write("      ".repeat(list) + "<ol>\n")
            }
            if (space < list) {
                output.write("      ".repeat(list) + "</ol>\n")
            }
            output.write("      ".repeat(space) + "<li>${text.dropWhile { it != ' ' }.drop(1)}</li>\n")
            list = space
        } else {
            if (listType == 1) output.write("      ".repeat(list) + "</ul>\n")
            if (listType == 2) output.write("      ".repeat(list) + "</ol>\n")
            if (paragraph) output.write("    </p>\n")
            output.write("    <p>\n")
            output.write("      ".repeat(space) + "$text\n")
            list = 0
            listType = 0
            paragraph = true
        }
    }
    if (listType == 1) output.write("      ".repeat(list) + "</ul>\n")
    if (listType == 2) output.write("      ".repeat(list) + "</ol>\n")
    if (paragraph) output.write("    </p>\n")
    output.write("  </body>\n</html>")
    output.close()
}

/**
 * Средняя (12 баллов)
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
   19935
*    111
--------
   19935
+ 19935
+19935
--------
 2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
  235
*  10
-----
    0
+235
-----
 2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    val lhvLength = digitNumber(lhv)
    val rhvLength = digitNumber(rhv)
    val factors = rhv.toString().map { d -> lhv * (d - '0') }
    val product =
        (factors.mapIndexed { i, d -> d * 10.0.pow((rhvLength - i - 1).toDouble()) }.sum()).toInt()
    val lineLength = maxOf(lhvLength, rhvLength, digitNumber(product), factors.maxOf { digitNumber(it) }) + 1
    File(outputName).bufferedWriter().use { out ->
        out.write("$lhv".padEnd(lineLength - lhvLength + 1, ' '))
        out.newLine()
        out.write("*" + "$rhv".padStart(lineLength - rhvLength - 1, ' '))
        out.newLine()
        out.write("-".repeat(lineLength))
        out.newLine()
        out.write(" ".repeat(lineLength - digitNumber(factors.last())) + factors.last())
        out.newLine()
        factors.indices.forEach { i ->
            if (i != 0) {
                out.write(
                    "+" + " ".repeat(lineLength - digitNumber(factors[factors.lastIndex - i]) - i - 1)
                            + factors[factors.lastIndex - i]
                )
                out.newLine()
            }
        }
        out.write("-".repeat(lineLength))
        out.newLine()
        out.write(" ".repeat(lineLength - digitNumber(product)) + product)
    }
}


/**
 * Сложная (25 баллов)
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
  19935 | 22
 -198     906
 ----
    13
    -0
    --
    135
   -132
   ----
      3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    val output = File(outputName).bufferedWriter()
    val lhvStr = lhv.toString()
    val rhvStr = rhv.toString()
    val maxLength = max(lhvStr.length, rhvStr.length)
    output.write(" ".repeat(maxLength - lhvStr.length) + lhvStr + " | $rhvStr\n")
}

