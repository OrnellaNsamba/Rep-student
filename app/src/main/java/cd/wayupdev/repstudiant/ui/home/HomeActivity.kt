package cd.wayupdev.repstudiant.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import cd.wayupdev.repstudiant.R
import cd.wayupdev.repstudiant.adapter.MyAdapter
import cd.wayupdev.repstudiant.ui.addEtudiant.AddEtudaintActivity
import cd.wayupdev.repstudiant.ui.home.business.HomeViewModel
import cd.wayupdev.repstudiant.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val myAdapter: MyAdapter by lazy { MyAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        countAll()
        filter()

        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = myAdapter

        homeViewModel.findAll(true).observe(this) {
            myAdapter.setData(it)
        }

        floatingActionButton.setOnClickListener {
            val intent = Intent(this@HomeActivity, AddEtudaintActivity::class.java)
            startActivity(intent)
        }
    }
    private fun filter(){
        var c = true
        filter_icon.setOnClickListener {
            c = !c
            homeViewModel.findAll(c).observe(this) {
                myAdapter.setData(it)
            }
        }
    }

    private fun countAll(){
        count_ic.setOnClickListener {
            homeViewModel.count.observe(this){
                if (it > 1){
                    toast("il y'a $it Etudiants dans la db", Toast.LENGTH_LONG)
                }
                toast("il y'a $it Etudiant dans la db", Toast.LENGTH_LONG)
            }
        }
    }
}