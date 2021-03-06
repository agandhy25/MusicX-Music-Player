package com.rks.musicx.data.Eq;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.rks.musicx.misc.utils.Constants;

import static com.rks.musicx.misc.utils.Constants.AUDIO_ID;
import static com.rks.musicx.misc.utils.Constants.CLOSE_EFFECTS;

/**
 * Created by Coolalien on 12/23/2016.
 */

public class AudioEffects extends BroadcastReceiver{

    /**
     * Default Constructor
     */
    public AudioEffects(){
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String scan = intent.getAction();
        int AudioID = intent.getIntExtra(AUDIO_ID, 0);
        if (Constants.OPEN_EFFECTS.equals(scan)) {
            Equalizers.initEq(AudioID);
            BassBoosts.initBass(AudioID);
            Virtualizers.initVirtualizer(AudioID);
            Loud.initLoudnessEnhancer(AudioID);
            Reverb.initReverb(AudioID);
        } else if (Constants.CLOSE_EFFECTS.equals(scan)) {
            Equalizers.EndEq();
            BassBoosts.EndBass();
            Virtualizers.EndVirtual();
            Loud.EndLoudnessEnhancer();
            Reverb.EndReverb();
        }
    }

}
