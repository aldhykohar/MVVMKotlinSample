package com.stimednp.mvvmkotlinsample.ui.home.quotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.stimednp.mvvmkotlinsample.R
import com.stimednp.mvvmkotlinsample.data.db.entities.Quote
import com.stimednp.mvvmkotlinsample.databinding.QuotesFragmentBinding
import com.stimednp.mvvmkotlinsample.util.Coroutines
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class QuotesFragment : Fragment(), KodeinAware {

    override val kodein by kodein()

    private val factory: QuotesViewModelFactory by instance()

    private lateinit var viewModel: QuotesViewModel

    private lateinit var binding: QuotesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.quotes_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(QuotesViewModel::class.java)
        bindUI()

    }

    private fun bindUI() = Coroutines.main {
        viewModel.quotes.await().observe(viewLifecycleOwner, Observer {
            initRecyclerView(it.toQuoteItem())
        })
    }

    private fun initRecyclerView(quoteItem: List<QuoteItem>) {
        val mAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(quoteItem)
        }

        binding.recyclerView.apply {
            layoutManager=LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter =mAdapter
        }

    }

    private fun List<Quote>.toQuoteItem(): List<QuoteItem> {
        return this.map {
            QuoteItem(it)
        }
    }

}