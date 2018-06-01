package com.jinhe.myframe.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.List;

import cn.pedant.SweetAlert.Constants;
import cn.pedant.SweetAlert.OptAnimationLoader;
import cn.pedant.SweetAlert.ProgressHelper;
import cn.pedant.SweetAlert.SuccessTickView;


/**
 * Created by LC on 2018/6/1.
 */
public class MyDialog extends Dialog implements View.OnClickListener{
    private View mDialogView;
    private AnimationSet mModalInAnim;
    private AnimationSet mModalOutAnim;
    private Animation mOverlayOutAnim;
    private Animation mErrorInAnim;
    private AnimationSet mErrorXInAnim;
    private AnimationSet mSuccessLayoutAnimSet;
    private Animation mSuccessBowAnim;
    private TextView mTitleTextView;
    private TextView mContentTextView;
    private FrameLayout mCustomViewContainer;
    private View mCustomView;
    private String mTitleText;
    private String mContentText;
    private boolean mShowCancel;
    private boolean mShowContent;
    private String mCancelText;
    private String mConfirmText;
    private String mNeutralText;
    private int mAlertType;
    private FrameLayout mErrorFrame;
    private FrameLayout mSuccessFrame;
    private FrameLayout mProgressFrame;
    private SuccessTickView mSuccessTick;
    private ImageView mErrorX;
    private View mSuccessLeftMask;
    private View mSuccessRightMask;
    private Drawable mCustomImgDrawable;
    private ImageView mCustomImage;
    private Button mConfirmButton;
    private Button mCancelButton;
    private Button mNeutralButton;
    private ProgressHelper mProgressHelper;
    private FrameLayout mWarningFrame;
    private MyDialog.OnSweetClickListener mCancelClickListener;
    private MyDialog.OnSweetClickListener mConfirmClickListener;
    private MyDialog.OnSweetClickListener mNeutralClickListener;
    private boolean mCloseFromCancel;

    public static final int NORMAL_TYPE = 0;
    public static final int ERROR_TYPE = 1;
    public static final int SUCCESS_TYPE = 2;
    public static final int WARNING_TYPE = 3;
    public static final int CUSTOM_IMAGE_TYPE = 4;
    public static final int PROGRESS_TYPE = 5;


    public static boolean DARK_STYLE = false;

    //aliases
    public final static int BUTTON_CONFIRM = DialogInterface.BUTTON_POSITIVE;
    public final static int BUTTON_CANCEL = DialogInterface.BUTTON_NEGATIVE;

    public interface OnSweetClickListener {
        void onClick(MyDialog sweetAlertDialog);
    }

    public MyDialog(Context context) {
        this(context, NORMAL_TYPE);
    }

    public MyDialog(Context context, int alertType) {
        super(context, DARK_STYLE ? cn.pedant.SweetAlert.R.style.alert_dialog_dark : cn.pedant.SweetAlert.R.style.alert_dialog_light);
        setCancelable(true);
        setCanceledOnTouchOutside(true); //TODO was false
        mProgressHelper = new ProgressHelper(context);
        mAlertType = alertType;
        mErrorInAnim = OptAnimationLoader.loadAnimation(getContext(), cn.pedant.SweetAlert.R.anim.error_frame_in);
        mErrorXInAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), cn.pedant.SweetAlert.R.anim.error_x_in);
        // 2.3.x system don't support alpha-animation on layer-list drawable
        // remove it from animation set
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
            List<Animation> childAnims = mErrorXInAnim.getAnimations();
            int idx = 0;
            for (; idx < childAnims.size(); idx++) {
                if (childAnims.get(idx) instanceof AlphaAnimation) {
                    break;
                }
            }
            if (idx < childAnims.size()) {
                childAnims.remove(idx);
            }
        }
        mSuccessBowAnim = OptAnimationLoader.loadAnimation(getContext(), cn.pedant.SweetAlert.R.anim.success_bow_roate);
        mSuccessLayoutAnimSet = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), cn.pedant.SweetAlert.R.anim.success_mask_layout);
        mModalInAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), cn.pedant.SweetAlert.R.anim.modal_in);
        mModalOutAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), cn.pedant.SweetAlert.R.anim.modal_out);
        mModalOutAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mDialogView.setVisibility(View.GONE);
                mDialogView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mCloseFromCancel) {
                            MyDialog.super.cancel();
                        } else {
                            MyDialog.super.dismiss();
                        }
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        // dialog overlay fade out
        mOverlayOutAnim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                WindowManager.LayoutParams wlp = getWindow().getAttributes();
                wlp.alpha = 1 - interpolatedTime;
                getWindow().setAttributes(wlp);
            }
        };
        mOverlayOutAnim.setDuration(120);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(cn.pedant.SweetAlert.R.layout.alert_dialog);

        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
        mTitleTextView =  findViewById(cn.pedant.SweetAlert.R.id.title_text);
        mContentTextView = findViewById(cn.pedant.SweetAlert.R.id.content_text);
        mCustomViewContainer = findViewById(cn.pedant.SweetAlert.R.id.custom_view_container);
        mErrorFrame =  findViewById(cn.pedant.SweetAlert.R.id.error_frame);
        mErrorX =  mErrorFrame.findViewById(cn.pedant.SweetAlert.R.id.error_x);
        mSuccessFrame =  findViewById(cn.pedant.SweetAlert.R.id.success_frame);
        mProgressFrame =  findViewById(cn.pedant.SweetAlert.R.id.progress_dialog);
        mSuccessTick = mSuccessFrame.findViewById(cn.pedant.SweetAlert.R.id.success_tick);
        mSuccessLeftMask = mSuccessFrame.findViewById(cn.pedant.SweetAlert.R.id.mask_left);
        mSuccessRightMask = mSuccessFrame.findViewById(cn.pedant.SweetAlert.R.id.mask_right);
        mCustomImage =  findViewById(cn.pedant.SweetAlert.R.id.custom_image);
        mWarningFrame =  findViewById(cn.pedant.SweetAlert.R.id.warning_frame);
        mConfirmButton =  findViewById(cn.pedant.SweetAlert.R.id.confirm_button);
        mConfirmButton.setOnClickListener(this);
        mConfirmButton.setOnTouchListener(Constants.FOCUS_TOUCH_LISTENER);
        mCancelButton =  findViewById(cn.pedant.SweetAlert.R.id.cancel_button);
        mCancelButton.setOnClickListener(this);
        mCancelButton.setOnTouchListener(Constants.FOCUS_TOUCH_LISTENER);
        mNeutralButton = findViewById(cn.pedant.SweetAlert.R.id.neutral_button);
        mNeutralButton.setOnClickListener(this);
        mNeutralButton.setOnTouchListener(Constants.FOCUS_TOUCH_LISTENER);
        mProgressHelper.setProgressWheel((ProgressWheel) findViewById(cn.pedant.SweetAlert.R.id.progressWheel));

        setTitleText(mTitleText);
        setContentText(mContentText);
        setCustomView(mCustomView);
        setCancelText(mCancelText);
        setConfirmText(mConfirmText);
        setNeutralText(mNeutralText);
        changeAlertType(mAlertType, true);

    }

    private void restore() {
        mCustomImage.setVisibility(View.GONE);
        mErrorFrame.setVisibility(View.GONE);
        mSuccessFrame.setVisibility(View.GONE);
        mWarningFrame.setVisibility(View.GONE);
        mProgressFrame.setVisibility(View.GONE);
        mConfirmButton.setVisibility(View.VISIBLE);

        mConfirmButton.setBackgroundResource(cn.pedant.SweetAlert.R.drawable.green_button_background);
        mErrorFrame.clearAnimation();
        mErrorX.clearAnimation();
        mSuccessTick.clearAnimation();
        mSuccessLeftMask.clearAnimation();
        mSuccessRightMask.clearAnimation();
    }

    private void playAnimation() {
        if (mAlertType == ERROR_TYPE) {
            mErrorFrame.startAnimation(mErrorInAnim);
            mErrorX.startAnimation(mErrorXInAnim);
        } else if (mAlertType == SUCCESS_TYPE) {
            mSuccessTick.startTickAnim();
            mSuccessRightMask.startAnimation(mSuccessBowAnim);
        }
    }

    private void changeAlertType(int alertType, boolean fromCreate) {
        mAlertType = alertType;
        // call after created views
        if (mDialogView != null) {
            if (!fromCreate) {
                // restore all of views state before switching alert type
                restore();
            }
            switch (mAlertType) {
                case ERROR_TYPE:
                    mErrorFrame.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS_TYPE:
                    mSuccessFrame.setVisibility(View.VISIBLE);
                    // initial rotate layout of success mask
                    mSuccessLeftMask.startAnimation(mSuccessLayoutAnimSet.getAnimations().get(0));
                    mSuccessRightMask.startAnimation(mSuccessLayoutAnimSet.getAnimations().get(1));
                    break;
                case WARNING_TYPE:
//                    mConfirmButton.setBackgroundResource(R.drawable.red_button_background);
                    mWarningFrame.setVisibility(View.VISIBLE);
                    break;
                case CUSTOM_IMAGE_TYPE:
                    setCustomImage(mCustomImgDrawable);
                    break;
                case PROGRESS_TYPE:
                    mProgressFrame.setVisibility(View.VISIBLE);
                    mConfirmButton.setVisibility(View.GONE);
                    mCancelButton.setVisibility(View.GONE);
                    mNeutralButton.setVisibility(View.GONE);
                    break;
            }
            if (!fromCreate) {
                playAnimation();
            }
        }
    }

    public int getAlerType() {
        return mAlertType;
    }

    public void changeAlertType(int alertType) {
        changeAlertType(alertType, false);
    }


    public String getTitleText() {
        return mTitleText;
    }

    public MyDialog setTitleText(String text) {
        mTitleText = text;
        if (mTitleTextView != null && mTitleText != null) {
            if (text.isEmpty()) {
                mTitleTextView.setVisibility(View.GONE);
            } else {
                mTitleTextView.setVisibility(View.VISIBLE);
                mTitleTextView.setText(mTitleText);
            }
        }
        return this;
    }

    public MyDialog setCustomImage(Drawable drawable) {
        mCustomImgDrawable = drawable;
        if (mCustomImage != null && mCustomImgDrawable != null) {
            mCustomImage.setVisibility(View.VISIBLE);
            mCustomImage.setImageDrawable(mCustomImgDrawable);
        }
        return this;
    }

    public MyDialog setCustomImage(int resourceId) {
        return setCustomImage(getContext().getResources().getDrawable(resourceId));
    }

    public String getContentText() {
        return mContentText;
    }

    public MyDialog setContentText(String text) {
        mContentText = text;
        if (mContentTextView != null && mContentText != null) {
            showContentText(true);
            mContentTextView.setText(mContentText);
            mContentTextView.setVisibility(View.VISIBLE);
            mCustomViewContainer.setVisibility(View.GONE);
        }
        return this;
    }

    public boolean isShowCancelButton() {
        return mShowCancel;
    }

    public MyDialog showCancelButton(boolean isShow) {
        mShowCancel = isShow;
        if (mCancelButton != null) {
            mCancelButton.setVisibility(mShowCancel ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public boolean isShowContentText() {
        return mShowContent;
    }

    public MyDialog showContentText(boolean isShow) {
        mShowContent = isShow;
        if (mContentTextView != null) {
            mContentTextView.setVisibility(mShowContent ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public String getCancelText() {
        return mCancelText;
    }

    public MyDialog setCancelText(String text) {
        mCancelText = text;
        if (mCancelButton != null && mCancelText != null) {
            showCancelButton(true);
            mCancelButton.setText(mCancelText);
        }
        return this;
    }

    public String getConfirmText() {
        return mConfirmText;
    }

    public MyDialog setConfirmText(String text) {
        mConfirmText = text;
        if (mConfirmButton != null && mConfirmText != null) {
            mConfirmButton.setText(mConfirmText);
        }
        return this;
    }

    public MyDialog setCancelClickListener(MyDialog.OnSweetClickListener listener) {
        mCancelClickListener = listener;
        return this;
    }

    public MyDialog setConfirmClickListener(MyDialog.OnSweetClickListener listener) {
        mConfirmClickListener = listener;
        return this;
    }

    public MyDialog setNeutralText(String text) {
        mNeutralText = text;
        if (mNeutralButton != null && mNeutralText != null && !text.isEmpty()) {
            mNeutralButton.setVisibility(View.VISIBLE);
            mNeutralButton.setText(mNeutralText);
        }
        return this;
    }

    public MyDialog setNeutralClickListener(MyDialog.OnSweetClickListener listener) {
        mNeutralClickListener = listener;
        return this;
    }

    @Override
    public void setTitle(CharSequence title) {
        this.setTitleText(title.toString());
    }

    @Override
    public void setTitle(int titleId) {
        this.setTitleText(getContext().getResources().getString(titleId));
    }

    public Button getButton(int buttonType) {
        switch (buttonType) {
            default:
            case BUTTON_CONFIRM:
                return mConfirmButton;
            case BUTTON_CANCEL:
                return mCancelButton;
            case BUTTON_NEUTRAL:
                return mNeutralButton;
        }
    }

    public MyDialog setConfirmButton(String text, MyDialog.OnSweetClickListener listener) {
        this.setConfirmText(text);
        this.setConfirmClickListener(listener);
        return this;
    }

    public MyDialog setConfirmButton(int resId, MyDialog.OnSweetClickListener listener) {
        String text = getContext().getResources().getString(resId);
        setConfirmButton(text, listener);
        return this;
    }


    public MyDialog setCancelButton(String text, MyDialog.OnSweetClickListener listener) {
        this.setCancelText(text);
        this.setCancelClickListener(listener);
        return this;
    }

    public MyDialog setCancelButton(int resId, MyDialog.OnSweetClickListener listener) {
        String text = getContext().getResources().getString(resId);
        setCancelButton(text, listener);
        return this;
    }

    public MyDialog setNeutralButton(String text, MyDialog.OnSweetClickListener listener) {
        this.setNeutralText(text);
        this.setNeutralClickListener(listener);
        return this;
    }

    public MyDialog setNeutralButton(int resId, MyDialog.OnSweetClickListener listener) {
        String text = getContext().getResources().getString(resId);
        setNeutralButton(text, listener);
        return this;
    }

    protected void onStart() {
        mDialogView.startAnimation(mModalInAnim);
        playAnimation();
    }

    /**
     * set custom view instead of message
     *
     * @param view
     */
    public MyDialog setCustomView(View view) {
        mCustomView = view;
        if (mCustomView != null && mCustomViewContainer != null) {
            mCustomViewContainer.addView(view);
            mCustomViewContainer.setVisibility(View.VISIBLE);
            mContentTextView.setVisibility(View.GONE);
        }
        return this;
    }

    /**
     * The real Dialog.cancel() will be invoked async-ly after the animation finishes.
     */
    @Override
    public void cancel() {
        dismissWithAnimation(true);
    }

    /**
     * The real Dialog.dismiss() will be invoked async-ly after the animation finishes.
     */
    public void dismissWithAnimation() {
        dismissWithAnimation(false);
    }

    private void dismissWithAnimation(boolean fromCancel) {
        mCloseFromCancel = fromCancel;
        mConfirmButton.startAnimation(mOverlayOutAnim);
        mDialogView.startAnimation(mModalOutAnim);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == cn.pedant.SweetAlert.R.id.cancel_button) {
            if (mCancelClickListener != null) {
                mCancelClickListener.onClick(MyDialog.this);
            } else {
                dismissWithAnimation();
            }
        } else if (v.getId() == cn.pedant.SweetAlert.R.id.confirm_button) {
            if (mConfirmClickListener != null) {
                mConfirmClickListener.onClick(MyDialog.this);
            } else {
                dismissWithAnimation();
            }
        } else if (v.getId() == cn.pedant.SweetAlert.R.id.neutral_button) {
            if (mNeutralClickListener != null) {
                mNeutralClickListener.onClick(MyDialog.this);
            } else {
                dismissWithAnimation();
            }
        }
    }

    public ProgressHelper getProgressHelper() {
        return mProgressHelper;
    }
}
