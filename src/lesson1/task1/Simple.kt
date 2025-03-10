@file:Suppress("UNUSED_PARAMETER")

package lesson1.task1

import junit.framework.TestCase.*
import lesson11.task1.UnsignedBigInteger
import lesson12.task1.TableFunction
import lesson2.task1.*
import lesson2.task2.*
import lesson3.task1.*
import lesson4.task1.*
import lesson5.task1.*
import lesson6.task1.*
import lesson7.task1.*
import lesson8.task1.*
import lesson9.task1.Cell
import lesson9.task1.Matrix
import org.junit.Assert.assertNotEquals
import java.io.File
import java.lang.ArithmeticException
import java.lang.IllegalArgumentException

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
 */


private fun approxEquals(expected: Line, actual: Line, delta: Double): Boolean =
    abs(expected.angle - actual.angle) <= delta && abs(expected.b - actual.b) <= delta

private fun assertApproxEquals(expected: Line, actual: Line, delta: Double = Math.ulp(10.0)) {
    assertTrue(approxEquals(expected, actual, delta))
}

private fun assertApproxNotEquals(expected: Line, actual: Line, delta: Double = Math.ulp(10.0)) {
    assertFalse(approxEquals(expected, actual, delta))
}

private fun approxEquals(expected: Point, actual: Point, delta: Double): Boolean =
    expected.distance(actual) <= delta

private fun assertApproxEquals(expected: Point, actual: Point, delta: Double = Math.ulp(10.0)) {
    assertTrue(approxEquals(expected, actual, delta))
}

private fun approxEquals(expected: Segment, actual: Segment, delta: Double): Boolean =
    expected.begin.distance(actual.begin) <= delta && expected.end.distance(actual.end) <= delta

private fun assertApproxEquals(expected: Segment, actual: Segment, delta: Double = Math.ulp(10.0)) {
    assertTrue(approxEquals(expected, actual, delta))
}

private fun approxEquals(expected: Circle, actual: Circle, delta: Double): Boolean =
    expected.center.distance(actual.center) <= delta && abs(expected.radius - actual.radius) <= delta

fun assertApproxEquals(expected: Circle, actual: Circle, delta: Double = Math.ulp(10.0)) {
    assertTrue(approxEquals(expected, actual, delta))
}
fun assertTrue(b: Boolean, function: () -> String) {
    if (!b) throw AssertionError(function())
}

fun main() {
    println("STARTED")

    assertEquals(
        mapOf<String, Double>(),
        averageStockPrice(listOf())
    )
    assertEquals(
        mapOf("MSFT" to 100.0, "NFLX" to 40.0),
        averageStockPrice(listOf("MSFT" to 100.0, "NFLX" to 40.0))
    )
    assertEquals(
        mapOf("MSFT" to 150.0, "NFLX" to 40.0),
        averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
    )
    assertEquals(
        mapOf("MSFT" to 150.0, "NFLX" to 45.0),
        averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0, "NFLX" to 50.0))
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
    val sageneMeters = 2.1336
    val arshinMeters = 0.7112
    val vershokMeters = 0.04445
    // Возврат суммы
    return (sagenes * sageneMeters) + (arshins * arshinMeters) + (vershoks * vershokMeters)
}

/**
 * Тривиальная (1 балл)
 *
 * Пользователь задает угол в градусах, минутах и секундах (например, 36 градусов 14 минут 35 секунд).
 * Вывести значение того же угла в радианах (например, 0.63256).
 */
fun angleInRadian(deg: Int, min: Int, sec: Int): Double {
    val minuteDegrees = 1.0 / 60.0
    val secondDegrees = 1.0 / 3600.0
    val degrees = deg + (min * minuteDegrees) + (sec * secondDegrees)
    return (degrees / 180) * PI // 360/180 = 2, = 2pi
}

/**
 * Тривиальная (1 балл)
 *
 * Найти длину отрезка, соединяющего точки на плоскости с координатами (x1, y1) и (x2, y2).
 * Например, расстояние между (3, 0) и (0, 4) равно 5
 */
fun trackLength(x1: Double, y1: Double, x2: Double, y2: Double): Double = sqrt(sqr(x2 - x1) + sqr(y2 - y1))

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
    val yearlyGrowth = (percent * 0.01) + 1
    return initial * yearlyGrowth.pow(3.0)
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
