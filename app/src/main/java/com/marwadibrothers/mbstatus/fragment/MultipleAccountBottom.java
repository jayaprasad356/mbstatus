package com.marwadibrothers.mbstatus.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.marwadibrothers.mbstatus.R;
import com.marwadibrothers.mbstatus.activity.BusinessProfileActivity;
import com.marwadibrothers.mbstatus.activity.PackageActivity;
import com.marwadibrothers.mbstatus.adapter.MyMasterRecyclerAdapter;
import com.marwadibrothers.mbstatus.databinding.FragmentMultipleAccountBottomBinding;
import com.marwadibrothers.mbstatus.databinding.ItemMyAccountBinding;
import com.marwadibrothers.mbstatus.models.buiness.BusinessExtraData;
import com.marwadibrothers.mbstatus.models.buiness.BusinessResponse;
import com.marwadibrothers.mbstatus.models.buiness.BusinessResponseData;
import com.marwadibrothers.mbstatus.sevices.ApiInterface;
import com.marwadibrothers.mbstatus.sevices.RetrofitClient;
import com.marwadibrothers.mbstatus.storage.SharedPreferencesHelper;
import com.marwadibrothers.mbstatus.utils.Config;
import com.marwadibrothers.mbstatus.utils.Helper;
import com.marwadibrothers.mbstatus.weidget.circleview.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.marwadibrothers.mbstatus.storage.SharedPreferencesHelper.STORAGE_BUSINESS_ACCOUNT;
import static com.marwadibrothers.mbstatus.utils.Config.SELECT_ACCOUNT;
import static com.marwadibrothers.mbstatus.utils.Config.SELECT_ACCOUNT_ID;

public class MultipleAccountBottom extends BottomSheetDialogFragment {
    private FragmentMultipleAccountBottomBinding binding;
    private MyMasterRecyclerAdapter recyclerAdapter;
    private RecyclerView rvAcount;
    private Context context;
    private List<BusinessResponseData> businessarrayList = new ArrayList<>();
    private BusinessExtraData businessExtraData = null;
    public static final String TAG = "ActionBottomDialog";
    private ApiInterface apiInterface;
    private SharedPreferencesHelper preferencesHelper;
    private TextView txtAccName;
    private CircleImageView profileImage;

    public MultipleAccountBottom(TextView txtAccName, CircleImageView profileImage) {
        // Required empty public constructor
        this.txtAccName = txtAccName;
        this.profileImage = profileImage;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_multiple_account_bottom, container, false);
        initviews();
        return binding.getRoot();
    }


    private void initviews() {
        context = getActivity();
        preferencesHelper = new SharedPreferencesHelper(context);
        apiInterface = RetrofitClient.getRetrofitInstance(context).create(ApiInterface.class);
        rvAcount = binding.rvAcount;
        setRecyclerAdapter();
        initAdapter();

        if (Helper.checkInternet(context)) {
            GetBuinsessList();
        }

        binding.rltvAddAcc.setOnClickListener(v -> {
//                    Intent intent = new Intent(context, BusinessProfileActivity.class);
//                    intent.putExtra(Config.FROM, Config.ADD_BUSINESS);
//                    startActivity(intent);

            if (businessarrayList != null) {
                if (businessExtraData.getPlan_status())
                    if (businessExtraData.getBusiness_added() < Integer.parseInt(businessExtraData.getBusiness_limit()))
                        startActivity(new Intent(context, BusinessProfileActivity.class).putExtra(Config.FROM, Config.ADD_BUSINESS_REGULER));
                    else
                        Toast.makeText(context, "your business add limit is over!", Toast.LENGTH_LONG).show();
                else
                    startActivity(new Intent(context, PackageActivity.class));
            } else
                startActivity(new Intent(context, PackageActivity.class));
            dismiss();
        });
    }


    private void setRecyclerAdapter() {
        rvAcount.setAdapter(recyclerAdapter);
    }

    private void BusinessUpdateProductlist(List<BusinessResponseData> list) {
        businessarrayList = list;
        if (recyclerAdapter == null) {
            initAdapter();
        } else {
            recyclerAdapter.updateNewList(list);
        }
        rvAcount.setAdapter(recyclerAdapter);
    }

    private void initAdapter() {
        recyclerAdapter = new MyMasterRecyclerAdapter(businessarrayList, R.layout.item_my_account);
        rvAcount.setLayoutManager(new GridLayoutManager(context, 1));
        rvAcount.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnBind(new MyMasterRecyclerAdapter.OnBind() {
            @Override
            public void onBindViewHolder(ViewDataBinding viewDataBinding, int position) {
                ItemMyAccountBinding binding = (ItemMyAccountBinding) viewDataBinding;
                BusinessResponseData model = businessarrayList.get(position);
                binding.setModel(model);
                Glide.with(context).load(Config.IMG_PATH + model.getBusinessLogo()).into(binding.ivProfile);
                if (model.isSetselectedAcc()) {
                    binding.ivChoosen.setImageResource(R.drawable.ic_radio_on_button);
                } else {
                    binding.ivChoosen.setImageResource(R.drawable.unfill_radio_button);
                }
                /*if (position == businessarrayList.size() - 1) {
                    binding.rltvAddAcc.setVisibility(View.VISIBLE);
                } else {
                    binding.rltvAddAcc.setVisibility(View.GONE);
                }


                binding.rltvAddAcc.setOnClickListener(v -> {
//                    Intent intent = new Intent(context, BusinessProfileActivity.class);
//                    intent.putExtra(Config.FROM, Config.ADD_BUSINESS);
//                    startActivity(intent);

                    if (businessarrayList != null) {
                        if (businessExtraData.getPlan_status())
                            if (businessExtraData.getBusiness_added() < Integer.parseInt(businessExtraData.getBusiness_limit()))
                                startActivity(new Intent(context, BusinessProfileActivity.class).putExtra(Config.FROM, Config.ADD_BUSINESS_REGULER));
                            else
                                Toast.makeText(context, "your business add limit is over!", Toast.LENGTH_LONG).show();
                        else
                            startActivity(new Intent(context, PackageActivity.class));
                    } else
                        startActivity(new Intent(context, PackageActivity.class));
                    dismiss();
                });*/

                binding.getRoot().setOnClickListener(v -> {
                    RemoveDot();
                    businessarrayList.get(position).setSetselectedAcc(true);
                    preferencesHelper.setBoolean(SELECT_ACCOUNT, true);
                    preferencesHelper.setString(SELECT_ACCOUNT_ID, model.getBusinessId());
                    txtAccName.setText(model.getBusinessName());
                    Glide.with(context).load(Config.IMG_PATH + model.getBusinessLogo()).into(profileImage);
                    AddSelectedAccount(businessarrayList.get(position));
                    BusinessUpdateProductlist(businessarrayList);
                });
            }

            private void RemoveDot() {
                for (int i = 0; i < businessarrayList.size(); i++) {
                    businessarrayList.get(i).setSetselectedAcc(false);
                }

            }
        });

    }

    private void GetBuinsessList() {
        businessarrayList.clear();
        Helper.Show_loader(context, false, false);
        RequestBody user_id = RequestBody.create(new SharedPreferencesHelper(context).getString(Config.USER_ID), MediaType.parse("text/plain"));

        Call<BusinessResponse> call = apiInterface.get_user_business_list(user_id);
        call.enqueue(new Callback<BusinessResponse>() {
            @Override
            public void onResponse(Call<BusinessResponse> call, Response<BusinessResponse> response) {
                if (response.isSuccessful()) {
                    Helper.cancel_loader();
                    try {
                        Log.d("sdshdh", "dgsg" + response.body().getData().size());
                        businessarrayList.addAll(response.body().getData());
                        businessExtraData = response.body().getExtraData();
                        if (preferencesHelper.getBoolean(SELECT_ACCOUNT)) {
                            SelectionLoop();
                        } else {
                            businessarrayList.get(0).setSetselectedAcc(true);
                            AddSelectedAccount(businessarrayList.get(0));
                        }
                        BusinessUpdateProductlist(businessarrayList);
                    } catch (Exception e) {
                        Helper.cancel_loader();
                        e.printStackTrace();
                    }
                } else {
                    Helper.cancel_loader();
                }
            }

            @Override
            public void onFailure(Call<BusinessResponse> call, Throwable t) {
                Helper.cancel_loader();
                Log.d("SignUpPhoneVerify", "onFailure: " + t.getMessage() + "_____" + t.getCause());
            }
        });

    }

    private void SelectionLoop() {
        Log.e("SELECT_ACCOUNT_ID", preferencesHelper.getString(SELECT_ACCOUNT_ID));
        for (int i = 0; i < businessarrayList.size(); i++) {
            if (businessarrayList.get(i).getBusinessId().equals(preferencesHelper.getString(SELECT_ACCOUNT_ID))) {
                Log.e("getBusinessId", "SAMEe");
                AddSelectedAccount(businessarrayList.get(i));
                businessarrayList.get(i).setSetselectedAcc(true);
                break;
            }
        }

    }

    private void AddSelectedAccount(BusinessResponseData userBusinessListResponse) {
        try {
            SharedPreferencesHelper.saveObjectToSharedPreference(context, STORAGE_BUSINESS_ACCOUNT, userBusinessListResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}