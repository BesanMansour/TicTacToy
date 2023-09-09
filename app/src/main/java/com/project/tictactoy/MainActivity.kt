package com.project.tictactoy

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random as Random1

class MainActivity : AppCompatActivity() {

    // ربطنا كل الازرار بنفس الليسنر

    // معلومات الللاعب الاول راح نخزن الارقام فيه
    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()

    // اللاعب الفعال يعني اللاعب اللي حاليا بلعب
    var activePlayer = 1
    private var mediaPlayer: MediaPlayer? = null
    private val handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }


    fun butSelect(view:View){
        //view: inctance من كلاس View
        //عشان هيك بدنا نحولها

        val butSelect = view as Button
        var cellId = 0
        // على حسب id
        when(butSelect.id){
            R.id.button1 -> cellId = 1
            R.id.button2 -> cellId = 2
            R.id.button3 -> cellId = 3
            R.id.button4 -> cellId = 4
            R.id.button5 -> cellId = 5
            R.id.button6 -> cellId = 6
            R.id.button7 -> cellId = 7
            R.id.button8 -> cellId = 8
            R.id.button9 -> cellId = 9
        }
        Log.e("cellId",cellId.toString())
        playGame(cellId,butSelect)
        butSelect.setBackgroundResource(R.color.teal_700)

    }


    fun playGame(cellId : Int , butSelect : Button){

        if (activePlayer == 1){
            butSelect.text = "X"
            butSelect.setBackgroundResource(R.color.teal_700)
            player1.add(cellId)
            activePlayer = 2
            AutoPlay()
        }else{
            butSelect.text = "O"
            butSelect.setBackgroundResource(R.color.teal_200)
            player2.add(cellId)
            activePlayer = 1
        }
        // عشان ما اسمح لاي شخص يضغط عليه مرة تانية
        butSelect.isEnabled = false
        checkWinner()
    }

    //ايجاد الفائز
    fun checkWinner() {
        var winner = 0 // بدلاً من 1 لعدم تحديد فائز مبدئيا

        // Check rows
        for (row in 1..3) {
            if (player1.containsAll(listOf(row, row + 1, row + 2))) {
                winner = 1
                break
            } else if (player2.containsAll(listOf(row, row + 1, row + 2))) {
                winner = 2
                break
            }
        }

        // Check columns
        for (col in 1..3) {
            if (player1.containsAll(listOf(col, col + 3, col + 6))) {
                winner = 1
                break
            } else if (player2.containsAll(listOf(col, col + 3, col + 6))) {
                winner = 2
                break
            }
        }

        // Check diagonals
        if (player1.containsAll(listOf(1, 5, 9)) || player1.containsAll(listOf(3, 5, 7))) {
            winner = 1
        } else if (player2.containsAll(listOf(1, 5, 9)) || player2.containsAll(listOf(3, 5, 7))) {
            winner = 2
        }

        if (winner != 0) {
            if (winner == 1) {
                Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_LONG).show()
                againPlayWithDelay()
                playWinSound() // تشغيل الصوت

            } else {
                Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_LONG).show()
                againPlayWithDelay()
                playWinSound() // تشغيل الصوت
            }

        }
    }
    // اللعب تلقائي
//    fun AutoPlay(){
//
//    }


    fun AutoPlay() {
        // الخلايا الفارغة اللي على أساسها راح يلعب الجهاز
        val emptyCells = ArrayList<Int>()

        for (cellId in 1..9) {
            if (!player1.contains(cellId) && !player2.contains(cellId)) {
                emptyCells.add(cellId)
            }
        }

        // اختيار خلية عشوائية من الخلايا الفارغة إذا كانت هناك خلايا فارغة
        if (emptyCells.isNotEmpty()) {
            val random = Random()
            val randIndex = random.nextInt(emptyCells.size)
            val cellId = emptyCells[randIndex]

            // اعثر على الزر المرتبط بالخلية وقم بلعبها
            val button = when (cellId) {
                1 -> button1
                2 -> button2
                3 -> button3
                4 -> button4
                5 -> button5
                6 -> button6
                7 -> button7
                8 -> button8
                9 -> button9
                else -> null
            }

            button?.let {
                playGame(cellId, it)
            }
        }
    }

    fun playWinSound() {
        // تأكد من تحرير اسم الملف الصوتي بناءً على اسم الملف الذي وضعته في مجلد res/raw
        mediaPlayer = MediaPlayer.create(this, R.raw.win)
        mediaPlayer?.start()
    }
    fun againPlayWithDelay() {
        // ... المحتوى الحالي لدالة againPlay()

        // تأخير استدعاء againPlay() بمدة 3 ثواني (3000 مللي ثانية)
        handler.postDelayed({
            againPlay()
        }, 3000)
    }

    fun againPlay() {
        player1.clear()
        player2.clear()
        activePlayer = 1
        button1.setBackgroundResource(R.color.white)
        button2.setBackgroundResource(R.color.white)
        button3.setBackgroundResource(R.color.white)
        button4.setBackgroundResource(R.color.white)
        button5.setBackgroundResource(R.color.white)
        button6.setBackgroundResource(R.color.white)
        button7.setBackgroundResource(R.color.white)
        button8.setBackgroundResource(R.color.white)
        button9.setBackgroundResource(R.color.white)

        button1.text = ""
        button2.text = ""
        button3.text = ""
        button4.text = ""
        button5.text = ""
        button6.text = ""
        button7.text = ""
        button8.text = ""
        button9.text = ""

        button1.isEnabled = true
        button2.isEnabled = true
        button3.isEnabled = true
        button4.isEnabled = true
        button5.isEnabled = true
        button6.isEnabled = true
        button7.isEnabled = true
        button8.isEnabled = true
        button9.isEnabled = true
    }
}


