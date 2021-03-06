package test.test.icheck;

import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import test.test.icheck.RetroFit.IMyService;
import test.test.icheck.RetroFit.RetrofitClient;
import test.test.icheck.adapter.FriendsAdapter;
//import test.test.icheck.adapter.productAdapter;
import test.test.icheck.adapter.ProductAdapter;
import test.test.icheck.entity.Customer;
import test.test.icheck.entity.Friendship;
import test.test.icheck.entity.friends;
import test.test.icheck.entity.Product;
import test.test.icheck.entity.reviews;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

interface CompletionHandler {
    public void prodectFetched(List<Product> products);
}

public class HomeFragment extends Fragment {
    private static ProductAdapter adapter;
    IMyService iMyService;
    String json_string;
    TextView seeAllProducts,chatBot,id_textTrendingProducts,fashion,decoration,cosmetic;
    ImageView id_categoryHome;
    TextView searchUser;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<Product> productList;
    private static FriendsAdapter adapterf;
    private static ArrayList<friends> friendsList;
    private SharedPreferences sp ;
    public static final String FILE_NAME = "test.test.icheck.shared";
    private SharedPreferences Preferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        productList = new ArrayList<Product>();
        createProductListView(v,productList);
        createFriendListView(v);
        seeAllProducts = (TextView)v.findViewById(R.id.id_seeAllProducts);
        id_textTrendingProducts=(TextView)v.findViewById(R.id.id_textTrendingProducts);
        fashion=(TextView)v.findViewById(R.id.id_fashioncat);
        decoration=(TextView)v.findViewById(R.id.id_decorationcat);
        cosmetic=(TextView)v.findViewById(R.id.id_cosmeticcat);
        searchUser = (TextView)v.findViewById(R.id.id_searchProduct) ;
        id_categoryHome = (ImageView)v.findViewById(R.id.id_categoryHome);
        chatBot = (TextView)v.findViewById(R.id.id_chatBots);
        chatBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ChatBotActivity.class);
                startActivity(intent);
            }
        });
        seeAllProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AllProductsActivity.class);
                startActivity(intent);
            }
        });
        fashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AllProductsActivity.class);
                startActivity(intent);
           /*
                Intent intent = new Intent(getActivity(),ChatActivity.class);
                startActivity(intent);*/
            }
        });
        decoration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AllProductsActivity.class);
                startActivity(intent);
            }
        });
        cosmetic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AllProductsActivity.class);
                startActivity(intent);
            }
        });
        searchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SearchFragment();

                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.Fcontainer, fragment);
                System.out.printf("inside on click");
                transaction.commit();
            }
        });

        return v;
    }
    public void createProductListView(final View v,List<Product> productList){
        getProducts(productList, new CompletionHandler() {
            @Override
            public void prodectFetched(List<Product> products) {
                if (products.size() > 0){
                    adapter = new ProductAdapter(products,getContext());
                    recyclerView = (RecyclerView) v.findViewById(R.id.id_listProcuts);
                    recyclerView.setHasFixedSize(true);
                  //  GridLayoutManager gridLayoutManager = new GridLayoutManager(v.getContext(),2,LinearLayoutManager.VERTICAL,false);
                    layoutManager = new LinearLayoutManager(v.getContext(),LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);
                    seeAllProducts.setText("See All("+productList.size()+")");
                }else{
                    System.out.println("Erreur liste ");
                    Log.e("Home Fragement", "Erreur liste ");
                }
            }
        });
    }
    public void createFriendListView(View v){
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

        HashMap<String,String> map = new HashMap<>();
        Preferences = getContext().getSharedPreferences(FILE_NAME,MODE_PRIVATE);
        map.put("userId",Preferences.getString("userId",""));
        Call <ArrayList<Friendship>> call = iMyService.getFriendship(map);
       /* call.enqueue(new Callback<ArrayList<Friendship>>>() {
            @Override
            public void onResponse(Call<ArrayList<Friendship>> call, Response<ArrayList<Friendship>> response) {
                ArrayList<Customer> allUsers = new ArrayList<>();
                allUsers = response.body();
                adapterf = new FriendsAdapter(allUsers,getContext());
                recyclerView = (RecyclerView) v.findViewById(R.id.id_listFriends);
                layoutManager = new LinearLayoutManager(v.getContext(),LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapterf);
            }

            @Override
            public void onFailure(Call<ArrayList<Friendship>> call, Throwable t) {

            }
        });*/
       call.enqueue(new Callback<ArrayList<Friendship>>() {
           @Override
           public void onResponse(Call<ArrayList<Friendship>> call, Response<ArrayList<Friendship>> response) {
               ArrayList<Customer> allUsers = new ArrayList<>();
               ArrayList<Friendship> fship = response.body();
               for (int i=0;i<fship.size();i++){
                   allUsers.add(fship.get(i).getCustomer());
               }
               System.out.println("Users : "+allUsers);
               adapterf = new FriendsAdapter(allUsers,getContext());
               recyclerView = (RecyclerView) v.findViewById(R.id.id_listFriends);
               layoutManager = new LinearLayoutManager(v.getContext(),LinearLayoutManager.HORIZONTAL, false);
               recyclerView.setHasFixedSize(true);
               recyclerView.setLayoutManager(layoutManager);
               recyclerView.setItemAnimator(new DefaultItemAnimator());
               recyclerView.setAdapter(adapterf);
           }

           @Override
           public void onFailure(Call<ArrayList<Friendship>> call, Throwable t) {

           }
       });
    }

    public void getProducts(final List<Product> productList, final CompletionHandler handler ){
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);
        Call <List<Product>> call = iMyService.getProductsTrend();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> products = response.body();
                List<reviews> reviews;
                for (int i=0;i<products.size();i++){
                    productList.add(products.get(i));
                }
                handler.prodectFetched(productList);
                System.out.println("succes "+productList);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                System.out.println("succes");
            }
        });
    }

}