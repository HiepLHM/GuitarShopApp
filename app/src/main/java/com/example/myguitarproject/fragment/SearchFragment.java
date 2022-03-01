package com.example.myguitarproject.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myguitarproject.R;
import com.example.myguitarproject.listproduct.Product;
import com.example.myguitarproject.retrofitcallapi.APIUtils;
import com.example.myguitarproject.retrofitcallapi.DataClient;
import com.example.myguitarproject.searchproduct.SearchAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment {
    private View view;
    private EditText edtSearchProduct;
    private RecyclerView rcvSearch;
    private List<Product> mListProduct;
    private SearchAdapter searchAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        init();

        mListProduct = new ArrayList<>();
        searchAdapter = new SearchAdapter(getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvSearch.setLayoutManager(linearLayoutManager);

        edtSearchProduct.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hanlderSearchProduct();
                }
                return false;
            }
        });

        return view;
    }

    private void hanlderSearchProduct() {
        String name = edtSearchProduct.getText().toString().trim();
        DataClient dataClient = APIUtils.getData();
        Call<List<Product>> call = dataClient.callSearchProduct(name);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                ArrayList<Product> products = (ArrayList<Product>) response.body();
                mListProduct.addAll(products);
                searchAdapter.setData(mListProduct);
                rcvSearch.setAdapter(searchAdapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    private void init() {
        edtSearchProduct = view.findViewById(R.id.edtSearchProduct);
        rcvSearch = view.findViewById(R.id.rcvSearch);
    }
}