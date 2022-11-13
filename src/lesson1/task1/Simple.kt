@file:Suppress("UNUSED_PARAMETER")

package lesson1.task1

import junit.framework.TestCase.*
import lesson2.task1.*
import lesson2.task2.*
import lesson3.task1.*
import lesson4.task1.*
import lesson5.task1.*
import lesson6.task1.*
import lesson7.task1.*
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertNotEquals
import java.io.File

import kotlin.math.*

// Урок 1: простые функции
// Максимальное количество баллов = 5
// Рекомендуемое количество баллов = 4

/**
 * Пример
 *
 * Вычисление квадрата целого числа
 */
fun sqr(x: Int) = x * x

/**
 * Пример
 *
 * Вычисление квадрата вещественного числа
 */
fun sqr(x: Double) = x * x

/**
 * Пример
 *
 * Вычисление дискриминанта квадратного уравнения
 */
fun discriminant(a: Double, b: Double, c: Double) = sqr(b) - 4 * a * c

/**
 * Пример
 *
 * Поиск одного из корней квадратного уравнения
 */
fun quadraticEquationRoot(a: Double, b: Double, c: Double) =
    (-b + sqrt(discriminant(a, b, c))) / (2 * a)

/**
 * Пример
 *
 * Поиск произведения корней квадратного уравнения
 */
fun quadraticRootProduct(a: Double, b: Double, c: Double): Double {
    val sd = sqrt(discriminant(a, b, c))
    val x1 = (-b + sd) / (2 * a)
    val x2 = (-b - sd) / (2 * a)
    return x1 * x2 // Результат
}

/**
 * Пример главной функции
 */ private fun checkHtmlListsExample() {
    val result = File("temp.html").readText().replace(Regex("[\\s\\n\\t]"), "")
    val expected =
        """
                    <html>
                      <body>
                        <p>
                          <ul>
                            <li>Утка по-пекински
                              <ul>
                                <li>Утка</li>
                                <li>Соус</li>
                              </ul>
                            </li>
                            <li>Салат Оливье
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
                    """.trimIndent().replace(Regex("[\\s\\n\\t]"), "")
    assertEquals(expected, result)

    File("temp.html").delete()
}

fun main() {
    println("STARTED")
    fun test(lhv: Int, rhv: Int, res: String) {
        printMultiplicationProcess(lhv, rhv, "temp.txt")
        assertFileContent("temp.txt", res.trimIndent())
        File("temp.txt").delete()
    }

    test(
        19935,
        111,
        """
                19935
             *    111
             --------
                19935
             + 19935
             +19935
             --------
              2212785
             """
    )

    test(
        12345,
        76,
        """
               12345
             *    76
             -------
               74070
             +86415
             -------
              938220
             """
    )

    test(
        12345,
        6,
        """
              12345
             *    6
             ------
              74070
             ------
              74070
             """
    )

    println("ENDED")
}

fun assertFileContent(filePath: String, expected: String) {
    val file = File(filePath)
    val content = file.readLines().joinToString("\n")
    assertEquals(expected, content)
}

/**
 * Тривиальная (3 балла).
 *
 * Задача имеет повышенную стоимость как первая в списке.
 *
 * Пользователь задает время в часах, минутах и секундах, например, 8:20:35.
 * Рассчитать время в секундах, прошедшее с начала суток (30035 в данном случае).
 */
fun seconds(hours: Int, minutes: Int, seconds: Int): Int = (hours * 3600) + (minutes * 60) + seconds

/**
 * Тривиальная (1 балл)
 *
 * Пользователь задает длину отрезка в саженях, аршинах и вершках (например, 8 саженей 2 аршина 11 вершков).
 * Определить длину того же отрезка в метрах (в данном случае 18.98).
 * 1 сажень = 3 аршина = 48 вершков, 1 вершок = 4.445 см.
 */
fun lengthInMeters(sagenes: Int, arshins: Int, vershoks: Int): Double {
    // Взял из википедии: https://en.wikipedia.org/wiki/Obsolete_Russian_units_of_measurement
    val sagene_meters = 2.1336
    val arshin_meters = 0.7112
    val vershok_meters = 0.04445
    // Возврат суммы
    return (sagenes * sagene_meters) + (arshins * arshin_meters) + (vershoks * vershok_meters)
}

/**
 * Тривиальная (1 балл)
 *
 * Пользователь задает угол в градусах, минутах и секундах (например, 36 градусов 14 минут 35 секунд).
 * Вывести значение того же угла в радианах (например, 0.63256).
 */
fun angleInRadian(deg: Int, min: Int, sec: Int): Double {
    val minute_degrees = 1.0 / 60.0
    val second_degrees = 1.0 / 3600.0
    val degrees = deg + (min * minute_degrees) + (sec * second_degrees)
    return (degrees / 180) * PI // 360/180 = 2, = 2pi
}

/**
 * Тривиальная (1 балл)
 *
 * Найти длину отрезка, соединяющего точки на плоскости с координатами (x1, y1) и (x2, y2).
 * Например, расстояние между (3, 0) и (0, 4) равно 5
 */
fun trackLength(x1: Double, y1: Double, x2: Double, y2: Double): Double = sqrt(sqr(x2 - x1 ) + sqr(y2 - y1))

/**
 * Простая (2 балла)
 *
 * Пользователь задает целое число, больше или равно 100 (например, 3801).
 * Определить третью цифру справа в этом числе (в данном случае 8).
 */
fun thirdDigit(number: Int): Int = (number / 100) % 10

/**
 * Простая (2 балла)
 *
 * Поезд вышел со станции отправления в h1 часов m1 минут (например в 9:25) и
 * прибыл на станцию назначения в h2 часов m2 минут того же дня (например в 13:01).
 * Определите время поезда в пути в минутах (в данном случае 216).
 */
fun travelMinutes(hoursDepart: Int, minutesDepart: Int, hoursArrive: Int, minutesArrive: Int): Int =
    ((hoursArrive - hoursDepart) * 60) + (minutesArrive - minutesDepart)
/**
 * Простая (2 балла)
 *
 * Человек положил в банк сумму в s рублей под p% годовых (проценты начисляются в конце года).
 * Сколько денег будет на счету через 3 года (с учётом сложных процентов)?
 * Например, 100 рублей под 10% годовых превратятся в 133.1 рубля
 */
fun accountInThreeYears(initial: Int, percent: Int): Double
{
    val yearly_growth = (percent * 0.01) + 1
    return initial * yearly_growth.pow(3.0)
}

/**
 * Простая (2 балла)
 *
 * Пользователь задает целое трехзначное число (например, 478).
 * Необходимо вывести число, полученное из заданного перестановкой цифр в обратном порядке (например, 874).
 */
fun numberRevert(number: Int): Int
{
    var value = number // изменяемая копия
    var reversed = 0
    while(value > 0) {
        reversed = 10 * reversed + value % 10
        value /= 10
    }
    return reversed
}
