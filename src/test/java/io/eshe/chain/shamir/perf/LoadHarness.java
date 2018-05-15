/*
 * Copyright © 2017 Coda Hale (coda.hale@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.eshe.chain.shamir.perf;

import io.eshe.chain.encrypt.shamir.Scheme;

public class LoadHarness {
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void main(String[] args) {
        final byte[] secret = new byte[10 * 1024];
        final Scheme scheme = Scheme.of(200, 20);
        for (int i = 0; i < 100_000_000; i++) {
            scheme.join(scheme.split(secret));
        }
    }
}