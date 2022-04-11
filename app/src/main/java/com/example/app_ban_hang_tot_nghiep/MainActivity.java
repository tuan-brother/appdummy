package com.example.app_ban_hang_tot_nghiep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.app_ban_hang_tot_nghiep.adapter.ExpandableListAdapter;
import com.example.app_ban_hang_tot_nghiep.adapter.HomeAdapter;
import com.example.app_ban_hang_tot_nghiep.adapter.SliderAdapterExample;
import com.example.app_ban_hang_tot_nghiep.databinding.ActivityMainBinding;
import com.example.app_ban_hang_tot_nghiep.fragment.CartFragment;
import com.example.app_ban_hang_tot_nghiep.fragment.CatorogyCommonFragment;
import com.example.app_ban_hang_tot_nghiep.fragment.DetailProductFragment;
import com.example.app_ban_hang_tot_nghiep.fragment.HomeFragment;
import com.example.app_ban_hang_tot_nghiep.fragment.SearchFragment;
import com.example.app_ban_hang_tot_nghiep.model.Category;
import com.example.app_ban_hang_tot_nghiep.model.MenuModel;
import com.example.app_ban_hang_tot_nghiep.model.Product;
import com.example.app_ban_hang_tot_nghiep.model.SliderItem;
import com.example.app_ban_hang_tot_nghiep.view.decor.GridSpacingItemDecoration;
import com.example.app_ban_hang_tot_nghiep.viewmodel.MainViewModel;
import com.google.android.material.navigation.NavigationView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public ActivityMainBinding mBinding;
    public MainViewModel mViewModel;
    public ExpandableListAdapter expandableListAdapter;
    List<Category> headerList = new ArrayList<>();
    List<String> listDemo = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    private SliderAdapterExample adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setUpViewModel();
        mBinding.navigationView.setNavigationItemSelectedListener(this);
        addHome();
        setUpSlider();
        populateExpandableList();

//        addNewItem();
        renewItems();
        onClick();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.home) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, new HomeFragment()).commit();
        } else if (id == R.id.information) {
            Intent intent = new Intent(this, InforDevActivity.class);
            startActivity(intent);
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.parent_content, new CatorogyCommonFragment().newInstance(id + "", id + ""), "catetory").commit();
        }
        mBinding.drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onBackPressed() {
        if (mBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mBinding.drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }

        Fragment fragmentManager = getSupportFragmentManager().findFragmentByTag("preview");
        if (fragmentManager != null) {
            getSupportFragmentManager().beginTransaction().remove(fragmentManager).commit();
            return;
        }
        Fragment fragmentSearch = getSupportFragmentManager().findFragmentByTag("search");
        if (fragmentSearch != null) {
            getSupportFragmentManager().beginTransaction().remove(fragmentSearch).commit();
            return;
        }
        Fragment fragmentCaterory = getSupportFragmentManager().findFragmentByTag("catetory");
        if (fragmentCaterory != null) {
            Log.d("TAG", "onBackPressed:  " + fragmentCaterory);
            getSupportFragmentManager().beginTransaction().remove(fragmentCaterory).commit();
            return;
        }

        Fragment fragmentCart = getSupportFragmentManager().findFragmentByTag("cart");
        if (fragmentCart != null) {
            Log.d("TAG", "onBackPressed:  " + fragmentCart);
            getSupportFragmentManager().beginTransaction().remove(fragmentCart).commit();
            return;
        }

        super.onBackPressed();
    }

    public void setUpViewModel() {
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mViewModel.getListCategory();
        mViewModel.listCate.observe(this, data -> {
            headerList.clear();
            headerList.addAll(data);
            expandableListAdapter.notifyDataSetChanged();
        });
    }

    void prepareMenuData() {
    }

    private void populateExpandableList() {
        expandableListAdapter = new ExpandableListAdapter(this, headerList, new HashMap<>());
        mBinding.expandableListView.setAdapter(expandableListAdapter);

        mBinding.expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.parent_content, new CatorogyCommonFragment().newInstance(headerList.get(groupPosition).getName(), headerList.get(groupPosition).getId() + ""), "catetory").commit();
                mBinding.drawerLayout.closeDrawers();
                return false;
            }
        });

        mBinding.expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                return false;
            }
        });
    }

    private void onClick() {
        mBinding.information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), InforDevActivity.class);
                startActivity(intent);
            }
        });
        mBinding.incController.imgHamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        mBinding.incController.imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinding.drawerLayout.closeDrawers();
                gotoSearch();
            }
        });

        mBinding.incController.imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinding.drawerLayout.closeDrawers();
                gotoCart();
            }
        });
    }

    private void addHome() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new HomeFragment()).commit();
    }

    void setUpSlider() {
        adapter = new SliderAdapterExample(this);
        mBinding.imageSlider.setSliderAdapter(adapter);
        mBinding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        mBinding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        mBinding.imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        mBinding.imageSlider.setIndicatorSelectedColor(Color.WHITE);
        mBinding.imageSlider.setIndicatorUnselectedColor(Color.GRAY);
        mBinding.imageSlider.setScrollTimeInSec(3);
        mBinding.imageSlider.setAutoCycle(true);
        mBinding.imageSlider.startAutoCycle();
    }

    public void renewItems() {
        List<SliderItem> sliderItemList = new ArrayList<>();
        //dummy data
//        for (int i = 0; i < 5; i++) {
//            SliderItem sliderItem = new SliderItem();
//            sliderItem.setDescription("Slider Item " + i);
//            if (i % 2 == 0) {
//                sliderItem.setImageUrl("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
//            } else {
//                sliderItem.setImageUrl("https://images.pexels.com/photos/747964/pexels-photo-747964.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260");
//            }
//            sliderItemList.add(sliderItem);
//        }
        sliderItemList.add(new SliderItem("Slider Item", "https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"));
        adapter.renewItems(sliderItemList);
    }

    public void gotoSearch() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.parent_content, new SearchFragment(), "search").commit();
    }

    public void gotoCart() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.parent_content, new CartFragment(), "cart").commit();
    }
}