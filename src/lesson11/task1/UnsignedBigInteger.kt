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
        for (i in first.indices) {
            val firstDigit = first[i].toString().toInt()
            val secondDigit = if (i < second.length) second[i].toString().toInt() else 0
            val diff = firstDigit - secondDigit - carry
            carry = if (diff < 0) {
                result.append(diff + 10)
                1
            } else {
                result.append(diff)
                0
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
        for (i in first.indices) {
            val firstDigit = first[i].toString().toInt()
            for (j in second.indices) {
                val secondDigit = second[j].toString().toInt()
                val product = firstDigit * secondDigit + carry
                if (i + j < result.length) {
                    val sum = product + result[i + j].toString().toInt()
                    result.setCharAt(i + j, (sum % 10).toString()[0])
                    carry = sum / 10
                } else {
                    result.append(product % 10)
                    carry = product / 10
                }
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
        var first = numberStr
        val second = other.numberStr
        var result = ""
        while (first.length >= second.length) {
            var i = 0
            while (first >= second) {
                first = (UnsignedBigInteger(first) - UnsignedBigInteger(second)).numberStr
                i++
            }
            result += i
            first = "0$first"
        }
        return UnsignedBigInteger(result)
    }

    /**
     * Взятие остатка
     */
    operator fun rem(other: UnsignedBigInteger): UnsignedBigInteger {
        var first = numberStr
        val second = other.numberStr
        while (first.length >= second.length) {
            while (first >= second)
                first = (UnsignedBigInteger(first) - UnsignedBigInteger(second)).numberStr
            first = "0$first"
        }
        return UnsignedBigInteger(first)
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
        for (i in numberStr.indices) {
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

    private var numberStr: String
}