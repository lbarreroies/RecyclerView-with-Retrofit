package dam.pmdm.recyclerview_with_retrofit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dam.pmdm.recyclerview_with_retrofit.R
import dam.pmdm.recyclerview_with_retrofit.data.model.Item
import dam.pmdm.recyclerview_with_retrofit.databinding.FragmentListBinding
import dam.pmdm.recyclerview_with_retrofit.viewmodel.CharacterViewModel

class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private lateinit var myAdapter: ItemAdapter
    private lateinit var viewModel: CharacterViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        // Inicializar el ViewModel
        viewModel = ViewModelProvider(this)[CharacterViewModel::class.java]

        // Nos subscribimos al LiveData del loading
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
            binding.recyclerview.isVisible = !isLoading
            binding.tvCount.isVisible = !isLoading
        }

        // Nos subscribimos al LiveData del listado
        viewModel.characters.observe(viewLifecycleOwner) { list ->
            myAdapter.submitList(list)
            binding.tvCount.text = getString(R.string.characters_count, list.size)
        }

        // Nos subscribimos al LiveData del error
        viewModel.error.observe(viewLifecycleOwner) { msg ->
            if (!msg.isNullOrBlank()) {
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            }
        }

        // Cargar los datos
        viewModel.loadCharacters()
    }

    private fun onGameClicked(itemData: Item) {
        // Do anything
    }

    private fun setupRecyclerView() {
        myAdapter = ItemAdapter(listOf(), ::onGameClicked)
        binding.recyclerview.adapter = myAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
    }
}