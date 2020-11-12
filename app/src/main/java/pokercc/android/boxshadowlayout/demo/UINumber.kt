package pokercc.android.boxshadowlayout.demo

import android.content.res.Resources
import android.util.TypedValue

class Px(private val value: Number) : Number() {
    fun toDp(): Dp = Dp(value.toFloat() / Resources.getSystem().displayMetrics.density)
    override fun toByte(): Byte {
        return value.toByte()
    }

    override fun toChar(): Char {
        return value.toChar()
    }

    override fun toDouble(): Double {
        return value.toDouble()
    }

    override fun toFloat(): Float {
        return value.toFloat()
    }

    override fun toInt(): Int {
        return value.toInt()
    }

    override fun toLong(): Long {
        return value.toLong()
    }

    override fun toShort(): Short {
        return value.toShort()
    }
}

class Dp(private val value: Number) : Number() {
    fun toPx() = Px(toInt())
    override fun toByte(): Byte = toFloat().toByte()

    override fun toChar(): Char = toFloat().toChar()

    override fun toDouble(): Double = toFloat().toDouble()

    override fun toFloat(): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value.toFloat(),
            Resources.getSystem().displayMetrics
        )
    }

    override fun toInt(): Int = toFloat().toInt()

    override fun toLong(): Long = toFloat().toLong()

    override fun toShort(): Short = toInt().toShort()

}