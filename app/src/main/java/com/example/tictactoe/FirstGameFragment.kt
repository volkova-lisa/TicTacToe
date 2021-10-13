package com.example.tictactoe

import android.app.GameManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.tictactoe.databinding.FragmentFirstGameBinding
import com.example.tictactoe.databinding.FragmentStartBinding

class FirstGameFragment : Fragment() {

    lateinit var gameManager: MyGameManager

    private var _binding : FragmentFirstGameBinding? = null
    val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstGameBinding.inflate(layoutInflater, container, false)

        gameManager = MyGameManager()

        mBinding.one.setOnClickListener { onBoxClicked(mBinding.one, Position(0, 0)) }
        mBinding.two.setOnClickListener { onBoxClicked(mBinding.two, Position(0, 1)) }
        mBinding.three.setOnClickListener { onBoxClicked(mBinding.three, Position(0, 2)) }
        mBinding.four.setOnClickListener { onBoxClicked(mBinding.four, Position(1, 0)) }
        mBinding.five.setOnClickListener { onBoxClicked(mBinding.five, Position(1, 1)) }
        mBinding.six.setOnClickListener { onBoxClicked(mBinding.six, Position(1, 2)) }
        mBinding.seven.setOnClickListener { onBoxClicked(mBinding.seven, Position(2, 0)) }
        mBinding.eight.setOnClickListener { onBoxClicked(mBinding.eight, Position(2, 1)) }
        mBinding.nine.setOnClickListener { onBoxClicked(mBinding.nine, Position(2, 2)) }

        return mBinding.root
    }

    private fun onBoxClicked(box: TextView, position: Position) {
        if (box.text.isEmpty()) {
            box.text = gameManager.currentPlayerMark
            val winningLine = gameManager.makeMove(position)
            if (winningLine != null) {
                disableBoxes()
                mBinding.startNewGameButton.visibility = View.VISIBLE
                showWinner(winningLine)
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

    private fun showWinner(winningLine: WinningLine) {
        val (winningBoxes, background) = when (winningLine) {
            WinningLine.ROW_0 -> Pair(listOf(mBinding.one, mBinding.two, mBinding.three), R.drawable.horiz_line)
            WinningLine.ROW_1 -> Pair(listOf(mBinding.four, mBinding.five, mBinding.six), R.drawable.horiz_line)
            WinningLine.ROW_2 -> Pair(listOf(mBinding.seven, mBinding.eight, mBinding.nine), R.drawable.horiz_line)
            WinningLine.COLUMN_0 -> Pair(listOf(mBinding.one, mBinding.four, mBinding.seven), R.drawable.vert_line)
            WinningLine.COLUMN_1 -> Pair(listOf(mBinding.two, mBinding.five, mBinding.eight), R.drawable.vert_line)
            WinningLine.COLUMN_2 -> Pair(listOf(mBinding.three, mBinding.six, mBinding.nine), R.drawable.vert_line)
            WinningLine.DIAGONAL_LEFT -> Pair(listOf(mBinding.one, mBinding.five, mBinding.nine),
                R.drawable.left_digonal
            )
            WinningLine.DIAGONAL_RIGHT -> Pair(listOf(mBinding.three, mBinding.five, mBinding.seven),
                R.drawable.right_diagonal
            )
        }

        winningBoxes.forEach { box ->
            box.background = ResourcesCompat.getDrawable(resources, R.drawable.right_diagonal, null)
        }
    }

}