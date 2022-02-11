package com.example.myguitarproject.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myguitarproject.R;
import com.example.myguitarproject.advertisement.Advertisement;
import com.example.myguitarproject.advertisement.AdvertisementAdapter;
import com.example.myguitarproject.listproduct.Category;
import com.example.myguitarproject.listproduct.CategoryAdapter;
import com.example.myguitarproject.listproduct.Product;
import com.example.myguitarproject.retrofitcallapi.APIUtils;
import com.example.myguitarproject.retrofitcallapi.DataClient;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    private View view;
    private ViewPager2 viewpagerAds;
    private CircleIndicator3 circleIndicator3;
    private RecyclerView rcvCategory;
    private AdvertisementAdapter advertisementAdapter;
    private List<Advertisement> mListAds;
    private List<Product> mListProduct;
    private List<Category> mListCategory;
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if(viewpagerAds.getCurrentItem()>mListAds.size()-1){
                viewpagerAds.setCurrentItem(0);
            } else {
                viewpagerAds.setCurrentItem(viewpagerAds.getCurrentItem()+1);
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initHome();
        mListCategory = new ArrayList<>();
        mListProduct = new ArrayList<>();
        mListAds = new ArrayList<>();

        callViewPagerAds();
        mHandler.removeCallbacks(mRunnable);
        mHandler.postDelayed(mRunnable, 3000);

        //add data to rcvCategory
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcvCategory.setLayoutManager(linearLayoutManager);
        rcvCategoryCallData();
        return view;
    }

    private void rcvCategoryCallData() {
        DataClient dataClientProduct = APIUtils.getData();
        Call<List<Product>> callProduct = dataClientProduct.callProduct();
        callProduct.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                ArrayList<Product> products = (ArrayList<Product>) response.body();
                mListProduct.addAll(products);
                DataClient dataClientCategory = APIUtils.getData();
                Call<List<Category>> callCategory = dataClientCategory.callCategory();
                callCategory.enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                        ArrayList<Category> categories = (ArrayList<Category>) response.body();
                        for(int i = 0; i<categories.size();i++){
                            int idCate = categories.get(i).getIdCategory();
                            ArrayList<Product> productlist = new ArrayList<>();
                            for(int k=0 ; k<mListProduct.size();k++){
                                Product pro = mListProduct.get(k);
                                if(idCate == pro.getIdCategory()){
                                    productlist.add(pro);
                                }
                            }
                            mListCategory.add(new Category(categories.get(i).getIdCategory(),categories.get(i).getNameCategory(), categories.get(i).getImageCategory(), productlist));
                        }
                        CategoryAdapter categoryAdapter = new CategoryAdapter(getContext());
                        categoryAdapter.setData(mListCategory);
                        rcvCategory.setAdapter(categoryAdapter);
                    }

                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable t) {
                        Toast.makeText(getContext(), "Failed call category", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(getContext(), "lỗi call product", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callViewPagerAds() {
        DataClient dataClientAds = APIUtils.getData();
        Call<List<Advertisement>> callAds = dataClientAds.callAds();
        callAds.enqueue(new Callback<List<Advertisement>>() {
            @Override
            public void onResponse(Call<List<Advertisement>> call, Response<List<Advertisement>> response) {
                ArrayList<Advertisement> advertisements = (ArrayList<Advertisement>) response.body();
                mListAds.addAll(advertisements);
                advertisementAdapter = new AdvertisementAdapter( mListAds, getContext());
                viewpagerAds.setAdapter(advertisementAdapter);
                circleIndicator3.setViewPager(viewpagerAds);
            }

            @Override
            public void onFailure(Call<List<Advertisement>> call, Throwable t) {
                Toast.makeText(getContext(), "lỗi slide Ads", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.postDelayed(mRunnable, 3000);
    }

    private void initHome(){
        viewpagerAds = view.findViewById(R.id.viewPagerAds);
        circleIndicator3 = view.findViewById(R.id.circleIndicator);
        rcvCategory = view.findViewById(R.id.rcvCategory);
    }
}