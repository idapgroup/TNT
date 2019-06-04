package com.idapgroup.tnt.transform

import android.graphics.Matrix
import com.idapgroup.tnt.transform.MatrixTransformer.Attrs

/**
 * Apply configured [MatrixTransformer] to this [Matrix]
 */
fun Matrix.transform(
    size: Size,
    config: MatrixTransformer<DataAttrs>.() -> Unit
): Matrix {
    val transformer = MatrixTransformer<DataAttrs>()
    transformer.config()
    val attrs = DataAttrs(matrix = this, size = size)
    return transformer.transform(attrs).matrix
}

data class DataAttrs(
    override val matrix: Matrix,
    override val size: Size
) : Attrs<DataAttrs> {

    override fun modify(matrix: Matrix, size: Size) = DataAttrs(matrix, size)
}

open class MatrixTransformer<A: Attrs<A>> {

    interface Attrs<A> {
        val matrix: Matrix
        val size: Size
        fun modify(matrix: Matrix = this.matrix, size: Size = this.size): A
    }

    private val preTransforms = mutableListOf<(A) -> A>()
    private val postTransforms = mutableListOf<(A) -> A>()

    fun preTransform(transform: (A) -> A) {
        preTransforms += transform
    }

    fun postTransform(transform: (A) -> A) {
        postTransforms += transform
    }

    internal open fun transform(attrs: A, block: (A) -> A = { it }): A {
        val preAttrs = preTransforms.fold(attrs)
        return postTransforms.fold(block(preAttrs))
    }

    private fun List<(A) -> A>.fold(initial: A): A =
        fold(initial) { acc, op -> op(acc) }
}
