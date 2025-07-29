# FloodFill

## Result:
See [complete implementation](https://github.com/danie1estrada/FloodFill/blob/main/GridAdapter.kt)

<img src="/flood_fill.gif" width="300px" />

## Pending 
### 1. Calculate the row and column given the position
Given the position of a element in a RecyclerView, calculate its index for row and column on a GridLayout.
```kotlin
val row = position / ROW_COUNT
val column = position / COLUMN_COUNT
```

### 2. Implement the flood fill algorithm
Approach using a Depth-First Search algorithm by recursively check adjacent cells in 4 directions (up, down, left and right).
```kotlin
suspend fun floodFill(row: Int, column: Int) {
    if ((row < 0 || row == ROWS) || (column < 0 || column == COLUMNS)) return

    if (grid[row][column] == WHITE) {
        grid[row][column] = COLORED
        val position = (row * ROWS) + column

        // Notify the view that an element has changed and update its color
        notifyItemChanged(position)

        // Recursively check adjacent cells 
        floodFill(row + 1, column)
        floodFill(row, column + 1)
        floodFill(row - 1, column)
        floodFill(row, column - 1)
    }
}
```

## Bonus Tasks
### Add a reset button
```kotlin
fun reset() {
    grid.forEach { array ->
        for (i in array.indices) {
            array[i] = Random.nextInt(0, 2)
        }
    }
    notifyDataSetChanged()
}
```
### Animate the fill
Adding a short delay after calling *notifyItemChanget(position)* to display visually how the Flood Fill algorithm is working.
```kotlin
suspend fun floodFill(row: Int, column: Int) {
    ...

    if (grid[row][column] == WHITE) {
        ...
        notifyItemChanged(position)
        delay(5)
        ...
        // Recursive calls
    }
}
```


