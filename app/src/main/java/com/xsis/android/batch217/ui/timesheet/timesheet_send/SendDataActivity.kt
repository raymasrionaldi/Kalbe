package com.xsis.android.batch217.ui.timesheet.timesheet_send


import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.ListTimesheetSendAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.TimesheetQueryHelper
import com.xsis.android.batch217.models.Timesheet
import kotlinx.android.synthetic.main.activity_send_data.*
import java.util.*
import javax.mail.Message.RecipientType
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import java.util.Properties
import javax.mail.*
import android.os.StrictMode
import android.view.MenuItem
import android.widget.Toast
import com.xsis.android.batch217.utils.*
import java.text.SimpleDateFormat


class SendDataActivity : AppCompatActivity() {
    val context = this
    lateinit var listTimesheet : List<Timesheet>
    val user = "difaraymas82@gmail.com"
    val tos = arrayOf("difaraymas82@gmail.com")
    val ccs = arrayOf<String>()
    val title = "Data Terkirim"
    val password = "hanifan49"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(com.xsis.android.batch217.R.layout.activity_send_data)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException){
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

        listTimesheet = databaseQueryHelper.getTimesheetBerdasarkanWaktuDanProgress(month, year, APPROVED)
        if (listTimesheet.isEmpty() || year.isEmpty() || month.isEmpty()) {
            dataNotFoundTimesheetSend.visibility = View.VISIBLE
            listTimesheetSendRecycler.visibility = View.GONE

            buttonNextSend.visibility = View.INVISIBLE

        }else {
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            listTimesheetSendRecycler.layoutManager = layoutManager

            val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
            listTimesheetSendRecycler.addItemDecoration(dividerItemDecoration)


            val adapterTimesheetSend = ListTimesheetSendAdapter(context!!, listTimesheet)
            listTimesheetSendRecycler.adapter = adapterTimesheetSend
            adapterTimesheetSend.notifyDataSetChanged()

            buttonNextSend.setOnClickListener{
//                val recipient = context!!.toString().trim()
//                val subject = context!!.toString().trim()
//                val message = context!!.toString().trim()

                //method call for email intent with these inputs as parameters

                sendEmail(user, tos, ccs, title, password)

            }

        }
        buttonBackTimesheetSend.setOnClickListener {
            finish()
        }
    }

    val auth = object: Authenticator() {
        override fun getPasswordAuthentication(): javax.mail.PasswordAuthentication? =
            PasswordAuthentication(user, password.toString())
    }

    fun sendEmail(user: String, tos: Array<String>, ccs: Array<String>, title: String,
                           password: String) {
        val konfirmasiSend = AlertDialog.Builder(context)
        konfirmasiSend.setMessage("DATA SENDING\n\nAre you sure to send the data ?\n\n\n")
            .setPositiveButton("SEND", DialogInterface.OnClickListener{ dialog, which ->
                Toast.makeText(context,"SUKSES!!!", Toast.LENGTH_SHORT).show()
                val databaseHelper = DatabaseHelper(context!!)
                val databaseQueryHelper = TimesheetQueryHelper(databaseHelper)
                val db = databaseHelper.writableDatabase

                for(timesheet in listTimesheet) {
                    val queryProgress =
                        "UPDATE $TABEL_TIMESHEET SET $PROGRESS_TIMESHEET = 'Sent' WHERE $ID_TIMESHEET = ${timesheet.id_timesheet}"
                    db.execSQL(queryProgress)
                }
                val formatter1 = SimpleDateFormat(DATE_PATTERN)
                val formatter2 = SimpleDateFormat("dd-MMMM-yyyy")
                var body=""
                if(listTimesheet.size == 1){
                    var timesheet = listTimesheet[0]
                    val dateString = formatter1.parse(timesheet.reportDate_timesheet).time
                    body = "Timesheet tanggal ${formatter2.format(dateString)} sudah dikirim"

                }else {

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

                    body = "Timesheet tanggal ${formatter2.format(min)} s/d ${formatter2.format(max)} sudah dikirim"
                }


                val props = Properties()
                val host = "smtp.gmail.com"
                with (props) {
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
                with (message) {
                    setFrom(InternetAddress(user))
                    for (to in tos) addRecipient(Message.RecipientType.TO, InternetAddress(to))
                    for (cc in ccs) addRecipient(Message.RecipientType.TO, InternetAddress(cc))
                    setSubject(title)
                    setText(body)
                }
                val transport = session.getTransport("smtp")
                with (transport) {
                    connect(host, user, password)
                    sendMessage(message, message.allRecipients)
                    close()
                }

                val berhasilSend = AlertDialog.Builder(context)
                berhasilSend.setMessage("DATA HAS BEEN SENT\n\n")
                    .setPositiveButton("CLOSE", DialogInterface.OnClickListener{ dialog, which ->
                        dialog.cancel()
                        finish()
                    })
                    .setCancelable(true)

                berhasilSend.create().show()

            })
            .setNegativeButton("CANCEL", DialogInterface.OnClickListener{ dialog, which ->
                dialog.cancel()
            })
            .setCancelable(true)

        konfirmasiSend.create().show()


//        /*ACTION_SEND action to launch an email client installed on your Android device.*/
//        val mIntent = Intent(Intent.ACTION_SEND)
//        /*To send an email you need to specify mailto: as URI using setData() method
//        and data type will be to text/plain using setType() method*/
//        mIntent.data = Uri.parse("mailto:")
//        mIntent.type = "text/plain"
//        // put recipient email in intent
//        /* recipient is put as array because you may wanna send email to multiple emails
//           so enter comma(,) separated emails, it will be stored in array*/
//        mIntent.putExtra(Intent.EXTRA_EMAIL, "satriahanif4@gmail.com")
//        //put the Subject in the intent
//        mIntent.putExtra(Intent.EXTRA_SUBJECT, "feedback")
//        //put the message in the intent
//        mIntent.putExtra(Intent.EXTRA_TEXT, "Timesheet tanggal xx-xx-xxxx s/d xx-xx-xxxx sudah dikirim")
//
//
//        try {
//            //start email intent
//            startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
//        }
//        catch (e: Exception){
//            //if any thing goes wrong for example no email client application or any exception
//            //get and show exception message
//            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
//        }

    }


}
