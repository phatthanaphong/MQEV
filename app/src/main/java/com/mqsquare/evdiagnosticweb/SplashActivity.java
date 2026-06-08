package com.mqsquare.evdiagnosticweb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Full screen — ไม่มี title bar / status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        setContentView(R.layout.activity_splash);

        ImageView logo = findViewById(R.id.splashLogo);

        // ─── Animation 1: Fade in + Scale up (700ms) ──────────
        Animation inAnim = AnimationUtils.loadAnimation(this, R.anim.splash_logo_in);
        inAnim.setFillAfter(true);  // คงไว้หลัง animation จบ

        inAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override public void onAnimationStart(Animation a) {
                logo.setVisibility(View.VISIBLE);
            }
            @Override public void onAnimationRepeat(Animation a) {}

            @Override
            public void onAnimationEnd(Animation a) {
                // ─── หน่วง 1.2 วิ แล้ว fade out ──────────────
                new Handler().postDelayed(() -> {
                    Animation outAnim = AnimationUtils.loadAnimation(
                        SplashActivity.this, R.anim.splash_logo_out);
                    outAnim.setFillAfter(true);

                    outAnim.setAnimationListener(new Animation.AnimationListener() {
                        @Override public void onAnimationStart(Animation a2) {}
                        @Override public void onAnimationRepeat(Animation a2) {}

                        @Override
                        public void onAnimationEnd(Animation a2) {
                            // ─── เปิด MainActivity ────────────
                            startActivity(new Intent(
                                SplashActivity.this, MainActivity.class));
                            // Slide transition
                            overridePendingTransition(
                                android.R.anim.fade_in,
                                android.R.anim.fade_out);
                            finish();
                        }
                    });
                    logo.startAnimation(outAnim);
                }, 1200); // หน่วง 1.2s หลัง fade in เสร็จ
                          // รวมทั้งหมด ~2.4s
            }
        });

        logo.startAnimation(inAnim);
    }

    @Override
    public void onBackPressed() {
        // กด back ระหว่าง splash = ไม่ทำอะไร
    }
}
