package com.a2electricboogaloo.audientes.ui.programs

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a2electricboogaloo.audientes.R
import com.a2electricboogaloo.audientes.controller.ProgramController
import com.a2electricboogaloo.audientes.model.types.Program
import kotlinx.android.synthetic.main.programs_activity.*

class ProgramsActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: ProgramsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.programs_activity)

        viewManager = LinearLayoutManager(this)
        viewAdapter = ProgramsAdapter(this)

        Program.getUserPrograms().observe(this, Observer { programs ->
            viewAdapter.setProgramsAndUpdate(programs)
        })

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        addButton.setOnClickListener {
            ProgramController.sharedInstance.setProgramToEdit(Program("New Program", null))
            startActivity(Intent(this, EditProgramActivity::class.java))
            finish()
        }
    }

}