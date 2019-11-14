package com.xsis.android.batch217.ui.timesheet.timesheet_submission

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.ListTimSubAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.TimesheetQueryHelper
import com.xsis.android.batch217.models.Timesheet
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_data_submit.*
import java.text.SimpleDateFormat
import java.util.*
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


class DataSubmitActivity : AppCompatActivity() {

    val context = this
    lateinit var listTimesheet: List<Timesheet>

    val user = "fajrixiomi@gmail.com"
    val tos = arrayOf("molenfajri@gmail.com")
    val ccs = arrayOf<String>()
    val title = "Data Submission"
    //var body = ""
    val password = "setiawan001"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_data_submit)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }

        var year = ""
        var month = ""

        val bundle = intent.extras
        bundle?.let {
            year = bundle.getString(YEAR_TIMESHEET, "")
            month = bundle.getString(MONTH_TIMESHEET, "")
        }
        val databaseHelper = DatabaseHelper(context)
        val databaseQueryHelper = TimesheetQueryHelper(databaseHelper)

        listTimesheet =
            databaseQueryHelper.getTimesheetBerdasarkanWaktuDanProgress(month, year, CREATED)

        if (listTimesheet.isEmpty() || year.isEmpty() || month.isEmpty()) {
            dataNotFoundTimSub.visibility = View.VISIBLE
            listTimSubRecycler.visibility = View.GONE

            buttonNextSub.visibility = View.INVISIBLE

        } else {
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            listTimSubRecycler.layoutManager = layoutManager

            val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
            listTimSubRecycler.addItemDecoration(dividerItemDecoration)


            val adapterTimSub = ListTimSubAdapter(context!!, listTimesheet)
            listTimSubRecycler.adapter = adapterTimSub
            adapterTimSub.notifyDataSetChanged()

            buttonNextSub.setOnClickListener {
                submitted()
            }

        }

        buttonBackTimeSub.setOnClickListener {
            finish()
        }

    }


    fun submitted() {
        val konfirmasiSubmit = AlertDialog.Builder(context, R.style.AlertDialogTheme)
        konfirmasiSubmit.setMessage("DATA SUBMISSION\n\nAre you sure to submit the data ?\n\n\n")
            .setPositiveButton("SUBMIT", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(context, "SUKSES!!!", Toast.LENGTH_SHORT).show()

                val databaseHelper = DatabaseHelper(context!!)
                val databaseQueryHelper = TimesheetQueryHelper(databaseHelper)
                val db = databaseHelper.writableDatabase
                //ubah status created to submitted
                for (timesheet in listTimesheet) {
                    val queryProgress =
                        "UPDATE $TABEL_TIMESHEET SET $PROGRESS_TIMESHEET = 'Submitted' WHERE $ID_TIMESHEET = ${timesheet.id_timesheet}"
                    db.execSQL(queryProgress)
                }

                //send to email
                sendEmail(user, tos, ccs, title, password)

                //pesan berhasil disubmit
                val berhasilSubmited = AlertDialog.Builder(context, R.style.AlertDialogTheme)
                berhasilSubmited.setMessage("DATA HAS BEEN SUBMISSION\n\n")
                    .setPositiveButton("CLOSE", DialogInterface.OnClickListener { dialog, which ->
                        dialog.cancel()
                        finish()
                    })
                    .setCancelable(true)

                berhasilSubmited.create().show()


            })
            .setNegativeButton("CANCEL", DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })
            .setCancelable(true)

        konfirmasiSubmit.create().show()
    }

    //Kirim Email
    val auth = object : Authenticator() {
        override fun getPasswordAuthentication(): javax.mail.PasswordAuthentication? =
            PasswordAuthentication(user, password.toString())
    }

    private fun sendEmail(
        user: String, tos: Array<String>, ccs: Array<String>, title: String,
        password: String
    ) {
        //fungsi tampilin tanggal min max
        val formatter1 = SimpleDateFormat(DATE_PATTERN)
        val formatter2 = SimpleDateFormat("dd-MMMM-yyyy")
        var min = Long.MAX_VALUE
        var max = Long.MIN_VALUE
        for (timesheet in listTimesheet) {
            val dateString = formatter1.parse(timesheet.reportDate_timesheet).time
            if (dateString > max) {
                max = dateString
            }
            if (dateString < min) {
                min = dateString
            }
        }

        val body = "Timesheet tanggal ${formatter2.format(min)} s/d ${formatter2.format(max)} sudah disubmit"


        //fungsi send mail
        val props = Properties()
        val host = "smtp.gmail.com"
        with(props) {
            put("mail.smtp.host", host)
            put("mail.smtp.port", "587") // for TLS
            put("mail.smtp.auth", "true")
            put("mail.smtp.starttls.enable", "true")
        }

        val session = Session.getInstance(
            props,
            auth
        )
        val message = MimeMessage(session)
        with(message) {
            setFrom(InternetAddress(user))
            for (to in tos) addRecipient(Message.RecipientType.TO, InternetAddress(to))
            for (cc in ccs) addRecipient(Message.RecipientType.TO, InternetAddress(cc))
            subject = title
            setText(body)

        }
        val transport = session.getTransport("smtp")
        with(transport) {
            connect(host, user, password)
            sendMessage(message, message.allRecipients)
            close()
        }

    }
}