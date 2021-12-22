// Generated by view binder compiler. Do not edit!
package com.macamp.complaint.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.Guideline;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.macamp.complaint.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityPendingBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final CardView bottomCardView;

  @NonNull
  public final CardView cardView;

  @NonNull
  public final AppCompatButton doneBtn;

  @NonNull
  public final Guideline guideline2;

  @NonNull
  public final AppCompatTextView header;

  @NonNull
  public final AppCompatImageView imageViewSelect;

  @NonNull
  public final AppCompatImageView ivAddress;

  @NonNull
  public final AppCompatTextView ivBookmark;

  @NonNull
  public final AppCompatTextView ivLike;

  @NonNull
  public final AppCompatTextView ivRatingBar;

  @NonNull
  public final AppCompatTextView ivStar;

  @NonNull
  public final AppCompatImageView ivUser;

  @NonNull
  public final AppCompatButton returnBtn;

  @NonNull
  public final AppCompatTextView shareBtn;

  @NonNull
  public final LinearLayoutCompat showBtnLayout;

  @NonNull
  public final AppCompatTextView statusTxt;

  @NonNull
  public final AppCompatTextView timeDateTxt;

  @NonNull
  public final AppCompatTextView tvAddress;

  @NonNull
  public final AppCompatTextView tvComplaint;

  @NonNull
  public final AppCompatTextView tvId;

  @NonNull
  public final AppCompatTextView tvName;

  @NonNull
  public final AppCompatTextView tvReason;

  private ActivityPendingBinding(@NonNull ScrollView rootView, @NonNull CardView bottomCardView,
      @NonNull CardView cardView, @NonNull AppCompatButton doneBtn, @NonNull Guideline guideline2,
      @NonNull AppCompatTextView header, @NonNull AppCompatImageView imageViewSelect,
      @NonNull AppCompatImageView ivAddress, @NonNull AppCompatTextView ivBookmark,
      @NonNull AppCompatTextView ivLike, @NonNull AppCompatTextView ivRatingBar,
      @NonNull AppCompatTextView ivStar, @NonNull AppCompatImageView ivUser,
      @NonNull AppCompatButton returnBtn, @NonNull AppCompatTextView shareBtn,
      @NonNull LinearLayoutCompat showBtnLayout, @NonNull AppCompatTextView statusTxt,
      @NonNull AppCompatTextView timeDateTxt, @NonNull AppCompatTextView tvAddress,
      @NonNull AppCompatTextView tvComplaint, @NonNull AppCompatTextView tvId,
      @NonNull AppCompatTextView tvName, @NonNull AppCompatTextView tvReason) {
    this.rootView = rootView;
    this.bottomCardView = bottomCardView;
    this.cardView = cardView;
    this.doneBtn = doneBtn;
    this.guideline2 = guideline2;
    this.header = header;
    this.imageViewSelect = imageViewSelect;
    this.ivAddress = ivAddress;
    this.ivBookmark = ivBookmark;
    this.ivLike = ivLike;
    this.ivRatingBar = ivRatingBar;
    this.ivStar = ivStar;
    this.ivUser = ivUser;
    this.returnBtn = returnBtn;
    this.shareBtn = shareBtn;
    this.showBtnLayout = showBtnLayout;
    this.statusTxt = statusTxt;
    this.timeDateTxt = timeDateTxt;
    this.tvAddress = tvAddress;
    this.tvComplaint = tvComplaint;
    this.tvId = tvId;
    this.tvName = tvName;
    this.tvReason = tvReason;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityPendingBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityPendingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_pending, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityPendingBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bottomCardView;
      CardView bottomCardView = ViewBindings.findChildViewById(rootView, id);
      if (bottomCardView == null) {
        break missingId;
      }

      id = R.id.cardView;
      CardView cardView = ViewBindings.findChildViewById(rootView, id);
      if (cardView == null) {
        break missingId;
      }

      id = R.id.doneBtn;
      AppCompatButton doneBtn = ViewBindings.findChildViewById(rootView, id);
      if (doneBtn == null) {
        break missingId;
      }

      id = R.id.guideline2;
      Guideline guideline2 = ViewBindings.findChildViewById(rootView, id);
      if (guideline2 == null) {
        break missingId;
      }

      id = R.id.header;
      AppCompatTextView header = ViewBindings.findChildViewById(rootView, id);
      if (header == null) {
        break missingId;
      }

      id = R.id.imageViewSelect;
      AppCompatImageView imageViewSelect = ViewBindings.findChildViewById(rootView, id);
      if (imageViewSelect == null) {
        break missingId;
      }

      id = R.id.iv_address;
      AppCompatImageView ivAddress = ViewBindings.findChildViewById(rootView, id);
      if (ivAddress == null) {
        break missingId;
      }

      id = R.id.iv_bookmark;
      AppCompatTextView ivBookmark = ViewBindings.findChildViewById(rootView, id);
      if (ivBookmark == null) {
        break missingId;
      }

      id = R.id.iv_like;
      AppCompatTextView ivLike = ViewBindings.findChildViewById(rootView, id);
      if (ivLike == null) {
        break missingId;
      }

      id = R.id.iv_rating_bar;
      AppCompatTextView ivRatingBar = ViewBindings.findChildViewById(rootView, id);
      if (ivRatingBar == null) {
        break missingId;
      }

      id = R.id.iv_star;
      AppCompatTextView ivStar = ViewBindings.findChildViewById(rootView, id);
      if (ivStar == null) {
        break missingId;
      }

      id = R.id.iv_user;
      AppCompatImageView ivUser = ViewBindings.findChildViewById(rootView, id);
      if (ivUser == null) {
        break missingId;
      }

      id = R.id.returnBtn;
      AppCompatButton returnBtn = ViewBindings.findChildViewById(rootView, id);
      if (returnBtn == null) {
        break missingId;
      }

      id = R.id.shareBtn;
      AppCompatTextView shareBtn = ViewBindings.findChildViewById(rootView, id);
      if (shareBtn == null) {
        break missingId;
      }

      id = R.id.showBtnLayout;
      LinearLayoutCompat showBtnLayout = ViewBindings.findChildViewById(rootView, id);
      if (showBtnLayout == null) {
        break missingId;
      }

      id = R.id.statusTxt;
      AppCompatTextView statusTxt = ViewBindings.findChildViewById(rootView, id);
      if (statusTxt == null) {
        break missingId;
      }

      id = R.id.timeDateTxt;
      AppCompatTextView timeDateTxt = ViewBindings.findChildViewById(rootView, id);
      if (timeDateTxt == null) {
        break missingId;
      }

      id = R.id.tv_address;
      AppCompatTextView tvAddress = ViewBindings.findChildViewById(rootView, id);
      if (tvAddress == null) {
        break missingId;
      }

      id = R.id.tv_complaint;
      AppCompatTextView tvComplaint = ViewBindings.findChildViewById(rootView, id);
      if (tvComplaint == null) {
        break missingId;
      }

      id = R.id.tv_id;
      AppCompatTextView tvId = ViewBindings.findChildViewById(rootView, id);
      if (tvId == null) {
        break missingId;
      }

      id = R.id.tv_name;
      AppCompatTextView tvName = ViewBindings.findChildViewById(rootView, id);
      if (tvName == null) {
        break missingId;
      }

      id = R.id.tv_reason;
      AppCompatTextView tvReason = ViewBindings.findChildViewById(rootView, id);
      if (tvReason == null) {
        break missingId;
      }

      return new ActivityPendingBinding((ScrollView) rootView, bottomCardView, cardView, doneBtn,
          guideline2, header, imageViewSelect, ivAddress, ivBookmark, ivLike, ivRatingBar, ivStar,
          ivUser, returnBtn, shareBtn, showBtnLayout, statusTxt, timeDateTxt, tvAddress,
          tvComplaint, tvId, tvName, tvReason);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
