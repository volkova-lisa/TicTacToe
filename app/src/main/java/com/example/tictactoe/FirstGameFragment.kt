package com.example.tictactoe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.tictactoe.databinding.FragmentFirstGameBinding
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class FirstGameFragment : Fragment() {


    lateinit var gameManager: MyGameManager

    private var _binding: FragmentFirstGameBinding? = null
    val mBinding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            _binding = FragmentFirstGameBinding.inflate(layoutInflater, container, false)

            gameManager = MyGameManager()

            mBinding.one.setOnClickListener { onBoxClicked(mBinding.one, WhichCell(0, 0)) }
            mBinding.two.setOnClickListener { onBoxClicked(mBinding.two, WhichCell(0, 1)) }
            mBinding.three.setOnClickListener { onBoxClicked(mBinding.three, WhichCell(0, 2)) }
            mBinding.four.setOnClickListener { onBoxClicked(mBinding.four, WhichCell(1, 0)) }
            mBinding.five.setOnClickListener { onBoxClicked(mBinding.five, WhichCell(1, 1)) }
            mBinding.six.setOnClickListener { onBoxClicked(mBinding.six, WhichCell(1, 2)) }
            mBinding.seven.setOnClickListener { onBoxClicked(mBinding.seven, WhichCell(2, 0)) }
            mBinding.eight.setOnClickListener { onBoxClicked(mBinding.eight, WhichCell(2, 1)) }
            mBinding.nine.setOnClickListener { onBoxClicked(mBinding.nine, WhichCell(2, 2)) }

        return mBinding.root

    }

    // WHY DONT I JUST SIMULATE ONBOX CLIIIIIIIIIIICK
    fun onBoxClicked(box: TextView, whichCell: WhichCell) {
        if (box.text.isEmpty()) {
            box.text = gameManager.currentPlayerMark
            val winningLine = gameManager.makeMove(whichCell)
            if (winningLine != null) {
                disableBoxes()
                mBinding.startNewGameButton.visibility = View.VISIBLE
                showWinner(winningLine)
                gameManager.currentPlayer = 3 - gameManager.currentPlayer
            }
            //передаем эстафету компьютеру
            var emptyCellsNum = emptyCellsNum()
            var emptyCellsList = emptyCellsList(emptyCellsNum)

            //val message: Deferred<String> = async{ getMessage()}
//            var compMoved: Deferred<Boolean> =
//                async { computerMakesMove(box, whichCell, emptyCellsList) }
            computerMakesMove(box, whichCell, emptyCellsList)
        }
        mBinding.startNewGameButton.setOnClickListener {
        mBinding.startNewGameButton.visibility = View.GONE
        gameManager.reset()
        resetboxes()
        }

    }

    private fun computerMakesMove(
        box: TextView,
        whichCell: WhichCell,
        list: MutableList<TextView>
    ): Boolean {
        GlobalScope.launch(Dispatchers.IO) {
            //Log.d("0000000000000000000", "ss" + emptyCellsNum())
            val winningLine = gameManager.makeMove(whichCell)
            delay(1000)
            //not to wait for so long
            //delay((1000..3000).random().toLong())
            if (winningLine != null) {
                showWinner(winningLine)
                disableBoxes()
                mBinding.startNewGameButton.visibility = View.VISIBLE
                gameManager.currentPlayer = 3 - gameManager.currentPlayer

            } else {
                if (list.size != 0) {
                    val compBox = (0 until list.size).random()
                    Log.d("6666666666666666", "hh" + compBox + "+" + list)
                    list[compBox]!!.text = "0"
                }
            }
        }
        return true
    }

    fun emptyCellsList(i: Int): MutableList<TextView> {
        var mutList = mutableListOf<TextView>()
        if (mBinding.one.text.isEmpty()) mutList.add(mBinding.one)
        if (mBinding.two.text.isEmpty()) mutList.add(mBinding.two)
        if (mBinding.three.text.isEmpty()) mutList.add(mBinding.three)
        if (mBinding.four.text.isEmpty()) mutList.add(mBinding.four)
        if (mBinding.five.text.isEmpty()) mutList.add(mBinding.five)
        if (mBinding.six.text.isEmpty()) mutList.add(mBinding.six)
        if (mBinding.seven.text.isEmpty()) mutList.add(mBinding.seven)
        if (mBinding.eight.text.isEmpty()) mutList.add(mBinding.eight)
        if (mBinding.nine.text.isEmpty()) mutList.add(mBinding.nine)
        return mutList
    }


    fun emptyCellsNum(): Int {
        var num = 0
        if (mBinding.one.text.isEmpty()) num++
        if (mBinding.two.text.isEmpty()) num++
        if (mBinding.three.text.isEmpty()) num++
        if (mBinding.four.text.isEmpty()) num++
        if (mBinding.five.text.isEmpty()) num++
        if (mBinding.six.text.isEmpty()) num++
        if (mBinding.seven.text.isEmpty()) num++
        if (mBinding.eight.text.isEmpty()) num++
        if (mBinding.nine.text.isEmpty()) num++
        return num
    }

    private fun resetboxes() {
        mBinding.one.text = ""
        mBinding.two.text = ""
        mBinding.three.text = ""
        mBinding.four.text = ""
        mBinding.five.text = ""
        mBinding.six.text = ""
        mBinding.seven.text = ""
        mBinding.eight.text = ""
        mBinding.nine.text = ""
        mBinding.one.background = null
        mBinding.two.background = null
        mBinding.three.background = null
        mBinding.four.background = null
        mBinding.five.background = null
        mBinding.six.background = null
        mBinding.seven.background = null
        mBinding.eight.background = null
        mBinding.nine.background = null
        mBinding.one.isEnabled = true
        mBinding.two.isEnabled = true
        mBinding.three.isEnabled = true
        mBinding.four.isEnabled = true
        mBinding.five.isEnabled = true
        mBinding.six.isEnabled = true
        mBinding.seven.isEnabled = true
        mBinding.eight.isEnabled = true
        mBinding.nine.isEnabled = true
    }


    private fun disableBoxes() {
        mBinding.one.isEnabled = false
        mBinding.two.isEnabled = false
        mBinding.three.isEnabled = false
        mBinding.four.isEnabled = false
        mBinding.five.isEnabled = false
        mBinding.six.isEnabled = false
        mBinding.seven.isEnabled = false
        mBinding.eight.isEnabled = false
        mBinding.nine.isEnabled = false
    }

    private fun showWinner(lines: Lines) {
        val (winningBoxes, background) = when (lines) {
            Lines.ROW_0 -> Pair(
                listOf(mBinding.one, mBinding.two, mBinding.three),
                R.drawable.horiz_line
            )
            Lines.ROW_1 -> Pair(
                listOf(mBinding.four, mBinding.five, mBinding.six),
                R.drawable.horiz_line
            )
            Lines.ROW_2 -> Pair(
                listOf(mBinding.seven, mBinding.eight, mBinding.nine),
                R.drawable.horiz_line
            )
            Lines.COLUMN_0 -> Pair(
                listOf(mBinding.one, mBinding.four, mBinding.seven),
                R.drawable.vert_line
            )
            Lines.COLUMN_1 -> Pair(
                listOf(mBinding.two, mBinding.five, mBinding.eight),
                R.drawable.vert_line
            )
            Lines.COLUMN_2 -> Pair(
                listOf(mBinding.three, mBinding.six, mBinding.nine),
                R.drawable.vert_line
            )
            Lines.DIAGONAL_LEFT -> Pair(
                listOf(mBinding.one, mBinding.five, mBinding.nine),
                R.drawable.left_digonal
            )
            Lines.DIAGONAL_RIGHT -> Pair(
                listOf(mBinding.three, mBinding.five, mBinding.seven),
                R.drawable.right_diagonal
            )
        }

        winningBoxes.forEach { box ->
            box.background = ResourcesCompat.getDrawable(resources, R.drawable.right_diagonal, null)
        }
    }

}