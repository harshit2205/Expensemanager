package com.example.users.myexpensemanager1.Utils;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;

import com.cleveroad.slidingtutorial.Direction;

public class TransformItemExt implements Parcelable {


    @IdRes
    private int mViewResId;
    private Direction mDirection;
    private float mShiftCoefficient;
    private View mView;

    /**
     * Create new {@link TransformItemExt} instance.
     *
     * @param viewResId        resource view id
     * @param direction        {@link Direction} of translation
     * @param shiftCoefficient speed translation coefficient
     */
    public static TransformItemExt create(@IdRes int viewResId, @NonNull Direction direction, float shiftCoefficient) {
        return new TransformItemExt(viewResId, direction, shiftCoefficient);
    }

    private TransformItemExt(@IdRes int viewResId, Direction direction, float shiftCoefficient) {
        this.mViewResId = viewResId;
        this.mDirection = direction;
        this.mShiftCoefficient = shiftCoefficient;
    }

    int getViewResId() {
        return mViewResId;
    }

    Direction getDirection() {
        return mDirection;
    }

    float getShiftCoefficient() {
        return mShiftCoefficient;
    }

    View getView() {
        return mView;
    }

    void setView(View view) {
        mView = view;
    }

    protected TransformItemExt(Parcel in) {
        mViewResId = in.readInt();
        mDirection = Direction.valueOf(in.readString());
        mShiftCoefficient = in.readFloat();
    }

    public static final Creator<TransformItemExt> CREATOR = new Creator<TransformItemExt>() {
        @Override
        public TransformItemExt createFromParcel(Parcel in) {
            return new TransformItemExt(in);
        }

        @Override
        public TransformItemExt[] newArray(int size) {
            return new TransformItemExt[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mViewResId);
        dest.writeString(mDirection.name());
        dest.writeFloat(mShiftCoefficient);
    }

}