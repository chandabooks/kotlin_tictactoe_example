package org.example

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val model = TicTacToeModel()
    val view = TicTacToeView()
    val viewmodel = TicTacToeViewModel(model, view)
    viewmodel.play()
}

class TicTacToeModel {
    val board = Array(3) { CharArray(3) { ' ' } }
    var currentPlayer = 'X'

    fun isBoardFull(): Boolean {
        for (row in board) {
            if (row.contains(' ')) return false
        }
        return true
    }

    fun checkWin(): Boolean {
        // Check rows and columns
        for (i in 0..2) {
            if ((board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) ||
                (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer)) {
                return true
            }
        }
        // Check diagonals
        if ((board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) ||
            (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer)) {
            return true
        }
        return false
    }

    fun switchPlayer() {
        currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
    }
}


class TicTacToeViewModel(private val model: TicTacToeModel, private val view: TicTacToeView) {
    fun play() {
        do {
            single_play()
        } while (view.getContinue())

    }

    fun single_play() {
        view.printMessage("Welcome to Tic-Tac-Toe!")
        view.printBoard(model.board)

        while (true) {

            var (row, col)  = view.getRowCol(model.currentPlayer)

            if (row !in 0..2 || col !in 0..2 || model.board[row][col] != ' ') {
                view.printMessage("This move is not valid.")
                continue
            }

            model.board[row][col] = model.currentPlayer
            view.printBoard(model.board)

            if (model.checkWin()) {
                view.printMessage("Player ${model.currentPlayer} wins!")
                break
            }

            if (model.isBoardFull()) {
                view.printMessage("The game is a draw!")
                break
            }

            model.switchPlayer()
        }
    }
}

class TicTacToeView {
    fun printBoard(board: Array<CharArray>) {
        for (row in board) {
            println(row.joinToString("|"))
        }
        println("-".repeat(5))
    }

    fun printMessage(message: String) {
        println(message)
    }

    fun getContinue(): Boolean {
        print("Play Another Game?(Y/N)")
        val input = readLine()?.uppercase()
        return (input == "Y")
    }

    fun getRowCol(player:Char): Pair<Int, Int> {
        // This will loop till the user inputs two valid number
        while (true) {
            printMessage("Player ${player}'s turn. Enter your move (row and column) : ")
            val input = readLine()?.split(' ')
            if (null == input) {
                printMessage("No Input received")
                continue
            }
            val (row, col) = input.map{ it.toIntOrNull() }
            if (null == row || null == col) {
                printMessage("Bad Input, please enter the row (0..2) and column (0..2)")
            } else {
                return Pair(row, col)
            }
        }
    }
 }


