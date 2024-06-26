package com.faceunity.nama.seekbar.internal.compat;

import com.faceunity.nama.seekbar.DiscreteSeekBar;

/**
 * Currently, there's no {@link android.animation.ValueAnimator} compatibility version
 * and as we didn't want to throw in external dependencies, we made this small class.
 * <p/>
 * <p>
 * This will work like {@link androidx.core.view.ViewPropertyAnimatorCompat}, that is,
 * not doing anything on API<11 and using the default {@link android.animation.ValueAnimator}
 * on API>=11
 * </p>
 * <p>
 * This class is used to provide animation to the {@link DiscreteSeekBar}
 * when navigating with the Keypad
 * </p>
 *
 * @hide
 */
public abstract class AnimatorCompat {
    AnimatorCompat() {

    }

    public static final AnimatorCompat create(float start, float end, AnimationFrameUpdateListener listener) {
        return new AnimatorCompatBase(start, end, listener);
    }

    public abstract void cancel();

    public abstract boolean isRunning();

    public abstract void setDuration(int progressAnimationDuration);

    public abstract void start();

    public interface AnimationFrameUpdateListener {
        public void onAnimationFrame(float currentValue);
    }

    private static class AnimatorCompatBase extends AnimatorCompat {

        private final AnimationFrameUpdateListener mListener;
        private final float mEndValue;

        public AnimatorCompatBase(float start, float end, AnimationFrameUpdateListener listener) {
            mListener = listener;
            mEndValue = end;
        }

        @Override
        public void cancel() {

        }

        @Override
        public boolean isRunning() {
            return false;
        }

        @Override
        public void setDuration(int progressAnimationDuration) {

        }

        @Override
        public void start() {
            mListener.onAnimationFrame(mEndValue);
        }
    }
}
