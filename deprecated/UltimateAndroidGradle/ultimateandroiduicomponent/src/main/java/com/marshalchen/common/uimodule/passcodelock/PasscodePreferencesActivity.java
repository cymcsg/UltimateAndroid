package com.marshalchen.common.uimodule.passcodelock;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.marshalchen.ultimateandroiduicomponent.R;

public class PasscodePreferencesActivity extends PreferenceActivity {
    
    static final int ENABLE_PASSLOCK = 0;
    static final int DISABLE_PASSLOCK = 1;
    static final int CHANGE_PASSWORD = 2;
    
    private Preference turnPasscodeOnOff = null;
    private Preference changePasscode = null;
    
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     
        overridePendingTransition(R.anim.slide_up, R.anim.do_nothing);

        setTitle("passcode_manage");

        if (android.os.Build.VERSION.SDK_INT >= 11) {
//            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
                
        addPreferencesFromResource(R.xml.passlock_preferences);        
         
        turnPasscodeOnOff = (Preference) findPreference("turn_passcode_on_off");
        changePasscode = (Preference) findPreference("change_passcode");
        
        if ( AppLockManager.getInstance().getCurrentAppLock().isPasswordLocked() ) { 
            turnPasscodeOnOff.setTitle("passcode_turn_off");
        } else {           
            turnPasscodeOnOff.setTitle("passcode_turn_on");
            changePasscode.setEnabled(false);
        }
        
        turnPasscodeOnOff.setOnPreferenceClickListener(passcodeOnOffTouchListener);
        changePasscode.setOnPreferenceClickListener(changePasscodeTouchListener);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // ignore orientation change
        super.onConfigurationChanged(newConfig);
    }
    
    OnPreferenceClickListener passcodeOnOffTouchListener = new OnPreferenceClickListener() {
        
        @Override
        public boolean onPreferenceClick(Preference preference) {
            int type = AppLockManager.getInstance().getCurrentAppLock().isPasswordLocked() ? DISABLE_PASSLOCK : ENABLE_PASSLOCK;
            Intent i = new Intent(PasscodePreferencesActivity.this, PasscodeManagePasswordActivity.class);
            i.putExtra("type", type);
            startActivityForResult(i, type);
            return false;
        }
    };
    
    private OnPreferenceClickListener changePasscodeTouchListener = new OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick (Preference preference) {
            Intent i = new Intent(PasscodePreferencesActivity.this, PasscodeManagePasswordActivity.class);
            i.putExtra("type", CHANGE_PASSWORD);
            i.putExtra("message", "passcode_enter_old_passcode");
            startActivityForResult(i, CHANGE_PASSWORD);
            return false;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case DISABLE_PASSLOCK:
                break;
            case ENABLE_PASSLOCK:
            case CHANGE_PASSWORD:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(PasscodePreferencesActivity.this, "passcode_set", Toast.LENGTH_SHORT).show();
                } 
                break;
            default:
                break;
        }
        updateUI();
    }
    
    private void updateUI() {
        if ( AppLockManager.getInstance().getCurrentAppLock().isPasswordLocked() ) { 
            turnPasscodeOnOff.setTitle("passcode_turn_off");
            changePasscode.setEnabled(true);
        } else {           
            turnPasscodeOnOff.setTitle("passcode_turn_on");
            changePasscode.setEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}