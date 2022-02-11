package com.example.myguitarproject.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myguitarproject.R;
import com.example.myguitarproject.bestsellingproduct.BestSellingProductAdapter;
import com.example.myguitarproject.listproduct.Product;
import com.example.myguitarproject.newproduct.NewProductAdapter;
import com.example.myguitarproject.productDiscount.DisCountAdapter;
import com.example.myguitarproject.retrofitcallapi.APIUtils;
import com.example.myguitarproject.retrofitcallapi.DataClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PossibilityFragment extends Fragment {
    private RecyclerView rcvDiscount;
    private RecyclerView rcvNewProduct;
    private RecyclerView rcvBestSellingProduct;
    private View view;
    private DisCountAdapter disCountAdapter;
    private NewProductAdapter newProductAdapter;
    private BestSellingProductAdapter bestSellingProductAdapter;
    private List<Product> mListProductDiscount;
    private List<Product> mListNewProduct;
    private List<Product> mListProductBest;
    //private List<BestSellingProduct> mListBestSellingProduct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_possibility, container, false);

        init();
        mListProductDiscount = new ArrayList<>();
        mListNewProduct = new ArrayList<>();
        //mListBestSellingProduct = new ArrayList<>();
        mListProductBest = new ArrayList<>();
        disCountAdapter = new DisCountAdapter(getContext());
        newProductAdapter = new NewProductAdapter(getContext());
        bestSellingProductAdapter = new BestSellingProductAdapter(getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rcvDiscount.setLayoutManager(linearLayoutManager);
        LinearLayoutManager linearLayoutManagerNewProduct = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rcvNewProduct.setLayoutManager(linearLayoutManagerNewProduct);
        LinearLayoutManager linearLayoutManagerBestSellingProduct = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rcvBestSellingProduct.setLayoutManager(linearLayoutManagerBestSellingProduct);

        getDataDiscountProduct();
        getDataNewProduct();
        getDataBestSellingProduct();
        return view;

    }

    private void getDataBestSellingProduct() {
        DataClient dataClient = APIUtils.getData();
        //Call<List<BestSellingProduct>> callBestSellingProduct = dataClient.callListBestSellingProduct();
        Call<List<Product>> callBestSellingProduct = dataClient.callListBestSellingProduct();
        callBestSellingProduct.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                ArrayList<Product> bestSellingProducts = (ArrayList<Product>) response.body();
                mListProductBest.addAll(bestSellingProducts);
                bestSellingProductAdapter.setData(mListProductBest);
                rcvBestSellingProduct.setAdapter(bestSellingProductAdapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    private void getDataNewProduct() {
        DataClient dataClient = APIUtils.getData();
        Call<List<Product>> callNewProduct = dataClient.callListNewProduct();
        callNewProduct.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                ArrayList<Product> newProducts = (ArrayList<Product>) response.body();
                mListNewProduct.addAll(newProducts);
                newProductAdapter.setData(mListNewProduct);
                rcvNewProduct.setAdapter(newProductAdapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    private void getDataDiscountProduct() {
        DataClient dataClient = APIUtils.getData();
        Call<List<Product>> callProducDiscount = dataClient.callListProductDisCount();
        callProducDiscount.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                ArrayList<Product> productDiscounts = (ArrayList<Product>) response.body();
                mListProductDiscount.addAll(productDiscounts);
                disCountAdapter.setData(mListProductDiscount);
                rcvDiscount.setAdapter(disCountAdapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    private void init(){
        rcvDiscount = view.findViewById(R.id.rcvDiscount);
        rcvNewProduct = view.findViewById(R.id.rcvNewProduct);
        rcvBestSellingProduct = view.findViewById(R.id.rcvBestSellingProduct);
    }
}