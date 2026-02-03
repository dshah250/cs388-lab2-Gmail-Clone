package com.example.gmailclone

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : ComponentActivity() {
    private lateinit var emailsRv: RecyclerView
    private lateinit var loadMoreBtn: Button
    private lateinit var adapter: EmailAdapter
    private val emailList = mutableListOf<Email>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailsRv = findViewById(R.id.emailsRv)
        loadMoreBtn = findViewById(R.id.loadMoreBtn)

        emailList.addAll(EmailFetcher.getEmails())

        adapter = EmailAdapter(emailList)

        emailsRv.layoutManager = LinearLayoutManager(this)

        emailsRv.adapter = adapter

        loadMoreBtn.setOnClickListener {
            loadMoreEmails()
        }
    }

    private fun loadMoreEmails() {
        val nextEmails = EmailFetcher.getNext5Emails()

        emailList.addAll(nextEmails)

        adapter.notifyItemRangeInserted(emailList.size - nextEmails.size, nextEmails.size)

        emailsRv.smoothScrollToPosition(emailList.size - 1)
    }
}