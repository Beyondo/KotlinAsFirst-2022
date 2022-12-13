package lesson11.task1

/**
 * Класс "беззнаковое большое целое число".
 *
 * Общая сложность задания -- очень сложная, общая ценность в баллах -- 32.
 * Объект класса содержит целое число без знака произвольного размера
 * и поддерживает основные операции над такими числами, а именно:
 * сложение, вычитание (при вычитании большего числа из меньшего бросается исключение),
 * умножение, деление, остаток от деления,
 * преобразование в строку/из строки, преобразование в целое/из целого,
 * сравнение на равенство и неравенство
 */
class UnsignedBigInteger : Comparable<UnsignedBigInteger> {

    /**
     * Конструктор из строки
     */
    constructor(s: String) {
        numberStr = s
    }

    /**
     * Конструктор из целого
     */
    constructor(i: Int) {
        numberStr = i.toString()
    }

    /**
     * Сложение
     */
    operator fun plus(other: UnsignedBigInteger): UnsignedBigInteger {
        val first = numberStr.reversed()
        val second = other.numberStr.reversed()
        val result = StringBuilder()
        var carry = 0
        for (i in 0 until Math.max(first.length, second.length)) {
            val firstDigit = if (i < first.length) first[i].toString().toInt() else 0
            val secondDigit = if (i < second.length) second[i].toString().toInt() else 0
            val sum = firstDigit + secondDigit + carry
            result.append(sum % 10)
            carry = sum / 10
        }
        if (carry > 0) result.append(carry)
        return UnsignedBigInteger(result.toString().reversed())
    }

    /**
     * Вычитание (бросить ArithmeticException, если this < other)
     */
    operator fun minus(other: UnsignedBigInteger): UnsignedBigInteger {
        if (this < other) throw ArithmeticException()
        val first = numberStr.reversed()
        val second = other.numberStr.reversed()
        val result = StringBuilder()
        var carry = 0
        for (i in 0 until first.length) {
            val firstDigit = first[i].toString().toInt()
            val secondDigit = if (i < second.length) second[i].toString().toInt() else 0
            val diff = firstDigit - secondDigit - carry
            if (diff < 0) {
                result.append(diff + 10)
                carry = 1
            } else {
                result.append(diff)
                carry = 0
            }
        }
        return UnsignedBigInteger(result.toString().reversed().trimStart('0'))
    }

    /**
     * Умножение
     */
    operator fun times(other: UnsignedBigInteger): UnsignedBigInteger {
        val first = numberStr.reversed()
        val second = other.numberStr.reversed()
        val result = StringBuilder()
        var carry = 0
        for (i in 0 until first.length) {
            val firstDigit = first[i].toString().toInt()
            for (j in 0 until second.length) {
                val secondDigit = second[j].toString().toInt()
                val sum = firstDigit * secondDigit + carry
                result.append(sum % 10)
                carry = sum / 10
            }
            if (carry > 0) result.append(carry)
            carry = 0
        }
        return UnsignedBigInteger(result.toString().reversed())
    }

    /**
     * Деление
     */
    operator fun div(other: UnsignedBigInteger): UnsignedBigInteger {
        var first = numberStr.reversed()
        val second = other.numberStr.reversed()
        val result = StringBuilder()
        var carry = 0
        for (i in 0 until first.length) {
            val firstDigit = first[i].toString().toInt()
            val sum = firstDigit + carry * 10
            result.append(sum / second.toInt())
            carry = sum % second.toInt()
        }
        return UnsignedBigInteger(result.toString().reversed().trimStart('0'))
    }

    /**
     * Взятие остатка
     */
    operator fun rem(other: UnsignedBigInteger): UnsignedBigInteger {
        var first = numberStr.reversed()
        val second = other.numberStr.reversed()
        val result = StringBuilder()
        var carry = 0
        for (i in 0 until first.length) {
            val firstDigit = first[i].toString().toInt()
            val sum = firstDigit + carry * 10
            result.append(sum / second.toInt())
            carry = sum % second.toInt()
        }
        return UnsignedBigInteger(carry.toString())
    }

    /**
     * Сравнение на равенство (по контракту Any.equals)
     */
    override fun equals(other: Any?): Boolean {
        if (other is UnsignedBigInteger) {
            return numberStr == other.numberStr
        }
        return false
    }

    /**
     * Сравнение на больше/меньше (по контракту Comparable.compareTo)
     */
    override fun compareTo(other: UnsignedBigInteger): Int {
        if (numberStr.length > other.numberStr.length) return 1
        if (numberStr.length < other.numberStr.length) return -1
        for (i in 0 until numberStr.length) {
            if (numberStr[i] > other.numberStr[i]) return 1
            if (numberStr[i] < other.numberStr[i]) return -1
        }
        return 0
    }

    /**
     * Преобразование в строку
     */
    override fun toString(): String {
        return numberStr
    }

    /**
     * Преобразование в целое
     * Если число не влезает в диапазон Int, бросить ArithmeticException
     */
    fun toInt(): Int {
        if (numberStr.length > 10) throw ArithmeticException()
        return numberStr.toInt()
    }

    private lateinit var numberStr: String
}