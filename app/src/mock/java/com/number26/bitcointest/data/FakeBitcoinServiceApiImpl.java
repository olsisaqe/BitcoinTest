/*
 * Copyright 2015, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.number26.bitcointest.data;

import com.google.gson.GsonBuilder;
import com.number26.bitcointest.data.model.BitcoinsGraphResponse;
import com.number26.bitcointest.util.ConfigUtils;


/**
 * Fake implementation of {@link BitcoinServiceApi} to inject a fake service in a hermetic test.
 */
public class FakeBitcoinServiceApiImpl implements BitcoinServiceApi {

    public static final BitcoinsGraphResponse BITCOIN_GRAPH_FAKE_DATA = new GsonBuilder().
            create().
            fromJson(ConfigUtils.JSON_FAKE_DATA, BitcoinsGraphResponse.class);


    @Override
    public void getBitcoinsGrapData(String queryString, BitcoinsServiceCallback<BitcoinsGraphResponse> callback){
        callback.onLoaded(BITCOIN_GRAPH_FAKE_DATA);
    }
}
