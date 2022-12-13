@file:Suppress("UNUSED_PARAMETER")

package lesson12.task1

import kotlin.math.abs

/**
 * Класс "табличная функция".
 *
 * Общая сложность задания -- средняя, общая ценность в баллах -- 16.
 * Объект класса хранит таблицу значений функции (y) от одного аргумента (x).
 * В таблицу можно добавлять и удалять пары (x, y),
 * найти в ней ближайшую пару (x, y) по заданному x,
 * найти (интерполяцией или экстраполяцией) значение y по заданному x.
 *
 * Класс должен иметь конструктор по умолчанию (без параметров).
 */
class TableFunction {

    /**
     * Количество пар в таблице
     */
    val size: Int get() = table.size

    /**
     * Добавить новую пару.
     * Вернуть true, если пары с заданным x ещё нет,
     * или false, если она уже есть (в этом случае перезаписать значение y)
     */
    fun add(x: Double, y: Double): Boolean {
        return if (table.containsKey(x)) {
            table[x] = y
            false
        } else {
            table[x] = y
            true
        }
    }

    /**
     * Удалить пару с заданным значением x.
     * Вернуть true, если пара была удалена.
     */
    fun remove(x: Double): Boolean {
        return if (table.containsKey(x)) {
            table.remove(x)
            true
        } else {
            false
        }
    }

    /**
     * Вернуть коллекцию из всех пар в таблице
     */
    fun getPairs(): Collection<Pair<Double, Double>> = table.toList()

    /**
     * Вернуть пару, ближайшую к заданному x.
     * Если существует две ближайшие пары, вернуть пару с меньшим значением x.
     * Если таблица пуста, бросить IllegalStateException.
     */
    fun findPair(x: Double): Pair<Double, Double>? {
        var min = Double.MAX_VALUE
        var result: Pair<Double, Double>? = null
        for ((key, value) in table) {
            if (abs(key - x) < min) {
                min = abs(key - x)
                result = key to value
            }
        }
        return result
    }

    /**
     * Вернуть значение y по заданному x.
     * Если в таблице есть пара с заданным x, взять значение y из неё.
     * Если в таблице есть всего одна пара, взять значение y из неё.
     * Если таблица пуста, бросить IllegalStateException.
     * Если существуют две пары, такие, что x1 < x < x2, использовать интерполяцию.
     * Если их нет, но существуют две пары, такие, что x1 < x2 < x или x > x2 > x1, использовать экстраполяцию.
     */
    fun getValue(x: Double): Double {
        if (table.isEmpty()) throw IllegalStateException()
        if (table.size == 1) return table.values.first()
        val pair = findPair(x)
        if (pair != null) return pair.second
        val (x1, y1) = table.toList().sortedBy { it.first }.first { it.first > x }
        val (x2, y2) = table.toList().sortedBy { it.first }.first { it.first < x }
        return y1 + (y2 - y1) * (x - x1) / (x2 - x1)
    }

    /**
     * Таблицы равны, если в них одинаковое количество пар,
     * и любая пара из второй таблицы входит также и в первую
     */
    override fun equals(other: Any?): Boolean {
        if (other is TableFunction) {
            if (table.size != other.table.size) return false
            for ((key, value) in other.table) {
                if (!table.containsKey(key) || table[key] != value) return false
            }
            return true
        }
        return false
    }

    private val table = HashMap<Double, Double>()
}