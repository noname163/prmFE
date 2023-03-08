package com.example.team_project.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.team_project.R;
import com.example.team_project.activity.ShowAllActivity;
import com.example.team_project.adapter.CategoryAdapter;
import com.example.team_project.adapter.PopularProductsAdapter;
import com.example.team_project.adapter.ProductAdapter;
import com.example.team_project.adapter.ProductUserAdapter;
import com.example.team_project.adapter.ProductUserResponseAdapter;
import com.example.team_project.databinding.FragmentHomePageBinding;
import com.example.team_project.dto.request.PageableRequest;
import com.example.team_project.dto.response.PaginationResponse;
import com.example.team_project.dto.response.ProductUserResponse;
import com.example.team_project.models.Category;
import com.example.team_project.models.PopularProducts;
import com.example.team_project.models.Product;
import com.example.team_project.retrofitclient.RetrofitClient;
import com.example.team_project.service.CategoryService;
import com.example.team_project.service.ProductService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class HomePageFragment extends Fragment {


    private FragmentHomePageBinding binding;

    TextView catShowAll,popularShowAll,productShowAll;

    LinearLayout linearLayout;
    ProgressDialog progressDialog;
    RecyclerView catRecyclerview,newProductRecycleview,popularRecycleview;

    //Product recycleview
    List<ProductUserResponse> productList;
    ProductUserAdapter productUserResponseAdapter;


    //Category recycleview
    CategoryAdapter categoryAdapter;
    CategoryService categoryService;
    List<Category> categoryList;

    //Popular Products
    PopularProductsAdapter popularProductsAdapter;
    List<PopularProducts> popularProductsList;

    ProductService productService;
    PageableRequest pageable = new PageableRequest();


    //Fire store
    FirebaseFirestore db;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        //init
        binding = FragmentHomePageBinding.inflate(inflater, container, false);
        ImageSlider imageSlider = binding.getRoot().findViewById(R.id.image_slider);
        //db
        db = FirebaseFirestore.getInstance();

        linearLayout = binding.getRoot().findViewById(R.id.home_layout);
        linearLayout.setVisibility(View.GONE);

        progressDialog = new ProgressDialog(getActivity());

        catRecyclerview = binding.getRoot().findViewById(R.id.rec_category);
        newProductRecycleview = binding.getRoot().findViewById(R.id.new_product_rec);
        popularRecycleview = binding.getRoot().findViewById(R.id.popular_rec);

        //See All
        catShowAll = binding.getRoot().findViewById(R.id.category_see_all);
        productShowAll = binding.getRoot().findViewById(R.id.newProducts_see_all);
        popularShowAll = binding.getRoot().findViewById(R.id.popular_see_all);

        catShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });

        productShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });

        popularShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });

        List<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.banner_1,"Discount On HeadPhone", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner_2,"Discount On Watch", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner_3,"Discount On Laptop 70%", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner_4,"Discount 70%", ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slideModels);
        progressDialog.setTitle("Welcome To My Ecommerce App");
        progressDialog.setMessage("please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        //category
        catRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getActivity(),categoryList);
        catRecyclerview.setAdapter(categoryAdapter);

        Retrofit retrofitClient = RetrofitClient.getClient();
        categoryService = new RetrofitClient().getClient().create(CategoryService.class);
        System.out.println("Load data...");
        categoryService = retrofitClient.create(CategoryService.class);
        Call<List<Category>> call = categoryService.getCategories();
        System.out.println("Load service...");

        call.enqueue(new Callback<List<Category>>() {

                         @Override
                         public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                             System.out.println("Load data...");
                             if (response.isSuccessful()) {
                                 System.out.println("Success...");
                                 List<Category> categories = response.body();
                                 for (Category category : categories
                                 ) {
                                     System.out.println("Category " + categories.toString());
                                     categoryList.add(category);
                                     categoryAdapter.notifyDataSetChanged();
                                     linearLayout.setVisibility(View.VISIBLE);
                                     progressDialog.dismiss();
                                     System.out.println("Load data complted");
                                 }
                             } else {
                                 System.out.println("Load data fail...");
                             }
                         }

                         @Override
                         public void onFailure(Call<List<Category>> call, Throwable t) {
                             System.out.println("Load data fail..." + t.getMessage());
                         }
                     });

        //New Products
        productList = new ArrayList<>();
        newProductRecycleview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        productUserResponseAdapter = new ProductUserAdapter(getActivity(), productList);
        newProductRecycleview.setAdapter(productUserResponseAdapter);
        productService = retrofitClient.create(ProductService.class);
        pageable.setField("modelYear");
        pageable.setSize(10);
        pageable.setSortType("DESC");
        pageable.setOffSet(0);
        Call<PaginationResponse<List<ProductUserResponse>>> callProduct = productService.getProducts(pageable);
        System.out.println("Call success now get response...");
        callProduct.enqueue(new Callback<PaginationResponse<List<ProductUserResponse>>>() {
            @Override
            public void onResponse(Call<PaginationResponse<List<ProductUserResponse>>> call, Response<PaginationResponse<List<ProductUserResponse>>> response) {
                System.out.println("Load data in home page...");

                if (response.isSuccessful()) {
                    System.out.println("Success...");
                    PaginationResponse<List<ProductUserResponse>> paginationResponse = response.body();
                    List<ProductUserResponse> products = paginationResponse.getData();
                    for (ProductUserResponse product: products
                    ) {
                        System.out.println("Product " + product.toString());
                        productList.add(product);
                        productUserResponseAdapter.notifyDataSetChanged();
                        System.out.println("Load data complted");
                    }
                } else {
                    System.out.println("Load data fail...");
                }
            }

            @Override
            public void onFailure(Call<PaginationResponse<List<ProductUserResponse>>> call, Throwable t) {
                System.out.println("Load data fail..." + t.getMessage());
            }});

//        db.collection("Products")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Product product = document.toObject(Product.class);
//                                productList.add(product);
//                                productAdapter.notifyDataSetChanged();
//                            }
//                        } else {
//                            Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });


        //Popular Products
        popularRecycleview.setLayoutManager(new GridLayoutManager(getActivity(),2));
        popularProductsList = new ArrayList<>();
        popularProductsAdapter = new PopularProductsAdapter(getActivity(),popularProductsList);
        popularRecycleview.setAdapter(popularProductsAdapter);

        db.collection("AllProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PopularProducts popular_product = document.toObject(PopularProducts.class);
                                popularProductsList.add(popular_product);
                                popularProductsAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        return binding.getRoot();


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ImageSlider imageSlider = view.findViewById(R.id.image_slider);
//        List<SlideModel> slideModels = new ArrayList<>();
//
//        slideModels.add(new SlideModel(R.drawable.banner1,"Discount On Shoes Items", ScaleTypes.CENTER_CROP));
//        slideModels.add(new SlideModel(R.drawable.banner2,"Discount On Perfume", ScaleTypes.CENTER_CROP));
//        slideModels.add(new SlideModel(R.drawable.banner3,"70%", ScaleTypes.CENTER_CROP));
//        imageSlider.setImageList(slideModels);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}