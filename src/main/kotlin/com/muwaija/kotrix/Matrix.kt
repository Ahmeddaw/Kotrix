package com.muwaija.kotrix

import com.sun.istack.internal.logging.Logger
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.max

val logger = Logger.getLogger(Matrix::class.java)

data class Matrix<T : Number>(var elements: ArrayList<T> = arrayListOf(), var shape: IntArray = intArrayOf(1)) {
//    var shape: IntArray = IntArray(1)
//    var data : Array<T> = elements

    init {

        if (elements.isEmpty()) {
            elements = ArrayList()
            for (i in 0 until count()) {
                elements.add(0 as T)
            }
        }
    }

    private fun count(): Int {
        return count(shape)
    }

    private fun count(arg: IntArray): Int {
        var i = 1

        for (s in arg)
            i *= s
        return i

    }


    operator fun get(i: Int) = elements[i]
    operator fun get(vararg arg: Int): T {


        val required = getIndex(arg)
        if (required > elements.size || shape.size < arg.size)
            throw IndexOutOfBoundsException("Index Out Of Bound")

        return elements[required]

    }

    operator fun get(range: IntRange) {
        val list = Matrix<T>()

        for (i in range) {
            list.elements.add(this[i])
        }
    }

    operator fun set(vararg i: Int, d: T) {
        val required = getIndex(i)
        if (required > elements.size || shape.size < i.size)
            throw IndexOutOfBoundsException("Index Out Of Bound")

        elements[required] = d
    }

    private fun getIndex(i: IntArray): Int {
        var c = 0
        for (x in 0 until i.size - 1) {
            var num = 1
            for (j in x + 1 until shape.size) {
                num *= shape[j]
            }
            c += i[x] * num


        }
        c += i.last()
        return c
    }


    fun dimension() = shape.size

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Matrix<*>

        if (elements != other.elements) return false
        if (!Arrays.equals(shape, other.shape)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = elements.hashCode()
        result = 31 * result + Arrays.hashCode(shape)
        return result
    }


    override fun toString(): String {
        val out = StringBuilder()

//        if (shape.size == 2)
        for (x in 0 until shape[0]) {
            out.append("[")
            for (y in 0 until shape[1]) {
                logger.info("$x, $y")
                out.append(this[x, y])
                out.append(" ,")
            }

            out.append("]\n")
        }

        return out.toString() //super.toString()
    }


}

// [START Matrix operations]


// [START Multiplication operator]

/**
 * **************************************
 *
 *  Element by element Multiplication Operation
 *
 * **************************************
 */
@JvmName("IntMatrixMulDouble")
public operator fun Matrix<Int>.times(x: Double): Matrix<Double> {
    val res = Matrix<Double>(shape = shape)
    for (i in 0 until elements.size)
        res[i] = elements[i] * x
    return res
}


@JvmName("FloatMatrixMulDouble")
public operator fun Matrix<Float>.times(x: Double): Matrix<Double> {
    val res = Matrix<Double>(shape = shape)
    for (i in 0 until elements.size)
        res[i] = elements[i] * x
    return res
}

@JvmName("DoubleMatrixMulDouble")
public operator fun Matrix<Double>.times(x: Double): Matrix<Double> {
    val res = Matrix<Double>(shape = shape)
    for (i in 0 until elements.size)
        res[i] = elements[i] * x
    return res
}

@JvmName("IntMatrixTimesInt")
public operator fun Matrix<Int>.times(x: Int): Matrix<Int> {
    val res = Matrix<Int>(shape = shape)
    for (i in 0 until elements.size)
        res[i] = elements[i] * x
    return res
}

@JvmName("IntMatrixTimesFloat")
public operator fun Matrix<Int>.times(x: Float) = this * x.toDouble()

@JvmName("FloatMatrixTimesInt")
public operator fun Matrix<Float>.times(x: Int) = this * x.toDouble()

@JvmName("FloatMatrixTimesFloat")
public operator fun Matrix<Float>.times(x: Float) = this * x.toDouble()

@JvmName("DoubleMatrixTimesInt")
public operator fun Matrix<Double>.times(x: Int) = this * x.toDouble()

@JvmName("DoubleMatrixTimesFloat")
public operator fun Matrix<Double>.times(x: Float) = this * x.toDouble()


/**
 * **************************************
 *
 *  Matrix Multiplication Operation
 *
 * **************************************
 */

infix fun Matrix<Int>.matMul(x: Matrix<Int>): Matrix<Int> {
    if (!checkCanMul(shape, x.shape))
        throw ValueException("The matrix must be 2-d , or use times ( * ) for element by element multiplication")

    val result = Matrix<Int>(shape = intArrayOf(shape[0], x.shape[1]))
    for (i in 0 until shape[0]) {
        for (j in 0 until x.shape[1]) {
            for (d in 0 until x.shape[0]) {
                result[i, j] += this[i, d] * x[d, j]
            }
        }
    }

    return result
}

private fun checkCanMul(size1: IntArray, size2: IntArray): Boolean {
    if (size1.size > 2 || size2.size > 2)
        return false

    if (size1[1] != size2[0])
        return false

    return true
}

// [END Multiplication operator]



// [START Division operator]

/**
 * **************************************
 *
 *  Element by Element Division Operation
 *
 * **************************************
 */
@JvmName("IntMatrixDivDouble")
public operator fun Matrix<Int>.div(x: Double): Matrix<Double> {
    val res = Matrix<Double>(shape = shape)
    for (i in 0 until elements.size)
        res[i] = elements[i] / x
    return res
}


@JvmName("FloatMatrixDivDouble")
public operator fun Matrix<Float>.div(x: Double): Matrix<Double> {
    val res = Matrix<Double>(shape = shape)
    for (i in 0 until elements.size)
        res[i] = elements[i] / x
    return res
}

@JvmName("DoubleMatrixDivDouble")
public operator fun Matrix<Double>.div(x: Double): Matrix<Double> {
    val res = Matrix<Double>(shape = shape)
    for (i in 0 until elements.size)
        res[i] = elements[i] / x
    return res
}

@JvmName("FloatMatrixDivFloat")
public operator fun Matrix<Float>.div(x: Float): Matrix<Float> {
    val res = Matrix<Float>(shape = shape)
    for (i in 0 until elements.size)
        res[i] = elements[i] / x
    return res
}

@JvmName("IntMatrixDivInt")
public operator fun Matrix<Int>.div(x: Int) = this / x.toDouble()

@JvmName("IntMatrixDivFloat")
public operator fun Matrix<Int>.div(x: Float) = this / x.toDouble()

@JvmName("FloatMatrixDivInt")
public operator fun Matrix<Float>.div(x: Int) = this / x.toDouble()

@JvmName("DoubleMatrixDivInt")
public operator fun Matrix<Double>.div(x: Int) = this / x.toDouble()

@JvmName("DoubleMatrixDivFloat")
public operator fun Matrix<Double>.div(x: Float) = this / x.toDouble()


/**
 * **************************************
 *
 *  Matrix Division Operation
 *
 * **************************************
 */

// [END Division operator]


// [START Plus operator]
@JvmName("FloatMatrixPlusInt")
public operator fun Matrix<Float>.plus(x: Int): Matrix<Float> {
    val res = Matrix<Float>(shape = shape)
    for (i in 0 until elements.size)
        res[i] = elements[i] + x
    return res
}

@JvmName("FloatMatrixPlusFloat")
public operator fun Matrix<Float>.plus(x: Float): Matrix<Float> {
    val res = Matrix<Float>(shape = shape)
    for (i in 0 until elements.size)
        res[i] = elements[i] + x
    return res
}

@JvmName("FloatMatrixPlusDouble")
public operator fun Matrix<Float>.plus(x: Double): Matrix<Double> {
    val res = Matrix<Double>(shape = shape)
    for (i in 0 until elements.size)
        res[i] = elements[i] + x

    return res
}

@JvmName("DoubleMatrixPlusInt")
public operator fun Matrix<Double>.plus(x: Int): Matrix<Double> {
    val res = Matrix<Double>(shape = shape)
    for (i in 0 until elements.size)
        res[i] = elements[i] + x
    return res
}

@JvmName("DoubleMatrixPlusFloat")
public operator fun Matrix<Double>.plus(x: Float): Matrix<Double> {
    val res = Matrix<Double>(shape = shape)
    for (i in 0 until elements.size)
        res[i] = elements[i] + x
    return res
}

@JvmName("DoubleMatrixPlusDouble")
public operator fun Matrix<Double>.plus(x: Double): Matrix<Double> {
    val res = Matrix<Double>(shape = shape)
    for (i in 0 until elements.size)
        res[i] = elements[i] + x

    return res
}

@JvmName("IntMatrixPlusInt")
public operator fun Matrix<Int>.plus(x: Int): Matrix<Int> {
    val res = Matrix<Int>(shape = shape)
    for (i in 0 until elements.size)
        res[i] = elements[i] + x
    return res
}

@JvmName("IntMatrixPlusFloat")
public operator fun Matrix<Int>.plus(x: Float): Matrix<Float> {
    val res = Matrix<Float>(shape = shape)
    for (i in 0 until elements.size)
        res[i] = elements[i] + x
    return res
}

@JvmName("IntMatrixPlusDouble")
public operator fun Matrix<Int>.plus(x: Double): Matrix<Double> {
    val res = Matrix<Double>(shape = shape)
    for (i in 0 until elements.size)
        res[i] = elements[i] + x

    return res
}

// [END Plus operator]
// [START Minus operator]
// Minus
@JvmName("FloatMatrixMinusInt")
public operator fun Matrix<Float>.minus(x: Int): Matrix<Float> {
    val res = Matrix<Float>(shape = shape)
    for (i in 0 until elements.size)
        res[i] = elements[i] - x
    return res
}

@JvmName("FloatMatrixMinusFloat")
public operator fun Matrix<Float>.minus(x: Float): Matrix<Float> {
    val res = Matrix<Float>(shape = shape)
    for (i in 0 until elements.size)
        res[i] = elements[i] - x
    return res
}

@JvmName("FloatMatrixMinusDouble")
public operator fun Matrix<Float>.minus(x: Double): Matrix<Double> {
    val res = Matrix<Double>(shape = shape)
    for (i in 0 until elements.size)
        res[i] = elements[i] - x

    return res
}

@JvmName("DoubleMatrixMinusInt")
public operator fun Matrix<Double>.minus(x: Int): Matrix<Double> {
    val res = Matrix<Double>(shape = shape)
    for (i in 0 until elements.size)
        res[i] = elements[i] - x
    return res
}

@JvmName("DoubleMatrixMinusFloat")
public operator fun Matrix<Double>.minus(x: Float): Matrix<Double> {
    val res = Matrix<Double>(shape = shape)
    for (i in 0 until elements.size)
        res[i] = elements[i] - x
    return res
}

@JvmName("DoubleMatrixMinusDouble")
public operator fun Matrix<Double>.minus(x: Double): Matrix<Double> {
    val res = Matrix<Double>(shape = shape)
    for (i in 0 until elements.size)
        res[i] = elements[i] - x

    return res
}

@JvmName("IntMatrixMinusInt")
public operator fun Matrix<Int>.minus(x: Int): Matrix<Int> {
    val res = Matrix<Int>(shape = shape)
    for (i in 0 until elements.size)
        res[i] = elements[i] - x
    return res
}

@JvmName("IntMatrixMinusFloat")
public operator fun Matrix<Int>.minus(x: Float): Matrix<Float> {
    val res = Matrix<Float>(shape = shape)
    for (i in 0 until elements.size)
        res[i] = elements[i] - x
    return res
}

@JvmName("IntMatrixMinusDouble")
public operator fun Matrix<Int>.minus(x: Double): Matrix<Double> {
    val res = Matrix<Double>(shape = shape)
    for (i in 0 until elements.size)
        res[i] = elements[i] - x

    return res
}
// [END Minus operator]

// [END Matrix operations]


public fun <T : Number> Matrix<T>.toDouble(): Matrix<Double> {
    val matrix = Matrix<Double>(shape = shape)
    for (e in 0 until elements.size)
        matrix[e] = elements[e].toDouble()

    return matrix

}


public fun <T : Number> Matrix<T>.toInt(): Matrix<Int> {
    val matrix = Matrix<Int>(shape = shape)
    for (e in 0 until elements.size)
        matrix[e] = elements[e].toInt()

    return matrix

}


@JvmName("MiniValueDoubleMatrix")
public fun Matrix<Double>.min(): Double? {
    val comparator = kotlin.Comparator<Double> { n1, n2 -> (n1 - n2).toInt() }
    return elements.minWith(comparator)
}

@JvmName("MiniValueIntMatrix")
public fun Matrix<Float>.min(): Float? {
    val comparator = kotlin.Comparator<Float> { n1, n2 -> (n1 - n2).toInt() }
    return elements.minWith(comparator)
}

@JvmName("MinValueFloatMatrix")
public fun Matrix<Int>.min(): Int? {
    val comparator = kotlin.Comparator<Int> { n1, n2 -> n1 - n2 }
    return elements.minWith(comparator)
}


@JvmName("MaxValueDoubleMatrix")
public fun Matrix<Double>.max(): Double? {
    val comparator = kotlin.Comparator<Double> { n1, n2 -> (n2 - n1).toInt() }
    return elements.minWith(comparator)
}

@JvmName("MaxValueIntMatrix")
public fun Matrix<Float>.max(): Float? {
    val comparator = kotlin.Comparator<Float> { n1, n2 -> (n2 - n1).toInt() }
    return elements.minWith(comparator)
}

@JvmName("MaxValueFloatMatrix")
public fun Matrix<Int>.max(): Int? {
    val comparator = kotlin.Comparator<Int> { n1, n2 -> n2 - n1 }
    return elements.minWith(comparator)
}


public fun matrixOf(vararg elements: Int): Matrix<Int> {
    return Matrix(elements.toList() as ArrayList<Int>, intArrayOf(1))
}

@JvmName("matrixOfFloat")
public fun matrixOf(vararg elements: Float): Matrix<Float> {
    return Matrix(elements.toList() as ArrayList<Float>, intArrayOf(1))
}

@JvmName("matrixOfDouble")
public fun matrixOf(vararg elements: Double): Matrix<Double> {
    return Matrix(elements.toList() as ArrayList<Double>, intArrayOf(1))
}

public fun matrixOf(vararg elements: ArrayList<Int>): Matrix<Int> {
    val list = arrayListOf<Int>()
    var size = 0
    elements.forEach {
        list += it
        size = max(size, it.size)
    }
    return Matrix(list, intArrayOf(1))
}

public operator fun Matrix<Int>.plus(p: Matrix<Int>): Matrix<Int> {

    if (!this.shape.contentEquals(p.shape))
        throw Exception("Array size error : cannot sum ${this.shape} with ${p.shape}")

    val res = Matrix<Int>(shape = shape)
    for (i in 0 until elements.size) {
        res[i] = this[i] + p[i]

    }

    return res

}


public fun Matrix<Float>.map(start: Float, end: Float): Matrix<Float> {
    val max = max() ?: 0F
    val min = min() ?: 0F
    val diff = max - min
    val toDiff = end - start
    val scale = diff / toDiff
    return ((this - min) / scale) + start
}

public fun Matrix<Double>.map(start: Double, end: Double): Matrix<Double> {
    val max = max() ?: 0.0
    val min = min() ?: 0.0
    val diff = max - min
    val toDiff = end - start
    val scale = diff / toDiff
    return ((this - min) / scale) + start
}

@JvmName("DoubleMatrixMapFloat")
public fun Matrix<Double>.map(start: Float, end: Float): Matrix<Double> {
    return map(start.toDouble(), end.toDouble())
}

@JvmName("DoubleMatrixMapInt")
public fun Matrix<Double>.map(start: Int, end: Int): Matrix<Double> {
    return map(start.toDouble(), end.toDouble())
}

