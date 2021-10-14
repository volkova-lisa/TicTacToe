package com.example.tictactoe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.example.tictactoe.databinding.FragmentFirstGameBinding

class FirstGameFragment : Fragment() {

    lateinit var gameManager: MyGameManager
    var computerTurn : Boolean = true

    private var _binding : FragmentFirstGameBinding? = null
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

    private fun onBoxClicked(box: TextView, whichCell: WhichCell) {
        if (box.text.isEmpty()) {
            //wrap makemove in if?
            if (gameManager.currentPlayerMark == "X") {
                box.text = gameManager.currentPlayerMark
                val winningLine = gameManager.makeMove(whichCell)
                if (winningLine != null) {
                    disableBoxes()
                    mBinding.startNewGameButton.visibility = View.VISIBLE
                    showWinner(winningLine)
                }
            } else {
                //make not move but simulateCompMove
                //gameManager.simulateCompMove()
            }
        }
        mBinding.startNewGameButton.setOnClickListener {
            mBinding.startNewGameButton.visibility = View.GONE
            gameManager.reset()
            resetboxes()
        }

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
            Lines.ROW_0 -> Pair(listOf(mBinding.one, mBinding.two, mBinding.three), R.drawable.horiz_line)
            Lines.ROW_1 -> Pair(listOf(mBinding.four, mBinding.five, mBinding.six), R.drawable.horiz_line)
            Lines.ROW_2 -> Pair(listOf(mBinding.seven, mBinding.eight, mBinding.nine), R.drawable.horiz_line)
            Lines.COLUMN_0 -> Pair(listOf(mBinding.one, mBinding.four, mBinding.seven), R.drawable.vert_line)
            Lines.COLUMN_1 -> Pair(listOf(mBinding.two, mBinding.five, mBinding.eight), R.drawable.vert_line)
            Lines.COLUMN_2 -> Pair(listOf(mBinding.three, mBinding.six, mBinding.nine), R.drawable.vert_line)
            Lines.DIAGONAL_LEFT -> Pair(listOf(mBinding.one, mBinding.five, mBinding.nine),
                R.drawable.left_digonal
            )
            Lines.DIAGONAL_RIGHT -> Pair(listOf(mBinding.three, mBinding.five, mBinding.seven),
                R.drawable.right_diagonal
            )
        }

        winningBoxes.forEach { box ->
            box.background = ResourcesCompat.getDrawable(resources, R.drawable.right_diagonal, null)
        }
    }

}