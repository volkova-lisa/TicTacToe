package com.example.tictactoe

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.example.tictactoe.databinding.FragmentSecondGameBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SecondGameFragment : Fragment() {

    lateinit var gameManager: MyGameManager

    private var _binding : FragmentSecondGameBinding? = null
    val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondGameBinding.inflate(layoutInflater, container, false)

        gameManager = MyGameManager()

        mBinding.oneNew.setOnClickListener { onBoxClicked(mBinding.oneNew, WhichCell(0, 0)) }
        mBinding.twoNew.setOnClickListener { onBoxClicked(mBinding.twoNew, WhichCell(0, 1)) }
        mBinding.threeNew.setOnClickListener { onBoxClicked(mBinding.threeNew, WhichCell(0, 2)) }
        mBinding.fourNew.setOnClickListener { onBoxClicked(mBinding.fourNew, WhichCell(1, 0)) }
        mBinding.fiveNew.setOnClickListener { onBoxClicked(mBinding.fiveNew, WhichCell(1, 1)) }
        mBinding.sixNew.setOnClickListener { onBoxClicked(mBinding.sixNew, WhichCell(1, 2)) }
        mBinding.sevenNew.setOnClickListener { onBoxClicked(mBinding.sevenNew, WhichCell(2, 0)) }
        mBinding.eightNew.setOnClickListener { onBoxClicked(mBinding.eightNew, WhichCell(2, 1)) }
        mBinding.nineNew.setOnClickListener { onBoxClicked(mBinding.nineNew, WhichCell(2, 2)) }

        return mBinding.root
    }

    private fun onBoxClicked(box: TextView, whichCell: WhichCell) {
        if (box.text.isEmpty()) {
            box.text = gameManager.currentPlayerMark
            val winningLine = gameManager.makeMove(whichCell)
            if (winningLine != null) {
                disableBoxes()
                mBinding.startNewGameButtonNew.visibility = View.VISIBLE
                showWinner(winningLine)
                gameManager.currentPlayer = 3 - gameManager.currentPlayer
            }
            //передаем эстафету компьютеру
            var emptyCellsNum = emptyCellsNum()
            var emptyCellsList = emptyCellsList(emptyCellsNum)
            computerMakesMove(box, whichCell, emptyCellsList)
        }
        mBinding.startNewGameButtonNew.setOnClickListener {
            mBinding.startNewGameButtonNew.visibility = View.GONE
            gameManager.reset()
            resetboxes()
        }
    }
    private fun computerMakesMove(
        box: TextView,
        whichCell: WhichCell,
        list: MutableList<TextView>
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            //Log.d("0000000000000000000", "ss" + emptyCellsNum())
            val winningLine = gameManager.makeMove(whichCell)
            delay(1000)
            if (winningLine != null) {
                showWinner(winningLine)
                disableBoxes()
                mBinding.startNewGameButtonNew.visibility = View.VISIBLE
                gameManager.currentPlayer = 3 - gameManager.currentPlayer

            } else {
                if (list.size != 0) {
                    val compBox = (0 until list.size).random()
                    Log.d("6666666666666666", "hh" + compBox + "+" + list)
                    list[compBox]!!.text = "0"
                }
            }
        }
    }
    fun emptyCellsList(i: Int): MutableList<TextView> {
        var mutList = mutableListOf<TextView>()
        if (mBinding.oneNew.text.isEmpty()) mutList.add(mBinding.oneNew)
        if (mBinding.twoNew.text.isEmpty()) mutList.add(mBinding.twoNew)
        if (mBinding.threeNew.text.isEmpty()) mutList.add(mBinding.threeNew)
        if (mBinding.fourNew.text.isEmpty()) mutList.add(mBinding.fourNew)
        if (mBinding.fiveNew.text.isEmpty()) mutList.add(mBinding.fiveNew)
        if (mBinding.sixNew.text.isEmpty()) mutList.add(mBinding.sixNew)
        if (mBinding.sevenNew.text.isEmpty()) mutList.add(mBinding.sevenNew)
        if (mBinding.eightNew.text.isEmpty()) mutList.add(mBinding.eightNew)
        if (mBinding.nineNew.text.isEmpty()) mutList.add(mBinding.nineNew)
        return mutList
    }

    fun emptyCellsNum(): Int {
        var num = 0
        if (mBinding.oneNew.text.isEmpty()) num++
        if (mBinding.twoNew.text.isEmpty()) num++
        if (mBinding.threeNew.text.isEmpty()) num++
        if (mBinding.fourNew.text.isEmpty()) num++
        if (mBinding.fiveNew.text.isEmpty()) num++
        if (mBinding.sixNew.text.isEmpty()) num++
        if (mBinding.sevenNew.text.isEmpty()) num++
        if (mBinding.eightNew.text.isEmpty()) num++
        if (mBinding.nineNew.text.isEmpty()) num++
        return num
    }

    private fun resetboxes() {
        mBinding.oneNew.text = ""
        mBinding.twoNew.text = ""
        mBinding.threeNew.text = ""
        mBinding.fourNew.text = ""
        mBinding.fiveNew.text = ""
        mBinding.sixNew.text = ""
        mBinding.sevenNew.text = ""
        mBinding.eightNew.text = ""
        mBinding.nineNew.text = ""
        mBinding.oneNew.background = null
        mBinding.twoNew.background = null
        mBinding.threeNew.background = null
        mBinding.fourNew.background = null
        mBinding.fiveNew.background = null
        mBinding.sixNew.background = null
        mBinding.sevenNew.background = null
        mBinding.eightNew.background = null
        mBinding.nineNew.background = null
        mBinding.oneNew.isEnabled = true
        mBinding.twoNew.isEnabled = true
        mBinding.threeNew.isEnabled = true
        mBinding.fourNew.isEnabled = true
        mBinding.fiveNew.isEnabled = true
        mBinding.sixNew.isEnabled = true
        mBinding.sevenNew.isEnabled = true
        mBinding.eightNew.isEnabled = true
        mBinding.nineNew.isEnabled = true
    }


    private fun disableBoxes() {
        mBinding.oneNew.isEnabled = false
        mBinding.twoNew.isEnabled = false
        mBinding.threeNew.isEnabled = false
        mBinding.fourNew.isEnabled = false
        mBinding.fiveNew.isEnabled = false
        mBinding.sixNew.isEnabled = false
        mBinding.sevenNew.isEnabled = false
        mBinding.eightNew.isEnabled = false
        mBinding.nineNew.isEnabled = false
    }

    private fun showWinner(lines: Lines) {
        val (winningBoxes, background) = when (lines) {
            Lines.ROW_0 -> Pair(listOf(mBinding.oneNew, mBinding.twoNew, mBinding.threeNew), R.drawable.horiz_line)
            Lines.ROW_1 -> Pair(listOf(mBinding.fourNew, mBinding.fiveNew, mBinding.sixNew), R.drawable.horiz_line)
            Lines.ROW_2 -> Pair(listOf(mBinding.sevenNew, mBinding.eightNew, mBinding.nineNew), R.drawable.horiz_line)
            Lines.COLUMN_0 -> Pair(listOf(mBinding.oneNew, mBinding.fourNew, mBinding.sevenNew), R.drawable.vert_line)
            Lines.COLUMN_1 -> Pair(listOf(mBinding.twoNew, mBinding.fiveNew, mBinding.eightNew), R.drawable.vert_line)
            Lines.COLUMN_2 -> Pair(listOf(mBinding.threeNew, mBinding.sixNew, mBinding.nineNew), R.drawable.vert_line)
            Lines.DIAGONAL_LEFT -> Pair(listOf(mBinding.oneNew, mBinding.fiveNew, mBinding.nineNew),
                R.drawable.left_digonal
            )
            Lines.DIAGONAL_RIGHT -> Pair(listOf(mBinding.threeNew, mBinding.fiveNew, mBinding.sevenNew),
                R.drawable.right_diagonal
            )
        }

        winningBoxes.forEach { box ->
            box.background = ResourcesCompat.getDrawable(resources, R.drawable.right_diagonal, null)
        }
    }

}