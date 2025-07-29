package com.pinterest.blankproject.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pinterest.interview.R
import com.pinterest.interview.databinding.LayoutGridItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class GridAdapter(
    private val coroutineScope: CoroutineScope
): RecyclerView.Adapter<GridAdapter.ViewHolder>() {
    private val grid = List(ROWS) {
        IntArray(COLUMNS) { Random.nextInt(0, 2) }
    }

    fun reset() {
        grid.forEach { array ->
            for (i in array.indices) {
                array[i] = Random.nextInt(0, 2)
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(
        LayoutGridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val row = position / ROWS
        val column = position % COLUMNS

        holder.bind(grid[row][column], position)
    }

    override fun getItemCount(): Int = ROWS * COLUMNS

    inner class ViewHolder(val binding: LayoutGridItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(value: Int, position: Int) {
            val color = when (value) {
                WHITE -> R.color.white
                BLACK -> R.color.black
                else -> R.color.teal_700
            }

            binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, color))
            binding.root.setOnClickListener {
                val row = position / ROWS
                val column = position % COLUMNS
                if (value == WHITE) {
                    coroutineScope.launch {
                        floodFill(row, column)
                    }
                }
            }
        }

        suspend fun floodFill(row: Int, column: Int) {
            if ((row < 0 || row == ROWS) || (column < 0 || column == COLUMNS)) return

            if (grid[row][column] == WHITE) {
                grid[row][column] = COLORED
                val position = (row * ROWS) + column
                notifyItemChanged(position)
                delay(5)

                floodFill(row + 1, column)
                floodFill(row, column + 1)
                floodFill(row - 1, column)
                floodFill(row, column - 1)
            }
        }
    }

    companion object {
        const val ROWS = 15
        const val COLUMNS = 15

        const val WHITE = 0
        const val BLACK = 1
        const val COLORED = 2
    }
}