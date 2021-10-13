package com.example.tictactoe

import android.util.Log

class MyGameManager {

    //need to simulate click on player 2!!!
    // vuah-la:    view.performClick()

        private var currentPlayer = 1
        var player1Points = 0
        var player2Points = 0

    val currentPlayerMark: String
            get() {
                return if (currentPlayer == 1) "X" else "O"
            }

        private var state = arrayOf( // 2D Array
            intArrayOf(0, 0, 0),
            intArrayOf(0, 0, 0),
            intArrayOf(0, 0, 0)
        )

         fun makeMove(whichCell: WhichCell): Lines? {
            state[whichCell.row][whichCell.column] = currentPlayer
            val winningLine = hasGameEnded()

            if (winningLine == null) {
                currentPlayer = 3 - currentPlayer
                //here becomes comp turn
                if (currentPlayer%2 == 0) {
                    //simulate click??
                    Log.d("-----------------","-----------------------------------------------")

                }
            } else {
                when (currentPlayer) {
                    1 -> player1Points++
                    2 -> player2Points++
                }
            }

            return winningLine
        }

        fun reset() {
            state = arrayOf(
                intArrayOf(0, 0, 0),
                intArrayOf(0, 0, 0),
                intArrayOf(0, 0, 0)
            )
            currentPlayer = 1
        }

        private fun hasGameEnded(): Lines? {
            if (state[0][0] == currentPlayer && state[0][1] == currentPlayer && state[0][2] == currentPlayer) {
                return Lines.ROW_0
            } else if (state[1][0] == currentPlayer && state[1][1] == currentPlayer && state[1][2] == currentPlayer) {
                return Lines.ROW_1
            } else if (state[2][0] == currentPlayer && state[2][1] == currentPlayer && state[2][2] == currentPlayer) {
                return Lines.ROW_2
            } else if (state[0][0] == currentPlayer && state[1][0] == currentPlayer && state[2][0] == currentPlayer) {
                return Lines.COLUMN_0
            } else if (state[0][1] == currentPlayer && state[1][1] == currentPlayer && state[2][1] == currentPlayer) {
                return Lines.COLUMN_1
            } else if (state[0][2] == currentPlayer && state[1][2] == currentPlayer && state[2][2] == currentPlayer) {
                return Lines.COLUMN_2
            } else if (state[0][0] == currentPlayer && state[1][1] == currentPlayer && state[2][2] == currentPlayer) {
                return Lines.DIAGONAL_LEFT
            } else if (state[0][2] == currentPlayer && state[1][1] == currentPlayer && state[2][0] == currentPlayer) {
                return Lines.DIAGONAL_RIGHT
            }
            return null
        }


}
